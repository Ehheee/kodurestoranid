package thething.kodurestoranid.db.dataaccess;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.db.mapping.NeoResultSetExtractor;
import thething.kodurestoranid.db.services.TypeDescriptorService;
import thething.kodurestoranid.db.utils.ThingFilter;
import thething.kodurestoranid.db.utils.Tools;

public class ThingDao {

	private static final String createQuery = "CREATE (a#replaceLabel {1}) ";
	private static final String updateQuery = "";
	private static final String deleteQuery = "MATCH (a#replaceLabel {id: {1} }) DELETE a";
	private static final String getQuery = "MATCH (a#replaceLabel {id: {1} }) RETURN a";
	private static final String getByLabelQuery = "MATCH (a#replaceLabel ) RETURN a";
	
	NeoResultSetExtractor extractor;
	
	public ThingDao(){
		extractor = new NeoResultSetExtractor();
	}
	
	public String create(Thing thing){
		String query = createQuery.replace("#replaceLabel", Tools.stringFromLabels(thing.getLabels()));
		String id = uniqueIdProvider.getId(thing.getLabels().get(0));
		thing.setProperty("id", id);
		jdbcTemplate.update(query, thing.getProperties());
		return id;
	}
	
	
	public void update(Thing thing){
		//TODO
	}
	
	
	
	public void delete(Thing thing){
		String query = this.replaceLabel(thing.getLabels(), deleteQuery);
		jdbcTemplate.update(query, thing.getProperty("id"));
	}
	
	public void get(String id){
		String query = this.replaceLabel(Collections.<String>emptyList(), typeDescriptorService.getLabelFromId(id));
		jdbcTemplate.queryForRowSet(query, id);
	}
	
	public void getByLabel(String label){
		String query = getByLabelQuery.replace("#replaceLabel", label);Z
		jdbcTemplate.query(query, extractor);
	}
	
	
	public void get(ThingFilter filter){
		filter.getQuery();
		MapSqlParameterSource paramSource = new MapSqlParameterSource(filter.getProperties());
		namedParameterJdbcTemplate.query(filter.getQuery(), paramSource, extractor);
	}
	
	
	/**
	 * 
	 * @param labels
	 * @param query - query to put labels into
	 * @return
	 */
	private String replaceLabel(List<String> labels, String query){
		if(labels.isEmpty()){
			return query.replace("#replaceLabel", "");
		}else{
			return query.replace("#replaceLabel", Tools.stringFromLabels(labels));
		}
	}
	
	

	
	
	
	
	
	
	
	
	
	@Autowired
	private TypeDescriptorService typeDescriptorService;
	public TypeDescriptorService getTypeDescriptorService() {
		return typeDescriptorService;
	}
	public void setTypeDescriptorService(TypeDescriptorService typeDescriptorService) {
		this.typeDescriptorService = typeDescriptorService;
	}


	
	
	@Autowired
	UniqueIdProvider uniqueIdProvider;
	public UniqueIdProvider getUniqueIdProvider() {
		return uniqueIdProvider;
	}
	public void setUniqueIdProvider(UniqueIdProvider uniqueIdProvider) {
		this.uniqueIdProvider = uniqueIdProvider;
	}

	
	@Autowired
	private BasicDataSource dataSource;
	protected JdbcTemplate jdbcTemplate;
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void setDataSource(DataSource dataSource) {
	    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
