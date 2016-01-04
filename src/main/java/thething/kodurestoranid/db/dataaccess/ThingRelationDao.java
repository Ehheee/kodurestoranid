package thething.kodurestoranid.db.dataaccess;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

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
	private static final String updateRelation = "MATCH (a#replaceLabel0 {id: &id0 })-[rel1:#relationLabel {id: &relationId}]->(b#replaceLabel1 {id: &id1 }) SET rel1 = &properties";
	private static final String deleteRelation = "MATCH (a#replaceLabel0 {id: &id0 })-[rel1:#relationLabel {id: &relationId}]->(b#replaceLabel1 {id: &id1 }) DELETE rel1";
	private static final String deleteRelations = "";
	
	public void createRelations(Thing thing) {
		for (Entry<String, Set<ThingRelation>> e: thing.getRelationsOutgoing().entrySet()) {
			for (ThingRelation relation: e.getValue()) {
				if (relation.getProperty("id") != null) {
					
				} else {
					relation.setProperty("id", this.uniqueIdProvider.getId("Relation"));
					createRelation(relation);
				}
			}
		}
	}
	
	public void updateRelation(ThingRelation relation) {
		String query = replaceLabels(relation, updateRelation);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("relationId", relation.getId());
		fillParamSource(paramSource, relation);
		//this.namedParameterJdbcTemplate.update(query, paramSource);
	}
	
	public void createRelation(ThingRelation relation){
		String query = replaceLabels(relation, createRelation);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		logger.info(relation.getFrom());
		logger.info(relation.getTo());
		fillParamSource(paramSource, relation);
		//this.namedParameterJdbcTemplate.update(query, paramSource);
	}
	
	public void deleteRelation(ThingRelation relation) {
		String query = replaceLabels(relation, deleteRelation);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("relationId", relation.getId());
		fillParamSource(paramSource, relation);
		//this.namedParameterJdbcTemplate.update(query, paramSource);
	}
	
	private void fillParamSource(MapSqlParameterSource paramSource, ThingRelation relation) {
		paramSource.addValue("id0", relation.getFrom().getId());
		paramSource.addValue("id1", relation.getTo().getId());
		paramSource.addValue("properties", relation.getProperties());
	}
	
	private String replaceLabels(ThingRelation relation, String query){
		query = query.replace("#replaceLabel0", Tools.stringFromLabels(relation.getFrom().getLabels()));
		query = query.replace("#replaceLabel1", Tools.stringFromLabels(relation.getTo().getLabels()));
		query = query.replace("#relationLabel", ":"+relation.getType());
		return query;
	}
}
