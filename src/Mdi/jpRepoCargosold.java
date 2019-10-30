/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mdi;

import Negocio.Bien;
import Negocio.Inventario;
import Negocio.Responsable;
import Negocio.Usuario;
import Persistencia.ResponsableJpaController;
import Presentacion.FrmLibroBienes;
import Utilidades.ConsultasDB;
import Utilidades.FechaHora;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author diego
 */
public class jpRepoCargosold extends javax.swing.JPanel {

    /**
     * Creates new form jpRepoCargos
     */
    MainMdi parent;
    DefaultListModel modeloResponsables = new DefaultListModel();
    List<Responsable> listaResponsables = new ArrayList();
    Responsable responsable;
    DefaultTableModel tblBienes = new DefaultTableModel();
    public ArrayList<Bien> listadeBienes = new ArrayList();
    List<Bien> asignaciones = new ArrayList();
    DefaultTableModel modeloBienes = new DefaultTableModel();
    String listaBienesImprimir;
    private Connection conn;
    public String reportName = "PlanilladeCargos";
    Map<String,Object> parametros = new HashMap();
    
    private Usuario user;
    
    
    public jpRepoCargosold() {
        initComponents();
        this.inicializar();
    }

    public MainMdi getParent() {
        return parent;
    }

    public void setParent(MainMdi parent) {
        this.parent = parent;
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
        
    public void buscar(){
        findByResponsable(false);
    }
    
        public final void colocarEncabezadoGrid(){
        String[] columnNames = {"Nro de Inventario",
                                "Descripcion",
                                "Estado",
                                "Responsable",
                                "Area",
                                "Fecha"};
        this.tblBienes.setColumnIdentifiers(columnNames);
        this.gridBienes.setModel(tblBienes);
    }    
    public final void cargarGrid(){
        Inventario inv = new Inventario();
        int cantBienes = this.listadeBienes.size();
        int COLS = 6;
        Object[][] data = new Object[cantBienes][COLS];
        int rowIndex = 0;
        FechaHora fecha = new FechaHora();
        
        //this.limpiarGrid();
        //this.colocarEncabezadoGrid();
        for (Bien bien : this.listadeBienes) {
            Integer nroInventario = bien.getNroInventario();
            data[rowIndex][0] = nroInventario;
            data[rowIndex][1] = bien.getDescripcion();
            data[rowIndex][2] = bien.getEstado();    
            data[rowIndex][5] = fecha.DateToString(bien.getFechaAlta());
            Responsable responsableBien = inv.responsableBienID(bien.getId());
            //Responsable responsableBien = null;
            addResponsableDataToRow(data, rowIndex, responsableBien);           
            tblBienes.addRow(data[rowIndex]);
        }
    }    

    private void addResponsableDataToRow(Object[][] data, int rowIndex, Responsable responsableBien) {
        if (responsableBien == null) {
            data[rowIndex][3] = "-";
            data[rowIndex][4] = "-";
        }
        else {
            data[rowIndex][3] = responsableBien.getNombre();
            data[rowIndex][4] = responsableBien.getSector();
        }
    }
    
    private void findByResponsable(boolean baja) {
        ConsultasDB consulta = new ConsultasDB();
        String responsableStr = cmbResponsable.getSelectedItem().toString();
        String strBaja = "";
        if(baja){
           strBaja = " AND BIEN.DEBAJA = true ";
        }
        String sql = "SELECT LPAD( BIEN.NROINVENTARIO, 6, '0' ) as NROINVENTARIO,BIEN.DESCRIPCION,BIEN.ESTADO,RESPONSABLE.NOMBRE as RESPONSABLE,SECTOR.NOMBRE,DATE_FORMAT(BIEN.FECHAALTA,'%d/%m/%Y') as FECHAALTA FROM ASIGNACION INNER JOIN BIEN ON ASIGNACION.BIEN_ID = BIEN.ID LEFT JOIN RESPONSABLE ON ASIGNACION.RESPONSABLE_ID = RESPONSABLE.ID LEFT JOIN SECTOR ON RESPONSABLE.SECTOR_ID = SECTOR.ID WHERE RESPONSABLE.NOMBRE = '" + responsableStr + "' AND ASIGNACION.fechaHasta is null"+ strBaja + " order by NROINVENTARIO";
        this.parametros.put("sql", sql);
        List<String> campos = new ArrayList();
        campos.add("NROINVENTARIO");
        campos.add("DESCRIPCION");
        campos.add("ESTADO");
        campos.add("RESPONSABLE");
        campos.add("NOMBRE");
        campos.add("FECHAALTA");
        Object[][] lista = consulta.ejcutarConsulta(sql, campos);
        mostrarConsulta(lista);
    }
    
    public void mostrarConsulta(Object[][] lista){
        
        int i;
        if (lista.length > 0){
            for(i=0 ; i < lista.length; i++ ){
                tblBienes.addRow(lista[i]);
            }
        }else{
           showNoResults();
        }

        
    }
    public void showNoResults(){
        //Imprimo sin resultados
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
        jButton1 = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        tblAsignaciones = new javax.swing.JScrollPane();
        gridBienes = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        jLabel1.setText("Responsable");

        cmbResponsable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbResponsable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbResponsableActionPerformed(evt);
            }
        });

        jButton1.setText("Buscar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnImprimir.setText("Imprimir");

        gridBienes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblAsignaciones.setViewportView(gridBienes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tblAsignaciones, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(tblAsignaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton2.setText("jButton2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbResponsable, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImprimir))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.buscar();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmbResponsableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbResponsableActionPerformed
        // TODO add your handling code here:
        String var = "";
        var = var.concat("dae");
        
    }//GEN-LAST:event_cmbResponsableActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        this.buscar();
    }//GEN-LAST:event_jButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimir;
    private javax.swing.JComboBox<String> cmbResponsable;
    private javax.swing.JTable gridBienes;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane tblAsignaciones;
    // End of variables declaration//GEN-END:variables
}
