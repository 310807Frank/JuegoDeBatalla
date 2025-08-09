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
public class PartidaDAO {
    
    /**
     * Registra una nueva partida en la base de datos
     */
    public void registrarPartida(int idJugador1, int idJugador2, 
                                 int idPersonaje1, int idPersonaje2, 
                                 int idGanador, int duracionTurnos) {
        
        String sql = "INSERT INTO partida (id_jugador1, id_jugador2, id_personaje1, " +
                     "id_personaje2, id_ganador, duracion_turnos) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idJugador1);
            pstmt.setInt(2, idJugador2);
            pstmt.setInt(3, idPersonaje1);
            pstmt.setInt(4, idPersonaje2);
            pstmt.setInt(5, idGanador);
            pstmt.setInt(6, duracionTurnos);
            
            pstmt.executeUpdate();
            System.out.println("✓ Partida registrada en la base de datos");
            
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar partida: " + e.getMessage());
        }
    }
    
    /**
     * Muestra el historial de partidas recientes
     */
    public void mostrarHistorialPartidas(int limite) {
        String sql = "SELECT j1.nombre as jugador1, j2.nombre as jugador2, " +
                     "jg.nombre as ganador, p.duracion_turnos, p.fecha_partida " +
                     "FROM partida p " +
                     "JOIN jugador j1 ON p.id_jugador1 = j1.id " +
                     "JOIN jugador j2 ON p.id_jugador2 = j2.id " +
                     "JOIN jugador jg ON p.id_ganador = jg.id " +
                     "ORDER BY p.fecha_partida DESC LIMIT ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limite);
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\n=== HISTORIAL DE PARTIDAS RECIENTES ===");
            System.out.println("----------------------------------------");
            
            while (rs.next()) {
                System.out.printf("%s vs %s | Ganador: %s | Turnos: %d | Fecha: %s\n",
                    rs.getString("jugador1"),
                    rs.getString("jugador2"),
                    rs.getString("ganador"),
                    rs.getInt("duracion_turnos"),
                    rs.getTimestamp("fecha_partida")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al mostrar historial: " + e.getMessage());
        }
    } 
}
