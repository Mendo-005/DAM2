package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/prueba_java";
        String user = "root";         // tu usuario MySQL
        String password = "Petalo050678.";  // tu contraseña

        try {
            // 1. Conexión
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Conexión exitosa");

            // 2. Crear Statement
            Statement stmt = con.createStatement();

            // 3. Ejecutar consulta
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");

            // 4. Mostrar resultados
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                System.out.println(id + " - " + nombre + " - " + email);
            }

            // 5. Cerrar conexión
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
