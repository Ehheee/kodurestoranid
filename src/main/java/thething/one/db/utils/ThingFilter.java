package thething.one.db.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import thething.utils.Tools;
/**
 * Class to represent a Filter that is used to create a query
 * for retrieving a Thing and along with it the full tree of Things
 * related to the root with outgoing relations.
 * relationDepth can be set in order to limit the levels of the tree.
 * @author Kaur
 *
 */
public class ThingFilter {

	private static Integer defaultRelationDepth = 100;
	private List<String> labels;
	private Integer relationDepth;
	private static String baseQuery = "MATCH (a#replaceLabel #replaceProperties)-[rr*..#relationDepth]->(bb) WITH distinct(bb) as b MATCH (b)<-[r]-(c) RETURN {labels: labels(c), data: c} as from, {relationType: type(r), data: r} as rel, {labels: labels(b), data: b} as to";
	private Map<String, Object> properties;
	
	public ThingFilter() {
		labels = new ArrayList<String>();
		properties = new HashMap<String, Object>();
	}
	
	public void setLabel(String label){ 
		if (!labels.isEmpty()) {
			labels = new ArrayList<String>();
		}
		labels.add(label);
	}
	
	public ThingFilter addLabel(String label) {
		labels.add(label);
		return this;
	}
	
	public String getQuery() {
		String query = baseQuery;
		if (labels.isEmpty()) {
			query = query.replace("#replaceLabel", "");
		} else {
			query = query.replace("#replaceLabel", Tools.stringFromLabels(getLabels()));
		}
		
		if (relationDepth == null) {
			relationDepth = defaultRelationDepth;
		}
		query = query.replace("#relationDepth", relationDepth.toString());
		query = replaceProperties(query);
		return query;
	}
	
	private String replaceProperties(String query){
		if (properties.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{ ");
			String comma = "";
			for (Entry<String, Object> e: properties.entrySet()) {
				sb.append(comma).append(e.getKey()).append(": {").append(e.getKey()).append("}");
				comma = ", ";
			}
			sb.append(" }");
			return query.replace("#replaceProperties", sb.toString());
		} else {
			return query.replace("#replaceProperties", "");
		}
	}
	
	
	
	public Object getProperty(String name) {
		return properties.get(name);
	}
	public void setProperty(String key, Object value) {
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
