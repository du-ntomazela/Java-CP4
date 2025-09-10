package br.com.restaurante.estoque;

import java.util.List;

public class App {
    public static void main(String[] args) {
        EstoqueService service = new EstoqueService();




        Louca prato = new Louca("Prato de Porcelana", 50, 25, true, "Porcelana");
        Alimentos arroz = new Alimentos("Arroz 5kg", 20, 5, 365);
        service.criarERegistrar(prato);
        service.criarERegistrar(arroz);


        service.deletar(3);
        System.out.println("Itens cadastrados no banco:");
        List<Item> itens = service.listar();
        for (Item i : itens) {
            System.out.printf("ID=%d | TIPO=%s | NOME=%s | QTD=%d | CAP=%d%n",
                    i.getId(), i.getTipo(), i.getNome(), i.getQtd(), i.getCapacidade());

        }
    }
}
