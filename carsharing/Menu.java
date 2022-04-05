package carsharing;

import java.nio.charset.CharacterCodingException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {

    static Scanner sc;
    static CompanyDAO cdao;
    static CarDAO cardao;
    static CustomerDAO cusdao;

    public static void mainMenu() {

        cdao = new CompanyDAOImpl();

        cardao = new CarDAOimpl();

        cusdao = new CustomerDAOImpl();

        sc = new Scanner(System.in);
        boolean fin = false;


        while (!fin) {

            System.out.println("  ");

            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");

            int action = sc.nextInt();

            switch (action) {

                case 0:
                    sc.close();
                    fin = true;
                    System.out.println("Bye!");
                    break;

                case 1:
                    managerMenu();
                    break;

                case 2:
                    displayListCust();
                    break;

                case 3:
                    crtCustomer();
                    break;
            }

        }

    }


    public static void managerMenu() {

        boolean fin = false;

        while (!fin) {

            System.out.println("  ");

            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");


            int action = sc.nextInt();

            switch (action) {

                case 0:
                    fin = true;
                    break;

                case 1:
                    displayListComp();
                    break;

                case 2:
                    crtComp();
                    break;


            }


        }

    }

    private static void displayListComp() {


        List<Company> listcomp = cdao.companyList();
        // trié par id

        if (!listcomp.isEmpty()) {

            System.out.println("Choose the company:");

            List<Company> sortCompId = listcomp.stream().sorted(Comparator.comparingInt(Company::getId)).collect(Collectors.toList());

            for (Company c : sortCompId) {

                System.out.println(c.getId() + ". " + c.getName());

            }

            System.out.println("0. Back");


            int id_company = sc.nextInt();

            if(id_company != 0) {

                if (listcomp.stream().filter(c -> c.getId() == id_company).findAny().isPresent()) {

                    dispCompany(listcomp.stream().filter(c -> c.getId() == id_company).findAny().get());

                } else {

                }
            }

        } else {

            System.out.println("The company list is empty!");
        }



    }


    private static Company crtComp() {


        System.out.println("Enter the company name:");

        // pour vider le scanner !!!!!!!!!!!!!!!!!!!!!!! car avant sc.nextInt et du coup sinon marche pas
        sc.nextLine();

        String name = sc.nextLine();

        return cdao.createCompany(name);

    }

    private static void dispCompany(Company company) {

        System.out.println("  ");

        System.out.println("'" + company.getName() + "' company");

        boolean fin = false;

        while (!fin) {

            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");

            int action = sc.nextInt();

            switch (action) {

                case 0:
                    fin = true;
                    break;

                case 1:
                    displayListCarByCompany(company);
                    break;

                case 2:
                    crtcar(company);
                    break;

            }

        }
    }

    static void displayListCarByCompany(Company company){

       List<Car> listcar = cardao.carListByCompany(company);

        if (!listcar.isEmpty()) {

            System.out.println("Car list:");

            List<Car> sortCarId = listcar.stream().sorted(Comparator.comparingInt(Car::getId)).collect(Collectors.toList());

            int idlist = 0;

            for (Car c : sortCarId) {

                idlist = idlist + 1;

                System.out.println(idlist + ". " + c.getName());

            }

            System.out.println("  ");

        }else{

            System.out.println("The car list is empty!");

        }
    }

    static Car crtcar(Company company){

        System.out.println("Enter the car name:");

        // pour vider le scanner !!!!!!!!!!!!!!!!!!!!!!! car avant sc.nextInt et du coup sinon marche pas
        sc.nextLine();

        String name = sc.nextLine();

        return cardao.creatCar(name, company);
    }


    static Customer crtCustomer(){

        System.out.println("Enter the customer name:");

        // pour vider le scanner !!!!!!!!!!!!!!!!!!!!!!! car avant sc.nextInt et du coup sinon marche pas
        sc.nextLine();

        String name = sc.nextLine();

        return cusdao.creatCustomer(name);
    }

    private static void displayListCust() {


        List<Customer> listcust = cusdao.customerList();
        // trié par id

        if (!listcust.isEmpty()) {

            System.out.println("Customer list:");

            List<Customer> sortCustId = listcust.stream().sorted(Comparator.comparingInt(Customer::getId)).collect(Collectors.toList());

            for (Customer c : sortCustId) {

                System.out.println(c.getId() + ". " + c.getName());

            }

            System.out.println("0. Back");


            int id_cust = sc.nextInt();

            if(id_cust != 0) {

                if (listcust.stream().filter(c -> c.getId() == id_cust).findAny().isPresent()) {

                    managtRentCarByCustomer(listcust.stream().filter(c -> c.getId() == id_cust).findAny().get());

                } else {

                }
            }

        } else {

            System.out.println("The customer list is empty!");
        }
    }

    private static void managtRentCarByCustomer(Customer customer){


        boolean fin = false;

        while (!fin) {

            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");

            int action = sc.nextInt();

            switch (action) {

                case 0:
                    fin = true;
                    break;

                case 1:
                    rentACar(customer);
                    break;

                case 2:
                    returnACAr(customer);
                    break;

                case 3:
                    rentedCarbyCustomer(customer);
                    break;

            }

        }

    }

    private static void rentACar(Customer customer){

        Customer customer2 = cusdao.findCustomerById(customer.getId());

        if(customer2.getCar() == null) {

            List<Company> listcomp = cdao.companyList();
            // trié par id

            if (!listcomp.isEmpty()) {

                System.out.println("Choose the company:");

                List<Company> sortCompId = listcomp.stream().sorted(Comparator.comparingInt(Company::getId)).collect(Collectors.toList());

                for (Company c : sortCompId) {

                    System.out.println(c.getId() + ". " + c.getName());

                }

                System.out.println("0. Back");


                int id_company = sc.nextInt();

                if (id_company != 0) {

                    if (listcomp.stream().filter(c -> c.getId() == id_company).findAny().isPresent()) {

                        int idCar = chooseCarByCompany(listcomp.stream().filter(c -> c.getId() == id_company).findAny().get());

                        cusdao.updateCustomerRentCar(idCar, customer.getId());

                    } else {

                    }
                }

            } else {

                System.out.println("The company list is empty!");
            }

        }else{

            System.out.println("You've already rented a car!");
        }

    }

    static int chooseCarByCompany(Company company){

        int idCar = 0;

        List<Car> listcar = cardao.carListByCompany(company);

        if (!listcar.isEmpty()) {

            System.out.println("Car list:");

            HashMap<Integer, Integer> hm = new HashMap<>();

            List<Car> sortCarId = listcar.stream().
                    sorted(Comparator.comparingInt(Car::getId)).
                    filter(c -> !cardao.findRentedCarById(c.getId())).
                    collect(Collectors.toList());

            int idlist = 0;

            for (Car c : sortCarId) {

                idlist = idlist + 1;

                System.out.println(idlist + ". " + c.getName());

                // pour avoir la correspondant Id list et Id fichier
                hm.put(idlist, c.getId());

            }

            System.out.println("0. Back");


            int id_car = sc.nextInt();

            if(id_car != 0) {

                if (listcar.stream().filter(c -> c.getId() == hm.get(id_car)).findAny().isPresent()) {

                    idCar = hm.get(id_car);

                } else {

                }
            }

            System.out.println("  ");

        }else{

            System.out.println("The car list is empty!");

        }

        return idCar;
    }

    private static void returnACAr(Customer customer){

        cusdao.updateCustomerReturnCar(customer.getId());

    }

    private static void rentedCarbyCustomer(Customer customer){

        Customer customer2 = cusdao.findCustomerById(customer.getId());

        if(customer2.getCar() != null) {
            System.out.println("You rented car:");
            System.out.println(customer2.getCar().getName());

            System.out.println("Company:");
            System.out.println(customer2.getCar().getCompany().getName());


        }else{

            System.out.println("You didn't rent a car!");
        }
    }


}
