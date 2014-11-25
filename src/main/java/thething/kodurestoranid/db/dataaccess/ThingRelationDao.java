package thething.kodurestoranid.db.dataaccess;

import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.dataobjects.ThingRelation;
import thething.kodurestoranid.db.utils.Tools;

@Component
public class ThingRelationDao extends BaseDao{

	private Log logger = LogFactory.getLog(getClass());
	
	private static final String createRelation = "MATCH (a#replaceLabel0 {id: &id0 }), (b#replaceLabel1 {id: &id1 }) CREATE (a)-[#relationLabel &properties ]->(b)";
	private static final String updateRelation = "";
	private static final String deleteRelation = "";
	private static final String deleteRelations = "";
	
	public void createRelations(Thing thing) {
		for (Entry<String, List<ThingRelation>> e: thing.getRelations().entrySet()) {
			for (ThingRelation relation: e.getValue()) {
				if (relation.getProperty("id") != null) {
					
				} else {
					relation.setProperty("id", this.uniqueIdProvider.getId("Relation"));
					createRelation(relation);
				}
			}
		}
	}
	
	public void createRelation(ThingRelation relation){
		String query = replaceLabels(relation);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		logger.info(relation.getFrom());
		logger.info(relation.getTo());
		paramSource.addValue("id0", relation.getFrom().getProperty("id"));
		paramSource.addValue("id1", relation.getTo().getProperty("id"));
		paramSource.addValue("properties", relation.getProperties());
		this.namedParameterJdbcTemplate.update(query, paramSource);
	}
	
	private String replaceLabels(ThingRelation relation){
		String query = createRelation.replace("#replaceLabel0", Tools.stringFromLabels(relation.getFrom().getLabels()));
		query = query.replace("#replaceLabel1", Tools.stringFromLabels(relation.getTo().getLabels()));
		query = query.replace("#relationLabel", ":"+relation.getType());
		return query;
	}
}
