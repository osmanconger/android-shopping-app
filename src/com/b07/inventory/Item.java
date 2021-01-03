package com.b07.inventory;

import java.math.BigDecimal;

public interface Item {
	public int getId();

	public void setId(int id);

	public String getName();

	public void setName(String name);

	public BigDecimal getPrice();

	public void setPrice(BigDecimal price);
}
