/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import DAO.ArmaDAO;
import DAO.JugadorDAO;
import DAO.PartidaDAO;
import DAO.PersonajeDAO;

/**
 *
 * @author  Franklin Castillo
 */
public class MenuUtil {
    private static final JugadorDAO jugadorDAO = new JugadorDAO();
    private static PersonajeDAO personajeDAO = new PersonajeDAO();
    private static ArmaDAO armaDAO = new ArmaDAO();
    private static PartidaDAO partidaDAO = new PartidaDAO();
    
    /**
     * Muestra el menú principal del juego
     */
    public static void mostrarMenuPrincipal() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║         " + Constantes.TITULO_JUEGO + " v" + Constantes.VERSION + "         ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║                MENÚ PRINCIPAL                 ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║  1. 🎮 Nueva Partida                          ║");
        System.out.println("║  2. 📊 Ver Estadísticas                       ║");
        System.out.println("║  3. 🏆 Ver Ranking                            ║");
        System.out.println("║  4. 🚪 Salir                                  ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.print("Selecciona una opción (1-4): ");
    }
    
    /**
     * Muestra las estadísticas generales del juego
     */
    public static void mostrarEstadisticasGenerales() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║           ESTADÍSTICAS GENERALES              ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        
        // Mostrar estadísticas de razas
        System.out.println("\n📈 USO DE RAZAS:");
        System.out.println("─────────────────");
        for (String stat : personajeDAO.obtenerEstadisticasRazas()) {
            System.out.println("  • " + stat);
        }
        
        // Mostrar estadísticas de armas
        System.out.println("\n⚔️ USO DE ARMAS:");
        System.out.println("─────────────────");
        armaDAO.mostrarEstadisticasArmas();
        
        // Mostrar historial de partidas
        System.out.println("\n📜 PARTIDAS RECIENTES:");
        System.out.println("──────────────────────");
        partidaDAO.mostrarHistorialPartidas(5);
        
        System.out.println("\nPresiona Enter para continuar...");
    }
    
    /**
     * Muestra el ranking de jugadores
     */
    public static void mostrarRanking() {
        jugadorDAO.mostrarRanking();
        System.out.println("\nPresiona Enter para continuar...");
    }
    
    /**
     * Muestra el banner de bienvenida
     */
    public static void mostrarBienvenida() {
        System.out.println("\n" + "═".repeat(55));
        System.out.println(" ".repeat(10) + "⚔️  BATALLA POR TURNOS  ⚔️");
        System.out.println(" ".repeat(15) + "Versión " + Constantes.VERSION);
        System.out.println("═".repeat(55));
        System.out.println("    Un juego de combate estratégico por turnos");
        System.out.println("       Desarrollado en Java - Modo Consola");
        System.out.println("═".repeat(55) + "\n");
    }
    
    /**
     * Muestra las instrucciones del juego
     */
    public static void mostrarInstrucciones() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║              CÓMO JUGAR                       ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║ 1. Cada jugador selecciona una raza          ║");
        System.out.println("║ 2. Elige un arma según tu raza               ║");
        System.out.println("║ 3. Los turnos se alternan entre jugadores    ║");
        System.out.println("║ 4. En tu turno puedes:                       ║");
        System.out.println("║    • Atacar (si estás cerca)                 ║");
        System.out.println("║    • Sanar                                   ║");
        System.out.println("║    • Moverte (avanzar/retroceder)            ║");
        System.out.println("║ 5. Gana quien deje al oponente sin vida      ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
    }
}
