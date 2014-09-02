package zpark.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;

public class TestGeneratedKeys extends TestBasic {
	
	@Autowired
	private DataSource dataSource;
	
	@Test @Transactional
	public void test() throws SQLException {
		Connection conn = dataSource.getConnection();
		PreparedStatement stmt = conn.prepareStatement("insert into bbb values (bbb_seq.nextval, 'ok')", new String[]{"id"});
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		if(rs.next()) {
			int id = rs.getInt(1);
			System.out.println(id);
		}
	}

}
