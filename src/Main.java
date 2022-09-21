import Classes.Adres;
import Classes.OVChipkaart;
import Classes.Reiziger;
import DAO.AdresDAO;
import DAO.OVChipkaartDAO;
import DAO.ReizigerDAO;
import DAOPsql.AdresDAOPsql;
import DAOPsql.OVChipkaartDAOPsql;
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
        OVChipkaartDAOPsql ovcDAO = new OVChipkaartDAOPsql(getConnection());

        reizigerDao.setAdao(adresDao);
        reizigerDao.setOvdao(ovcDAO);
        adresDao.setRdao(reizigerDao);
        ovcDAO.setRdao(reizigerDao);



//        testReizigerDAO(reizigerDao);
        testAdresDAO(adresDao,reizigerDao);
//        testOVChipkaartDAO(ovcDAO, reizigerDao);

        closeConnection();

    }


    public static Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection
                ("jdbc:postgresql://localhost:5432/ovchip?user=postgres&password=test");
        return connection;
    }

    public static void closeConnection() throws SQLException {
        System.out.println("closing connection");
        connection.close();
    }





    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");
//
//        System.out.println("________[TEST CREATE FUNCTIES]_______");
        String gbdatum = "2000-02-22";

//         Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        gbdatum = "1990-07-22";
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

        System.out.println("[test] reiziger die gedelete gaat worden: " + rdao.findById(77) + "\n");
        rdao.delete(sietske);
        System.out.println("[test] reiziger gevonden na delete: " + rdao.findById(77) + "\n");


    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {

        System.out.println("\n---------- Test AdresDAO -------------");

        //        Test de read functionaliteit
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] ADRESDAO.findall() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();


//        Test de create functionaliteit
        System.out.println("________[TEST CREATE FUNCTIES]_______");
        String gbdatum = "2000-02-22";
        Reiziger dummy = new Reiziger(6, "D", "Best", "Dummy", java.sql.Date.valueOf(gbdatum));

        System.out.println("saved?: " + rdao.save(dummy));
        Adres adr = new Adres(6, "2000xp", "77", "Crash course", "Gouda", 6);

        System.out.println("saved?: " + adao.save(adr) + "\n");
        dummy.setAdres(adr);


//        Test de update functionaliteit
        System.out.println("________[TEST UPDATE FUNCTIES]_______");
        adr.setStraat("testy testers ");
        System.out.println("Update gelukt?: " + adao.update(adr) + "\n");

//        Test de delete functionaliteit
        System.out.println("________[TEST DELETE FUNCTIES]_______");
        System.out.println("deleted?: " + adao.delete(adr));
        System.out.println("deleted?: " + rdao.delete(dummy) + "\n");
    }

    private static void testOVChipkaartDAO(OVChipkaartDAO ovdao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test OVChipkaartDAO -------------");


//        test de findall functies
        List<OVChipkaart> ovchipkaarten = ovdao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findall() geeft de volgende ovchipkaarten:");
        for (OVChipkaart ovc : ovchipkaarten) {
            System.out.println(ovc);
        }
        System.out.println();

        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

//       create een reiziger
        String gbdatum = "1975-01-08";
        Reiziger Whiskey = new Reiziger(7, "W", "from", "Rome", java.sql.Date.valueOf(gbdatum));
        rdao.save(Whiskey);
        System.out.println("save gelukt?: " + rdao.findById(7));

        //create een ovchipkaart
        String geldigtot = "2025-01-06";
        OVChipkaart whiskeykaart = new OVChipkaart(12345, java.sql.Date.valueOf(geldigtot), 1, 7554, 7);
        ovdao.save(whiskeykaart);
        System.out.println("save gelukt?: " + ovdao.findByReiziger(Whiskey));

        //lees de ovchipkaart die bij de reiziger hoort
        System.out.println(ovdao.findByReiziger(Whiskey)+"\n");



        //update de reiziger
        whiskeykaart.setKlasse(2);
        ovdao.update(whiskeykaart);
        System.out.println(ovdao.findByReiziger(Whiskey) + "\n");

        //delete
        System.out.println("delete? "+ovdao.delete(whiskeykaart));
        System.out.println("delete? "+rdao.delete(Whiskey));
    }
}