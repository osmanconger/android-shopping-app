package com.b07.database.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;

import com.b07.database.DatabaseDriverAndroid;
import com.b07.exceptions.BadInputException;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.inventory.Inventory;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.Item;
import com.b07.store.Sale;
import com.b07.store.SaleImpl;
import com.b07.store.SalesLog;
import com.b07.users.User;

public class Deserializing {
	
	@SuppressWarnings("unchecked")
	public static void loadDatabase(Context context) throws DatabaseInsertException, java.sql.SQLException, BadInputException, ConnectionFailedException {
		
		DatabaseInsertHelper dbInsert = new DatabaseInsertHelper(context);
		DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(context);
		ArrayList<Object> data = new ArrayList<Object>();
		PasswordInserter pswInsert = new PasswordInserter(context);
		
		try {
			FileInputStream fileIn = new FileInputStream("database_copy.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			data = (ArrayList<Object>) in.readObject();
			in.close();
			fileIn.close();
			
		} catch(IOException i) {
			i.printStackTrace();
		} catch(ClassNotFoundException c) {
			c.printStackTrace();
		}
		
		//need to clear the database  
		
		List<User> users = (List<User>) data.get(0); // insertNewUser
		List<Item> items = (List<Item>) data.get(1); //insertItem
		Inventory inventory = (Inventory) data.get(2); // insertInventory
		SalesLog reg_sales = (SalesLog) data.get(3); // insertSale
		SalesLog itemized_sales = (SalesLog) data.get(4); // insertItemizedSale
		ArrayList<String> passwords = (ArrayList<String>) data.get(5); // insertPassword

		int i = 0;
		int user_id;
		for (User user: users) {
			user_id = (int) dbInsert.insertNewUserHelper(user.getName(), user.getAge(), user.getAddress(), passwords.get(i));
			pswInsert.insertUnhashedPassword(passwords.get(i), user_id);
			i++;
		}
		for (Item item : items) {
			dbInsert.insertItemHelper(item.getName(), item.getPrice());
		}
		InventoryImpl inv_impl = (InventoryImpl) inventory;
		dbInsert.insertInventoryHelper(inv_impl.getItem().getId(), inventory.getTotalItems());
		
		List<Sale> list_reg_sales = reg_sales.getSales(reg_sales);
		for(Sale sale : list_reg_sales) {
			dbInsert.insertSaleHelper(sale.getUser().getId(), sale.getTotalPrice());
		}
		
        List<Sale> list_item_sales = itemized_sales.getSales(itemized_sales);
		for(Sale sale : list_item_sales) {
			SaleImpl saleimpl = (SaleImpl) sale;
			dbInsert.insertItemizedSaleHelper(sale.getId(), saleimpl.getItem().getId(), saleimpl.getQuantity());
		}
	}
}
