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

public class DatabaseUpdateHelper extends DatabaseDriverAndroid {

	// creating constant field
	private Context context;
	private static DatabaseUpdateHelper singleton = null;

	/**
	 * super constructor for context
	 * 
	 * @param context
	 */
	public DatabaseUpdateHelper(Context context) {
		super(context);
		this.context = context;
	}

	public static DatabaseUpdateHelper getInstance(Context context) {
		if (singleton == null) {
			singleton = new DatabaseUpdateHelper(context);
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
	 * @param name
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DatabaseInsertException
	 */
	public boolean updateRoleNameHelper(String name, int id) throws SQLException, DatabaseInsertException {
		// TODO Implement this method as stated on the assignment sheet
		// hint: You should be using these three lines in your final code
		boolean validRole;
		DatabaseUpdateHelper dbUpdate = new DatabaseUpdateHelper(context);
		validRole = checkExistEnum(name);
		if (!validRole) {
			dbUpdate.close();
			throw new DatabaseInsertException();
		}
		boolean complete = this.updateRoleName(name, id);
		dbUpdate.close();
		return complete;
	}

	/**
	 * update a users name
	 * 
	 * @param name    - name of the user
	 * @param userId- id of the user
	 * @return boolean representing whether the update was succesful
	 * @throws SQLException
	 */
	public boolean updateUserNameHelper(String name, int userId) throws SQLException {
		// checking if the name is not an empty string
		// checking if the user id is greater than or equal to 1
		boolean complete;
		DatabaseUpdateHelper dbUpdate = new DatabaseUpdateHelper(context);
		if (userId >= 1 && !name.isEmpty()) {
			complete = this.updateUserName(name, userId);
			dbUpdate.close();
		} else {
			// the userid or name is invalid
			dbUpdate.close();
			complete = false;
		}
		return complete;
	}

	/**
	 * update users age
	 * 
	 * @param age    - age of user
	 * @param userId - id of user
	 * @return boolean representing whether the update was succesful
	 * @throws SQLException
	 */
	public boolean updateUserAgeHelper(int age, int userId) throws SQLException {
		// check whether userId is greater than or equal to 1
		// check whether age is greater than or equal to 0
		boolean complete;
		DatabaseUpdateHelper dbUpdate = new DatabaseUpdateHelper(context);
		if ((userId >= 1) && (age >= 0)) {
			complete = this.updateUserAge(age, userId);
			dbUpdate.close();
		} else {
			// either the userId or age is invalid
			dbUpdate.close();
			complete = false;
		}
		return complete;
	}

	/**
	 * update the users adress in the database
	 * 
	 * @param address- adress of user
	 * @param userId   -id of user
	 * @return boolean representing whether the update was succesful
	 * @throws SQLException
	 * @throws DatabaseInsertException
	 */
	public boolean updateUserAddressHelper(String address, int userId) throws SQLException, DatabaseInsertException {
		DatabaseUpdateHelper dbUpdate = new DatabaseUpdateHelper(context);
		if (address.length() > 100) {
			dbUpdate.close();
			throw new DatabaseInsertException();
		}
		boolean complete = this.updateUserAddress(address, userId);
		dbUpdate.close();
		return complete;
	}

	/**
	 * udate the users role in the database
	 * 
	 * @param roleId - id of the role
	 * @param userId id of the user
	 * @return boolean representing whether the update was succesful
	 * @throws SQLException
	 * @throws DatabaseInsertException
	 * @throws BadInputException
	 */
	public boolean updateUserRoleHelper(int roleId, int userId)
			throws SQLException, DatabaseInsertException, BadInputException {
		boolean validRole;
		boolean complete;
		DatabaseUpdateHelper dbUpdate = new DatabaseUpdateHelper(context);
		validRole = verifyRoleId(roleId);
		if (!validRole) {
			dbUpdate.close();
			throw new DatabaseInsertException();
		}
		// check if userId is valid
		else if (userId >= 1) {
			complete = this.updateUserRole(roleId, userId);
			dbUpdate.close();
			return complete;
		} else {
			dbUpdate.close();
			complete = false;
		}
		return complete;
	}

	/**
	 * updaet the name of the item
	 * 
	 * @param name   - name of item
	 * @param itemId - id of item
	 * @return boolean representing whether the update was succesful
	 * @throws SQLException
	 * @throws DatabaseInsertException
	 */
	public boolean updateItemNameHelper(String name, int itemId) throws SQLException, DatabaseInsertException {
		DatabaseUpdateHelper dbUpdate = new DatabaseUpdateHelper(context);
		if (name.length() >= 64) {
			dbUpdate.close();
			throw new DatabaseInsertException();
		}
		boolean complete = this.updateItemName(name, itemId);
		dbUpdate.close();
		return complete;
	}

	/**
	 * update the price of the item
	 * 
	 * @param price  - price of item
	 * @param itemId -id of item
	 * @return boolean representing whether the update was succesful
	 * @throws SQLException
	 * @throws DatabaseInsertException
	 */
	public boolean updateItemPriceHelper(BigDecimal price, int itemId) throws SQLException, DatabaseInsertException {
		BigDecimal zero = new BigDecimal("0");
		DatabaseUpdateHelper dbUpdate = new DatabaseUpdateHelper(context);
		if (price.compareTo(zero) == 0 || price.compareTo(zero) == -1) {
			dbUpdate.close();
			throw new DatabaseInsertException();
		}
		boolean complete = this.updateItemPrice(price, itemId);
		dbUpdate.close();
		return complete;
	}

	/**
	 * update the quantitity of an item in the inventory
	 * 
	 * @param quantity -amount of item
	 * @param itemId   -id of item
	 * @return boolean representing whether the update was succesful
	 * @throws SQLException
	 * @throws DatabaseInsertException
	 */
	public boolean updateInventoryQuantityHelper(int quantity, int itemId)
			throws SQLException, DatabaseInsertException {
		DatabaseUpdateHelper dbUpdate = new DatabaseUpdateHelper(context);
		if (quantity <= 0) {
			dbUpdate.close();
			throw new DatabaseInsertException();
		}
		boolean complete = dbUpdate.updateInventoryQuantity(quantity, itemId);
		dbUpdate.close();
		return complete;
	}
}
