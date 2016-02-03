package thething.one.db.dataaccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.ogm.session.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import thething.exceptions.DatabaseException;
import thething.one.cyto.dataobjects.CytoNode;
import thething.one.cyto.dataobjects.CytoWrapper;
import thething.one.dataobjects.Thing;
import thething.one.dataobjects.ThingRelation;
import thething.one.db.mapping.NeoResultExtractor;
import thething.one.db.utils.NeoResultWrapper;
import thething.one.db.utils.ThingFilter;
import thething.utils.Tools;

@Component
public class ThingDaoImpl extends BaseDao implements ThingDao {

	private Log logger = LogFactory.getLog(getClass());
	
	private static final String createQuery = "CREATE (a#replaceLabel {properties}) ";
	private static final String updateQuery = "MATCH (a#replaceLabel {id: {id} }) SET a = {properties} ";
	private static final String deleteQuery = "MATCH (a#replaceLabel {id: {id} }) DELETE a";
	private static final String getQuery = "MATCH (a#replaceLabel {id: {id} }) RETURN {labels: labels(a), data: a} as node";
	private static final String getByLabelQuery = "MATCH (a#replaceLabel ) RETURN a";
	
	
	public ThingDaoImpl() {
	}
	
	
	/**
	 * Transactional method to wrap around recursive method. Otherwise each recursion would be a new transaction.
	 * The point is to rollback everything in case of errors.
	 * @param Thing thing
	 * @return the root Thing inserted
	 * @throws DatabaseException 
	 */
	public Thing createOrUpdateWithRelations(Thing thing) throws DatabaseException {
		return createOrUpdateWithRelations(thing, 10);
	}
	
	/**
	 * Attempt to create method for inserting a new @Thing while separately also inserting all the related things and relations.
	 * It is done recursively
	 * @param Thing thing
	 * @return the root Thing inserted
	 * @throws DatabaseException 
	 */
	public Thing createOrUpdateWithRelations(Thing thing, int depth) throws DatabaseException {
		for (Entry<String, Set<ThingRelation>> relationsEntry: thing.getRelationsOutgoing().entrySet()) {
			Set<ThingRelation> thingRelations = relationsEntry.getValue();
			for (ThingRelation relation: thingRelations) {
				createOrUpdateWithRelations(relation.getTo(), depth);
			}
		}
		if (thing.getProperty("id") != null) {
			update(thing);
		} else {
			create(thing);
		}
		thingRelationDao.createRelations(thing);
		return thing;
	}
	
	

	/**
	 * Retrieves id from uniqueIdProvider and inserts thing to database.
	 * Executes "CREATE (a#replaceLabel &properties)"
	 * @param thing
	 * @return inserted Thing that now has an Id.
	 * @throws DatabaseException 
	 * 
	 */
	public Thing create(Thing thing) throws DatabaseException {
		String query = this.replaceLabels(thing.getLabels(), createQuery);
		String id = uniqueIdProvider.getId(thing.getLabels());
		thing.setId(id);
		Map<String, Object> params = new HashMap<>();
		params.put("properties", thing.getProperties());
		Result r = this.neo4jOperations.query(query, params);
		logger.info(r.toString());
		return thing;
	}
	
	/**
	 * Executes "MATCH (a#replaceLabel {id: &id }) SET a = &properties "
	 * @param Thing thing
	 */
	public void update(Thing thing) {
		String query = this.replaceLabels(thing.getLabels(), updateQuery);
		Map<String, Object> params = new HashMap<>();
		params.put("id", thing.getProperty("id"));
		params.put("properties", thing.getProperties());
		this.neo4jOperations.query(query, params);
	}
	
	
	/**
	 * Executes "MATCH (a#replaceLabel {id: {1} }) DELETE a"
	 * @param Thing thing
	 */
	public void delete(Thing thing) {
		String query = this.replaceLabels(thing.getLabels(), deleteQuery);
		Map<String, Object> params = new HashMap<>();
		params.put("id", thing.getProperty("id"));
		this.neo4jOperations.query(query, params);
	}
	
	
	public Thing get(String id) throws DatabaseException {
		ThingFilter filter = new ThingFilter();
		filter.setProperty("id", id);
		return getThingByFilter(filter);
	}

	
	/**
	 * Returns the results by the root thing
	 * @throws DatabaseException 
	 */
	public Thing getThingByFilter(ThingFilter filter) throws DatabaseException {
		return this.getResultsByFilter(filter).getRoot();
	}
	
	
	/**
	 * Returns results in resultwrapper for easy access by id
	 * @throws DatabaseException 
	 * 
	 */
	public NeoResultWrapper getResultsByFilter(ThingFilter filter) throws DatabaseException {
		NeoResultExtractor extractor = new NeoResultExtractor();
		extractor.setRootFilter(filter.getProperties());
		logger.info(filter.getQuery());
		Result result = this.neo4jOperations.query(filter.getQuery(), filter.getProperties());
		return extractor.extractData(result);
	}
	
	/**
	 * Retruns results as CytoWrapper for network graphs
	 * @param filter
	 * @return
	 * @throws DatabaseException 
	 */
	public CytoWrapper getCytoWrapperByFilter(ThingFilter filter) throws DatabaseException {
		NeoResultWrapper resultWrapper = this.getResultsByFilter(filter);
		CytoWrapper cytoWrapper = new CytoWrapper();
		for (Thing thing: resultWrapper.getThings().values()) {
			CytoNode node = new CytoNode();
			node.getData().putAll(thing.getProperties());
			cytoWrapper.getNodes().add(node);
		}
		for (ThingRelation relation: resultWrapper.getRelations().values()) {
			CytoNode node = new CytoNode();
			node.getData().putAll(relation.getProperties());
			node.setSource(relation.getFrom().getId());
			node.setTarget(relation.getTo().getId());
			cytoWrapper.getEdges().add(node);
		}
		return cytoWrapper;
	}
	
	
	/**
	 * 
	 * @param labels
	 * @param query - query to put labels into
	 * @return
	 */
	private String replaceLabels(List<String> labels, String query) {
		if(labels.isEmpty()){
			return query.replace("#replaceLabel", "");
		}else{
			return query.replace("#replaceLabel", Tools.stringFromLabels(labels));
		}
	}
	
	
	private String replaceLabel(String label, String query) {
		if(label == null){
			return query.replace("#replaceLabel", "");
		}else{
			label = ":" + label;
			return query.replace("#replaceLabel", label);
		}	
	}
	
	@Autowired
	private ThingRelationDao thingRelationDao;
	@Autowired
	private Neo4jOperations neo4jOperations;
}
