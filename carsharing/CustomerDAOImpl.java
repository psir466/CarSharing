package carsharing;

import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {


    @Override
    public List<Customer> customerList() {
        return DataBase.ListCust();
    }

    @Override
    public Customer creatCustomer(String name) {
        return DataBase.crtCusData(name);
    }

    @Override
    public void updateCustomerRentCar(int idcar, int idcus) {
        DataBase.updateCustomerRentCar(idcar, idcus);
    }

    @Override
    public Customer findCustomerById(int idcus) {
        return DataBase.findCustomerById(idcus);
    }

    @Override
    public void updateCustomerReturnCar(int idcus) {
        DataBase.updateCustomerReturnCar(idcus);
    }
}
