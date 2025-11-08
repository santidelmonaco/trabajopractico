/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ayed.gta;

/**
 *
 * @author delmo
 */
public class Exotico extends Vehiculo {
    private int ruedas;
    
    public Exotico(String nombre, int precio, int capacidadGasolina,  int velocidadMaxima, int ruedas) {
        super(nombre, precio, capacidadGasolina, velocidadMaxima);
        if(ruedas <= 0) {
            throw new IllegalArgumentException("Ruedas debe ser mayor a 0");
        }
        this.ruedas = ruedas;
    }
    
    @Override
    public int calcularCostoMantenimiento() {
        // Costo de ruedas: 10 + 20 + 30 + ... + (ruedas * 10)
        int costoRuedas = 0;
        for(int i = 1; i <= ruedas; i++) {
            costoRuedas += i * 10;
        }
        // Costo base + 100 fijo (no aumenta por kilometraje)
        return costoRuedas + capacidadGasolina + 100;
    }
    
    @Override
    public int getRuedas() {
        return ruedas;
    }
    
    public void setRuedas(int ruedas) {
        if(ruedas <= 0) {
            throw new IllegalArgumentException("Ruedas debe ser mayor a 0");
        }
        this.ruedas = ruedas;
    }
}