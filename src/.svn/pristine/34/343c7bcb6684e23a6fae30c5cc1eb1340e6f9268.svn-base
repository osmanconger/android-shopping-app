package com.b07.database.helper;

import com.b07.database.*;
import com.b07.exceptions.BadInputException;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.inventory.Item;
import com.b07.users.User;

import android.content.Context;

import com.b07.enums.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInsertHelper extends DatabaseDriverAndroid {

	/**
	 * creating context field
	 */
	private Context context;
	private static DatabaseInsertHelper singleton = null;

	//
	/**
	 * super constructor for context
	 * 
	 * @param context
	 */
	public DatabaseInsertHelper(Context context) {
		super(context);
		this.context = context;
	}
	
	  public static DatabaseInsertHelper getInstance(Context context) {
		    if (singleton == null) {
		      singleton = new DatabaseInsertHelper(context);
		    }
		    return singleton;
		  }

	//
	/**
	 * check if the enum exists
	 * 
	 * @param name - name of the enum
	 * @return boolean true if enum exists false if it doesnt
	 */
	private static boolean checkExistEnum(String name) {
		for (Roles role : Roles.values()) {
			if (role.name().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * check if role exists in db
	 * 
	 * @param role - the role of the user
	 * @return roleId or -1 if the id does not exist
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public int checkExistRole(String role) throws SQLException, BadInputException {
		DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(context);
		List<Integer> roleIdList = dbSelect.getRoleIds();
		// closing the db to avoid leaks
		dbSelect.close();
		// loop through the roleIdlist
		for (Integer roleId : roleIdList) {
			// check if the role given matches the role in the list
			if (role.equals((dbSelect.getRoleName(roleId)))) {
				return roleId;
			}
		}
		// if it does not exist return -1
		return -1;
	}

	/**
	 * verify if user exists in the db
	 * 
	 * @param UserId -id of the user
	 * @return boolean verifying whether or not the user exists in the database
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public boolean verifyUserId(int UserId) throws SQLException, BadInputException {
		DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(context);
		List<User> UserList = dbSelect.getUsersDetailsHelper();
		List<Integer> userIdList = new ArrayList<Integer>();
		dbSelect.close();
		// add the userids to a list
		for (User user : UserList) {
			userIdList.add(user.getId());
		}
		// check if the list contains the given userId
		return (userIdList.contains(UserId));
	}

	/**
	 * verify if role is in the db
	 * 
	 * @param roleId -int representing the role of the user
	 * @return boolean checking whether the roleid exists in the database
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public boolean verifyRoleId(int roleId) throws SQLException, BadInputException {
		DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(context);
		List<Integer> roleList = dbSelect.getRoleIds();
		dbSelect.close();
		// add the userids to a list
		return (roleList.contains(roleId));
	}

	/**
	 * verify if item exists in db
	 * 
	 * @param itemId- id of the item
	 * @return boolean representing whether the item exists in the database
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public boolean verifyItemId(int itemId) throws SQLException, BadInputException {
		DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(context);
		List<Item> itemList = dbSelect.getAllItemsHelper();
		List<Integer> itemIdList = new ArrayList<Integer>();
		dbSelect.close();
		// add all the items to a list
		for (Item item : itemList) {
			itemIdList.add(item.getId());
		}
		// check if the item list contains the id
		return (itemIdList.contains(itemId));
	}

	/**
	 * insert the role into db if it does not exist if it does return the id
	 * 
	 * @param name - name of the role
	 * @return long that represents the roleId
	 * @throws BadInputException
	 * @throws SQLException
	 */
	public long insertRoleHelper(String name) throws BadInputException, SQLException {
		// TODO Implement this method as stated on the assignment sheet
		// hint: You should be using these three lines in your final code
		boolean validRole;
		// check if role is valid if it exists
		validRole = checkExistEnum(name);
		// initialize the roleId
		long roleId;
		// if the role id not valid throw a bad input exception
		if (!validRole) {
			throw new BadInputException();
		}
		// if the role is valid
		else {
			// check if the role exists, if it does the roleId is returned if not
			// -1 will be returned
			roleId = this.checkExistRole(name);
			// if roleId is -1 a new role must be made
			if (roleId == -1) {
				roleId = this.insertRole(name);
			}
		}
		// return the roleID
		return roleId;
	}

	/**
	 * insert a new user into the db
	 * 
	 * @param name     - name of new user
	 * @param age      - age of new user
	 * @param address  - adress of new user
	 * @param password - password of new user
	 * @return a long representing the userId
	 * @throws DatabaseInsertException
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public long insertNewUserHelper(String name, int age, String address, String password)
			throws DatabaseInsertException, SQLException, BadInputException {
		// TODO Implement this method as stated on the assignment sheet
		// hint: You should be using these three lines in your final code
		if (address.length() > 100) {
			throw new BadInputException();
		}
		// inserting the new user
		long userId = this.insertNewUser(name, age, address, password);
		return userId;
	}

	/**
	 * insert a new role for the user
	 * 
	 * @param userId - the id of the user
	 * @param roleId - the id of the role
	 * @return the id of the users role
	 * @throws ConnectionFailedException
	 * @throws DatabaseInsertException
	 * @throws SQLException
	 */
	public long insertUserRoleHelper(int userId, int roleId)
			throws ConnectionFailedException, DatabaseInsertException, SQLException {
		// inserting the role with the user
		// if role does not exist in db a role is created
		long userRoleId = this.insertUserRole(userId, roleId);
		return userRoleId;
	}

	/**
	 * insert an item into db
	 * 
	 * @param name  - name of item
	 * @param price - price of item
	 * @return an itemId
	 * @throws ConnectionFailedException
	 * @throws DatabaseInsertException
	 * @throws SQLException
	 */
	@SuppressWarnings("deprecation")
	public long insertItemHelper(String name, BigDecimal price)
			throws ConnectionFailedException, DatabaseInsertException, SQLException {
		BigDecimal zero = new BigDecimal("0");
		if (name == null || name.length() > 63 || price.compareTo(zero) == 0 || price.compareTo(zero) == -1) {
			throw new DatabaseInsertException();
		}
		price = price.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		long itemId = this.insertItem(name, price);
		return itemId;
	}

	/**
	 * insert an inventory
	 * 
	 * @param itemId   - id representing the item
	 * @param quantity - amount of item
	 * @return - the inventory id of the item
	 * @throws ConnectionFailedException
	 * @throws DatabaseInsertException
	 * @throws SQLException
	 */
	public long insertInventoryHelper(int itemId, int quantity)
			throws ConnectionFailedException, DatabaseInsertException, SQLException {
		if (quantity <= 0) {
			throw new DatabaseInsertException();
		}
		long inventoryId = this.insertInventory(itemId, quantity);
		return inventoryId;
	}

	/**
	 * insert a sale
	 * 
	 * @param userId     - id of the user
	 * @param totalPrice - price of the item
	 * @return a long representing the saleId
	 * @throws ConnectionFailedException
	 * @throws DatabaseInsertException
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public long insertSaleHelper(int userId, BigDecimal totalPrice)
			throws ConnectionFailedException, DatabaseInsertException, SQLException, BadInputException {
		BigDecimal lowestPrice = new BigDecimal("0.01");
		if (!(totalPrice.compareTo(lowestPrice) == 0) || !(totalPrice.compareTo(lowestPrice) == 1)
				|| !(verifyUserId(userId))) {
			throw new DatabaseInsertException();
		} else {
			long saleId = this.insertSale(userId, totalPrice);
			return saleId;
		}
	}

	/**
	 * return the itemizedsale
	 * 
	 * @param saleId   - the id of the sale
	 * @param itemId   - the id of the item
	 * @param quantity - the amount of the item
	 * @return - an itemized id
	 * @throws ConnectionFailedException
	 * @throws DatabaseInsertException
	 * @throws SQLException
	 */
	public long insertItemizedSaleHelper(int saleId, int itemId, int quantity)
			throws ConnectionFailedException, DatabaseInsertException, SQLException {
		if (quantity <= 0) {
			throw new DatabaseInsertException();
		}

		long itemizedId = this.insertItemizedSale(saleId, itemId, quantity);
		return itemizedId;
	}

	/**
	 * insert an account for a specific user
	 * 
	 * @param userId - id of the user
	 * @return the long representing the id of the account
	 * @throws SQLException
	 * @throws DatabaseInsertException
	 * @throws BadInputException
	 */
	public long insertAccount(int userId) throws SQLException, DatabaseInsertException, BadInputException {
		if (!this.verifyUserId(userId)) {
			throw new DatabaseInsertException();
		}
		long accntId = this.insertAccount(userId);
		return accntId;
	}

	/**
	 * insert an accountline and return the accountId
	 * 
	 * @param accountId - id of the persons account
	 * @param itemId    - id of the item
	 * @param quantity  - amount of item
	 * @return - a long representing the accountId
	 * @throws SQLException
	 * @throws DatabaseInsertException
	 * @throws BadInputException
	 */
	public long insertAccountLineHelper(int accountId, int itemId, int quantity)
			throws SQLException, DatabaseInsertException, BadInputException {
		if (!this.verifyItemId(itemId)) {
			throw new DatabaseInsertException();
		}
		long accntId = this.insertAccountLine(accountId, itemId, quantity);
		return accntId;
	}

}