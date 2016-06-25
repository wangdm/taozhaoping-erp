package com.zh.base.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.zh.base.model.bean.User;
import com.zh.base.realm.UserRealm;
import com.zh.base.service.UserInfoService;
import com.zh.base.util.Base64;
import com.zh.base.util.MailUtil;
import com.zh.base.util.PasswordDigestUtil;
import com.zh.base.util.Tools;
import com.zh.core.base.action.BaseAction;
import com.zh.core.model.VariableUtil;
import com.zh.core.util.BCrypt;
import com.zh.core.util.DateUtil;


public class LoginAction extends BaseAction {


	private static final long serialVersionUID = 1L;
	
	private static Logger LOGGER = LoggerFactory.getLogger(LoginAction.class); 

	// 前台传入的验证码
	private String validecode;
	
	//新密码
	private String newPassWord;
	
	//随机数
	private String hash;
	
	//创建时间
	private String created;
	
	//加密后的字符串
	private String passwordDigest;

	/**
	 * 系统用户接口
	 */
	@Autowired
	private UserInfoService userInfoService;
	
	private User userInfo = new User();;

	public String excute() {
		Subject subject = SecurityUtils.getSubject();
		
		boolean isAuth = subject.isAuthenticated();
		if(isAuth){
			return "success";
		}
		return "creater";
	}
	
	/**
	 * 登出
	 * @return
	 */
	public String logout(){
		Subject subject = SecurityUtils.getSubject();
		//清除session中的用户信息
		this.getSession().removeAttribute(VariableUtil.SESSION_KEY);
		subject.logout();
		return "success";
	}

	/**
	 * 用户登录
	 * @return
	 */
	public String loginUser() {
		String code = (String) this.getRequest().getSession().getAttribute("code");
		if (null == validecode || null == code || !validecode.toUpperCase().equals(code)) {
			this.setErrorMessage(getText("COM.SSI.ERROR.CODE"));
			return "creater";
		}
		String password = null;
		if(null != userInfo.getUserPassword()){
			password = userInfo.getUserPassword();
		}else{
			this.setErrorMessage(getText("COM.SSI.ERROR.USERNAME"));
			return "creater";
		}
		//userInfo.setUserPassword(null);
		//User user = userInfoService.query(userInfo);
		//是否记住密码
		boolean isRememberMe = userInfo.isRemember();
		
		Subject subject = SecurityUtils.getSubject();
		
        String loginName = userInfo.getLoginName();
		char[] loginPassword = password.toCharArray();
		UsernamePasswordToken token = new UsernamePasswordToken(loginName, loginPassword);
        token.setRememberMe(isRememberMe);
        
        try {  
        	subject.login(token);
        	
            String userID = (String) subject.getPrincipal();
            LOGGER.info("User [" + userID + "] logged in successfully.");
            User user = new User();
            user.setLoginName(loginName);
            user = userInfoService.query(user);
            this.getSession().setAttribute(VariableUtil.SESSION_KEY, user);
            return "success";
        } catch (UnknownAccountException uae) {
            errorMessage = "登录失败：" + "用户名不存在";  
            LOGGER.info(errorMessage);
        } catch (IncorrectCredentialsException ice) {
            errorMessage = "登录失败：" + "密码错误";  
            LOGGER.info(errorMessage);
        } catch (LockedAccountException lae) {  
            errorMessage = "登录失败：" + "账号已经锁定";  
            LOGGER.info(errorMessage);
        } catch(ExcessiveAttemptsException eae){
        	errorMessage = "登录失败：" + "输入密码错误次数过多";  
            LOGGER.info(errorMessage);
        } catch (AuthenticationException e) {
            errorMessage = "登录失败错误信息：" + e;
            LOGGER.error(errorMessage);
            e.printStackTrace();
            token.clear();
        }  
        this.setErrorMessage(errorMessage);
		return "creater";
        
		//密码验证
		/*if (null != password && null != user && BCrypt.checkpw(password , user.getUserPassword())) {
			//保存当前用户信息到session中
			this.getSession().setAttribute(VariableUtil.SESSION_KEY, user);
			return "success";
		} else {
			this.setErrorMessage(getText("COM.SSI.ERROR.USERNAME"));
			return "creater";
		}*/
	}
	
	/***
	 * 跳转到获取密码页面
	 * @return
	 */
	public String resetPassword(){
		LOGGER.debug("resetPassword()");
		return "sendEmail";
	}
	
