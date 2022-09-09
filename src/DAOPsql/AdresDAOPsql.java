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
        try {
            String sql = "update adres set postcode =?, huisnummer=?, straat=?, woonplaats=?, reiziger_id=? " +
                    "where adres_id = ?";

            PreparedStatement prepstate = connection.prepareStatement(sql);
            prepstate.setString(1, adres.getPostcode());
            prepstate.setString(2, adres.getHuisnummer());
            prepstate.setString(3, adres.getStraat());
            prepstate.setString(4, adres.getWoonplaats());
            prepstate.setInt(5,adres.getReiziger_id());
            prepstate.setInt(6,adres.getAdres_id());

            prepstate.executeUpdate();

            prepstate.close();

        }catch (SQLException e){
            System.out.println("fout in sql code 'update' adres");
            System.out.println(e.getMessage());
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }


        return true;
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            String sql = "delete from adres where adres_id =? ";
            PreparedStatement prepstate = connection.prepareStatement(sql);
            prepstate.setInt(1,adres.getAdres_id());
            prepstate.executeUpdate();

            prepstate.close();

        }catch (SQLException e){
            System.out.println("fout in sql code 'delete' adres");
            System.out.println(e.getMessage());
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }


        return true;
    }




    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            String sql = "select * from adres where reiziger_id =?";
            PreparedStatement prepstate = connection.prepareStatement(sql);
            prepstate.setInt(1, reiziger.getId());

            ResultSet resultSet = prepstate.executeQuery();

            Adres adres = null;

            while (resultSet.next()) {
                adres = new Adres(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6));
            }

            prepstate.close();
            resultSet.close();

            return adres;

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
        List <Adres> adressen = new ArrayList<>();

        try {
            String sql = "select * from adres";
            PreparedStatement prepstate = connection.prepareStatement(sql);

            ResultSet resultSet = prepstate.executeQuery();

            while(resultSet.next()){
                Adres adres = new Adres(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6));

                        adressen.add(adres);
            }

            prepstate.close();
            resultSet.close();

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
