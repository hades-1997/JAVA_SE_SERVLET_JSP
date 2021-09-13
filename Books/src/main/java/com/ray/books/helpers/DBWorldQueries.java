package com.ray.books.helpers;

import com.ray.books.models.Books;
import com.ray.books.models.Category;

public class DBWorldQueries {
	
	public static String getCategory() {
		return "select * from category order by category_id asc";
	}
	// add category
	public static String insertCategory(Category g) {
		return String.format("INSERT INTO category(name_category) VALUES ('%s')", g.name_category);
	}
	//where id category 
	public static String loadCategory(String categoryId) {
		return "select * from category where category_id="+categoryId;
	}
	//update category
	public static String updateCategory(Category g) {
		return String.format("UPDATE category SET name_category='%s' WHERE category_id = %d", g.name_category,g.category_id);
	}
	//delete category

	public static String deleteCategory(String categoryId) {
		return "delete from category where category_id=" + categoryId ;
	}
	//list books
	public static String listBooks() {
		return "SELECT b.book_id,b.title,b.summary,b.price,b.author,b.date_p,b.images,b.category_id,c.name_category FROM book b INNER JOIN category c on b.category_id = c.category_id order by b.book_id asc";
	}
	// load book SELECT * FROM book b INNER JOIN category c on b.category_id = c.category_id  WHERE book_id=
	public static String loadBooks(String bookId) {
		return "SELECT b.book_id,b.title,b.summary,b.price,b.author,b.date_p,b.images,b.category_id,c.name_category FROM book b INNER JOIN category c on b.category_id = c.category_id  WHERE book_id="+ bookId ;
	}
	// add books 
	public static String insertBooks(Books b) {
		return String.format("INSERT INTO book(title,summary,price,author,date_p,images,category_id) VALUES ('%s','%s',%2f,'%s','%s','%s',%d)", b.title,
				b.summary,
				b.price,
				b.author,
				b.date_p,
				b.images,
				b.category_id);
	}
	//update books 
	public static String updateBooks(Books b) {
		return String.format("UPDATE book SET title='%s',summary='%s',price=%2f,author='%s',date_p='%s',images='%s',category_id= %d WHERE book_id=%d", 
				b.title,
				b.summary,
				b.price,
				b.author,
				b.date_p,
				b.images,
				b.category_id,
				b.book_id);
	}
	// delete books
	public static String deleteBooks(String bookId) {
		return "DELETE FROM book WHERE book_id="+bookId;
	}
	
	
	// login 
	public static String getWebUserByUserNamePassword(String username, String password) {
		return String.format("select * from Author where username = '%s' and password='%s'", username, password);
	}
	
}