	/***
	 * 重置密码
	 * @return
	 */
	public String doResetPassword(){
		LOGGER.debug("doResetPassword()");
		//验证码不对，返回
		String code = (String) this.getRequest().getSession().getAttribute("code");
		if (null == validecode || null == code || !validecode.toUpperCase().equals(code)) {
			this.setErrorMessage(getText("COM.SSI.ERROR.CODE"));
			return "sendEmail";
		}
		User user = userInfoService.query(userInfo);
		if(user != null){
			String email = user.getEmail();
			if(email != null && !email.isEmpty()){
				//发送邮件
				try {
					ActionContext context = ActionContext.getContext();
					HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
					String path = request.getContextPath(); 
					String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
					
					byte[] nonceValue = PasswordDigestUtil.generateNonce(16);
					String nonce = Base64.encode(nonceValue);
					
					String created = DateUtil.getCreated();
					
					String password = "whoisyourdaddy";
					
					String passwordDigest = PasswordDigestUtil.doPasswordDigest(nonce, created, password);
					
					StringBuffer url = new StringBuffer();
					url.append(basePath)
						.append("login/emailVerification.jspa?")
						.append("userInfo.loginName=")
						.append(URLEncoder.encode(userInfo.getLoginName(),"utf-8"))
						.append("&hash=").append(URLEncoder.encode(nonce,"utf-8"))
						.append("&created=").append(URLEncoder.encode(created,"utf-8"))
						.append("&passwordDigest=").append(URLEncoder.encode(passwordDigest,"utf-8"));
					
					StringBuffer text = new StringBuffer();
					String rn = "<br/>";
					text.append("尊敬的").append(user.getName()).append("，您好：").append(rn)
						.append("&nbsp;&nbsp;").append("点击下面链接重置密码").append(rn)
						.append("<a href=\"").append(url.toString()).append("\"").append(" target=_blank>重置密码</a>").append(rn)
						.append("如果点击无效，请复制下方网页地址到浏览器地址栏中打开：").append(rn)
						.append(url.toString());
					
					userInfo.setNonce(nonce);
					userInfo.setId(user.getId());
					userInfoService.update(userInfo);
					MailUtil.sendMail(email, "", "重置密码", text.toString());
					userInfo.setEmail(Tools.mailCover(email));
				} catch (MessagingException e) {
					e.printStackTrace();
					this.setErrorMessage("用户不存在，或者用户没有配置邮箱地址，请确认后重试。");
					return "sendEmail";
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					this.setErrorMessage("用户不存在，或者用户没有配置邮箱地址，请确认后重试。");
					return "sendEmail";
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
					this.setErrorMessage("用户不存在，或者用户没有配置邮箱地址，请确认后重试。");
					return "sendEmail";
				}
			}else{
				this.setErrorMessage("用户不存在，或者用户没有配置邮箱地址，请确认后重试。");
				return "sendEmail";
			}
		}else{
			this.setErrorMessage("用户不存在，或者用户没有配置邮箱地址，请确认后重试。");
			return "sendEmail";
		}
		return "sendSuccess";
	}
	
	/***
	 * 校验邮件中的URL
	 * @return
	 */
	public String verify(){
		LOGGER.debug("verify()");
		String passwordDigestNew;
		User user = userInfoService.query(userInfo);
		String serverNonce = user.getNonce();
		try {
			if(hash != null && hash.equalsIgnoreCase(serverNonce)){
				passwordDigestNew = PasswordDigestUtil.doPasswordDigest(hash, created, "whoisyourdaddy");
				//通过校验
				if(passwordDigest != null && passwordDigest.equals(passwordDigestNew)){
					Date createdDate = DateUtil.getDate(created);
					//验证有没过期，30分钟过期,后续可以修改为配置
					if(DateUtil.verifyCreated(createdDate, 1800, 0)){
						//初始化随机码，防止一次链接可以重置密码多次
						userInfo.setNonce("0");
						userInfo.setId(user.getId());
						userInfoService.update(userInfo);
					}else{
						return "verifyError";
					}
				}else{
					return "verifyError";
				}
			}else{
				return "verifyError";
			}
		} catch (UnsupportedEncodingException e) {
			return "verifyError";
		} catch (ParseException e) {
			return "verifyError";
		}
		return "verifySuccess";
	}
	
	/***
	 * 重置密码提交
	 * @return
	 */
	public String resetPwdSubmit(){
		LOGGER.debug("resetPwdSubmit()");
		User user = userInfoService.query(userInfo);
		//重置密码
		if(null != user && null != newPassWord  && !"".equals(newPassWord)) {
			String bcryptPassword = BCrypt.hashpw(newPassWord, BCrypt.gensalt(12));
			user.setUserPassword(bcryptPassword);
			user.setUpdateTime(new Date());
			userInfoService.update(user);
			Subject subject = SecurityUtils.getSubject();
			RealmSecurityManager securityManager =  (RealmSecurityManager) SecurityUtils.getSecurityManager();
			UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
			userRealm.clearCachedAuthenticationInfo(subject.getPrincipals());
			char[] loginPassword = newPassWord.toCharArray();
			UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), loginPassword);
			subject.login(token);
			
		}else{
			return "error";
		}
		
		return "success";
	}


	public String getValidecode() {
		return validecode;
	}

	public void setValidecode(String validecode) {
		this.validecode = validecode;
	}

	public Object getModel() {
		return userInfo;
	}

	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public User getUserInfo() {
		return userInfo;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getPasswordDigest() {
		return passwordDigest;
	}

	public void setPasswordDigest(String passwordDigest) {
		this.passwordDigest = passwordDigest;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}

	public String getNewPassWord() {
		return newPassWord;
	}

	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String editor() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
