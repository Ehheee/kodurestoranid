package thething.kodurestoranid.db.dataaccess;

import java.util.List;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.dataobjects.ThingRelation;
import thething.kodurestoranid.db.mapping.NeoResultSetExtractor;
import thething.kodurestoranid.db.services.TypeDescriptorService;
import thething.kodurestoranid.db.utils.ThingFilter;
import thething.kodurestoranid.db.utils.Tools;

@Component
public class ThingDaoImpl extends BaseDao implements ThingDao {

	private Log logger = LogFactory.getLog(getClass());
	
	private static final String createQuery = "CREATE (a#replaceLabel &properties) ";
	private static final String updateQuery = "MATCH (a#replaceLabel {id: &id }) SET a = &properties ";
	private static final String deleteQuery = "MATCH (a#replaceLabel {id: {1} }) DELETE a";
	private static final String getQuery = "MATCH (a#replaceLabel {id: {1} }) RETURN {labels: labels(a), data: a} as node";
	private static final String getByLabelQuery = "MATCH (a#replaceLabel ) RETURN a";
	
	
	public ThingDaoImpl(){
	}
	
	
	/**
	 * Transactional method to wrap around recursive method. Otherwise each recursion would be a new transaction.
	 * The point is to rollback everything in case of errors.
	 */
	public Thing createOrUpdateWithRelations(Thing thing){
		createOrUpdateWithRelations(thing, 10);
		return thing;
	}
	
	/**
	 * Attempt to create method for inserting a new Thing while separately also inserting all the related things and relations.
	 * It is done recursively
	 * @param thing
	 * @return
	 */
	public Thing createOrUpdateWithRelations(Thing thing, int depth) {
		for (Entry<String, List<ThingRelation>> relationsEntry: thing.getRelations().entrySet()) {
			List<ThingRelation> thingRelations = relationsEntry.getValue();
			for (ThingRelation relation: thingRelations) {
				createOrUpdateWithRelations(relation.getTo(), depth);
			}
		}
		if (thing.getProperty("id") != null) {
			update(thing);
		} else {
			thing = create(thing);
		}
		thingRelationDao.createRelations(thing);
		return null;
	}
	
	

	
	public Thing create(Thing thing) {
		String query = this.replaceLabels(thing.getLabels(), createQuery);
		String id = uniqueIdProvider.getId(thing.getLabels().get(0));
		thing.setProperty("id", id);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("properties", thing.getProperties());
		this.namedParameterJdbcTemplate.update(query, paramSource);
		return thing;
	}
	
	
	public void update(Thing thing){
		String query = this.replaceLabels(thing.getLabels(), updateQuery);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("properties", thing.getProperties());
		paramSource.addValue("id", thing.getProperty("id"));
		this.namedParameterJdbcTemplate.update(query, paramSource);
	}
	
	
	
	public void delete(Thing thing){
		String query = this.replaceLabels(thing.getLabels(), deleteQuery);
		jdbcTemplate.update(query, thing.getProperty("id"));
	}
	
	public Thing get(String id){
		ThingFilter filter = new ThingFilter();
		filter.setProperty("id", id);
		return getByFilter(filter);
	}

	
	public Thing getByFilter(ThingFilter filter){
		MapSqlParameterSource paramSource = new MapSqlParameterSource(filter.getProperties());
		NeoResultSetExtractor extractor = new NeoResultSetExtractor();
		extractor.setRootFilter(filter.getProperties());
		this.namedParameterJdbcTemplate.query(filter.getQuery(), paramSource, extractor);
		return null;
	}
	
	
	/**
	 * 
	 * @param labels
	 * @param query - query to put labels into
	 * @return
	 */
	private String replaceLabels(List<String> labels, String query){
		if(labels.isEmpty()){
			return query.replace("#replaceLabel", "");
		}else{
			return query.replace("#replaceLabel", Tools.stringFromLabels(labels));
		}
	}
	
	
	private String replaceLabel(String label, String query){
		if(label == null){
			return query.replace("#replaceLabel", "");
		}else{
			label = ":" + label;
			return query.replace("#replaceLabel", label);
		}	
	}
	
	@Autowired
	private ThingRelationDao thingRelationDao;
	
}
