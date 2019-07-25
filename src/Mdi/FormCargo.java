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
import Presentacion.FrmCargo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author ramiel
 */



public class FormCargo extends javax.swing.JPanel {

    /**
     * Creates new form FormCargo
     */
    
    ResponsableJpaController responsableDao = new ResponsableJpaController();
    AsignacionJpaController asignacionDao = new AsignacionJpaController();
    Bien unBien;
    Usuario user;
    MainMdi parent;
    
    public FormCargo() {
        initComponents();
        this.inicializar();
    }
    
    public void inicializar(){
        this.fillResponsables();
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void setParent(MainMdi parent) {
        this.parent = parent;
    }

    public void setUnBien(Bien unBien) {
        this.unBien = unBien;
    }
    
    private void actualizarVentana(String descripcion) {
        txtDescripcion.setText(descripcion);
        btnGuardar.setEnabled(true);
    } 
    public void buscar(){
        BienJpaController bienDao = new BienJpaController();
        int nroInv = Integer.parseInt(txtNroInventario.getText());
        List<Bien> bienes = bienDao.findBienByNroInventario(nroInv);
        unBien = bienes.get(0);
        if(!unBien.isDebaja()){
            actualizarVentana(unBien.getDescripcion());
            this.lstResponsable.setEnabled(!unBien.isDebaja());
            this.lstSubResponsable.setEnabled(!unBien.isDebaja());
            this.btnGuardar.setEnabled(!unBien.isDebaja());
        }else{
            JOptionPane.showMessageDialog(this, 
                    "El Bien seleccionado fue dado de baja, no es posible asignarlo",
                    "Algo salió mal", 
                    JOptionPane.WARNING_MESSAGE);
        }
        
    }
    private Asignacion getAsignacionFromForm() {
        Responsable resp = null;
        Responsable subResp = null;
        try{
            resp = (Responsable) lstResponsable.getSelectedItem();
        }catch(ClassCastException ex){
                
                JOptionPane.showMessageDialog(null, "Seleccione un Responsable");
        }        
        
        try{
        subResp = (Responsable) lstSubResponsable.getSelectedItem();
        
        }catch(ClassCastException ex){
                
                // JOptionPane.showMessageDialog(null, "Seleccione un Sub Responsable");
        } 
        String strDate = new SimpleDateFormat("yyyy-dd-MM").format(Calendar.getInstance().getTime());
        Date fechaDesde = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
            fechaDesde = sdf.parse(strDate);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, 
                    "El formato de la fecha es incorrecto",
                    "Algo salió mal", 
                    JOptionPane.WARNING_MESSAGE);
            Logger.getLogger(FrmCargo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Asignacion(resp, this.unBien, fechaDesde, subResp);
    }  
    public void asignar(){
        try{
            Asignacion asignacion = getAsignacionFromForm();
            if(asignacion.getResponsable() != null){
                asignacionDao.create(asignacion,this.user,this.unBien);
            
                JOptionPane.showMessageDialog(null, "El bien se ha asignado al responsable.");
                this.txtNroInventario.setText(null);
                this.unBien = null;
                this.txtDescripcion.setText(null);
                this.lstResponsable.setSelectedItem("Seleccione un Responsable");
                this.lstSubResponsable.setSelectedItem("Seleccione un Sub Responsable");
            }

        }catch(ClassCastException ex){
                
                JOptionPane.showMessageDialog(null, "Error.");
        }
        
        //System.out.println();
    }
    
    
    private void fillResponsables() {
        List<Responsable> responsables = responsableDao.findResponsableEntities();
        lstResponsable.setModel(new DefaultComboBoxModel(responsables.toArray()));        
        lstSubResponsable.setModel(new DefaultComboBoxModel(responsables.toArray()));
        this.lstResponsable.addItem("Seleccione un Responsable");
        this.lstSubResponsable.addItem("Seleccione un Sub Responsable");
        
        this.lstResponsable.setSelectedItem("Seleccione un Responsable");
        this.lstSubResponsable.setSelectedItem("Seleccione un Sub Responsable");
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
        txtNroInventario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lstResponsable = new javax.swing.JComboBox<>();
        lstSubResponsable = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        btnGuardar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jLabel1.setText("Nro de Inventario");

        txtNroInventario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNroInventarioKeyPressed(evt);
            }
        });

        jLabel2.setText("Responsable");

        jLabel3.setText("Sub Responsable");

        jLabel4.setText("Descripcion del Bien");

        lstResponsable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lstSubResponsable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtDescripcion.setEditable(false);
        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lstSubResponsable, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNroInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 122, Short.MAX_VALUE))
                            .addComponent(lstResponsable, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNroInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lstResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lstSubResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNroInventarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroInventarioKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            this.buscar();
        }
    }//GEN-LAST:event_txtNroInventarioKeyPressed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        this.asignar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.parent.ocultarCargo();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> lstResponsable;
    private javax.swing.JComboBox<String> lstSubResponsable;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtNroInventario;
    // End of variables declaration//GEN-END:variables
}
