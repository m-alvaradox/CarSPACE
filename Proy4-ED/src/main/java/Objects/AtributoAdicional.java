package Objects;

import java.io.Serializable;

public class AtributoAdicional implements Serializable{
    private String title;
    private String descripcion;

    public AtributoAdicional(String title, String descripcion) {
        this.title = title;
        this.descripcion = descripcion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
