/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbConn;

/**
 *
 * @author diego
 */
import java.sql.*;
public class Conexion {
   private static Connection cnx = null;
           
   
   public static Connection obtener() throws SQLException, ClassNotFoundException {
       
       
       String server = System.getenv().get("SERVERPATRIMONIO");
       String db = System.getenv().get("BASEPATRIMONIO");
       String user = System.getenv().get("USERPATRIMONIO"); 
       String pass = System.getenv().get("PASSPATRIMONIO");
       
       /*
       
       String server = "localhost";
       String db = "patrimonio";
       String user = "patrimonio";
       String pass = "V4lh4ll$";
       */
       if (cnx == null) {
         try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection("jdbc:mysql://"+server+"/"+db, user, pass);
         } catch (SQLException ex) {
            throw new SQLException(ex);
         } catch (ClassNotFoundException ex) {
            throw new ClassCastException(ex.getMessage());
         }
      }
      return cnx;
   }
   public static void cerrar() throws SQLException {
      if (cnx != null) {
         cnx.close();
      }
   }

 
   
}
