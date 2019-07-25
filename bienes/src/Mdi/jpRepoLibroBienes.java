/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mdi;

import Negocio.Bien;
import Negocio.Responsable;
import Negocio.Sector;
import Negocio.Usuario;
import Persistencia.AsignacionJpaController;
import Persistencia.BienJpaController;
import Persistencia.ResponsableJpaController;
import Persistencia.SectorJpaController;
import Presentacion.FrmLibroBienes;
import Reportes.AbsJasperReports;
import dbConn.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author diego
 */
public class jpRepoLibroBienes extends javax.swing.JPanel {

    /**
     * Creates new form jpRepoLibroBienes
     */
    MainMdi parent;
    public int tipoBusqueda = 0;
    public Responsable unResponsable;
    public Sector unSector;
    public String reportName = "ListadoBienes";
    public Integer desde;
    public Integer hasta;
    public List<Bien> listaBienes;
    DefaultTableModel modeloBienes = new DefaultTableModel();
    Map<String,Object> parametros = new HashMap();
    private Connection conn;
    private Usuario user;
    
    
    public jpRepoLibroBienes() {
        initComponents();
        this.inicializar();
    }

    public MainMdi getParent() {
        return parent;
    }

    public void setParent(MainMdi parent) {
        this.parent = parent;
    }
    
