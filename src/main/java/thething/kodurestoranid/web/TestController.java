package thething.kodurestoranid.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController extends BaseController {

	@ResponseBody
	@RequestMapping(value = "/test")
	public Map<String, Object> test(HttpServletResponse response) throws IOException{
		Map<String, Object> result = new HashMap<>();
		result.put("this", "is cool");
		result.put("and that", "is too");
		return result;
	}
}
