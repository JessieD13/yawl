package demo;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class SigninFilter extends HttpServlet implements Filter {


    public void init(FilterConfig filterConfig) throws ServletException { }

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {

    	HttpServletRequest request = (HttpServletRequest) arg0;

    	HttpServletResponse response   = (HttpServletResponse) arg1;

    	HttpSession session = request.getSession();
    	
    	String userid = (String) session.getAttribute("userid");
    	String userhandle = (String) session.getAttribute("userhandle");
    	
    	String url = request.getRequestURI();
    	
    	if(userid==null || userid.equals("") || userhandle==null || userhandle.equals("")) {
    		//判断获取的路径不为空且不是访问登录页面或执行登录操作时跳转
    		
    		if(url != null && !url.equals("") && (url.indexOf("signin")<0 && url.indexOf("signin")<0)) {
    			response.sendRedirect(request.getContextPath() + "/signin.jsp");
    			return;
    		}
    		
    		arg2.doFilter(arg0, arg1);
    		return;
    	}

    }

    public void destroy() { }

}