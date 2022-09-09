package DAO;

import Classes.Adres;
import Classes.Reiziger;

import java.util.List;

public interface AdresDAO {

    public boolean save(Adres adres);
    public boolean update (Adres adres);
    public boolean delete(Adres adres);
    public Adres findByReiziger(Reiziger reiziger);
    public List<Adres> findAll();

}

