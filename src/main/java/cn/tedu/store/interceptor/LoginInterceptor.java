package cn.tedu.store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor 
	implements HandlerInterceptor {
	
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {
		System.out.println("LoginInterceptor.preHandle()");
		// ��ȡSession
		HttpSession session
			= request.getSession();
		// �ж�session���Ƿ��е�¼��Ϣ
		if (session.getAttribute("username") == null) {
			// û�е�¼��Ϣ�����ض��򵽵�¼ҳ
			response.sendRedirect("../web/login.html");
			// ִ������
			return false;
		} else {
			// �е�¼��Ϣ���������������ʣ�ֱ�ӷ���
			return true;
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("LoginInterceptor.postHandle()");
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("LoginInterceptor.afterCompletion()");
	}

}
