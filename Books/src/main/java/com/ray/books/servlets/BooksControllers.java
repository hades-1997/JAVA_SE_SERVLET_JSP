package com.ray.books.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ray.books.dbmodels.DBManager;
import com.ray.books.helpers.DBWorldQueries;
import com.ray.books.models.Books;

@WebServlet("/bookscontrollers.do")
@MultipartConfig(
        fileSizeThreshold = 1024*1024*1,    // 1MB
        maxFileSize = 1024 * 1024 * 10,     // 10MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)
public class BooksControllers extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String theCommand = request.getParameter("command");
		
		if(theCommand == null) {
			theCommand = "LIST";
		}
		
		switch(theCommand) {
		
			case"LIST":
				getBooks(request, response);
				break;	
			case"LOAD":
				loadBooks(request, response);
				break;	
			case"VIEW":
				viewBooks(request, response);
				break;
			case"DELETE":
				deleteBooks(request, response);
				break;
			default:
				getBooks(request, response);
				break;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String theCommand = request.getParameter("command");
		
		if(theCommand == null) {
			theCommand = "LIST";
		}
		switch(theCommand) {
			case"ADD":
				addBooks(request, response);
				break;	
			case"UPDATE":
				updateBooks(request, response);
				break;
		}
		
	}
	
	private void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
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

	                String query = DBWorldQueries.listBooks();

	                ArrayList<Books> allBooks = new ArrayList<Books>();

	                ResultSet rs = dbm.ExecuteResultSet(query);

	                while (rs.next())
	                {
	                	
	                	Books b = new Books();

	                	b.setBook_id(rs.getInt("book_id"));
	                	b.setTitle(rs.getString("title"));
	                	b.setSummary(rs.getString("summary"));  
	                	b.setPrice(rs.getFloat("price"));
	                	b.setAuthor(rs.getString("author"));
	                	b.setDate_p(rs.getDate("date_p"));
	                	b.setImages(rs.getString("images"));
	                	b.setCategory_id(rs.getInt("category_id"));
	                	b.setNameCategory(rs.getString(9));
	                   
	                	allBooks.add(b);
	                }
	                s.setAttribute("Books", allBooks);

	            }
	            catch (Exception ex)
	            {
	                throw new IOException("Query could not be executed to get all Books");
	            }
	            response.sendRedirect(getServletContext().getInitParameter("hostURL")
	                    + getServletContext().getContextPath() +"/index.jsp");
	        }
	        else
	        {
	        	response.sendRedirect(getServletContext().getInitParameter("hostURL")
	                    + getServletContext().getContextPath() +"login.jsp");
	        }
	}
	
	private void addBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			
			
			String name = request.getParameter("title");
			String description = request.getParameter("summary");
			String price = request.getParameter("price");
			String author = request.getParameter("author");
		    String date_p = request.getParameter("date_p");  
		    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		    java.sql.Date SQLDate = new java.sql.Date(df.parse(date_p).getTime());
		    
			String category = request.getParameter("category");	
		     
			Part filePart = request.getPart("file");
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); 
			InputStream inputStream =  filePart.getInputStream();
			String uploadPath = getServletContext().getInitParameter("uploadPath");
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
			            uploadDir.mkdir();
			    }
			FileOutputStream outputStream = new FileOutputStream(uploadPath + 
			File.separator + fileName);
			        int read = 0;
			        final byte[] bytes = new byte[1024];
			        while ((read = inputStream.read(bytes)) != -1) {
			            outputStream.write(bytes, 0, read);
			        }
			        
			if(name == null || name.equals("")) {
				 response.sendRedirect(getServletContext().getInitParameter("hostURL")
		                    + getServletContext().getContextPath() + "Category/addBook.jsp");
			}
			
			Books b = new Books();
			b.setTitle(name);
			b.setSummary(description);
			b.setPrice(Float.parseFloat(price));
			b.setAuthor(author);
			b.setDate_p(SQLDate);
			b.setImages(fileName);
			b.setCategory_id(Integer.parseInt(category));
			
			 HttpSession s = request.getSession();
			 
