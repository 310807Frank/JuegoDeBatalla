/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Raza;
import Modelo.Personaje;
import Modelo.Personaje;
import Util.Constantes;
import java.util.Scanner;
/**
 *
 * @author  Franklin Castillo
 */
public class Orco extends Personaje{
    private boolean curacionRetrasada = false;
    private int curacionPendiente = 0;
    
    /**
     * Constructor del Orco
     * @param nombre Nombre del personaje
     * @param nombreJugador Nombre del jugador
     */
    public Orco(String nombre, String nombreJugador) {
        super(nombre, nombreJugador, "Orco", Constantes.VIDA_ORCO);
    }
    
    /**
     * Constructor alternativo con todos los parámetros
     */
    public Orco(String nombre, String nombreJugador, String raza, int vidaMaxima) {
        super(nombre, nombreJugador, raza, vidaMaxima);
    }
    
    @Override
    public String obtenerDescripcionRaza() {
        return "Orcos: Guerreros brutales, fuertes y resistentes al daño. " +
               "Sus hachas causan sangrado y pueden usar pociones de curación con efecto retardado.";
    }
    
    @Override
    public void seleccionarArma() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        SELECCIÓN DE ARMA - ORCO       ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. 🪓 Hacha                            ║");
        System.out.println("║    • Daño: 1-5                        ║");
        System.out.println("║    • Causa sangrado (2 turnos)        ║");
        System.out.println("║    • -3 vida/turno al enemigo         ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 2. 🔨 Martillo                         ║");
        System.out.println("║    • Daño: 1-5                        ║");
        System.out.println("║    • Golpe contundente                ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Selecciona (1-2): ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        switch (opcion) {
            case 1:
                armaSeleccionada = "Hacha";
                idArma = Constantes.ID_HACHA;
                System.out.println("✓ Has seleccionado: Hacha");
                System.out.println("  Especial: Causa sangrado durante 2 turnos");
                break;
            case 2:
                armaSeleccionada = "Martillo";
                idArma = Constantes.ID_MARTILLO;
                System.out.println("✓ Has seleccionado: Martillo");
                System.out.println("  Ataque contundente estándar");
                break;
            default:
                armaSeleccionada = "Hacha";
                idArma = Constantes.ID_HACHA;
                System.out.println("⚠ Opción inválida. Se seleccionó Hacha por defecto.");
        }
    }
    
    @Override
    public int atacar(Personaje enemigo, boolean aDistancia) {
        int dano = random.nextInt(5) + 1; // Daño base 1-5
        
        if (armaSeleccionada.equals("Hacha")) {
            System.out.println("\n🪓 " + nombre + " ataca salvajemente con el Hacha!");
            System.out.println("   → Daño base: " + dano);
            
            // Aplicar daño
            enemigo.recibirDanio(dano);
            
            // Aplicar efecto de sangrado
            if (!enemigo.isSangrando()) {
                enemigo.setSangrando(true);
                enemigo.setTurnosSangrado(2);
                System.out.println("   💉 ¡El enemigo comienza a sangrar!");
                System.out.println("      (Perderá 3 de vida por turno durante 2 turnos)");
            } else {
                System.out.println("   💉 ¡El enemigo ya está sangrando!");
            }
            
        } else if (armaSeleccionada.equals("Martillo")) {
            System.out.println("\n🔨 " + nombre + " golpea con fuerza brutal usando el Martillo!");
            System.out.println("   → Daño contundente: " + dano);
            enemigo.recibirDanio(dano);
        }
        
        // Mostrar vida restante del enemigo
        System.out.println("   ❤ Vida del enemigo: " + enemigo.getVidaActual() + "/" + enemigo.getVidaMaxima());
        
        return dano;
    }
    
    @Override
    public void sanar() {
        // Curación inmediata: 25% de la vida máxima
        int curacionInmediata = (int)(vidaMaxima * 0.25);
        int vidaAnterior = vidaActual;
        
        vidaActual += curacionInmediata;
        if (vidaActual > vidaMaxima) {
            vidaActual = vidaMaxima;
        }
        
        // Guardar curación pendiente para el siguiente turno (15% adicional)
        curacionPendiente = (int)(vidaMaxima * 0.15);
        curacionRetrasada = true;
        
        System.out.println("\n🧪 " + nombre + " bebe una poción de curación órquica!");
        System.out.println("   → Curación inmediata: +" + (vidaActual - vidaAnterior) + " puntos de vida");
        System.out.println("   → Vida actual: " + vidaActual + "/" + vidaMaxima);
        System.out.println("   ⏱ Efecto retardado: +" + curacionPendiente + " puntos el próximo turno");
    }
    
    /**
     * Aplica la curación retrasada del orco
     * Este método debe ser llamado al inicio del turno del orco
     */
    public void aplicarCuracionRetrasada() {
        if (curacionRetrasada && curacionPendiente > 0) {
            int vidaAnterior = vidaActual;
            vidaActual += curacionPendiente;
            
            if (vidaActual > vidaMaxima) {
                vidaActual = vidaMaxima;
            }
            
            System.out.println("\n💚 Efecto retardado de la poción:");
            System.out.println("   " + nombre + " recupera " + (vidaActual - vidaAnterior) + " puntos de vida adicionales!");
            System.out.println("   → Vida actual: " + vidaActual + "/" + vidaMaxima);
            
            // Resetear las variables
            curacionRetrasada = false;
            curacionPendiente = 0;
        }
    }
    
    /**
     * Verifica si tiene curación pendiente
     * @return true si hay curación retrasada pendiente
     */
    public boolean tieneCuracionPendiente() {
        return curacionRetrasada && curacionPendiente > 0;
    }
    
    /**
     * Obtiene la cantidad de curación pendiente
     * @return cantidad de puntos de vida pendientes de curar
     */
    public int getCuracionPendiente() {
        return curacionPendiente;
    }
    
    @Override
    public void mostrarEstado() {
        super.mostrarEstado(); // Llamar al método de la clase padre
        
        // Mostrar información adicional específica del Orco
        if (curacionRetrasada && curacionPendiente > 0) {
            System.out.println("  💚 Curación pendiente: +" + curacionPendiente + " (próximo turno)");
        }
    }
}
