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

    public ReizigerDAOPsql(Connection connection) {

        this.connection = connection;
    }

    public OVChipkaartDAO getOvdao() {
        return ovdao;
    }

    public void setOvdao(OVChipkaartDAO ovdao) {
        this.ovdao = ovdao;
    }

    public AdresDAO getAdao() {
        return adao;
    }

    public void setAdao(AdresDAO adao) {
        this.adao = adao;
    }


    @Override
    public boolean save(Reiziger reiziger) {

        try {
            String sql = "insert into reiziger " +
                    "(reiziger_id, voorletters, tussenvoegsel,achternaam, geboortedatum) Values(?,?,?,?,?)";
            PreparedStatement prepstate = connection.prepareStatement(sql);

            prepstate.setInt(1,reiziger.getId());
            prepstate.setString(2,reiziger.getVoorletters());
            prepstate.setString(3, reiziger.getTussenvoegsel());
            prepstate.setString(4, reiziger.getAchternaam());
            prepstate.setDate(5,reiziger.getGeboortedatum());

            prepstate.executeUpdate();

            if(reiziger.getReizigerAdres() != null){
                adao.save(reiziger.getReizigerAdres());
            }

            ArrayList<OVChipkaart> Kaarten = reiziger.getOvchipkaarten();
            for(OVChipkaart ovchipkaart : Kaarten){
                if(Kaarten.size() != 0) {
                    ovdao.save(ovchipkaart);
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
            String sql = "update reiziger " +
                    "set voorletters =?,tussenvoegsel =?,achternaam =?,geboortedatum =? where reiziger_id=?";
            PreparedStatement prepstate = connection.prepareStatement(sql);

            prepstate.setString(1,reiziger.getVoorletters());
            prepstate.setString(2,reiziger.getTussenvoegsel());
            prepstate.setString(3,reiziger.getAchternaam());
            prepstate.setDate(4,reiziger.getGeboortedatum());
            prepstate.setInt(5,reiziger.getId());

            prepstate.executeUpdate();


            if(adao.findByReiziger(reiziger) != null) adao.update(reiziger.getReizigerAdres());
            else adao.save(reiziger.getReizigerAdres());


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
    public boolean delete(Reiziger reiziger) {
        try {
            String sql = "delete from reiziger where reiziger_id =?";
            PreparedStatement prepstate = connection.prepareStatement(sql);
            prepstate.setInt(1,reiziger.getId());

            prepstate.executeUpdate();

            if(reiziger.getReizigerAdres() != null)adao.delete(reiziger.getReizigerAdres());

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
    public Reiziger findById(int id) {
        try {
            String sql = "Select * from reiziger where reiziger_id =?";
            PreparedStatement prepstate = connection.prepareStatement(sql);
            prepstate.setInt(1,id);

            ResultSet resultSet = prepstate.executeQuery();

            Reiziger reiziger = null;

            while(resultSet.next()){
             reiziger =new Reiziger(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5)
                );

                reiziger.setReizigerAdres(adao.findByReiziger(reiziger));

                for(OVChipkaart ovChipkaart : ovdao.findByReiziger(reiziger)){
                    reiziger.addOVChipkaart(ovChipkaart);
                }
            }

            resultSet.close();
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
    public List<Reiziger> findByGbdatum(String datum) {
        List<Reiziger> reizigers = new ArrayList<>();
        try{
            String sql = "Select from reiziger where geboortedatum =?";
            PreparedStatement prepstate = connection.prepareStatement(sql);
            prepstate.setDate(1, Date.valueOf(datum));

            ResultSet resultSet = prepstate.executeQuery();

            while(resultSet.next()){
                Reiziger reiziger = new Reiziger(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5)
                );
                reiziger.setReizigerAdres(adao.findByReiziger(reiziger));

                for(OVChipkaart ovChipkaart : ovdao.findByReiziger(reiziger)){
                    reiziger.addOVChipkaart(ovChipkaart);
                }

                reizigers.add(reiziger);
            }
            prepstate.close();
            resultSet.close();

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
    public List<Reiziger> findAll() {
        List <Reiziger> reizigers = new ArrayList<>();

        try{
            String sql = "Select * from reiziger";
            PreparedStatement prepstate = connection.prepareStatement(sql);
            ResultSet resultSet = prepstate.executeQuery();

            while(resultSet.next()){
                Reiziger reiziger = new Reiziger(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5)
                );
                reizigers.add(reiziger);
            }


            prepstate.close();
            resultSet.close();

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

