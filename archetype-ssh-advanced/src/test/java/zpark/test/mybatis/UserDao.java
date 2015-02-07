package zpark.test.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface UserDao {

	@Insert("insert into day1_user values (#{id},#{name})")
//	@SelectKey(statement = "SELECT LAST_INSERT_ID()", before = false, keyProperty = "id", resultType = int.class)
	@Options(useGeneratedKeys=true)
	public void saveUser(User user);
	
	@Delete("delete from day1_user where id = #{a}")
	public int deleteUser(int id);
	
	@Select("select id,name from day1_user")
	public List<User> selectUsers();
	
//	@Select("select id,name as namea from day1_user where id = #{a}")
	public User selectUserById(int id);

}
