package carsharing;

import java.util.List;
import java.util.Optional;

public class CarDAOimpl implements CarDAO{
    @Override
    public List<Car> carListByCompany(Company company) {
        return DataBase.ListCarByComp(company);
    }

    @Override
    public Car creatCar(String name, Company company) {
        return DataBase.crtCarData(name, company);
    }

    @Override
    public Car findCarById(int id) {
        return DataBase.findCarByID(id);
    }

    @Override
    public boolean findRentedCarById(int id) {
        return DataBase.findRentedCarById(id);
    }

}
