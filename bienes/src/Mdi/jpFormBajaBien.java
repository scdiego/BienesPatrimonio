/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mdi;

import Negocio.Bien;
import Negocio.Usuario;
import Persistencia.BienJpaController;
import Persistencia.exceptions.NonexistentEntityException;
import Utilidades.FechaHora;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author diego
 */
public class jpFormBajaBien extends javax.swing.JPanel {

    /**
     * Creates new form jpFormBajaBien
     */
    
    private Bien unBien;    
    private Usuario user;
    private BienJpaController dao = new BienJpaController();
    private MainMdi parent;
    
    public jpFormBajaBien() {
        initComponents();
        // this.activarComponentes(false);
    }

    public MainMdi getParent() {
        return parent;
    }

    public void setParent(MainMdi parent) {
        this.parent = parent;
    }
    

    public Bien getUnBien() {
        return unBien;
    }

    public void setUnBien(Bien unBien) {
        this.unBien = unBien;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
     public void activarComponentes(boolean var ){
         this.btnBaja.setEnabled(var);
         this.btnCancelar.setEnabled(!var);
         this.btnSalir.setEnabled(!var);
     }
    
    
    public final void inicializarCampos(){
       // this.txtNroInventario.setText(this.unBien.getNroInventario().toString());
        FechaHora fecha = new FechaHora();
        this.btnBaja.setEnabled(true);
        
        if(this.unBien.getDescripcion() != null){
            this.txtDescripcion.setText(this.unBien.getDescripcion());
        }
        
        if(this.unBien.getNroInventario() != null){
            this.txtNroInventario.setText(this.unBien.getNroInventario().toString());
        }
        
        if(this.unBien.getFechaBaja() != null){
            this.txtFechaBaja.setText(fecha.DateToString(this.unBien.getFechaBaja()));
        }else{
            this.txtFechaBaja.setText(fecha.DateToString(fecha.fechaActual()));
        }
        
        if(this.unBien.getResolucionBaja() != null){
            this.txtResolucionBaja.setText(this.unBien.getResolucionBaja());
        }
        
        this.inicializarTiposBaja();
        
        if(this.unBien.getTipoBaja() != null){
            this.txtTipo.setSelectedItem(this.unBien.getTipoBaja());
        }
        
        this.txtFechaBaja.setEnabled(!this.unBien.isDebaja());
        this.txtResolucionBaja.setEnabled(!this.unBien.isDebaja());
        this.btnBaja.setEnabled(!this.unBien.isDebaja());
        this.txtTipo.setEnabled(!this.unBien.isDebaja());
        
    }
    public void inicializarTiposBaja(){
        this.txtTipo.removeAllItems();
        this.txtTipo.addItem("Hurto");
        this.txtTipo.addItem("Destruccion");
        this.txtTipo.addItem("Natural");
        
        
    }
    public final void limpiarCampos(){
        this.txtFechaBaja.setText(null);
        this.txtNroInventario.setText(null);
        this.txtResolucionBaja.setText(null);
        this.txtDescripcion.setText(null);
        this.unBien = null;
    }
    
    public void buscarBien(){
        //this.unBien = this.logica.porNroInventario(Integer.decode(this.txtNroInventario.getText())).get(0);
        List<Bien> lista = this.dao.findBienByNroInventario(Integer.parseInt(this.txtNroInventario.getText()));
        this.limpiarCampos();
        if(lista.size() > 0){
            this.unBien = lista.get(0);
        }else{
            JOptionPane.showMessageDialog(null, "No se encontro ningun bien con el nro ingresado.");
        }
        this.inicializarCampos();
        
        
        
    }
    
    public Bien getBienFromForm(){
        FechaHora fecha = new FechaHora();
        String resolucion = this.txtResolucionBaja.getText();
        String tipoBaja = (String) this.txtTipo.getSelectedItem();
        Date fechaBaja = fecha.StringToDate(this.txtFechaBaja.getText());
        BigDecimal valor = new BigDecimal(1);
        
        return new Bien(
        this.unBien.getCodigo(),
        this.unBien.getNroInventario(),
        this.unBien.getDescripcion(), 
        this.unBien.getFechaAlta(), 
        this.unBien.getNroActa(), 
        valor, 
        "BAJA", 
        true, 
        this.unBien.getNroExpedienteAlta(),  
        this.unBien.getResolucionAlta(),  
        this.unBien.getNroExpedienteBaja(),  
        resolucion, 
                "",  
        fechaBaja,  
        tipoBaja);
    }

   
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTipo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Descripcion = new javax.swing.JLabel();
        txtResolucionBaja = new javax.swing.JTextField();
        txtFechaBaja = new javax.swing.JFormattedTextField();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnBaja = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtNroInventario = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        txtTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtTipo.setEnabled(false);

        txtDescripcion.setEditable(false);
        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        jLabel2.setText("Fecha");

        jLabel4.setText("Resolucion");

        Descripcion.setText("Descripcion");

        txtResolucionBaja.setEnabled(false);
        txtResolucionBaja.setName("txtResolucion"); // NOI18N

        try {
            txtFechaBaja.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFechaBaja.setEnabled(false);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnBaja.setText("Baja");
        btnBaja.setEnabled(false);
        btnBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajaActionPerformed(evt);
            }
        });

        jLabel6.setText("Nro de Inventario");

        txtNroInventario.setToolTipText("");
        txtNroInventario.setName("txtNroInventario"); // NOI18N
        txtNroInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroInventarioActionPerformed(evt);
            }
        });
        txtNroInventario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNroInventarioKeyPressed(evt);
            }
        });

        jLabel1.setText("Tipo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtResolucionBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel1)
                                        .addGap(24, 24, 24)
                                        .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtNroInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtFechaBaja))))
                            .addComponent(jLabel4)
                            .addComponent(Descripcion))
                        .addGap(35, 35, 35)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNroInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtFechaBaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtResolucionBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Descripcion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir)
                    .addComponent(btnCancelar)
                    .addComponent(btnBaja))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.unBien = null;
        this.txtFechaBaja.setText(null);
        this.txtNroInventario.setText(null);
        this.txtResolucionBaja.setText(null);
        this.txtDescripcion.setText(null);

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:

        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
        switch(respuesta) {
            case JOptionPane.YES_OPTION:
            //--- Operaciones en caso afirmativo
            this.parent.ocultarBajaBien();
            break;
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaActionPerformed
        // TODO add your handling code here:
        FechaHora fecha = new FechaHora();
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea dar de Baja el Bien?", "Confirmación", JOptionPane.YES_NO_OPTION);
        switch(respuesta) {
            case JOptionPane.YES_OPTION:
            try{
                //Bien newBien = this.getBienFromForm();
                this.unBien.setDebaja(true);
                this.unBien.setResolucionBaja(this.txtResolucionBaja.getText());
                this.unBien.setFechaBaja(fecha.StringToDate(this.txtFechaBaja.getText()));
                this.unBien.setTipoBaja(this.txtTipo.getSelectedItem().toString());
                this.unBien.setEstado("BAJA");
                this.unBien.setValor(new BigDecimal(1));
                this.dao.baja(this.unBien, this.unBien.getId(),this.getUser());
            }
            catch (NonexistentEntityException e){
                JOptionPane.showMessageDialog(null, "No se puede guardar la infomación.\nDebe completar los datos requeridos.");
            }
            break;
        }

        this.activarComponentes(false);

        this.limpiarCampos();
    }//GEN-LAST:event_btnBajaActionPerformed

    private void txtNroInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroInventarioActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtNroInventarioActionPerformed

    private void txtNroInventarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroInventarioKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            this.buscarBien();
        }
    }//GEN-LAST:event_txtNroInventarioKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Descripcion;
    private javax.swing.JButton btnBaja;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JFormattedTextField txtFechaBaja;
    private javax.swing.JTextField txtNroInventario;
    private javax.swing.JTextField txtResolucionBaja;
    private javax.swing.JComboBox<String> txtTipo;
    // End of variables declaration//GEN-END:variables
}
