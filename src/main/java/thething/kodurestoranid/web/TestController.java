package thething.kodurestoranid.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController extends BaseController {

	@RequestMapping(value = "/test")
	public void test(HttpServletResponse response) throws IOException{
		response.getWriter().write("it works");
	}
}
