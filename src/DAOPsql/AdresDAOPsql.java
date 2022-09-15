package DAOPsql;

import Classes.Adres;
import Classes.Reiziger;
import DAO.AdresDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection connection;

    public AdresDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Adres adres) {
        try{
            String query = "insert into Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id ) " +
                    "values(?,?,?,?,?,?)";
            PreparedStatement prepstate = connection.prepareStatement(query);
            prepstate.setInt(1, adres.getAdres_id());
            prepstate.setString(2, adres.getPostcode());
            prepstate.setString(3, adres.getHuisnummer());
            prepstate.setString(4, adres.getStraat());
            prepstate.setString(5, adres.getWoonplaats());
            prepstate.setInt(6, adres.getReiziger_id());
            prepstate.executeUpdate();

            prepstate.close();

            return true;
        }catch (SQLException e){
            System.out.println("Fout in sql 'save' adres");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try{
            String query = "update adres set postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?" +
                    "where adres_id = ?";
            PreparedStatement prepstate = connection.prepareStatement(query);
            prepstate.setString(1, adres.getPostcode());
            prepstate.setString(2, adres.getHuisnummer());
            prepstate.setString(3, adres.getStraat());
            prepstate.setString(4, adres.getWoonplaats());
            prepstate.setInt(5, adres.getAdres_id());
            prepstate.executeUpdate();
            prepstate.close();

            return true;

        }catch (SQLException e){
            System.out.println("fout in sql code 'update' adres");
            System.out.println(e.getMessage());
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try{
            String query = "delete from adres where adres_id = ? ";
            PreparedStatement prepstate = connection.prepareStatement(query);
            prepstate.setInt(1, adres.getAdres_id());
            prepstate.executeUpdate();
            prepstate.close();

            return true;

        }catch (SQLException e){
            System.out.println("fout in sql code 'delete' adres");
            System.out.println(e.getMessage());
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            String query = "select * from adres where reiziger_id = ?";
            PreparedStatement prepstate = connection.prepareStatement(query);
            prepstate.setInt(1, reiziger.getReiziger_id());
            ResultSet resultset = prepstate.executeQuery();

            Adres reizigerAdres = null;
            while(resultset.next()){
                reizigerAdres = new Adres(
                        resultset.getInt(1),
                        resultset.getString(2),
                        resultset.getString(3),
                        resultset.getString(4),
                        resultset.getString(5),
                        resultset.getInt(6));

            }
            resultset.close();
            prepstate.close();

            return reizigerAdres;

        }catch (SQLException e){
            System.out.println("fout in sql code 'findByReiziger' adres");
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public List<Adres> findAll() {
        List<Adres> adressen = new ArrayList<>();
        try{
            String query = "select * from adres";
            PreparedStatement prepstate = connection.prepareStatement(query);
            ResultSet resultset = prepstate.executeQuery();
            while(resultset.next()){
                adressen.add(new Adres(resultset.getInt(1),
                        resultset.getString(2),
                        resultset.getString(3),
                        resultset.getString(4),
                        resultset.getString(5),
                        resultset.getInt(6)));
            }
            resultset.close();
            prepstate.close();
            return adressen;

        }catch (SQLException e){
            System.out.println("fout in sql code 'findall' adres");
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}