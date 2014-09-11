package thething.kodurestoranid.db.dataaccess;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.db.services.TypeDescriptorService;

public class ThingDao {

	private static final String createQuery = "CREATE (a#replaceLabel &props) ";
	private static final String updateQuery = "";
	private static final String deleteQuery = "";
	private static final String getQuery = "MATCH (a#replaceLabel {id: {1} }) RETURN a";
	
	public void create(Thing thing){
		String query = createQuery.replaceAll("#replaceLabel", stringFromLabels(thing.getLabels()));
	}
	
	public void update(Thing thing){
		
	}
	
	public void delete(Thing thing){
		
	}
	
	public void get(String id){
		String query = getQuery.replace("", parseId(id));
		jdbcTemplate.queryForRowSet(query, id);
	}
	
	private String parseId(String id){
		return typeDescriptorService.getLabelFromId(id);
	}

	@Autowired
	private TypeDescriptorService typeDescriptorService;
	public TypeDescriptorService getTypeDescriptorService() {
		return typeDescriptorService;
	}
	public void setTypeDescriptorService(TypeDescriptorService typeDescriptorService) {
		this.typeDescriptorService = typeDescriptorService;
	}

	private String stringFromLabels(List<String> labels){
		StringBuilder b = new StringBuilder("");
		for(String s: labels){
			b.append(":").append(s);
		}
		return b.toString();
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
