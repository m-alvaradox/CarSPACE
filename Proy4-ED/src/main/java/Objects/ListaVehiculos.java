package Objects;

import TDAS.DoublyLinkedList;
import TDAS.DoublyNodeList;
import TDAS.List;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;

public class ListaVehiculos implements Serializable{
    
    private DoublyLinkedList <Vehiculos> vehiculos = new DoublyLinkedList<>();

    public ListaVehiculos() {
        this.vehiculos = new DoublyLinkedList<Vehiculos>();
    }

    public void agregarVehiculo(Vehiculos vehiculo) {
        vehiculos.addLast(vehiculo);
    }

    public void eliminarVehiculo(Vehiculos vehiculo) {
        vehiculos.eliminar(vehiculo);
    }

    public DoublyLinkedList<Vehiculos> getVehiculos() {
        return vehiculos;
    }

    public List<Vehiculos> filtrarPorMarcaYModelo(String marca, String modelo) {
        Iterator<Vehiculos> iterator = this.iterator();
        List<Vehiculos> resultado = new DoublyLinkedList<>();
        
        Queue<Vehiculos> cola = new PriorityQueue<>(new Comparator<Vehiculos>() {
            @Override
            public int compare(Vehiculos v1, Vehiculos v2) {
                return v1.getMarca().compareToIgnoreCase(v2.getMarca());
            }
        });

        while (iterator.hasNext()) {
            Vehiculos vehiculo = iterator.next();
            if (vehiculo.getMarca().equalsIgnoreCase(marca) && vehiculo.getModelo().equalsIgnoreCase(modelo)) {
                cola.offer(vehiculo);
            }
        }

        while (!cola.isEmpty()) {
            resultado.addLast(cola.poll());
        }

        return resultado;
    }
    
    public List<Vehiculos> filtrarPorRangoDePrecio(double minPrecio, double maxPrecio) {
        Iterator<Vehiculos> iterator = this.iterator();
        List<Vehiculos> resultado = new DoublyLinkedList<>();
        
        Queue<Vehiculos> cola = new PriorityQueue<>(new Comparator<Vehiculos>() {
            @Override
            public int compare(Vehiculos v1, Vehiculos v2) {
                return Double.compare(v1.getPrecio(), v2.getPrecio());
            }
        });

        while (iterator.hasNext()) {
            Vehiculos vehiculo = iterator.next();
            if (vehiculo.getPrecio() >= minPrecio && vehiculo.getPrecio() <= maxPrecio) {
                cola.offer(vehiculo);
            }
        }

        while (!cola.isEmpty()) {
            resultado.addLast(cola.poll());
        }

        return resultado;
    }
    
    public List<Vehiculos> filtrarPorRangoDeKilometraje(int minKm, int maxKm) {
        Iterator<Vehiculos> iterator = this.iterator();
        List<Vehiculos> resultado = new DoublyLinkedList<>();
        
        Queue<Vehiculos> cola = new PriorityQueue<>(new Comparator<Vehiculos>() {
            @Override
            public int compare(Vehiculos v1, Vehiculos v2) {
                return Double.compare(v1.getKilometraje(), v2.getKilometraje());
            }
        });

        while (iterator.hasNext()) {
            Vehiculos vehiculo = iterator.next();
            if (vehiculo.getPrecio() >= minKm && vehiculo.getPrecio() <= maxKm) {
                cola.offer(vehiculo);
            }
        }

        while (!cola.isEmpty()) {
            resultado.addLast(cola.poll());
        }

        return resultado;
    }


    
    private class VehiculosIterator implements Iterator<Vehiculos> {
    private DoublyNodeList<Vehiculos> cursor;

    public VehiculosIterator() {
        this.cursor = vehiculos.getHeader();
    }

    @Override
    public boolean hasNext() {
        return cursor != null;
    }

    @Override
    public Vehiculos next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Vehiculos vehiculo = cursor.getContent();
        cursor = cursor.getNext();
        return vehiculo;
    }

    
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
 
    

   private Iterator<Vehiculos> iterator() {
        return new VehiculosIterator();
    }



}