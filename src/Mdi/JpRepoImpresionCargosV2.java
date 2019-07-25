/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mdi;

import Negocio.Asignacion;
import Negocio.Bien;
import Negocio.Responsable;
import Negocio.Usuario;
import Persistencia.AsignacionJpaController;
import Persistencia.BienJpaController;
import Persistencia.ResponsableJpaController;
import Presentacion.FrmLibroBienes;
import Reportes.AbsJasperReports;
import dbConn.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author ramiel
 */
public class JpRepoImpresionCargosV2 extends javax.swing.JPanel {

    /**
     * Creates new form JpRepoImpresionCargosV2
     */
    DefaultListModel modeloResponsables = new DefaultListModel();
    List<Responsable> listaResponsables = new ArrayList();
    Responsable responsable;
    private EntityManagerFactory emf = null;
    
    List<Bien> asignaciones = new ArrayList();
    DefaultListModel modelo = new DefaultListModel();
    DefaultListModel modeloImprimir = new DefaultListModel();
    String listaBienesImprimir;
    private Connection conn;
    public String reportName = "PlanilladeCargos";
    Map<String,Object> parametros = new HashMap();
    
    Bien unBien;
    BienJpaController controllerBien = new BienJpaController();
    ResponsableJpaController responsableDao = new ResponsableJpaController();
    
    private Usuario user;
    public MainMdi parent;
    
    public JpRepoImpresionCargosV2() {
        initComponents();
        this.cargarResponsables();
        this.inicializar();
        
    }
    
    
    public void cargarResponsables(){
        
        this.listaResponsables = responsableDao.findResponsableEntities();
        
        this.cmbResponsable.removeAllItems();
        this.cmbResponsable.addItem("Seleccione un Responsable");
        for (Responsable responsable:this.listaResponsables) {
            this.cmbResponsable.addItem(responsable.toString());
        }
        
    }
    
