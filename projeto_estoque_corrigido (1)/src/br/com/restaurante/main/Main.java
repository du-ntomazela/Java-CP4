package br.com.restaurante.main;

import br.com.restaurante.estoque.ConexaoBD;
import br.com.restaurante.estoque.EstoqueService;
import br.com.restaurante.estoque.Item;
import br.com.restaurante.estoque.Louca;
import br.com.restaurante.estoque.Alimentos;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Conectando...");
            var meta = ConexaoBD.getInstancia().getConexao().getMetaData();
            System.out.println("OK! Banco: " + meta.getURL());

            var service = new EstoqueService();

            Item prato = new Louca("Prato de Porcelana", 50, 25, true, "Porcelana");
            Item arroz = new Alimentos("Arroz 5kg", 20, 5, 365);

            service.criarERegistrar(prato);
            service.criarERegistrar(arroz);

            System.out.println("\nItens cadastrados:");
            imprimir(service.listar());

            if (prato instanceof Louca l) {
                l.setLimpo(false);
                l.setMaterial("Vidro");
                l.setQtd(60);
                service.atualizar(l);
            }

            System.out.println("\nApós atualizar o prato:");
            imprimir(service.listar());

            service.deletar(arroz.getId());

            System.out.println("\nApós remover o arroz:");
            imprimir(service.listar());

        } catch (Exception e) {
            System.err.println("Erro ao executar a aplicação:");
            e.printStackTrace();
        }
    }

    private static void imprimir(List<Item> itens) {
        for (Item it : itens) {
            System.out.printf("ID=%d | TIPO=%s | NOME=%s | QTD=%d | CAP=%d%n",
                    it.getId(), it.getTipo(), it.getNome(), it.getQtd(), it.getCapacidade());
        }
    }
}
