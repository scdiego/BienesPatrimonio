
package Presentacion;

import Negocio.Bien;
import Negocio.Inventario;
import Negocio.Responsable;
import Negocio.Sector;
import Negocio.Usuario;
import Persistencia.AsignacionJpaController;
import Persistencia.BienJpaController;
import Persistencia.ResponsableJpaController;
import Persistencia.SectorJpaController;
import Reportes.AbsJasperReports;
import Utilidades.FechaHora;
import dbConn.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.NoResultException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author diego
 */
public class FrmBuscarBien extends javax.swing.JDialog {

    public Integer filter;
    public DefaultTableModel tblBienes;// = new DefaultTableModel();
    public CtrlVista logica;
    public ArrayList<Bien> listadeBienes = new ArrayList();
    private final BienJpaController dao = new BienJpaController();
    private String reportName = "ListadoBienesNro";
    private String parametro = "";
    private Map<String,Object> parametros = new HashMap();
    SectorJpaController sectorDao;
    AsignacionJpaController asignacionDao = new AsignacionJpaController();
    
    private Usuario user;
    
    
    private static final int NRO_INVENTARIO = 1;
    private static final int DESCRIPCION = 2;
    private static final int SECTOR = 3;
    private static final int RESPONSABLE = 4;
    private static final int RANGO = 5;
    private static final int FECHA = 6;
  
    JDialog viewer = new JDialog (new JFrame(), "Reporte",true);
    Connection conn;
    
