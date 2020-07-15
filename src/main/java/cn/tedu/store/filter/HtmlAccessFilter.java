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
	 * 允许直接访问的html文件
	 */
	private List<String> accessibleHtml;

	public void init(FilterConfig arg0) 
			throws ServletException {
		// 初始化方法
		System.out.println("HtmlAccessFilter.init()");
		// 穷举允许直接访问的html文件
		accessibleHtml = new ArrayList<String>();
		accessibleHtml.add("register.html");
		accessibleHtml.add("login.html");
		accessibleHtml.add("index.html");
		accessibleHtml.add("goods_details.html");
		System.out.println("允许直接访问的html文件：");
		System.out.println(accessibleHtml);
	}

	public void doFilter(ServletRequest arg0, 
			ServletResponse arg1, FilterChain filterChain)
			throws IOException, ServletException {
		// 执行过滤的方法
		System.out.println("HtmlAccessFilter.doFilter()");
		// 获取当前访问时的页面
		HttpServletRequest request 
			= (HttpServletRequest) arg0;
		String uri = request.getRequestURI();
		System.out.println("\turi=" + uri);
		String[] pathArray = uri.split("/");
		String file = pathArray[pathArray.length - 1];
		System.out.println("\tfile=" + file);
		
		// 判断当前页面是否直接放行
		if (accessibleHtml.contains(file)) {
			System.out.println("\t【放行】");
			// 继续执行过滤器链中后续的其它过滤器
			filterChain.doFilter(arg0, arg1);
			return;
		}
		
		System.out.println("\t【拦截】");
		// 判断是否登录
		HttpSession session = request.getSession();
		if (session.getAttribute("uid") != null) {
			// session中有uid，即已登录，则放行
			System.out.println("\t已经登录，放行！");
			filterChain.doFilter(arg0, arg1);
			return;
		}
		
		// 此次访问的html不在白名单中，并且，没有登录
		// 重定向到登录页
		HttpServletResponse response
			= (HttpServletResponse) arg1;
		String loginURI 
			= request.getContextPath() 
				+ "/web/login.html";
		System.out.println(
				"\t未登录，重定向到：" + loginURI);
		response.sendRedirect(loginURI);
	}

	public void destroy() {
		// 销毁方法
		System.out.println("HtmlAccessFilter.destroy()");
	}
	
}
