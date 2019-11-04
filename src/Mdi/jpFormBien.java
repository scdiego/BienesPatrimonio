/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mdi;

import Negocio.Asignacion;
import Negocio.Bien;
import Negocio.Responsable;
import Negocio.Sector;
import Negocio.Usuario;
import Persistencia.BienJpaController;
import Persistencia.ResponsableJpaController;
import Persistencia.SectorJpaController;
import Persistencia.AsignacionJpaController;
import Persistencia.exceptions.NonexistentEntityException;
import Presentacion.CtrlVista;
import Presentacion.FrmCargo;
import Presentacion.FrmLibroBienes;
import Reportes.AbsJasperReports;
import Utilidades.FechaHora;
import Utilidades.ImprimirEtiqueta;
import Utilidades.formatoBigDecimal;
import com.sun.glass.events.KeyEvent;
import dbConn.Conexion;
import java.awt.EventQueue;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author diego
 */
public class jpFormBien extends javax.swing.JPanel {

    private final DefaultListModel modeloBienes = new DefaultListModel();
    private boolean nuevoBien;
    private Bien unBien;
    private final CtrlVista logica = new CtrlVista();
    //private java.awt.Frame miparent;
    private MainMdi miparent;
    private BienJpaController dao = new BienJpaController();
    private Usuario user;
    private Responsable unResponsable;
    
    SectorJpaController sectorDao = new SectorJpaController();
    AsignacionJpaController asignacionDao = new AsignacionJpaController();
    ResponsableJpaController responsableDao = new ResponsableJpaController();
    
    private boolean asignar = false;
    Connection conn;
    
