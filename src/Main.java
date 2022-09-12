import Classes.Adres;
import Classes.Reiziger;
import DAO.AdresDAO;
import DAO.ReizigerDAO;
import DAOPsql.AdresDAOPsql;
import DAOPsql.ReizigerDAOPsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    private static Connection connection;


    public static void main(String[] args) throws SQLException {
        ReizigerDAOPsql reizigerDao = new ReizigerDAOPsql(getConnection());
        AdresDAOPsql adresDao = new AdresDAOPsql(getConnection());

        testReizigerDAO(reizigerDao);
//        testAdresDAO(adresDao,reizigerDao);

        closeConnection();

    }




    public static Connection getConnection() throws SQLException {
         connection = DriverManager.getConnection
                ("jdbc:postgresql://localhost:5432/ovchip?user=postgres&password=test");
         System.out.println("connecting to database");
         return connection;
    }

    public static void closeConnection() throws SQLException{
        System.out.println("closing connection");
        connection.close();
    }




    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

//         Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // voeg tussenvoegsel toe aan sietske
         sietske.setTussenvoegsel("test");
         rdao.update(sietske);
        System.out.println("[test] " + rdao.findById(77) + "\n");

         //delete sietske uit de database
        System.out.println("[test] reiziger die gedelete gaat worden: " + rdao.findById(77) +"\n");
        rdao.delete(sietske);
        System.out.println("[test] reiziger gevonden na delete: " +rdao.findById(77) + "\n");

        // extra read test


    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {

        System.out.println("\n---------- Test AdresDAO -------------");

//        Test de create functionaliteit
        System.out.println("________[TEST CREATE FUNCTIES]_______");
        String gbdatum = "2000-02-22";
        Reiziger dummy = new Reiziger(6, "D", "Best", "Dummy", java.sql.Date.valueOf(gbdatum));
        System.out.println("saved?: "+ rdao.save(dummy));
        Adres adr = new Adres(6, "2000xp", "77", "Crash course", "Gouda", 6);
        System.out.println("saved?: "+ adao.save(adr)+"\n");
        dummy.setReizigerAdres(adr);


//        Test de read functionaliteit
        System.out.println("________[TEST READ FUNCTIES]_______");
        List<Adres> adressen = adao.findAll();
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println("\n"+ adao.findByReiziger(dummy)+"\n");

//        Test de update functionaliteit
        System.out.println("________[TEST UPDATE FUNCTIES]_______");
        adr.setStraat("testy testers ");
        System.out.println("Update gelukt?: "+adao.update(adr)+"\n");

//        Test de delete functionaliteit
        System.out.println("________[TEST DELETE FUNCTIES]_______");
        System.out.println("deleted?: "+ adao.delete( adr));
        System.out.println("deleted?: "+ rdao.delete(dummy)+"\n");
    }
}