import java.sql.*;
import java.util.Scanner;


/**
 * Created by condor on 26/02/15.
 * FastTrackIT, 2015
 * <p/>
 * DEMO ONLY PURPOSES, IT MIGHT CONTAINS INTENTIONALLY ERRORS OR ESPECIALLY BAD PRACTICES
 *
 * make sure you refactor it and remove lots of bad practices like loading the driver multiple times or
 * repeating the same common code multiple times
 *
 * BTW, exercise 1: how we reorg this/refactor in small pieces
 */
public class DemoCRUDOperations {


    public static void main(String[] args) {
        System.out.println("AgendaTa versiunea 2.0");

        try {

            Scanner in = new Scanner(System.in);
            int option = 0;
        do {
            printMenu();
            System.out.println("Select option: ");
            option = in.nextInt();
            switch (option) {
                case 1:
                    listAgenda();
                    break;
                case 2:
                    create();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    System.out.println("Bye!");
                    break;
            }
            }while (option != 5);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void create() throws ClassNotFoundException, SQLException {

        Connection conn = getConnection();

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("INSERT INTO agendatelefonica (nume, prenume, numar_telefon ) VALUES (?,?,?)");
        Contact newContact = readFromKeyboard();

        pSt.setString(1, newContact.getNume());
        pSt.setString(2, newContact.getPrenume());
        pSt.setString(3, newContact.getNumarTelefon());

        // 5. execute a prepared statement
        pSt.executeUpdate();

        // 6. close the objects
        pSt.close();
        conn.close();
    }

    private static void listAgenda() throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();


        // 4. create a query statement
        Statement st = conn.createStatement();

        // 5. execute a query
        ResultSet rs = st.executeQuery("SELECT nume, prenume, numar_telefon from agendatelefonica");

        // 6. iterate the result set and print the values
        while (rs.next()) {
            System.out.print(rs.getString("nume").trim());
            System.out.print("---");
            System.out.print(rs.getString("prenume").trim());
            System.out.print("---");
            System.out.println(rs.getString("numar_telefon").trim());
        }

        // 7. close the objects
        rs.close();
        st.close();
        conn.close();
    }



    private static void update() throws ClassNotFoundException, SQLException {

        Connection conn = getConnection();
        System.out.print("Select the name you want to update");
        Scanner in = new Scanner(System.in);
        String updatedName = in.nextLine();

//        Statement st = conn.createStatement();
//        ResultSet rs = st.executeQuery("SELECT COUNT (*) from agendatelefonica WHERE nume = "+updatedName);
//
//        if(rs.next())

        Contact update = readFromKeyboard();


        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("UPDATE agendatelefonica SET nume=?, prenume=?, numar_telefon=? WHERE nume=?"); //so we have 3 params
        pSt.setString(1, update.getNume());
        pSt.setString(2, update.getPrenume());
        pSt.setString(3, update.getNumarTelefon());
        pSt.setString(4, updatedName);

        // 5. execute a prepared statement
        int rowsUpdated = pSt.executeUpdate();

        // 6. close the objects
        pSt.close();
        conn.close();
    }


    private static void delete() throws ClassNotFoundException, SQLException {

        Connection conn = getConnection();

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("DELETE FROM agendatelefonica WHERE nume=?");
        pSt.setString(1, "Anghel");

        // 5. execute a prepared statement
        int rowsDeleted = pSt.executeUpdate();
        System.out.println(rowsDeleted + " rows were deleted.");
        // 6. close the objects
        pSt.close();
        conn.close();
    }
    private static void printMenu() {
        System.out.println("1. List");
        System.out.println("2. Create");
        System.out.println("3. Update");
        System.out.println("4. Delete");
        System.out.println("5. Exit");

    }
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/Haiduk_Agenda";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    private static Contact readFromKeyboard(){
        Scanner in = new Scanner(System.in);
        Contact newContact = new Contact();
        System.out.print("Enter Last Name: ");
        newContact.setNume(in.nextLine());
        System.out.print("Enter First Name: ");
        newContact.setPrenume(in.nextLine());
        System.out.print("Enter Telephone Number: ");
        newContact.setNumarTelefon(in.nextLine());



        return newContact;
    }
}

