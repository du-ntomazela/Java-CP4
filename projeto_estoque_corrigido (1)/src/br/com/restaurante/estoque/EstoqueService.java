package br.com.restaurante.estoque;

import java.util.List;

public class EstoqueService {
    private final EstoqueDAO dao = new EstoqueDAOImpl();

    public Item criarERegistrar(Item item) {
        dao.salvar(item);
        return item;
    }

    public Item buscar(int id) { return dao.buscarPorId(id); }
    public void atualizar(Item item) { dao.atualizar(item); }
    public void deletar(int id) { dao.deletar(id); }
    public List<Item> listar() { return dao.listarTodos(); }
}
