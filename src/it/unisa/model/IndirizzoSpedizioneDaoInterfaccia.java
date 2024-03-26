package it.unisa.model;

import java.sql.SQLException;

public interface IndirizzoSpedizioneDaoInterfaccia {

	public void doSave(IndirizzoSpedizioneBean bean) throws SQLException;
	
	public IndirizzoSpedizioneBean doRetrieveByKey(String indirizzo, String cap) throws SQLException;
	
	public void doDelete(IndirizzoSpedizioneBean bean) throws SQLException;
}
