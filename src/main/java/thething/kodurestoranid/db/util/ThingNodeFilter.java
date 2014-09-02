package thething.kodurestoranid.db.util;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import thething.kodurestoranid.dataobjects.ThingNode;

public class ThingNodeFilter {

	/*
	select o1.id as o1Id, o1.name as o1Name, o1.type as o1Type, o1.text as o1Text, o2.id as o2Id, o2.name as o2Name, o2.type as o2Type, o2.text as o2Text, o3.id as o3Id, o3.name as o3Name, o3.type as o3Type, o3.text as o3Text from objects o1 
	left join objectRelations or1 on o1.id=or1.objectId or o1.id=or1.relationId 
	left join objects o2 on (or1.relationId=o2.id or or1.objectId=o2.id) and o2.id != o1.id 
	left join objectRelations or2 on o2.id = or2.objectId or o2.id = or2.relationId
	left join objects o3 on (or2.relationId=o3.id or or2.objectId=o3.id) and o3.id != o2.id and o3.id != o1.id
	where o1.id=1;
	*/
	
	
	private Log logger = LogFactory.getLog(getClass());
	private static String[] FIELDS = fillFields();										//Contains all the field names declared in the object; 
	
	
	/**
	 * Fills the fields, similar method could also be use to make this filter totally abstract
	 * @return
	 */
	private static String[] fillFields(){
		Field[] fs = ThingNode.class.getDeclaredFields();
		String[] fields = new String[fs.length];
		for(int i = 0; i < fields.length; i++){
			fields[i] = fs[i].getName();
		}
		return fields;
	}
	
	
	private ThingNode thingNode;
	protected MapSqlParameterSource paramSource;
	
	/**
	 * Creates new ObjectFilter and initialises the fields value as well.
	 */
	public ThingNodeFilter(){
		if(paramSource == null){
			paramSource = new MapSqlParameterSource();
		}
	}
	
	public ThingNodeFilter(ThingNode thingNode){
		this.thingNode = thingNode;
	}
	
	public String getQuery(){
		StringBuilder query = new StringBuilder("select");
		for(int i = 1;i <= returnDepth;i++ ){
			for(String f: FIELDS){
				query.append(" o" + i + "." + f + ',');
			}
		}
		query.setLength(query.length() -1);
		query.append(" from objects o1");
		for(int i = 1; i < returnDepth; i++){
			joinObjectRelations(query, i);
			joinObjects(query, i);
		}
		addFilters(query);
		return query.toString();
	}
	
	private void joinObjectRelations(StringBuilder query, int level){
		String join = " left join objectRelations or%1$d on o%1$d.id = or%1$d.objectId or o%1$d.id = or%1$d.relationId";
		query.append(String.format(join, level, level, level, level, level));
		
	}
	
	private void joinObjects(StringBuilder query, int level){
		String join = " left join objects o%2$d on (or%1$d.relationId=o%2$d.id or or%1$d.objectId=o%2$d.id)";
		String ands = " and o%1$d.id != o%2$d.id";
		query.append(String.format(join, level, level +1));
		for(int i = 0; i < level; ++i){
			query.append(String.format(ands, i+1, level+1));
		}
	}
	
	private void addFilters(StringBuilder query){
		Method[] methods = thingNode.getClass().getDeclaredMethods();
		String prepend = " ";
		query.append(" where");
		for(Method m: methods){
			if(m.getName().indexOf("get") == 0){
				String field = m.getName().substring(3);
				field = Introspector.decapitalize(field);
				if(Arrays.asList(FIELDS).contains(field)){
					try {
						Object value = m.invoke(thingNode);
						if(value != null){
							paramSource.addValue(field, value);
							query.append(prepend + field + " = :" + field);
							prepend = " AND ";
							
						}
					} catch (IllegalAccessException e) {
						logger.error("Error when adding filters: ",e);
					} catch (IllegalArgumentException e) {
						logger.error("Error when adding filters: ",e);
					} catch (InvocationTargetException e) {
						logger.error("Error when adding filters: ",e);
					}
				}
			}
		}
	}
	private int returnDepth;

	public int getReturnDepth() {
		return returnDepth;
	}

	public void setReturnDepth(int returnDepth) {
		this.returnDepth = returnDepth;
	}

	public ThingNode getThingNode() {
		return thingNode;
	}

	public void setThingNode(ThingNode thingNode) {
		this.thingNode = thingNode;
	}
	
	
}
