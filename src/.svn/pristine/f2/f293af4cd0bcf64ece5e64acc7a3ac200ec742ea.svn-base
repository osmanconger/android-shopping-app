package com.b07.store;

import android.content.Context;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.exceptions.BadInputException;
import com.b07.inventory.Item;
import com.b07.users.Customer;

public class ShoppingCart {
	private HashMap<Item, Integer> items;
	private Customer customer;
	private Context context;
	private BigDecimal total = new BigDecimal("0");
	private static final BigDecimal TAXRATE = new BigDecimal("1.13");
	// selecthelper
	private DatabaseSelectHelper dbSelect;

	public ShoppingCart(Context context, Customer customer) throws BadInputException, SQLException {
		int userId = customer.getId();
		DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(context);
		if (dbSelect.getUserDetailsHelper(userId) == null) {
			throw new BadInputException();
		} else {
			this.customer = customer;
			dbSelect.close();
		}
		this.context = context;
	}

	public void addItem(Item item, int quantity) {
		BigDecimal price;
		if (!items.containsKey(item)) {
			items.put(item, quantity);
		} else {
			items.replace(item, items.get(item) + quantity);
		}
		price = item.getPrice();
		for (int i = 0; i < quantity; i++) {
			total = total.add(price);
		}
	}

	public int getQuantity(Item item) {
		return this.items.get(item);
	}

	public void removeItem(Item item, int quantity) {
		int remaining = items.get(item) - quantity;
		BigDecimal price = item.getPrice();
		if (remaining <= 0) {
			items.remove(item);
		} else {
			items.replace(item, items.get(item) - quantity);
		}
		for (int i = 0; i < quantity; i++) {
			total = total.subtract(price);
		}
	}

	public List<Item> getItems() {
		List<Item> allItems = new ArrayList<>();
		for (Item key : items.keySet()) {
			allItems.add(key);
		}
		return allItems;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public BigDecimal getTaxRate() {
		return TAXRATE;
	}

	public boolean checkOut() throws SQLException {
		int userId = customer.getId();
		BigDecimal totalWTax;
		DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(context);
		if (dbSelect.getUserDetailsHelper(userId) != null) {
			totalWTax = total.multiply(TAXRATE);
			return true;
		}
		return false;
	}

	public void clearCart() {
		this.items = new HashMap<Item, Integer>();
	}

}
