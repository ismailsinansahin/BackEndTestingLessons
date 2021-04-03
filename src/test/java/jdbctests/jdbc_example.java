package jdbctests;

import org.junit.Test;

import java.sql.*;

public class jdbc_example {

    String dbUrl = "jdbc:oracle:thin:@52.201.159.252:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void test1() throws SQLException {

        //create a connection
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        //create statement object
        Statement statement = connection.createStatement();
        //run query and get result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from regions");

        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        //----------------------------------------------------------------------------

        resultSet = statement.executeQuery("select * from departments");

        while (resultSet.next()) {
            System.out.println(resultSet.getString(2));
        }

        //closing all connections
        resultSet.close();
        statement.close();
        connection.close();

    }

    @Test
    public void CountandNavigate() throws SQLException {

        //create a connection
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from regions");

        //how to find how many rows we have for the query
        //go to the last row
        resultSet.last();
        //get the row count
        int rowCount = resultSet.getRow();
        System.out.println("rowCount = " + rowCount);

        //to get full list we need to move before first row since we are at the last row right now
        //go to the default position
        resultSet.beforeFirst();
        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        //how many columns we have and what are their names


        //closing all connections
        resultSet.close();
        statement.close();
        connection.close();

    }

    @Test
    public void metaData() throws SQLException {

        //create a connection
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from countries");

        //get the database related data inside the dbMetadata object
        DatabaseMetaData dbmd = connection.getMetaData();

        System.out.println("User:" + dbmd.getUserName());
        System.out.println("dbmd.getDatabaseProductName() = " + dbmd.getDatabaseProductName());
        System.out.println("dbmd.getDatabaseProductVersion() = " + dbmd.getDatabaseProductVersion());
        System.out.println("dbmd.getDriverName() = " + dbmd.getDriverName());
        System.out.println("dbmd.getDriverVersion() = " + dbmd.getDriverVersion());

        //get the resultset object metadata
        ResultSetMetaData rsmd = resultSet.getMetaData();

        //how many columns do we have?
        System.out.println("Column count: " + rsmd.getColumnCount());

        //column names
        //System.out.println("Column names: " + rsmd.getColumnName(4));

        //print all the column names dynamically
        int colCount = rsmd.getColumnCount();
        for (int i = 1; i <= colCount; i++) {
            System.out.println(rsmd.getColumnName(i));
        }

        //closing all connections
        resultSet.close();
        statement.close();
        connection.close();

    }

}