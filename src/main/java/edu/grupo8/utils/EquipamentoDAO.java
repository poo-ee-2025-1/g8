package edu.grupo8.utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import edu.grupo8.models.Equipamento;
import java.sql.SQLException;
import java.util.List;

public class EquipamentoDAO {

    private final Dao<Equipamento, Integer> dao;

    public EquipamentoDAO() throws SQLException {
        dao = DaoManager.createDao(DBUtils.getConnectionSource(), Equipamento.class);
        TableUtils.createTableIfNotExists(DBUtils.getConnectionSource(), Equipamento.class);
    }

    public void create(Equipamento equipamento) throws SQLException {
        dao.create(equipamento);
    }

    public Equipamento read(int id) throws SQLException {
        return dao.queryForId(id);
    }

    public List<Equipamento> readAll() throws SQLException {
        return dao.queryForAll();
    }

    public void update(Equipamento equipamento) throws SQLException {
        dao.update(equipamento);
    }

    public void delete(Equipamento equipamento) throws SQLException {
        dao.delete(equipamento);
    }
}
