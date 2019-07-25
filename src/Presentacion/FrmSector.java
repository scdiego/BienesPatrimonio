package Presentacion;

import Negocio.Sector;
import Negocio.Usuario;
import Persistencia.SectorJpaController;
import Persistencia.exceptions.NonexistentEntityException;
import java.awt.HeadlessException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author diego
 */
public class FrmSector extends javax.swing.JDialog {

    private CtrlVista logica = new CtrlVista();
    private DefaultListModel modeloAreas = new DefaultListModel();
    private boolean nuevoSector;
    private Sector unSector;
    private SectorJpaController dao = new SectorJpaController();
    private Usuario user;

    
    public FrmSector(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cargarListaAreas();                
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    public final void cargarListaAreas(){
        this.modeloAreas.clear();
        List<Sector> sectores = dao.findSectorEntities();
        for (Sector sector:sectores) {
            this.modeloAreas.addElement(sector); //Test: deberia agregar areas
        }
        this.lstSectores.setModel(modeloAreas);
    }
    public final void habilitarCampos(boolean habilitar){
        this.txtNombre.setEnabled(habilitar);  
    }
    public final boolean camposRequeridos(){
        boolean retorno = false;
        int i = 0;
        
        i += this.txtNombre.getText().equals("") ? 0 : 1;
        retorno = (i == 1) ? true : false;
        
        return retorno;
    }
    public final void limpiarComponentes(){
        this.txtNombre.setText(null);
    }
    public final void seleccionarSector(){        
        unSector = (Sector) this.modeloAreas.getElementAt(this.lstSectores.getSelectedIndex());
        this.txtNombre.setText(this.unSector.getNombre());
    }
    public final void activarBM(boolean activar){
        this.btnEliminar.setEnabled(activar);
        this.btnModificar.setEnabled(activar);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstSectores = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sectores - Patrimonio");
        setResizable(false);

        jLabel1.setText("Sectores");

        lstSectores.setName("lstSectores"); // NOI18N
        lstSectores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstSectoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstSectores);

        jLabel2.setText("Nombre");

        txtNombre.setEnabled(false);
        txtNombre.setName("txtNombre"); // NOI18N

        btnEliminar.setText("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.setName("btnEliminar"); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
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

        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.setName("btnCancelar"); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.setName("btnSalir"); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addContainerGap(289, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(179, 179, 179)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnModificar)
                            .addComponent(btnGuardar)
                            .addComponent(btnCancelar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSalir)
                            .addComponent(btnNuevo)
                            .addComponent(btnEliminar))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        this.btnCancelar.setEnabled(true);
        this.btnEliminar.setEnabled(false);
        this.btnGuardar.setEnabled(true);
        this.btnModificar.setEnabled(false);
        this.btnNuevo.setEnabled(false);
        this.btnSalir.setEnabled(true);       
        this.nuevoSector = true;
        this.habilitarCampos(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        if(this.btnGuardar.isEnabled()){
            int respuesta = JOptionPane.showConfirmDialog(null, "Está editando un Sector.\n¿Desea salir de todos modos?", "Confirmación", JOptionPane.YES_NO_OPTION);
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

    private Sector getSectorFromForm() {
        return new Sector(this.txtNombre.getText());
    }
    
    private void createSector() {
        if(this.camposRequeridos()){
            //CtrlVista.crearSector(this.txtNombre.getText());            
            dao.create(getSectorFromForm());
            this.cargarListaAreas();
            this.btnCancelar.setEnabled(false);
            this.btnEliminar.setEnabled(false);
            this.btnGuardar.setEnabled(false);
            this.btnModificar.setEnabled(false);
            this.btnNuevo.setEnabled(true);
            this.btnSalir.setEnabled(true);
            this.habilitarCampos(false);
            this.limpiarComponentes();
        } else{
            JOptionPane.showMessageDialog(null, "No se puede guardar la infomación.\nDebe completar los datos requeridos.");
            this.txtNombre.requestFocus(true);
        }
    }
    
    private void updateSector() {
        if(this.camposRequeridos()){                
            //CtrlVista.modificarSector(this.unSector,this.txtNombre.getText());
            Sector newSector = new Sector(this.txtNombre.getText());
            int sectorId = this.lstSectores.getSelectedValue().getId();
            try {
                dao.update(newSector, sectorId);
                this.cargarListaAreas();                   
                this.btnCancelar.setEnabled(false);
                this.btnEliminar.setEnabled(false);
                this.btnGuardar.setEnabled(false);
                this.btnModificar.setEnabled(false);
                this.btnNuevo.setEnabled(true);
                this.btnSalir.setEnabled(true);
                this.habilitarCampos(false);
                this.limpiarComponentes();
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(FrmSector.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Algo salió mal", JOptionPane.WARNING_MESSAGE);
            }

        } else{
            JOptionPane.showMessageDialog(null, "No se puede guardar la infomación.\nDebe completar los datos requeridos.");
            this.txtNombre.requestFocus(true);
        }
    }
    
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(this.nuevoSector){
            createSector();
        }else{
            updateSector();            
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void lstSectoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstSectoresMouseClicked
        if(!this.modeloAreas.isEmpty()){
            this.seleccionarSector();
            this.activarBM(true);
        }
    }//GEN-LAST:event_lstSectoresMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        this.nuevoSector = false;
        this.txtNombre.setEnabled(true);
        this.btnCancelar.setEnabled(true);
        this.btnEliminar.setEnabled(false);
        this.btnGuardar.setEnabled(true);
        this.btnModificar.setEnabled(false);
        this.btnNuevo.setEnabled(false);
        this.btnSalir.setEnabled(true);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el Sector seleccionado?", "Confirmación", JOptionPane.YES_NO_OPTION);
        switch(respuesta) {
            case JOptionPane.YES_OPTION:
                try {
                    //--- Operaciones en caso afirmativo
                    //CtrlVista.borrarSector(this.unSector);
                    Sector sector = this.lstSectores.getSelectedValue();
                    dao.destroy(sector, sector.getId());
                    this.cargarListaAreas();
                    this.limpiarComponentes();
                    this.activarBM(false);
                    JOptionPane.showMessageDialog(null, "El Sector ha sido eliminado.");
                } catch (NonexistentEntityException | HeadlessException ex) {
                    JOptionPane.showMessageDialog(null, "No se puede eliminar el Sector:" + ex.getMessage());
                }
                   this.btnCancelar.setEnabled(false);
                   this.btnEliminar.setEnabled(false);
                   this.btnGuardar.setEnabled(false);
                   this.btnModificar.setEnabled(false);
                   this.btnNuevo.setEnabled(true);
                   this.btnSalir.setEnabled(true);
                   this.habilitarCampos(false);
                break;
        }
        
        
        
        
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea cancelar la edición del Area?", "Confirmación", JOptionPane.YES_NO_OPTION);
        switch(respuesta) {
            case JOptionPane.YES_OPTION:
                //--- Operaciones en caso afirmativo
                this.limpiarComponentes();
                this.btnCancelar.setEnabled(false);
                this.btnEliminar.setEnabled(false);
                this.btnGuardar.setEnabled(false);
                this.btnModificar.setEnabled(false);
                this.btnNuevo.setEnabled(true);
                this.btnSalir.setEnabled(true);
                this.habilitarCampos(false);
                this.activarBM(false);
                break;
        } 
        
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(FrmSector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmSector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmSector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmSector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmSector dialog = new FrmSector(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<Sector> lstSectores;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
