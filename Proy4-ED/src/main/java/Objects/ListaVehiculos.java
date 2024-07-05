package Objects;
import TDAS.CircularDoublyList;
import TDAS.DoublyNodeList;
import TDAS.DoublyLinkedList;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Collections;

public class ListaVehiculos implements Comparator, Serializable{
    
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

    public DoublyLinkedList<Vehiculos> ordenarPorPrecio() {
            

        return vehiculos;
    }

    public DoublyLinkedList<Vehiculos> ordenarPorKilometraje(Comparator cmp) {
        return vehiculos;
    }

    public DoublyLinkedList<Vehiculos> filtrarPorCriterios(Comparator cmp) {
        return vehiculos;
    }

    @Override
    public int compare(Object o1, Object o2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }




}