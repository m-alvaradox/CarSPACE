package TDAS;

public interface List<E> {

    public int size();

    public boolean isEmpty();

    public void clear();


    public boolean addLast(E element); // inserta el elemento element al final

    public E remove(int index); // remueve y retorna el elemento en la posición index

    public E get(int index); // retorna el elemento ubicado en la posición index


    public boolean add(int index, E element);

    public String toString();
    
    public boolean remove(E e);

}
