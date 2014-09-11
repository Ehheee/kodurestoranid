package tests;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.db.mapping.NeoThingMapper;

public class EmbeddedNeo4j
{
	private Log logger = LogFactory.getLog(getClass());
	private static String insert = "MATCH (n:System:Domain { name: 'kokad' })"+
			"CREATE (n2:System:Interface:Container { name: 'main view kokkadele', id: '2' }),(n)-[:hasView]->(n2)";
	private static String insert2 = "CREATE (n:{labels} {props})";
	private static String select = "MATCH (n:System) RETURN n";
	private static String selectAll = "MATCH (n)-[r]-() return *";
    private static final String DB_PATH = "c:\\neo4j";

    public String greeting;

    // START SNIPPET: vars
    GraphDatabaseService graphDb;
    Node firstNode;
    Node secondNode;
    Relationship relationship;
    // END SNIPPET: vars

    // START SNIPPET: createReltype
    private static enum RelTypes implements RelationshipType
    {
        KNOWS
    }
    // END SNIPPET: createReltype

    public static void main( final String[] args )
    {
        EmbeddedNeo4j hello = new EmbeddedNeo4j();
        hello.createDb();
        hello.removeData();
        hello.shutDown();
    }

    void createDb()
    {
        // START SNIPPET: startDb
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
        ExecutionEngine engine = new ExecutionEngine( graphDb );
        NeoThingMapper mapper = new NeoThingMapper();
        // END SNIPPET: startDb
        // START SNIPPET: transaction
        try ( Transaction tx = graphDb.beginTx() )
        {
        	Map<String, Object> params = new HashMap<String, Object>();
        	Map<String, Object> props = new HashMap<String, Object>();
        	props.put("name", "paramsInsertTest");
        	params.put("props", props);
        	params.put("labels", "System:Test");
        	
        	ExecutionResult response = engine.execute(selectAll);
        	Map<String, Object> mapThings = mapper.mapThings(response);
        	
            tx.success();
        }
        // END SNIPPET: transaction
    }

    void removeData()
    {
        try ( Transaction tx = graphDb.beginTx() )
        {
            // START SNIPPET: removingData
            // let's remove the data
            // END SNIPPET: removingData

            tx.success();
        }
    }

    void shutDown()
    {
        System.out.println();
        System.out.println( "Shutting down database ..." );
        // START SNIPPET: shutdownServer
        graphDb.shutdown();
        // END SNIPPET: shutdownServer
    }

    // START SNIPPET: shutdownHook
    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }
    // END SNIPPET: shutdownHook

    private static void deleteFileOrDirectory( File file )
    {
        if ( file.exists() )
        {
            if ( file.isDirectory() )
            {
                for ( File child : file.listFiles() )
                {
                    deleteFileOrDirectory( child );
                }
            }
            file.delete();
        }
    }
}