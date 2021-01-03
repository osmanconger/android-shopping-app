package com.b07.database.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;

import com.b07.exceptions.BadInputException;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.store.SalesLog;
import com.b07.users.User;

public class Serializing {
	
	public static void saveDatabase(Context context) throws  java.sql.SQLException, BadInputException {
		
		DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(context);
		
		List<User> users = dbSelect.getUsersDetailsHelper(); // insertNewUser
		List<Item> items = dbSelect.getAllItemsHelper(); //insertItem
		Inventory inventory = dbSelect.getInventoryHelper(); // insertInventory
		SalesLog reg_sales = dbSelect.getSalesHelper(); // insertSale
		SalesLog itemized_sales = dbSelect.getItemizedSalesHelper(); // insertItemizedSale
		ArrayList<String> passwords = new ArrayList<String>(); // insertPassword
		
		for(User user : users) {
			passwords.add(dbSelect.getPassword(user.getId()));
		}
		
		ArrayList<Object> data = new ArrayList<Object>(); 
		data.add(users);
		data.add(items);
		data.add(inventory);
		data.add(reg_sales);
		data.add(itemized_sales);
		data.add(passwords);
		
		try {
			FileOutputStream fileOut = new FileOutputStream("database_copy.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(data);
			out.close();
			fileOut.close();
			
		} catch(IOException i) {
			i.printStackTrace();
		}
		
	}
	
}
