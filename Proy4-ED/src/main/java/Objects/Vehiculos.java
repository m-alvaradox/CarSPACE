package Objects;

import TDAS.ArrayList;
import TDAS.CircularDoublyList;
import java.io.Serializable;

public class Vehiculos implements Serializable, Comparable<Vehiculos>{
    private String marca;
    private String modelo;
    private int anio;
    private int precio;
    private int kilometraje;
    private String motor;
    private String transmision;
    private double peso;
    private String ubicacion;
    private SubTipo subTipo;
    private EstadoD estado;
    private CircularDoublyList<String> fotos = new CircularDoublyList<>();
    private ArrayList<Historial> historial = new ArrayList<>();
    private ArrayList<AtributoAdicional> AtributoAdicional = new ArrayList<>();
    private User vendedor;



    public Vehiculos(String marca, SubTipo subTipo, String modelo, int anio, int precio, int kilometraje, String motor,
            String transmision, double peso, String ubicacion,EstadoD estado,CircularDoublyList<String> fotos,
            ArrayList<Historial> historial, ArrayList<AtributoAdicional> AtributoAdicional, User vendedor) {
        this.marca = marca;
        this.subTipo = subTipo;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.kilometraje = kilometraje;
        this.motor = motor;
        this.transmision = transmision;
        this.peso = peso;
        this.ubicacion = ubicacion;
        this.fotos = fotos;
        this.historial = historial;
        this.estado = estado;
        this.AtributoAdicional = AtributoAdicional;
        this.vendedor = vendedor;
    }

    public User getVendedor() {
        return vendedor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getMotor() {
        return motor;
    }

    public SubTipo getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(SubTipo subTipo) {
        this.subTipo = subTipo;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public EstadoD getEstado() {
        return estado;
    }

    public void setEstado(EstadoD estado) {
        this.estado = estado;
    }

    public ArrayList<Historial> getHistorial() {
        return historial;
    }

    public void setHistorial(ArrayList<Historial> historial) {
        this.historial = historial;
    }
    

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public CircularDoublyList<String> getFotos() {
        return fotos;
    }

    public void setFotos(CircularDoublyList<String> fotos) {
        this.fotos = fotos;
    }

    public ArrayList<Historial> gethistorial() {
        return historial;
    }

    public ArrayList<AtributoAdicional> getAtributoAdicional() {
        return AtributoAdicional;
    }

    public void setAtributoAdicional(ArrayList<AtributoAdicional> AtributoAdicional) {
        this.AtributoAdicional = AtributoAdicional;
    }

    

    @Override
    public String toString() {
        return "Marca : " + marca + "\nModelo : " + modelo + "\nAño : " + anio + "\nPrecio : " + precio
                + "\nKilometraje : " + kilometraje + "\nMotor : " + motor + "\nTransmision : " + transmision + "\nPeso : "
                + peso + "\nUbicacion : " + ubicacion+ "\nEstado : " + estado+"\nVendedor : " + vendedor.getName() + " "+vendedor.getLastname();
    }

    @Override
    public int hashCode() {
        
        return super.hashCode();
    }

    @Override
    public int compareTo(Vehiculos o) {
        int comparison;

        comparison = this.marca.compareTo(o.marca);
        if (comparison != 0) return comparison;

        comparison = this.modelo.compareTo(o.modelo);
        if (comparison != 0) return comparison;

        comparison = Integer.compare(this.anio, o.anio);
        if (comparison != 0) return comparison;
        
        comparison = Integer.compare(this.precio, o.precio);
        if (comparison != 0) return comparison;

        comparison = Integer.compare(this.kilometraje, o.kilometraje);
        if (comparison != 0) return comparison;

        comparison = this.motor.compareTo(o.motor);
        if (comparison != 0) return comparison;

        comparison = this.transmision.compareTo(o.transmision);
        if (comparison != 0) return comparison;

        comparison = Double.compare(this.peso, o.peso);
        if (comparison != 0) return comparison;

        comparison = this.ubicacion.compareTo(o.ubicacion);
        if (comparison != 0) return comparison;

        comparison = this.estado.compareTo(o.estado);
        if (comparison != 0) return comparison;
 
        comparison = this.vendedor.compareTo(o.vendedor);
        if (comparison != 0) return comparison;
        
        comparison = Integer.compare(this.historial.size(), o.historial.size());
        if (comparison != 0) return comparison;

        comparison = Integer.compare(this.AtributoAdicional.size(), o.AtributoAdicional.size());
        if (comparison != 0) return comparison;

        return 0;
    }
}