/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import DAO.DB.ConexionDB;
import java.sql.*;
/**
 *
 * @author Anguar Alberto Rodriguez Fonseca
 */
public class ArmaDAO {
    /**
     * Obtiene el ID de un arma por su nombre
     */
    public int obtenerIdArma(String nombreArma) {
        String sql = "SELECT id FROM arma WHERE nombre = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombreArma);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener ID del arma: " + e.getMessage());
        }
        
        return 1; // ID por defecto
    }
    
    /**
     * Inserta un arma nueva en la base de datos
     */
    public int insertarArma(String nombre, String tipo, int danoMin, int danoMax, String modificadores) {
        String sql = "INSERT INTO arma (nombre, tipo, daño_minimo, daño_maximo, modificadores) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, nombre);
            pstmt.setString(2, tipo);
            pstmt.setInt(3, danoMin);
            pstmt.setInt(4, danoMax);
            pstmt.setString(5, modificadores);
            
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar arma: " + e.getMessage());
        }
        
        return 1;
    }
    
    /**
     * Muestra estadísticas de uso de armas
     */
    public void mostrarEstadisticasArmas() {
        String sql = "SELECT a.nombre, a.tipo, COUNT(p.id) as veces_usada " +
                     "FROM arma a " +
                     "LEFT JOIN personaje p ON a.id = p.id_arma " +
                     "GROUP BY a.id, a.nombre, a.tipo " +
                     "ORDER BY veces_usada DESC";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== ESTADÍSTICAS DE ARMAS ===");
            while (rs.next()) {
                System.out.printf("%-20s (%s): %d usos\n",
                    rs.getString("nombre"),
                    rs.getString("tipo"),
                    rs.getInt("veces_usada")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al mostrar estadísticas de armas: " + e.getMessage());
        }
    }
}
