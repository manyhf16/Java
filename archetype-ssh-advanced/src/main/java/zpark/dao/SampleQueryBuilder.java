package zpark.dao;

import java.util.List;

import zpark.common.dao.QueryBuilder;

public final class SampleQueryBuilder implements QueryBuilder {
	private final Integer id;
	private final String name;

	public SampleQueryBuilder(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public void build(StringBuilder sb, List<Object> params) {
		if(id != null) {
			sb.append(" and id > ?");
			params.add(id);
		}
		if(name != null) {
			sb.append(" and name like ?");
			params.add(name);
		}
	}
}