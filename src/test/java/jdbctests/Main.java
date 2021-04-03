package jdbctests;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        String dbUrl = "jdbc:oracle:thin:@52.201.159.252:1521:xe";
        String dbUsername = "hr";
        String dbPassword = "hr";

        //create a connection
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        //create statement object
        Statement statement = connection.createStatement();

        //run query and get result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from regions");

        //move pointer to first row

//        resultSet.next();
//        System.out.println(resultSet.getInt(1) + " " + resultSet.getString("region_name"));
//
//        resultSet.next();
//        System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
//
//        resultSet.next();
//        System.out.println(resultSet.getInt(1) + " " + resultSet.getString("region_name"));

        System.out.println("with while loop");
        while (resultSet.next()){
            System.out.println(resultSet.getInt(1) + " " + resultSet.getString("region_name"));
        }

        //closing all connections
        resultSet.close();
        statement.close();
        connection.close();

    }

}