    public void inicializar(){
        this.cargarResponsables();
                try { 
            conn = Conexion.obtener();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FrmLibroBienes.class.getName()).log(Level.SEVERE, null, ex);
        }
       // this.btnImprimir.setEnabled(false);
        this.lstBienesImprimir.setModel(this.modeloImprimir);
        emf = Persistence.createEntityManagerFactory("patromonioPU");
    }
    
    public void setearResponsable(){
        if(this.cmbResponsable.getItemCount() > 0){
            String nombre = this.cmbResponsable.getSelectedItem().toString();
            this.responsable = responsableDao.findResponsableByNombre(nombre);
        }
        
        
    }
    
    public void cargarAsignaciones(){
        AsignacionJpaController dao = new AsignacionJpaController();
        this.asignaciones = dao.findAsignacionesByResponsable(this.responsable);

        this.completarGrid();
    }
    
    public void completarGrid(){

        this.modelo.clear();
        AsignacionJpaController asignacionDao = new AsignacionJpaController();
        this.asignaciones = asignacionDao.findAsignacionesByResponsable(this.responsable);
                
        this.lstBienes.removeAll();
        for(Bien bienes: this.asignaciones){
            this.modelo.addElement(bienes.toString());
        }
        this.lstBienes.setModel(modelo);
         
        
    }
    
    public void agregar(){
        String nombre =  this.modelo.getElementAt(this.lstBienes.getSelectedIndex()).toString();
        String[] parts = nombre.split("---") ;
        unBien = this.controllerBien.findBienNroInventario(Integer.parseInt(parts[0].trim()));        
        this.modeloImprimir.addElement(unBien);
    //    this.modelo.remove(this.lstBienes.getSelectedIndex());
    }
    
    public void agregar(int posicion){
        String nombre =  this.modelo.getElementAt(posicion).toString();
        String[] parts = nombre.split("---") ;
        //unBien = this.controllerBien.findBien(Integer.parseInt(parts[0].trim()));        
        unBien = this.controllerBien.findBienNroInventario(Integer.parseInt(parts[0].trim()));
        this.modeloImprimir.addElement(unBien);
     //   this.modelo.remove(posicion);
    }
    
    public void agregarTodos(){
        //int max = this.modelo.getSize();
        for(int i = 0; i <= this.modelo.getSize()-1; i += 1){
            this.agregar(i);
        }
        
    }

    public void quitar(){
        
        String nombre =  this.modeloImprimir.getElementAt(this.lstBienes.getSelectedIndex()).toString();
        this.modeloImprimir.remove(this.lstBienes.getSelectedIndex());
        String[] parts = nombre.split("---") ;
        unBien = this.controllerBien.findBienNroInventario(Integer.parseInt(parts[0].trim()));   
        this.modelo.addElement(unBien);
    }
    
    public void quitar(int posicion){
        String nombre =  this.modeloImprimir.getElementAt(posicion).toString();
        this.modeloImprimir.remove(this.lstBienes.getSelectedIndex());
        String[] parts = nombre.split("---") ;
        unBien = this.controllerBien.findBienNroInventario(Integer.parseInt(parts[0].trim()));   
        this.modelo.addElement(unBien);
    }
    
    public void quitarTodos(){
        int max = this.modeloImprimir.getSize();
        for(int i = 0; i <= max-1; i += 1){
            this.quitar(i);
        }
    }
    
    public void limpiarLista(){
        this.modeloImprimir.clear();
        this.lstBienesImprimir.removeAll();
        
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void imprimir(){
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        StoredProcedureQuery sp = em.createStoredProcedureQuery("agregarBienesReporte");
//        StoredProcedureQuery sp2 = em.createStoredProcedureQuery("AGREGA_BIEN_REPORTE");
        
        sp.registerStoredProcedureParameter("bienes", String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("id", Integer.class, ParameterMode.OUT);

        //int idResponsable = this.responsable.getId();
        //sp.setParameter("IDRESPONSABLE", idResponsable);
        //sp.execute();
        
        //final  int idReporte = Integer.parseInt(sp.getOutputParameterValue("IDREPORTE").toString());
        //em.getTransaction().commit();
        
        //https://www.facebook.com/LuliPedotti
        //https://www.facebook.com/photo.php?fbid=739472276455401&set=a.107809909621644&type=3&theater
        
        //String nombreResponsable = responsableDao.findResponsable(this.idResponsable).toString();
            
        
        String element = "";
        String bienes = "";
        int max = this.modeloImprimir.getSize();
        for(int i = 0; i <= max-1; i += 1){
            element = this.modeloImprimir.getElementAt(i).toString();
            String[] parts = element.split("---");
            bienes += parts[0].trim() + ",";
        }
        bienes = bienes.substring(0, bienes.length()-1);
        sp.setParameter("bienes", bienes);
        sp.execute();
        final  int idReporte = Integer.parseInt(sp.getOutputParameterValue("id").toString());
        em.getTransaction().commit();
        
        
        

        String vpath = System.getenv().get("RUTAREPORTES")+"/"+this.reportName+".jasper";
        //String vpath = "/home/diego/proyectos/patrimonio/src/Reportes/"+this.reportName+".jasper";
        this.inicializarParametros(idReporte,"");
        AbsJasperReports.createReport(conn, vpath,parametros);
        AbsJasperReports.showViewer();
        
    }
    public void inicializarParametros(int reporte, String responsable){
        this.parametros.put("idReport", reporte);
        this.parametros.put("responsable", responsable);
    }    
    
    private void showNoResults() {
        JOptionPane.showMessageDialog(this,
                "No se encontraron bienes seleccinados para imprimir el reporte",        
                "Algo salió mal",                        
                 JOptionPane.INFORMATION_MESSAGE);

    } 
    
    public boolean control(){
        boolean salida = false;
        if(this.modeloImprimir.getSize() > 0){
            salida = true;
        }
            
        return salida;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cmbResponsable = new javax.swing.JComboBox<>();
        btnImprimir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstBienes = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstBienesImprimir = new javax.swing.JList<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jLabel1.setText("Responsable");

        cmbResponsable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbResponsable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbResponsableMouseClicked(evt);
            }
        });
        cmbResponsable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbResponsableActionPerformed(evt);
            }
        });

        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        lstBienes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lstBienes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 367, Short.MAX_VALUE)
        );

        btnAceptar.setText("Aceptar");

        jButton1.setText(">");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lstBienesImprimir.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstBienesImprimir.setValueIsAdjusting(true);
        jScrollPane2.setViewportView(lstBienesImprimir);

        jButton2.setText(">>");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("<<");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("<");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnImprimir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(btnAceptar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnImprimir))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(78, 78, 78)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAceptar)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbResponsableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbResponsableActionPerformed
        // TODO add your handling code here:
        this.setearResponsable();
        this.cargarAsignaciones();
    }//GEN-LAST:event_cmbResponsableActionPerformed

    private void cmbResponsableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbResponsableMouseClicked
        // TODO add your handling code here:
        this.setearResponsable();
        this.cargarAsignaciones();
        this.limpiarLista();
    }//GEN-LAST:event_cmbResponsableMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.agregar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.agregarTodos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.quitarTodos();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.quitar();
                
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        if(this.control()){
            this.imprimir();
        }else{
            this.showNoResults();
        }
    }//GEN-LAST:event_btnImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JComboBox<String> cmbResponsable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> lstBienes;
    private javax.swing.JList<String> lstBienesImprimir;
    // End of variables declaration//GEN-END:variables
}
