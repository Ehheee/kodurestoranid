package thething.one.db.mapping;

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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import thething.one.dataobjects.Thing;
import thething.one.dataobjects.ThingRelation;
import thething.one.db.utils.NeoResultWrapper;

public class NeoResultSetExtractor implements ResultSetExtractor<NeoResultWrapper>{

	private final Log logger = LogFactory.getLog(getClass());
	private Map<String, Object> rootFilter;

	
	public NeoResultWrapper extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		NeoResultWrapper wrapper = new NeoResultWrapper();
		boolean rootFound = false;
		while(rs.next()){
			Thing from = createThing(rs.getObject("from"));
			Thing to = createThing(rs.getObject("to"));
			ThingRelation relation = createRelation(rs.getObject("rel"));
			from = wrapper.addThing(from);
			to = wrapper.addThing(to);
			relation.setFrom(from);
			relation.setTo(to);
			from.addRelation(relation);
			to.addRelation(relation);
			wrapper.addRelation(relation);
			if (!rootFound) {
				rootFound = checkRoot(from);
				if (rootFound) {
					wrapper.setRoot(from);
				}
			}
			
			
		}
		return wrapper;
	}
	 
	private boolean checkRoot(Thing thing) {
		for (Entry<String, Object> prop: rootFilter.entrySet()) {
			if (thing.getProperty(prop.getKey()) != null && thing.getProperty(prop.getKey()).equals(prop.getValue())) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
	
	
	@SuppressWarnings("unchecked")
	private Thing createThing(Object object) {
		if (object instanceof LinkedHashMap) {
			return createThing((LinkedHashMap<String, Object> )object);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private ThingRelation createRelation(Object object) {
		if (object instanceof LinkedHashMap) {
			return createRelation((LinkedHashMap<String, Object> )object);
		}
		return null;
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
		for (Entry<String, Object> entry: m.entrySet()) {
			if (entry.getKey() == "labels" ){
				if (entry.getValue() instanceof ArrayList) {
					thing.setLabels((List<String>) entry.getValue());
				}
			}
			
			if (entry.getKey() == "data") {
				if (entry.getValue() instanceof LinkedHashMap) {
					//thing.setProperties((Map<String, Object>) entry.getValue());
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
