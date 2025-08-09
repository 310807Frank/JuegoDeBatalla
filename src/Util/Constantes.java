/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

/**
 *
 * @author  Franklin Castillo
 */
public class Constantes {
    // Vida inicial por raza
    public static final int VIDA_HUMANO = 100;
    public static final int VIDA_ELFO = 100;
    public static final int VIDA_ELFO_AGUA = 115;
    public static final int VIDA_ORCO = 100;
    public static final int VIDA_BESTIA = 10;
    
    // IDs de armas (deben coincidir con la BD)
    public static final int ID_ESCOPETA = 1;
    public static final int ID_RIFLE = 2;
    public static final int ID_BACULO_FUEGO = 3;
    public static final int ID_BACULO_TIERRA = 4;
    public static final int ID_BACULO_AIRE = 5;
    public static final int ID_BACULO_AGUA = 6;
    public static final int ID_HACHA = 7;
    public static final int ID_MARTILLO = 8;
    public static final int ID_PUNOS = 9;
    public static final int ID_ESPADA = 10;
    
    // Configuración del juego
    public static final int MAX_TURNOS = 100;
    public static final int POSICION_INICIAL_J1 = 0;
    public static final int POSICION_INICIAL_J2 = 3;
    
    // Daño por efectos
    public static final int DANO_SANGRADO = 3;
    public static final int TURNOS_SANGRADO = 2;
    
    // Porcentajes de sanación
    public static final double SANACION_HUMANO = 0.50;
    public static final double SANACION_ELFO = 0.75;
    public static final double SANACION_ELFO_AGUA = 0.90;
    public static final double SANACION_ORCO_INMEDIATA = 0.25;
    public static final double SANACION_ORCO_RETRASADA = 0.15;
    public static final double SANACION_BESTIA = 0.45;
    
    // Mensajes del sistema
    public static final String TITULO_JUEGO = "BATALLA POR TURNOS";
    public static final String VERSION = "1.0.0";
    public static final String DESARROLLADORES = "Equipo de Desarrollo Java";
}
