package DAOPsql;

import Classes.OVChipkaart;
import Classes.Reiziger;
import DAO.OVChipkaartDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{

    Connection connection;

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try{
            String sql = "insert into ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id)" +
                    "Values (?,?,?,?,?)";
            PreparedStatement prepstate = connection.prepareStatement(sql);
            prepstate.setInt(1, ovChipkaart.getKaartNummer());
            prepstate.setDate(2, ovChipkaart.getGeldigTot());
            prepstate.setInt(3, ovChipkaart.getKlasse());
            prepstate.setFloat(4, ovChipkaart.getSaldo());
            prepstate.setInt(5, ovChipkaart.getReizigerId());

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
    public boolean update(OVChipkaart ovChipkaart) {
        try{
            String sql = "update ov_chipkaart (geldig_tot =?, klasse=?, saldo=?, reiziger_id=?) " +
                    "where kaart_nummer = ?";
            PreparedStatement prepstate = connection.prepareStatement(sql);
            prepstate.setDate(1, ovChipkaart.getGeldigTot());
            prepstate.setInt(2, ovChipkaart.getKlasse());
            prepstate.setFloat(3, ovChipkaart.getSaldo());
            prepstate.setInt(4, ovChipkaart.getReizigerId());
            prepstate.setInt(5, ovChipkaart.getKaartNummer());

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
    public boolean delete(OVChipkaart ovChipkaart) {
        try{
            String sql = "delete from ov_chipkaart where kaart_nummer =?";
            PreparedStatement prepstate = connection.prepareStatement(sql);
            prepstate.setInt(1,ovChipkaart.getKaartNummer());

            prepstate.executeUpdate();

            prepstate.close();

            return true;


        }catch (SQLException e){
            System.out.println("fout in sql 'delete' ovchipkaart");
            System.out.println(e.getMessage());
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        List<OVChipkaart> kaarten = new ArrayList<>();

        try {
            String sql = "select reiziger from ov_chipkaart where kaart_nummer =?";
            PreparedStatement prepstate = connection.prepareStatement(sql);
            prepstate.setInt(1,reiziger.getId());

            ResultSet resultSet = prepstate.executeQuery();



            while (resultSet.next()){
                OVChipkaart kaart = new OVChipkaart(
                        resultSet.getInt(1),
                        resultSet.getDate(2),
                        resultSet.getInt(3),
                        resultSet.getFloat(4),
                        resultSet.getInt(5)
                );
                kaarten.add(kaart);
            }

            prepstate.close();
            resultSet.close();

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
    public List<OVChipkaart> findAll() {
        List<OVChipkaart> ovKaarten = new ArrayList<>();

        try{
            String sql = "select * from ov_chipkaart";
            PreparedStatement prepstate = connection.prepareStatement(sql);

            ResultSet resultSet = prepstate.executeQuery();

            while (resultSet.next()){
                OVChipkaart ovkaart = new OVChipkaart(
                        resultSet.getInt(1),
                        resultSet.getDate(2),
                        resultSet.getInt(3),
                        resultSet.getFloat(4),
                        resultSet.getInt(5)
                );
                ovKaarten.add(ovkaart);
            }

            prepstate.close();
            resultSet.close();

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
