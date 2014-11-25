package thething.kodurestoranid.web;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thething.kodurestoranid.db.dataaccess.ThingDao;
import thething.kodurestoranid.db.dataaccess.ThingDaoImpl;
import thething.kodurestoranid.db.services.TypeDescriptorService;

public class BaseController {
	protected Log logger = LogFactory.getLog(getClass());
	
	
	
	
	protected String printRequestparams(Map<String, String[]> params){
		StringBuilder sb = new StringBuilder("RequestParams: { ");
		for(Entry<String, String[]> e: params.entrySet()){
			sb.append(e.getKey());
			String[] values = e.getValue();
			if(values != null && values.length > 0){
				String comma = "";
				sb.append(" : [ ");
				for(String s: values){
					sb.append(comma).append(s);
					comma = ", ";
				}
				sb.append(" ] ");
			}
		}
		sb.append(" }");
		return sb.toString();
	}
	
	
	@Autowired
	protected ThingDao thingDao;
	
	@Autowired
	protected TypeDescriptorService typeDescriptorService;
}
