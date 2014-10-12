package de.inovex.javamagazin.jpa;

import java.util.List;

import de.inovex.javamagazin.domain.InventoryCategory;
import de.inovex.javamagazin.domain.InventoryItem;

public interface InventoryRepository {

	// Item methods
	List<InventoryItem> getAllItems();

	InventoryItem getSingleItem(int id);

	void addItem(String name, String description, float price,
			int categoryID);

	void updateItem(int id, String name, String description,
			float price, int categoryID);

	void deleteItem(int id);

	//Category Methods    
	List<InventoryCategory> getAllCategories();

	InventoryCategory getSingleCategory(int id);

	void addCategory(String name, String description);

	void updateCategory(int id, String name, String description);

	void deleteCategory(int id);

	void deleteCategoryByName(String name);

	InventoryCategory getCategoryByName(String name);

	List<InventoryItem> getAllItemsByCategory(String categoryName);

	void deleteItemByName(String name);

	InventoryItem getInventoryItemByName(String name);

}