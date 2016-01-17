package thething.one.web;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import thething.one.dataobjects.Thing;
import thething.one.db.dataaccess.ThingDao;
import thething.one.db.services.TypeDescriptorService;


public class BaseController {
	protected Log logger = LogFactory.getLog(getClass());
	

	protected final static ObjectMapper mapper = new ObjectMapper();
	
	
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
	
	protected void sendError(HttpServletResponse response, int error) {
		try {
			response.sendError(error);
		} catch (IOException e) {
			logger.warn(e);
		}
	}
	
	/*
	protected Thing jsonToThing(String json) throws JsonProcessingException, IOException {
		JsonNode jsonNode = mapper.readTree(json);
		
	}
	*/
	@Autowired
	protected ThingDao thingDao;
	
	@Autowired
	protected TypeDescriptorService typeDescriptorService;
}
