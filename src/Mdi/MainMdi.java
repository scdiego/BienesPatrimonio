/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
-norecarmen@outlook.com
 */
package Mdi;

import Negocio.Bien;
import Negocio.Usuario;

/**
 *
 * @author diego
 */
public class MainMdi extends javax.swing.JFrame {

    /**
     * Creates new form MainMdi
     * https://salvadorhm.blogspot.com/2014/07/aplicacion-interfaz-de-multiples.html
     */
    private Usuario user;
    
    private jpFormBien objFormBien = new jpFormBien();
    private jpFormBajaBien objFormBajaBien = new jpFormBajaBien();
    private jpFormSectores objFormSector = new jpFormSectores();
    private FormCargo objFormCargo = new FormCargo();
    private jpFormCambiarPass objFormCambiarPass = new jpFormCambiarPass();
    private JpFormResponsable objFormResponsable = new JpFormResponsable();
    private FrmCvsImport objFrmImportCvs = new FrmCvsImport();
    

    
    private jpRepoBienes objRepoBienes = new jpRepoBienes();
    private jpRepoLibroBienes objRepoLibroBienes = new jpRepoLibroBienes();
    private JpRepoImpresionCargosV2 objRepoCargos = new JpRepoImpresionCargosV2();
    private JpRepoInfAuditoria objRepoAuditoria = new JpRepoInfAuditoria();
    
    
    
    
    public MainMdi() {
        initComponents();
       
    }
    
    public void inicializarVentanas(Usuario user){
        this.inicializarUsuario(user);
        
        jifFormBien.setContentPane(objFormBien);
        objFormBien.setMiparent(this);
        objFormBien.setUser(this.user);
        objFormBien.setSize(800,450);
        jifFormBien.setSize(800, 450);
        
        jifFormBajaBien.setContentPane(objFormBajaBien);
        objFormBajaBien.setParent(this);   
        objFormCambiarPass.setUser(this.user);
        objFormBajaBien.setSize(800,350);
        jifFormBajaBien.setSize(800, 350);
        
        jifFormSector.setContentPane(objFormSector);
        objFormSector.setParent(this);   
        objFormSector.setUser(this.user);
        objFormSector.setSize(800,550);
        jifFormSector.setSize(800,550);
        
        jifFormResponsable.setContentPane(objFormResponsable);
        objFormResponsable.setParent(this);
        objFormResponsable.SetUser(this.user);
        objFormResponsable.setSize(800,550);
        jifFormResponsable.setSize(800,550);
        
        jifFormCargo.setContentPane(objFormCargo);
        objFormCargo.setParent(this);   
        objFormCargo.setUser(this.user);
        objFormCargo.setSize(800,350);
        jifFormCargo.setSize(800,350);
        
        
        jifFrmImportCvs.setContentPane(objFrmImportCvs);
        objFrmImportCvs.setParent(this); 
        objFrmImportCvs.setUser(this.user);
        objFrmImportCvs.setSize(800,550);
        jifFrmImportCvs.setSize(800,550);
        
        // Reportes //
        jifRepoBienes.setContentPane(objRepoBienes);
       // objRepoBienes.setParent(this);   
        objRepoBienes.setSize(1100,550);
        jifRepoBienes.setSize(1100,550);
        
        jifRepoLibroBienes.setContentPane(objRepoLibroBienes);
        objRepoLibroBienes.setParent(this);   
        objRepoLibroBienes.setSize(1100,350);
        jifRepoLibroBienes.setSize(1100,350);
        
        jifRepoCargos.setContentPane(objRepoCargos);
        //objRepoCargos.setParent(this);   
        objRepoCargos.setSize(550,450);
        jifRepoCargos.setSize(550,450);
        
        jifRepoAuditoria.setContentPane(objRepoAuditoria);
        //objRepoAuditoria.setParent(this);   
        objRepoAuditoria.setSize(800,350);
        jifRepoAuditoria.setSize(800,350);
        
        jifCambiarPass.setContentPane(objFormCambiarPass);
        objFormCambiarPass.setParent(this);
        objFormCambiarPass.setUser(this.user);
        objFormCambiarPass.setSize(350, 200);
        jifCambiarPass.setSize(350,200);
        
        
    }

