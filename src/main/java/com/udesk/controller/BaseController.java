package com.udesk.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udesk.entity.ResponseResult;
import com.udesk.service.ex.AddressNotFoundException;
import com.udesk.service.ex.ArgumentException;
import com.udesk.service.ex.InsertDataException;
import com.udesk.service.ex.PasswordNotMatchException;
import com.udesk.service.ex.ServiceException;
import com.udesk.service.ex.UpdateDataException;
import com.udesk.service.ex.UploadAvatarException;
import com.udesk.service.ex.UserNotFoundException;
import com.udesk.service.ex.UsernameConflictException;

public class BaseController {

	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public ResponseResult<Void> handleException(Exception e) {
		if (e instanceof UsernameConflictException) {
			return new ResponseResult<Void>(401, e);
		} else if (e instanceof UserNotFoundException) {
			return new ResponseResult<Void>(402, e);
		} else if (e instanceof PasswordNotMatchException) {
			return new ResponseResult<Void>(403, e);
		} else if (e instanceof UploadAvatarException) {
			return new ResponseResult<Void>(408, e);
		} else if (e instanceof AddressNotFoundException) {
			return new ResponseResult<Void>(409, e);
		} else if (e instanceof ArgumentException) {
			return new ResponseResult<Void>(410, e);
		} else if (e instanceof InsertDataException) {
			return new ResponseResult<Void>(501, e);
		} else if (e instanceof UpdateDataException) {
			return new ResponseResult<Void>(502, e);
		} else {
			return new ResponseResult<Void>(600, e);
		}
	}
	
	/**
	 * 从Session中获取当前登录的用户的id
	 * @param session
	 * @return 当前登录的用户的id
	 */
	protected final Integer getUidFromSession(HttpSession session) {
		return Integer.valueOf(	session.getAttribute("uid").toString());
	}

}
