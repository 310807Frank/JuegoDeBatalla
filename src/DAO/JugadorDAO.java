/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import DAO.DB.ConexionDB;
import java.sql.*;
/**
 *
 * @author  Franklin Castillo
 */
public class JugadorDAO {
    /**
     * Registra un nuevo jugador o retorna el ID si ya existe
     */
    public int registrarJugador(String nombre) {
        // Primero verificar si existe
        String sqlBuscar = "SELECT id FROM jugador WHERE nombre = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sqlBuscar)) {
            
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                // Si no existe, crear nuevo
                return crearNuevoJugador(nombre);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar jugador: " + e.getMessage());
        }
        return -1;
    }
    
    /**
     * Crea un nuevo jugador en la base de datos
     */
    private int crearNuevoJugador(String nombre) {
        String sql = "INSERT INTO jugador (nombre, partidas_ganadas, partidas_perdidas) VALUES (?, 0, 0)";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("✓ Nuevo jugador registrado: " + nombre);
                return id;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al crear jugador: " + e.getMessage());
        }
        return -1;
    }
    
    /**
     * Actualiza las estadísticas del jugador
     */
    public void actualizarEstadisticas(String nombre, boolean gano) {
        String sql = gano ? 
            "UPDATE jugador SET partidas_ganadas = partidas_ganadas + 1 WHERE nombre = ?" :
            "UPDATE jugador SET partidas_perdidas = partidas_perdidas + 1 WHERE nombre = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombre);
            int filasActualizadas = pstmt.executeUpdate();
            
            if (filasActualizadas > 0) {
                String resultado = gano ? "victoria" : "derrota";
                System.out.println("✓ Estadísticas actualizadas: " + nombre + " - " + resultado);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar estadísticas: " + e.getMessage());
        }
    }
    
    /**
     * Muestra las estadísticas de un jugador
     */
    public void mostrarEstadisticas(String nombre) {
        String sql = "SELECT * FROM jugador WHERE nombre = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int ganadas = rs.getInt("partidas_ganadas");
                int perdidas = rs.getInt("partidas_perdidas");
                int total = ganadas + perdidas;
                
                System.out.println("\n╔════════════════════════════════════╗");
                System.out.println("║  Estadísticas de: " + String.format("%-17s", nombre) + "║");
                System.out.println("╠════════════════════════════════════╣");
                System.out.println("║  Partidas ganadas: " + String.format("%-16d", ganadas) + "║");
                System.out.println("║  Partidas perdidas: " + String.format("%-15d", perdidas) + "║");
                System.out.println("║  Total de partidas: " + String.format("%-15d", total) + "║");
                
                if (total > 0) {
                    double porcentaje = (ganadas * 100.0) / total;
                    System.out.println("║  % de victorias: " + String.format("%-18.2f", porcentaje) + "║");
                }
                System.out.println("╚════════════════════════════════════╝");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al mostrar estadísticas: " + e.getMessage());
        }
    }
    
    /**
     * Muestra el ranking de todos los jugadores
     */
    public void mostrarRanking() {
        String sql = "SELECT nombre, partidas_ganadas, partidas_perdidas, " +
                     "(partidas_ganadas * 100.0 / NULLIF(partidas_ganadas + partidas_perdidas, 0)) as porcentaje " +
                     "FROM jugador " +
                     "ORDER BY partidas_ganadas DESC, porcentaje DESC " +
                     "LIMIT 10";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n╔═══════════════════════════════════════════════════╗");
            System.out.println("║              🏆 RANKING DE JUGADORES 🏆            ║");
            System.out.println("╠═══╦════════════╦═════════╦══════════╦════════════╣");
            System.out.println("║ # ║   Nombre   ║ Ganadas ║ Perdidas ║ % Victoria ║");
            System.out.println("╠═══╬════════════╬═════════╬══════════╬════════════╣");
            
            int posicion = 1;
            while (rs.next()) {
                System.out.printf("║%2d ║ %-10s ║   %3d   ║    %3d   ║   %6.2f%%  ║\n",
                    posicion++,
                    rs.getString("nombre"),
                    rs.getInt("partidas_ganadas"),
                    rs.getInt("partidas_perdidas"),
                    rs.getDouble("porcentaje") != 0 ? rs.getDouble("porcentaje") : 0.0
                );
            }
            System.out.println("╚═══╩════════════╩═════════╩══════════╩════════════╝");
            
        } catch (SQLException e) {
            System.err.println("❌ Error al mostrar ranking: " + e.getMessage());
        }
    }

}
