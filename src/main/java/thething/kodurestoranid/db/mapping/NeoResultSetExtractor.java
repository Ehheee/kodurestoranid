package thething.kodurestoranid.db.mapping;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.graphdb.Node;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.dataobjects.ThingRelation;
import thething.kodurestoranid.db.utils.NeoResultWrapper;

public class NeoResultSetExtractor implements ResultSetExtractor<NeoResultWrapper>{

	private final Log logger = LogFactory.getLog(getClass());
	private Map<String, Object> rootFilter;

	
	@SuppressWarnings("unchecked")
	public NeoResultWrapper extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		NeoResultWrapper wrapper = new NeoResultWrapper();
		while(rs.next()){
			final ResultSetMetaData meta = rs.getMetaData();
			
			Object column;
			final int cols = meta.getColumnCount();
			for (int col=1;col<=cols;col++) {
	            column = rs.getObject(col);
				if(column instanceof LinkedHashMap){
					LinkedHashMap<String, Object> m = (LinkedHashMap<String, Object> )column;
					if(m.get("labels") != null){
						Thing thing = createThing(m);
						
						wrapper.addThing(thing);
					}
					if(m.get("relationType") != null){
						ThingRelation relation = createRelation(m);
						wrapper.addRelation(relation);
					}
					printMap(m);
				}
	            
	        }
			
			
			
		}
		return wrapper;
	}
	 

	
	
	@SuppressWarnings("unchecked")
	private ThingRelation createRelation(LinkedHashMap<String, Object> m) {
		ThingRelation relation = new ThingRelation();
		relation.setType((String) m.get("relationType"));
		relation.setProperties((Map<String, Object>) m.get("data"));
		logger.info(relation);
		return relation;
	}



	@SuppressWarnings("unchecked")
	private Thing createThing(LinkedHashMap<String, Object> m) {
		Thing thing = new Thing();
		for(Entry<String, Object> entry: m.entrySet()){
			if(entry.getKey() == "labels"){
				if(entry.getValue() instanceof ArrayList){
					thing.setLabels((List<String>) entry.getValue());
				}
			}
			
			if(entry.getKey() == "data"){
				if(entry.getValue() instanceof LinkedHashMap){
					thing.setProperties((Map<String, Object>) entry.getValue());
				}
				
			}
		}
		logger.info(thing);
		return thing;
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
			  logger.info(e.getKey());
			  logger.info(e.getValue());
		  }
	  }




	public Map<String, Object> getRootFilter() {
		return rootFilter;
	}
	public void setRootFilter(Map<String, Object> rootFilter) {
		this.rootFilter = rootFilter;
	}
	  
	  
}
