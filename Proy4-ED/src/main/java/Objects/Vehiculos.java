package Objects;

import TDAS.ArrayList;
import TDAS.CircularDoublyList;
import java.io.Serializable;
import java.util.Objects;

public class Vehiculos implements Serializable {
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
    private ArrayList<Historial> historial = new ArrayList<>();
    private ArrayList<AtributoAdicional> AtributoAdicional = new ArrayList<>();
    private User vendedor;



    public Vehiculos(String marca, String modelo, int anio, double precio, int kilometraje, String motor,
            String transmision, double peso, String ubicacion,EstadoD estado,CircularDoublyList<String> fotos,
            ArrayList<Historial> historial, ArrayList<AtributoAdicional> AtributoAdicional, User vendedor) {
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
                + peso + "\nUbicacion : " + ubicacion+"\nVendedor : " + vendedor.getName() + " "+vendedor.getLastname();
    }

    @Override
    public boolean equals(Object obj) {
      
        // Verifica si el objeto es el mismo
        if (this == obj) {
            return true;
        }
        // Verifica si el objeto pasado es null o si son de diferentes clases
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Compara los atributos
        Vehiculos vehiculo = (Vehiculos) obj;
        return anio == vehiculo.anio &&
                Objects.equals(marca, vehiculo.marca) &&
                Objects.equals(modelo, vehiculo.modelo) &&
                Objects.equals(anio, vehiculo.anio) &&        
                Objects.equals(precio, vehiculo.precio) &&
                Objects.equals(kilometraje, vehiculo.kilometraje) &&
                Objects.equals(motor, vehiculo.motor) &&
                Objects.equals(transmision, vehiculo.transmision) && 
                Objects.equals(peso, vehiculo.peso) &&
                Objects.equals(ubicacion, vehiculo.ubicacion) &&
                Objects.equals(estado, vehiculo.estado);

                //Objects.equals(vendedor, vehiculo.vendedor);
    }

    @Override
    public int hashCode() {
        
        return super.hashCode();
    }


}