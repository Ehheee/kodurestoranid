package thething.kodurestoranid.db.utils;

import java.util.Map;
import java.util.TreeMap;

import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.dataobjects.ThingRelation;

/**
 * Wrap database results in a nice wrapper so it would be possible to get objects by id
 * @author Kaur
 *
 */
public class NeoResultWrapper {

	//Things mapped by id
	private Map<String, Thing> things;
	//Relations mapped by id
	private Map<String, ThingRelation> relations;
	private Thing root;
	
	public NeoResultWrapper(){
		things = new TreeMap<String, Thing>();
		relations = new TreeMap<String, ThingRelation>();
	}
	

	public Thing getRoot() {
		return root;
	}
	public void setRoot(Thing root) {
		this.root = root;
	}

	public Thing addThing(Thing thing){
		if (!things.containsKey(thing.getId())) {
			things.put(thing.getId(), thing);
			return thing;
		} else {
			return things.get(thing.getId());
		}
		
	}
	public ThingRelation addRelation(ThingRelation relation){
		if (!relations.containsKey(relation.getId())) {
			relations.put(relation.getId(), relation);
			return relation;
		} else {
			return relations.get(relation.getId());
		}
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
