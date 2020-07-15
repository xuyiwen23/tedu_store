package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.UploadAvatarException;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="/handle_reg.do",
		method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleReg(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		String phone,
		String email,
		@RequestParam(value="gender", required=false, defaultValue="1") Integer gender) {
		// ��5��������װ��User������
		User user = new User(
			username, password, email, phone, gender);
		// ����ҵ�������User reg(User user)����
		userService.reg(user);
		// ����ResponseResult����
		return new ResponseResult<Void>();
	}
	
	@RequestMapping(value="/handle_login.do",
		method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleLogin(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		HttpSession session) {
		// ����ҵ�������User login(String, String)����������ȡ����ֵ
		User user = userService.login(username, password);
		// ��uid��username��װ��Session��
		session.setAttribute("uid", user.getId());
		session.setAttribute("username", user.getUsername());
		// ����ResponseResult����
		return new ResponseResult<Void>();
	}
	
	@RequestMapping(value="/handle_change_password.do",
			method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleChangePassword(
		@RequestParam("old_password") String oldPassword,
		@RequestParam("new_password") String newPassword,
		HttpSession session) {
		// ��session��ȡuid
		Integer uid = getUidFromSession(session);
		// ִ���޸�
		userService.changePasswordByOldPassword(
				uid, oldPassword, newPassword);
		// ����
		return new ResponseResult<Void>();
	}
	
	@RequestMapping(value="/handle_change_info.do")
	@ResponseBody
	public ResponseResult<String> handleChangeInfo(
		User user, HttpServletRequest request,
		@RequestParam CommonsMultipartFile avatarFile,
	    HttpSession session) {
	    // �ж��û��������䣬���Ϊ""������Ϊnull
		// Ҳ������������ʽ���ж�
		if ("".equals(user.getUsername())) {
			user.setUsername(null);
		}
		if ("".equals(user.getEmail())) {
			user.setEmail(null);
		}
		
		// �жϴ˴β����Ƿ��ϴ���ͷ��
		if (!avatarFile.isEmpty()) {
			// �ϴ�ͷ�񣬲���ȡ�ϴ����·��
			String avatarPath = uploadAvatar(
					request, avatarFile);
			// ��ͷ���ļ���·����װ����д�뵽���ݱ���
			user.setAvatar(avatarPath);
		}
		
	    // ��session��ȡuid
		Integer uid = getUidFromSession(session);
	    // ��uid��װ������user��
		user.setId(uid);
	    // ִ���޸ģ�userService.changeInfo(user)
		userService.changeInfo(user);
	    // ����
		ResponseResult<String> rr
			= new ResponseResult<String>();
		rr.setData(user.getAvatar());
		return rr;
	}
	
	@RequestMapping("/getInfo.do")
	@ResponseBody
	public ResponseResult<User> getInfo(
			HttpSession session) {
	    // ��session�л�ȡuid
		Integer uid = getUidFromSession(session);
	    // ����ҵ�������getUserById()���õ���ǰ�û���User����
		User user = userService.getUserById(uid);
	    // ��������ֵ���󣬽�User�����װ��Data������
		ResponseResult<User> rr
			= new ResponseResult<User>();
		rr.setData(user);
	    // ����
		return rr;
	}
	
	/**
	 * �ϴ�ͷ��
	 * @param request HttpServletRequest
	 * @param avatarFile CommonsMultipartFile
	 * @return �ɹ��ϴ����ļ����浽��·��
	 * @throws UploadAvatarException �ϴ�ͷ���쳣
	 */
	private String uploadAvatar(
			HttpServletRequest request,
			CommonsMultipartFile avatarFile)
				throws UploadAvatarException {
		// ȷ��ͷ�񱣴浽���ļ��е�·������Ŀ��Ŀ¼�µ�upload�ļ���
		String uploadDirPath
			= request.getServletContext()
				.getRealPath("upload");
		// ȷ��ͷ�񱣴浽���ļ���
		File uploadDir = new File(uploadDirPath);
		// ȷ���ļ��д���
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		// ȷ��ͷ���ļ�����չ�������磺aaa.bbb.ccc.jpg���������.jpg
		int beginIndex = avatarFile.getOriginalFilename()
				.lastIndexOf(".");
		String suffix = avatarFile.getOriginalFilename()
				.substring(beginIndex);
		// ȷ��ͷ���ļ����ļ���������Ψһ
		String fileName = UUID.randomUUID().toString() + suffix;
		// ȷ��ͷ�񱣴浽�ĸ��ļ�
		File dest = new File(uploadDir, fileName);
				
		// ����ͷ���ļ�
		try {
			avatarFile.transferTo(dest);
			return "upload/" + fileName;
		} catch (IllegalStateException e) {
			throw new UploadAvatarException(
				"�Ƿ�״̬��");
		} catch (IOException e) {
			throw new UploadAvatarException(
				"��д����");
		}
	}

}










