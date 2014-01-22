package com.myschool.game.model;

public class Product {
	private int storeId;
	private int id;
	private String name;
	private String category;
	private String subCategory;
	private int count;
	private int price;
	public Product() {
	}

	public Product(Shop shop, String name, String category, String subCategory,
			int price, int count) {
		this.storeId = shop.getId();
		this.name = name;
		this.category = category;
		this.subCategory = subCategory;
		this.price = price;
		this.count = count;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getShopId() {
		return storeId;
	}
	public void setShopId(int storeId) {
		this.storeId = storeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String string) {
		this.category = string;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

}
