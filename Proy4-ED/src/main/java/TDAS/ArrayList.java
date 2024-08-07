package TDAS;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class ArrayList<E>  implements List<E>, Serializable, Collection<E> {
    private E[] elements = null; //arreglo de elementos genericos
    private int capacity = 100;
    private int effectiveSize;
    
    public ArrayList(){
        elements = (E[])(new Object[capacity]); // SI FUNCIONA con Casting permitido con el ArrayList
        effectiveSize = 0;
    }
    
    private boolean isFull(){
        return effectiveSize == capacity;
    }
   
    @Override
    public boolean eliminar(E e){
        if (e == null) {
            for (int index = 0; index < effectiveSize; index++) {
                if (elements[index] == null) {
                    remove(index);
                    return true;
                }
            }
        } else {
            for (int index = 0; index < effectiveSize; index++) {
                if (e.equals(elements[index])) {
                    remove(index);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean addLast(E e) {
       if (e == null) {
            return false;
        }
        if (isFull()) {
            addCapacity();
        }
        elements[effectiveSize] = e;
        effectiveSize++;
        return true;
    }


    @Override
    public int size() {
        return effectiveSize;
    }

    @Override
    public boolean isEmpty() {
        return effectiveSize == 0;
    }

    @Override
    public void clear() {
        for(int i = effectiveSize - 1; i >= 0; i--){
            remove(i);
        }
        effectiveSize = 0;
    }

    @Override
    public E remove(int index) { 
        if (index >= effectiveSize || index < 0) {
         return null;
        }   
        E element = get(index);
        for(int i=index; i<effectiveSize-1 ; i++){
            elements[i]= elements[i+1];
        }
        elements[effectiveSize - 1] = null;
        effectiveSize--;
        return element;
    }

    @Override
    public E get(int index) {
        return elements[index];
    }

    private void addCapacity() {
        E[] tmp = (E[]) new Object[capacity * 2];
        for (int i = 0; i < capacity; i++){
            tmp[i] = elements[i];
        }
        elements = tmp;
        capacity = capacity * 2;
    }
    
    @Override
    public String toString() {
        String cadena = "[";
        int contador = 0;
        for (E el : elements) {
            if (el == null) {
                return cadena+"]";
            } else {
                contador+=1;
                cadena += el; }

            if (effectiveSize > contador ) {
                cadena +=",";
            }
        }
        return cadena;  
    }
    
    @Override
    public boolean add(int index, E element) {
        if(element==null){
            return false;
        } else if (isEmpty()) {
            elements[index] = element;
            effectiveSize++;
            return true;
        } else if (isFull()) {
            addCapacity();
        }
        
        for (int i=effectiveSize; i > index; i--){
            elements[i]=elements[i-1]; //bit shifting 
            //desplazamiento de valores hacia la derecha, 
            //debe empezar de atras hacia adelante
            }
        elements[index] = element;
        effectiveSize++;
        return true; 
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Iterator<E> iterator() {
       Iterator<E> it=new Iterator<E>() {
           int cursor = 0;
           @Override
           public boolean hasNext() {
               return cursor < effectiveSize;
           }

           @Override
           public E next() {
               E e=elements[cursor];
               cursor++;
                return e;
           }
        };
        return it;   
    
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
       
}
