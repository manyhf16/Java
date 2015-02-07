package zpark.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@SuppressWarnings("serial")
@Entity
@TableGenerator(name = "sample_tg", table = "pk_table", pkColumnName = "table_name", pkColumnValue = "sampleentity", valueColumnName = "next_value", initialValue = 0, allocationSize = 1)
public class SampleEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sample_tg")
	private int id;

	private String name;

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

}
