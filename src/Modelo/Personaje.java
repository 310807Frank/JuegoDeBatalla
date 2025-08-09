/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.Random;
/**
 *
 * @author  Franklin Castillo
 */
public abstract class Personaje {
    protected int id;
    protected String nombre;
    protected String nombreJugador;
    protected String raza;
    protected int fuerza;
    protected int energia;
    protected int vidaActual;
    protected int vidaMaxima;
    protected String armaSeleccionada;
    protected int idArma;
    protected int posicion;
    protected boolean sangrando;
    protected int turnosSangrado;
    protected Random random;
    
    /**
     * Constructor principal con 3 parámetros
     * Este es el que usan las clases Humano, Elfo, Orco y Bestia originalmente
     */
    public Personaje(String nombre, String raza, int vidaMaxima) {
        this.nombre = nombre;
        this.raza = raza;
        this.vidaMaxima = vidaMaxima;
        this.vidaActual = vidaMaxima;
        this.nombreJugador = ""; // Se asignará después con el setter
        this.posicion = 0;
        this.fuerza = 10;
        this.energia = 100;
        this.sangrando = false;
        this.turnosSangrado = 0;
        this.random = new Random();
    }
    
    /**
     * Constructor alternativo con 4 parámetros
     * Este es el que necesitas si quieres pasar el nombreJugador desde el constructor
     */
    public Personaje(String nombre, String nombreJugador, String raza, int vidaMaxima) {
        this.nombre = nombre;
        this.nombreJugador = nombreJugador;
        this.raza = raza;
        this.vidaMaxima = vidaMaxima;
        this.vidaActual = vidaMaxima;
        this.posicion = 0;
        this.fuerza = 10;
        this.energia = 100;
        this.sangrando = false;
        this.turnosSangrado = 0;
        this.random = new Random();
    }
    
    // Métodos abstractos
    public abstract int atacar(Personaje enemigo, boolean aDistancia);
    public abstract void sanar();
    public abstract void seleccionarArma();
    public abstract String obtenerDescripcionRaza();
    
    /**
     * Recibe daño y actualiza la vida actual
     */
    public void recibirDanio(int cantidad) {
        this.vidaActual -= cantidad;
        if (this.vidaActual < 0) {
            this.vidaActual = 0;
        }
    }
    
    /**
     * Aplica el efecto de sangrado si está activo
     */
    public void aplicarSangrado() {
        if (sangrando && turnosSangrado > 0) {
            System.out.println("💉 " + nombre + " sufre 3 de daño por sangrado!");
            recibirDanio(3);
            turnosSangrado--;
            if (turnosSangrado == 0) {
                sangrando = false;
                System.out.println("✓ El sangrado de " + nombre + " ha parado.");
            }
        }
    }
    
    /**
     * Mueve al personaje hacia adelante
     */
    public void avanzar() {
        posicion++;
        System.out.println("→ " + nombre + " avanza. Posición actual: " + posicion);
    }
    
    /**
     * Mueve al personaje hacia atrás
     */
    public void retroceder() {
        if (posicion > 0) {
            posicion--;
            System.out.println("← " + nombre + " retrocede. Posición actual: " + posicion);
        } else {
            System.out.println("⚠ " + nombre + " no puede retroceder más.");
        }
    }
    
    /**
     * Verifica si está frente a frente con otro personaje
     */
    public boolean estaFrenteAFrente(Personaje otro) {
        return Math.abs(this.posicion - otro.posicion) <= 1;
    }
    
    /**
     * Verifica si está a distancia de otro personaje
     */
    public boolean estaADistancia(Personaje otro) {
        return Math.abs(this.posicion - otro.posicion) > 1;
    }
    
    /**
     * Verifica si el personaje está vivo
     */
    public boolean estaVivo() {
        return vidaActual > 0;
    }
    
    /**
     * Muestra el estado actual del personaje
     */
    public void mostrarEstado() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║ " + String.format("%-34s", nombre) + " ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ Raza: " + String.format("%-29s", raza) + "║");
        System.out.println("║ Vida: " + String.format("%-29s", vidaActual + "/" + vidaMaxima + " ❤") + "║");
        System.out.println("║ Arma: " + String.format("%-29s", armaSeleccionada != null ? armaSeleccionada : "Sin arma") + "║");
        System.out.println("║ Posición: " + String.format("%-25s", posicion) + "║");
        if (sangrando) {
            System.out.println("║ ⚠ SANGRANDO (Turnos: " + String.format("%-13s", turnosSangrado + ")") + "║");
        }
        System.out.println("╚════════════════════════════════════╝");
        
        // Barra de vida visual
        mostrarBarraVida();
    }
    
    /**
     * Muestra una barra de vida visual
     */
    private void mostrarBarraVida() {
        int porcentaje = (vidaActual * 20) / vidaMaxima;
        System.out.print("  [");
        for (int i = 0; i < 20; i++) {
            if (i < porcentaje) {
                System.out.print("█");
            } else {
                System.out.print("░");
            }
        }
        System.out.println("] " + vidaActual + "/" + vidaMaxima);
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getNombreJugador() { return nombreJugador; }
    public void setNombreJugador(String nombreJugador) { this.nombreJugador = nombreJugador; }
    
    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }
    
    public int getFuerza() { return fuerza; }
    public void setFuerza(int fuerza) { this.fuerza = fuerza; }
    
    public int getEnergia() { return energia; }
    public void setEnergia(int energia) { this.energia = energia; }
    
    public int getVidaActual() { return vidaActual; }
    public void setVidaActual(int vidaActual) { this.vidaActual = vidaActual; }
    
    public int getVidaMaxima() { return vidaMaxima; }
    public void setVidaMaxima(int vidaMaxima) { this.vidaMaxima = vidaMaxima; }
    
    public String getArmaSeleccionada() { return armaSeleccionada; }
    public void setArmaSeleccionada(String armaSeleccionada) { 
        this.armaSeleccionada = armaSeleccionada; 
    }
    
    public int getIdArma() { return idArma; }
    public void setIdArma(int idArma) { this.idArma = idArma; }
    
    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }
    
    public boolean isSangrando() { return sangrando; }
    public void setSangrando(boolean sangrando) { this.sangrando = sangrando; }
    
    public int getTurnosSangrado() { return turnosSangrado; }
    public void setTurnosSangrado(int turnosSangrado) { 
        this.turnosSangrado = turnosSangrado; 
    }
    
}
