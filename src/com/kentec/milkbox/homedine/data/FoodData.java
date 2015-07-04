package com.kentec.milkbox.homedine.data;

import java.util.ArrayList;

public class FoodData {
	
	private int foodCount;
	private int foodPageCount;
	private ArrayList<FoodItem> foodList;

	public int getFoodCount() {
		return foodCount;
	}

	public void setFoodCount(int foodCount) {
		this.foodCount = foodCount;
	}

	public int getFoodPageCount() {
		return foodPageCount;
	}

	public void setFoodPageCount(int foodPageCount) {
		this.foodPageCount = foodPageCount;
	}

	public ArrayList<FoodItem> getFoodList() {
		return foodList;
	}

	public void setFoodList(ArrayList<FoodItem> foodList) {
		this.foodList = foodList;
	}
	
	public void addFoodList(ArrayList<FoodItem> foodList) {
		this.foodList.addAll(foodList);
	}

}
