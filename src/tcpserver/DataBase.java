/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package tcpserver;


import java.sql.*;

public class DataBase
{
  Connection conn;

  public DataBase()
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      String url = "jdbc:mysql://localhost/theatre";
      conn = DriverManager.getConnection(url, "root", "isep");
      doTests();
      conn.close();
    }
    catch (ClassNotFoundException ex) {System.err.println(ex.getMessage());}
    catch (IllegalAccessException ex) {System.err.println(ex.getMessage());}
    catch (InstantiationException ex) {System.err.println(ex.getMessage());}
    catch (SQLException ex)           {System.err.println(ex.getMessage());}
  }



  private void doTests()
  {
    doSelectTest();

    doInsertTest();  doSelectTest();
    doUpdateTest();  doSelectTest();
    doDeleteTest();  doSelectTest();
  }

  public void doSelectTest()
  {
    System.out.println("[OUTPUT FROM SELECT]");
    String query = "SELECT PIECE_NAME, PLACE_NUM FROM SPECTACLE";
    try
    {
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery(query);
      while (rs.next())
      {
        String s = rs.getString("PIECE_NAME");
        float n = rs.getFloat("PLACE_NUM");
        System.out.println(s + "   " + n);
      }
    }
    catch (SQLException ex)
    {
      System.err.println(ex.getMessage());
    }
  }

  public void doInsertTest()
  {
    System.out.print("\n[Performing INSERT] ... ");
    try
    {
      Statement st = conn.createStatement();
      st.executeUpdate("INSERT INTO SPECTACLE " +
                       "VALUES ('Yes ca marche !!!!!', 5)"
              );
            st.executeUpdate("INSERT INTO SPECTACLE " +
                       "VALUES ('Plus Belle la vie', 1000)"
              );
    }
    catch (SQLException ex)
    {
      System.err.println(ex.getMessage());
    }
  }

  public void doUpdateTest()
  {
    System.out.print("\n[Performing UPDATE] ... ");
    try
    {
      Statement st = conn.createStatement();
      st.executeUpdate("UPDATE SPECTACLE SET PLACE_NUM=3 WHERE PIECE_NAME='La Belle est la bête'");
    }
    catch (SQLException ex)
    {
      System.err.println(ex.getMessage());
    }
  }

  public void doDeleteTest()
  {
    System.out.print("\n[Performing DELETE] ... ");
    try
    {
      Statement st = conn.createStatement();
      st.executeUpdate("DELETE FROM SPECTACLE WHERE PIECE_NAME='La Belle est la bête'");
    }
    catch (SQLException ex)
    {
      System.err.println(ex.getMessage());
    }
  }
}

