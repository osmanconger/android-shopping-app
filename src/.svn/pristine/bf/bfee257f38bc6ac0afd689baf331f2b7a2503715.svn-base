package com.b07.inventory;

import java.util.HashMap;

public class InventoryImpl implements Inventory {

	HashMap<Item, Integer> inventory = new HashMap<Item, Integer>();
	int totalItems;
    Item itemType;
    int quantity;
    static InventoryImpl instance = new InventoryImpl();

	public InventoryImpl(Item itemType, int quantity) {
	  this.itemType = itemType;
	  this.quantity = quantity;
	}

	public InventoryImpl() {
	}
	public static InventoryImpl getInstance() {
		 return instance;
		  }

	@Override
	public HashMap<Item, Integer> getItemMap() {
		return this.inventory;
	}

	@Override
	public void setItemMap(HashMap<Item, Integer> itemMap) {
		inventory = itemMap;
	}
  
  public Item getItem() {
	  return this.itemType;
  }
  
  public void setItem(Item itemType) {
	  this.itemType = itemType;
  }
  public int getQuantity(Item item) {
	  return getItemMap().get(item);
	  
  }

	@Override
	public void updateMap(Item item, Integer value) {
		try {
			inventory.replace(item, value);
		} catch (Exception BadInputException) {
		}
	}

	@Override
	public int getTotalItems() {
		return this.totalItems;
	}

	@Override
	public void setTotalItems(int total) {
		this.totalItems = total;
	}

}
