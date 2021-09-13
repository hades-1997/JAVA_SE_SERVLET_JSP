package com.ray.books.models;

public class Category {
	public int category_id;
	public String name_category;
	
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getName_category() {
		return name_category;
	}
	public void setName_category(String name_category) {
		this.name_category = name_category;
	}
	@Override
	public String toString() {
		return "Category [category_id=" + category_id + ", name=" + name_category + "]";
	}
}