    public void inicializarUsuario(Usuario user){
      //  UsuarioJpaController daoUser = new UsuarioJpaController();
      //   this.user = daoUser.findUsuario(id);
            this.setUser(user);
        
        }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    public void ocultarCargo(){
        this.jifFormCargo.setVisible(false);
    }
    
    public void ocultarBien(){
        this.jifFormBien.setVisible(false);
    }
    
    public void ocultarBajaBien(){
        this.jifFormBajaBien.setVisible(false);
    }
    
    public void ocultarSectores(){
        this.jifFormSector.setVisible(false);
    }
    
    public void ocultarRepoBien(){
        this.jifRepoBienes.setVisible(false);        
    }
    
    public void ocultarRepoLibroBienes(){
        this.jifRepoLibroBienes.setVisible(false);
    }
    public void ocultarRepoCargos(){
        this.jifRepoCargos.setVisible(false);
    }
    public void ocultarRepoAuditoria(){
        this.jifRepoAuditoria.setVisible(false);
    }
    public void ocultarCambiarPass(){
        this.jifCambiarPass.setVisible(false);
    }
    public void ocultarResponsalbe(){
        this.jifFormResponsable.setVisible(false);
    }  
    public void ocultarFrmImportCvs(){
        this.jifFrmImportCvs.setVisible(false);
    }
    
    public void mostrarBajaBien(Bien unBien){
         objFormBajaBien.setBien(unBien);
         objFormBajaBien.inicializarCampos();
         jifFormBajaBien.setVisible(true);
    }
        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        jifFormBien = new javax.swing.JInternalFrame();
        jifFormBajaBien = new javax.swing.JInternalFrame();
        jifFormSector = new javax.swing.JInternalFrame();
        jifFormCargo = new javax.swing.JInternalFrame();
        jifRepoBienes = new javax.swing.JInternalFrame();
        jifRepoLibroBienes = new javax.swing.JInternalFrame();
        jifRepoCargos = new javax.swing.JInternalFrame();
        jifRepoAuditoria = new javax.swing.JInternalFrame();
        jifCambiarPass = new javax.swing.JInternalFrame();
        jifFormResponsable = new javax.swing.JInternalFrame();
        jifFrmImportCvs = new javax.swing.JInternalFrame();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jmiFrmBien = new javax.swing.JMenuItem();
        jmiFrmBajaBien = new javax.swing.JMenuItem();
        jmiFrmCvsImport = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmiFrmCambiarPass = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jmiFrmSectores = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jmiFrmResponsables = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jmiFrmCargos = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jmiSalir = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Patrimonio - Parque del Conocimiento");

        jifFormBien.setClosable(true);
        jifFormBien.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jifFormBien.setIconifiable(true);
        jifFormBien.setTitle("Bienes");
        jifFormBien.setVisible(false);

