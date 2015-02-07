package zpark.test;

public class ServiceA {
	
	private ServiceB serviceB;
	
	public void setServiceB(ServiceB serviceB) {
		this.serviceB = serviceB;
	}
	
	public void a() {
		try {
			System.out.println("a");
			serviceB.b();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
