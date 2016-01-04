package tests;

import org.apache.commons.dbcp.BasicDataSource;

import thething.kodurestoranid.db.dataaccess.ThingDao;
import thething.kodurestoranid.db.dataaccess.ThingDaoImpl;
import thething.kodurestoranid.db.utils.ThingFilter;

public class A3 {

	
	public static void main(String[] args){
		BasicDataSource ds2 = new BasicDataSource();
		ds2.setDriverClassName("org.neo4j.jdbc.Driver");
		ds2.setUrl("jdbc:neo4j://localhost:7474/?debug=true");
		ds2.setMaxActive(10);
		ds2.setMaxIdle(5);
		ds2.setInitialSize(5);
		
		
		ThingDaoImpl thingDao = new ThingDaoImpl();
		thingDao.setDataSource(ds2);
		ThingFilter filter = new ThingFilter();
		filter.setProperty("id", "Sy3");
		System.out.println(filter.getQuery());
		
		thingDao.getThingByFilter(filter);
	}
}
