package thething.one.db.mapping;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.ogm.session.result.Result;

import thething.exceptions.DatabaseException;
import thething.one.dataobjects.Thing;
import thething.one.dataobjects.ThingRelation;
import thething.one.db.utils.NeoResultWrapper;
import thething.utils.ThingTools;

public class NeoResultExtractor {

	private final Log logger = LogFactory.getLog(getClass());
	private Map<String, Object> rootFilter;
	
	public NeoResultWrapper extractData(Result result) throws DatabaseException {
		NeoResultWrapper wrapper = new NeoResultWrapper();
		boolean rootFound = false;
		Iterator<Map<String, Object>> iterator = result.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = iterator.next();
			Thing from = createThing(row.get("from"));
			Thing to = createThing(row.get("to"));
			ThingRelation relation = createRelation(row.get("rel"));
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
			return ThingTools.createThing((LinkedHashMap<String, Object> )object);
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
	public Map<String, Object> getRootFilter() {
		return rootFilter;
	}
	public void setRootFilter(Map<String, Object> rootFilter) {
		this.rootFilter = rootFilter;
	}
	
	
}
