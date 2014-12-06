package thething.kodurestoranid.dataobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Thing {

	//Things relations mapped by relations type.
	private Map<String, Set<ThingRelation>> relations;
	//Labels are hierarchical so 0 index is the root and the rest follow in order
	private List<String> labels;
	private Map<String, Object> properties;

	
	public Thing(){
		properties = new HashMap<String, Object>();
		labels = new ArrayList<String>();
		relations = new HashMap<String, Set<ThingRelation>>();
	}
	public Object getProperty(String name){
		return properties.get(name);
	}
	public void setProperty(String key, Object value){
		properties.put(key, value);
	}
	
	public String getId() {
		Object id = properties.get("id");
		if (id == null) {
			return null;
		} else {
			return (String) id;
		}
	}
	
	public void setId(String id) {
		properties.put("id", id);
	}
	
	
	

	public Map<String, Set<ThingRelation>> getRelations() {
		return relations;
	}
	public void setRelations(Map<String, Set<ThingRelation>> relations) {
		this.relations = relations;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public void addLabel(String label){
		this.labels.add(label);
	}
	public Map<String, Object> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	public void addRelation(ThingRelation relation) {
		if (relations.get(relation.getType()) == null) {
			relations.put(relation.getType(), new HashSet<ThingRelation>());
		}
		relations.get(relation.getType()).add(relation);
	}
	
	
	public String toString(){
	StringBuilder sb = new StringBuilder();
	sb.append("Thing");
	for(String label: labels){
		sb.append(":").append(label);
	}
	sb.append("{");
	for(Entry<String, Object> prop: properties.entrySet()){
		sb.append(prop.getKey()).append(": ").append(prop.getValue()).append(", ");
	}
	sb.append("}");
	return sb.toString();
	}
	
}
