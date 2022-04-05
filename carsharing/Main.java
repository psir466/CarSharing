package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {


    public static void main(String[] args) {
        // write your code here

        String nameDB = "";

        if (args.length > 0) {
            if (args[0] == "-databaseFileName") {

                nameDB = args[1];

            }
        } else {

            nameDB = "myDataBase";
        }


        DataBase.createDb(nameDB);

        DataBase.initId();

        Menu.mainMenu();

    }
}
