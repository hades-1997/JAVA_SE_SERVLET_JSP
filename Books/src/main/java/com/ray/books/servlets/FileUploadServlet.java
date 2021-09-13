package com.ray.books.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ray.books.models.Books;

@WebServlet("/fileuploadservlet")
@MultipartConfig(
        fileSizeThreshold = 1024*1024*1,    // 1MB
        maxFileSize = 1024 * 1024 * 10,     // 10MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		try {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// Configure a repository (to ensure a secure temp location is used)
			ServletContext servletContext = this.getServletConfig().getServletContext();
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			factory.setRepository(repository);
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			// Parse the request
			List<FileItem> items = upload.parseRequest(request);
			// Process the uploaded items
			Iterator<FileItem> iter = items.iterator();
			String file = null;
			
			while (iter.hasNext()) {
			    FileItem item = iter.next();
			    HashMap<String, String> fields = new HashMap<>();
			    if (item.isFormField()) {
			    	fields.put(item.getFieldName(),item.getString());
			    	  String name = item.getFieldName();
			    	 String value = item.getString();
			    } else {
			    	
			    	 file = item.getName();
			    	Path filePart = Paths.get(file);	
			    	//
			    	//String storePath = servletContext.getRealPath("/uploads");
					String storePath = getServletContext().getInitParameter("uploadPath");
					File uploadDir = new File(storePath);
					if (!uploadDir.exists()) {
					            uploadDir.mkdir();
					    }
			    	File uploadFile = new File(storePath + "/" + filePart.getFileName());
			    	item.write(uploadFile);
			    }
			}
			HttpSession s = request.getSession();
			Books tempBooks = (Books) s.getAttribute("books");
	        tempBooks.setImages(file);
	        s.setAttribute("Books", tempBooks);
	        response.sendRedirect(getServletContext().getInitParameter("hostURL")
	                + getServletContext().getContextPath() +"/updateBook.jsp");
	        
		} catch (Exception e) {
			// TODO: handle exception
		}	
	}
}
