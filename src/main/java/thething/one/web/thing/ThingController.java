package thething.one.web.thing;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import thething.exceptions.DatabaseException;
import thething.one.dataobjects.Thing;
import thething.one.db.utils.ThingFilter;
import thething.one.web.BaseController;
import thething.utils.ThingTools;

@Controller
@RequestMapping("/thing")
public class ThingController extends BaseController {

	@MessageMapping("/thing/create")
	public void wsCreateThing(@Payload Map<String, Object> body) {
		logger.info("received thing1");
		if (body instanceof LinkedHashMap) {
			Thing t = ThingTools.createThing((LinkedHashMap<String, Object>)body);
			logger.debug("Converted to thing: " + t);
		}
	}
	@MessageMapping("/thing/get")
	public Object wsGetThing(@Payload Map<String, Object> request) throws DatabaseException {
		ThingFilter thingFilter = filterFromRequest(request);
		thingFilter.setLabels(ThingTools.getStringArray(request, "labels"));
		return this.createJsonResponse(thingFilter, ThingTools.getString(request, "responseFormat"));
	}
	@RequestMapping(value = "/json/get", method = RequestMethod.POST)
	@ResponseBody
	public Object getThing(@RequestBody Map<String, Object> request) throws DatabaseException {
		ThingFilter thingFilter = filterFromRequest(request);
		thingFilter.setLabels(ThingTools.getStringArray(request, "labels"));
		return this.createJsonResponse(thingFilter, ThingTools.getString(request, "responseFormat"));
	}
	@RequestMapping(value = "/json/update", method = RequestMethod.POST)
	@ResponseBody
	public Object updateThing(@RequestBody Map<String, Object> body) throws DatabaseException {
		return this.createOrUpdateThing(body);
	}
	
	@SuppressWarnings("unchecked")
	private ThingFilter filterFromRequest(Map<String, Object> request) {
		ThingFilter thingFilter = new ThingFilter();
		Object o = request.get("properties");
		if (o instanceof Map) {
			thingFilter.setProperties((Map<String, Object>) o);
		}
		return thingFilter;
	}
}
