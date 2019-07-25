/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Mdi;

import Negocio.Bien;
import Negocio.Responsable;
import Negocio.Usuario;
import Persistencia.AsignacionJpaController;
import Persistencia.ResponsableJpaController;
import Presentacion.FrmLibroBienes;
import Reportes.AbsJasperReports;
import Utilidades.ConsultasDB;
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
public class JpRepoImpresionCargos extends javax.swing.JPanel {

    /** Creates new form JpRepoImpresionCargos */
        DefaultListModel modeloResponsables = new DefaultListModel();
    List<Responsable> listaResponsables = new ArrayList();
    Responsable responsable;
    
    List<Bien> asignaciones = new ArrayList();
    DefaultTableModel modeloBienes = new DefaultTableModel();
    String listaBienesImprimir;
    private Connection conn;
    public String reportName = "PlanilladeCargos";
    Map<String,Object> parametros = new HashMap();
    //1159073557
    private Usuario user;
    public MainMdi parent;
    
    public JpRepoImpresionCargos() {
        initComponents();
        this.cargarResponsables();
        this.inicializar();
    }

    public void setParent(MainMdi parent) {
        this.parent = parent;
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
    
    public void cargarAsignaciones(){
        AsignacionJpaController dao = new AsignacionJpaController();
        this.asignaciones = dao.findAsignacionesByResponsable(this.responsable);
      /*  ConsultasDB consulta = new ConsultasDB();
        String id_responsable = responsable.getId().toString();

        String sql = "SELECT BIEN.NROINVENTARIO,BIEN.DESCRIPCION,BIEN.ESTADO,BIEN.VALOR FROM ASIGNACION INNER JOIN BIEN ON ASIGNACION.BIEN_ID = BIEN.ID WHERE ASIGNACION.RESPONSABLE_ID = " + id_responsable ;
       
      //   JOptionPane.showMessageDialog(this,sql,"",JOptionPane.INFORMATION_MESSAGE);
         
        List<String> campos = new ArrayList();
        campos.add("NROINVENTARIO");
        campos.add("DESCRIPCION");
        campos.add("ESTADO");
        campos.add("VALOR");
        
        Object[][] lista = consulta.ejcutarConsulta(sql, campos);
        mostrarConsulta(lista);*/
        this.completarGrid();
    }
    public void mostrarConsulta(Object[][] lista){
        
        int i;
        if (lista.length > 0){
                    for(i=0 ; i < lista.length; i++ ){
            modeloBienes.addRow(lista[i]);
        }
        }else{
           showNoResults("");
        }

        
    }
    
        private void showNoResults(String campo) {
        JOptionPane.showMessageDialog(this,
                "No se encontraron bienes" + campo,        
                "Algo salió mal",                        
                 JOptionPane.INFORMATION_MESSAGE);

    } 
    public void setearResponsable(){
        ResponsableJpaController dao = new ResponsableJpaController();
        String nombre = this.cmbResponsable.getSelectedItem().toString();
        this.responsable = dao.findResponsableByNombre(nombre);
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
    
    public void mostrarReporte(){
        String vpath = System.getenv().get("RUTAREPORTES")+"/"+this.reportName+".jasper";        
        AbsJasperReports.createReport(conn, vpath,parametros);
        AbsJasperReports.showViewer();

    }

    
    public void imprimirCargos(){
        this.setearParametrosReporte();
        this.mostrarReporte();
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cmbResponsable = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        gridBienes = new javax.swing.JTable();

        jLabel1.setText("Responsable");

        cmbResponsable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbResponsable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbResponsableActionPerformed(evt);
            }
        });

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImprimir)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.btnImprimir.setEnabled(false);
        this.buscarAsignaciones();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
         this.imprimirCargos();
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void cmbResponsableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbResponsableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbResponsableActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimir;
    private javax.swing.JComboBox<String> cmbResponsable;
    private javax.swing.JTable gridBienes;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
