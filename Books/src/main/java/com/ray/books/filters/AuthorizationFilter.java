package com.ray.books.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ray.books.models.User;


@WebFilter("/Category/*")
public class AuthorizationFilter implements Filter {

    public AuthorizationFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
	HttpSession s = req.getSession();
	
	String dest = req.getServletContext().getContextPath()+"/login.jsp";
	
	if(req.getRequestURI().toLowerCase().contains("city")) {
		dest = dest+ "?dest=Protected/ListCities.jsp";
	}else if(req.getRequestURI().toLowerCase().contains("countries")) {
		dest = dest+ "?dest=Protected/ListCountries.jsp";
	}
		if(s.getAttribute("authorized_user") == null) {
			res.sendRedirect(dest);
			return;
		}else {
			User wu = (User) s.getAttribute("authorized_user");
			
			if( wu.getUsername().equals("") || wu.getUsername() == null || wu.getPassword() == null || wu.getPassword().equals("")) {
				res.sendRedirect(dest);
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
