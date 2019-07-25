/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mdi;

import Negocio.Sector;
import Negocio.Usuario;
import Persistencia.SectorJpaController;
import Persistencia.exceptions.NonexistentEntityException;
import Presentacion.CtrlVista;
import Presentacion.FrmSector;
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
public class jpFormSectores extends javax.swing.JPanel {
    private final CtrlVista logica = new CtrlVista();
    //private java.awt.Frame miparent;
    private MainMdi miparent;
    private DefaultListModel modeloAreas = new DefaultListModel();
    private boolean nuevoSector;
    private Sector unSector;
    private SectorJpaController dao = new SectorJpaController();
    private Usuario user;
    //private Ct
    
    
    /**
     * Creates new form jpFormSectores
     */
    public jpFormSectores() {
        initComponents();
        this.inicializar();
    }

    public void setParent(MainMdi miparent) {
        this.miparent = miparent;
    }
    public void inicializar(){
        this.cargarListaAreas();
        
        
    }
    
    public final void cargarListaAreas(){
        this.modeloAreas.clear();
        List<Sector> sectores = dao.findSectorEntities();
        for (Sector sector:sectores) {
            this.modeloAreas.addElement(sector); //Test: deberia agregar areas
        }
        this.lstSectores.setModel(modeloAreas);
    }
    
    
        public void setUser(Usuario user) {
        this.user = user;
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
            int sectorId = this.dao.findSectorByNombre(this.lstSectores.getSelectedValue()).getId();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        lstSectores = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        lstSectores.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstSectores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstSectoresMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(lstSectores);

        jLabel2.setText("Nombre");

        txtNombre.setEnabled(false);
        txtNombre.setName("txtNombre"); // NOI18N

        btnModificar.setText("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.setName("btnModificar"); // NOI18N
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
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

        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.setName("btnGuardar"); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
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

        btnNuevo.setText("Nuevo");
        btnNuevo.setName("btnNuevo"); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.setName("btnSalir"); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(12, 12, 12)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar)
                            .addComponent(btnCancelar)
                            .addComponent(btnModificar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSalir)
                            .addComponent(btnNuevo)
                            .addComponent(btnEliminar)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(this.nuevoSector){
            createSector();
        }else{
            updateSector();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el Sector seleccionado?", "Confirmación", JOptionPane.YES_NO_OPTION);
        switch(respuesta) {
            case JOptionPane.YES_OPTION:
            try {
                //--- Operaciones en caso afirmativo
                //CtrlVista.borrarSector(this.unSector);
            //   Sector sector = this.lstSectores.getSelectedValue();
                Sector sector = this.dao.findSectorByNombre(this.lstSectores.getSelectedValue());
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
              //  this.dispose();
                this.miparent.ocultarSectores();
                break;
            }
        }else{
            this.miparent.ocultarSectores();
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void lstSectoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstSectoresMouseClicked
        // TODO add your handling code here:
                if(!this.modeloAreas.isEmpty()){
            this.seleccionarSector();
            this.activarBM(true);

        }
    }//GEN-LAST:event_lstSectoresMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> lstSectores;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
