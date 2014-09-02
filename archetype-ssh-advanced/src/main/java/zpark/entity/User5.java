package zpark.entity;

import java.util.HashSet;
import java.util.Set;

public class User5 {
	private int id;
	private String name;
	private Set<Order5> orders = new HashSet<Order5>();
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
	public Set<Order5> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order5> orders) {
		this.orders = orders;
	}

}
