package carsharing;

import java.util.List;
import java.util.Optional;

public interface CarDAO {

    public List<Car> carListByCompany(Company company);

    public Car creatCar(String name, Company company);

    public Car findCarById(int id);

    public boolean findRentedCarById(int id);

}
