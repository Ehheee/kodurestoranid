package thething.kodurestoranid.db.services;

import java.util.Map;

import thething.kodurestoranid.dataobjects.TypeDescriptor;

public class TypeDescriptorService {

	private Map<String, TypeDescriptor> descriptions;
	
	
	
	public TypeDescriptor getDescriptor(String type){
		return descriptions.get(type);
	}
}
