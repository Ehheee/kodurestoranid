package thething.kodurestoranid.db.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import thething.kodurestoranid.dataobjects.ThingNode;

public class ThingNodeExtractor implements ResultSetExtractor<Map<String, ThingNode>>{

	public Map<String, ThingNode> extractData(ResultSet rs)
			throws SQLException, DataAccessException {
		
		
		return null;
	}

	
}
