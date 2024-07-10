
package Objects;

import java.io.Serializable;

public class Container<T extends Serializable> {
    private T value;

    public Container(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
    
    public void displayValue() {
        System.out.println("Value: " + value);
    }
    
    
    
    
    
    
}
