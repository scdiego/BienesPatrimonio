package Reportes;

import java.awt.Dialog;
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;



/**
 *
 * @author diego
 * https://www.youtube.com/watch?v=j6-eiujim78
 * 
 */
public abstract class AbsJasperReports {
    
    private static JasperReport report;
    private static JasperPrint reportFilled;
    private static JasperViewer viewer;
    
    public static void createReport(Connection conn,String pathReport){
        try{
            report = (JasperReport) JRLoader.loadObjectFromFile(pathReport);
            reportFilled = JasperFillManager.fillReport(report, null,conn);
        }catch (JRException ex){
            ex.printStackTrace();
        }
    }
    public static void createReport(Connection conn,String pathReport,Map<String,Object> parametros){
        try{
            report = (JasperReport) JRLoader.loadObjectFromFile(pathReport);
            reportFilled = JasperFillManager.fillReport(report, parametros,conn);
        }catch (JRException ex){
            ex.printStackTrace();
        }
    }
    public static void showViewer(){
        viewer = new JasperViewer(reportFilled,false);
        viewer.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        viewer.setAlwaysOnTop(true);
        viewer.setVisible(true);
    }    
    public static void exportToPdf(String pathDestino){
        
        try{
            JasperExportManager.exportReportToPdfFile(reportFilled,pathDestino);
            
        }
        catch(JRException ex){
            ex.printStackTrace();
        }
        
    }
}
