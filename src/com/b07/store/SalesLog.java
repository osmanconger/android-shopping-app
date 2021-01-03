package com.b07.store;

import java.util.List;
import com.b07.exceptions.BadInputException;

public interface SalesLog {
	public void addSale(Sale sale);

	public void removeSale(Sale sale) throws BadInputException;

	public List<Sale> getSales(SalesLog salesLog);
}
