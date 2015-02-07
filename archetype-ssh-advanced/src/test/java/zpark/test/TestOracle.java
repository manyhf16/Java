package zpark.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class TestOracle {

    public static void main(String[] args) throws Exception {
//        Class.forName("oracle.jdbc.OracleDriver");
//        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "test", "test");
//        PreparedStatement stmt = conn.prepareStatement("insert into s values (?)");
        Double d = Double.valueOf("123456789123456789.22");
        System.out.println(d); //1.11111111222E8
        
        Float f = Float.valueOf("123456789.255");
        System.out.println(f); //1.11111112E8
//        stmt.setDouble(1, d);
//        stmt.executeUpdate();
    }

}
