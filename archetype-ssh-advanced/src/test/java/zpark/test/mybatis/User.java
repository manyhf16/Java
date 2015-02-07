package zpark.test.mybatis;

public class User {

	private int id;

	private String namea;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getNamea() {
		return namea;
	}

	public void setNamea(String namea) {
		this.namea = namea;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + namea + "]";
	}

}
