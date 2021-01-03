package com.b07.inventory;

import java.math.BigDecimal;

public class ItemImpl implements Item {
	private int id;
	private String name;
	private BigDecimal price;

	public ItemImpl(int id, String name, BigDecimal price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public BigDecimal getPrice() {
		return this.price;
	}

	@Override
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
