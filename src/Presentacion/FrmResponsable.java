package Presentacion;

import Negocio.Responsable;
import Negocio.Sector;
import Negocio.Usuario;
import Persistencia.*;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author diego
 */
public class FrmResponsable extends javax.swing.JDialog {

    /**
     * Creates new form FrmResponsable
     */
    
    private CtrlVista logica = new CtrlVista();
    private DefaultListModel modeloResponsables = new DefaultListModel();
    private boolean nuevoResponsable;
    private Responsable unResponsable;
    private ResponsableJpaController dao = new ResponsableJpaController();
    private final SectorJpaController sectorDao = new SectorJpaController();
    private Usuario user;
    
    public FrmResponsable(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        fillAreas();
        //this.cargarListaResponsables();
        //this.lstResponsables.setModel(modeloResponsables);
        //cargarListaResponsables();
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    private void fillAreas() {
        List<Sector> sectores = sectorDao.findSectorEntities();
        lstSectores.setModel(new DefaultComboBoxModel(sectores.toArray()));
    }
    
    public final void activarComponentes(boolean activar){
       this.btnNuevo.setEnabled(!activar);
       this.btnSalir.setEnabled(activar);
       this.btnModificar.setEnabled(!activar);
       this.btnCancelar.setEnabled(activar);
       this.btnEliminar.setEnabled(!activar);
       this.btnGuardar.setEnabled(activar);
    }
    public final void cargarListaResponsables(){
        this.modeloResponsables.clear();
        List<Responsable> responsables = dao.findResponsableEntities();
        for(Responsable responsable:responsables) {
            modeloResponsables.addElement(responsable);
        }
        
    }

    public final void limpiarCampos(){
        this.txtDni.setText(null);
        this.txtResponsable.setText(null);
    }
    public final void habilitarCampos(){
        this.txtDni.setEnabled(true);
        this.txtResponsable.setEnabled(true);
    }
    public final void deshabilitarCampos(){
        this.txtDni.setEnabled(false);
        this.txtResponsable.setEnabled(false);
        this.btnGuardar.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        this.btnNuevo.setEnabled(true);
              
    }
    public final void activarBM(boolean activar){
        this.btnModificar.setEnabled(activar);
        this.btnEliminar.setEnabled(activar);
    }
    public final boolean camposRequeridos(){
        boolean retorno = false;
        int i = 0;
        i += this.txtDni.getText().equals("") ? 0 : 1;
        i += this.txtResponsable.getText().equals("") ? 0 : 1;
        if(i == 2) {
            retorno = true;
        }
        return retorno;
    }   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtResponsable = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        lstSectores = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCargo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Responsables - Patrimonio");
        setResizable(false);

        jLabel2.setText("Apellido y Nombres");

        txtResponsable.setEnabled(false);
        txtResponsable.setName("txtResponsable"); // NOI18N

        jLabel3.setText("Dni");

        txtDni.setEnabled(false);
        txtDni.setName("txtDni"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.setName("btnCancelar"); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.setName("btnEliminar"); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.setName("btnSalir"); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.setName("btnNuevo"); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.setName("btnModificar"); // NOI18N
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.setName("btnGuardar"); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar)
                    .addComponent(btnModificar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir)
                    .addComponent(btnNuevo)
                    .addComponent(btnEliminar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setText("Sectores de Trabajo");

        jLabel5.setText("Cargo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtResponsable)
                                    .addComponent(lstSectores, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3)
                                    .addComponent(txtDni)
                                    .addComponent(txtCargo, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(103, 103, 103))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lstSectores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        this.habilitarCampos();
        this.activarComponentes(true);
        this.nuevoResponsable = true;
        this.limpiarCampos();
        this.activarBM(false);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        if(this.btnGuardar.isEnabled()){
            int respuesta = JOptionPane.showConfirmDialog(null, "Está editando un Responsable.\n¿Desea salir de todos modos?", "Confirmación", JOptionPane.YES_NO_OPTION);
            switch(respuesta) {
                case JOptionPane.YES_OPTION:
                    //--- Operaciones en caso afirmativo
                    this.dispose();
                    break;
            }            
        }else{
            this.dispose();
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.deshabilitarCampos();
        this.limpiarCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        this.nuevoResponsable = false;
        this.activarComponentes(true);
        this.activarBM(false);
        this.habilitarCampos();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
    /**
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el Responsable seleccionado?", "Confirmación", JOptionPane.YES_NO_OPTION);
        switch(respuesta) {
            case JOptionPane.YES_OPTION:
                try {
                    //--- Operaciones en caso afirmativo
                   // Responsable responsable = lstResponsables.getSelectedValue();
                  //  dao.destroy(responsable, responsable.getId());
                    this.cargarListaResponsables();
                    this.limpiarCampos();
                    this.activarBM(false);
                    JOptionPane.showMessageDialog(null, "El Responsable ha sido eliminado.");
                } catch (NonexistentEntityException | HeadlessException ex) {
                    JOptionPane.showMessageDialog(null, "No se puede eliminar el Responsable:" + ex.getMessage());
                }
                break;
        }
        */ 
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void cleanForm() {
       cargarListaResponsables();
       activarComponentes(true);
       limpiarCampos();
       deshabilitarCampos();
    }

    private void saveModifications() {
        if(this.camposRequeridos()){
       /**     Integer id = lstResponsables.getSelectedValue().getId();
            try {
                dao.update(getResponsibleFromForm(), id);
                cleanForm();
            } catch (NonexistentEntityException ex) {
                JOptionPane.showMessageDialog(this, "Error", ex.getMessage(), JOptionPane.WARNING_MESSAGE);
                Logger.getLogger(FrmResponsable.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
    }

    private Responsable getResponsibleFromForm() {
        String responsable = txtResponsable.getText();
        String dni = this.txtDni.getText();
        String cargo = this.txtCargo.getText();
        Sector sector = (Sector) this.lstSectores.getSelectedItem();
        return new Responsable(responsable, dni, cargo, sector);
    }
    
    private boolean responsibleExist() {
        String dni = this.txtDni.getText();
        return (dao.findResponsableByDNI(dni) != null);
    }
    
    private void saveNewResponsible() {
      if (!responsibleExist()) {
        if(this.camposRequeridos()){
          dao.create(getResponsibleFromForm());
          cleanForm();
          JOptionPane.showMessageDialog(this, "Se ha agregado un nuevo responsable exitosamente", 
                  "Exito", 
                  JOptionPane.INFORMATION_MESSAGE);
        } else{
              JOptionPane.showMessageDialog(this,
                      "No se puede guardar la infomación.\nDebe completar los datos requeridos.", 
                      "Algo salió mal", 
                      JOptionPane.WARNING_MESSAGE);
              this.txtResponsable.requestFocus(true);
          }    
      } else {
          JOptionPane.showMessageDialog(this,
                  "Ya existe un responsable registrado con ese DNI.",
                  "Algo salió mal", 
                  JOptionPane.WARNING_MESSAGE);
          this.txtDni.requestFocus(true);
      }     
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try{
            if(this.nuevoResponsable) {
                saveNewResponsible();
            } else{
                saveModifications();
            }
        }catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(null,
                    "No se puede guardar la información: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(FrmResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmResponsable dialog = new FrmResponsable(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> lstSectores;
    private javax.swing.JTextField txtCargo;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtResponsable;
    // End of variables declaration//GEN-END:variables
}