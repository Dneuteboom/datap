package DAOPsql;

import Classes.OVChipkaart;
import Classes.Reiziger;
import DAO.OVChipkaartDAO;
import DAO.ReizigerDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection connection;
    private ReizigerDAO rdao;


    public OVChipkaartDAOPsql(Connection connection) {
        this.connection = connection;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }


    @Override
    public boolean save(OVChipkaart ovchipkaart){
        try{
            String query = "insert into ov_chipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) " +
                    "values(?,?,?,?,?)";
            PreparedStatement prepstate = connection.prepareStatement(query);
            prepstate.setInt(1, ovchipkaart.getKaart_nummer());
            prepstate.setDate(2, (java.sql.Date) ovchipkaart.getGeldig_tot());
            prepstate.setInt(3, ovchipkaart.getKlasse());
            prepstate.setFloat(4, ovchipkaart.getSaldo());
            prepstate.setInt(5, ovchipkaart.getReiziger_id());
            prepstate.executeUpdate();


            prepstate.close();

            return true;


        }catch (SQLException e){
            System.out.println("fout in sql 'save' ovchipkaart");
            System.out.println(e.getMessage());
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovchipkaart){
        try{



            String query = "update ov_chipkaart set geldig_tot = ?, klasse = ?, saldo = ?" +
                    "where kaart_nummer = ?";
            PreparedStatement prepstate = connection.prepareStatement(query);
            prepstate.setDate(1, (java.sql.Date) ovchipkaart.getGeldig_tot());
            prepstate.setInt(2, ovchipkaart.getKlasse());
            prepstate.setFloat(3, ovchipkaart.getSaldo());
            prepstate.setInt(4, ovchipkaart.getKaart_nummer());
            prepstate.executeUpdate();

            prepstate.close();

            return true;

        }catch (SQLException e){
            System.out.println("fout in sql 'update' ovchipkaart");
            System.out.println(e.getMessage());
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean delete(OVChipkaart ovchipkaart) {
        try {


            String query = "delete from ov_chipkaart where kaart_nummer = ? ";
            PreparedStatement prepstate = connection.prepareStatement(query);
            prepstate.setInt(1, ovchipkaart.getKaart_nummer());
            prepstate.executeUpdate();

            prepstate.close();

            return true;

        } catch (SQLException e) {
            System.out.println("fout in sql 'delete' ovchipkaart");
            System.out.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger){
        List<OVChipkaart> kaarten = new ArrayList<>();
        try {
            String query = "select * from ov_chipkaart where reiziger_id = ? ";
            PreparedStatement prepstate = connection.prepareStatement(query);
            prepstate.setInt(1, reiziger.getReiziger_id());
            ResultSet resultset = prepstate.executeQuery();
            while(resultset.next()) {
                OVChipkaart ovc = new OVChipkaart(
                        resultset.getInt(1),
                        resultset.getDate(2),
                        resultset.getInt(3),
                        resultset.getFloat(4),
                        resultset.getInt(5));
                ovc.setReiziger(reiziger);
                kaarten.add(ovc);
            }
            prepstate.close();
            return kaarten;

        }catch (SQLException e){
            System.out.println("fout in sql 'findByReiziger' ovchipkaart");
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }



    @Override
    public List<OVChipkaart> findAll(){
        List<OVChipkaart> ovKaarten = new ArrayList<>();
        try{
            String query = "select * from ov_chipkaart";
            PreparedStatement prepstate = connection.prepareStatement(query);
            ResultSet resultset = prepstate.executeQuery();
            while(resultset.next()){
                OVChipkaart ovc = new OVChipkaart(
                        resultset.getInt(1),
                        resultset.getDate(2),
                        resultset.getInt(3),
                        resultset.getFloat(4),
                        resultset.getInt(5));

                ovc.setReiziger(rdao.findById(resultset.getInt(5)));
                ovKaarten.add(ovc);
            }

            resultset.close();
            prepstate.close();

            return ovKaarten;


        }catch (SQLException e){
            System.out.println("fout in sql 'findAll' ovchipkaart");
            System.out.println(e.getMessage());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
