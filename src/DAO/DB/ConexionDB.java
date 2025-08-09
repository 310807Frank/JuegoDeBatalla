/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Anguar Alberto Rodriguez Fonseca
 */
public class ConexionDB {
  // Configuración para SQL Server
    private static final String DB_TYPE = "sqlserver"; // Tipo de base de datos
    private static final String HOST = "localhost";
    private static final String PORT = "1433";
    private static final String DATABASE = "juego_batalla";
    private static final String USUARIO = "sa";
    private static final String CONTRASENA = "admin"; // Cambia esto si tienes otra contraseña

    private static Connection conexion = null;

    /**
     * Obtiene la URL de conexión según el tipo de base de datos
     */
    private static String obtenerURL() {
        switch (DB_TYPE.toLowerCase()) {
            case "postgresql":
                return "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE;
            case "sqlserver":
                return "jdbc:sqlserver://" + HOST + ":" + PORT + ";databaseName=" + DATABASE + ";encrypt=true;trustServerCertificate=true";
            case "oracle":
                return "jdbc:oracle:thin:@" + HOST + ":" + PORT + ":xe";
            default:
                return "jdbc:sqlserver://" + HOST + ":" + PORT + ";databaseName=" + DATABASE + ";encrypt=true;trustServerCertificate=true";
        }
    }

    /**
     * Obtiene el driver según el tipo de base de datos
     */
    private static String obtenerDriver() {
        switch (DB_TYPE.toLowerCase()) {
            case "postgresql":
                return "org.postgresql.Driver";
            case "sqlserver":
                return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            case "oracle":
                return "oracle.jdbc.driver.OracleDriver";
            default:
                return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        }
    }

    /**
     * Obtiene la conexión a la base de datos
     */
    public static Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                Class.forName(obtenerDriver());
                conexion = DriverManager.getConnection(obtenerURL(), USUARIO, CONTRASENA);
                System.out.println("✓ Conexión establecida con " + DB_TYPE);
            } catch (ClassNotFoundException e) {
                System.err.println("❌ Error: Driver no encontrado - " + e.getMessage());
                throw new SQLException("Driver de base de datos no encontrado", e);
            }
        }
        return conexion;
    }

    /**
     * Verifica si la conexión está disponible
     */
    public static boolean verificarConexion() {
        try {
            Connection conn = obtenerConexion();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("❌ Error al verificar conexión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cierra la conexión a la base de datos
     */
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("✓ Conexión cerrada correctamente");
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}
