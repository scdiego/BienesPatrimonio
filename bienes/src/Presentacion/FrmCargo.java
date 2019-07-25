
package Presentacion;

import Negocio.Asignacion;
import Negocio.Bien;
import Negocio.Responsable;
import Negocio.Usuario;
import Persistencia.AsignacionJpaController;
import Persistencia.BienJpaController;
import Persistencia.ResponsableJpaController;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author diego
 */
public class FrmCargo extends javax.swing.JDialog {
    ResponsableJpaController responsableDao = new ResponsableJpaController();
    AsignacionJpaController asignacionDao = new AsignacionJpaController();
    Bien unBien;
    Usuario user;
    
    
    public FrmCargo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        fillResponsables();
    }

    public void setUser(Usuario unUsuario) {
        this.user = unUsuario;
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
            
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNroInventario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lstResponsable = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        lstSubResponsable = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cargos - Patrimonio");

        jLabel1.setText("Nro de Inventario");

        txtNroInventario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNroInventarioKeyPressed(evt);
            }
        });

        jLabel2.setText("Responsable");

        lstResponsable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Sub Responsable");

        lstSubResponsable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Descripcion del Bien");

        txtDescripcion.setEditable(false);
        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNroInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBuscar)
                                .addGap(0, 58, Short.MAX_VALUE))
                            .addComponent(lstResponsable, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lstSubResponsable, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
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
                    .addComponent(txtNroInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lstResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lstSubResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // Tomar bien, Responsable y subResponsable
        this.asignar();
    }//GEN-LAST:event_btnGuardarActionPerformed
    private void actualizarVentana(String descripcion) {
        txtDescripcion.setText(descripcion);
        btnGuardar.setEnabled(true);
    } 
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        this.buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtNroInventarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroInventarioKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode() == 10){
             this.buscar();
         }
    }//GEN-LAST:event_txtNroInventarioKeyPressed

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
            java.util.logging.Logger.getLogger(FrmCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmCargo dialog = new FrmCargo(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
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
