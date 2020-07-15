package com.udesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.udesk.entity.ResponseResult;
import com.udesk.entity.User;
import com.udesk.service.IUserService;
import com.udesk.service.ex.UploadAvatarException;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="/handle_reg.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleReg(
//		接受来自前端的request的，名为username和password的参数
	    @RequestParam("username") String username,
	    @RequestParam("password") String password,
	    String phone,
	    String email,
//	    value代表前端必须传aa，required 代表所传这个参数是否必传，defaultValue 为这个参数如果没有传的默认值
	    @RequestParam(value="gender", required=false, defaultValue="1") Integer gender) {
		User user = new User(username, password, email, phone, gender);
		userService.register(user);
		return new ResponseResult<Void>();
	}
	
	@RequestMapping(value="/handle_login.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleLogin(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		HttpSession session) {
		User user = userService.login(username, password);
		
		session.setAttribute("uid", user.getId());
		session.setAttribute("username", user.getUsername());
		return new ResponseResult<Void>();
	}

	@RequestMapping(value="/handle_change_password.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleChangePassword(
		@RequestParam("old_password") String oldPassword,
	    @RequestParam("new_password") String newPassword,
	    HttpSession session) {
		Integer uid = getUidFromSession(session);
		userService.changePasswordByOldPassword(uid, oldPassword, newPassword);
		return new ResponseResult<Void>();
	}
	
	@RequestMapping(value="/handle_change_info.do")
	@ResponseBody
	public ResponseResult<String> handleChangeInfo(
		User user, HttpServletRequest request,
		@RequestParam CommonsMultipartFile avatarFile,
	    HttpSession session) {
	    // 判断用户名和邮箱，如果为""，则设为null
		// 也可以用正则表达式来判断
		if ("".equals(user.getUsername())) {
			user.setUsername(null);
		}
		if ("".equals(user.getEmail())) {
			user.setEmail(null);
		}
		
		// 判断此次操作是否上传了头像
		if (!avatarFile.isEmpty()) {
			// 上传头像，并获取上传后的路径
			String avatarPath = uploadAvatar(request, avatarFile);
			// 把头像文件的路径封装，以写入到数据表中
			user.setAvatar(avatarPath);
		}
		
	    // 从session获取uid
		Integer uid = getUidFromSession(session);
	    // 将uid封装到参数user中
		user.setId(uid);
	    // 执行修改：userService.changeInfo(user)
		userService.changeInfo(user);
	    // 返回
		ResponseResult<String> rr= new ResponseResult<String>();
		rr.setData(user.getAvatar());
		return rr;
	}
	
	/**
	 * 上传头像
	 * @param request HttpServletRequest
	 * @param avatarFile CommonsMultipartFile
	 * @return 成功上传后，文件保存到的路径
	 * @throws UploadAvatarException 上传头像异常
	 */
	private String uploadAvatar(HttpServletRequest request,CommonsMultipartFile avatarFile) throws UploadAvatarException {
		// 确定头像保存到的文件夹的路径：项目根目录下的upload文件夹
		String uploadDirPath = request.getServletContext().getRealPath("upload");
		// 确定头像保存到的文件夹
		File uploadDir = new File(uploadDirPath);
		// 确保文件夹存在
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		// 确定头像文件的扩展名，例如：aaa.bbb.ccc.jpg，所需的是.jpg
		int beginIndex = avatarFile.getOriginalFilename().lastIndexOf(".");
		String suffix = avatarFile.getOriginalFilename().substring(beginIndex);
		// 确定头像文件的文件名，必须唯一
		String fileName = UUID.randomUUID().toString() + suffix;
		// 确定头像保存到哪个文件
		File dest = new File(uploadDir, fileName);
				
		// 保存头像文件
		try {
			// 将用户上传的文件数据(尚且在内存中)保存为文件
			avatarFile.transferTo(dest);
			return "upload/" + fileName;
		} catch (IllegalStateException e) {
			throw new UploadAvatarException("非法状态！");
		} catch (IOException e) {
			throw new UploadAvatarException("读写出错！");
		}
	}
	
	@RequestMapping("/getInfo.do")
	@ResponseBody
	public ResponseResult<User> getInfo(
			HttpSession session) {
	    // 从session中获取uid
		Integer uid = getUidFromSession(session);
	    // 调用业务层对象的getUserById()，得到当前用户的User数据
		User user = userService.getUserById(uid);
	    // 创建返回值对象，将User对象封装到Data属性中
		ResponseResult<User> rr
			= new ResponseResult<User>();
		rr.setData(user);
	    // 返回
		return rr;
	}
}
