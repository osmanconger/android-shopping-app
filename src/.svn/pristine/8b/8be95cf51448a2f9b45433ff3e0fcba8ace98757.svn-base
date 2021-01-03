package com.b07.users;

import java.sql.SQLException;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.security.PasswordHelpers;
import android.content.Context;

public abstract class User {
  // TODO: Complete this class based on UML provided on the assignment sheet.
  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private boolean authenticated;
  private Context context;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return this.age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getRoleId() {
    return this.roleId;
  }

  public final boolean authenticate(String password) throws SQLException {
	DatabaseSelectHelper dbSelect = new DatabaseSelectHelper(context);
    String passwordDb = dbSelect.getPassword(this.id);
    dbSelect.close();
    return PasswordHelpers.comparePassword(passwordDb, password);
  }
}
