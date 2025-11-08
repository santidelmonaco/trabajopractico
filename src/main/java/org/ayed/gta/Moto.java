/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ayed.gta;

/**
 *
 * @author delmo
 */
public class Moto extends Vehiculo {
    private static final int RUEDAS = 2;
    private static final int COSTO_RUEDA = 30;
    
    public Moto(String nombre,String marca, int precio, int capacidadGasolina, int velocidadMaxima) {
        super(nombre, marca, precio, capacidadGasolina, velocidadMaxima);
    }
    
    @Override
    public int calcularCostoMantenimiento() {
        int costoBase = RUEDAS * COSTO_RUEDA + capacidadGasolina;
        int costoPorKm = (int)(kilometraje / 500) * 10;
        return costoBase + costoPorKm;
    }
    
    @Override
    public int getRuedas() {
        return RUEDAS;
    }
}