package thething.kodurestoranid.db.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThingFilter {

	private static Integer defaultRelationDepth = 100;
	List<String> labels;
	Integer relationDepth;
	private static String baseQuery = "MATCH (a#replaceLabel {id: &id })-[rr*..#relationDepth]->(bb) WITH distinct(bb) as b MATCH (b)<-[r]-(c) RETURN c, {relationType: type(r), data: r} as rel, b";
	private Map<String, Object> properties;
	
	public ThingFilter(){
		labels = new ArrayList<String>();
		properties = new HashMap<String, Object>();
	}
	
	public void setLabel(String label){
		if(!labels.isEmpty()){
			labels = new ArrayList<String>();
		}
		labels.add(label);
	}
	
	public String getQuery(){
		//Adds an empty label if they are empty for replace to work
		if(labels.isEmpty()){
			labels.add("");
		}
		String query = baseQuery.replace("#replaceLabel", Tools.stringFromLabels(labels));
		if(relationDepth == null){
			relationDepth = defaultRelationDepth;
		}
		query = query.replace("#relationDepth", relationDepth.toString());
		return query;
	}
	
	
	
	public Object getProperty(String name){
		return properties.get(name);
	}
	public void setProperty(String key, Object value){
		properties.put(key, value);
	}

	
	
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	
	
	public Integer getRelationDepth() {
		return relationDepth;
	}
	public void setRelationDepth(Integer relationDepth) {
		this.relationDepth = relationDepth;
	}

	
	
	public Map<String, Object> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	
}
