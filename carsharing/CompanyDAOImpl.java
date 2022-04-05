package carsharing;

import java.util.List;

public class CompanyDAOImpl implements CompanyDAO{


    @Override
    public List<Company> companyList() {
        return DataBase.ListComp();
    }

    @Override
    public Company createCompany(String name) {
        return DataBase.crtCompData(name);
    }

    @Override
    public Company findCompanyByID(int id) {
        return DataBase.findCompanybyId(id);
    }


}
