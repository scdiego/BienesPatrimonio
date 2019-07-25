/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import Presentacion.FrmLibroBienes;
import dbConn.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diego
 */
public class ConsultasDB {
    
    Connection conn;
    Statement comando;
    ResultSet registro;
    Conexion db = new Conexion();

    public ConsultasDB() {
        this.inicializar();
    }
    
    
    public void inicializar(){
        try{
            conn = db.obtener();
        
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FrmLibroBienes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private int getRowCount(ResultSet resultSet) {
        if (resultSet == null) {
            return 0;
        }
        try {
            resultSet.last();
            return resultSet.getRow();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                resultSet.beforeFirst();
            } catch (SQLException exp) {
                exp.printStackTrace();
            }
        }
        return 0;
    }
    
    public Object[][] ejcutarConsulta(String consulta,List<String> campos){
       Integer numRows;
       Integer cols; 
       int rowIndex = 0;
      // List<registroDB> salida = new ArrayList();
       Object[][] data = null;
       try {
            Statement comando=conn.createStatement();
            registro = comando.executeQuery(consulta);
            numRows = this.getRowCount(comando.getResultSet());
            cols = campos.size();
            data = new Object[numRows][cols];
            int i = 1;
            
            while(registro.next()){
                int j = 0;
                for(String str : campos)
                {
                    data[rowIndex][j] = registro.getString(str);
                    j = j + 1;
                }
                rowIndex  = rowIndex + 1;
            }         
       } catch(SQLException ex){
         //   setTitle(ex.toString());
         System.out.println(consulta);
       }
       return data;
    }    
        
    
}
