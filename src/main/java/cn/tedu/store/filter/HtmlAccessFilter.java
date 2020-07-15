package cn.tedu.store.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HtmlAccessFilter implements Filter {
	
	/**
	 * ����ֱ�ӷ��ʵ�html�ļ�
	 */
	private List<String> accessibleHtml;

	public void init(FilterConfig arg0) 
			throws ServletException {
		// ��ʼ������
		System.out.println("HtmlAccessFilter.init()");
		// �������ֱ�ӷ��ʵ�html�ļ�
		accessibleHtml = new ArrayList<String>();
		accessibleHtml.add("register.html");
		accessibleHtml.add("login.html");
		accessibleHtml.add("index.html");
		accessibleHtml.add("goods_details.html");
		System.out.println("����ֱ�ӷ��ʵ�html�ļ���");
		System.out.println(accessibleHtml);
	}

	public void doFilter(ServletRequest arg0, 
			ServletResponse arg1, FilterChain filterChain)
			throws IOException, ServletException {
		// ִ�й��˵ķ���
		System.out.println("HtmlAccessFilter.doFilter()");
		// ��ȡ��ǰ����ʱ��ҳ��
		HttpServletRequest request 
			= (HttpServletRequest) arg0;
		String uri = request.getRequestURI();
		System.out.println("\turi=" + uri);
		String[] pathArray = uri.split("/");
		String file = pathArray[pathArray.length - 1];
		System.out.println("\tfile=" + file);
		
		// �жϵ�ǰҳ���Ƿ�ֱ�ӷ���
		if (accessibleHtml.contains(file)) {
			System.out.println("\t�����С�");
			// ����ִ�й��������к���������������
			filterChain.doFilter(arg0, arg1);
			return;
		}
		
		System.out.println("\t�����ء�");
		// �ж��Ƿ��¼
		HttpSession session = request.getSession();
		if (session.getAttribute("uid") != null) {
			// session����uid�����ѵ�¼�������
			System.out.println("\t�Ѿ���¼�����У�");
			filterChain.doFilter(arg0, arg1);
			return;
		}
		
		// �˴η��ʵ�html���ڰ������У����ң�û�е�¼
		// �ض��򵽵�¼ҳ
		HttpServletResponse response
			= (HttpServletResponse) arg1;
		String loginURI 
			= request.getContextPath() 
				+ "/web/login.html";
		System.out.println(
				"\tδ��¼���ض��򵽣�" + loginURI);
		response.sendRedirect(loginURI);
	}

	public void destroy() {
		// ���ٷ���
		System.out.println("HtmlAccessFilter.destroy()");
	}
	
}
