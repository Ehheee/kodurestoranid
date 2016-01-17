package thething.one.db.utils;

import javax.servlet.http.HttpServletRequest;

import thething.utils.Tools;

public class RequestWrapper {

	private HttpServletRequest request;
	
	public RequestWrapper(HttpServletRequest request) {
		this.request = request;
	}
	
	public int[] getIntValues(String key) {
		String[] values = request.getParameterValues(key);
		int[] result = new int[values.length];
		for (int i = 0; i < values.length; i++) {
			result[i] = Integer.valueOf( values[i] );
		}
		return result;
	}
	
	public boolean[] getBooleanValues(String key) {
		String[] values = request.getParameterValues(key);
		boolean[] result = new boolean[values.length];
		for (int i = 0; i < values.length; i++) {
			result[i] = Boolean.valueOf( values[i] );
		}
		return result;
	}
	
	public String[] getParameterValues(String key){
		return request.getParameterValues(key);
	}
	
	public String getParameter(String key){
		return request.getParameter(key);
	}
	
	public Class<?>[] getClassValues(String key){
		String[] values = request.getParameterValues(key);
		Class<?>[] result = new Class<?>[values.length];
		for (int i = 0; i < values.length; i++) {
			result[i] = Tools.stringToClass(values[i]);
		}
		return result;
	}
	
}
