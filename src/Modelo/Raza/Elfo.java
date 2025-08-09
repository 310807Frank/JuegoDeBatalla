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
 * @author Franklin Castillo
 */
public class Elfo extends Personaje{
     private String tipoMagia;
    private double porcentajeSanacion;
    
    public Elfo(String nombre, String nombreJugador) {
        super(nombre, nombreJugador, "Elfo", Constantes.VIDA_ELFO);
        porcentajeSanacion = 0.75; // 75% por defecto
    }
    
    @Override
    public String obtenerDescripcionRaza() {
        return "Elfos: Maestros de la magia elemental, gráciles y sabios.";
    }
    
    @Override
    public void seleccionarArma() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      SELECCIÓN DE MAGIA - ELFO        ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. 🔥 Magia de Fuego                   ║");
        System.out.println("║    • Daño: 1-5 (+10% adicional)       ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 2. 🌍 Magia de Tierra                  ║");
        System.out.println("║    • Daño: 1-5 (+2%, más precisión)   ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 3. 💨 Magia de Aire                    ║");
        System.out.println("║    • Daño: 1-5 (a distancia: 4-12)    ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 4. 💧 Magia de Agua                    ║");
        System.out.println("║    • Daño: 1-5                        ║");
        System.out.println("║    • Vida inicial: 115                ║");
        System.out.println("║    • Sanación: 90%                    ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Selecciona (1-4): ");
        
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                tipoMagia = "Fuego";
                armaSeleccionada = "Báculo de Fuego";
                idArma = Constantes.ID_BACULO_FUEGO;
                break;
            case 2:
                tipoMagia = "Tierra";
                armaSeleccionada = "Báculo de Tierra";
                idArma = Constantes.ID_BACULO_TIERRA;
                break;
            case 3:
                tipoMagia = "Aire";
                armaSeleccionada = "Báculo de Aire";
                idArma = Constantes.ID_BACULO_AIRE;
                break;
            case 4:
                tipoMagia = "Agua";
                armaSeleccionada = "Báculo de Agua";
                idArma = Constantes.ID_BACULO_AGUA;
                vidaMaxima = Constantes.VIDA_ELFO_AGUA;
                vidaActual = Constantes.VIDA_ELFO_AGUA;
                porcentajeSanacion = 0.90; // 90% para agua
                break;
            default:
                tipoMagia = "Fuego";
                armaSeleccionada = "Báculo de Fuego";
                idArma = Constantes.ID_BACULO_FUEGO;
                System.out.println("⚠ Opción inválida. Se seleccionó Fuego por defecto.");
        }
        System.out.println("✓ Has seleccionado magia de: " + tipoMagia);
    }
    
    @Override
    public int atacar(Personaje enemigo, boolean aDistancia) {
        int dano = 0;
        
        switch (tipoMagia) {
            case "Fuego":
                dano = random.nextInt(5) + 1;
                dano = (int)(dano * 1.10); // +10% de daño
                System.out.println("🔥 " + nombre + " lanza una bola de fuego!");
                System.out.println("   → Daño ardiente: " + dano);
                break;
                
            case "Tierra":
                if (random.nextDouble() < 0.85) { // 85% de acierto
                    dano = random.nextInt(5) + 1;
                    dano = (int)(dano * 1.02); // +2% de daño
                    System.out.println("🌍 " + nombre + " invoca espinas de tierra!");
                    System.out.println("   → Daño telúrico: " + dano);
                } else {
                    System.out.println("✗ " + nombre + " falla el hechizo de tierra!");
                }
                break;
                
            case "Aire":
                if (aDistancia) {
                    dano = random.nextInt(9) + 4; // 4-12 a distancia
                    System.out.println("💨 " + nombre + " lanza un tornado devastador!");
                    System.out.println("   → Daño masivo: " + dano);
                } else {
                    dano = random.nextInt(5) + 1; // 1-5 normal
                    System.out.println("💨 " + nombre + " lanza una ráfaga de viento!");
                    System.out.println("   → Daño: " + dano);
                }
                break;
                
            case "Agua":
                dano = random.nextInt(5) + 1;
                System.out.println("💧 " + nombre + " lanza un torrente de agua!");
                System.out.println("   → Daño acuático: " + dano);
                break;
        }
        
        if (dano > 0) {
            enemigo.recibirDanio(dano);
        }
        return dano;
    }
    
    @Override
    public void sanar() {
        int curacion = (int)(vidaMaxima * porcentajeSanacion);
        vidaActual += curacion;
        if (vidaActual > vidaMaxima) {
            vidaActual = vidaMaxima;
        }
        System.out.println("✨ " + nombre + " invoca un hechizo de sanación!");
        System.out.println("   → Recupera " + curacion + " puntos de vida");
    }
}
