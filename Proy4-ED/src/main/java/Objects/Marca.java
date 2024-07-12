/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Objects;

import TDAS.*;


/**
 *
 * @author USER
 */
public enum Marca {
    ElijaMarca("Modelo"),
    Chevrolet("Spark GT", "Sail", "Captiva", "Aveo Family"),
    Toyota("Fortuner", "Yaris", "Hilux", "Rav"),
    Kia("Sonet", "Picanto", "Sportage", "Cerato"),
    Hyundai("Tucson", "Sonata", "Loniq", "Accent"),
    Volkswagen("Tiguan", "Polo", "Virtus", "Passat"),
    Mercedes_Benz("C200", "C300", "E350", "GLA 180"),
    Ford("Escape", "Edge", "Explorer", "F150"),
    Nissan("Versa", "X-Trail", "Loniq", "PathFinder"),
    Mazda("CX-3", "CX-5", "CX-9", "New BT-50"),
    Suzuki("Grand Vitara SZ", "S-Cross", "Jimny", "Grand Vitara 2.4");
    

    private final ArrayList<String> modelos ;

    Marca(String... modelos) {
        this.modelos = new ArrayList<>();
        for (String modelo : modelos) {
            this.modelos.addLast(modelo);
        }
       
    }
    public ArrayList<String> getModelos() {
        return modelos;
    }
}
