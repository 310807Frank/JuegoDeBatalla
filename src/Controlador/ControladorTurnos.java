/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.Personaje;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Franklin
 */
public class ControladorTurnos {
    private List<Personaje> ordenTurnos;
    private int turnoActual;
    private boolean combateActivo;
    
    public ControladorTurnos() {
        this.ordenTurnos = new ArrayList<>();
        this.turnoActual = 0;
        this.combateActivo = false;
    }
    
    /**
     * Inicializa el orden de turnos
     */
    public void inicializarTurnos(Personaje jugador1, Personaje jugador2) {
        ordenTurnos.clear();
        ordenTurnos.add(jugador1);
        ordenTurnos.add(jugador2);
        turnoActual = 0;
        combateActivo = true;
    }
    
    /**
     * Obtiene el personaje del turno actual
     */
    public Personaje obtenerTurnoActual() {
        if (!combateActivo || ordenTurnos.isEmpty()) {
            return null;
        }
        return ordenTurnos.get(turnoActual % ordenTurnos.size());
    }
    
    /**
     * Avanza al siguiente turno
     */
    public void siguienteTurno() {
        turnoActual++;
    }
    
    /**
     * Verifica si el combate continúa
     */
    public boolean combateContinua() {
        if (!combateActivo) {
            return false;
        }
        
        for (Personaje p : ordenTurnos) {
            if (!p.estaVivo()) {
                combateActivo = false;
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Obtiene el ganador del combate
     */
    public Personaje obtenerGanador() {
        for (Personaje p : ordenTurnos) {
            if (p.estaVivo()) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Reinicia el controlador de turnos
     */
    public void reiniciar() {
        ordenTurnos.clear();
        turnoActual = 0;
        combateActivo = false;
    }
    
    // Getters y Setters
    public int getTurnoNumero() {
        return turnoActual + 1;
    }
    
    public boolean isCombateActivo() {
        return combateActivo;
    }
    
    public void setCombateActivo(boolean activo) {
        this.combateActivo = activo;
    } 
}
