package com.kentec.milkbox.homedine.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MealsList {

	private HashMap<String, HashMap<String, MealsFood>> mealsList;

	public MealsList() {
		mealsList = new HashMap<String, HashMap<String, MealsFood>>();
	}

	public void addFood(String store, FoodItem foodItem, long quantity) {
		MealsFood tmpFood = new MealsFood();
		tmpFood.setFoodItem(foodItem);
		tmpFood.setFoodQuantity(quantity);

		HashMap<String, MealsFood> tmpMap = mealsList.get(store);
		if (tmpMap == null) {
			tmpMap = new HashMap<String, MealsFood>();
		}
		tmpMap.put(foodItem.getId(), tmpFood);

		mealsList.put(store, tmpMap);
	}

	public void delFood(String store, String foodId) {
		HashMap<String, MealsFood> tmpMap = mealsList.get(store);
		if (tmpMap == null) {
			tmpMap = new HashMap<String, MealsFood>();
		}
		tmpMap.remove(foodId);

		mealsList.put(store, tmpMap);
	}

	public void clearStoreFood(String store) {
		mealsList.remove(store);
	}

	public long getQuantity(String store, String foodId) {
		if (mealsList.get(store) != null) {
			if (mealsList.get(store).get(foodId) != null) {
				return mealsList.get(store).get(foodId).getFoodQuantity();
			}
		}
		return 0;
	}

	public double getPrice(String store, String foodId) {
		if (mealsList.get(store) != null) {
			if (mealsList.get(store).get(foodId) != null) {
				return mealsList.get(store).get(foodId).getFoodItem().getPrice();
			}
		}
		return 0;
	}

	public int getStoreMealsCount(String storeId) {
		int count = 0;
		Iterator<?> iter = mealsList.get(storeId).entrySet().iterator();
		while (iter.hasNext()) {
			@SuppressWarnings("unused")
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iter.next();
			count++;
		}
		return count;
	}

	public MealsFood getMealsFood(String storeId, String foodId) {
		return mealsList.get(storeId).get(foodId);
	}

	public ArrayList<MealsFood> getStoreMealsList(String nowStoreId) {
		ArrayList<MealsFood> list = new ArrayList<MealsFood>();

		HashMap<String, MealsFood> tmp = mealsList.get(nowStoreId);
		if (tmp != null) {
			Iterator<?> iter = tmp.entrySet().iterator();
			while (iter.hasNext()) {
				MealsFood tmpFood = (MealsFood) ((Entry<?, ?>) iter.next()).getValue();
				if (tmpFood.getFoodQuantity() > 0) {
					list.add(tmpFood);
				}
			}
		}

		return list;
	}

}