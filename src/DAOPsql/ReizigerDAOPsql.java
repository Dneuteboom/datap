package DAOPsql;

import Classes.OVChipkaart;
import Classes.Reiziger;
import DAO.AdresDAO;
import DAO.OVChipkaartDAO;
import DAO.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection connection;
    private AdresDAO adao;
    private OVChipkaartDAO ovdao;


    public void setAdao(AdresDAO adao) {
        this.adao = adao;
    }

    public void setOvdao(OVChipkaartDAO ovdao) {
        this.ovdao = ovdao;
    }

    public ReizigerDAOPsql(Connection connection) {
        this.connection = connection;
    }



    @Override
    public boolean save(Reiziger reiziger){
        try{
            String query = "insert into reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) values(?,?,?,?,?)";
            PreparedStatement prepstate = connection.prepareStatement(query);
            prepstate.setInt(1, reiziger.getReiziger_id());
            prepstate.setString(2, reiziger.getVoorletters());
            prepstate.setString(3, reiziger.getTussenvoegsel());
            prepstate.setString(4, reiziger.getAchternaam());
            prepstate.setDate(5, (Date) reiziger.getGeboortedatum());
            prepstate.executeUpdate();

            if(reiziger.getAdres() != null){
                adao.save(reiziger.getAdres());
            }

            ArrayList<OVChipkaart> ovKaarten = reiziger.getOvchipkaarten();
            for(OVChipkaart ovc : ovKaarten){
                if(ovKaarten.size() != 0) {
                    ovdao.save(ovc);
                }
            }

            prepstate.close();

            return true;

        } catch (SQLException e) {
            System.out.println("fout in sql statement 'save' reiziger");
            System.out.println(e.getMessage());
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger){
        try{
            String query = "update reiziger set voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? " +
                    "where reiziger_id = ?";
            PreparedStatement prepstate = connection.prepareStatement(query);
            prepstate.setString(1, reiziger.getVoorletters());
            prepstate.setString(2, reiziger.getTussenvoegsel());
            prepstate.setString(3, reiziger.getAchternaam());
            prepstate.setDate(4, (Date) reiziger.getGeboortedatum());
            prepstate.setInt(5, reiziger.getReiziger_id());
            prepstate.executeUpdate();

            if(adao.findByReiziger(reiziger) != null) {
                adao.update(reiziger.getAdres());
            } else {
                adao.save(reiziger.getAdres());
            }

            if(reiziger.getOvchipkaarten().size() >= 1) {
                for (OVChipkaart ovc : reiziger.getOvchipkaarten()) {
                    ovdao.update(ovc);
                }
            }

            prepstate.close();
            return true;
        }catch(SQLException e){
            System.out.println("fout in sql code 'update' reiziger");
            System.out.println(e.getMessage());
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger){
        try{
            String query = "delete from reiziger where reiziger_id = ? ";
            PreparedStatement prepstate = connection.prepareStatement(query);
            prepstate.setInt(1, reiziger.getReiziger_id());
            prepstate.executeUpdate();

            if(reiziger.getAdres() != null){
                adao.delete(reiziger.getAdres());
            }

            if (reiziger.getOvchipkaarten().size() >= 1) {
                for(OVChipkaart ovc : reiziger.getOvchipkaarten()){
                    ovdao.delete(ovc);
                }
            }

            prepstate.close();

            return true;

        }catch (SQLException e){
            System.out.println("fout in sql code 'delete' reiziger ");
            System.out.println(e.getMessage());
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Reiziger findById(int id){
        try {
            String query = "select * from reiziger where reiziger_id = ? ";
            PreparedStatement prepstate = connection.prepareStatement(query);
            prepstate.setInt(1, id);
            ResultSet resultset = prepstate.executeQuery();

            Reiziger reiziger = null;

            while(resultset.next()){
                reiziger = new Reiziger(
                        resultset.getInt(1),
                        resultset.getString(2),
                        resultset.getString(3),
                        resultset.getString(4),
                        resultset.getDate(5));
                reiziger.setAdres(adao.findByReiziger(reiziger));

                for(OVChipkaart ovc : ovdao.findByReiziger(reiziger)){
                    reiziger.addOvchipkaart(ovc);
                }
            }

            resultset.close();
            prepstate.close();
            return reiziger;


        }catch (SQLException e){
            System.out.println("fout in sql code 'findbyid' reiziger");
            System.out.println(e.getMessage());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbDatum(String datum){
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            String query = "select * from reiziger where geboortedatum = ?";
            PreparedStatement prepstate = connection.prepareStatement(query);
            prepstate.setDate(1, java.sql.Date.valueOf(datum));
            ResultSet resultset = prepstate.executeQuery();
            while (resultset.next()){
                Reiziger reiziger = new Reiziger(
                        resultset.getInt(1),
                        resultset.getString(2),
                        resultset.getString(3),
                        resultset.getString(4),
                        resultset.getDate(5));
                reiziger.setAdres(adao.findByReiziger(reiziger));

                for(OVChipkaart ovc : ovdao.findByReiziger(reiziger)){
                    reiziger.addOvchipkaart(ovc);
                }
                reizigers.add(reiziger);
            }
            prepstate.close();
            resultset.close();
            return reizigers;

        }catch (SQLException e){
            System.out.println("fout in sql code 'findbygbdatum' reiziger");
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        return null;
    }

    @Override
    public List<Reiziger> findAll(){
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            String query = "select * from reiziger";
            PreparedStatement prepstate = connection.prepareStatement(query);
            ResultSet resultset = prepstate.executeQuery();
            while(resultset.next()) {
                Reiziger reiziger = new Reiziger(
                        resultset.getInt(1),
                        resultset.getString(2),
                        resultset.getString(3),
                        resultset.getString(4),
                        resultset.getDate(5));
                reiziger.setAdres(adao.findByReiziger(reiziger));
                reiziger.setOvchipkaarten((ArrayList<OVChipkaart>) ovdao.findByReiziger(reiziger));
                reizigers.add(reiziger);
            }
            resultset.close();
            prepstate.close();
            return reizigers;

        }catch (SQLException e){
            System.out.println("fout in sql code 'findall' reiziger");
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
