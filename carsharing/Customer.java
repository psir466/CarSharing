package carsharing;

public class Customer {

    private int id;
    private String name;
    private Car car;

    public Customer(){

    }

    public Customer(String name, Car car) {
        this.name = name;
        this.car = car;
    }

    public Customer(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
