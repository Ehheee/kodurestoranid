package thething.kodurestoranid.db.mapping;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.Node;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class NeoResultSetExtractor implements ResultSetExtractor{

	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		
		while(rs.next()){
			final ResultSetMetaData meta = rs.getMetaData();
			dumpColumns(rs);
			Object o;
			final int cols = meta.getColumnCount();
	        for (int col=1;col<=cols;col++) {
	            o = rs.getObject(col);
	            System.out.println(o);
	            System.out.println(o.getClass());
	            if(o instanceof Node){
					Node n = (Node)o;
					System.out.println(n.getLabels());
					System.out.println(n.getRelationships());
				}
				if(o instanceof LinkedHashMap){
					LinkedHashMap<String, Object> m = (LinkedHashMap<String, Object> )o;
					
					//printMap(m);
				}
	            
	        }
			
			
			
		}
		return null;
	}
	
	
	  protected void dumpColumns(ResultSet rs) throws SQLException {
	        final ResultSetMetaData meta = rs.getMetaData();
	        final int cols = meta.getColumnCount();
	        for (int col=1;col<=cols;col++) {
	            System.out.println(meta.getColumnName(col));
	        }
	    }

	  protected void printMap(Map<String, Object> map){
		  for(Entry<String, Object> e: map.entrySet()){
			  System.out.println(e.getKey());
			  System.out.println(e.getValue());
		  }
	  }
}
