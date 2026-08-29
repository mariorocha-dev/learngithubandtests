package br.com.sergranbel.sistemacarregamentos.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

    private static final String URL = "jdbc:sqlite:sistemacarregamentos.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
