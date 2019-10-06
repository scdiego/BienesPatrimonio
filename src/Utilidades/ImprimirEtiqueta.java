/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.util.HashMap;
import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.Iterator;

/**
 *
 * @author diego
 */
public class ImprimirEtiqueta {
    public  HashMap listaBienes = new HashMap();
    public int indice = 1;

    public ImprimirEtiqueta() {
    }

    public void setListaBienes(HashMap listaBienes) {
        listaBienes = listaBienes;
    }
    
    public void addBien (String unBien){
        this.listaBienes.put(this.indice, unBien);
        this.sumarIndice();
    }
    
    private void sumarIndice(){
        this.indice = this.indice + 1;
    }
    
    public interface TscLibDll extends Library {
        TscLibDll INSTANCE = (TscLibDll) Native.loadLibrary ("TSCLIB", TscLibDll.class);
        int about ();
        int openport (String pirnterName);
        int closeport ();
        int sendcommand (String printerCommand);
        int setup (String width,String height,String speed,String density,String sensor,String vertical,String offset);
        int downloadpcx (String filename,String image_name);
        int barcode (String x,String y,String type,String height,String readable,String rotation,String narrow,String wide,String code);
        int printerfont (String x,String y,String fonttype,String rotation,String xmul,String ymul,String text);
        int clearbuffer ();
        int printlabel (String set, String copy);
        int formfeed ();
        int nobackfeed ();
        int windowsfont (int x, int y, int fontheight, int rotation, int fontstyle, int fontunderline, String szFaceName, String content);
    }
    
    public void imprimir(String nro_serie){
        //TscLibDll.INSTANCE.about();
        TscLibDll.INSTANCE.openport("TSC TTP-244 Plus");
        //TscLibDll.INSTANCE.downloadpcx("C:\\UL.PCX", "UL.PCX");
        TscLibDll.INSTANCE.sendcommand("REM ***** This is a test by JAVA. *****");
        TscLibDll.INSTANCE.setup("50", "28", "3", "5", "0", "0", "0");
        TscLibDll.INSTANCE.clearbuffer();
        //TscLibDll.INSTANCE.sendcommand("PUTPCX 550,10,\"UL.PCX\"");
       // TscLibDll.INSTANCE.printerfont ("100", "10", "3", "0", "1", "1", "Sociedad del Conocimiento SAPEM");
        TscLibDll.INSTANCE.barcode("85", "70", "128", "100", "1", "0", "2", "2", nro_serie);
        //TscLibDll.INSTANCE.windowsfont(200, 200, 38, 0, 0, 1, "arial", "");
        TscLibDll.INSTANCE.windowsfont(26, 20, 24, 0, 0, 0, "arial", "Sociedad del Conocimiento SAPEM");
        TscLibDll.INSTANCE.printlabel("1", "1");
        TscLibDll.INSTANCE.closeport();
    }
    public  void printLista(){
        this.imprimirLista();
    }
    
    public void imprimirLista() {
        Iterator it = this.listaBienes.keySet().iterator();
        while(it.hasNext()){
            int key = (Integer)it.next();           
            String nro_serie = (String)listaBienes.get(key);
            imprimir(nro_serie);
        }
    }
    
}
