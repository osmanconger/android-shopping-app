package com.b07.users;

import com.b07.enums.*;
import android.content.Context;


public class UserFactory {
	private Context context;

	public UserFactory(Context context) {
		this.context = context;
	}

	public User createUser(String role, int id, String name, int age, String address) {
		if (role.equals(Roles.ADMIN.name())) {
			return new Admin(context, id, name, age, address);
		} else if (role.equals(Roles.CUSTOMER.name())) {
			return new Customer(context, id, name, age, address);
		} else if (role.equals(Roles.EMPLOYEE.name())) {
			return new Employee(context, id, name, age, address);
		}
		return null;
	}
}