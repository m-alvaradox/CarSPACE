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
       if( vehiculos.eliminar(vehiculo)){
           System.out.println("Cambio Satisfactorio.");
       }else{
           System.out.println("Hubo Fallos");
       }
    }
    
    public void editarVehiculo(Vehiculos Antiguo, Vehiculos nuevo){
        if (Antiguo != null && nuevo!=null) { 
            DoublyNodeList<Vehiculos> p = vehiculos.getHeader();
            while(p!=null){
                if(p.getContent().compareTo(Antiguo)==0){
                    p.setContent(nuevo);
                    System.out.println("Cambio Satisfactorio.");
                } 
                p=p.getNext();
            }  
        } 
    }

    public DoublyLinkedList<Vehiculos> getVehiculos() {
        return vehiculos;
    }

    public DoublyLinkedList<Vehiculos> filtrarPorMarcaYModelo(String marca, String modelo) {
        Iterator<Vehiculos> iterator = this.iterator();
        DoublyLinkedList<Vehiculos> resultado = new DoublyLinkedList<>();
        
        
        
        
    Comparator<Vehiculos> comparator = new Comparator<Vehiculos>() {
        @Override
        public int compare(Vehiculos v1, Vehiculos v2) {
            
            int marcaCompare = v1.getMarca().compareToIgnoreCase(v2.getMarca());
            if (marcaCompare == 0) {
                return v1.getModelo().compareToIgnoreCase(v2.getModelo());
            }
            return marcaCompare;
        }
    };
        Queue<Vehiculos> cola = new PriorityQueue<>(comparator);
    
        while (iterator.hasNext()) {
        Vehiculos vehiculos = iterator.next();
        cola.offer(vehiculos);
        }

        while (!cola.isEmpty()) {
        Vehiculos vehiculo = iterator.next();
        Vehiculos filtro = new Vehiculos(marca, modelo, 0, 0, 0, null, null, 0, null, null, null, null, null, null);  // Crear filtro con marca y modelo
        if (comparator.compare(vehiculo, filtro) == 0) {
            resultado.addLast(cola.poll());
        }        
      }
      return resultado;  
    }
    
    
    
    public DoublyLinkedList<Vehiculos> filtrarPorRangoDePrecio(double minPrecio, double maxPrecio) {
        Iterator<Vehiculos> iterator = this.iterator();
        DoublyLinkedList<Vehiculos> resultado = new DoublyLinkedList<>();
        
        Queue<Vehiculos> cola = new PriorityQueue<>(new Comparator<>() {
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
    
    public DoublyLinkedList<Vehiculos> filtrarPorRangoDeKilometraje(int minKm, int maxKm) {
        Iterator<Vehiculos> iterator = this.iterator();
        DoublyLinkedList<Vehiculos> resultado = new DoublyLinkedList<>();
        
        Queue<Vehiculos> cola = new PriorityQueue<>(new Comparator<>() {
            @Override
            public int compare(Vehiculos v1, Vehiculos v2) {
                return Integer.compare(v1.getKilometraje(), v2.getKilometraje());
            }
        });

        while (iterator.hasNext()) {
            Vehiculos vehiculo = iterator.next();
            if (vehiculo.getKilometraje() >= minKm && vehiculo.getKilometraje() <= maxKm) {
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