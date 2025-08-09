/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.Personaje;
import Modelo.Raza.Orco;
import java.util.Scanner;
/**
 *
 * @author Franklin
 */
public class ControladorCombate {
    private Scanner scanner;
    private int turnoActual;
    private int totalTurnos;
    
    public ControladorCombate() {
        this.scanner = new Scanner(System.in);
        this.turnoActual = 1;
        this.totalTurnos = 0;
    }
    
    /**
     * Ejecuta el combate completo entre dos personajes
     */
    public String ejecutarCombate(Personaje jugador1, Personaje jugador2) {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║         ⚔️  ¡COMIENZA EL COMBATE!  ⚔️        ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        // Posicionar jugadores
        jugador1.setPosicion(0);
        jugador2.setPosicion(3); // Empiezan separados
        
        String ganador = null;
        
        while (jugador1.estaVivo() && jugador2.estaVivo() && totalTurnos < 100) {
            mostrarSeparadorTurno();
            
            // Mostrar estado de ambos jugadores
            mostrarEstadoCombate(jugador1, jugador2);
            
            // Turno del Jugador 1
            if (jugador1.estaVivo()) {
                ejecutarTurnoJugador(jugador1, jugador2, 1);
                aplicarEfectosPasivos(jugador1);
                
                if (!jugador2.estaVivo()) {
                    ganador = jugador1.getNombreJugador();
                    mostrarVictoria(jugador1);
                    break;
                }
            }
            
            // Turno del Jugador 2
            if (jugador2.estaVivo()) {
                ejecutarTurnoJugador(jugador2, jugador1, 2);
                aplicarEfectosPasivos(jugador2);
                
                if (!jugador1.estaVivo()) {
                    ganador = jugador2.getNombreJugador();
                    mostrarVictoria(jugador2);
                    break;
                }
            }
            
            turnoActual++;
            totalTurnos++;
        }
        
        if (totalTurnos >= 100) {
            System.out.println("\n⚠️ ¡El combate ha durado demasiado! Empate técnico.");
            ganador = "EMPATE";
        }
        
        return ganador;
    }
    
    /**
     * Ejecuta el turno de un jugador
     */
    private void ejecutarTurnoJugador(Personaje atacante, Personaje defensor, int numeroJugador) {
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│  Turno de: " + String.format("%-24s", atacante.getNombre()) + "│");
        System.out.println("│  Jugador " + numeroJugador + "                          │");
        System.out.println("└─────────────────────────────────────┘");
        
        mostrarMenuAcciones(atacante, defensor);
        
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        ejecutarAccion(opcion, atacante, defensor);
    }
    
    /**
     * Muestra el menú de acciones disponibles
     */
    private void mostrarMenuAcciones(Personaje atacante, Personaje defensor) {
        System.out.println("\n¿Qué deseas hacer?");
        System.out.println("┌─────────────────────────────┐");
        System.out.println("│ 1. ⚔️  Atacar                │");
        
        if (!atacante.estaFrenteAFrente(defensor)) {
            System.out.println("│    (No estás en rango)      │");
        }
        
        System.out.println("│ 2. 💚 Sanar                  │");
        System.out.println("│ 3. → Avanzar                │");
        System.out.println("│ 4. ← Retroceder             │");
        System.out.println("└─────────────────────────────┘");
        System.out.print("Selecciona (1-4): ");
    }
    
    /**
     * Ejecuta la acción seleccionada
     */
    private void ejecutarAccion(int opcion, Personaje atacante, Personaje defensor) {
        switch (opcion) {
            case 1:
                // Atacar
                if (atacante.estaFrenteAFrente(defensor)) {
                    boolean aDistancia = atacante.estaADistancia(defensor);
                    atacante.atacar(defensor, aDistancia);
                } else {
                    System.out.println("\n⚠️ ¡Estás demasiado lejos para atacar!");
                    System.out.println("   Debes acercarte primero.");
                }
                break;
                
            case 2:
                // Sanar
                atacante.sanar();
                break;
                
            case 3:
                // Avanzar
                atacante.avanzar();
                verificarDistancia(atacante, defensor);
                break;
                
            case 4:
                // Retroceder
                atacante.retroceder();
                verificarDistancia(atacante, defensor);
                break;
                
            default:
                System.out.println("⚠️ Opción inválida. Pierdes tu turno.");
        }
    }
    
    /**
     * Aplica efectos pasivos como sangrado y curación retrasada
     */
    private void aplicarEfectosPasivos(Personaje personaje) {
        // Aplicar sangrado
        personaje.aplicarSangrado();
        
        // Aplicar curación retrasada si es Orco
        if (personaje instanceof Orco) {
            ((Orco) personaje).aplicarCuracionRetrasada();
        }
    }
    
    /**
     * Verifica y muestra la distancia entre los combatientes
     */
    private void verificarDistancia(Personaje p1, Personaje p2) {
        int distancia = Math.abs(p1.getPosicion() - p2.getPosicion());
        
        if (distancia <= 1) {
            System.out.println("⚔️ ¡Los combatientes están frente a frente!");
        } else {
            System.out.println("📏 Distancia entre combatientes: " + distancia + " unidades");
        }
    }
    
    /**
     * Muestra el estado del combate
     */
    private void mostrarEstadoCombate(Personaje j1, Personaje j2) {
        System.out.println("\n═══════════════ ESTADO DEL COMBATE ═══════════════");
        j1.mostrarEstado();
        System.out.println("\n                    VS\n");
        j2.mostrarEstado();
        System.out.println("═══════════════════════════════════════════════════");
    }
    
    /**
     * Muestra el separador de turno
     */
    private void mostrarSeparadorTurno() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║           TURNO " + String.format("%-3d", turnoActual) + "                   ║");
        System.out.println("╚═══════════════════════════════════════╝");
    }
    
    /**
     * Muestra el mensaje de victoria
     */
    private void mostrarVictoria(Personaje ganador) {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║              🏆 ¡VICTORIA! 🏆               ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║  Ganador: " + String.format("%-32s", ganador.getNombre()) + "║");
        System.out.println("║  Jugador: " + String.format("%-32s", ganador.getNombreJugador()) + "║");
        System.out.println("║  Vida restante: " + String.format("%-26s", ganador.getVidaActual() + "/" + ganador.getVidaMaxima()) + "║");
        System.out.println("║  Turnos totales: " + String.format("%-25d", totalTurnos) + "║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }
    
    // Getters
    public int getTotalTurnos() {
        return totalTurnos;
    }
}