        javax.swing.GroupLayout jifFormBienLayout = new javax.swing.GroupLayout(jifFormBien.getContentPane());
        jifFormBien.getContentPane().setLayout(jifFormBienLayout);
        jifFormBienLayout.setHorizontalGroup(
            jifFormBienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jifFormBienLayout.setVerticalGroup(
            jifFormBienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        desktopPane.add(jifFormBien);
        jifFormBien.setBounds(150, 140, 84, 32);

        jifFormBajaBien.setClosable(true);
        jifFormBajaBien.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jifFormBajaBien.setIconifiable(true);
        jifFormBajaBien.setTitle("Baja de Bien");
        jifFormBajaBien.setVisible(false);

        javax.swing.GroupLayout jifFormBajaBienLayout = new javax.swing.GroupLayout(jifFormBajaBien.getContentPane());
        jifFormBajaBien.getContentPane().setLayout(jifFormBajaBienLayout);
        jifFormBajaBienLayout.setHorizontalGroup(
            jifFormBajaBienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jifFormBajaBienLayout.setVerticalGroup(
            jifFormBajaBienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        desktopPane.add(jifFormBajaBien);
        jifFormBajaBien.setBounds(70, 20, 83, 32);

        jifFormSector.setClosable(true);
        jifFormSector.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jifFormSector.setIconifiable(true);
        jifFormSector.setTitle("Sectores");
        jifFormSector.setVisible(false);

        javax.swing.GroupLayout jifFormSectorLayout = new javax.swing.GroupLayout(jifFormSector.getContentPane());
        jifFormSector.getContentPane().setLayout(jifFormSectorLayout);
        jifFormSectorLayout.setHorizontalGroup(
            jifFormSectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jifFormSectorLayout.setVerticalGroup(
            jifFormSectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        desktopPane.add(jifFormSector);
        jifFormSector.setBounds(110, 30, 87, 32);

        jifFormCargo.setClosable(true);
        jifFormCargo.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jifFormCargo.setIconifiable(true);
        jifFormCargo.setTitle("Cargos");
        jifFormCargo.setVisible(false);

        javax.swing.GroupLayout jifFormCargoLayout = new javax.swing.GroupLayout(jifFormCargo.getContentPane());
        jifFormCargo.getContentPane().setLayout(jifFormCargoLayout);
        jifFormCargoLayout.setHorizontalGroup(
            jifFormCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jifFormCargoLayout.setVerticalGroup(
            jifFormCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        desktopPane.add(jifFormCargo);
        jifFormCargo.setBounds(150, 20, 86, 32);

        jifRepoBienes.setClosable(true);
        jifRepoBienes.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jifRepoBienes.setIconifiable(true);
        jifRepoBienes.setMaximizable(true);
        jifRepoBienes.setResizable(true);
        jifRepoBienes.setTitle("Buscar Bienes");
        jifRepoBienes.setVisible(false);

        javax.swing.GroupLayout jifRepoBienesLayout = new javax.swing.GroupLayout(jifRepoBienes.getContentPane());
        jifRepoBienes.getContentPane().setLayout(jifRepoBienesLayout);
        jifRepoBienesLayout.setHorizontalGroup(
            jifRepoBienesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jifRepoBienesLayout.setVerticalGroup(
            jifRepoBienesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        desktopPane.add(jifRepoBienes);
        jifRepoBienes.setBounds(30, 110, 101, 32);

        jifRepoLibroBienes.setClosable(true);
        jifRepoLibroBienes.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jifRepoLibroBienes.setIconifiable(true);
        jifRepoLibroBienes.setMaximizable(true);
        jifRepoLibroBienes.setResizable(true);
        jifRepoLibroBienes.setTitle("Libro de Bienes");
        jifRepoLibroBienes.setVisible(false);

        javax.swing.GroupLayout jifRepoLibroBienesLayout = new javax.swing.GroupLayout(jifRepoLibroBienes.getContentPane());
        jifRepoLibroBienes.getContentPane().setLayout(jifRepoLibroBienesLayout);
        jifRepoLibroBienesLayout.setHorizontalGroup(
            jifRepoLibroBienesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jifRepoLibroBienesLayout.setVerticalGroup(
            jifRepoLibroBienesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        desktopPane.add(jifRepoLibroBienes);
        jifRepoLibroBienes.setBounds(90, 110, 95, 32);

        jifRepoCargos.setClosable(true);
        jifRepoCargos.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jifRepoCargos.setIconifiable(true);
        jifRepoCargos.setMaximizable(true);
        jifRepoCargos.setResizable(true);
        jifRepoCargos.setTitle("Impresion de Cargos");
        jifRepoCargos.setVisible(false);

        javax.swing.GroupLayout jifRepoCargosLayout = new javax.swing.GroupLayout(jifRepoCargos.getContentPane());
        jifRepoCargos.getContentPane().setLayout(jifRepoCargosLayout);
        jifRepoCargosLayout.setHorizontalGroup(
            jifRepoCargosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jifRepoCargosLayout.setVerticalGroup(
            jifRepoCargosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        desktopPane.add(jifRepoCargos);
        jifRepoCargos.setBounds(150, 110, 100, 32);

        jifRepoAuditoria.setClosable(true);
        jifRepoAuditoria.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jifRepoAuditoria.setIconifiable(true);
        jifRepoAuditoria.setMaximizable(true);
        jifRepoAuditoria.setResizable(true);
        jifRepoAuditoria.setTitle("Auditoria");
        jifRepoAuditoria.setVisible(false);

        javax.swing.GroupLayout jifRepoAuditoriaLayout = new javax.swing.GroupLayout(jifRepoAuditoria.getContentPane());
        jifRepoAuditoria.getContentPane().setLayout(jifRepoAuditoriaLayout);
        jifRepoAuditoriaLayout.setHorizontalGroup(
            jifRepoAuditoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jifRepoAuditoriaLayout.setVerticalGroup(
            jifRepoAuditoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        desktopPane.add(jifRepoAuditoria);
        jifRepoAuditoria.setBounds(200, 110, 102, 32);

        jifCambiarPass.setClosable(true);
        jifCambiarPass.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jifCambiarPass.setIconifiable(true);
        jifCambiarPass.setTitle("Cambiar Password");
        jifCambiarPass.setVisible(false);

        javax.swing.GroupLayout jifCambiarPassLayout = new javax.swing.GroupLayout(jifCambiarPass.getContentPane());
        jifCambiarPass.getContentPane().setLayout(jifCambiarPassLayout);
        jifCambiarPassLayout.setHorizontalGroup(
            jifCambiarPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jifCambiarPassLayout.setVerticalGroup(
            jifCambiarPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        desktopPane.add(jifCambiarPass);
        jifCambiarPass.setBounds(320, 20, 93, 32);

        jifFormResponsable.setClosable(true);
        jifFormResponsable.setIconifiable(true);
        jifFormResponsable.setVisible(false);

        javax.swing.GroupLayout jifFormResponsableLayout = new javax.swing.GroupLayout(jifFormResponsable.getContentPane());
        jifFormResponsable.getContentPane().setLayout(jifFormResponsableLayout);
        jifFormResponsableLayout.setHorizontalGroup(
            jifFormResponsableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jifFormResponsableLayout.setVerticalGroup(
            jifFormResponsableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        desktopPane.add(jifFormResponsable);
        jifFormResponsable.setBounds(290, 220, 50, 32);

        jifFrmImportCvs.setClosable(true);
        jifFrmImportCvs.setIconifiable(true);
        jifFrmImportCvs.setVisible(false);

        javax.swing.GroupLayout jifFrmImportCvsLayout = new javax.swing.GroupLayout(jifFrmImportCvs.getContentPane());
        jifFrmImportCvs.getContentPane().setLayout(jifFrmImportCvsLayout);
        jifFrmImportCvsLayout.setHorizontalGroup(
            jifFrmImportCvsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jifFrmImportCvsLayout.setVerticalGroup(
            jifFrmImportCvsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        desktopPane.add(jifFrmImportCvs);
        jifFrmImportCvs.setBounds(290, 220, 24, 32);

        fileMenu.setMnemonic('f');
        fileMenu.setText("Operaciones");

        jmiFrmBien.setMnemonic('o');
        jmiFrmBien.setText("Bienes");
        jmiFrmBien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFrmBienActionPerformed(evt);
            }
        });
        fileMenu.add(jmiFrmBien);

        jmiFrmBajaBien.setText("Baja de Bienes");
        jmiFrmBajaBien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFrmBajaBienActionPerformed(evt);
            }
        });
        fileMenu.add(jmiFrmBajaBien);

        jmiFrmCvsImport.setText("Importar Bienes");
        jmiFrmCvsImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFrmCvsImportActionPerformed(evt);
            }
        });
        fileMenu.add(jmiFrmCvsImport);
        fileMenu.add(jSeparator1);

        jmiFrmCambiarPass.setText("Cambiar Password");
        jmiFrmCambiarPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFrmCambiarPassActionPerformed(evt);
            }
        });
        fileMenu.add(jmiFrmCambiarPass);
        fileMenu.add(jSeparator2);

        jmiFrmSectores.setText("Sectores");
        jmiFrmSectores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFrmSectoresActionPerformed(evt);
            }
        });
        fileMenu.add(jmiFrmSectores);
        fileMenu.add(jSeparator3);

        jmiFrmResponsables.setText("Responsables");
        jmiFrmResponsables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFrmResponsablesActionPerformed(evt);
            }
        });
        fileMenu.add(jmiFrmResponsables);
        fileMenu.add(jSeparator4);

        jmiFrmCargos.setText("Cargos");
        jmiFrmCargos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFrmCargosActionPerformed(evt);
            }
        });
        fileMenu.add(jmiFrmCargos);
        fileMenu.add(jSeparator5);

        jmiSalir.setText("Salir");
        fileMenu.add(jmiSalir);

        menuBar.add(fileMenu);

        jMenu1.setText("Reportes");

        jMenuItem1.setText("Bienes");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Libro de Bienes");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Impresion de Cargos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Auditoria");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiFrmBienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFrmBienActionPerformed
        // TODO add your handling code here:
        jifFormBien.setVisible(true);
    }//GEN-LAST:event_jmiFrmBienActionPerformed

    private void jmiFrmBajaBienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFrmBajaBienActionPerformed
        // TODO add your handling code here:
        jifFormBajaBien.setVisible(true);
    }//GEN-LAST:event_jmiFrmBajaBienActionPerformed

    private void jmiFrmSectoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFrmSectoresActionPerformed
        // TODO add your handling code here:
       // jifFormSector.inicializar();
        jifFormSector.setVisible(true);

    }//GEN-LAST:event_jmiFrmSectoresActionPerformed

    private void jmiFrmCargosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFrmCargosActionPerformed
        // TODO add your handling code here:
        jifFormCargo.setVisible(true);
    }//GEN-LAST:event_jmiFrmCargosActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        jifRepoBienes.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        jifRepoLibroBienes.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        jifRepoCargos.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        jifRepoAuditoria.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jmiFrmCambiarPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFrmCambiarPassActionPerformed
        // TODO add your handling code here:
        jifCambiarPass.setVisible(true);
    }//GEN-LAST:event_jmiFrmCambiarPassActionPerformed

    private void jmiFrmResponsablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFrmResponsablesActionPerformed
        // TODO add your handling code here:
        jifFormResponsable.setVisible(true);
    }//GEN-LAST:event_jmiFrmResponsablesActionPerformed

    private void jmiFrmCvsImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFrmCvsImportActionPerformed
        // TODO add your handling code here:
        jifFrmImportCvs.setVisible(true);
    }//GEN-LAST:event_jmiFrmCvsImportActionPerformed

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
            java.util.logging.Logger.getLogger(MainMdi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMdi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMdi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMdi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMdi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JInternalFrame jifCambiarPass;
    private javax.swing.JInternalFrame jifFormBajaBien;
    private javax.swing.JInternalFrame jifFormBien;
    private javax.swing.JInternalFrame jifFormCargo;
    private javax.swing.JInternalFrame jifFormResponsable;
    private javax.swing.JInternalFrame jifFormSector;
    private javax.swing.JInternalFrame jifFrmImportCvs;
    private javax.swing.JInternalFrame jifRepoAuditoria;
    private javax.swing.JInternalFrame jifRepoBienes;
    private javax.swing.JInternalFrame jifRepoCargos;
    private javax.swing.JInternalFrame jifRepoLibroBienes;
    private javax.swing.JMenuItem jmiFrmBajaBien;
    private javax.swing.JMenuItem jmiFrmBien;
    private javax.swing.JMenuItem jmiFrmCambiarPass;
    private javax.swing.JMenuItem jmiFrmCargos;
    private javax.swing.JMenuItem jmiFrmCvsImport;
    private javax.swing.JMenuItem jmiFrmResponsables;
    private javax.swing.JMenuItem jmiFrmSectores;
    private javax.swing.JMenuItem jmiSalir;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

}
