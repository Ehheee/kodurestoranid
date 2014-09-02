package thething.kodurestoranid.db.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.helpers.collection.Iterables;

import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.dataobjects.ThingRelation;

public class NeoThingMapper {

	protected Log logger = LogFactory.getLog(getClass());
	
	
	
	public Map<String, Object> mapThings(ExecutionResult resultSet){
		logger.info("bla");
		Map<String, Object> things = new HashMap<String, Object>();
		ResourceIterator<Map<String, Object>> a = resultSet.iterator();
		while(a.hasNext()){
			for(Entry<String, Object> e: a.next().entrySet()){
				logger.info(e.getValue().getClass());
				if(e.getValue() instanceof Node){
					Node node = (Node)e.getValue();
					logger.info(node.getLabels());
					Thing thing = new Thing();
					for(Label l: node.getLabels()){
						thing.addLabel(l.name());
					}
					for(String key: node.getPropertyKeys()){
						
						thing.setProperty(key, node.getProperty(key));
						logger.info(key + "   -     " + thing.getProperty(key));
					}
					things.put((String) thing.getProperty("id"), thing);
				}
				
				
				else if(e.getValue() instanceof Relationship){
					Relationship relationship = (Relationship)e.getValue();
					ThingRelation relation = new ThingRelation();
					relation.setName(relationship.getType().name());
					logger.info(relation.getName());
					for(String key: relationship.getPropertyKeys()){
						
						relation.setProperty(key, relationship.getProperty(key));
						logger.info(key + "   -     " + relation.getProperty(key));
					}
					things.put((String) relation.getProperty("id"), relation);
				}
				
				
				
			}
		}
		
		return null;
		
	}
}
