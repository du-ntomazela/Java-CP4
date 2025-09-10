package br.com.restaurante.estoque;

import java.util.List;

public interface EstoqueDAO {
    void salvar(Item item);
    Item buscarPorId(int id);
    void atualizar(Item item);
    void deletar(int id);
    List<Item> listarTodos();
}
