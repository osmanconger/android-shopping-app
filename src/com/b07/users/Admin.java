package com.b07.users;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.enums.Roles;
import com.b07.exceptions.BadInputException;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.inventory.Item;
import com.b07.store.Account;
import com.b07.store.Sale;
import com.b07.store.SalesLog;
import com.b07.database.helper.Serializing;
import com.b07.database.helper.Deserializing;

import android.content.Context;

import java.lang.StringBuilder.*;
import java.math.BigDecimal;

public class Admin extends User {
	private int id;
	private String name;
	private int age;
	private String address;
	private int roleId;
	private boolean authenticated;
	private Context context;
	// inserthelper
	private DatabaseInsertHelper dbInsert;
	// selecthelper
	private DatabaseSelectHelper dbSelect;
	// updateHelper
	private DatabaseUpdateHelper dbUpdate;

	public Admin(Context context, int id, String name, int age, String address) {

	}

	public Admin(int id, String name, int age, String address, boolean authenticated) {

	}

	// gotta check this need to connect to db
	public static boolean promoteEmployee(int userId) throws SQLException, DatabaseInsertException, BadInputException {
		// check if the person being promoted is an employee
		DatabaseInsertHelper dbInsert = DatabaseInsertHelper.getInstance(null);
		int adminId = (int) dbInsert.insertRoleHelper(Roles.ADMIN.name());
		dbInsert.close();
		boolean complete = false;
		DatabaseUpdateHelper dbUpdate = DatabaseUpdateHelper.getInstance(null);
		complete = dbUpdate.updateUserRoleHelper(adminId, userId);
		dbUpdate.close();
		return complete;
	}

	public StringBuilder viewBooks(SalesLog salesLog) {
		StringBuilder books = new StringBuilder();
		String custName = null;
		User user = null;
		int saleId;
		BigDecimal userTotalPrice;
		BigDecimal totalOverallPrice = new BigDecimal("0");
		HashMap<Item, Integer> userItemsSold = null;
		HashMap<String, Integer> totalItemsSold = new HashMap<String, Integer>();
		List<Sale> sales = salesLog.getSales(salesLog);
		for (Sale sale : sales) {
			saleId = sale.getId();
			user = sale.getUser();
			custName = user.getName();
			userItemsSold = sale.getItemMap();
			userTotalPrice = sale.getTotalPrice();
			totalOverallPrice.add(userTotalPrice);
			books.append("Customer: <<" + custName + ">> \r\n");
			books.append("Purchase Number: <<" + saleId + ">> \r\n");
			books.append("Total Purchase Price: <<" + userTotalPrice + ">> \r\n");
			books.append("Itemized Breakdown: <<Item>>: <<Quantity>>");
			for (Map.Entry<Item, Integer> entry : userItemsSold.entrySet()) {
				books.append(String.format("%1$" + 20 + "s",
						"<<" + entry.getKey().getName() + ">>: <<" + entry.getValue() + ">> \r\n"));
				if (totalItemsSold.containsKey(entry.getKey().getName())) {
					totalItemsSold.put(entry.getKey().getName(),
							totalItemsSold.get(entry.getKey().getName()) + entry.getValue());
				} else {
					totalItemsSold.put(entry.getKey().getName(), entry.getValue());
				}
			}
			books.append("----------------------------------------------------------------\r\n");
		}
		for (Map.Entry<String, Integer> entry : totalItemsSold.entrySet()) {
			books.append("Number <<" + entry.getKey() + ">> Sold: <<" + entry.getValue() + ">> \r\n");
		}
		books.append("TOTAL SALES: <<" + totalOverallPrice + ">> \r\n");

		return books;
	}

	// function that displays all inactive accounts for a given customer
	public List<Account> getHistoricAccounts(int userId) throws BadInputException, SQLException {
		List<Account> users;
		List<Account> historicAccounts = new ArrayList<Account>();
		DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(context);
		users = dbSelect.getAllAccounts(userId);
		for (Account account : users) {
			// 0 is inactive
			if (account.getActive() == 0) {
				users.add(account);
			}
		}
		dbSelect.close();
		return historicAccounts;
	}

	public List<Account> getActiveAccounts(int userId) throws BadInputException, SQLException {
		List<Account> users;
		List<Account> activeAccounts = new ArrayList<Account>();
		DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(context);
		users = dbSelect.getAllAccounts(userId);
		for (Account account : users) {
			// 1 is active
			if (account.getActive() == 1) {
				users.add(account);
			}
		}
		dbSelect.close();
		return activeAccounts;
	}

	public void serialize() throws BadInputException, java.sql.SQLException {
		Serializing.saveDatabase(context);
	}

	public void deserialize()
			throws DatabaseInsertException, java.sql.SQLException, BadInputException, ConnectionFailedException {
		Deserializing.loadDatabase(context);
	}
}
