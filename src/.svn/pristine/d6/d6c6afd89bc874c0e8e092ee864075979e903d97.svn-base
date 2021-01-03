package com.b07.store;

import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.users.Customer;
import com.b07.users.Employee;

import android.content.Context;

import java.sql.SQLException;
import com.b07.database.helper.*;
import com.b07.exceptions.BadInputException;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.enums.*;
import com.b07.database.*;

public class EmployeeInterface {
	private Employee currentEmployee;
	private Inventory inventory;
	// context
	private Context context;
	// inserthelper
	private DatabaseInsertHelper dbInsert;
	// selecthelper
	private DatabaseSelectHelper dbSelect;
	// updateHelper
	private DatabaseUpdateHelper dbUpdate;

	public EmployeeInterface(Context context, Employee employee, Inventory inventory) throws SQLException {
		this.context = context;
		int userId = employee.getId();
		String passwordDb = dbSelect.getPassword(userId);
		boolean userAuthenticated = employee.authenticate(passwordDb);
		if (userAuthenticated) {
			this.currentEmployee = employee;
			this.inventory = inventory;
			dbSelect.close();
		}
	}

	public EmployeeInterface(Context context, Inventory inventory) {
		this.inventory = inventory;
		this.context = context;
	}

	// create new admin
	public static int createNewAdmin(String name, int age, String address, String password)
			throws BadInputException, SQLException, DatabaseInsertException, ConnectionFailedException {
		DatabaseInsertHelper dbInstance = DatabaseInsertHelper.getInstance(null);
		long RoleId = dbInstance.insertRoleHelper(Roles.ADMIN.name());
		int userId = (int) dbInstance.insertNewUserHelper(name, age, address, password);
		dbInstance.insertUserRoleHelper(userId, (int) RoleId);
		dbInstance.close();
		return userId;
	}
	
	// create new emp
	public static int createNewEmp(String name, int age, String address, String password)
			throws BadInputException, SQLException, DatabaseInsertException, ConnectionFailedException {
		DatabaseInsertHelper dbInstance = DatabaseInsertHelper.getInstance(null);
		long RoleId = dbInstance.insertRoleHelper(Roles.EMPLOYEE.name());
		int userId = (int) dbInstance.insertNewUserHelper(name, age, address, password);
		dbInstance.insertUserRoleHelper(userId, (int) RoleId);
		dbInstance.close();
		return userId;
	}
	
	// create new user
	public static int createNewCustomer(String name, int age, String address, String password)
			throws BadInputException, SQLException, DatabaseInsertException, ConnectionFailedException {
		DatabaseInsertHelper dbInstance = DatabaseInsertHelper.getInstance(null);
		long RoleId = dbInstance.insertRoleHelper(Roles.CUSTOMER.name());
		int userId = (int) dbInstance.insertNewUserHelper(name, age, address, password);
		dbInstance.insertUserRoleHelper(userId, (int) RoleId);
		dbInstance.close();
		return userId;
	}

	public void setCurrentEmployee(Employee employee) throws SQLException {
		int userId = employee.getId();
		String passwordDb = dbSelect.getPassword(userId);
		boolean userAuthenticated = employee.authenticate(passwordDb);
		if (userAuthenticated) {
			this.currentEmployee = employee;
			dbSelect.close();
		}
	}

	public boolean hasCurrentEmployee() {
		if (currentEmployee != null) {
			return true;
		}
		return false;
	}

	/**
	 * restock the inventory with the specified item and quantity
	 * 
	 * @param item     - item
	 * @param quantity - amount of item
	 * @return - boolean to represent whether the inventory was updated
	 */
	public boolean restockInventory(Item item, int quantity) {
		inventory.updateMap(item, quantity);
		return true;
	}

	/**
	 * creat a customer based on the given parameters
	 *
	 * @param name     - name of customer
	 * @param age      - age of customer
	 * @param address  - adresss of customer
	 * @param password - password of customer
	 * @return a customer
	 * @throws DatabaseInsertException
	 * @throws SQLException
	 * @throws BadInputException
	 * @throws ConnectionFailedException
	 */
	public long createCustomer(String name, int age, String address, String password)
			throws DatabaseInsertException, SQLException, BadInputException, ConnectionFailedException {
		Customer customer;
		int userId;
		int roleId;
		DatabaseInsertHelper dbInsert = new DatabaseInsertHelper(context);
		userId = (int) dbInsert.insertNewUserHelper(name, age, address, password);
		roleId = (int) dbInsert.insertRoleHelper(name);
		customer = new Customer(context, userId, name, age, address);
		dbInsert.insertUserRoleHelper(userId, roleId);
		dbInsert.close();
		return userId;
	}

	/**
	 * create an employee based on specified parameters
	 * 
	 * @param name     - name of employee
	 * @param age      - age of employeee
	 * @param address  - adress of employee
	 * @param password - password of employee
	 * @return an employee
	 * @throws DatabaseInsertException
	 * @throws SQLException
	 * @throws BadInputException
	 */
	public int createEmployee(String name, int age, String address, String password)
			throws DatabaseInsertException, SQLException, BadInputException {
		Employee employee;
		int userId;
		int roleId;
		DatabaseInsertHelper dbInsert = new DatabaseInsertHelper(context);
		userId = (int) dbInsert.insertNewUserHelper(name, age, address, password);
		roleId = (int) dbInsert.insertRoleHelper(Roles.EMPLOYEE.name());
		employee = new Employee(context, userId, name, age, address);
		dbInsert.close();
		return age;
	}

}