    ArrayList respon = new ArrayList();
    ArrayList areas = new ArrayList();
    /**
     * Creates new form jpFormBien
     */
    public jpFormBien() {
        initComponents();
        //this.cargarUltimo();
       
        try {             
             conn = Conexion.obtener();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FrmLibroBienes.class.getName()).log(Level.SEVERE, null, ex);
        }
        respon = responsableDao.arrayResponsables();
        areas = sectorDao.arraySectores();
        this.completarResponsalbe();
        this.completarArea();
        this.ultimo();
    }
    
  // video del autocomplete

    public JFrame getMiparent() {
        return miparent;
    }

    public void setMiparent(MainMdi miparent) {
        this.miparent = miparent;
    }

    
    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    
    
    public void inicializarUsuario(Usuario user){
        this.setUser(user);
    }
    

    
    private void fillBienesList() {
        modeloBienes.clear();
        List<Bien> bienes = dao.findBienEntities();
        for (Bien bien:bienes) {
            modeloBienes.addElement(bien);
        }
    }
    
    private void primero(){
        this.unBien = dao.primerBien();
        this.btbModificar.setEnabled(!this.unBien.isDebaja());
        this.btnEliminar.setEnabled(!this.unBien.isDebaja());
        this.completarTextos();
        
    }

    
    private void anterior(){
        int nro =  Integer.parseInt(this.txtNrodeInventario.getText());
        String salida = String.valueOf(dao.anteriorNroInventario(nro));
        this.txtNrodeInventario.setText(salida);
        this.buscar();
        this.completarTextos();
    }
    private void siguiente(){
        Integer nro = Integer.parseInt(txtNrodeInventario.getText()); 
        int salida = dao.siguienteNroInventaio(nro);
        this.txtNrodeInventario.setText(String.valueOf(salida));
        this.buscar();
        this.completarTextos();
        
    }
    private void ultimo(){
        this.cargarUltimo();        
    }
    
    private void habilitarNavegacionBotones(int nro){
        int max = dao.utltimoNroInventario();
        
        jButton3.setEnabled(true);
        jButton4.setEnabled(true);
        jButton5.setEnabled(true);
        jButton6.setEnabled(true);
        
        if(nro == max){
            //deshabilito siguientes
            jButton5.setEnabled(false);
            jButton6.setEnabled(false);

        }
        
        if(nro == 1){
            //deshabilito anteriores
            jButton3.setEnabled(false);
            jButton4.setEnabled(false);
        }
    }
    
    public void cargarUltimo(){
        try{
            this.unBien = this.dao.ultimoBien();
            this.btbModificar.setEnabled(!this.unBien.isDebaja());
            this.btnEliminar.setEnabled(!this.unBien.isDebaja());
            /* 
                deshabilito mover para adelante
            */
            
            this.completarTextos();
        }
        catch (NullPointerException e){
            // nada
        }
    }
    
    public void activarComponentes(boolean var ){
      this.activarBotonesRecorrida();  
      this.btnNuevo.setEnabled(true);
      this.btnSalir.setEnabled(true);
    }
    
    public void habilitarPermisos(){
        this.btbModificar.setEnabled(this.user.habilitarEdicion());
        this.btnEliminar.setEnabled(this.user.habilitarBaja());
        this.btnGuardar.setEnabled(this.user.habilitarEdicion());
        this.btnNuevo.setEnabled(this.user.habilitarNuevo());
        
    }
 /**   
    public void buscar(){
        // TODO add your handling code here:
        String nro = this.txtNrodeInventario.getText();
        //if(!"".equals(nro)){
            if(this.isInteger(nro) && !"".equals(nro) ){
                List<Bien> lista = this.dao.findBienByNroInventario(Integer.parseInt(this.txtNrodeInventario.getText()));
                this.limpiarComponentes();
                if(lista.size() > 0){
                    this.unBien = lista.get(0);
                    this.btbModificar.setEnabled(!this.unBien.isDebaja());
                    this.btnEliminar.setEnabled(!this.unBien.isDebaja());            
            //this.b
                }else{
                    //JOptionPane.showMessageDialog(null, "No se encontro ningun bien con el nro ingresado.");
                }
            this.completarTextos(); 
            }
        
       
    }
  */  
    public boolean buscar(){
        // TODO add your handling code here:
        String nro = this.txtNrodeInventario.getText();
        boolean salida = false;
        //if(!"".equals(nro)){
            if(this.isInteger(nro) && !"".equals(nro) ){
                List<Bien> lista = this.dao.findBienByNroInventario(Integer.parseInt(this.txtNrodeInventario.getText()));
                this.limpiarComponentes();
                if(lista.size() > 0){
                    this.unBien = lista.get(0);
                    this.btbModificar.setEnabled(!this.unBien.isDebaja());
                    this.btnEliminar.setEnabled(!this.unBien.isDebaja());   
                    salida = true;
            //this.b
                }
            
            }
            return salida;
    }
    
    public boolean isInteger( String input )
{
   try
   {
      Integer.parseInt( input );
      return true;
   }
   catch( Exception e)
   {
      return false;
   }
}

    
    private Asignacion getAsignacionFromForm(Responsable unResponsable, Responsable unSubResponsable,Bien unBien) {
         
        String strDate = new SimpleDateFormat("yyyy-dd-MM").format(Calendar.getInstance().getTime());
        java.util.Date fechaDesde = null;
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
        return new Asignacion(unResponsable, unBien, fechaDesde, unSubResponsable);
    }
    
    private void crearBien() {
        if(this.camposRequeridos()){
            Bien bn = getBienFromForm();
            dao.create(bn,this.getUser());
            java.util.Date fechaDesde = null;
            String strDate = new SimpleDateFormat("yyyy-dd-MM").format(Calendar.getInstance().getTime());
            /*Creo asignacion */
            
            if(asignar){
            String nombreResponsable = this.txtResponsable.getSelectedItem().toString();
            String nombreArea = this.txtArea.getSelectedItem().toString();
            String nombreSubResponsable = this.txtSubResponsable.getSelectedItem().toString();
            if(nombreResponsable != ""){
               try { 
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
                fechaDesde = sdf.parse(strDate);
                 } catch (ParseException ex) {
                  JOptionPane.showMessageDialog(this, 
                    "El formato de la fecha es incorrecto",
                    "Algo salió mal", 
                    JOptionPane.WARNING_MESSAGE);   
                 }
                Responsable unSubResponsable = responsableDao.findResponsableByNombre(nombreSubResponsable);
                Asignacion unaAsignacion = new Asignacion(unResponsable,bn, fechaDesde, unSubResponsable);
                asignacionDao.create(unaAsignacion,this.user,bn);
                this.jButton2.setEnabled(true);
                //Responsable responsable, Bien bien, Date fechaDesde, Responsable subResponsable
            }
            }
            
            //logica.crearBien(unCodigo,unNroInventario,unaDescripcion,unaFechaAlta,unNroActa,unValor,unNroExpedienteAlta,unaResolucionAlta); 
            this.fillBienesList();
            habilitarTextos(false);
            limpiarComponentes();                   
            deshabilitarBotones();
            habilitarBotones(); 
            this.cargarUltimo();
         }else{
            JOptionPane.showMessageDialog(null, "No se puede guardar la infomación.\nDebe completar los datos requeridos.");
       }
    }
    private void modificarBien() {
       if(this.camposRequeridos()){
            try{
            Bien newBien = this.getBienFromForm();
            this.dao.update(newBien, this.unBien.getId(),this.getUser());
            fillBienesList();
            this.habilitarTextos(false);
            this.limpiarComponentes();        
            this.prepararBotones();  
            }
            catch (NonexistentEntityException e){
              JOptionPane.showMessageDialog(null, "No se puede guardar la infomación.\nDebe completar los datos requeridos.");  
            }
       }
       
    }
       
    
    
     private void prepararBotones() {
        this.deshabilitarBotones();
        this.habilitarBotones();
    }
    
    private void habilitarBotones() {
        this.btnNuevo.setEnabled(true);
        this.btnSalir.setEnabled(true);
    }
    
    private void deshabilitarBotones() {
        this.btbModificar.setEnabled(false);
        this.btnCancelar.setEnabled(false);                    
        this.btnEliminar.setEnabled(false);
        this.btnGuardar.setEnabled(false);
    }
    
    private Bien getBienFromForm() {
        FechaHora fecha = new FechaHora();
        String code = txtCodigo.getText();
        BigDecimal valor;
       
        Integer stockNumber = Integer.parseInt(txtNrodeInventario.getText()); 
        
        String description = txtDescripcion.getText(); 
        
        Date fechaAlta = fecha.StringToDate(txtFechaAlta.getText());
        
        String nroActa = txtNroActa.getText();
        
       // try {
            valor = validarValor(txtValor.getText());                    
       // } catch(NumberFormatException ex){{
       //     valor = new BigDecimal(0.0);
       // }
        
        
        String nroExpedienteAlta = txtNroExpediente.getText();
        
        String resolucionAlta = txtResolucion.getText();
        
        return new Bien(code, stockNumber, description, fechaAlta, nroActa, valor, nroExpedienteAlta, resolucionAlta );
        
        
    }
    
    public BigDecimal validarValor(String unString){
        unString = unString.replace(",", ".");
        //return  formatoBigDecimal.stringToBigDecimal(unString);
        BigDecimal bigDecimalCurrency=new BigDecimal(unString);
        return bigDecimalCurrency;
    }

    public final void habilitarTextos(boolean habilitar){
        this.txtCodigo.setEnabled(habilitar);
        this.txtDescripcion.setEnabled(habilitar);
        this.txtNroExpediente.setEnabled(habilitar);
        this.txtNroActa.setEnabled(habilitar);
        this.txtFechaAlta.setEnabled(habilitar);
        this.txtNrodeInventario.setEnabled(habilitar);
        this.txtResolucion.setEnabled(habilitar);
        this.txtValor.setEnabled(habilitar);        
        this.txtResponsable.setEnabled(habilitar); 
        this.txtArea.setEnabled(habilitar); 
        this.txtSubResponsable.setEnabled(habilitar); 
        
        
    }
    public final void limpiarComponentes(){
        this.txtCodigo.setText(null);
        this.txtDescripcion.setText(null);
        this.txtNroExpediente.setText(null);
        this.txtNroActa.setText(null);
        this.txtFechaAlta.setText(null);
        this.txtNrodeInventario.setText(null);
        this.txtResolucion.setText(null);
        this.txtValor.setText(null);
        this.txtResponsable.setSelectedItem("Seleccione un Responsable");
        this.txtSubResponsable.setSelectedItem("Seleccione un Responsable");
        this.txtArea.setSelectedItem("Seleccione Sector");
      //  this.txtResolucionBaja.setText(null);
      //  this.txtFechaBaja.setText(null);
       
    }
    

    public final void activarBM(boolean activar){
        this.btnEliminar.setEnabled(activar);
        this.btbModificar.setEnabled(activar);      
        this.btnCancelar.setEnabled(activar);
        this.btnEliminar.setEnabled(activar);
        this.btnGuardar.setEnabled(activar);
        this.btnNuevo.setEnabled(!activar);
        this.btnSalir.setEnabled(!activar);
               
        this.activarBotonesRecorrida();
    }
    
    public final void activarBotonesRecorrida(){
        boolean var = false;
        if(this.modeloBienes.size() > 0){
            var = true;
        }     
    }
    
    public boolean camposRequeridos(){
        boolean salida = true;
        //if()
        
        
        return salida;
    }
    
    public final void seleccionarBien(){              
        this.completarTextos();        
    }
    public final void completarTextos(){
        this.txtResponsable.setSelectedItem("Seleccione un Responsable");
        this.txtSubResponsable.setSelectedItem("Seleccione un Responsable");
        this.txtArea.setSelectedItem("Seleccione Sector");
        this.txtFechaBaja.setText(null);
        this.txtResolucionBaja.setText(null);

        if(this.unBien.getCodigo() != null){
            this.txtCodigo.setText(this.unBien.getCodigo());
        }
        
        if(this.unBien.getDescripcion() != null){
            this.txtDescripcion.setText(this.unBien.getDescripcion());
        }
        
        FechaHora fecha = new FechaHora();
        
        if(this.unBien.getNroActa() != null){
            this.txtNroActa.setText(this.unBien.getNroActa());
        }
        
        if(this.unBien.getNroExpedienteAlta() != null){
            this.txtNroExpediente.setText(this.unBien.getNroExpedienteAlta());
        }
        
        if(this.unBien.getFechaAlta() != null){
            this.txtFechaAlta.setText(fecha.DateToString(this.unBien.getFechaAlta()));
        }
        
        if(this.unBien.getNroInventario() != null){
            this.txtNrodeInventario.setText(this.unBien.getNroInventario().toString());
        }
        
        if(this.unBien.getResolucionAlta() != null){
            this.txtResolucion.setText(this.unBien.getResolucionAlta());
        }
        
        if(this.unBien.getValor() != null){
            
            this.txtValor.setText(formatoBigDecimal.bigDecimalToString(this.unBien.getValor()));
        }
        /*BAJA*/
        if(this.unBien.getResolucionBaja() != null){
            this.txtResolucionBaja.setText(this.unBien.getResolucionBaja());
        }
        
        if(this.unBien.getFechaBaja() != null){
            this.txtFechaBaja.setText(fecha.DateToString(this.unBien.getFechaBaja()));
        }
         this.competarTextosCargos();
         this.habilitarNavegacionBotones(this.unBien.getNroInventario());
    }
    
    public void competarTextosCargos(){
        Responsable unResponsable ;
        Sector unSector;
        Responsable unSubResponsable;
        this.jButton2.setEnabled(false);
        try{
            unResponsable = this.unBien.responsableAsignado();
            unSector = unResponsable.getSector();
            unSubResponsable = this.unBien.SubresponsableAsignado();
            
            //this.txtResponsable.setSelectedItem(unResponsable.getNombre()); //.setText(this.unBien.responsableAsignado().getNombre());
            this.txtResponsable.setSelectedItem(unResponsable.getNombre());//.setText(unResponsable.getNombre());
            
            this.txtArea.setSelectedItem(unSector.getNombre());//.setText(unSector.getNombre());  
            this.txtSubResponsable.setSelectedItem(this.unBien.SubresponsableAsignado().getNombre());

        }catch (NullPointerException e){
           //int respuesta = JOptionPane.showConfirmDialog(null, "Error al completar texto cargos", "Confirmación", JOptionPane.YES_NO_OPTION);
           // this.jButton2.setEnabled(false);
        }
        String s1 = this.txtResponsable.getSelectedItem().toString(); //.setSelectedItem("Seleccione un Responsable");
        String s3 = this.txtArea.getSelectedItem().toString();//.setSelectedItem("Seleccione Sector");
        if(!s1.equals("Seleccione un Responsable") || !s3.equals("Seleccione Sector")){
            this.jButton2.setEnabled(true);
        }
 
    }
    
    public void completarResponsalbe(){
        //respon
        int i ;
        for (i=0;i < respon.size();i++){
            this.txtResponsable.addItem(respon.get(i).toString());
            this.txtSubResponsable.addItem(respon.get(i).toString());            
        }
    }
    public void completarArea(){
        int i ;
        for (i=0;i < areas.size();i++){
            this.txtArea.addItem(areas.get(i).toString());
        } 
    }
    public final void activarBotonesSeleccion(){
       /*     this.activarBM(true);
            this.btbModificar.setEnabled(true);
            this.btnCancelar.setEnabled(true);
            this.btnEliminar.setEnabled(true);
            this.btnGuardar.setEnabled(false);
            this.btnNuevo.setEnabled(false);
            this.btnSalir.setEnabled(true);
        */
    }
    
    public void accionCancelarBoton(boolean limpiarCampos){
        // TODO add your handling code here:
        if(this.btnGuardar.isEnabled()){
            int respuesta = JOptionPane.showConfirmDialog(null, "Está editando un Bien.\n¿Desea salir de todos modos?", "Confirmación", JOptionPane.YES_NO_OPTION);
            switch(respuesta) {
                case JOptionPane.YES_OPTION:
                //--- Operaciones en caso afirmativo
                    this.nuevoBien = true;
                    this.activarBM(false);
                    
                    if(limpiarCampos){
                        this.limpiarComponentes();
                    }else{
                        this.miparent.ocultarBien();    
                    }
                    this.cargarUltimo();
                            
                    
                break;
            }
        }else{
        this.miparent.ocultarBien();
        }
    }

    
    public void guardarAsignacion(){
       
        String responsabletxt = txtResponsable.getSelectedItem().toString();
        String sectorTxt = txtArea.getSelectedItem().toString();
        
        ResponsableJpaController daoResponsable = new ResponsableJpaController();
        Responsable responsable = daoResponsable.ObtenerResponsalble(responsabletxt,sectorTxt);
        
        String strDate = new SimpleDateFormat("yyyy-dd-MM").format(Calendar.getInstance().getTime());
        java.util.Date fechaDesde = null;
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
        
        Asignacion  asig = new Asignacion(unResponsable, this.unBien, fechaDesde, null);
    }
    
    public void imprimirCargo(){
        String reportName = "PlanilladeCargo";
        String nroInventario = this.unBien.getNroInv();
        
        String dni = "";
        try{
            dni = this.asignacionDao.findResponsableByNroInventario(Integer.parseInt(nroInventario)).getDni();
        }
        catch(NullPointerException e){
            dni = "";
        }
        
        String nombreResponsable = "";        
        try{
            nombreResponsable = this.asignacionDao.findResponsableByNroInventario(Integer.parseInt(nroInventario)).getApeyNom();
        }catch(NullPointerException e){
            nombreResponsable = "";
        }
        String sector = "que poner";
        
        try{
            sector = this.asignacionDao.findSectorByNroInventario(Integer.parseInt(nroInventario)).getNombre();        
        }catch(NullPointerException e){
            sector = "nada";
        }
        String estado = this.unBien.getEstado();
        
        FechaHora mifecha = new FechaHora();
        
        String fecha = " ,"  + mifecha.diaActual()+ " de "+ mifecha.mesActual() + " del " + mifecha.anioActual();
        
        Map<String,Object> parametros = new HashMap();
        
        parametros.put("nroInventario", nroInventario);
        parametros.put("dni",dni);
        parametros.put("nombreResponsable",nombreResponsable);
        parametros.put("sector",sector);
        parametros.put("estado",estado);
        parametros.put("fecha",fecha);
        
        parametros.put("ruta","/home/diego/proyectos/PatrimonioGitCC/src/Reportes/");       
        //String vpath = System.getenv().get("RUTAREPORTES")+"/"+reportName+".jasper";        
        
        String vpath = "/home/diego/proyectos/PatrimonioGitCC/src/Reportes/"+reportName+".jasper";
        
        AbsJasperReports.createReport(conn, vpath,parametros);
        AbsJasperReports.showViewer();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.nro
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNrodeInventario = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNroActa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtResolucion = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNroExpediente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        txtFechaAlta = new javax.swing.JFormattedTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        txtValor = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btbModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        panelText = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        txtResponsable = new javax.swing.JComboBox<>();
        txtArea = new javax.swing.JComboBox<>();
        txtSubResponsable = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtResolucionBaja = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtFechaBaja = new javax.swing.JTextField();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Nro de Inventario");

        txtNrodeInventario.setName("txtNrodeInventario"); // NOI18N
        txtNrodeInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNrodeInventarioActionPerformed(evt);
            }
        });
        txtNrodeInventario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNrodeInventarioKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNrodeInventarioKeyPressed(evt);
            }
        });

        jLabel7.setText("Nro de Acta");

        txtNroActa.setEnabled(false);
        txtNroActa.setName("txtNroActa"); // NOI18N

        jLabel9.setText("Resolucion");

        txtResolucion.setEnabled(false);
        txtResolucion.setName("txtResolucion"); // NOI18N

        jLabel3.setText("Nomenclador");

        txtCodigo.setEnabled(false);
        txtCodigo.setName("txtCodigo"); // NOI18N

        jLabel6.setText("Valor");

        jLabel5.setText("Fecha de Alta");

        jLabel8.setText("Nro de Expediente ");

        txtNroExpediente.setEnabled(false);
        txtNroExpediente.setName("txtNroExpediente"); // NOI18N

        txtDescripcion.setColumns(15);
        txtDescripcion.setRows(5);
        txtDescripcion.setEnabled(false);
        txtDescripcion.setName("txtDescripcion"); // NOI18N
        jScrollPane2.setViewportView(txtDescripcion);

        jLabel4.setText("Descripcion");

        try {
            txtFechaAlta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFechaAlta.setEnabled(false);

        jButton3.setText("<<");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("<");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText(">");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText(">>");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValorKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValorKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCodigo)
                                    .addComponent(txtNroActa)
                                    .addComponent(txtResolucion)
                                    .addComponent(txtNrodeInventario)
                                    .addComponent(txtFechaAlta, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(txtNroExpediente)
                                    .addComponent(txtValor)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addContainerGap(28, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNrodeInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(txtNroActa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addComponent(jLabel9))
                    .addComponent(txtResolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFechaAlta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txtNroExpediente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNuevo.setText("Nuevo");
        btnNuevo.setName("btnNuevo"); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btbModificar.setText("Modificar");
        btbModificar.setEnabled(false);
        btbModificar.setName("btnModificar"); // NOI18N
        btbModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbModificarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Baja");
        btnEliminar.setEnabled(false);
        btnEliminar.setName("btnEliminar"); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
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

        btnGuardar.setEnabled(false);
        btnGuardar.setLabel("Guardar");
        btnGuardar.setName("btnGuardar"); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.setName("btnSalir"); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jButton1.setText("Imprimir Etiqueta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btbModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btbModificar)
                    .addComponent(btnEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar)
                    .addComponent(btnSalir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        panelText.setBorder(javax.swing.BorderFactory.createTitledBorder("Responsable"));

        jLabel10.setText("Responsable");

        jLabel11.setText("Área");

        jLabel1.setText("Sub Responsable");

        jButton2.setText("Imprimir Cargo");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtResponsable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un Responsable" }));
        txtResponsable.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtResponsableFocusLost(evt);
            }
        });
        txtResponsable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtResponsableMouseClicked(evt);
            }
        });

        txtArea.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un Area" }));

        txtSubResponsable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un Sub Responsable" }));

        javax.swing.GroupLayout panelTextLayout = new javax.swing.GroupLayout(panelText);
        panelText.setLayout(panelTextLayout);
        panelTextLayout.setHorizontalGroup(
            panelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTextLayout.createSequentialGroup()
                .addGroup(panelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelTextLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelTextLayout.createSequentialGroup()
                        .addGroup(panelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtResponsable, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtArea, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSubResponsable, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        panelTextLayout.setVerticalGroup(
            panelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTextLayout.createSequentialGroup()
                .addGroup(panelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSubResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Baja"));

        jLabel13.setText("Resolucion");

        txtResolucionBaja.setEnabled(false);

        jLabel12.setText("Fecha");

        txtFechaBaja.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtResolucionBaja, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                    .addComponent(txtFechaBaja))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtResolucionBaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFechaBaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(466, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(panelText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(462, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNrodeInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNrodeInventarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNrodeInventarioActionPerformed

    private void txtNrodeInventarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNrodeInventarioKeyTyped
        // TODO add your handling code here:
        char vchar = evt.getKeyChar();
        if( !Character.isDigit(vchar) & vchar != KeyEvent.VK_BACKSPACE & vchar != KeyEvent.VK_DELETE ){
            evt.consume();
        }
    }//GEN-LAST:event_txtNrodeInventarioKeyTyped

    private void txtNrodeInventarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNrodeInventarioKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            String nro = this.txtNrodeInventario.getText();
            if(!"".equals(nro)){
                this.buscar();
                this.completarTextos();

            }else{
                JOptionPane.showMessageDialog(null, "Ingrese un Nro de Inventario.");
            }
        }
    }//GEN-LAST:event_txtNrodeInventarioKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.primero();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.anterior();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.siguiente();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        this.ultimo();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        this.habilitarTextos(true);
        this.btbModificar.setEnabled(false);
        this.btnCancelar.setEnabled(true);
        this.btnEliminar.setEnabled(false);
        this.btnGuardar.setEnabled(true);
        this.btnNuevo.setEnabled(false);
        this.btnSalir.setEnabled(true);
        this.jButton2.setEnabled(false);
        this.nuevoBien=true;
        this.limpiarComponentes();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btbModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbModificarActionPerformed
        this.nuevoBien = false;
        this.txtCodigo.setEnabled(true);
        this.txtDescripcion.setEnabled(true);
        this.txtNroExpediente.setEnabled(true);
        this.txtNroActa.setEnabled(true);
        this.txtFechaAlta.setEnabled(true);
        this.txtNrodeInventario.setEnabled(true);
        this.txtValor.setEnabled(true);
        this.txtResolucion.setEnabled(true);
        this.btbModificar.setEnabled(false);
        this.btnCancelar.setEnabled(true);
        this.btnEliminar.setEnabled(false);
        this.btnGuardar.setEnabled(true);
        this.btnNuevo.setEnabled(false);
        this.btnSalir.setEnabled(true);
    }//GEN-LAST:event_btbModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
       
        this.miparent.mostrarBajaBien(unBien);
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.accionCancelarBoton(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(nuevoBien){
            crearBien();
        }else{
            modificarBien();
        }
        
       // this.guardarAsignacion();
        
        this.txtNrodeInventario.setEnabled(true);

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.accionCancelarBoton(false);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ImprimirEtiqueta impresora = new ImprimirEtiqueta();
        impresora.imprimir(this.unBien.getNroInv().toString());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.imprimirCargo();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtResponsableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtResponsableMouseClicked
        // TODO add your handling code here:
        unResponsable = responsableDao.findResponsableByNombre(txtResponsable.getSelectedItem().toString());
        asignar = true;
    }//GEN-LAST:event_txtResponsableMouseClicked

    private void txtResponsableFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtResponsableFocusLost
        // TODO add your handling code here:
        unResponsable = responsableDao.findResponsableByNombre(txtResponsable.getSelectedItem().toString());
        asignar = true;
    }//GEN-LAST:event_txtResponsableFocusLost

    private void txtValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtValorKeyPressed

    private void txtValorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(c < '0'||  c > '9'){
            if(c != ',' &&  c != '.'){
                evt.consume();
            }
            
        }
    }//GEN-LAST:event_txtValorKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btbModificar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelText;
    private javax.swing.JComboBox<String> txtArea;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JFormattedTextField txtFechaAlta;
    private javax.swing.JTextField txtFechaBaja;
    private javax.swing.JTextField txtNroActa;
    private javax.swing.JTextField txtNroExpediente;
    private javax.swing.JTextField txtNrodeInventario;
    private javax.swing.JTextField txtResolucion;
    private javax.swing.JTextField txtResolucionBaja;
    private javax.swing.JComboBox<String> txtResponsable;
    private javax.swing.JComboBox<String> txtSubResponsable;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
