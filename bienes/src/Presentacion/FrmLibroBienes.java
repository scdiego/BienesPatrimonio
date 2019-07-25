/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Negocio.Bien;
import Negocio.Responsable;
import Negocio.Sector;
import Negocio.Usuario;
import Persistencia.AsignacionJpaController;
import Persistencia.BienJpaController;
import Persistencia.ResponsableJpaController;
import Persistencia.SectorJpaController;
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
public class FrmLibroBienes extends javax.swing.JDialog {

    /**
     * Creates new form FrmLibroBienes
     */
    
    /**
     * Tipo Busqueda 
     * 0 = Todo ListadoBienes
     * 1 = Desde ListadoBienesd
     * 2 = Desde y Hasta ListadoBienesdh
     * 3 = Responsable ListadoBienesResponsable
     * 4 = Sector ListadoBienesSector
     * reportName
     * 
     */
    
    
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
    
    public FrmLibroBienes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.inicializar();
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    public void inicializar(){
        this.fillResponsables();
        this.fillAreas();
        this.setTitle("Libro de Bienes");
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
       this.setAlwaysOnTop(false);
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
        //Nro de Inventario

        
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDesde = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtHasta = new javax.swing.JTextField();
        cmbResponsable = new javax.swing.JComboBox<>();
        Responsable = new javax.swing.JLabel();
        Area = new javax.swing.JLabel();
        cmbArea = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        gridBienes = new javax.swing.JTable();
        btnBuscar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Libro de Bienes - Patrimonio");

        jLabel1.setText("Desde");

        jLabel2.setText("Hasta");

        cmbResponsable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        Responsable.setText("Responsable");

        Area.setText("Area");

        cmbArea.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Responsable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Area)
                .addGap(3, 3, 3)
                .addComponent(cmbArea, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(txtDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2)
                .addComponent(txtHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(Responsable)
                .addComponent(Area)
                .addComponent(cmbArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        gridBienes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro de Inventario", "Descripcion", "Estado", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(gridBienes);

        btnBuscar.setText("Buscar");
        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarMouseClicked(evt);
            }
        });
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnImprimir)
                        .addComponent(btnBuscar))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        this.buscar();
       // this.deshabilitar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        this.mostrarReporte();
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseClicked
        // TODO add your handling code here:
        this.buscar();
    }//GEN-LAST:event_btnBuscarMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmLibroBienes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLibroBienes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLibroBienes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLibroBienes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmLibroBienes dialog = new FrmLibroBienes(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Area;
    private javax.swing.JLabel Responsable;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JComboBox<String> cmbArea;
    private javax.swing.JComboBox<String> cmbResponsable;
    private javax.swing.JTable gridBienes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtDesde;
    private javax.swing.JTextField txtHasta;
    // End of variables declaration//GEN-END:variables
}
