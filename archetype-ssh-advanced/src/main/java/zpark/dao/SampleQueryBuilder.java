package zpark.dao;

import java.util.Map;

import zpark.ext.query.QueryBuilder;

public final class SampleQueryBuilder implements QueryBuilder {
	private final Integer id;
	private final String name;

	public SampleQueryBuilder(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public void build(StringBuilder sb, Map<String, Object> params) {
		sb.append(" where 1=1");
		if (id != null) {
			sb.append(" and id > :id");
			params.put("id", id);
		}
		if (name != null) {
			sb.append(" and name like :name");
			params.put("name", name);
		}
	}
}