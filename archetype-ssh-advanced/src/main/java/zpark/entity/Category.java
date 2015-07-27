package zpark.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@TableGenerator(name = "category_tg", table = "pk_table", pkColumnName = "table_name", pkColumnValue = "category", valueColumnName = "next_value", initialValue = 0, allocationSize = 1)
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "category_tg")
	private int id;

	private String name;

	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	@JsonIgnore	
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Product> products = new HashSet<Product>();

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

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }
	
	
}