//				
				try {
					
					System.out.println("database test connect ");
					
			        if (getServletConfig().getServletContext().getAttribute("WorldDBManager") != null)
			        {
			            DBManager dbm = (DBManager)getServletConfig().getServletContext().getAttribute("WorldDBManager");

			            try {
			                if (!dbm.isConnected())
			                {
			                    if (!dbm.openConnection())
			                        throw new IOException("Could not connect to database and open connection");
			                }

			                String query = DBWorldQueries.insertBooks(b);
			                dbm.ExecuteNonQuery(query);
			                s.setAttribute("Books", null);
			            }
			            catch (Exception ex)
			            {
			                throw new IOException("Query could not be executed to insert Books");
			            }
			            
			            response.sendRedirect(getServletContext().getInitParameter("hostURL")
			                    + getServletContext().getContextPath() + "/index.jsp");
			        }
			        else
			        {
			        	response.sendRedirect(getServletContext().getInitParameter("hostURL")
			                    + getServletContext().getContextPath() + "/login.jsp");
			        }
			        
					
				} catch (Exception e) {
					response.sendRedirect(getServletContext().getInitParameter("hostURL")
		                    + getServletContext().getContextPath() +  "/errorHandler.jsp");
				}
				
		
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
	private void loadBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession s = request.getSession();
		String bookId = request.getParameter("bookId");
		  
		  
	        if (getServletConfig().getServletContext().getAttribute("WorldDBManager") != null)
	        {
	            DBManager dbm = (DBManager)getServletConfig().getServletContext().getAttribute("WorldDBManager");

	            try {
	                if (!dbm.isConnected())
	                {
	                    if (!dbm.openConnection())
	                        throw new IOException("Could not connect to database and open connection");
	                }

	                String query = DBWorldQueries.loadBooks(bookId);
	                
	                Books books =  new Books();
	                
	                ResultSet rs = dbm.ExecuteResultSet(query);
	                
	                while (rs.next())
	                {
	                	books.setBook_id(rs.getInt("book_id"));
	                	books.setTitle(rs.getString("title"));
	                	books.setAuthor(rs.getString("author"));
	                	books.setPrice(rs.getFloat("price"));	
	                	books.setSummary(rs.getString("summary"));
	                    books.setDate_p(rs.getDate("date_p"));
	                    books.setImages((rs.getString("images") == null || rs.getString("images").equals("")) ? "default.jpg" : rs.getString("images"));
	                    books.setCategory_id(rs.getInt("category_id"));
	                    books.setNameCategory(rs.getString(9));
	                }
	                s.setAttribute("books", books);

	            }
	            catch (Exception ex)
	            {
	                throw new IOException("Query could not be executed to get all Books");
	            }
	            response.sendRedirect(getServletContext().getInitParameter("hostURL")
	                    + getServletContext().getContextPath() +"/updateBook.jsp");
	        }
	        else
	        {
	        	response.sendRedirect(getServletContext().getInitParameter("hostURL")
	                    + getServletContext().getContextPath() +"login.jsp");
	        }
	}
	private void viewBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession s = request.getSession();
		  String bookId = request.getParameter("bookId");
		  
		  
	        if (getServletConfig().getServletContext().getAttribute("WorldDBManager") != null)
	        {
	            DBManager dbm = (DBManager)getServletConfig().getServletContext().getAttribute("WorldDBManager");

	            try {
	                if (!dbm.isConnected())
	                {
	                    if (!dbm.openConnection())
	                        throw new IOException("Could not connect to database and open connection");
	                }

	                String query = DBWorldQueries.loadBooks(bookId);
	                
	                Books books =  new Books();
	                
	                ResultSet rs = dbm.ExecuteResultSet(query);
	                
	                while (rs.next())
	                {
	                	books.setTitle(rs.getString("title"));
	                	books.setAuthor(rs.getString("author"));
	                	books.setPrice(rs.getFloat("price"));	
	                	books.setSummary(rs.getString("summary"));
	                    books.setDate_p(rs.getDate("date_p"));
	                    books.setCategory_id(rs.getInt("category_id"));
	                    books.setNameCategory(rs.getString(9));
	                    books.setImages((rs.getString("images") == null || rs.getString("images").equals("")) ?
                            "default.jpg" : rs.getString("images"));
	                }
	                s.setAttribute("books", books);

	            }
	            catch (Exception ex)
	            {
	                throw new IOException("Query could not be executed to get all Books");
	            }
	            response.sendRedirect(getServletContext().getInitParameter("hostURL")
	                    + getServletContext().getContextPath() +"/viewBooks.jsp");
	        }
	        else
	        {
	        	response.sendRedirect(getServletContext().getInitParameter("hostURL")
	                    + getServletContext().getContextPath() +"login.jsp");
	        }
		
	}
	private void updateBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		try {
			String name = request.getParameter("title");
			String description = request.getParameter("summary");
			String price = request.getParameter("price");
			String author = request.getParameter("author");
		    String date_p = request.getParameter("date_p");  
		    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		    java.sql.Date SQLDate = new java.sql.Date(df.parse(date_p).getTime());
		    
			String category = request.getParameter("category");	
			
			
			if(name == null || name.equals("")) {
				 response.sendRedirect(getServletContext().getInitParameter("hostURL")
		                    + getServletContext().getContextPath() + "/Category/addBook.jsp");
			}
			

	        try {
	        	HttpSession s = request.getSession();
	        	Books tempBooks = (Books) s.getAttribute("books");
	        	
	        	
	            int booksId = Integer.parseInt(request.getParameter("booksId"));

	            Books books =  new Books();
	            
	            books.setTitle(name);
	            books.setSummary(description);
	            books.setPrice(Float.parseFloat(price));
	            books.setAuthor(author);
	            books.setDate_p(SQLDate);
	            books.setImages(tempBooks.getImages());
	            books.setCategory_id(Integer.parseInt(category));
				books.setBook_id(booksId);
	            
				
				
	            if (getServletConfig().getServletContext().getAttribute("WorldDBManager") != null)
	            {
	                DBManager dbm = (DBManager)getServletConfig().getServletContext().getAttribute("WorldDBManager");

	                try {
	                    if (!dbm.isConnected())
	                    {
	                        if (!dbm.openConnection())
	                            throw new IOException("Could not connect to database and open connection");
	                    }

	                    String query = DBWorldQueries.updateBooks(books);
	                    System.out.println(query);
	                    dbm.ExecuteNonQuery(query);
	                }
	                catch (Exception ex)
	                {
	                    throw new IOException("Query could not be executed to update a Books");
	                }
	                s.setAttribute("Books", null);
	                s.setAttribute("books", null);
	                response.sendRedirect(getServletContext().getInitParameter("hostURL") +
	                        getServletContext().getContextPath() + "/index.jsp");
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
		} catch (Exception e) {
			System.out.println("Error" + e.getMessage());
		}
        
	}
	private void deleteBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
            String bookId = request.getParameter("bookId");

            if (getServletConfig().getServletContext().getAttribute("WorldDBManager") != null)
            {
                DBManager dbm = (DBManager)getServletConfig().getServletContext().getAttribute("WorldDBManager");

                try {
                    if (!dbm.isConnected())
                    {
                        if (!dbm.openConnection())
                            throw new IOException("Could not connect to database and open connection");
                    }

                    String query = DBWorldQueries.deleteBooks(bookId);
                   
                    dbm.ExecuteNonQuery(query);
                }
                catch (Exception ex)
                {
                    throw new IOException("Query could not be executed to delete Books");
                }

                HttpSession s = request.getSession();
                s.setAttribute("Books", null);
                response.sendRedirect(getServletContext().getInitParameter("hostURL") +
                        getServletContext().getContextPath() + "/index.jsp");
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
