package br.com.restaurante.estoque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static volatile ConexaoBD instancia;
    private Connection conexao;

    private static final String URL = "jdbc:oracle:thin:@//oracle.fiap.com.br:1521/ORCL"; // ajuste para seu ambiente
    private static final String USUARIO = "rm557768";
    private static final String SENHA = "260206";

    private ConexaoBD() {
        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar no banco: " + e.getMessage(), e);
        }
    }

    public static ConexaoBD getInstancia() {
        if (instancia == null) {
            synchronized (ConexaoBD.class) {
                if (instancia == null) {
                    instancia = new ConexaoBD();
                }
            }
        }
        return instancia;
    }

    public Connection getConexao() {
        return conexao;
    }
}
