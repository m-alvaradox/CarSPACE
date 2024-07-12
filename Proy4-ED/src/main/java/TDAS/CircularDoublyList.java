/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDAS;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class CircularDoublyList<E> implements List<E>, Serializable {
     private DoublyNodeList<E> last;

    public CircularDoublyList() {
        this.last = null;
    }

    public DoublyNodeList<E> getLast() {
        return last;
    }
    
    public DoublyNodeList<E> getHeader(){
        return last.getNext();
    }
    public void setLast(DoublyNodeList<E> last) {
        this.last = last;
    }

    public DoublyNodeList<E> getNext() {
        return getHeader();
    }

    public DoublyNodeList getProvius() {
        if(last.getNext().getPrevious()==null){
            return last;
        }
        return last.getPrevious();
        
    }
    
    public String toString(){
        String s="";
        for (DoublyNodeList<E> n=getHeader(); n!=last; n=n.getNext()) {
            s+=n.getContent()+", ";
        }
        s+=last.getContent();
        return s;
    }
    
    public boolean removeNode(DoublyNodeList<E> nodo){
        if(nodo!=null){   
            if(nodo==last && nodo==getHeader()){
                last=null;
            } else {
                DoublyNodeList<E> anterior= nodo.getPrevious();
                DoublyNodeList<E> siguiente = nodo.getNext();
                anterior.setNext(siguiente);
                siguiente.setPrevious(anterior);
                if(last==nodo){
                    last=anterior;
                    last.setNext(siguiente);
                }else if(getHeader()==nodo){
                    last.setNext(siguiente);
                    siguiente.setPrevious(last);
                }
            }        
           return true;
        }else{
            return false;
        }
    }
    

    @Override
    public boolean addLast(E e) {
        if (e!=null){
            DoublyNodeList<E> newNode = new DoublyNodeList<>(e);
            if (last==null){
                last = newNode; 
                last.setPrevious(last);
                last.setNext(last);
            } else {
                DoublyNodeList<E> header = getHeader();
                last.setNext(newNode);
                newNode.setPrevious(last);
                newNode.setNext(header);
                header.setPrevious(newNode);
                last = newNode;
            }
            return true;
        } else {
            return false;
        }
    }

    public E removeLast() {
       E e = last.getContent();
       DoublyNodeList<E> anterior = last.getPrevious();
       anterior.setNext(getHeader());
       DoublyNodeList<E> header = getHeader();
       header.setPrevious(anterior);
       last = anterior;
       return e;
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isEmpty() {
        return last==null;
    }

    @Override
    public void clear() {
        if (last == null) {
            return; // La lista ya está vacía
        }

        DoublyNodeList<E> current = last.getNext(); // Iniciamos desde el head
        while (current != last) {
            DoublyNodeList<E> nextNode = current.getNext();
            current.setPrevious(null);
            current.setNext(null);
            current = nextNode;
        }
        // Limpiar el último nodo
        last.setPrevious(null);
        last.setNext(null);

        last = null;
    }

    @Override
    public boolean add(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E get(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(E e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
