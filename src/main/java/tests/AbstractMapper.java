package tests;

public class AbstractMapper<K> {
	
	private Class<K> type;
	
	public AbstractMapper(Class<K> type) {
		this.type = type;
	}

	
}
