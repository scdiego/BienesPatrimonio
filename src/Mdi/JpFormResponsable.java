/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mdi;

import Negocio.Responsable;
import Negocio.Sector;
import Negocio.Usuario;
import Persistencia.ResponsableJpaController;
import Persistencia.SectorJpaController;
import Persistencia.exceptions.NonexistentEntityException;
import Presentacion.CtrlVista;
import Presentacion.FrmResponsable;
import java.awt.HeadlessException;
import java.util.List;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author diego
 */
public class JpFormResponsable extends javax.swing.JPanel {

    /**
     * Creates new form JpFormResponsable
     */
    
    
    private final CtrlVista logica = new CtrlVista();
    //private java.awt.Frame miparent;
    private MainMdi parent;
    private DefaultListModel modeloResponsables = new DefaultListModel();
    private boolean nuevoResponsable;
    private Responsable unResponsable;
    private ResponsableJpaController dao = new ResponsableJpaController();
    private Usuario user;
    
    
    
    private final SectorJpaController sectorDao = new SectorJpaController();
    
    public JpFormResponsable() {
        initComponents();
        this.inicializar();
    }

    public void setParent(MainMdi parent) {
        this.parent = parent;
    }
    public void SetUser(Usuario user){
        this.user=user;
    }
    
    public void inicializar(){
        this.cargarResponsables();
        this.fillAreas();
    }
    
    public void cargarResponsables(){
        /**
         *         this.modeloAreas.clear();
        List<Sector> sectores = dao.findSectorEntities();
        for (Sector sector:sectores) {
            this.modeloAreas.addElement(sector); //Test: deberia agregar areas
        }
        this.lstSectores.setModel(modeloAreas);
         */
        
        this.modeloResponsables.clear();
        List<Responsable> responsables = dao.findResponsableEntities();
        for(Responsable responsable:responsables){
            this.modeloResponsables.addElement(responsable);
        }
        this.lstResponsables.setModel(modeloResponsables);
    }
    public void setUnResponsable(Responsable unResponsable) {
        this.unResponsable = unResponsable;
    }
    
        public final void limpiarCampos(){
        this.txtDni.setText(null);
        this.txtResponsable.setText(null);
        this.txtCargo.setText(null);
    }
    public final void habilitarCampos(){
        this.txtDni.setEnabled(true);
        this.txtResponsable.setEnabled(true);
        this.txtCargo.setEnabled(true);
    }
    public final void deshabilitarCampos(){
        this.txtDni.setEnabled(false);
        this.txtResponsable.setEnabled(false);
        this.btnGuardar.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        this.btnNuevo.setEnabled(true);
        this.txtCargo.setEnabled(false);
        
              
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
    private void cleanForm() {
       cargarResponsables();
       activarComponentes(true);
       limpiarCampos();
       deshabilitarCampos();
    }
    private boolean responsibleExist() {
        String dni = this.txtDni.getText();
        return (dao.findResponsableByDNI(dni) != null);
    }
    
        private Responsable getResponsibleFromForm() {
        String responsable = txtResponsable.getText();
        String dni = this.txtDni.getText();
        String cargo = this.txtCargo.getText();
        Sector sector = (Sector) this.lstSectores.getSelectedItem();
        return new Responsable(responsable, dni, cargo, sector);
    }
    
    public void seleccionarResponsable(){
        this.unResponsable = (Responsable) this.modeloResponsables.getElementAt(this.lstResponsables.getSelectedIndex());
        this.completarCampos();
    }
    
    public void completarCampos(){
        this.txtResponsable.setText(this.unResponsable.getNombre());
        this.txtCargo.setText(this.unResponsable.getCargo());
        this.txtDni.setText(this.unResponsable.getDni());

        try{
             this.lstSectores.setSelectedItem(this.unResponsable.getSector().getNombre());    
        }
        catch(NullPointerException e){
            // no hago nada
        }
        //test.setSelectedItem("manzanas");
        
    }
    
        
        
            private void saveModifications() {
       // if(this.camposRequeridos()){
            Integer id = this.unResponsable.getId();
            try {
                dao.update(getResponsibleFromForm(), id);
                cleanForm();
            } catch (NonexistentEntityException ex) {
                JOptionPane.showMessageDialog(this, "Error", ex.getMessage(), JOptionPane.WARNING_MESSAGE);
                //Logger.getLogger(JpFormResponsable.class.getName()).log(Level.SEVERE, null, ex);
            
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

        jScrollPane1 = new javax.swing.JScrollPane();
        lstResponsables = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtResponsable = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lstSectores = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCargo = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();

        lstResponsables.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstResponsables.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstResponsablesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstResponsables);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel1.setText("Responsables");

        jLabel2.setText("Apellido y Nombres");

        txtResponsable.setEnabled(false);
        txtResponsable.setName("txtResponsable"); // NOI18N

        jLabel4.setText("Sectores de Trabajo");

        jLabel3.setText("Dni");

        txtDni.setEnabled(false);
        txtDni.setName("txtDni"); // NOI18N

        jLabel5.setText("Cargo");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
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
                                    .addComponent(txtCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 223, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(91, 91, 91))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.deshabilitarCampos();
        this.limpiarCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

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

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        if(this.btnGuardar.isEnabled()){
            int respuesta = JOptionPane.showConfirmDialog(null, "Está editando un Responsable.\n¿Desea salir de todos modos?", "Confirmación", JOptionPane.YES_NO_OPTION);
            switch(respuesta) {
                case JOptionPane.YES_OPTION:
                //--- Operaciones en caso afirmativo
              //  this.dispose();
                this.parent.ocultarResponsalbe();
                    break;
            }
        }else{
            this.parent.ocultarResponsalbe();
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        this.habilitarCampos();
        this.activarComponentes(true);
        this.nuevoResponsable = true;
        this.limpiarCampos();
        this.activarBM(false);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        this.nuevoResponsable = false;
        this.activarComponentes(true);
        this.activarBM(false);
        this.habilitarCampos();
    }//GEN-LAST:event_btnModificarActionPerformed

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
        
        this.deshabilitarCampos();
        this.limpiarCampos();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void lstResponsablesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstResponsablesMouseClicked
        // TODO add your handling code here:
        /**
         *                 if(!this.modeloAreas.isEmpty()){
            this.seleccionarSector();
            this.activarBM(true);
         */
        
        if(!this.modeloResponsables.isEmpty()){
            this.seleccionarResponsable();
            this.activarBM(true);
        }
    }//GEN-LAST:event_lstResponsablesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lstResponsables;
    private javax.swing.JComboBox<String> lstSectores;
    private javax.swing.JTextField txtCargo;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtResponsable;
    // End of variables declaration//GEN-END:variables
}
