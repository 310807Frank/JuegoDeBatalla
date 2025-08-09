/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Raza;
import Modelo.Personaje;
import Util.Constantes;
import java.util.Scanner;
/**
 *
 * @author  Franklin Castillo
 */
public class Humano extends Personaje{
    private int danoRecibidoTotal = 0;
    
    public Humano(String nombre, String nombreJugador) {
        super(nombre, nombreJugador, "Humano", Constantes.VIDA_HUMANO);
    }
    
    @Override
    public String obtenerDescripcionRaza() {
        return "Humanos: Expertos en armas de fuego, versátiles y resistentes.";
    }
    
    @Override
    public void seleccionarArma() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        SELECCIÓN DE ARMA - HUMANO      ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. 🔫 Escopeta                         ║");
        System.out.println("║    • Daño: 1-5 (+2% adicional)        ║");
        System.out.println("║    • Corto alcance                    ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 2. 🎯 Rifle Francotirador              ║");
        System.out.println("║    • Daño: 1-5 (normal)                ║");
        System.out.println("║    • A distancia: 5-10 daño           ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Selecciona (1-2): ");
        
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                armaSeleccionada = "Escopeta";
                idArma = Constantes.ID_ESCOPETA;
                break;
            case 2:
                armaSeleccionada = "Rifle Francotirador";
                idArma = Constantes.ID_RIFLE;
                break;
            default:
                armaSeleccionada = "Escopeta";
                idArma = Constantes.ID_ESCOPETA;
                System.out.println("⚠ Opción inválida. Se seleccionó Escopeta por defecto.");
        }
        System.out.println("✓ Has seleccionado: " + armaSeleccionada);
    }
    @Override
    public int atacar(Personaje enemigo, boolean aDistancia) {
        int dano = 0;
        
        if (armaSeleccionada.equals("Escopeta")) {
            dano = random.nextInt(5) + 1;
            dano = (int)(dano * 1.02); // +2% de daño
            System.out.println("💥 " + nombre + " dispara con Escopeta!");
            System.out.println("   → Daño causado: " + dano);
        } else if (armaSeleccionada.equals("Rifle Francotirador")) {
            if (aDistancia) {
                dano = random.nextInt(6) + 5; // 5-10 a distancia
                System.out.println("🎯 " + nombre + " dispara a distancia con precisión!");
                System.out.println("   → Daño crítico: " + dano);
            } else {
                dano = random.nextInt(5) + 1; // 1-5 normal
                System.out.println("🔫 " + nombre + " dispara con Rifle Francotirador!");
                System.out.println("   → Daño causado: " + dano);
            }
        }
        
        enemigo.recibirDanio(dano);
        return dano;
    }
    
    @Override
    public void sanar() {
        int curacion = danoRecibidoTotal / 2; // 50% del daño recibido
        vidaActual += curacion;
        if (vidaActual > vidaMaxima) {
            vidaActual = vidaMaxima;
        }
        System.out.println("🍔 " + nombre + " come para recuperar energías!");
        System.out.println("   → Recupera " + curacion + " puntos de vida");
        danoRecibidoTotal = 0;
    }
    
    @Override
    public void recibirDanio(int cantidad) {
        super.recibirDanio(cantidad);
        danoRecibidoTotal += cantidad;
    }
}
