package thething.kodurestoranid.db.dataaccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.ogm.session.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Component;

import thething.exceptions.DatabaseException;

/**
 * Class for providing unique ids to neo4j nodes. Ids provided are in string form and also contains the type string for the possible case when
 * it is neccessary to find out the type of node before querying the database.
 * @author Kaur
 *
 */
@Component
public class UniqueIdProvider {

	private Log logger = LogFactory.getLog(getClass());
	
	private static String query = "MERGE (id:UniqueId {name: {name}, str: {str} }) "
								+"ON CREATE SET id.count = 1 "
								+"ON MATCH SET id.count = id.count + 1 "
								+"RETURN id.str + id.count AS uid ";
	
	
	
	public String getId() throws DatabaseException {
		Map<String, Object> params = new HashMap<>();
		params.put("name", "System");
		params.put("str", "s");
		Result r = this.neo4jOperations.query(query, params);
		return extractId(r);
	}
	
	/**
	 * Creates id by using a sequence and the first 2 chars of each label.
	 * @param labels
	 * @return
	 * @throws DatabaseException 
	 */
	public String getId(List<String> labels) throws DatabaseException {
		StringBuilder prefix = new StringBuilder();
		for (String label: labels) {
			prefix.append(label.substring(0, 2));
		}
		String name = labels.get(labels.size()-1);
		String str = prefix.toString();
		Map<String, Object> params = new HashMap<>();
		params.put("name", name);
		params.put("str", str);
		Result r = this.neo4jOperations.query(query, params);
		return extractId(r);
	}
	/**
	 * Creates id using a sequence and 2 first chars of the provided string.
	 * Results in the form %s%s%d+
	 * @param type
	 * @return
	 * @throws DatabaseException 
	 */
	public String getId(String type) throws DatabaseException {
		Map<String, Object> params = new HashMap<>();
		params.put("name", type);
		params.put("str", type.substring(0, 2));
		Result r = this.neo4jOperations.query(query, params);
		return extractId(r);
	}
	
	
	private String extractId(Result r) throws DatabaseException {
		Iterator<Map<String, Object>> iterator = r.iterator();
		if (iterator.hasNext()) {
			Object o = iterator.next().get("uid");
			if (o instanceof String) {
				return (String) o;
			}
			
		}
		throw new DatabaseException ();
	}
	
	
	
	
	
	private Neo4jOperations neo4jOperations;	
	@Autowired
	public void setNeo4jOperations(Neo4jOperations neo4jOperations) {
		this.neo4jOperations = neo4jOperations;
	}
}
