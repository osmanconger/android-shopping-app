package com.b07.store;

import java.math.BigDecimal;
import java.util.HashMap;
import com.b07.inventory.Item;
import com.b07.users.User;

public interface Sale {
  public int getId();

  public void setId(int id);

  public User getUser();

  public void setUser(User user);

  public BigDecimal getTotalPrice();

  public void setTotalPrice(BigDecimal price);

  public HashMap<Item, Integer> getItemMap();

  public void setItemMap(HashMap<Item, Integer> itemMap);
}