    public FrmBuscarBien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.filter = 1;
        tblBienes = new DefaultTableModel();
        this.colocarEncabezadoGrid();
        this.setTitle("Buscar Bienes");
        fillSectores();
        fillResponsables();
        groupButtons();
        try {             
             conn = Conexion.obtener();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FrmLibroBienes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    
    private void groupButtons() {
        btngrp.add(rdDescripcion);
        btngrp.add(rdNroInventario);
        btngrp.add(rdResponsable);
        btngrp.add(rdSector);
    }
    
    private void fillSectores() {
        sectorDao = new SectorJpaController();
        List<Sector> sectores = sectorDao.findSectorEntities();
        for (Sector sector:sectores) {
            this.cmbSector.addItem(sector);
        }
    }
    
    private void fillResponsables() {
        ResponsableJpaController responsableDao = new ResponsableJpaController();
        List<Responsable> responsables = responsableDao.findResponsableEntities();
        for (Responsable responsable:responsables) {
            this.cmbResponsable.addItem(responsable);
        }
    }
    
    public final void limpiarGrid(){
        if (this.tblBienes.getRowCount() > 0) {
            for (int i = this.tblBienes.getRowCount() - 1; i > -1; i--) {
               this.tblBienes.removeRow(i);
            }
        }
    }
    public final void colocarEncabezadoGrid(){
        String[] columnNames = {"Nro de Inventario",
                                "Descripcion",
                                "Estado",
                                "Responsable",
                                "Area",
                                "Fecha"};
        this.tblBienes.setColumnIdentifiers(columnNames);
        this.gridBienes.setModel(tblBienes);
    }
    
    public final void cargarGrid(){
        Inventario inv = new Inventario();
        int cantBienes = this.listadeBienes.size();
        int COLS = 6;
        Object[][] data = new Object[cantBienes][COLS];
        int rowIndex = 0;
        FechaHora fecha = new FechaHora();
        
        //this.limpiarGrid();
        //this.colocarEncabezadoGrid();
        for (Bien bien : this.listadeBienes) {
            Integer nroInventario = bien.getNroInventario();
            data[rowIndex][0] = nroInventario;
            data[rowIndex][1] = bien.getDescripcion();
            data[rowIndex][2] = bien.getEstado();    
            data[rowIndex][5] = fecha.DateToString(bien.getFechaAlta());
            Responsable responsableBien = inv.responsableBienID(bien.getId());
            //Responsable responsableBien = null;
            addResponsableDataToRow(data, rowIndex, responsableBien);           
            tblBienes.addRow(data[rowIndex]);
        }
    }
    
    private void addResponsableDataToRow(Object[][] data, int rowIndex, Responsable responsableBien) {
        if (responsableBien == null) {
            data[rowIndex][3] = "-";
            data[rowIndex][4] = "-";
        }
        else {
            data[rowIndex][3] = responsableBien.getNombre();
            data[rowIndex][4] = responsableBien.getSector();
        }
    }
    
    private void showNoResults(String campo) {
        JOptionPane.showMessageDialog(this,
                "No se encontraron bienes activos con ese " + campo,        
                "Algo salió mal",                        
                 JOptionPane.INFORMATION_MESSAGE);

    }
    
    public final void findByNroInventario() {
        try {
            int nroInventario = Integer.parseInt(txtBusqueda.getText());
            List<Bien> bienes = dao.findBienByNroInventario(nroInventario);
            if (bienes.isEmpty()) {
                showNoResults("Nro. de Inventario");
            } 
            else {
                for (Bien bien:bienes) {
                    this.listadeBienes.add(bien);
                }
                cargarGrid();
            }
        } catch (NullPointerException ex){
            JOptionPane.showMessageDialog(null,"La busqueda no arrojo resultados","Búsqueda",JOptionPane.ERROR_MESSAGE); //Tipo de mensaje
        }
       // this.txtBusqueda.setText(null);
    }
    
    private void mostrarBienes(List<Bien> bienes) {
        for (Bien bien:bienes) {
                this.listadeBienes.add(bien);
            }
            this.cargarGrid();
    }
    public final void findByDescripcion(){
        try{
            String descripcion = txtBusqueda.getText();
            List<Bien> bienes = dao.findBienesByDescripcion(descripcion);
            if (bienes.isEmpty()) {
                showNoResults("Descripcion");
            } else {
                mostrarBienes(bienes);
            }            
        } catch (NullPointerException ex){
            JOptionPane.showMessageDialog(null,"La busqueda no arrojo resultados","Búsqueda",JOptionPane.ERROR_MESSAGE); //Tipo de mensaje    
        }
      //  this.txtBusqueda.setText(descripcion);
      
    }
    
    public final void findBySector(){
        AsignacionJpaController asignacionDao = new AsignacionJpaController();
        Sector sector = (Sector) cmbSector.getSelectedItem();
        List<Bien> listaBienes = asignacionDao.findBienBySector(sector);
        if (listaBienes.isEmpty()) {
            showNoResults("Sector");
        } else {
            mostrarBienes(listaBienes);
        }
        
    }
    public final void findByRango(){
        
        
    }
    public final void findByFecha(){
        
    }
    public void buscarBienes(Integer desde){
        BienJpaController bienDao = new BienJpaController();
        List<Bien> listaBienes = bienDao.findBienByNroDesde(desde);
                if (listaBienes.isEmpty()) {
            showNoResults("Sector");
        } else {
            mostrarBienes(listaBienes);
        }
    }
    
    public void buscarBienes(Integer desde, Integer Hasta){
        BienJpaController bienDao = new BienJpaController();
        List<Bien> listaBienes = bienDao.findBienByNroDesdeHasta(desde, Hasta);
                if (listaBienes.isEmpty()) {
            showNoResults("Sector");
        } else {
            mostrarBienes(listaBienes);
        }
    }
   /* 
    public void setearParametros(){
        this.parametro  = this.txtBusqueda.getText().trim();
        
        if(!this.parametro.equals("")){
            this.parametros.put("parametro", this.parametro);
            this.setearParametros();
           // String vpath = "//home//diego//Escritorio//Patrimonio//patrimonio//src//Reportes//"+this.reportName+".jasper";
            String vpath = System.getenv().get("RUTAREPORTES")+this.reportName+".jasper";
            AbsJasperReports.createReport(this.conn, vpath,this.parametros);
            AbsJasperReports.showViewer();        
            this.setAlwaysOnTop(false);
           // this.dispose();
        }else{
            JOptionPane.showMessageDialog(null, "Verifique los valores ingresados");
        }
    }
    */
    public boolean isSearchFieldEmpty() {
        String searchStr = this.txtBusqueda.getText().trim();
        if (searchStr.length() == 0 ) {
            JOptionPane.showMessageDialog(null,"Debe ingresar algun parámetro para la busqueda","Búsqueda",JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }
    
    public void buscar(){
        DefaultTableModel dtm = (DefaultTableModel) gridBienes.getModel();
        listadeBienes.clear(); //Es posible que no haga falta cargar esta lista.
        dtm.setRowCount(0);
        this.inicializarParametros();
        switch (filter){
            case NRO_INVENTARIO:
                if (!isSearchFieldEmpty()){
                    findByNroInventario();
                }                    
                break;
            case DESCRIPCION:
                if (!isSearchFieldEmpty()) {
                    findByDescripcion();
                }
                break;
           case SECTOR:
                findBySector();
                break;
           case RESPONSABLE:
                findByResponsable();   
                break;
           case RANGO:
               findByRango();
               break;
           case FECHA:
               findByFecha();
               break;
            }                        
    }
    
    private void findByResponsable() {
        Responsable responsable = (Responsable) cmbResponsable.getSelectedItem();
        List<Bien> bienes = asignacionDao.findAsignacionesByResponsable(responsable);
        if (bienes.isEmpty()) {
            showNoResults("Responsable");
        } else {
            mostrarBienes(bienes);
        }        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grBusqueda = new javax.swing.ButtonGroup();
        btngrp = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        gridBienes = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtBusqueda = new javax.swing.JTextField();
        rdNroInventario = new javax.swing.JRadioButton();
        rdDescripcion = new javax.swing.JRadioButton();
        cmbResponsable = new javax.swing.JComboBox();
        rdSector = new javax.swing.JRadioButton();
        rdResponsable = new javax.swing.JRadioButton();
        cmbSector = new javax.swing.JComboBox();
        btnBuscar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        rdRangoInentario = new javax.swing.JRadioButton();
        Ndesde = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nHasta = new javax.swing.JTextField();
        rdFecha = new javax.swing.JRadioButton();
        fDede = new javax.swing.JTextField();
        fHasta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buscador Bienes - Patrimonio");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );

        gridBienes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nro. Inventario", "Descripción", "Sector", "Responsable"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        gridBienes.setName("gridBienes"); // NOI18N
        jScrollPane2.setViewportView(gridBienes);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Parámetros de búsqueda"));

        txtBusqueda.setName("txtBusqueda"); // NOI18N
        txtBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaActionPerformed(evt);
            }
        });
        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyPressed(evt);
            }
        });

        grBusqueda.add(rdNroInventario);
        rdNroInventario.setSelected(true);
        rdNroInventario.setText("Nro de Inventario");
        rdNroInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNroInventarioActionPerformed(evt);
            }
        });

        grBusqueda.add(rdDescripcion);
        rdDescripcion.setText("Descripcion");
        rdDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdDescripcionActionPerformed(evt);
            }
        });

        cmbResponsable.setEnabled(false);

        rdSector.setText("Sector");
        rdSector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdSectorActionPerformed(evt);
            }
        });

        rdResponsable.setText("Responsable");
        rdResponsable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdResponsableActionPerformed(evt);
            }
        });

        cmbSector.setEnabled(false);

        btnBuscar.setText("Buscar");
        btnBuscar.setName("btnBuscar"); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        rdRangoInentario.setText("Nro de Inventario");
        rdRangoInentario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdRangoInentarioActionPerformed(evt);
            }
        });

        jLabel1.setText("Desde");

        jLabel2.setText("Hasta");

        rdFecha.setText("Fecha");
        rdFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdFechaActionPerformed(evt);
            }
        });

        jLabel3.setText("Desde");

        jLabel4.setText("Hasta");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdResponsable)
                                    .addComponent(rdSector))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbResponsable, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbSector, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(241, 241, 241))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdFecha)
                            .addComponent(rdRangoInentario)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdNroInventario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdDescripcion))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(nHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Ndesde, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(fHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fDede, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdDescripcion)
                    .addComponent(rdNroInventario))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdSector, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdRangoInentario)
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(Ndesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rdFecha)
                        .addGap(56, 56, 56))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fDede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(fHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImprimir)
                    .addComponent(btnBuscar)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void inicializarParametros(){
        String desde;
        String hasta;
        switch (this.reportName){           
            case  "ListadoBienesNro":
                this.parametros.put("Nro", this.txtBusqueda.getText());
                break;
            case "ListadoBienesDescripcion":
                this.parametros.put("descripcion", this.txtBusqueda.getText());
                break;
            case "ListadoBienesxSector":
                String sector = this.cmbSector.getSelectedItem().toString();
                this.parametros.put("sector", sector);
                break; 
            case "ListadoBienesxResponsable":
                String responsable = this.cmbResponsable.getSelectedItem().toString();
                this.parametros.put("responsable", responsable);
                break;
            case "ListadoBienesD":
                desde = this.Ndesde.getText();
                this.parametros.put("desde", desde);
                break;
            case "ListadoBienesDH":
                desde = this.nHasta.getText();
                hasta = this.nHasta.getText();
                this.parametros.put("desde", desde);
                this.parametros.put("hasta",hasta);
                break;
            case "ListadoBienesFechaD":
                desde = this.fDede.getText();
                this.parametros.put("desde", desde);
                
                break;
            case "ListadoBienesFechaDH":
                desde = this.fDede.getText();
                hasta = this.fHasta.getText();
                this.parametros.put("desde", desde);
                this.parametros.put("hasta",hasta);
                break;
        }
    }
    
    
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        String vpath = System.getenv().get("RUTAREPORTES")+"/"+this.reportName+".jasper";        
        //this.setearParametros();
        this.inicializarParametros();
        AbsJasperReports.createReport(conn, vpath,parametros);
        AbsJasperReports.showViewer();
        
       //JOptionPane.showMessageDialog(null, this.listaBienesImprimir);
       this.setAlwaysOnTop(false);
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        
        
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void rdDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdDescripcionActionPerformed
        filter = DESCRIPCION;
        this.reportName = "ListadoBienesDescripcion";
        txtBusqueda.setEnabled(true);
        cmbSector.setEnabled(false);
        cmbResponsable.setEnabled(false);
    }//GEN-LAST:event_rdDescripcionActionPerformed

    private void rdNroInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdNroInventarioActionPerformed
        filter = NRO_INVENTARIO;
        reportName = "ListadoBienesNro";
        txtBusqueda.setEnabled(true);
        cmbSector.setEnabled(false);
        cmbResponsable.setEnabled(false);
    }//GEN-LAST:event_rdNroInventarioActionPerformed

    private void txtBusquedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyPressed
        int caracter =   evt.getKeyCode() ;
        if(caracter == 10){
            this.buscar();
        }
    }//GEN-LAST:event_txtBusquedaKeyPressed

    private void txtBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaActionPerformed

    }//GEN-LAST:event_txtBusquedaActionPerformed

    private void rdSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdSectorActionPerformed
        filter = SECTOR;
        reportName = "ListadoBienesxSector";
        txtBusqueda.setEnabled(false);
        cmbResponsable.setEnabled(false);
        cmbSector.setEnabled(true);
    }//GEN-LAST:event_rdSectorActionPerformed

    private void rdResponsableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdResponsableActionPerformed
        filter = RESPONSABLE;
        reportName = "ListadoBienesxResponsable";
        txtBusqueda.setEnabled(false);
        cmbSector.setEnabled(false);
        cmbResponsable.setEnabled(true);
    }//GEN-LAST:event_rdResponsableActionPerformed

    private void rdRangoInentarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdRangoInentarioActionPerformed
        // TODO add your handling code here:
        /*
                filter = SECTOR;
        reportName = "ListadoBienesxSector";
        txtBusqueda.setEnabled(false);
        cmbResponsable.setEnabled(false);
        cmbSector.setEnabled(true);
        
        */
        filter = RANGO;
        

        txtBusqueda.setEnabled(false);
        cmbResponsable.setEnabled(false);
        cmbSector.setEnabled(false);
        this.fDede.setEnabled(false);
        this.fHasta.setEnabled(false);
        
        
    }//GEN-LAST:event_rdRangoInentarioActionPerformed

    private void rdFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdFechaActionPerformed
        // TODO add your handling code here:
        filter = FECHA;
        
        // String hasta = this.nHasta.getText().trim();

        txtBusqueda.setEnabled(false);
        cmbResponsable.setEnabled(false);
        cmbSector.setEnabled(false);
        
        this.Ndesde.setEnabled(false);
        this.nHasta.setEnabled(false);
    }//GEN-LAST:event_rdFechaActionPerformed

    private void cleanGrid() {
        tblBienes.getDataVector().removeAllElements();
        tblBienes.fireTableDataChanged();
    }
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
            java.util.logging.Logger.getLogger(FrmBuscarBien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmBuscarBien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmBuscarBien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmBuscarBien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmBuscarBien dialog = new FrmBuscarBien(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField Ndesde;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.ButtonGroup btngrp;
    private javax.swing.JComboBox cmbResponsable;
    private javax.swing.JComboBox cmbSector;
    private javax.swing.JTextField fDede;
    private javax.swing.JTextField fHasta;
    private javax.swing.ButtonGroup grBusqueda;
    private javax.swing.JTable gridBienes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nHasta;
    private javax.swing.JRadioButton rdDescripcion;
    private javax.swing.JRadioButton rdFecha;
    private javax.swing.JRadioButton rdNroInventario;
    private javax.swing.JRadioButton rdRangoInentario;
    private javax.swing.JRadioButton rdResponsable;
    private javax.swing.JRadioButton rdSector;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables
}