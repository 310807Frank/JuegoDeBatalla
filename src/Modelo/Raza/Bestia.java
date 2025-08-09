/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Raza;
import Modelo.Personaje;
import java.util.Scanner;
/**
 *
 * @author  Franklin Castillo
 */
public class Bestia extends Personaje {

    public Bestia(String nombre, String nombreJugador) {
        // Llamar al constructor de Personaje con 3 parámetros
        // El constructor de Personaje debe ser: Personaje(String nombre, String raza, int vidaMaxima)
        super(nombre, "Bestia", 100);
        
        // Después de crear el personaje, asignar el nombre del jugador
        this.setNombreJugador(nombreJugador);
    }
    
    @Override
    public String obtenerDescripcionRaza() {
        return "Bestias: Híbridos salvajes con fuerza animal y ferocidad.";
    }
    
    @Override
    public void seleccionarArma() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       SELECCIÓN DE ARMA - BESTIA      ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. 👊 Puños                            ║");
        System.out.println("║    • Daño fijo: 25                    ║");
        System.out.println("║    • ⚠ Pierdes 10 de vida al atacar  ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 2. ⚔️ Espada                           ║");
        System.out.println("║    • Daño: 1-10                       ║");
        System.out.println("║    • Ataque equilibrado               ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Selecciona (1-2): ");
        
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                armaSeleccionada = "Puños";
                idArma = 9; // ID para Puños
                break;
            case 2:
                armaSeleccionada = "Espada";
                idArma = 10; // ID para Espada
                break;
            default:
                armaSeleccionada = "Espada";
                idArma = 10;
                System.out.println("⚠ Opción inválida. Se seleccionó Espada por defecto.");
        }
        System.out.println("✓ Has seleccionado: " + armaSeleccionada);
    }
    
    @Override
    public int atacar(Personaje enemigo, boolean aDistancia) {
        int dano = 0;
        
        if (armaSeleccionada.equals("Puños")) {
            dano = 25; // Daño fijo
            System.out.println("👊 " + nombre + " ataca ferozmente con sus puños!");
            System.out.println("   → Daño devastador: " + dano);
            enemigo.recibirDanio(dano);
            
            // El atacante también pierde vida
            this.recibirDanio(10);
            System.out.println("   ⚠ " + nombre + " pierde 10 de vida por el esfuerzo!");
        } else if (armaSeleccionada.equals("Espada")) {
            dano = random.nextInt(10) + 1; // 1-10
            System.out.println("⚔️ " + nombre + " ataca con su espada!");
            System.out.println("   → Daño cortante: " + dano);
            enemigo.recibirDanio(dano);
        }
        
        return dano;
    }
    
    @Override
    public void sanar() {
        int curacion = (int)(vidaMaxima * 0.45); // 45% de vida
        vidaActual += curacion;
        if (vidaActual > vidaMaxima) {
            vidaActual = vidaMaxima;
        }
        System.out.println("😴 " + nombre + " duerme para recuperar fuerzas!");
        System.out.println("   → Recupera " + curacion + " puntos de vida");
    }
}
