package com.kentec.milkbox.homedine.data;

public class FoodItem {

	private String Id;
	private String SKU;
	private String Pic;
	private String Name;
	private String Thumbnail;
	private double Quantity;
	private double RemainingQuantity;
	private double Price;
	private String About;
	private float Calorie;
	private int Protein;
	private int Carbohydrates;
	private int Sugar;
	private int Fat;
	private float SaturatedFat;
	private int TrandFat;
	private int Cholesterol;
	private int Sodlum;

	public String getId() {
		return Id;
	}

	public String getSKU() {
		return SKU;
	}

	public void setSKU(String sKU) {
		SKU = sKU;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getPic() {
		return Pic;
	}

	public void setPic(String pic) {
		Pic = pic;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double price) {
		Price = price;
	}

	public String getAbout() {
		return About;
	}

	public void setAbout(String about) {
		About = about;
	}

	public float getCalorie() {
		return Calorie;
	}

	public void setCalorie(float calorie) {
		Calorie = calorie;
	}

	public int getProtein() {
		return Protein;
	}

	public void setProtein(int protein) {
		Protein = protein;
	}

	public int getCarbohydrates() {
		return Carbohydrates;
	}

	public void setCarbohydrates(int carbohydrates) {
		Carbohydrates = carbohydrates;
	}

	public int getSugar() {
		return Sugar;
	}

	public void setSugar(int sugar) {
		Sugar = sugar;
	}

	public int getFat() {
		return Fat;
	}

	public void setFat(int fat) {
		Fat = fat;
	}

	public float getSaturatedFat() {
		return SaturatedFat;
	}

	public void setSaturatedFat(float saturatedFat) {
		SaturatedFat = saturatedFat;
	}

	public int getTrandFat() {
		return TrandFat;
	}

	public void setTrandFat(int trandFat) {
		TrandFat = trandFat;
	}

	public int getCholesterol() {
		return Cholesterol;
	}

	public void setCholesterol(int cholesterol) {
		Cholesterol = cholesterol;
	}

	public int getSodlum() {
		return Sodlum;
	}

	public void setSodlum(int sodlum) {
		Sodlum = sodlum;
	}

	public String getThumbnail() {
		return Thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		Thumbnail = thumbnail;
	}

	public double getRemainingQuantity() {
		return RemainingQuantity;
	}

	public void setRemainingQuantity(double remainingQuantity) {
		RemainingQuantity = remainingQuantity;
	}

	public double getQuantity() {
		return Quantity;
	}

	public void setQuantity(double quantity) {
		Quantity = quantity;
	}

}