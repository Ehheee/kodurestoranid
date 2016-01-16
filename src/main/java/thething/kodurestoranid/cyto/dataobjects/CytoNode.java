package thething.kodurestoranid.cyto.dataobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thething.utils.Tools;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CytoNode {
	
	private Map<String, Object> data;
	
	public CytoNode() {
		data = new HashMap <>();
	}
	
	@SuppressWarnings("unchecked")
	@JsonIgnore
	public List<String> getLabels() {
		Object labels = data.get("labels");
		return labels == null ? null : (List<String>) labels;
	}
	public void setLabels(List<String> labels) {
		data.put("labels", labels);
	}
	
	
	
	@JsonIgnore
	public String getId() {
		return data.get("id").toString();
	}
	public void setId(String id) {
		data.put("id", id);
	}
	
	@JsonIgnore
	public String getSource() {
		Object source = data.get("source");
		return source == null ? null : source.toString();
	}
	public void setSource(String source) {
		data.put("source", source);
	}
	
	@JsonIgnore
	public String getTarget() {
		Object target = data.get("target");
		return target == null ? null : target.toString();
	}
	public void setTarget(String target) {
		data.put("target", target);
	}

	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	private void setDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append(getId()).append(";").append(Tools.stringFromLabels(getLabels()));
	}
	
}
