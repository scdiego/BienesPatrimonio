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
public class FrmBuscarBienes extends javax.swing.JDialog {

    /**
     * Creates new form FrmBuscarBienes
     */
    
    public Integer filter;
    public int tipoBusqueda = 0;
    public Responsable unResponsable;
    public Sector unSector;
    public String reportName = "ListadoBienesNro";
   // private String reportName = "ListadoBienesNro";
    public Integer desde;
    public Integer hasta;
    public List<Bien> listaBienes;
    DefaultTableModel modeloBienes = new DefaultTableModel();
    Map<String,Object> parametros = new HashMap();
    private Connection conn;
    private Usuario user;
    private final BienJpaController dao = new BienJpaController();
    
    private static final int NRO_INVENTARIO = 1;
    private static final int DESCRIPCION = 2;
    private static final int SECTOR = 3;
    private static final int RESPONSABLE = 4;
    
    public FrmBuscarBienes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.inicializar();
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
        this.filter = 1;
        
    }
     
    public void setUser(Usuario unUsuario){
        this.user = unUsuario;
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
        this.txtBusqueda.setText(null);
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
                this.reportName = "ListadoBienesNro";
            //    this.parametros.put("desde", desde);
            //    this.buscarBienes(this.desde);
                this.parametros.put("Nro", this.txtBusqueda.getText());
                this.findByNroInventario();
                break;
                
            case 2:
                this.reportName = "ListadoBienesDescripcion";
            //    this.parametros.put("desde", desde);
            //    this.parametros.put("hasta", hasta);
            //    this.buscarBienes(desde, hasta);
                this.parametros.put("descripcion", this.txtBusqueda.getText());
                this.findByDescripcion();
                break;
            case 3:
                this.reportName = "ListadoBienesxResponsable";
                this.parametros.put("responsable", this.unResponsable.getId());
                this.buscarBienes(unResponsable);
                break;
            case 4:
                this.reportName = "ListadoBienesxSector";
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
    
    public final void findByDescripcion(){
        try{
            String descripcion = txtBusqueda.getText();
            this.listaBienes  = dao.findBienesByDescripcion(descripcion);
        } catch (NullPointerException ex){
            JOptionPane.showMessageDialog(null,"La busqueda no arrojo resultados","Búsqueda",JOptionPane.ERROR_MESSAGE); //Tipo de mensaje    
        }
     //   this.txtBusqueda.setText(null);
      
    }
    
    
    public final void findByNroInventario() {
        try {
            int nroInventario = Integer.parseInt(txtBusqueda.getText());
            this.listaBienes  = dao.findBienByNroInventario(nroInventario);            
        } catch (NullPointerException ex){
            JOptionPane.showMessageDialog(null,"La busqueda no arrojo resultados","Búsqueda",JOptionPane.ERROR_MESSAGE); //Tipo de mensaje
        }
     //   this.txtBusqueda.setText(null);
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
       
        strResponsable = this.cmbResponsable.getSelectedItem().toString();
        
        this.tipoBusqueda = filter;
        switch (this.tipoBusqueda){ 
            case 1:
                // id
                break;
            case 2:
                //Descripcion
                break;
            case 3:
                strSector = this.cmbArea.getSelectedItem().toString();
                if(!"".equals(strSector) && !"Seleccione un Area".equals(strSector)){
                    this.unSector = this.buscarSector(strSector);
                    this.tipoBusqueda = 4;
                }
                break;
            case 4:
                if(!"".equals(strResponsable) && !"Seleccione un Responsable".equals(strResponsable)){
                    this.unResponsable = this.buscarResponsable(strResponsable);
                    this.tipoBusqueda = 3;
                }
                break;
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

        opciones = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txtBusqueda = new javax.swing.JTextField();
        cmbResponsable = new javax.swing.JComboBox<>();
        cmbArea = new javax.swing.JComboBox<>();
        rdNroInventario = new javax.swing.JRadioButton();
        rdDescripcion = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        btnImprimir = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        gridBienes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Parámetros de búsqueda"));

        cmbResponsable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbArea.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        opciones.add(rdNroInventario);
        rdNroInventario.setSelected(true);
        rdNroInventario.setText("Nro de Inventario");
        rdNroInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNroInventarioActionPerformed(evt);
            }
        });

        opciones.add(rdDescripcion);
        rdDescripcion.setText("Descripcion");
        rdDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdDescripcionActionPerformed(evt);
            }
        });

        opciones.add(jRadioButton1);
        jRadioButton1.setText("Responsable");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        opciones.add(jRadioButton2);
        jRadioButton2.setText("Area");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtBusqueda))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rdNroInventario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdDescripcion))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jRadioButton2)
                                .addGap(67, 67, 67))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbArea, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cmbResponsable, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdDescripcion)
                    .addComponent(rdNroInventario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImprimir)
                    .addComponent(btnBuscar))
                .addContainerGap())
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        filter = RESPONSABLE;
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        this.mostrarReporte();
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        this.buscar();
        // this.deshabilitar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        filter = SECTOR;
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void rdNroInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdNroInventarioActionPerformed
        // TODO add your handling code here:
        filter = 1;
        
    }//GEN-LAST:event_rdNroInventarioActionPerformed

    private void rdDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdDescripcionActionPerformed
        // TODO add your handling code here:
        filter = DESCRIPCION;
    }//GEN-LAST:event_rdDescripcionActionPerformed

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
            java.util.logging.Logger.getLogger(FrmBuscarBienes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmBuscarBienes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmBuscarBienes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmBuscarBienes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmBuscarBienes dialog = new FrmBuscarBienes(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JComboBox<String> cmbArea;
    private javax.swing.JComboBox<String> cmbResponsable;
    private javax.swing.JTable gridBienes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.ButtonGroup opciones;
    private javax.swing.JRadioButton rdDescripcion;
    private javax.swing.JRadioButton rdNroInventario;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables
}
