package com.b07.store;

import java.util.ArrayList;
import java.util.List;
import com.b07.exceptions.BadInputException;

public class SalesLogImpl implements SalesLog {
	private List<Sale> sales = new ArrayList<>();

	public SalesLogImpl() {

	}

	public void addSale(Sale sale) {
		this.sales.add(sale);
	}

	public void removeSale(Sale sale) throws BadInputException {
		boolean removed;
		removed = this.sales.remove(sale);
		if (!removed) {
			throw new BadInputException();
		}
	}

	@Override
	public List<Sale> getSales(SalesLog salesLog) {
		return this.sales;
	}
}
