package thething.kodurestoranid.db.dataaccess;

import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;

import thething.kodurestoranid.dataobjects.Thing;

public class NeoDao {
	
	private static String insertQuery = "CREATE (n#replaceLabel {props})";
	
	
	public String saveNode(Thing thing){
		String query = insertQuery.replaceAll("#replaceLabel", stringFromLabels(thing.getLabels()));
		
		engine.execute(query, thing.getProperties());
		return null;
	}
	
	private String stringFromLabels(List<String> labels){
		StringBuilder b = new StringBuilder("");
		for(String s: labels){
			b.append(":").append(s);
		}
		return b.toString();
	}
	
	
	@Autowired
	GraphDatabaseService graphDb;
	ExecutionEngine engine;
	public GraphDatabaseService getGraphDb() {
		return graphDb;
	}
	public void setGraphDb(GraphDatabaseService graphDb) {
		this.graphDb = graphDb;
		this.engine = new ExecutionEngine(graphDb);
	}
	
}
