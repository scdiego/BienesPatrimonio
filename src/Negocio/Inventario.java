
package Negocio;

import Comparadores.BienComparador;
import Utilidades.FechaHora;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;
import javax.persistence.*;
import java.io.Serializable;
import Persistencia.AsignacionJpaController;
import Persistencia.BienJpaController;
import Persistencia.UsuarioJpaController;

/**
 *
 * @author Usuario
 */
@Entity
public class Inventario implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;    
    @Transient
    private Map<String,Usuario> usuarios;
    @Transient
    private Map<Integer,Bien> bienes;
    @Transient
    private Map<Integer,Responsable> responsables;
    @Transient
    private Map<Integer,Sector> sectores;
    @Transient
    private Map<Integer,Asignacion> asignacionesActivas;
    @Transient
    private Map<Integer,Asignacion> historicoAsignaciones;
    
    @Basic
    private Integer maxUsuario;
    @Basic
    private Integer maxBien;
    @Basic
    private Integer maxResponsable;
    @Basic
    private Integer maxSector;
    @Basic
    private Integer maxAsignacionActiva;
    @Basic
    private Integer maxHistoricoAsignaciones;

    public Inventario() {
        this.usuarios = new HashMap();
        this.bienes = new HashMap();
        this.responsables = new HashMap();
        this.sectores = new HashMap();
        this.asignacionesActivas = new HashMap();
        this.historicoAsignaciones = new HashMap();
        this.maxUsuario = 1;
        this.maxBien = 1;
        this.maxResponsable = 1;
        this.maxSector = 1;
        this.maxAsignacionActiva = 1;
        this.maxHistoricoAsignaciones = 1;
    }

    public Inventario(Map<String, Usuario> usuarios, Map<Integer, Bien> bienes, Map<Integer, Responsable> responsables, Map<Integer,Sector> sectores, Map<Integer, Asignacion> asignacionesActivas, Map<Integer,Asignacion> historicoAsignacinoes) {
        this.usuarios = usuarios;
        this.bienes = bienes;
        this.responsables = responsables;
        this.sectores = sectores;
        this.asignacionesActivas = asignacionesActivas;
        this.historicoAsignaciones = historicoAsignacinoes;
        this.maxUsuario = 1;
        this.maxBien = 1;
        this.maxResponsable = 1;
        this.maxSector = 1;
        this.maxAsignacionActiva = 1;
        this.maxHistoricoAsignaciones = 1;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Map<Integer, Bien> getBienes() {
        return bienes;
    }
    public Map<Integer, Bien> getBienes(int tipo) {
        Map<Integer,Bien> salida = new HashMap();
        switch(tipo){
            case 1: //todo
                salida = this.getBienes();
                break;
                
            case 2: //alta
                salida = this.bienesActivos();
                break;
            
            case 3: //baja
                 salida = this.bienesBaja();
                break;
        }
        
        
        return salida;
    }

    public void setBienes(Map<Integer, Bien> bienes) {
        this.bienes = bienes;
    }

    public Map<Integer, Responsable> getResponsables() {
        return responsables;
    }

    public void setResponsables(Map<Integer, Responsable> responsables) {
        this.responsables = responsables;
    }

    public Map<Integer,Sector> getSectores() {
        return sectores;
    }

    public void setSectores(Map<Integer,Sector> sectores) {
        this.sectores = sectores;
    }

    public Map<Integer, Asignacion> getAsignacionesActivas() {
        return asignacionesActivas;
    }

    public void setAsignacionesActivas(Map<Integer, Asignacion> asignacionesActivas) {
        this.asignacionesActivas = asignacionesActivas;
    }

    public Map<Integer,Asignacion> getHistoricoAsignacinoes() {
        return historicoAsignaciones;
    }

    public void setHistoricoAsignacinoes(Map<Integer,Asignacion> historicoAsignacinoes) {
        this.historicoAsignaciones = historicoAsignacinoes;
    }

    public Integer getMaxUsuario() {
        return maxUsuario;
    }

    public void setMaxUsuario(Integer maxUsuario) {
        this.maxUsuario = maxUsuario;
    }

    public Integer getMaxBien() {
        return maxBien;
    }

    public void setMaxBien(Integer maxBien) {
        this.maxBien = maxBien;
    }

    public Integer getMaxResponsable() {
        return maxResponsable;
    }

    public void setMaxResponsable(Integer maxResponsable) {
        this.maxResponsable = maxResponsable;
    }

    public Integer getMaxSector() {
        return maxSector;
    }

    public void setMaxSector(Integer maxSector) {
        this.maxSector = maxSector;
    }

    public Integer getMaxAsignacionActiva() {
        return maxAsignacionActiva;
    }

    public void setMaxAsignacionActiva(Integer maxAsignacionActiva) {
        this.maxAsignacionActiva = maxAsignacionActiva;
    }

    public Integer getMaxHistoricoAsignaciones() {
        return maxHistoricoAsignaciones;
    }

    public void setMaxHistoricoAsignaciones(Integer maxHistoricoAsignaciones) {
        this.maxHistoricoAsignaciones = maxHistoricoAsignaciones;
    }
    
    

    
    /**
     * METODOS
     */
    
    /**
     * Contadores
     */
    
    public void sumarAsignacionActiva(){
        this.maxAsignacionActiva = this.maxAsignacionActiva + 1;
    } 
    public void sumarUsuario(){
        this.maxUsuario = this.maxUsuario + 1;
    } 
    public void sumarBien(){
        this.maxBien = this.maxBien + 1;
    }
    public void sumarResponsable() {
        this.maxResponsable = this.maxResponsable + 1;        
    }
    public void sumarSector(){
        this.maxSector = this.maxSector + 1;                
    }
    public void sumarHistoricoAsignacion(){
        this.maxHistoricoAsignaciones = this.maxHistoricoAsignaciones + 1;
    }
            
   /**
    * Asignacion
     * @param unaAsignacion
    */
    
    public void bajaAsignacion(Asignacion unaAsignacion){
        FechaHora fecha = new FechaHora();
        unaAsignacion.setFechaHasta(fecha.fechaActual());
        this.historicoAsignaciones.put(unaAsignacion.getId() ,unaAsignacion);
        this.asignacionesActivas.remove(unaAsignacion.getId());
    } 
    
    /*public void asignarBien(Bien unBien, Responsable unResponsable,Responsable unSubResponsable,Sector unSector){
        FechaHora fecha = new FechaHora();
        Asignacion unaAsignacion = new Asignacion();
        unaAsignacion.setBien(unBien);
        unaAsignacion.setResponsable(unResponsable);
        unaAsignacion.setSubResponsable(unSubResponsable);
        unaAsignacion.setSector(unSector);
        unaAsignacion.setFechaDesde(fecha.fechaActual());        
        /**
         * si esta asignado quito la asignacion y reemplazo
         */        
    /*    if(this.asignacionesActivas.containsKey(unBien.getNroInventario())){
            this.bajaAsignacion(this.asignacionesActivas.get(unBien.getNroInventario()));            
        }else{
            this.asignacionesActivas.put(unBien.getNroInventario(), unaAsignacion);
        }        
    }*/
    
    /*public void cambiarResponsableAsignacion(Asignacion unaAsignacion, Responsable unResponsableNuevo,Responsable unSubResponsable){
        this.bajaAsignacion(unaAsignacion);
        this.asignarBien(unaAsignacion.getBien(), unResponsableNuevo,unSubResponsable, unaAsignacion.getSector());
    }*/
    
    public Integer siguienteAsignacion(){
        Integer salida =  this.maxAsignacionActiva ;
        this.sumarAsignacionActiva();
        return salida;
    }
    
    public Asignacion devolverAsignacion(Integer identificador){
        Asignacion salida = null;
        if(this.asignacionesActivas.containsKey(identificador)){
            salida = this.asignacionesActivas.get(identificador);
        }else{
            if(this.historicoAsignaciones.containsKey(identificador)){
                salida = this.historicoAsignaciones.get(identificador);
            }
        }
    
        return salida;
    }
    
    public List<Asignacion> asignacionesPorResponsable(Responsable unResponsable){
        List<Asignacion> salida = new ArrayList();
        Asignacion unaAsignacion;
         for(Map.Entry asignacion : this.asignacionesActivas.entrySet()){
            unaAsignacion = (Asignacion)asignacion.getValue();
            if(unaAsignacion.getResponsable().equals(unResponsable)){
                salida.add(unaAsignacion);
            }
         }
        
        return salida;
    }
    

    /**
     * Bien
     * @param unBien
     */
    
    public void agregarBien(Bien unBien){
        //Verifico si no existe la clave
        if(!this.bienes.containsKey(unBien.getNroInventario())){
            this.bienes.put(unBien.getNroInventario(), unBien);
        
        }
    }    
    public Bien crearBien (String codigo, Integer nroInventario, String descripcion, Date fechaAlta, String nroActa, BigDecimal valor,  String nroExpedienteAlta, String resolucionAlta) {
        Bien unBien = new Bien(codigo,nroInventario,descripcion,fechaAlta,nroActa,valor,nroExpedienteAlta,resolucionAlta);
        return unBien;
    }
    
    /*public void nuevoBien(String codigo, Integer nroInventario, String descripcion, Date fechaAlta, String nroActa, BigDecimal valor, String nroExpedienteAlta, String resolucionAlta,Responsable responsable, Responsable subResponsable,Sector area) {
        Bien unBien = this.crearBien(codigo,nroInventario,descripcion,fechaAlta,nroActa,valor,nroExpedienteAlta,resolucionAlta);
        this.agregarBien(unBien);
        this.asignarBien(unBien, subResponsable,subResponsable, area);
    }*/
    
    public void bajaBien(Integer nroInventario,String nroExpediente, String resolucion,String descripcion,Date fechaBaja,String tipo){
        if(this.bienes.containsKey(nroInventario)){
            Bien unBien = this.bienes.get(nroInventario);
            unBien.bajaBien(nroExpediente, resolucion,descripcion,fechaBaja,tipo);
            this.bienes.put(nroInventario, unBien);
        }
    }   
    public Integer UltimoNroBien(){
            Integer nro = 0;
            Bien unBien;
            
            for(Map.Entry bien : this.bienes.entrySet()){
                unBien = (Bien)bien.getValue();
                if(unBien.getNroInventario() > nro){
                    nro = unBien.getNroInventario(); 
                }
            }
        return nro;
    }
    public Integer PrimerNroBien(){
        Integer nro = 1;
        Bien unBien;
            
        for(Map.Entry bien : this.bienes.entrySet()){
            unBien = (Bien)bien.getValue();
            if(unBien.getNroInventario() < nro){
                nro = unBien.getNroInventario(); 
            }
        }
        return nro;

    }
    public Map<Integer,Bien> bienesActivos(){
        Bien auxBien;
        Map<Integer,Bien> salida =  new HashMap();
        for (Map.Entry unBien : this.getBienes().entrySet()) {
            auxBien = (Bien)unBien.getValue();
            if(!auxBien.isDebaja()) {
                salida.put(auxBien.getNroInventario(), auxBien);
            }
        }
        
        return salida;
    }
    public Map<Integer,Bien> bienesBaja(){
        Bien auxBien;
        Map<Integer,Bien> salida =  new HashMap();
        for (Map.Entry unBien : this.getBienes().entrySet()) {
            auxBien = (Bien)unBien.getValue();
            if(auxBien.isDebaja()) {
                salida.put(auxBien.getNroInventario(), auxBien);
            }
        }
        
        return salida;
    }
    /**
    public ArrayList bienesPorSector(String unSector){
        ArrayList salida = new ArrayList();
        Asignacion unaAsignacion;
        
        for (Map.Entry asignacion : this.asignacionesActivas.entrySet()) {
             unaAsignacion = (Asignacion)asignacion.getValue();
             if( unaAsignacion.getSector().getNombre().equals(unSector)){
                 salida.add(unaAsignacion.getBien());
             }
        }
        return salida;
    }
    public ArrayList bienesPorResponsable(Responsable unResponsable){
        ArrayList salida = new ArrayList();
        Asignacion unaAsignacion;
        
        for(Map.Entry asignacion : this.asignacionesActivas.entrySet()){
            unaAsignacion = (Asignacion)asignacion.getValue();
            if(unaAsignacion.getResponsable().equals(unResponsable)){
                salida.add(unaAsignacion.getBien());
            }
        }
        
        return salida;
    }    
    */ 
    
    
    public Map<Integer,Bien> bienesQueContienenSubString(String unString){
        Map<Integer,Bien> salida = new HashMap();
        Bien unBien;
        for(Map.Entry bien : this.bienes.entrySet()){
            unBien = (Bien)bien.getValue();
            if(unBien.ContieneNombre(unString)){
                salida.put(unBien.getNroInventario(), unBien);
            }
        }
        return salida;

    }
    public Bien siguienteNroInventario(Integer unNro){
        Iterator it = this.bienes.entrySet().iterator();
        ArrayList listaClaves = new ArrayList();
        Integer indice;
	while (it.hasNext()){
            Map.Entry entrada = (Map.Entry)it.next();
            listaClaves.add(entrada.getKey());
	}
		
	Collections.sort(listaClaves);
        indice = (Integer)listaClaves.get(listaClaves.indexOf(unNro) + 1);

        if(indice > this.UltimoNroBien()){
            indice = this.PrimerNroBien();
        }
        return this.bienes.get(indice);
    }
    public Bien anteriorNroInventario(Integer unNro){
        Iterator it = this.bienes.entrySet().iterator();
        ArrayList listaClaves = new ArrayList();
        Integer indice;
	while (it.hasNext()){
            Map.Entry entrada = (Map.Entry)it.next();
            listaClaves.add(entrada.getKey());
	}
		
	Collections.sort(listaClaves);
        indice = (Integer)listaClaves.get(listaClaves.indexOf(unNro) - 1);
        if(indice < 1){
            indice = this.PrimerNroBien();
        }
        return this.bienes.get(indice);        
    }
    public Bien primerNroDeInventario(){
        return this.bienes.get(this.PrimerNroBien());
    }
    public Bien ultimoNroDeInventario(){
        return this.bienes.get(this.UltimoNroBien());
    }
    public void modificarBien(Bien unBien,String codigo, Integer nroInventario, String descripcion, Date fechaAlta, String nroActa, BigDecimal valor,  String nroExpedienteAlta, String resolucionAlta){
        //Falta ver
        unBien.modificar(codigo, nroInventario, descripcion, fechaAlta, nroActa, valor, nroExpedienteAlta, resolucionAlta);
        this.bienes.put(unBien.getNroInventario(), unBien);
    }
    
    public ArrayList<Bien> porNroInventario  (Integer unNumero) throws NullPointerException{
        Bien unBien;
        ArrayList salida = new ArrayList();
        if(this.bienes.containsKey(unNumero)){
            unBien = this.bienes.get(unNumero);
        }else{
            throw new NullPointerException();
        }
        salida.add(unBien);
        Collections.sort(salida, new BienComparador());
        return salida;
    }
    public ArrayList<Bien> porDescripcion(String unaDescripcion) throws NullPointerException {
        ArrayList<Bien> salida = new ArrayList();
        Bien unBien;
        for(Map.Entry bien: this.getBienes().entrySet()){
            unBien = (Bien)bien.getValue();
            if(unBien.ContieneNombre(unaDescripcion)){
                salida.add(unBien);
            }
        }
        if(salida.size() <= 0){
          throw new NullPointerException();  
        }
        Collections.sort(salida, new BienComparador());
        return salida;
    }
    public ArrayList<Bien> porArea(String unNombreArea){
        ArrayList<Bien> salida = new ArrayList();
        Asignacion unaAsignacion;
        for(Map.Entry asignacion: this.getAsignacionesActivas().entrySet()){
            unaAsignacion = (Asignacion)asignacion.getValue();
            if(unaAsignacion.getResponsable().getSector().getNombre().equals(unNombreArea)){
                salida.add( unaAsignacion.getBien());
            }
        }
        Collections.sort(salida, new BienComparador());
        return salida;
    
    }
    
    public ArrayList<Bien>porResponsable(String unNombreResponsable){
        ArrayList<Bien> salida = new ArrayList();
        Asignacion unaAsignacion;
        for(Map.Entry asignacion: this.getAsignacionesActivas().entrySet()){
            unaAsignacion = (Asignacion)asignacion.getValue();
            if(unaAsignacion.getResponsable().getNombre().equals(unNombreResponsable)){
                salida.add(unaAsignacion.getBien());
            }
        }
        Collections.sort(salida, new BienComparador());
        return salida;
    }
    
    public ArrayList<Bien> buscar (Date desde){
        ArrayList<Bien> salida = new ArrayList();
        Bien unBien;
        for(Map.Entry bien: this.getBienes().entrySet()){
            unBien = (Bien)bien.getValue();
            if(unBien.getFechaAlta().compareTo(desde) >= 0){
                salida.add(unBien);
            }
        }
        Collections.sort(salida, new BienComparador());
        return salida;
    }
    public ArrayList<Bien>buscar(int tipo){
        ArrayList lista = new ArrayList();
        Bien unBien;
        for(Map.Entry bien: this.getBienes(tipo).entrySet()){
            unBien = (Bien)bien.getValue();
            lista.add(unBien);
        }
        Collections.sort(lista, new BienComparador());
        
        return lista;
    }
    
    public ArrayList<Bien>buscar(Integer desde,int tipo){
        ArrayList lista = new ArrayList();
        Bien unBien;
        for(Map.Entry bien: this.getBienes(tipo).entrySet()){
            unBien = (Bien)bien.getValue();
            if(unBien.getNroInventario() >= desde){
                lista.add(unBien);
            }
        }
        Collections.sort(lista, new BienComparador());
        
        return lista;
    }
    
    public ArrayList<Bien>buscar(Integer desde,Integer hasta,int tipo){
        if(hasta < desde){
            Integer aux = desde;
            desde = hasta;
            hasta = aux;
        }
                
        
        ArrayList lista = new ArrayList();
        Bien unBien;
        
        for(Map.Entry bien: this.getBienes(tipo).entrySet()){
            unBien = (Bien)bien.getValue();
            if(unBien.getNroInventario() >= desde && unBien.getNroInventario() <= hasta){
                lista.add(unBien);
            }
        }
        
        Collections.sort(lista, new BienComparador());
        return lista;
    }
    
    public boolean existeBienAsignado(Bien unBien, Responsable unResponsable){
        AsignacionJpaController asignacionDao = new AsignacionJpaController();
        return asignacionDao.existeAsignacion(unBien, unResponsable);
        
    }
    
    public Bien BienPorNroDeInventario(Integer unNro){
        Bien salida = null;
        /**
        if(this.bienes.containsKey(unNro)){
            salida = this.bienes.get(unNro);            
        }
        
        
        Asignacion asignacion = asignacionDao.findAsignacionByNroInventario(nroInventario)
        */
        BienJpaController bienDao = new BienJpaController();
        salida = bienDao.findBien(unNro);        
        return salida;
    }
    /**
    * Responsable
    * @param nroInventario
    * @return El responsable del Bien con nroInventario
    */
    public Responsable responsableBien(Integer nroInventario){
        Responsable responsable = null;
        AsignacionJpaController asignacionDao = new AsignacionJpaController();
        Asignacion asignacion = asignacionDao.findAsignacionByNroInventario(nroInventario);
        if (asignacion != null) {
            responsable = asignacion.getResponsable();
        }       
        return responsable;
    }
    
    public Responsable responsableBienID(Integer idBien){
        Responsable responsable = null;
        AsignacionJpaController asignacionDao = new AsignacionJpaController();
        Asignacion asignacion = asignacionDao.findAsignacionByIdBien(idBien);
        if (asignacion != null) {
            responsable = asignacion.getResponsable();
        }       
        return responsable;        
    }
    
    public Responsable responsableXNombre(String unNombre){
        Responsable unResponsable;   
        Responsable salida = null;
        for(Map.Entry responsable : this.getResponsables().entrySet()){
            unResponsable = (Responsable)responsable.getValue();
            if(unResponsable.getNombre().equals(unNombre)){
                salida = unResponsable;
            }
        }
        return salida;
    }  
    public Responsable responsableXDni(String unDni){
        Responsable unResponsable;
        Responsable salida = null;
        for(Map.Entry responsable : this.getResponsables().entrySet()){
            unResponsable = (Responsable)responsable.getValue();
            if(unResponsable.getDni().equals(unDni)){
                salida = unResponsable;
            }
        }
        return salida;
    }   
    public void borrarResponsable(Responsable unResponsable){
        if(this.existeResponsable(unResponsable)){
            //Pongo en baja
            unResponsable.darBaja();
            this.responsables.put(unResponsable.getId(), unResponsable);
        }else{
            //Borro el responsable
            if(this.getResponsables().containsKey(unResponsable.getId())){
                this.responsables.remove(unResponsable.getId());
            }
        }
    }
    public boolean existeResponsable(Responsable unResponsable){
        boolean salida = false;
        if(existeResponsableAsignacionActiva(unResponsable) || existeResponsableAsignacionHistorica(unResponsable) ){
            salida = true;
        }
        return salida;
    }
    public boolean existeResponsableAsignacionHistorica(Responsable unResponsable){
        boolean salida = false;
        Asignacion unaAsignacion;
        for(Map.Entry asignacion: this.getHistoricoAsignacinoes().entrySet()){
            unaAsignacion = (Asignacion)asignacion.getValue();
            if(Objects.equals(unaAsignacion.getResponsable().getId(), unResponsable.getId())){
                salida = true;
            }
                
        }
        return salida;
   
    }
    public boolean existeResponsableAsignacionActiva(Responsable unResponsable){
        Asignacion unaAsignacion;   
        boolean salida = false;
        
        for(Map.Entry asignacion : this.getAsignacionesActivas().entrySet()){
            unaAsignacion = (Asignacion)asignacion.getValue();
            if(Objects.equals(unaAsignacion.getResponsable(), unResponsable) ){
                salida = true;
            }
        }
        return salida;
    }
    
    public ArrayList<Bien> bienesAsignados(Responsable unResponsable){
        ArrayList<Bien> salida = new ArrayList();
        Asignacion unaAsignacion;
        for(Map.Entry asignacion:this.getAsignacionesActivas().entrySet()){
            unaAsignacion = (Asignacion)asignacion.getValue();
            if(unaAsignacion.getResponsable().equals(unResponsable)){
                salida.add(unaAsignacion.getBien());
            }
        }
        //Collections.sort(salida);
        return salida;
        
    }
            
            
    public void agregarResponsable(String unNombre, String unDni,String unCargo,Sector unSector){
        Responsable unResponsable;
        unResponsable = new Responsable(this.siguienteNroResponsable(),unNombre,unDni,unCargo,unSector);
        this.responsables.put(unResponsable.getId(), unResponsable);
    }  
    
    public Integer siguienteNroResponsable(){
        Integer salida;
        salida = this.maxResponsable;
        this.sumarResponsable();
        return salida;
    }
    public void modificarResponsable(Responsable unResponsable, String unNombre, String unDni,String unCargo, Sector unSector){
        unResponsable.modificarDatos(unNombre, unDni,unCargo,unSector);
        this.responsables.put(unResponsable.getId(), unResponsable);
    }
    
      /**
       * Sector
     * @param unNombreSector
       */
    public void agregarSector(String unNombreSector){
        Sector unSector = new Sector(this.siguienteNroSector(),unNombreSector);
        if(!this.existeSector(unNombreSector)){
            this.sectores.put(unSector.getId(),unSector);
        }
    }
    public boolean existeSector(String unNombreSector){
        boolean salida = false;
        Sector unSector;
        
        for(Map.Entry sector : this.getSectores().entrySet()){
            unSector = (Sector)sector.getValue();
            if(unSector.getNombre() == null ? unNombreSector == null : unSector.getNombre().equals(unNombreSector) ){
                salida = true;
            }
        }
        
        
        return salida;
    }
    public Sector obtenerSectorNombre(String unNombreSector){
        Sector unSector = null;
        boolean salida = false;
        for(Map.Entry sector : this.getSectores().entrySet()){
            unSector = (Sector)sector.getValue();
            if(unSector.getNombre() == null ? unNombreSector == null : unSector.getNombre().equals(unNombreSector) ){
                salida = true;
            }
        }
        
        return unSector;
    }
    public Integer siguienteNroSector(){
        Integer salida = this.maxSector;        
        this.sumarSector();
        return salida;
    }
    /*public void quitarSector(Sector unSector){
        if(this.existeSectorEnAsignacion(unSector)){
            //solo desactivo
            unSector.darBaja();
            this.sectores.put(unSector.getId(), unSector);
        }else{
            this.sectores.remove(unSector.getId());           
        }
    }
    
    public boolean existeSectorEnAsignacion(Sector unSector){
        boolean salida = false;
        if(existeSectorAsignacionActiva(unSector) || existeSectorAsignacionHistorica(unSector)){
            salida = true;
        }
        return salida;
    }
    
    public boolean existeSectorAsignacionActiva(Sector unSector){
         Asignacion unaAsignacion;   
        boolean salida = false;
        
        for(Map.Entry asignacion : this.getAsignacionesActivas().entrySet()){
            unaAsignacion = (Asignacion)asignacion.getValue();
            if(Objects.equals(unaAsignacion.getSector().getId(), unSector.getId()) ){
                salida = true;
            }
        }
        return salida;
    }
    
    public boolean existeSectorAsignacionHistorica(Sector unSector){
        boolean salida = false;
        Asignacion unaAsignacion;   

        for(Map.Entry asignacion : this.getHistoricoAsignacinoes().entrySet()){
            unaAsignacion = (Asignacion)asignacion.getValue();
            if(Objects.equals(unaAsignacion.getSector().getId(), unSector.getId())){
                salida = true;
            }
            
        }
        return salida;
    }*/
    
    public void modificarSector(Sector unSector,String unNombre){
        unSector.setNombre(unNombre);
        this.sectores.put(unSector.getId(), unSector);
    }
    
    public Map<Integer,Sector>getSectoresActivos(){
        Sector unSector;
        Map<Integer,Sector>salida = new HashMap();
        for(Map.Entry sector : this.getSectores().entrySet()){
            unSector = (Sector)sector.getValue();
            if(!unSector.isBaja()){
                salida.put(unSector.getId(), unSector);
            }
        }
        return salida;
    }
       /**
        * Usuario
     * @param nombre
     * @param username
        */
    
    public Usuario nuevoUsuario(String nombre, String username, String pass, String rol){
        Usuario unUsuario = new Usuario(nombre,username,pass,rol);
        return unUsuario;
    }
    public void agregarUsuario(Usuario unUsuario){
        if(!this.existeUsuario(unUsuario.getUsername())){
            this.usuarios.put(unUsuario.getUsername(), unUsuario);
        }
    }
    public boolean validarUsuario(String userName,String password){
        boolean salida=false;
        if(this.existeUsuario(userName)){
           salida   = this.obtenerUsuario(userName).validar(userName, password);
        }
        return salida;
    }
    public boolean existeUsuario(String username){
      
      boolean salida = false;
      try{
          Usuario user = this.obtenerUsuario(username);
          salida = true;
      }catch(NoResultException e){
        salida = false;
     }
      
      return salida;
    }
    
    public Usuario obtenerUsuario(String unUserName) throws NoResultException{
        Usuario user;// = new Usuario();
        UsuarioJpaController userDao = new UsuarioJpaController();
        user = userDao.findUsuarioByUserName(unUserName);
        return user;
    }
    
    public void bajaUsuario(String unUserName){
        Usuario unUsuario = this.usuarios.get(unUserName) ;
        unUsuario.darBaja();
        this.usuarios.put(unUserName, unUsuario);
    }
    public void actualizarUsuario(Usuario unUsuario){
        this.usuarios.put(unUsuario.getUsername(), unUsuario);
    }
    public void modificarUsuario(Usuario unUsuario,String unNombre,String unUserName,String unPass,String unRol){
        if(!unUsuario.getUsername().equals(unUserName)){
            this.usuarios.remove(unUsuario.getUsername());
        }
        unUsuario.modificar(unNombre, unUserName, unPass, unRol);
        this.usuarios.put(unUsuario.getUsername(), unUsuario);
        
    }
    public Map<String,Usuario> getUsuariosActivos(){

        Map <String,Usuario> salida = new HashMap();
        Usuario unUsuario;
        for(Map.Entry usuario: this.getUsuarios().entrySet()){
            unUsuario = (Usuario)usuario.getValue();
            if(!unUsuario.isBaja()){
                salida.put(unUsuario.getUsername(), unUsuario);
            }
        }
        return salida;
    }
    public Integer siguienteIdUsuario(){
        Integer salida = this.maxUsuario;
        this.sumarUsuario();
        return salida;
    }
    public String obtenerResponsableBien(Integer id){
        String salida = "-";
        if(this.asignacionesActivas.containsKey(id)){
            salida = this.asignacionesActivas.get(id).getResponsable().getNombre();
        }
        
        return salida;
    }
    /*public String obtenerAreaBien(Integer id){
        String salida = "-";
        if(this.asignacionesActivas.containsKey(id)){
            salida = this.asignacionesActivas.get(id).getSector().getNombre();
        }
        return salida;
    }*/
    
    /*public String obtenerFechaAsignacion(Integer id){
        String salida = "-";
        FechaHora fecha = new FechaHora();
        if(this.asignacionesActivas.containsKey(id)){
            salida = fecha.DateToString(this.asignacionesActivas.get(id).getFechaDesde()) ;;
        }
        return salida;
    }*/
   
    public Integer getId() {
        return id;
    }
    
   
    
    
} 
