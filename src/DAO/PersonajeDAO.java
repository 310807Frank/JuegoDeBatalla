/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Modelo.Personaje;
import java.sql.*;
/**
 *
 * @author Franklin Castillo
 */
public class PersonajeDAO {
     public void guardarPersonaje(Personaje personaje) {
        String sql = "INSERT INTO personaje (nombre, raza, fuerza, energia, vida_actual, id_arma) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, personaje.getNombre());
            pstmt.setString(2, personaje.getRaza());
            pstmt.setInt(3, personaje.getFuerza());
            pstmt.setInt(4, personaje.getEnergia());
            pstmt.setInt(5, personaje.getVidaActual());
            pstmt.setInt(6, obtenerIdArma(personaje.getArmaSeleccionada()));
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    personaje.setId(rs.getInt(1));
                }
                System.out.println("Personaje guardado: " + personaje.getNombre());
            }
            
        } catch (SQLException e) {
            System.err.println("Error al guardar personaje: " + e.getMessage());
        }
    }
    
    public Personaje obtenerPersonaje(int id) {
        String sql = "SELECT * FROM personaje WHERE id = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Aquí deberías crear el personaje según su raza
                // Este es un ejemplo simplificado
                String nombre = rs.getString("nombre");
                String raza = rs.getString("raza");
                int vida = rs.getInt("vida_actual");
                
                System.out.println("Personaje encontrado: " + nombre);
                // Retornar el personaje apropiado según la raza
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener personaje: " + e.getMessage());
        }
        
        return null;
    }
    
    private int obtenerIdArma(String nombreArma) {
        String sql = "SELECT id FROM arma WHERE nombre = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombreArma);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                // Si no existe, insertarla
                return insertarArma(nombreArma);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener ID del arma: " + e.getMessage());
        }
        
        return 1; // ID por defecto
    }
    
    private int insertarArma(String nombreArma) {
        String sql = "INSERT INTO arma (nombre, tipo, daño_minimo, daño_maximo, modificadores) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, nombreArma);
            pstmt.setString(2, determinarTipoArma(nombreArma));
            pstmt.setInt(3, 1);
            pstmt.setInt(4, 10);
            pstmt.setString(5, "");
            
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al insertar arma: " + e.getMessage());
        }
        
        return 1;
    }
    
    private String determinarTipoArma(String nombreArma) {
        if (nombreArma.contains("Báculo")) return "Magia";
        if (nombreArma.contains("Escopeta") || nombreArma.contains("Rifle")) return "Fuego";
        if (nombreArma.contains("Hacha") || nombreArma.contains("Martillo")) return "Melee";
        if (nombreArma.contains("Espada")) return "Melee";
        if (nombreArma.contains("Puños")) return "Natural";
        return "Desconocido";
    }

    public Iterable<String> obtenerEstadisticasRazas() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
