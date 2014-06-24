package zpark.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "h1_user")
public class User1 implements Serializable {

	@Id
	private int id;

	private String name;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, optional = false)
	private UserDetail1 detail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserDetail1 getDetail() {
		return detail;
	}

	public void setDetail(UserDetail1 detail) {
		this.detail = detail;
	}

}
