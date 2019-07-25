
package Presentacion;

import Negocio.Usuario;
import Persistencia.UsuarioJpaController;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

public class MainForm extends javax.swing.JFrame {
    
    private Usuario user; // Para auditoria

    public MainForm() {
        initComponents();
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.user = user;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    public void inicializarUsuario(Usuario user){
      //  UsuarioJpaController daoUser = new UsuarioJpaController();
      //   this.user = daoUser.findUsuario(id);
      this.setUser(user);
        
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MnBienes = new javax.swing.JMenuItem();
        MnBajaBien = new javax.swing.JMenuItem();
        MnImprimirEtiqueta = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        MnUsuarios = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        MnSectores = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        MnResponsables = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mnuCargos = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuBuscarBienes = new javax.swing.JMenuItem();
        MnSalir = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Patrimonio");
        setName("MainForm"); // NOI18N

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 715, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 457, Short.MAX_VALUE)
        );

        jMenu1.setText("Operaciones");

        MnBienes.setText("Bienes");
        MnBienes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBienesActionPerformed(evt);
            }
        });
        jMenu1.add(MnBienes);

        MnBajaBien.setText("Baja de Bienes");
        MnBajaBien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBajaBienActionPerformed(evt);
            }
        });
        jMenu1.add(MnBajaBien);

        MnImprimirEtiqueta.setText("Imprimir Etiquetas");
        MnImprimirEtiqueta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnImprimirEtiquetaActionPerformed(evt);
            }
        });
        jMenu1.add(MnImprimirEtiqueta);
        jMenu1.add(jSeparator2);

        MnUsuarios.setText("Usuarios");
        MnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUsuariosActionPerformed(evt);
            }
        });
        jMenu1.add(MnUsuarios);
        jMenu1.add(jSeparator3);

        MnSectores.setText("Sectores");
        MnSectores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSectoresActionPerformed(evt);
            }
        });
        jMenu1.add(MnSectores);
        jMenu1.add(jSeparator4);

        MnResponsables.setText("Responsables");
        MnResponsables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResponsablesActionPerformed(evt);
            }
        });
        jMenu1.add(MnResponsables);
        jMenu1.add(jSeparator5);

        mnuCargos.setText("Cargos");
        mnuCargos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCargosActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCargos);
        jMenu1.add(jSeparator1);

        mnuBuscarBienes.setText("Buscar Bienes");
        mnuBuscarBienes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuBuscarBienesActionPerformed(evt);
            }
        });
        jMenu1.add(mnuBuscarBienes);

        MnSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        MnSalir.setText("Salir");
        MnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSalirActionPerformed(evt);
            }
        });
        jMenu1.add(MnSalir);

        jMenuBar1.add(jMenu1);

        jMenu5.setText("Reportes");

        jMenuItem2.setText("Bienes");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem2);

        jMenuItem3.setText("Libro de Bienes");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem3);

        jMenuItem1.setText("Impresion de Cargos");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem1);

        jMenuItem11.setText("Auditoria");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem11);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MnBienesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBienesActionPerformed
     
        FrmBien ventana = new FrmBien(this,rootPaneCheckingEnabled);
        ventana.inicializarUsuario(this.getUser());
        ventana.habilitarPermisos();
        mostrarVentana(ventana);
        
    }//GEN-LAST:event_MnBienesActionPerformed

    private void MnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUsuariosActionPerformed
        if(this.user.habilitarUsuario()){
            FrmUsuario ventana = new FrmUsuario(this, rootPaneCheckingEnabled);
            ventana.setUser(this.getUser());
            mostrarVentana(ventana);
        }
    }//GEN-LAST:event_MnUsuariosActionPerformed

    private void MnResponsablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnResponsablesActionPerformed
        FrmResponsable ventana = new FrmResponsable(this,rootPaneCheckingEnabled);
        ventana.setUser(this.getUser());
        mostrarVentana(ventana);
    }//GEN-LAST:event_MnResponsablesActionPerformed

    private void MnSectoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSectoresActionPerformed
        FrmSector ventana = new FrmSector (this,rootPaneCheckingEnabled);
        ventana.setUser(this.getUser());
        mostrarVentana(ventana);
    }//GEN-LAST:event_MnSectoresActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        FrmBuscarBien ventana = new FrmBuscarBien(this,rootPaneCheckingEnabled);
        ventana.setUser(this.getUser());
        mostrarVentana(ventana);    
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void mnuCargosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCargosActionPerformed
        FrmCargo ventana = new FrmCargo(this,rootPaneCheckingEnabled);
        ventana.setUser(this.getUser());
        mostrarVentana(ventana);
    }//GEN-LAST:event_mnuCargosActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        FrmLibroBienes ventana = new FrmLibroBienes(this,rootPaneCheckingEnabled);
        ventana.setUser(this.getUser());
        mostrarVentana(ventana);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void MnBajaBienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBajaBienActionPerformed
        FrmBajaBien ventana = new FrmBajaBien(this,rootPaneCheckingEnabled);
        ventana.setUser(this.getUser());
        ventana.habilitarPermisos();
        mostrarVentana(ventana);
    }//GEN-LAST:event_MnBajaBienActionPerformed

    private void MnImprimirEtiquetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnImprimirEtiquetaActionPerformed
        FrmListImpresionCodigo ventana = new FrmListImpresionCodigo(this,rootPaneCheckingEnabled);
        ventana.setUser(this.getUser());
        mostrarVentana(ventana);
    }//GEN-LAST:event_MnImprimirEtiquetaActionPerformed

    private void MnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSalirActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(null, "Desea Salir", "Confirmación", JOptionPane.YES_NO_OPTION);
            switch(respuesta) {
                case JOptionPane.YES_OPTION:
                System.exit(0); 
                break;
            }
    }//GEN-LAST:event_MnSalirActionPerformed

    private void mostrarVentana(JDialog form) {
        form.setLocationRelativeTo(null);
        form.setVisible(true);
    }
    
    private void mostrarVentana(JFrame from){
        from.setLocationRelativeTo(null);
        from.setVisible(true);
        
    }
    private void mostrarVentana(JInternalFrame form){
        form.setVisible(true);
    }
    private void mnuBuscarBienesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuBuscarBienesActionPerformed
        FrmBuscarBien ventana = new FrmBuscarBien(this,rootPaneCheckingEnabled);
        ventana.setUser(this.getUser());
        mostrarVentana(ventana);
    }//GEN-LAST:event_mnuBuscarBienesActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
       // FrmImpresionCargos ventana = new FrmImpresionCargos(this,rootPaneCheckingEnabled);
       // ventana.setUser(this.getUser());
      // Prueba2 ventana = new Prueba2(); 
      // mostrarVentana(ventana);
        /**
         * JFrame frame = new JFrame("Test InternalJFrame");
        frame.add(new MyInternalFrame());
        frame.setVisible(true);
         */
     //   https://salvadorhm.blogspot.com/2014/07/aplicacion-interfaz-de-multiples.html
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        if(this.user.habilitarAuditoria()){
            FrmAuditoria ventana = new FrmAuditoria(this, rootPaneCheckingEnabled);
            ventana.setUser(this.getUser());
            mostrarVentana(ventana);
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }
    
    public void inicializaVentana(){
   // public static void main(String[] args) {
        
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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MnBajaBien;
    private javax.swing.JMenuItem MnBienes;
    private javax.swing.JMenuItem MnImprimirEtiqueta;
    private javax.swing.JMenuItem MnResponsables;
    private javax.swing.JMenuItem MnSalir;
    private javax.swing.JMenuItem MnSectores;
    private javax.swing.JMenuItem MnUsuarios;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JMenuItem mnuBuscarBienes;
    private javax.swing.JMenuItem mnuCargos;
    // End of variables declaration//GEN-END:variables
}
