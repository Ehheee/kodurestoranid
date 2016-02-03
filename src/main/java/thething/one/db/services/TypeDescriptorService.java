package thething.one.db.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import thething.exceptions.DatabaseException;
import thething.one.dataobjects.FieldDescriptor;
import thething.one.dataobjects.Thing;
import thething.one.dataobjects.ThingRelation;
import thething.one.dataobjects.ThingType;
import thething.one.db.dataaccess.ThingDao;
import thething.one.db.utils.ThingFilter;
import thething.utils.Tools;

@Component
public class TypeDescriptorService {

	private Log logger = LogFactory.getLog(getClass());
	
	private Map<String, ThingType> descriptions;
	private Map<String, String> labelsByIdPrefix;
	
	
	public TypeDescriptorService() {
		descriptions = new HashMap<String, ThingType>();
		labelsByIdPrefix = new HashMap<String, String>();
	}
	
	public ThingType getDescriptor(String type ) throws DatabaseException {
		if (descriptions.get(type) == null) {
			ThingFilter filter = new ThingFilter();
			filter.addLabel("System").addLabel("ThingType");
			filter.setProperty("typeName", type);
			Thing thing = thingDao.getThingByFilter(filter);
			if (thing != null) {
				descriptions.put(type, this.descriptorFromThing(thing));
			}
		}
		return descriptions.get(type);
	}

	
	public Thing thingFromDescriptor(ThingType thingType) {
		Thing typeThing = new Thing();
		typeThing.addLabel("System");
		typeThing.addLabel("ThingType");
		typeThing.setProperty("typeName", thingType.getTypeName());
		typeThing.setProperty("id", thingType.getId());
		
		Map<String, Set<ThingRelation>> thingRelations = new HashMap<>();
		Set<ThingRelation> fieldRelations = new HashSet<>();
		for (Entry<String, FieldDescriptor> e: thingType.getFields().entrySet()) {
			FieldDescriptor field = e.getValue();
			Thing fieldThing = new Thing();
			
			fieldThing.addLabel("System");
			fieldThing.addLabel("FieldDescriptor");
			
			fieldThing.setProperty("name", field.getName());
			fieldThing.setProperty("displayName", field.getDisplayName());
			fieldThing.setProperty("required", field.getRequired());
			fieldThing.setProperty("type", field.getType().getSimpleName());
			fieldThing.setProperty("id", field.getId());
			
			ThingRelation relation = new ThingRelation();
			relation.setFrom(typeThing);
			relation.setTo(fieldThing);
			relation.setType("hasField");
			fieldRelations.add(relation);
		}
		thingRelations.put("hasField", fieldRelations);
		typeThing.setRelationsOutgoing(thingRelations);
		return typeThing;
		
	}
	
	
	public ThingType descriptorFromThing(Thing thing) {
		ThingType thingType = new ThingType();
		if (thing.getLabels().get(0).equals("System") && thing.getLabels().get(1).equals("ThingType")) {
			thingType.setTypeName((String) thing.getProperty("typeName"));
		} else {
			logger.warn("Tried to create descriptor from invalid Thing: " + thing);
			return null;
		}
		thingType.setId((String) thing.getProperty("id"));
		Set<ThingRelation> fieldRelations = thing.getRelationsOutgoing().get("hasField");
		for (ThingRelation fieldRelation: fieldRelations) {
			FieldDescriptor field = new FieldDescriptor();
			Thing fieldThing = fieldRelation.getTo();
			
			Class <?> type = Tools.stringToClass((String) fieldThing.getProperty("type"));
			
			field.setType(type);
			field.setName((String) fieldThing.getProperty("name"));
			field.setDisplayName((String) fieldThing.getProperty("displayName"));
			field.setRequired((Boolean) fieldThing.getProperty("required"));
			field.setId((String) fieldThing.getProperty("id"));
			thingType.addField(field);
		}
		return thingType;
	}
	

	
	
	@Autowired
	private ThingDao thingDao;
	public ThingDao getThingDao() {
		return thingDao;
	}
	public void setThingDao(ThingDao thingDao) {
		this.thingDao = thingDao;
	}
	
}
