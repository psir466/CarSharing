package carsharing;

import java.util.List;

public interface CustomerDAO {

    public List<Customer> customerList();

    public Customer creatCustomer(String name);

    public void updateCustomerRentCar(int idcar, int idcus);

    public Customer findCustomerById(int idcus);

    public void updateCustomerReturnCar(int idcus);

}
