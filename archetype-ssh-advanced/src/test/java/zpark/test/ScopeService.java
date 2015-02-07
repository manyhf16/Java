package zpark.test;

public class ScopeService {
	
	private ScopeObject object;
	
	public void setObject(ScopeObject object) {
		this.object = object;
	}
	
	public void test() {
		System.out.println(this.object);
	}

}
