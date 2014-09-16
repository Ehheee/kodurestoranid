package thething.kodurestoranid.db.utils;

import java.util.Map;
import java.util.TreeMap;

import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.dataobjects.ThingRelation;

public class ExecutionResult {

	Map<String, Thing> things;
	Map<String, ThingRelation> relations;
	
	
	public ExecutionResult(){
		things = new TreeMap<String, Thing>();
		relations = new TreeMap<String, ThingRelation>();
	}
	
	public void addThing(Thing thing){
		things.put((String)thing.getProperty("id"), thing);
	}
	
	public void addRelation(ThingRelation relation){
		relations.put((String)relation.getProperty("id"), relation);
	}
	
	public Map<String, Thing> getThings() {
		return things;
	}
	public void setThings(Map<String, Thing> things) {
		this.things = things;
	}
	public Map<String, ThingRelation> getRelations() {
		return relations;
	}
	public void setRelations(Map<String, ThingRelation> relations) {
		this.relations = relations;
	}
	
	
}
