package com.b07.store;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import android.content.Context;

import com.b07.database.DatabaseDriver;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.enums.*;
import com.b07.inventory.Item;
import com.b07.users.Admin;
import com.b07.users.Employee;
import com.b07.users.Customer;
import com.b07.users.User;
import com.b07.users.UserFactory;
import java.math.BigDecimal;


public class SalesApplication {
	/**
	 * This is the main method to run your entire program! Follow the "Pulling it
	 * together" instructions to finish this off.
	 * 
	 * @param argv unused.
	 */
	private static Context context;

	public SalesApplication(Context context) {
		this.context = context;
	}

	public static void main(String[] argv) {
		DatabaseInsertHelper dbInsert = new DatabaseInsertHelper(context);
		DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(context);
		DatabaseUpdateHelper dbUpdate = new DatabaseUpdateHelper(context);

		// Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

		// if (connection == null) {
		//   System.out.print("NOOO");
		// }
		try {
			int argc = 1;
			if (argc == -1) {
				// DatabaseDriverExtender.initialize(connection);
				String adminPass = "123456";
				long adminId = dbInsert.insertNewUserHelper("Some Admin", 19, "Canada", adminPass);
				long roleId = dbInsert.insertRoleHelper(Roles.ADMIN.name());
				long adminRoleId = dbInsert.insertUserRoleHelper((int) adminId, (int) roleId);
				String employeePass = "654321";
				long employeeId = dbInsert.insertNewUserHelper("Some Employee", 19, "Portugal", employeePass);
				long employeeRoleId = dbInsert.insertRoleHelper(Roles.EMPLOYEE.name());
				long empRoleId = dbInsert.insertUserRoleHelper((int) employeeId, (int) employeeRoleId);

			} else if (argc == 1) {
				String input = "";
				boolean cont = true;
				List<Integer> list = dbSelect.getRoleIds();
				System.out.println(list);
				System.out.println("Choose one of the following options:");
				System.out.println("1 - Admin Mode");
				System.out.println("2 - Employee Mode");
				System.out.println("3 - Customer Mode");
				input = bufferedReader.readLine();
				// admin mode
				if (input.equals("1")) {
					System.out.println("Welcome to admin mode");
					System.out.println("Login with valid admin account.");
					System.out.println("Enter admin ID");
					String adminInput = bufferedReader.readLine();
					Integer adminId = Integer.parseInt(adminInput);
					System.out.println("enter password");
					String adminPass = bufferedReader.readLine();
					Boolean validatedPass = dbSelect.validatePassword(adminId, adminPass);
					if (validatedPass == true) {
						System.out.println("Congrats admin you have successfully logged in");
						System.out.println("Choose one of the following options:");
						System.out.println("1 - Promote an Employee");
						System.out.println("4 - demote an Employee");
						System.out.println("2 - Log off");
						input = bufferedReader.readLine();
						if (input.equals("1")) {
							System.out.println("Please enter the ID of the Employee that you would like to promote");
							String employId = bufferedReader.readLine();
							int empId = Integer.parseInt(employId);
							Boolean promote = Admin.promoteEmployee(empId);
							if (promote) {
								System.out.println("Employee is promoted to Admin");
							} else {
								System.out.println("The operation was unsuccessful");
							}
						} else if (input.equals("4")) {
							System.out.println("Please enter the ID of the Employee that you would like to promote");
							String employId = bufferedReader.readLine();
							int empId = Integer.parseInt(employId);
							Boolean promote = dbUpdate.updateUserRoleHelper(2, empId);
							if (promote) {
								System.out.println("Employee is demoted to employee");
							} else {
								System.out.println("The operation was unsuccessful");
							}
						} else {
							System.out.println("Goodbye Admin");
							cont = false;
							System.exit(0);
						}
					} else {
						System.out.println("Password is incorrect");
						System.exit(0);
					}
				} else if (input.equals("2")) {
					while (cont) {
						System.out.println("Welcome to employee mode");
						System.out.println("Login with valid employee account.");
						System.out.println("Enter employee ID");
						String empInput = bufferedReader.readLine();
						Integer empId = Integer.parseInt(empInput);
						System.out.println("enter password");
						String empPass = bufferedReader.readLine();
						Boolean validatedPass = dbSelect.validateEmpPassword(empId, empPass);
						if (validatedPass == true) {
							System.out.println("Congrats employee you have successfully logged in");
							System.out.println("Choose one of the following options");
							System.out.println("1 - Authenticate New Employee");
							System.out.println("2 - Make New Customer");
							System.out.println("3 - Make New Account");
							System.out.println("4 - Make New Employee");
							System.out.println("5 - Restock Inventory");
							System.out.println("6 - Exit");
							input = bufferedReader.readLine();
							if (input.equals("1")) {
								System.out.print("enter Employee UserID to authenticate");
								input = bufferedReader.readLine();
								int userId = Integer.parseInt(input);
								System.out.print("enter employee password");
								input = bufferedReader.readLine();
								String password = input;
								// try to authenticate
								// maybe add authenticate to emp interface
								Admin admin = new Admin(context, 0, password, userId, password);
								admin.authenticate(password);
								if (!admin.authenticate(password)) {
									System.out.println("The password is incorrect!!!");
								} else {
									System.out.println("This employee is authenticated");
								}
							} else if (input.equals("2")) {
								System.out.println("What is the name");
								String name = bufferedReader.readLine();
								System.out.println("What is the age?");
								String ages = bufferedReader.readLine();
								int age = Integer.parseInt(ages);
								System.out.println("What is the adress?");
								String address = bufferedReader.readLine();
								System.out.println("what is the password?");
								String password = bufferedReader.readLine();
								long userId = dbInsert.insertNewUserHelper(name, age, address, password);
								long roleId = dbInsert.insertRoleHelper(Roles.CUSTOMER.name());
								long custRoleId = dbInsert.insertUserRoleHelper((int) userId, (int) roleId);
								System.out.println("successfuly created a new user");
								;
							} else if (input.equals("3")) {
								System.out.println("please re-enter your userId");
								String userIdString = bufferedReader.readLine();
								int userId = Integer.parseInt(userIdString);
								long acctid = dbInsert.insertAccount(userId);
								System.out.println("successfuly created a new account");
							} else if (input.equals("4")) {
								System.out.println("What is the name");
								String name = bufferedReader.readLine();
								System.out.println("What is the age?");
								String ages = bufferedReader.readLine();
								int age = Integer.parseInt(ages);
								System.out.println("What is the adress?");
								String address = bufferedReader.readLine();
								System.out.println("what is the password?");
								String password = bufferedReader.readLine();
								long userId = dbInsert.insertNewUserHelper(name, age, address, password);
								long roleId = dbInsert.insertRoleHelper(Roles.EMPLOYEE.name());
								long empRoleId = dbInsert.insertUserRoleHelper((int) userId, (int) roleId);
								System.out.println("successfuly created a new employee");
								;
							} else if (input.equals("5")) {
								System.out.println("Choose one of the following options");
								System.out.println("1 - add items");
								System.out.println("2 - remove items");
								input = bufferedReader.readLine();
								if (input.equals("1")) {
									System.out.println("What is the itemId of the item you would like to modify");
									input = bufferedReader.readLine();
									int itemId = Integer.parseInt(input);
									Item item = dbSelect.getItemHelper(itemId);
									System.out.println("How much of the following would you like to add?");
									input = bufferedReader.readLine();
									int quantity = Integer.parseInt(input);
									dbUpdate.updateInventoryQuantityHelper(quantity, itemId);
								} else if (input.equals("2")) {
									System.out.println("What is the itemId of the item you would like to modify");
									input = bufferedReader.readLine();
									int itemId = Integer.parseInt(input);
									Item item = dbSelect.getItemHelper(itemId);
									System.out.println("How much of the following would you like to remove?");
									input = bufferedReader.readLine();
									int quantity = Integer.parseInt(input);
									dbUpdate.updateInventoryQuantityHelper(quantity, itemId);
								}
							} else if (input.equals("6")) {
								System.exit(0);
							}
						} else {
							System.out.println("Password is incorrect");
							System.exit(0);
						}

					}
				} else if (input.equals("3")) {
					System.out.println("Welcome to Customer mode");
					System.out.println("Login with valid Customer account.");
					System.out.println("Enter Customer ID");
					String cusInput = bufferedReader.readLine();
					Integer cusId = Integer.parseInt(cusInput);
					System.out.println("enter password");
					String cusPass = bufferedReader.readLine();
					Boolean validatedCustPass = dbSelect.validateCustPassword(cusId, cusPass);
					if (validatedCustPass == true) {
						boolean contTwo = true;
						System.out.println("Customer has been authenticated...");
						while (contTwo) {
							System.out.println("Choose one of the following options");
							System.out.println("1 - List Current Items in Cart");
							System.out.println("2 - Add a Quantity of the Item to the Cart");
							System.out.println("3 - Check Total Price of Items in the Cart");
							System.out.println("4 - Remove a Quantity of an Item from the Cart");
							System.out.println("5 - Checkout");
							System.out.println("6 - Exit");
							input = bufferedReader.readLine();
							if (input.equals("1")) {
								System.out.println("What is your customer id");
								input = bufferedReader.readLine();
								int customerId = Integer.parseInt(input);
								User customer = dbSelect.getUserDetailsHelper(customerId);
								ShoppingCart sc = new ShoppingCart(context, (Customer) customer);
								System.out.println("your items" + sc.getItems());
							} else if (input.equals("2")) {
								// add items to cart
								System.out.println("What is your customer id");
								input = bufferedReader.readLine();
								int customerId = Integer.parseInt(input);
								User customer = dbSelect.getUserDetailsHelper(customerId);
								System.out
										.println("What is the ID of the item that you would like to add to your cart?");
								input = bufferedReader.readLine();
								int id_item = Integer.parseInt(input);
								Item item = dbSelect.getItemHelper(id_item);
								System.out.println("How many of the selected item whould you like to buy?");
								input = bufferedReader.readLine();
								int quantity_item = Integer.parseInt(input);
								ShoppingCart sc = new ShoppingCart(context, (Customer) customer);
								sc.addItem(item, quantity_item);
							} else if (input.equals("3")) {
								// total price of items
								System.out.println("What is your customer id");
								input = bufferedReader.readLine();
								int customerId = Integer.parseInt(input);
								User customer = dbSelect.getUserDetailsHelper(customerId);
								ShoppingCart sc = new ShoppingCart(context, (Customer) customer);
								BigDecimal total = sc.getTotal();
								System.out.println("Your total is " + total);
							} else if (input.equals("4")) {
								// remove quantity
								System.out.println("What is your customer id");
								input = bufferedReader.readLine();
								int customerId = Integer.parseInt(input);
								User customer = dbSelect.getUserDetailsHelper(customerId);
								ShoppingCart sc = new ShoppingCart(context, (Customer) customer);
								System.out.println(
										"What is the ID of the item that you would like to remove from your cart?");
								input = bufferedReader.readLine();
								int id_item = Integer.parseInt(input);
								Item item = dbSelect.getItemHelper(id_item);
								System.out.println("How many of the selected item whould you like to remove?");
								input = bufferedReader.readLine();
								int quantity_item = Integer.parseInt(input);
								sc.removeItem(item, quantity_item);
							} else if (input.equals("5")) {
								// checkout
								System.out.println("What is your customer id");
								input = bufferedReader.readLine();
								int customerId = Integer.parseInt(input);
								User customer = dbSelect.getUserDetailsHelper(customerId);
								ShoppingCart sc = new ShoppingCart(context, (Customer) customer);
								sc.checkOut();
							} else {
								System.out.println("Goodbye");
								System.exit(0);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Improve this!
			System.out.println("Something went wrong :(");
			e.printStackTrace();
		} finally {
			try {
				dbInsert.close();
			} catch (Exception e) {
				System.out.println("Looks like it was closed already :)");
			}
		}
	}
}