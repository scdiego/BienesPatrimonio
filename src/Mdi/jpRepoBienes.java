/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mdi;

import Negocio.Bien;
import Negocio.Inventario;
import Negocio.Responsable;
import Negocio.Sector;
import Negocio.Usuario;
import Persistencia.AsignacionJpaController;
import Persistencia.BienJpaController;
import Persistencia.ResponsableJpaController;
import Persistencia.SectorJpaController;
import Presentacion.CtrlVista;
import Presentacion.FrmLibroBienes;
import Reportes.AbsJasperReports;
import Utilidades.ConsultasDB;
import Utilidades.FechaHora;
import Utilidades.registroDB;
import dbConn.Conexion;
import java.sql.Connection;
import java.sql.Date;
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
public class jpRepoBienes extends javax.swing.JPanel {

    /**
     * Creates new form jpRepoBienes
     */
    
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
    ResponsableJpaController responsableDao = new ResponsableJpaController();
    public boolean baja = false;
    
    
    private Usuario user;
    
        
    private static final int NRO_INVENTARIO = 1;
    private static final int DESCRIPCION = 2;
    private static final int SECTOR = 3;
    private static final int RESPONSABLE = 4;
    private static final int RANGO = 5;
    private static final int FECHA = 6;
  
  
    JDialog viewer = new JDialog (new JFrame(), "Reporte",true);
    Connection conn;
    
