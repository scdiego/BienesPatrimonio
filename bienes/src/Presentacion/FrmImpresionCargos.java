/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Negocio.Bien;
import Negocio.Responsable;
import Negocio.Usuario;
import Persistencia.AsignacionJpaController;
import Persistencia.ResponsableJpaController;
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
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author diego
 */
public class FrmImpresionCargos extends javax.swing.JDialog {

    DefaultListModel modeloResponsables = new DefaultListModel();
    List<Responsable> listaResponsables = new ArrayList();
    Responsable responsable;
    
    List<Bien> asignaciones = new ArrayList();
    DefaultTableModel modeloBienes = new DefaultTableModel();
    String listaBienesImprimir;
    private Connection conn;
    public String reportName = "PlanilladeCargos";
    Map<String,Object> parametros = new HashMap();
    
    private Usuario user;
    
    /**
     * Creates new form FrmImpresionCargos
     */
    
    
    public FrmImpresionCargos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.inicializar();
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    public void inicializar(){
        this.cargarResponsables();
                try { 
            conn = Conexion.obtener();
        } catch (SQLException ex) {
            Logger.getLogger(FrmLibroBienes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrmLibroBienes.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.btnImprimir.setEnabled(false);
        
    }
    
    public void cargarResponsables(){
        ResponsableJpaController responsableDao = new ResponsableJpaController();
        this.listaResponsables = responsableDao.findResponsableEntities();
        
        this.cmbResponsable.removeAllItems();
        this.cmbResponsable.addItem("Seleccione un Responsable");
        for (Responsable responsable:this.listaResponsables) {
            this.cmbResponsable.addItem(responsable.toString());
        }
        
    }
    
    public  void limpiarGrid(){
        if (this.modeloBienes.getRowCount() > 0) {
            for (int i = this.modeloBienes.getRowCount() - 1; i > -1; i--) {
               this.modeloBienes.removeRow(i);
            }
        }
    }
    
    public void colocarEncabezados(){
                String[] columnNames = {"Nro de Inventario",
                                "Descripcion",
                                "Estado",
                                "Valor"};
        this.modeloBienes.setColumnIdentifiers(columnNames);
        this.gridBienes.setModel(this.modeloBienes);
    }
    
    public void cargarAsignaciones(){
        AsignacionJpaController dao = new AsignacionJpaController();
        this.asignaciones = dao.findAsignacionesByResponsable(this.responsable);
        this.completarGrid();
    }
    
    public void completarGrid(){
        int cantBienes = this.asignaciones.size();
        int COLS = 4;
        Object[][] data = new Object[cantBienes][COLS];
        int rowIndex = 0;
        this.limpiarGrid();
        this.colocarEncabezados();
        if(cantBienes > 0 ){
            this.btnImprimir.setEnabled(true);
        }else{
            JOptionPane.showMessageDialog(null, "La Busqeda no produjo resultados");
        }

        for (Bien bien : this.asignaciones) {
            Integer nroInventario = bien.getNroInventario();
            
            data[rowIndex][0] = nroInventario;
            data[rowIndex][1] = bien.getDescripcion();
            data[rowIndex][2] = bien.getEstado(); 
            data[rowIndex][3] = "$"+ bien.getValor(); 

            modeloBienes.addRow(data[rowIndex]);
        }  
    }
    
    public void buscarAsignaciones(){
        this.setearResponsable();
        this.cargarAsignaciones();
    }
    
    public void setearResponsable(){
        ResponsableJpaController dao = new ResponsableJpaController();
        String nombre = this.cmbResponsable.getSelectedItem().toString();
        this.responsable = dao.findResponsableByNombre(nombre);
    }
    
    public void imprimirCargos(){
        this.setearParametrosReporte();
        this.mostrarReporte();
        
    }
    
    public void mostrarReporte(){
        String vpath = System.getenv().get("RUTAREPORTES")+"/"+this.reportName+".jasper";        
        AbsJasperReports.createReport(conn, vpath,parametros);
        AbsJasperReports.showViewer();
        this.setAlwaysOnTop(false);
    }
    
    public void setearParametrosReporte(){
        int cantidad=1 ;
        this.listaBienesImprimir = "";
        for (Bien a:this.asignaciones) {
            this.listaBienesImprimir = this.listaBienesImprimir +a.getNroInventario().toString()+",";
        }
        String  cadena=this.listaBienesImprimir;
        int m = Math.max(0, cadena.length() - cantidad);
        this.listaBienesImprimir = cadena.substring(0, cadena.length()-cantidad);

        this.parametros.put("responsable", this.responsable.getId().toString());
     
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
        jScrollPane1 = new javax.swing.JScrollPane();
        gridBienes = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Impresion Cargos - Patrimonio");

        jLabel1.setText("Responsable");

        cmbResponsable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbResponsable.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbResponsableItemStateChanged(evt);
            }
        });
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
        jScrollPane1.setViewportView(gridBienes);

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cmbResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 74, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(btnImprimir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbResponsableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbResponsableActionPerformed
        // TODO add your handling code here:    
    }//GEN-LAST:event_cmbResponsableActionPerformed

    private void cmbResponsableItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbResponsableItemStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cmbResponsableItemStateChanged

    private void cmbResponsableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbResponsableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbResponsableMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.btnImprimir.setEnabled(false);
        this.buscarAsignaciones();
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        this.imprimirCargos();
    }//GEN-LAST:event_btnImprimirActionPerformed

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
            java.util.logging.Logger.getLogger(FrmImpresionCargos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmImpresionCargos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmImpresionCargos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmImpresionCargos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmImpresionCargos dialog = new FrmImpresionCargos(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnImprimir;
    private javax.swing.JComboBox<String> cmbResponsable;
    private javax.swing.JTable gridBienes;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
