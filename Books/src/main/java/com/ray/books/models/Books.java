package com.ray.books.models;

import java.sql.Date;

public class Books {
	public int book_id;
	public String title;
	public String summary;
	public float price;
	public String author;
	public Date date_p;
	public String images;
	public int category_id;
	public String nameCategory;
	
	
	public String getNameCategory() {
		return nameCategory;
	}
	public void setNameCategory(String nameCategory) {
		this.nameCategory = nameCategory;
	}
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getDate_p() {
		return date_p;
	}
	public void setDate_p(Date date_p) {
		this.date_p = date_p;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	
	@Override
	public String toString() {
		return "Books [book_id=" + book_id + ", title=" + title + ", summary=" + summary + ", price=" + price
				+ ", author=" + author + ", date_p=" + date_p + ", images=" + images + ", category_id=" + category_id
				+ "]";
	}
	
	

}
