/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class Historial implements Serializable {
    private tipoHistorial tipo;
    private String descripcion;
    private String fecha;

    public Historial(tipoHistorial tipo, String descripcion, String fecha) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha= fecha;
    }

    public tipoHistorial getTipo() {
        return tipo;
    }

    public void setTipo(tipoHistorial tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}
