package com.b07.users;

import android.content.Context;

public class Customer extends User {
	private int id;
	private String name;
	private int age;
	private String address;
	private int roleId;
	private boolean authenticated;
	private Context context;

	public Customer(Context context, int id, String name, int age, String address) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
		this.context = context;

	}

	public Customer(Context context, int id, String name, int age, String address, boolean authenticated) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
		this.context = context;
	}
}
