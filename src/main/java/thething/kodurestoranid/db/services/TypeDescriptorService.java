package thething.kodurestoranid.db.services;

import java.util.Map;

import thething.kodurestoranid.dataobjects.TypeDescriptor;

public class TypeDescriptorService {

	private Map<String, TypeDescriptor> descriptions;
	private Map<String, String> labelsByIdPrefix;
	
	
	
	public TypeDescriptor getDescriptor(String type){
		return descriptions.get(type);
	}
	
	public String getLabelFromId(String id){
		return labelsByIdPrefix.get(id.substring(0, 2));
		
	}
}
