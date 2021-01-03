package com.b07.database.helper;

import com.b07.database.*;
import com.b07.exceptions.BadInputException;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.enums.Roles;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.inventory.ItemImpl;
import com.b07.security.PasswordHelpers;
import com.b07.inventory.InventoryImpl;
import com.b07.store.Account;
import com.b07.store.Sale;
import com.b07.store.SaleImpl;
import com.b07.store.SalesLog;
import com.b07.store.SalesLogImpl;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.User;
import com.b07.users.UserFactory;

import android.content.Context;
import android.database.Cursor;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/*
 * TODO: Complete the below methods to be able to get information out of the database. TODO: The
 * given code is there to aide you in building your methods. You don't have TODO: to keep the exact
 * code that is given (for example, DELETE the System.out.println()) TODO: and decide how to handle
 * the possible exceptions
 */
public class DatabaseSelectHelper extends DatabaseDriverAndroid {

	// creating constant field
	private Context context;
	private static DatabaseSelectHelper singleton = null;

	// super constructor for context
	/**
	 * @param context
	 */
	public DatabaseSelectHelper(Context context) {
		super(context);
		this.context = context;
	}
	
	public static DatabaseSelectHelper getInstance(Context context) {
		if (singleton == null) {
			singleton = new DatabaseSelectHelper(context);
		}
		return singleton;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public List<Integer> getRoleIds() throws SQLException {
		// initialize a cursor
		Cursor cursor = null;

		List<Integer> ids = new ArrayList<>();
		cursor = this.getRoles();
		while (cursor.moveToNext()) {
			int intRoleId = (int) (cursor.getLong(cursor.getColumnIndex("ID")));
			ids.add(intRoleId);
		}
		cursor.close();
		return ids;
	}

	/**
	 * return the role
	 * 
	 * @param roleId - id of the role
	 * @return role
	 */
	public String getRoleName(int roleId) {
		String role = this.getRole(roleId);
		return role;
	}

	/**
	 * return id of the user
	 * 
	 * @param userId - id of user
	 * @return roleId
	 * @throws SQLException
	 */
	public int getUserRoleId(int userId) throws SQLException {
		int roleId = this.getUserRole(userId);
		return roleId;
	}

	/**
	 * get all the users that have the roleId specified
	 * 
	 * @param roleId - id of role
	 * @return a list of users with a specific roleId
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public List<Integer> getUsersByRoleHelper(int roleId) throws SQLException, BadInputException {
		Cursor cursor = null;
		// Get the cursor containing all of the users ids associated witha role id and
		// add the users
		// ids to a list which is returned.
		DatabaseInsertHelper dbInsert = new DatabaseInsertHelper(context);
		if (dbInsert.verifyRoleId(roleId)) {
			cursor = this.getUsersByRole(roleId);
			List<Integer> userIds = new ArrayList<>();
			while (cursor.moveToNext()) {
				userIds.add((int) cursor.getLong(cursor.getColumnIndex("USERID")));
			}
			// closing the cursor
			cursor.close();
			dbInsert.close();
			return userIds;
		} else {
			dbInsert.close();
			throw new BadInputException();

		}

	}

	/**
	 * get a list of users in the database
	 * 
	 * @return list of users
	 * @throws SQLException
	 */
	public List<User> getUsersDetailsHelper() throws SQLException {
		Cursor cursor = this.getUsersDetails();
		List<User> users = new ArrayList<>();
		UserFactory userfactory = new UserFactory(context);
		while (cursor.moveToNext())
			;
		{
			int id = cursor.getInt(cursor.getColumnIndex("ID"));
			String name = cursor.getString(cursor.getColumnIndex("NAME"));
			int age = cursor.getInt(cursor.getColumnIndex("AGE"));
			String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
			String role = cursor.getString(cursor.getColumnIndex("ROLES"));
			users.add(userfactory.createUser(role, id, name, age, address));
		}
		cursor.close();
		return users;

	}

	/**
	 * return the user that belongs to the specified userId
	 * 
	 * @param userId - id of the user
	 * @return
	 * @throws SQLException
	 */
	public User getUserDetailsHelper(int userId) throws SQLException {
		try {
			Cursor cursor = this.getUserDetails(userId);
			UserFactory userfactory = new UserFactory(context);
			cursor.moveToFirst();
			int id = cursor.getInt(cursor.getColumnIndex("ID"));
			String name = cursor.getString(cursor.getColumnIndex("NAME"));
			int age = cursor.getInt(cursor.getColumnIndex("AGE"));
			String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
			String role = cursor.getString(cursor.getColumnIndex("ROLES"));
			User user = (userfactory.createUser(role, id, name, age, address));
			cursor.close();
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * return the password of the userId
	 *
	 * @param userId - id of user
	 *
	 */
	public String getPassword(int userId) {
		String password = this.getPassword(userId);
		return password;
	}

	/**
	 * return all accounts that belong to the user
	 * 
	 * @param userId - id of user
	 * @return a list of accounts
	 * @throws BadInputException
	 * @throws SQLException
	 */
	public List<Account> getAllAccounts(int userId) throws BadInputException, SQLException {
		Cursor cursor = null;
		cursor = this.getUserAccounts(userId);
		Customer cust = (Customer) getUserDetailsHelper(userId);
		List<Account> allAccounts = new ArrayList<Account>();
		while (cursor.moveToNext()) {
			int id = cursor.getColumnIndex("ID");
			int active = cursor.getColumnIndex("ACTIVE");
			Account account = new Account(context, cust, (int) cursor.getLong(id), cursor.getInt(active));
			HashMap<Item, Integer> ShoppingCart = getAllAccountDetails((int) cursor.getLong(id));
			for (Item e : ShoppingCart.keySet()) {
				account.addItem(e, ShoppingCart.get(e));
			}
			allAccounts.add(account);
		}
		return allAccounts;
	}

	/**
	 * return all accountDetails for an account
	 * 
	 * @param accountId - id of account
	 * @return a hashmap of all items in account cart
	 * @throws BadInputException
	 * @throws SQLException
	 */
	public HashMap<Item, Integer> getAllAccountDetails(int accountId) throws BadInputException, SQLException {
		Cursor cursor = null;
		cursor = this.getAccountDetails(accountId);
		HashMap<Item, Integer> cart = new HashMap<Item, Integer>();
		while (cursor.moveToNext()) {
			int id = cursor.getColumnIndex("ITEMID");
			int quantity = cursor.getColumnIndex("QUANTITY");
			Item item = (Item) getItemHelper(cursor.getInt(id));
			cart.put(item, cursor.getInt(quantity));
		}
		cursor.close();
		return cart;
	}

	/**
	 * list that gets all items in db
	 * 
	 * @return lit of items
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public List<Item> getAllItemsHelper() throws SQLException, BadInputException {
		Cursor cursor = null;
		List<Item> items = new ArrayList<>();
		cursor = this.getAllItems();
		while (cursor.moveToNext()) {
			int id = (int) cursor.getLong(cursor.getColumnIndex("ID"));
			Item item = this.getItemHelper(id);
			items.add(item);
		}
		cursor.close();
		return items;
	}

	/**
	 * get an item using the specified id
	 * 
	 * @param itemId - id of items
	 * @return
	 * @throws BadInputException
	 * @throws SQLException
	 */
	public Item getItemHelper(int itemId) throws BadInputException, SQLException {
		Cursor cursor = null;

		try {
			cursor = this.getItem(itemId);
			cursor.moveToFirst();
			Item item = null;
			int id = cursor.getInt(cursor.getColumnIndex("ID"));
			String name = cursor.getString(cursor.getColumnIndex("NAME"));
			String price = cursor.getString(cursor.getColumnIndex("PRICE"));
			item = new ItemImpl(id, name, new BigDecimal(price));
			return item;
		} catch (Exception e) {
			throw new BadInputException();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/**
	 * get the inventory
	 * 
	 * @return inventory
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public Inventory getInventoryHelper() throws SQLException, BadInputException {
		Cursor cursor = null;
		cursor = this.getInventory();
		Inventory inv = new InventoryImpl();
		HashMap<Item, Integer> invMap = new HashMap<Item, Integer>();
		while (cursor.moveToNext()) {
			int itemId = cursor.getColumnIndex("ITEMID");
			int quantity = cursor.getColumnIndex("QUANTITY");
			Item item = getItemHelper((int) cursor.getLong(itemId));
			Integer quant = cursor.getInt(quantity);
			invMap.put(item, quant);
		}
		inv.setItemMap(invMap);
		return inv;
	}

	/**
	 * get the quantity of a specific item
	 * 
	 * @param itemId - id of item
	 * @return
	 * @throws SQLException
	 */
	public int getInventoryQuantityHelper(int itemId) throws SQLException {
		DatabaseInsertHelper dbInsert = new DatabaseInsertHelper(context);
		int quantity = this.getInventoryQuantity(itemId);
		dbInsert.close();
		return quantity;
	}
	/**
	 * get the quantity of a specific item
	 * 
	 * @param itemId - id of item
	 * @return
	 * @throws SQLException
	 */
	public static int getInventoryQuantityHelperStatic(int itemId) throws SQLException {
		DatabaseSelectHelper dbSelect = DatabaseSelectHelper.getInstance(null);
		int quantity = dbSelect.getInventoryQuantityHelper(itemId);
		dbSelect.close();
		return quantity;
	}
	
	

	/**
	 * get all sales
	 * 
	 * @return - SalesLog
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public SalesLog getSalesHelper() throws SQLException, BadInputException {
		Cursor cursor = null;
		cursor = this.getSales();
		SalesLog saleslog = new SalesLogImpl();
		while (cursor.moveToNext()) {
			int id = cursor.getColumnIndex("ID");
			Sale sale = this.getSaleByIdHelper((int) cursor.getLong(id));
			if (sale != null) {
				saleslog.addSale(sale);
			}
		}
		cursor.close();
		return saleslog;
	}

	/**
	 * return the sale belonging to the given saleID
	 * 
	 * @param saleId - id of sale
	 * @return
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public Sale getSaleByIdHelper(int saleId) throws SQLException, BadInputException {
		Cursor cursor = null;
		cursor = this.getSaleById(saleId);
		Sale sale = null;
		while (cursor.moveToNext()) {
			int saleIdent = cursor.getColumnIndex("ID");
			int userId = cursor.getColumnIndex("USERID");
			int totalPrice = cursor.getColumnIndex("TOTALPRICE");
			sale = new SaleImpl((int) cursor.getLong(saleIdent),
					this.getUserDetailsHelper((int) cursor.getLong(userId)),
					new BigDecimal(cursor.getString(totalPrice)));
		}
		this.getItemizedSaleByIdHelper(saleId);
		return sale;
	}

	/**
	 * return list of sales belonging to a user
	 * 
	 * @param userId-id of user
	 * @return
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public List<Sale> getSalesToUserHelper(int userId) throws SQLException, BadInputException {
		Cursor cursor = null;
		DatabaseInsertHelper dbInsert = new DatabaseInsertHelper(context);
		cursor = this.getSalesToUser(userId);
		if (dbInsert.verifyUserId(userId)) {
			List<Sale> sales = new ArrayList<>();
			while (cursor.moveToNext()) {
				int saleId = cursor.getColumnIndex("ID");
				Sale sale = this.getSaleByIdHelper((int) cursor.getLong(saleId));
				sales.add(sale);
			}
			dbInsert.close();
			cursor.close();
			return sales;
		} else {
			dbInsert.close();
			throw new BadInputException();
		}

	}

	/**
	 * return the itimized sale when the saleid is specified
	 * 
	 * @param saleId - id of the sale
	 * @return sale
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public Sale getItemizedSaleByIdHelper(int saleId) throws SQLException, BadInputException {
		Cursor cursor = null;
		cursor = this.getItemizedSaleById(saleId);
		Sale sale = null;
		while (cursor.moveToNext()) {
			int saleIdent = cursor.getColumnIndex("SALEID");
			int itemId = cursor.getColumnIndex("ITEMID");
			int quantity = cursor.getColumnIndex("QUANTITY");
			sale = new SaleImpl((int) cursor.getLong(saleIdent), this.getItemHelper((int) cursor.getLong(itemId)),
					(int) cursor.getLong(quantity));
		}
		cursor.close();
		return sale;
	}

	/**
	 * get the SalesLog
	 * 
	 * @return salesLog
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public SalesLog getItemizedSalesHelper() throws SQLException, BadInputException {
		Cursor cursor = null;
		cursor = this.getSales();
		SalesLog saleslog = new SalesLogImpl();
		Sale sale = null;
		while (cursor.moveToNext()) {
			int id = cursor.getColumnIndex("ID");
			sale = this.getSaleByIdHelper((int) cursor.getLong(id));
			if (sale != null) {
				saleslog.addSale(sale);
			}
		}
		cursor.close();
		return saleslog;
	}

	/**
	 * validate the password for an admin
	 * 
	 * @param userId-id of user
	 * @param password- password of user
	 * @return
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public Boolean validatePassword(int userId, String password) throws SQLException, BadInputException {
		DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(context);
		int roleId = dbSelect.getUserRoleId(userId);
		boolean validated = true;
		long adminRoleId = dbSelect.insertRole(Roles.ADMIN.name());
		dbSelect.close();
		System.out.println(roleId);
		if (roleId == adminRoleId) {
			List<Integer> admins = dbSelect.getUsersByRoleHelper(roleId);
			System.out.println("ADMINS");
			for (Integer i : admins)
				System.out.println(i);
			System.out.println("END ADMINS");
			if (admins.contains(userId)) {
				String dbPassword = dbSelect.getPassword(userId);
				System.out.println(dbPassword);
				boolean validatedPass = PasswordHelpers.comparePassword(dbPassword, password);
				System.out.println(validated);
				validated = validatedPass;
			}
		} else {
			System.out.println("Incorrect Role or password");
			validated = false;
		}
		return validated;
	}

	/**
	 * validate an employee password
	 * 
	 * @param userId-           id of user
	 * @param password-password of user
	 * @return
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public Boolean validateEmpPassword(int userId, String password) throws SQLException, BadInputException {
		DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(context);
		int roleId = dbSelect.getUserRoleId(userId);
		boolean validated = true;
		long empRoleId = dbSelect.insertRole(Roles.EMPLOYEE.name());
		dbSelect.close();
		System.out.println(roleId);
		if (roleId == empRoleId) {
			List<Integer> employees = dbSelect.getUsersByRoleHelper(roleId);
			System.out.println("Employees");
			for (Integer i : employees)
				System.out.println(i);
			System.out.println("END ADMINS");
			if (employees.contains(userId)) {
				String dbPassword = dbSelect.getPassword(userId);
				System.out.println(dbPassword);
				boolean validatedPass = PasswordHelpers.comparePassword(dbPassword, password);
				System.out.println(validated);
				validated = validatedPass;
			}
		} else {
			System.out.println("Incorrect Role or password");
			validated = false;
		}
		return validated;
	}

	/**
	 * check if customer provided correct password
	 * 
	 * @param userId   - id of user
	 * @param password - password of user
	 * @return
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public Boolean validateCustPassword(int userId, String password) throws SQLException, BadInputException {
		DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(context);
		int roleId = dbSelect.getUserRoleId(userId);
		boolean validated = true;
		long custRoleId = dbSelect.insertRole(Roles.CUSTOMER.name());
		dbSelect.close();
		System.out.println(roleId);
		if (roleId == custRoleId) {
			List<Integer> customers = dbSelect.getUsersByRoleHelper(roleId);
			System.out.println("customers");
			for (Integer i : customers)
				System.out.println(i);
			System.out.println("end customers");
			if (customers.contains(userId)) {
				String dbPassword = dbSelect.getPassword(userId);
				System.out.println(dbPassword);
				boolean validatedPass = PasswordHelpers.comparePassword(dbPassword, password);
				System.out.println(validated);
				validated = validatedPass;
			}
		} else {
			System.out.println("Incorrect Role or password");
			validated = false;
		}
		return validated;

	}
}