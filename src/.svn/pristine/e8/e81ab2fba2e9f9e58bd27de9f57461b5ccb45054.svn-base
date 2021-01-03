package com.b07.store;

import java.math.BigDecimal;
import java.util.HashMap;
import com.b07.inventory.Item;
import com.b07.users.User;

public class SaleImpl implements Sale {
  private int saleId;
  private User user;
  private Item item;
  private int quantity;
  private BigDecimal totalPrice;
  private HashMap<Item, Integer> sales;
  // map of what item and quantity for each

  public SaleImpl(int saleId, Item item, int quantity) {
    this.saleId = saleId;
    this.item = item;
    this.quantity = quantity;
    sales.put(item, quantity);
  }

  public SaleImpl(int saleId, User user, BigDecimal totalPrice) {
    this.saleId = saleId;
    this.user = user;
    this.totalPrice = totalPrice;
  }

  @Override
  public int getId() {
    // TODO Auto-generated method stub
    return this.saleId;
  }

  @Override
  public void setId(int id) {
    // TODO Auto-generated method stub
    this.saleId = id;
  }

  @Override
  public User getUser() {
    // TODO Auto-generated method stub
    return this.user;
  }

  @Override
  public void setUser(User user) {
    // TODO Auto-generated method stub
    this.user = user;
  }
  
  public Item getItem() {
    // TODO Auto-generated method stub
    return this.item;
  }

  public void setItem(Item item) {
    // TODO Auto-generated method stub
    this.item = item;
  }
  
  public int getQuantity() {
	// TODO Auto-generated method stub
	return this.quantity;
  }
  
  public void setQuantity(int quantity) {
	// TODO Auto-generated method stub
	this.quantity = quantity;
  }
  
  @Override
  public BigDecimal getTotalPrice() {
    // TODO Auto-generated method stub
    return this.totalPrice;
  }

  @Override
  public void setTotalPrice(BigDecimal price) {
    // TODO Auto-generated method stub
    this.totalPrice = price;
  }

  @Override
  public HashMap<Item, Integer> getItemMap() {
    // TODO Auto-generated method stub
    return this.sales;
  }

  @Override
  public void setItemMap(HashMap<Item, Integer> itemMap) {
    // TODO Auto-generated method stub
    this.sales = itemMap;
  }

}
