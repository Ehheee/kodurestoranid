package thething.one.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import thething.exceptions.DatabaseException;
import thething.one.db.dataaccess.ThingDao;
import thething.one.db.services.TypeDescriptorService;
import thething.one.db.utils.ThingFilter;
import thething.utils.ThingTools;


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
	
	protected Object createJsonResponse(HttpServletRequest request, HttpServletResponse response, ThingFilter filter) throws DatabaseException {
		String responseFormat = request.getParameter("responseFormat");
		if (responseFormat == null) {
			responseFormat = "thing";
		}
		return createJsonResponse(filter, responseFormat);
	}
	protected Object createJsonResponse(ThingFilter filter, String responseFormat) throws DatabaseException {
		
		Object responseObject = null;
		switch (responseFormat) {
		case "cyto" :
			responseObject = thingDao.getCytoWrapperByFilter(filter);
			break;
		case "resultWrapper" :
			responseObject = thingDao.getResultsByFilter(filter);
			break;
		case "thing" :
			responseObject = thingDao.getThingByFilter(filter);
			break;
			
		default :
			logger.warn("Illegal responseFormat queried: " + responseFormat);
		}
		return responseObject;
	}
	protected Object createOrUpdateThing(Map<String, Object> request) {
		Map<String, Object> response = new HashMap<String, Object>();
		logger.info("createOrUpdateThing: " + response);
		try {
			thingDao.createOrUpdateWithRelations(ThingTools.createThing((LinkedHashMap<String, Object>)request));
			response.put("status", "OK");
		} catch (Exception e) {
			logger.error(e);
			response.put("status", "error");
			response.put("error", e.getMessage());
		}
		return response;
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
