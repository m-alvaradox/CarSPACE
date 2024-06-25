package Objects;

import TDAS.ArrayList;
import TDAS.CircularDoublyList;

public class Vehiculos {
    private String marca;
    private String modelo;
    private int anio;
    private double precio;
    private int kilometraje;
    private String motor;
    private String transmision;
    private double peso;
    private String ubicacion;
    private EstadoD estado;
    private CircularDoublyList<String> fotos = new CircularDoublyList<>();
    private ArrayList<tipoHistorial> historial = new ArrayList<>();
    private ArrayList<AtributoAdicional> AtributoAdicional = new ArrayList<>();



    public Vehiculos(String marca, String modelo, int anio, double precio, int kilometraje, String motor,
            String transmision, double peso, String ubicacion,EstadoD estado,CircularDoublyList<String> fotos,
            ArrayList<tipoHistorial> historial, ArrayList<AtributoAdicional> AtributoAdicional) {
        this.marca = marca;
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

    }

    public Vehiculos(String marca1, String modelo1, int year1, double precio1, int kilometraje1, String motor1, String transmision1, int peso1, String ubicacion1, Object object, CircularDoublyList<String> listaImagenes, Object object0) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
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

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public CircularDoublyList<String> getFotos() {
        return fotos;
    }

    public void setFotos(CircularDoublyList<String> fotos) {
        this.fotos = fotos;
    }

    public ArrayList<tipoHistorial> gethistorial() {
        return historial;
    }



    @Override
    public String toString() {
        return "Vehiculo [marca=" + marca + ", modelo=" + modelo + ", anio=" + anio + ", precio=" + precio
                + ", kilometraje=" + kilometraje + ", motor=" + motor + ", transmision=" + transmision + ", peso="
                + peso + ", ubicacion=" + ubicacion + ", fotos=" + fotos + ", historial="
                + historial + "]";
    }


    public void AgegrarFoto(String s){
        fotos.addLast(s);
    }


    

    @Override
    public boolean equals(Object obj) {
      
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        
        return super.hashCode();
    }


}