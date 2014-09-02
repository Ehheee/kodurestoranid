package tests;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.data.neo4j.support.Neo4jTemplate;

import thething.kodurestoranid.dataobjects.ThingNode;
import thething.kodurestoranid.db.util.ThingNodeFilter;

public class A2 {
	static Neo4jTemplate template;
	
	public static void main(String[] args){
		ThingNode tn = new ThingNode();
		tn.setActive(true);
		tn.setName("domeen");
		Method[] methods = tn.getClass().getMethods();
		for(Method m: methods){
			try {
				if(m.getName().contains("get")){
					String field = m.getName().substring(3);
					field = Introspector.decapitalize(field);
					System.out.println(m.getName().indexOf("get"));
					System.out.println(m.getName());
					System.out.println(field);
					System.out.println(m.invoke(tn ));
					
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
		String[] fields;
		Class[] fieldTypes;
		Field[] fs = ThingNode.class.getDeclaredFields();
		fields = new String[fs.length];
		fieldTypes = new Class[fs.length];
		for(int i = 0; i < fields.length; i++){
			fields[i] = fs[i].getName();
			fieldTypes[i] = fs[i].getType();
		}
		for(String f: fields){
			System.out.println(f + "  -  " + fieldTypes[Arrays.asList(fields).indexOf(f)]);
		}
		StringBuilder query = new StringBuilder("select bla, bla, bla,");
		System.out.println(query.substring(0, query.length() -1));
		ThingNode node = new ThingNode();
		node.setName("ababa");
		node.setAuthor(42L);
		node.setText1("tetetetete");
		ThingNodeFilter of = new ThingNodeFilter();
		of.setThingNode(node);
		of.setReturnDepth(2);
		System.out.println(of.getQuery());
		
	
	}
}
