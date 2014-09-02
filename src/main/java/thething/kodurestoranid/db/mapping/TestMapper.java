package thething.kodurestoranid.db.mapping;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class TestMapper<T extends Object> {
	
	private T type;
	
	public TestMapper(T type){
		this.type = type;
	}

	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		Map<String, String> resultInfo = new HashMap<String, String>();
        for (int i = 1, n = meta.getColumnCount() + 1; i < n; i++){
        	String columnName = meta.getColumnName(i);
        	String tableName = meta.getTableName(i);
        	resultInfo.put(tableName, columnName);
        
        	
        }
        
		return null;
	}

}