    public void inicializar(){
        this.fillResponsables();
        this.fillAreas();
        //this.setTitle("Libro de Bienes");
        try { 
            conn = Conexion.obtener();
        } catch (SQLException ex) {
            Logger.getLogger(FrmLibroBienes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrmLibroBienes.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.deshabilitar();
        
    }
    
    public void habilitar(){
        this.btnImprimir.setEnabled(true);
    }
    
    public void deshabilitar(){
        this.btnImprimir.setEnabled(false);
    }
    private void fillResponsables() {
        this.cmbResponsable.removeAllItems();
        this.cmbResponsable.addItem("Seleccione un Responsable");
        ResponsableJpaController responsableDao = new ResponsableJpaController();
        List<Responsable> responsables = responsableDao.findResponsableEntities();
        for (Responsable responsable:responsables) {
            this.cmbResponsable.addItem(responsable.toString());
        }
    }
    
    private void fillAreas(){
        this.cmbArea.removeAllItems();
        this.cmbArea.addItem("Seleccione un Area");
        SectorJpaController sectorDao = new SectorJpaController();
        List<Sector> sectores = sectorDao.findSectorEntities();
        for(Sector sector:sectores){
            this.cmbArea.addItem(sector.toString());
        }
    }
    
     public  void limpiarGrid(){
        if (this.modeloBienes.getRowCount() > 0) {
            for (int i = this.modeloBienes.getRowCount() - 1; i > -1; i--) {
               this.modeloBienes.removeRow(i);
            }
        }
    }
     
    public  void colocarEncabezadoGrid(){
        String[] columnNames = {"Nro de Inventario",
                                "Descripcion",
                                "Estado",
                                "Valor"};
        this.modeloBienes.setColumnIdentifiers(columnNames);
        this.gridBienes.setModel(this.modeloBienes);
    }
    
    public void buscar(){
        this.setearParametros();
        this.buscarRegistros();
        this.completarGrid();
    }
    
    public void limpiarCampos(){
        this.txtDesde.setText(null);
        this.txtHasta.setText(null);        
    }
    
    public void completarGrid(){
        int cantBienes = this.listaBienes.size();
        int COLS = 4;
        Object[][] data = new Object[cantBienes][COLS];
        int rowIndex = 0;
        this.limpiarGrid();
        this.colocarEncabezadoGrid();

        for (Bien bien : this.listaBienes) {
            Integer nroInventario = bien.getNroInventario();
            
            data[rowIndex][0] = nroInventario;
            data[rowIndex][1] = bien.getDescripcion();
            data[rowIndex][2] = bien.getEstado(); 
            data[rowIndex][3] = "$"+ bien.getValor(); 

            modeloBienes.addRow(data[rowIndex]);
        }  
    }
    
    
    
    
    public void buscarBienes(){
        BienJpaController bienDao = new BienJpaController();
        this.listaBienes = bienDao.findBienesOrdenados();
    }
    
    public void buscarBienes(Integer desde){
        BienJpaController bienDao = new BienJpaController();
        this.listaBienes = bienDao.findBienByNroDesde(desde);
    }
    
    public void buscarBienes(Integer desde, Integer Hasta){
        BienJpaController bienDao = new BienJpaController();
        this.listaBienes = bienDao.findBienByNroDesdeHasta(desde, Hasta);
    }
    
    public void buscarBienes(Sector unSector){
        AsignacionJpaController asignacionDao = new AsignacionJpaController();
        this.listaBienes = asignacionDao.findBienBySector(this.unSector);
    }
    
    public void buscarBienes(Responsable unResponsable){
        AsignacionJpaController asignacionDao = new AsignacionJpaController();
        this.listaBienes = asignacionDao.findAsignacionesByResponsable(unResponsable);
    }
    
    public void buscarRegistros(){
        switch(this.tipoBusqueda){
            case 0:
                this.reportName = "ListadoBienes";
                this.buscarBienes();
                break;
            case 1:
                this.reportName = "ListadoBienesD";
                this.parametros.put("desde", desde);
                this.buscarBienes(this.desde);
                break;
            case 2:
                this.reportName = "ListadoBienesDH";
                this.parametros.put("desde", desde);
                this.parametros.put("hasta", hasta);
                this.buscarBienes(desde, hasta);
                break;
            case 3:
                this.reportName = "ListadoBienesResponsable";
                this.parametros.put("responsable", this.unResponsable.getId());
                this.buscarBienes(unResponsable);
                break;
            case 4:
                this.reportName = "ListadoBienesSector";
                this.parametros.put("sector", this.unSector.getId());
                this.buscarBienes(unSector);
                break;
        }
        this.habilitar();
        if(this.listaBienes.size() <= 0){
            JOptionPane.showMessageDialog(null, "La busqueda no ha arrojado resultados");
            this.deshabilitar();
        }
        
    }
    
    public void mostrarReporte(){
        String vpath = System.getenv().get("RUTAREPORTES")+"/"+this.reportName+".jasper";        
        AbsJasperReports.createReport(conn, vpath,parametros);
        //this.deshabilitar();
        AbsJasperReports.showViewer();
       //JOptionPane.showMessageDialog(null, vpath);
       //this.setAlwaysOnTop(false);
       //this.dispose();
       
    }
    
    public Responsable buscarResponsable(String name){
        ResponsableJpaController responsableDao = new ResponsableJpaController();        
        return responsableDao.findResponsableByNombre(name);
    }
    
    public Sector buscarSector(String name){
        SectorJpaController sectorDao = new SectorJpaController();
        return sectorDao.findSectorByNombre(name);
    }
    
    public void setearParametros(){
        String strResponsable;
        String strSector;
        
        if(!"".equals(this.txtDesde.getText().trim())){
            this.desde = Integer.parseInt(this.txtDesde.getText().trim());
            this.tipoBusqueda = 1;
        }
        
        if(!"".equals(this.txtHasta.getText().trim())){
            this.hasta = Integer.parseInt(this.txtHasta.getText().trim());
            this.tipoBusqueda = 2;
        }
        
        strResponsable = this.cmbResponsable.getSelectedItem().toString();
        
        if(!"".equals(strResponsable) && !"Seleccione un Responsable".equals(strResponsable)){
            this.unResponsable = this.buscarResponsable(strResponsable);
            this.tipoBusqueda = 3;
        }
        
        strSector = this.cmbArea.getSelectedItem().toString();
        if(!"".equals(strSector) && !"Seleccione un Area".equals(strSector)){
            this.unSector = this.buscarSector(strSector);
            this.tipoBusqueda = 4;
        }
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
        txtDesde = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtHasta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbResponsable = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cmbArea = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        gridBienes = new javax.swing.JTable();
        btnImprimir = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1193, 545));

        jLabel1.setText("Desde");

        jLabel2.setText("Hasta");

        jLabel3.setText("Responsable");

        cmbResponsable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Area");

        cmbArea.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        gridBienes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nro de Inventario", "Descripcion", "Estado", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(gridBienes);

        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbArea, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnImprimir)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(0, 57, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cmbResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cmbArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir)
                    .addComponent(btnBuscar)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        this.mostrarReporte();
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        this.buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.buscar();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JComboBox<String> cmbArea;
    private javax.swing.JComboBox<String> cmbResponsable;
    private javax.swing.JTable gridBienes;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtDesde;
    private javax.swing.JTextField txtHasta;
    // End of variables declaration//GEN-END:variables
}
