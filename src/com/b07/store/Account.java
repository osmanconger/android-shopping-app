package com.b07.store;

import android.content.Context;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.exceptions.BadInputException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.inventory.Item;
import com.b07.users.Customer;
import com.b07.store.ShoppingCart;
import java.sql.SQLException;
import java.util.List;



public class Account {
	
	//id for account
	private int accountId;
	//check status of account if active or inactive (active is 1, inactive is 0)
	private int active;
	//cart for storing items
	private ShoppingCart shoppingCart;
	//context
	private Context context;
	//inserthelper
	private DatabaseInsertHelper dbInsert;
	
	public Account(Context context, Customer customer, int accountId, int active) throws BadInputException, SQLException {
		this.shoppingCart = new ShoppingCart(context, customer);
		this.active=active;
		this.accountId=accountId;
		this.context = context;
		dbInsert = new DatabaseInsertHelper(context);
	}
	
	//addding an item to cart
	public void addItem(Item item, int quantity) {
		this.shoppingCart.addItem(item, quantity);
		
	}
	
	//removing an item to cart
	public void removeItem(Item item, int quantity) {
		this.shoppingCart.removeItem(item, quantity);
		
	}
	
	public void setActive(int active) {
		this.active = active;
	}
	
	public int getActive() {
		return this.active;
	}
	
	public void clearcart(Customer customer) {
	}
	
	public ShoppingCart getShoppingCart() {
		return this.shoppingCart;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	/**
	 * if a user is active return their cart
	 * 
	 * @throws SQLException
	 * @throws DatabaseInsertException
	 * @throws BadInputException
	 */
	public void obtainOldCart() throws SQLException, DatabaseInsertException, BadInputException {
		//check account status
		DatabaseInsertHelper dbInsert = new DatabaseInsertHelper(context);
		if (this.active == 1){
			List<Item> items = this.shoppingCart.getItems();
			for(Item item:items) {
				dbInsert.insertAccountLineHelper(this.accountId, item.getId(), this.shoppingCart.getQuantity(item));
			dbInsert.close();
			}
		}
	}

}