    public jpRepoBienes() {
        initComponents();
        
        this.filter = 1;
        tblBienes = new DefaultTableModel();
        this.colocarEncabezadoGrid();
        //this.setTitle("Buscar Bienes");
        fillSectores();
        fillResponsables();
       // groupButtons();
        try {             
             conn = Conexion.obtener();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FrmLibroBienes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setUser(Usuario user) {
        this.user = user;
    }
    private void fillSectores() {
        sectorDao = new SectorJpaController();
        List<Sector> sectores = sectorDao.findSectorEntities();
        for (Sector sector:sectores) {
            this.cmbSector.addItem(sector.getNombre());
        }
    }    
    private void fillResponsables() {
        
        List<Responsable> responsables = responsableDao.findResponsableEntities();
        for (Responsable responsable:responsables) {
            this.cmbResponsable.addItem(responsable.getNombre());
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
                "No se encontraron bienes" + campo,        
                "Algo salió mal",                        
                 JOptionPane.INFORMATION_MESSAGE);

    }    
    public final void findByNroInventario(boolean baja) {
        try {
            int nroInventario = Integer.parseInt(txtBusqueda.getText());
            List<Bien> bienes = dao.findBienByNroInventario(nroInventario,baja);
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
    public final void findByDescripcion(boolean baja){
        ConsultasDB consulta = new ConsultasDB();
        String descrip = txtBusqueda.getText();;
        String strBaja = "";
        if(baja){
            strBaja = " AND BIEN.DEBAJA = true ";
        }
        String sql = "SELECT BIEN.NROINVENTARIO,BIEN.DESCRIPCION,BIEN.ESTADO,RESPONSABLE.NOMBRE as RESPONSABLE,SECTOR.NOMBRE,DATE_FORMAT(BIEN.FECHAALTA,'%d/%m/%Y') as FECHAALTA FROM ASIGNACION INNER JOIN BIEN ON ASIGNACION.BIEN_ID = BIEN.ID LEFT JOIN RESPONSABLE ON ASIGNACION.RESPONSABLE_ID = RESPONSABLE.ID LEFT JOIN SECTOR ON RESPONSABLE.SECTOR_ID = SECTOR.ID WHERE BIEN.DESCRIPCION like '%" + descrip + "%'"+ strBaja;
        List<String> campos = new ArrayList();
        campos.add("NROINVENTARIO");
        campos.add("DESCRIPCION");
        campos.add("ESTADO");
        campos.add("RESPONSABLE");
        campos.add("NOMBRE");
        campos.add("FECHAALTA");
        Object[][] lista = consulta.ejcutarConsulta(sql, campos);
        mostrarConsulta(lista);
    }    
    public final void findBySector(){
        AsignacionJpaController asignacionDao = new AsignacionJpaController();
        Sector sector =  this.sectorDao.findSectorByNombre(cmbSector.getSelectedItem().toString());
        List<Bien> listaBienes = asignacionDao.findBienBySector(sector);
        if (listaBienes.isEmpty()) {
            showNoResults("Sector");
        } else {
            mostrarBienes(listaBienes);
        }
        
    }
    public void mostrarConsulta(Object[][] lista){
        
        int i;
        if (lista.length > 0){
                    for(i=0 ; i < lista.length; i++ ){
            tblBienes.addRow(lista[i]);
        }
        }else{
           showNoResults("");
        }

        
    }
    public final void findBySector(boolean baja){

       ConsultasDB consulta = new ConsultasDB();
       String sectorStr = cmbSector.getSelectedItem().toString();
       String strBaja = "";
       if(baja){
           strBaja = " AND BIEN.DEBAJA = true ";
       }
       String sql = "SELECT BIEN.NROINVENTARIO,BIEN.DESCRIPCION,BIEN.ESTADO,RESPONSABLE.NOMBRE as RESPONSABLE,SECTOR.NOMBRE,DATE_FORMAT(BIEN.FECHAALTA,'%d/%m/%Y') as FECHAALTA FROM ASIGNACION INNER JOIN BIEN ON ASIGNACION.BIEN_ID = BIEN.ID LEFT JOIN RESPONSABLE ON ASIGNACION.RESPONSABLE_ID = RESPONSABLE.ID LEFT JOIN SECTOR ON RESPONSABLE.SECTOR_ID = SECTOR.ID WHERE SECTOR.NOMBRE = '" + sectorStr + "' AND ASIGNACION.fechaHasta is null "+ strBaja;
       List<String> campos = new ArrayList();
       campos.add("NROINVENTARIO");
       campos.add("DESCRIPCION");
       campos.add("ESTADO");
       campos.add("RESPONSABLE");
       campos.add("NOMBRE");
       campos.add("FECHAALTA");
       Object[][] lista = consulta.ejcutarConsulta(sql, campos);
       mostrarConsulta(lista);
    }
    private void findByResponsable(boolean baja) {
        ConsultasDB consulta = new ConsultasDB();
        String responsableStr = cmbResponsable.getSelectedItem().toString();
        String strBaja = "";
        if(baja){
           strBaja = " AND BIEN.DEBAJA = true ";
        }
        String sql = "SELECT BIEN.NROINVENTARIO,BIEN.DESCRIPCION,BIEN.ESTADO,RESPONSABLE.NOMBRE as RESPONSABLE,SECTOR.NOMBRE,DATE_FORMAT(BIEN.FECHAALTA,'%d/%m/%Y') as FECHAALTA FROM ASIGNACION INNER JOIN BIEN ON ASIGNACION.BIEN_ID = BIEN.ID LEFT JOIN RESPONSABLE ON ASIGNACION.RESPONSABLE_ID = RESPONSABLE.ID LEFT JOIN SECTOR ON RESPONSABLE.SECTOR_ID = SECTOR.ID WHERE RESPONSABLE.NOMBRE = '" + responsableStr + "' AND ASIGNACION.fechaHasta is null"+ strBaja;
        List<String> campos = new ArrayList();
        campos.add("NROINVENTARIO");
        campos.add("DESCRIPCION");
        campos.add("ESTADO");
        campos.add("RESPONSABLE");
        campos.add("NOMBRE");
        campos.add("FECHAALTA");
        Object[][] lista = consulta.ejcutarConsulta(sql, campos);
        mostrarConsulta(lista);
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
    public final void findByRango(boolean baja){
        //findBienByNroDesde
        int desde = 0; 
        int hasta = 0;
        String condicion = "";
        if(!"".equals(this.Ndesde.getText())){
            desde =   Integer.parseInt(this.Ndesde.getText());
        }
        if(!"".equals(this.nHasta.getText())){
            hasta = Integer.parseInt(this.nHasta.getText());
        }   
        if (hasta == 0){
          condicion = " BIEN.NROINVENTARIO >= ";
          condicion = condicion.concat(String.valueOf(desde));
        }else{
         condicion = " BIEN.NROINVENTARIO BETWEEN (" + desde + " and " + hasta +" ) ";
         
        }
      ConsultasDB consulta = new ConsultasDB();
       String sectorStr = cmbSector.getSelectedItem().toString();
       String strBaja = "";
       if(baja){
           strBaja = " AND BIEN.DEBAJA = true ";
       }
       String sql = "SELECT BIEN.NROINVENTARIO,BIEN.DESCRIPCION,BIEN.ESTADO,RESPONSABLE.NOMBRE as RESPONSABLE,SECTOR.NOMBRE,DATE_FORMAT(BIEN.FECHAALTA,'%d/%m/%Y') as FECHAALTA FROM ASIGNACION INNER JOIN BIEN ON ASIGNACION.BIEN_ID = BIEN.ID LEFT JOIN RESPONSABLE ON ASIGNACION.RESPONSABLE_ID = RESPONSABLE.ID LEFT JOIN SECTOR ON RESPONSABLE.SECTOR_ID = SECTOR.ID WHERE " + condicion + strBaja;
       List<String> campos = new ArrayList();
       campos.add("NROINVENTARIO");
       campos.add("DESCRIPCION");
       campos.add("ESTADO");
       campos.add("RESPONSABLE");
       campos.add("NOMBRE");
       campos.add("FECHAALTA");
       Object[][] lista = consulta.ejcutarConsulta(sql, campos);
       mostrarConsulta(lista);
        
    }
    public final void findByFecha(boolean baja){
        Date desde = null;
        Date hasta = null;
        BienJpaController dao = new BienJpaController(); 
        
        FechaHora fecha = new FechaHora();
        List<Bien> bienes = new ArrayList();
        
        if(!"".equals(this.fDede.getText())){
            desde = fecha.StringToDate(this.fDede.getText());
        }
        
        if(!"".equals(this.fHasta.getText())) {
            hasta = fecha.StringToDate(this.fHasta.getText());
        }
        
        if(!"".equals(this.fHasta.getText())) {
            bienes = dao.findBienesByFecha(desde, hasta);
        }else{
            if(baja) {
                bienes = dao.findBienesByFechaBaja(desde);
            }else{
                bienes = dao.findBienesByFecha(desde);
            }
            
        }
        
        if (bienes.isEmpty()) {
            showNoResults("Fechas");
        } else {
            mostrarBienes(bienes);
        }  
        
    }    
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
                        findByNroInventario(this.baja);                    
                }                    
                break;
            case DESCRIPCION:
                if (!isSearchFieldEmpty()) {
                    findByDescripcion(this.baja);
                }
                break;
           case SECTOR:
                findBySector(this.baja);
                break;
           case RESPONSABLE:
                findByResponsable(this.baja);   
                break;
           case RANGO:
               findByRango(this.baja);
               break;
           case FECHA:
               findByFecha(this.baja);
               break;
            }                        
    }
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
            case "ListadoBienesNroD":
                desde = this.Ndesde.getText();
                this.parametros.put("desde", desde);
                break;
            case "ListadoBienesNroDH":
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grOpciones = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txtBusqueda = new javax.swing.JTextField();
        rdNroInventario = new javax.swing.JRadioButton();
        rdDescripcion = new javax.swing.JRadioButton();
        rdSector = new javax.swing.JRadioButton();
        rdResponsable = new javax.swing.JRadioButton();
        cmbSector = new javax.swing.JComboBox<>();
        cmbResponsable = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        rdRangoInentario = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        Ndesde = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nHasta = new javax.swing.JTextField();
        rdFecha = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        chBaja = new javax.swing.JCheckBox();
        fDede = new javax.swing.JFormattedTextField();
        fHasta = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        gridBienes = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Parámetros de búsqueda"));

        grOpciones.add(rdNroInventario);
        rdNroInventario.setSelected(true);
        rdNroInventario.setText("Nro de Inventario");
        rdNroInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNroInventarioActionPerformed(evt);
            }
        });

        grOpciones.add(rdDescripcion);
        rdDescripcion.setText("Descripcion");
        rdDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdDescripcionActionPerformed(evt);
            }
        });

        grOpciones.add(rdSector);
        rdSector.setText("Sector");
        rdSector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdSectorActionPerformed(evt);
            }
        });

        grOpciones.add(rdResponsable);
        rdResponsable.setText("Responsable");
        rdResponsable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdResponsableActionPerformed(evt);
            }
        });

        cmbSector.setEnabled(false);

        cmbResponsable.setEnabled(false);

        jButton1.setText("Imprimir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Buscar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        grOpciones.add(rdRangoInentario);
        rdRangoInentario.setText("Nro de Inventario");
        rdRangoInentario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdRangoInentarioActionPerformed(evt);
            }
        });

        jLabel1.setText("Desde");

        jLabel2.setText("Hasta");

        grOpciones.add(rdFecha);
        rdFecha.setText("Fecha");
        rdFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdFechaActionPerformed(evt);
            }
        });

        jLabel3.setText("Desde");

        jLabel4.setText("Hasta");

        chBaja.setText("Solo Baja");
        chBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chBajaActionPerformed(evt);
            }
        });

        fDede.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));

        fHasta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBusqueda, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jButton2)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(nHasta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                        .addComponent(Ndesde, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(fDede, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdSector)
                                .addGap(49, 49, 49)
                                .addComponent(cmbSector, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdResponsable)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbResponsable, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rdNroInventario)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdDescripcion))
                                    .addComponent(rdRangoInentario)
                                    .addComponent(rdFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chBaja)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdNroInventario)
                    .addComponent(rdDescripcion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdSector)
                    .addComponent(cmbSector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdResponsable)
                    .addComponent(cmbResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(rdRangoInentario)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(Ndesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdFecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fDede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(fHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chBaja)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)))
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

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(gridBienes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String hasta = this.nHasta.getText().trim();
       if(filter == RANGO){
                if("".equals(hasta)){
                    reportName = "ListadoBienesD";
                    this.nHasta.setEnabled(false);
                }else{
                    reportName = "ListadoBienesDH";
                }

                if("".equals(hasta)){
                    reportName = "ListadoBienesD";
                    this.nHasta.setEnabled(false);
                }else{
                    reportName = "ListadoBienesDH";
                }
       }else{
           if(filter == FECHA){
               
           
                if("".equals(hasta)){
                    reportName = "ListadoBienesFechaD";
                    this.nHasta.setEnabled(false);
                }else{
                    reportName = "ListadoBienesFechaDH";
                }   

                if("".equals(hasta)){
                    reportName = "ListadoBienesFechaD";
                    this.nHasta.setEnabled(false);
                }else{
                    reportName = "ListadoBienesFechaDH";

               }
       }
        } 

        

        
        buscar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(this.baja){
            this.reportName = this.reportName.concat("Baja");
        }
        String vpath = System.getenv().get("RUTAREPORTES")+"/"+this.reportName+".jasper";        
        //this.setearParametros();
        this.inicializarParametros();
        AbsJasperReports.createReport(conn, vpath,parametros);
        AbsJasperReports.showViewer();
        
       //JOptionPane.showMessageDialog(null, this.listaBienesImprimir);
       //this.setAlwaysOnTop(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void rdNroInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdNroInventarioActionPerformed
        // TODO add your handling code here:
        filter = NRO_INVENTARIO;
        reportName = "ListadoBienesNro";
        txtBusqueda.setEnabled(true);
        cmbSector.setEnabled(false);
        cmbResponsable.setEnabled(false);
    }//GEN-LAST:event_rdNroInventarioActionPerformed

    private void rdDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdDescripcionActionPerformed
        // TODO add your handling code here:
                filter = DESCRIPCION;
        this.reportName = "ListadoBienesDescripcion";
        txtBusqueda.setEnabled(true);
        cmbSector.setEnabled(false);
        cmbResponsable.setEnabled(false);
    }//GEN-LAST:event_rdDescripcionActionPerformed

    private void rdSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdSectorActionPerformed
        // TODO add your handling code here:
                filter = SECTOR;
        reportName = "ListadoBienesxSector";
        txtBusqueda.setEnabled(false);
        cmbResponsable.setEnabled(false);
        cmbSector.setEnabled(true);
    }//GEN-LAST:event_rdSectorActionPerformed

    private void rdResponsableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdResponsableActionPerformed
        // TODO add your handling code here:
                filter = RESPONSABLE;
        reportName = "ListadoBienesxResponsable";
        txtBusqueda.setEnabled(false);
        cmbSector.setEnabled(false);
        cmbResponsable.setEnabled(true);
    }//GEN-LAST:event_rdResponsableActionPerformed

    private void rdFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdFechaActionPerformed
               filter = FECHA;
        
        // String hasta = this.nHasta.getText().trim();
        String hasta = this.fHasta.getText().trim();
       

        txtBusqueda.setEnabled(false);
        cmbResponsable.setEnabled(false);
        cmbSector.setEnabled(false);
        
        this.Ndesde.setEnabled(false);
        this.nHasta.setEnabled(false);
        
        this.fDede.setEnabled(true);
        this.fHasta.setEnabled(true);
    }//GEN-LAST:event_rdFechaActionPerformed

    private void rdRangoInentarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdRangoInentarioActionPerformed
        filter = RANGO;
        
       // String hasta = this.nHasta.getText().trim();
       

        txtBusqueda.setEnabled(false);
        cmbResponsable.setEnabled(false);
        cmbSector.setEnabled(false);
        this.fDede.setEnabled(false);
        this.fHasta.setEnabled(false);
        this.nHasta.setEnabled(true);
        this.nHasta.setEnabled(true);
    }//GEN-LAST:event_rdRangoInentarioActionPerformed

    private void chBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chBajaActionPerformed
        // TODO add your handling code here:
        this.baja = !this.baja;
    }//GEN-LAST:event_chBajaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Ndesde;
    private javax.swing.JCheckBox chBaja;
    private javax.swing.JComboBox<String> cmbResponsable;
    private javax.swing.JComboBox<String> cmbSector;
    private javax.swing.JFormattedTextField fDede;
    private javax.swing.JFormattedTextField fHasta;
    private javax.swing.ButtonGroup grOpciones;
    private javax.swing.JTable gridBienes;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
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
