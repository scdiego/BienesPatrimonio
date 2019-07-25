/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mdi;

import Negocio.RegistroImportacionBien;
import Negocio.Usuario;
import Utilidades.CvsReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author diego
 */
public class FrmCvsImport extends javax.swing.JPanel {

    /**
     * Creates new form FrmCvsImport
     */
    private Usuario user;
    MainMdi parent;
    private EntityManagerFactory emf = null;

    private ArrayList<RegistroImportacionBien> lista = new ArrayList();;
    public DefaultTableModel tblBienes;

    
    public FrmCvsImport() {
        initComponents();
        this.inicializar();
    }
    
    public void inicializar(){
        tblBienes = new DefaultTableModel();
        this.colocarEncabezadoGrid();
        emf = Persistence.createEntityManagerFactory("patromonioPU");
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void setParent(MainMdi miparent) {
        this.parent = miparent;
    }
    
    public final void colocarEncabezadoGrid(){
        String[] columnNames = {"Nro de Inventario",
                                "Codigo",
                                "Descripcion",
                                "Fecha",
                                "Nro de Acta",
                                "Valor",
                                "Area",
                                "Responsable"
        };
        this.tblBienes.setColumnIdentifiers(columnNames);
        this.gridBienes.setModel(tblBienes);
    } 
    
    public void leer(){
        String ruta = this.txtPath.getText();
        File f = new File(ruta);
        if(f.exists() && !f.isDirectory()){
            CvsReader lector = new CvsReader(ruta);
            lista = lector.read();
            
            int rowIndex = 0;
            Object[][] data = new Object[lista.size()][8];
            for (RegistroImportacionBien bien : lista) {
                data[rowIndex][0] = bien.getNroInventario();
                data[rowIndex][1] = bien.getCodigo();
                data[rowIndex][2] = bien.getDescripcion();
                data[rowIndex][3] = bien.getFecha();
                data[rowIndex][4] = bien.getNroActa();
                data[rowIndex][5] = bien.getValor();
                data[rowIndex][6] = bien.getArea();
                data[rowIndex][7] = bien.getResponsable();

                tblBienes.addRow(data[rowIndex]);  
                rowIndex = rowIndex + 1;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Archivo inexistente. Verifique","Importar Bienes", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void insertarRegistro(String NroInventario, String Codigo, String Descripcion, String Fecha, String NroActa, String Valor, String Area, String Responsable){
        
        EntityManager em = getEntityManager();
        
        try {
            em.getTransaction().begin();
            StoredProcedureQuery sp = em.createStoredProcedureQuery("SP_IMPORTARLIBROBIENES");
            sp.registerStoredProcedureParameter("P_NROINVETARIO", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("P_CODIGO", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("P_DESCRIPCION", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("P_FECHA", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("P_NROACTA", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("P_VALOR", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("P_AREA", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("P_RESPONSABLE", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("P_USUARIO", String.class, ParameterMode.IN);
            
            
            sp.setParameter("P_NROINVETARIO", NroInventario);
            sp.setParameter("P_CODIGO", Codigo);
            sp.setParameter("P_DESCRIPCION", Descripcion);
            sp.setParameter("P_FECHA", Fecha);
            sp.setParameter("P_NROACTA", NroActa);
            sp.setParameter("P_VALOR", Valor);
            sp.setParameter("P_AREA", Area);
            sp.setParameter("P_RESPONSABLE", Responsable);
            sp.setParameter("P_USUARIO", this.user.getNombre());

            
            sp.execute();
            em.getTransaction().commit();
            
            } finally {
            if (em != null) {
                em.close();
            }
            }

    }
    
    public void borrargrid(){
        if (this.tblBienes.getRowCount() > 0) {
            for (int i = tblBienes.getRowCount() - 1; i > -1; i--) {
                tblBienes.removeRow(i);
            }
        }
    }

    public void cargarRegistros(){

       RegistroImportacionBien r = new RegistroImportacionBien();
       int size=this.lista.size();
       int cont = 0;
       for(int x=0;x < size ; x++) {
           this.insertarRegistro(lista.get(x).getNroInventario(),lista.get(x).getCodigo(),lista.get(x).getDescripcion(),lista.get(x).getFecha(),lista.get(x).getNroActa(),lista.get(x).getValor(),lista.get(x).getArea(),lista.get(x).getResponsable());
           cont = cont + 1;
       }
      //  this.guardarBienes();
        this.borrargrid();
        JOptionPane.showMessageDialog(null, cont  + " Registros Importados ","Importar Bienes", JOptionPane.INFORMATION_MESSAGE);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        txtPath = new javax.swing.JTextField();
        btnLeer = new javax.swing.JButton();
        btnBrowse = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        gridBienes = new javax.swing.JTable();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        jLabel1.setText("Archivo");

        btnLeer.setText("Leer");
        btnLeer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeerActionPerformed(evt);
            }
        });

        btnBrowse.setText("...");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

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
        jScrollPane1.setViewportView(gridBienes);

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPath, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(btnBrowse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLeer, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAceptar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLeer)
                    .addComponent(btnBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLeerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeerActionPerformed
        // TODO add your handling code here:
        this.leer();
    }//GEN-LAST:event_btnLeerActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        this.cargarRegistros();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
        switch(respuesta) {
            case JOptionPane.YES_OPTION:
            //--- Operaciones en caso afirmativo
          //  this.dispose();
            this.parent.ocultarFrmImportCvs();
                break;
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        // TODO add your handling code here:
            int returnVal = fileChooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
       // try {
          // What to do with the file, e.g. display it in a TextArea
          txtPath.setText(file.getAbsolutePath() );
       // } catch (IOException ex) {
          System.out.println("problem accessing file"+file.getAbsolutePath());
       // }
   // } else {
        System.out.println("File access cancelled by user.");
    
    }
    }//GEN-LAST:event_btnBrowseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnLeer;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JTable gridBienes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtPath;
    // End of variables declaration//GEN-END:variables
}
