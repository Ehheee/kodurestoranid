package tests;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

public class AbstractMapper<K> {
	
	private Class<K> type;
	
	public AbstractMapper(Class<K> type) {
		this.type = type;
	}

	
}
