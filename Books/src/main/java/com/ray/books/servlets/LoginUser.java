package com.ray.books.servlets;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ray.books.dbmodels.DBManager;
import com.ray.books.helpers.DBWorldQueries;
import com.ray.books.models.User;

@WebServlet("/loginUser.do")
public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession s = request.getSession();
		User u = (User) s.getAttribute("authorized_user");

        if (u == null || u.getUsername().equals("") || u.getUsername() == null || u.getDecentral_id() < 1) {
            String username = "";
            String password = "";

            if (request.getParameter("username") != null)
            	username = request.getParameter("username");

            if (request.getParameter("password") != null)
            	password = request.getParameter("password");

            if ((u == null || u.getUsername().equals("")  || u.getDecentral_id() < 1 || u.getUsername() == null)
                    && (username != "" && password != "")) {

                if (getServletConfig().getServletContext().getAttribute("WorldDBManager") != null)
                {
                    DBManager dbm = (DBManager)getServletConfig().getServletContext().getAttribute("WorldDBManager");

                    try {
                        if (!dbm.isConnected())
                        {
                            if (!dbm.openConnection())
                                throw new IOException("Could not connect to database and open connection");
                        }

                        String query = DBWorldQueries.getWebUserByUserNamePassword(username, password);
                        ResultSet rs = dbm.ExecuteResultSet(query);

                        while (rs.next())
                        {
                            u = new User();

                            u.setUsername(rs.getString("username"));
                            u.setPassword(rs.getString("password"));
                            u.setDecentral_id(rs.getInt("decentral_id"));

                            s.setAttribute("authorized_user", u);

                            if (request.getParameter("rememberMe") != null)
                            {
                                String rememberMe =  request.getParameter("rememberMe");
                                if (rememberMe.equalsIgnoreCase("ON"))
                                {
                                    int CookieLife = 3600*24*7;

                                    Cookie uidCookie = new Cookie("credentials_uid", username);
                                    Cookie pwdCookie = new Cookie("credentials_pwd", password);

                                    uidCookie.setMaxAge(CookieLife);
                                    pwdCookie.setMaxAge(CookieLife);

                                    response.addCookie(uidCookie);
                                    response.addCookie(pwdCookie);
                                }
                            }
                        }

                    }
                    catch (Exception ex)
                    {
                        System.out.println("Exception: " + ex.getMessage());
                        response.sendRedirect(getServletContext().getContextPath() +"/loginError.jsp");
                        return;
                    }
                }
                else
                {
                    response.sendRedirect(getServletContext().getContextPath() + "/login.jsp");
                }
            }

            if (u == null || u.getUsername().equals("") || u.getUsername() == null || u.getDecentral_id() < 1)
            {
                response.sendRedirect(getServletContext().getContextPath() +"/loginError.jsp");
                return;
            }

        }

        String target = (request.getParameter("dest")==null || request.getParameter("dest")=="")
                ? "index.jsp"
                : request.getParameter("dest") + ".jsp";

        response.sendRedirect(target);
	}

}
