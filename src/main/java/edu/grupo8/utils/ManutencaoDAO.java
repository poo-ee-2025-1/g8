package edu.grupo8.utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import edu.grupo8.models.Manutencao;
import edu.grupo8.models.Equipamento;

import java.sql.SQLException;
import java.util.List;

public class ManutencaoDAO {

    private final Dao<Manutencao, Integer> dao;

    public ManutencaoDAO() throws SQLException {
        dao = DaoManager.createDao(DBUtils.getConnectionSource(), Manutencao.class);
        TableUtils.createTableIfNotExists(DBUtils.getConnectionSource(), Manutencao.class);
    }

    public void create(Manutencao manutencao) throws SQLException {
        dao.create(manutencao);
    }

    public Manutencao read(int id) throws SQLException {
        return dao.queryForId(id);
    }

    public List<Manutencao> readAll() throws SQLException {
        return dao.queryForAll();
    }

    public void update(Manutencao manutencao) throws SQLException {
        dao.update(manutencao);
    }

    public void delete(Manutencao manutencao) throws SQLException {
        dao.delete(manutencao);
    }

    public List<Manutencao> listarPorEquipamento(Equipamento equipamento) throws SQLException {
        return dao.queryBuilder()
                .where()
                .eq("equipamento_id", equipamento.getId())
                .query();
    }
}
