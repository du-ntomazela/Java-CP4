package br.com.restaurante.estoque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAOImpl implements EstoqueDAO {

    private final Connection conn;

    public EstoqueDAOImpl() {
        this.conn = ConexaoBD.getInstancia().getConexao();
    }

    @Override
    public void salvar(Item item) {
        String sql = "INSERT INTO ESTOQUE (TIPO, NOME, QTD, CAPACIDADE, LIMPO, MATERIAL, VALIDADE_DIAS) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, new String[]{ "ID" })) {
            ps.setString(1, item.getTipo());
            ps.setString(2, item.getNome());
            ps.setInt(3, item.getQtd());
            ps.setInt(4, item.getCapacidade());

            if (item instanceof Louca) {
                Louca l = (Louca) item;
                ps.setString(5, l.isLimpo() ? "S" : "N");
                ps.setString(6, l.getMaterial());
                ps.setObject(7, null);
            } else if (item instanceof Alimentos) {
                Alimentos a = (Alimentos) item;
                ps.setObject(5, null);
                ps.setObject(6, null);
                ps.setInt(7, a.getValidadeDias());
            } else {
                ps.setObject(5, null);
                ps.setObject(6, null);
                ps.setObject(7, null);
            }

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    item.setId(rs.getInt(1));
                }
            } catch (SQLException ignore) {}
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar item: " + e.getMessage(), e);
        }
    }

    @Override
    public Item buscarPorId(int id) {
        String sql = "SELECT ID, TIPO, NOME, QTD, CAPACIDADE, LIMPO, MATERIAL, VALIDADE_DIAS FROM ESTOQUE WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapItem(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar item: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void atualizar(Item item) {
        String sql = "UPDATE ESTOQUE SET TIPO=?, NOME=?, QTD=?, CAPACIDADE=?, LIMPO=?, MATERIAL=?, VALIDADE_DIAS=? WHERE ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getTipo());
            ps.setString(2, item.getNome());
            ps.setInt(3, item.getQtd());
            ps.setInt(4, item.getCapacidade());

            if (item instanceof Louca) {
                Louca l = (Louca) item;
                ps.setString(5, l.isLimpo() ? "S" : "N");
                ps.setString(6, l.getMaterial());
                ps.setObject(7, null);
            } else if (item instanceof Alimentos) {
                Alimentos a = (Alimentos) item;
                ps.setObject(5, null);
                ps.setObject(6, null);
                ps.setInt(7, a.getValidadeDias());
            } else {
                ps.setObject(5, null);
                ps.setObject(6, null);
                ps.setObject(7, null);
            }

            ps.setInt(8, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM ESTOQUE WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar item: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Item> listarTodos() {
        String sql = "SELECT ID, TIPO, NOME, QTD, CAPACIDADE, LIMPO, MATERIAL, VALIDADE_DIAS FROM ESTOQUE ORDER BY ID";
        List<Item> itens = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                itens.add(mapItem(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens: " + e.getMessage(), e);
        }
        return itens;
    }

    private Item mapItem(ResultSet rs) throws SQLException {
        String tipo = rs.getString("TIPO");
        String nome = rs.getString("NOME");
        int qtd = rs.getInt("QTD");
        int cap = rs.getInt("CAPACIDADE");

        Item item;
        if ("LOUCA".equalsIgnoreCase(tipo)) {
            Louca l = new Louca(nome, qtd, cap, "S".equalsIgnoreCase(rs.getString("LIMPO")), rs.getString("MATERIAL"));
            l.setId(rs.getInt("ID"));
            item = l;
        } else if ("ALIMENTO".equalsIgnoreCase(tipo)) {
            Alimentos a = new Alimentos(nome, qtd, cap, rs.getInt("VALIDADE_DIAS"));
            a.setId(rs.getInt("ID"));
            item = a;
        } else {
            Alimentos a = new Alimentos(nome, qtd, cap, rs.getInt("VALIDADE_DIAS"));
            a.setId(rs.getInt("ID"));
            item = a;
        }
        return item;
    }
}
