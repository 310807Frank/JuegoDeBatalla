/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Principal;

import Controlador.ControladorCombate;
import DAO.DB.ConexionDB;
import DAO.JugadorDAO;
import DAO.PartidaDAO;
import DAO.PersonajeDAO;
import Modelo.Personaje;
import Modelo.Raza.Bestia;
import Modelo.Raza.Elfo;
import Modelo.Raza.Humano;
import Modelo.Raza.Orco;
import Util.MenuUtil;
import java.util.Scanner;

/**
 *
 * @author  Franklin Castillo
 */
public class JuegoBatalla {
     private Scanner scanner;
    private Personaje jugador1;
    private Personaje jugador2;
    private int turnoActual;
    
    public JuegoBatalla() {
        this.scanner = new Scanner(System.in);
        this.turnoActual = 1;
    }
    
    /**
     * Método principal para iniciar el juego
     */
    public void iniciarJuego() {
        mostrarBienvenida();
        configurarJugadores();
        ejecutarCombate();
        mostrarGanador();
    }
    
    /**
     * Muestra el mensaje de bienvenida
     */
    private void mostrarBienvenida() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║     ⚔️  BATALLA POR TURNOS - JAVA  ⚔️      ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║      Juego de Combate en Consola          ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
    }
    
    /**
     * Configura los dos jugadores
     */
    private void configurarJugadores() {
        System.out.println("═══════════ CONFIGURACIÓN DE JUGADORES ═══════════\n");
        
        // Jugador 1
        System.out.println("--- JUGADOR 1 ---");
        this.jugador1 = crearPersonaje(1);
        
        System.out.println("\n" + "-".repeat(50) + "\n");
        
        // Jugador 2
        System.out.println("--- JUGADOR 2 ---");
        this.jugador2 = crearPersonaje(2);
        
        System.out.println("\n" + "═".repeat(50) + "\n");
    }
    
    /**
     * Crea un personaje para un jugador
     */
    private Personaje crearPersonaje(int numeroJugador) {
        System.out.print("Ingresa el nombre del Jugador " + numeroJugador + ": ");
        String nombreJugador = scanner.nextLine();
        
        System.out.print("Ingresa el nombre de tu personaje: ");
        String nombrePersonaje = scanner.nextLine();
        
        System.out.println("\nSelecciona tu raza:");
        System.out.println("1. Humano - Especialista en armas de fuego");
        System.out.println("2. Elfo - Maestro de la magia");
        System.out.println("3. Orco - Guerrero brutal");
        System.out.println("4. Bestia - Híbrido salvaje");
        System.out.print("Opción (1-4): ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        Personaje personaje = null;
        
        switch (opcion) {
            case 1:
                personaje = new Humano(nombrePersonaje, nombreJugador);
                break;
            case 2:
                personaje = new Elfo(nombrePersonaje, nombreJugador);
                break;
            case 3:
                personaje = new Orco(nombrePersonaje, nombreJugador);
                break;
            case 4:
                personaje = new Bestia(nombrePersonaje, nombreJugador);
                break;
            default:
                System.out.println("Opción inválida. Se asignará Humano por defecto.");
                personaje = new Humano(nombrePersonaje, nombreJugador);
        }
        
        // Seleccionar arma
        personaje.seleccionarArma();
        
        System.out.println("\n✓ Personaje creado: " + nombrePersonaje + " (" + personaje.getRaza() + ")");
        
        return personaje;
    }
    
    /**
     * Ejecuta el combate entre los dos jugadores
     */
    private void ejecutarCombate() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║         ¡COMIENZA EL COMBATE!             ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        // Posicionar jugadores
        jugador1.setPosicion(0);
        jugador2.setPosicion(3);
        
        boolean combateTerminado = false;
        
        while (!combateTerminado && turnoActual < 100) {
            System.out.println("\n" + "═".repeat(50));
            System.out.println("TURNO " + turnoActual);
            System.out.println("═".repeat(50));
            
            // Mostrar estado
            mostrarEstadoCombate();
            
            // Turno Jugador 1
            if (jugador1.estaVivo()) {
                System.out.println("\n--- Turno de " + jugador1.getNombre() + " ---");
                ejecutarTurno(jugador1, jugador2);
                aplicarEfectos(jugador1);
                
                if (!jugador2.estaVivo()) {
                    combateTerminado = true;
                    continue;
                }
            }
            
            // Turno Jugador 2
            if (jugador2.estaVivo() && !combateTerminado) {
                System.out.println("\n--- Turno de " + jugador2.getNombre() + " ---");
                ejecutarTurno(jugador2, jugador1);
                aplicarEfectos(jugador2);
                
                if (!jugador1.estaVivo()) {
                    combateTerminado = true;
                }
            }
            
            turnoActual++;
        }
        
        if (turnoActual >= 100) {
            System.out.println("\n¡El combate ha durado demasiado! Empate técnico.");
        }
    }
    
    /**
     * Ejecuta el turno de un jugador
     */
    private void ejecutarTurno(Personaje atacante, Personaje defensor) {
        System.out.println("\n¿Qué deseas hacer?");
        System.out.println("1. Atacar");
        System.out.println("2. Sanar");
        System.out.println("3. Avanzar");
        System.out.println("4. Retroceder");
        System.out.print("Opción: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        switch (opcion) {
            case 1:
                if (atacante.estaFrenteAFrente(defensor)) {
                    boolean aDistancia = atacante.estaADistancia(defensor);
                    atacante.atacar(defensor, aDistancia);
                } else {
                    System.out.println("¡Estás demasiado lejos para atacar!");
                }
                break;
            case 2:
                atacante.sanar();
                break;
            case 3:
                atacante.avanzar();
                break;
            case 4:
                atacante.retroceder();
                break;
            default:
                System.out.println("Opción inválida. Pierdes tu turno.");
        }
    }
    
    /**
     * Aplica efectos como sangrado y curación retrasada
     */
    private void aplicarEfectos(Personaje personaje) {
        // Aplicar sangrado
        personaje.aplicarSangrado();
        
        // Si es Orco, aplicar curación retrasada
        if (personaje instanceof Orco) {
            ((Orco) personaje).aplicarCuracionRetrasada();
        }
    }
    
    /**
     * Muestra el estado actual del combate
     */
    private void mostrarEstadoCombate() {
        jugador1.mostrarEstado();
        jugador2.mostrarEstado();
        
        // Mostrar distancia
        int distancia = Math.abs(jugador1.getPosicion() - jugador2.getPosicion());
        System.out.println("\n📏 Distancia entre combatientes: " + distancia);
        if (distancia <= 1) {
            System.out.println("⚔️ ¡Están frente a frente!");
        }
    }
    
    /**
     * Muestra el ganador del combate
     */
    private void mostrarGanador() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║           FIN DEL COMBATE                  ║");
        System.out.println("╠════════════════════════════════════════════╣");
        
        if (jugador1.estaVivo()) {
            System.out.println("║  🏆 ¡GANADOR: " + String.format("%-28s", jugador1.getNombre() + "!") + "║");
            System.out.println("║  Vida restante: " + String.format("%-27s", jugador1.getVidaActual() + "/" + jugador1.getVidaMaxima()) + "║");
        } else if (jugador2.estaVivo()) {
            System.out.println("║  🏆 ¡GANADOR: " + String.format("%-28s", jugador2.getNombre() + "!") + "║");
            System.out.println("║  Vida restante: " + String.format("%-27s", jugador2.getVidaActual() + "/" + jugador2.getVidaMaxima()) + "║");
        } else {
            System.out.println("║  ¡EMPATE! Ambos cayeron en combate        ║");
        }
        
        System.out.println("║  Turnos totales: " + String.format("%-26d", turnoActual) + "║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
    
    /**
     * Método main para ejecutar el juego
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean seguirJugando = true;
        
        while (seguirJugando) {
            JuegoBatalla juego = new JuegoBatalla();
            juego.iniciarJuego();
            
            System.out.print("\n¿Desean jugar otra partida? (S/N): ");
            String respuesta = scanner.nextLine().toUpperCase();
            
            if (!respuesta.equals("S")) {
                seguirJugando = false;
                System.out.println("\n¡Gracias por jugar! Hasta pronto.");
            }
        }
        
        scanner.close();
    }
}
