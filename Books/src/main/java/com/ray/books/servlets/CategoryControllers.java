package com.ray.books.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ray.books.dbmodels.DBManager;
import com.ray.books.helpers.DBWorldQueries;
import com.ray.books.models.Category;
import com.ray.books.models.User;


@WebServlet("/categorycontrollers.do")
public class CategoryControllers extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CategoryControllers() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String theCommand = request.getParameter("command");
		
		if(theCommand == null) {
			theCommand = "LIST";
		}
		
		switch(theCommand) {
			case"LIST":
				GetCategory(request, response);
				break;
			case"LOAD":
				loadCategory(request, response);
				break;
			case"DELETE":
				deleteCategory(request, response);
				break;
			default:
				GetCategory(request, response);
				break;
		}
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String theCommand = request.getParameter("command");
		
		if(theCommand == null) {
			theCommand = "LIST";
		}
		
		switch(theCommand) {
			case"ADD":
				AddCategory(request, response);
				break;
			case"UPDATE":
				updateCategory(request, response);
				break;
				
		}  
		
	}

	private void GetCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession s = request.getSession();

	        if (getServletConfig().getServletContext().getAttribute("WorldDBManager") != null)
	        {
	            DBManager dbm = (DBManager)getServletConfig().getServletContext().getAttribute("WorldDBManager");

	            try {
	                if (!dbm.isConnected())
	                {
	                    if (!dbm.openConnection())
	                        throw new IOException("Could not connect to database and open connection");
	                }

	                String query = DBWorldQueries.getCategory();

	                ArrayList<Category> allCategory = new ArrayList<Category>();

	                ResultSet rs = dbm.ExecuteResultSet(query);

	                while (rs.next())
	                {
	                	Category c = new Category();

	                	c.setCategory_id(rs.getInt("category_id"));
	                	c.setName_category(rs.getString("name_category"));    
	                   
	                    allCategory.add(c);
	                }
	                s.setAttribute("Category", allCategory);

	            }
	            catch (Exception ex)
	            {
	                throw new IOException("Query could not be executed to get all Category");
	            }
	            response.sendRedirect(getServletContext().getInitParameter("hostURL")
	                    + getServletContext().getContextPath() +"/Category/listCategory.jsp");
	        }
	        else
	        {
	        	response.sendRedirect(getServletContext().getInitParameter("hostURL")
	                    + getServletContext().getContextPath() +"login.jsp");
	        }
	}
	
	private void AddCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String name = request.getParameter("title");
		
		if(name == null || name.equals("")) {
			 response.sendRedirect(getServletContext().getInitParameter("hostURL")
	                    + getServletContext().getContextPath() + "/Category/addCategory.jsp");
		}
		
		Category g = new Category();
		g.setName_category(name);
		
		 HttpSession s = request.getSession();
		if (s.getAttribute("authorized_user") == null) {
            response.sendRedirect(getServletContext().getInitParameter("hostURL")
                    + getServletContext().getContextPath() + "/login.jsp");
            return;
        } else {
            User us =  (User) s.getAttribute("authorized_user");
           if( 0 < us.getDecentral_id() ) {
                response.sendRedirect(getServletContext().getInitParameter("hostURL")
                        + getServletContext().getContextPath() + "/login.jsp");
                return;
            }else if( us.getDecentral_id() > 1) {
            	 response.sendRedirect(getServletContext().getInitParameter("hostURL")
                         + getServletContext().getContextPath() + "Category/listCategory.jsp");
            }
        }
		
		try {
	        if (getServletConfig().getServletContext().getAttribute("WorldDBManager") != null)
	        {
	            DBManager dbm = (DBManager)getServletConfig().getServletContext().getAttribute("WorldDBManager");

	            try {
	                if (!dbm.isConnected())
	                {
	                    if (!dbm.openConnection())
	                        throw new IOException("Could not connect to database and open connection");
	                }

	                String query = DBWorldQueries.insertCategory(g);

	                dbm.ExecuteNonQuery(query);
	                s.setAttribute("Category", null);
	                
	            }
	            catch (Exception ex)
	            {
	                throw new IOException("Query could not be executed to get all Category");
	            }
	            response.sendRedirect(getServletContext().getContextPath() +("/Category/listCategory.jsp"));
	        }
	        else
	        {
	        	 response.sendRedirect(getServletContext().getContextPath() + "login.jsp");
	        }
			
		} catch (Exception e) {
			 response.sendRedirect(getServletContext().getContextPath() + "/errorHandler.jsp");
		}
	}
	private void loadCategory (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession s = request.getSession();
		  String categoryId = request.getParameter("categoryId");
		  
		  System.out.println(categoryId);
		  
		  
	        if (getServletConfig().getServletContext().getAttribute("WorldDBManager") != null)
	        {
	            DBManager dbm = (DBManager)getServletConfig().getServletContext().getAttribute("WorldDBManager");

	            try {
	                if (!dbm.isConnected())
	                {
	                    if (!dbm.openConnection())
	                        throw new IOException("Could not connect to database and open connection");
	                }

	                String query = DBWorldQueries.loadCategory(categoryId);
	                Category category =  new Category();
	                ResultSet rs = dbm.ExecuteResultSet(query);
	                while (rs.next())
	                {
	                	category.setCategory_id(rs.getInt("category_id"));
	                	category.setName_category(rs.getString("name_category"));
	                	
	                }
	                s.setAttribute("category", category);

	            }
	            catch (Exception ex)
	            {
	                throw new IOException("Query could not be executed to get all Category");
	            }
	            response.sendRedirect(getServletContext().getInitParameter("hostURL")
	                    + getServletContext().getContextPath() +"/Category/updateCategory.jsp");
	        }
	        else
	        {
	        	response.sendRedirect(getServletContext().getInitParameter("hostURL")
	                    + getServletContext().getContextPath() +"login.jsp");
	        }
	}
	private void updateCategory (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("title");
		
		if(name == null || name.equals("")) {
			 response.sendRedirect(getServletContext().getInitParameter("hostURL")
	                    + getServletContext().getContextPath() + "/Category/addCategory.jsp");
		}
		

        try {
            int category_id = Integer.parseInt(request.getParameter("categoryId"));

            Category category =  new Category();
            category.setCategory_id(category_id);
            category.setName_category(name);
            
            if (getServletConfig().getServletContext().getAttribute("WorldDBManager") != null)
            {
                DBManager dbm = (DBManager)getServletConfig().getServletContext().getAttribute("WorldDBManager");

                try {
                    if (!dbm.isConnected())
                    {
                        if (!dbm.openConnection())
                            throw new IOException("Could not connect to database and open connection");
                    }

                    String query = DBWorldQueries.updateCategory(category);

                    dbm.ExecuteNonQuery(query);
                }
                catch (Exception ex)
                {
                    throw new IOException("Query could not be executed to update a Category");
                }

                HttpSession s = request.getSession();
                s.setAttribute("Category", null);

                response.sendRedirect(getServletContext().getInitParameter("hostURL") +
                        getServletContext().getContextPath() + "/Category/listCategory.jsp");
            }
            else
            {
                response.sendRedirect(getServletContext().getInitParameter("hostURL")
                        + getServletContext().getContextPath() + "login.jsp");
            }
        } catch (Exception ex)
        {
            response.sendRedirect(getServletContext().getInitParameter("hostURL")
                    + getServletContext().getContextPath() + "/errorHandler.jsp");
        }
	}
	private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
            String categoryId = request.getParameter("categoryId");

            if (getServletConfig().getServletContext().getAttribute("WorldDBManager") != null)
            {
                DBManager dbm = (DBManager)getServletConfig().getServletContext().getAttribute("WorldDBManager");

                try {
                    if (!dbm.isConnected())
                    {
                        if (!dbm.openConnection())
                            throw new IOException("Could not connect to database and open connection");
                    }

                    String query = DBWorldQueries.deleteCategory(categoryId);

                    dbm.ExecuteNonQuery(query);
                }
                catch (Exception ex)
                {
                    throw new IOException("Query could not be executed to insert a new city");
                }

                HttpSession s = request.getSession();
                s.setAttribute("Category", null);

                response.sendRedirect(getServletContext().getInitParameter("hostURL") +
                        getServletContext().getContextPath() + "/Category/listCategory.jsp");
            }
            else
            {
                response.sendRedirect(getServletContext().getInitParameter("hostURL")
                        + getServletContext().getContextPath() + "login.jsp");
            }
        } catch (Exception ex)
        {
            response.sendRedirect(getServletContext().getInitParameter("hostURL")
                    + getServletContext().getContextPath() + "/errorHandler.jsp");
        }
	}
}
