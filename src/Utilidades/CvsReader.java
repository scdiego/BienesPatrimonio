/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

/**
 *
 * @author diego
 * 
 * 
 * /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Negocio.Bien;
import Negocio.RegistroImportacionBien;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
    
public class CvsReader {
   private String csvFile;

    public CvsReader(String csvFile) {
        this.csvFile = csvFile;
    }
   
   public ArrayList<RegistroImportacionBien> read(){
       CSVReader reader = null;
       ArrayList<RegistroImportacionBien> lista = new ArrayList();
       //List<Asignacion> salida = new ArrayList();
        try {
            
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
      //          int campos = line.length;
      //          for(int i=1;i<=campos;i++){
                    lista.add(new RegistroImportacionBien(line[1],line[0],line[2],line[3],line[4],line[5],line[6],line[7]))  ;
      //          }
                //System.out.println("Country [id= " + line[0] + ", code= " + line[1] + " , name=" + line[2] + "]");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
       return lista;
   }
   
}
