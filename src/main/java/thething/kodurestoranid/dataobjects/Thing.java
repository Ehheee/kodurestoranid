package thething.kodurestoranid.dataobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Thing {

	//Map of relations for current String. Relation type used as key.
	private Map<String, List<ThingRelation>> relations;
	//Labels are hierarchical so 0 index is the root and the rest follow in order
	private List<String> labels;
	private Map<String, Object> properties;

	
	public Thing(){
		properties = new HashMap<String, Object>();
		labels = new ArrayList<String>();
		relations = new HashMap<String, List<ThingRelation>>();
	}
	public Object getProperty(String name){
		return properties.get(name);
	}
	public void setProperty(String key, Object value){
		properties.put(key, value);
	}
	
	
	
	

	public Map<String, List<ThingRelation>> getRelations() {
		return relations;
	}
	public void setRelations(Map<String, List<ThingRelation>> relations) {
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
