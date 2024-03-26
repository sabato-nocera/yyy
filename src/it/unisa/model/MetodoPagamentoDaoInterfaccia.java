package it.unisa.model;

import java.sql.SQLException;

public interface MetodoPagamentoDaoInterfaccia {

	public void doSave(MetodoPagamentoBean bean) throws SQLException;
	
	public MetodoPagamentoBean doRetrieveByKey(String numeroCarta) throws SQLException;
	
	public void doDelete(MetodoPagamentoBean bean) throws SQLException;
}
