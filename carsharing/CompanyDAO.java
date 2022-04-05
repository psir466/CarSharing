package carsharing;

import java.util.List;

public interface CompanyDAO {

    public List<Company> companyList();

    public Company createCompany(String name);

    public Company findCompanyByID(int id);

}
