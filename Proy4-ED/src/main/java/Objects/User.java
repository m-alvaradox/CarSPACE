package Objects;

import java.io.Serializable;

import TDAS.DoublyLinkedList;

public class User implements Serializable, Comparable<User> {

    private String name;
    private String lastname;
    private String username;
    private String date_birth;
    private Gender gender;
    private String password;
    private DoublyLinkedList<Vehiculos> misVehiculos = new DoublyLinkedList<>();
    private DoublyLinkedList<Vehiculos> FavVehiculos = new DoublyLinkedList<>();
    

    public User(String name, String lastname, String username, String date_birth, Gender gender, String password, DoublyLinkedList<Vehiculos> misVehiculos, DoublyLinkedList<Vehiculos> FavVehiculos) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.date_birth = date_birth;
        this.gender = gender;
        this.password = password;
        this.misVehiculos = misVehiculos;
        this.FavVehiculos = FavVehiculos;
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(String date_birth) {
        this.date_birth = date_birth;
    }

    


    public DoublyLinkedList<Vehiculos> getMisVehiculos() {
        return misVehiculos;
    }



    public void setMisVehiculos(DoublyLinkedList<Vehiculos> misVehiculos) {
        this.misVehiculos = misVehiculos;
    }

    
    public void addMisvehiculos(Vehiculos vehiculo){
        misVehiculos.addLast(vehiculo);

    }



    public DoublyLinkedList<Vehiculos> getFavVehiculos() {
        return FavVehiculos;
    }



    public void setFavVehiculos(DoublyLinkedList<Vehiculos> favVehiculos) {
        FavVehiculos = favVehiculos;
    }



    public void addFavMisvehiculos(Vehiculos vehiculo){
        FavVehiculos.addLast(vehiculo);

    }

    @Override
    public int compareTo(User o) {
        return this.username.compareTo(o.getUsername());
    }


    
    
}
