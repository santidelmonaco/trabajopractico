/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ayed.gta;

/**
 *
 * @author delmo
 */
public class CatalogoVehiculos {
    
    /**
     * Crea un catálogo de vehículos predefinidos para el concesionario
     */
    public static Vehiculo[] crearCatalogoAutos() {
        Vehiculo[] catalogo = new Vehiculo[11];
        int indice = 0;
        
        // Autos Peugeot
        catalogo[indice++] = new Auto("Peugeot 306", "Peugeot", 8000, 60, 180);
        catalogo[indice++] = new Auto("Peugeot 307", "Peugeot", 12000, 60, 190);
        catalogo[indice++] = new Auto("Peugeot 308", "Peugeot", 15000, 55, 200);
        
        // Autos Lamborghini
        catalogo[indice++] = new Auto("Lamborghini Gallardo", "Lamborghini", 180000, 90, 325);
        catalogo[indice++] = new Auto("Lamborghini Aventador", "Lamborghini", 400000, 90, 350);
        catalogo[indice++] = new Auto("Lamborghini Huracán", "Lamborghini", 260000, 83, 325);
        
        // Autos Ferrari
        catalogo[indice++] = new Auto("Ferrari F430", "Ferrari", 200000, 95, 315);
        catalogo[indice++] = new Auto("Ferrari 458 Italia", "Ferrari", 240000, 86, 325);
        
        // Autos económicos
        catalogo[indice++] = new Auto("Toyota Corolla", "Toyota", 18000, 50, 180);
        catalogo[indice++] = new Auto("Honda Civic", "Honda", 20000, 47, 190);
        catalogo[indice++] = new Auto("Ford Focus", "Ford", 17000, 52, 185);
        
        return catalogo;
    }
    
    /**
     * Crea un catálogo de motos predefinidas para el concesionario
     */
    public static Vehiculo[] crearCatalogoMotos() {
        Vehiculo[] catalogo = new Vehiculo[7];
        int indice = 0;
        
        // Motos deportivas
        catalogo[indice++] = new Moto("Kawasaki Ninja", "Kawasaki", 12000, 17, 280);
        catalogo[indice++] = new Moto("Yamaha R1", "Yamaha", 15000, 17, 290);
        catalogo[indice++] = new Moto("Honda CBR1000RR", "Honda", 14000, 16, 285);
        
        // Motos cruiser
        catalogo[indice++] = new Moto("Harley Davidson Fat Boy", "Harley Davidson", 18000, 19, 180);
        catalogo[indice++] = new Moto("Indian Chief", "Indian", 19000, 20, 170);
        
        // Motos urbanas
        catalogo[indice++] = new Moto("Honda PCX", "Honda", 3000, 8, 90);
        catalogo[indice++] = new Moto("Yamaha NMAX", "Yamaha", 3200, 7, 95);
        
        return catalogo;
    }
    public static Exotico crearVehiculoExotico() {
        return new Exotico(
            "Zentolla",   
            500000,
            60,             
            350,             
            4               
        );
    }

    /**
     * Crea un concesionario con inventario completo
     */
    public static Concesionario crearConcesionarioCompleto() {
        Concesionario concesionario = new Concesionario();
        
        // Agregar todos los autos
        Vehiculo[] autos = crearCatalogoAutos();
        for(int i = 0; i < autos.length; i++) {
            concesionario.agregarVehiculo(autos[i]);
        }
        
        // Agregar todas las motos
        Vehiculo[] motos = crearCatalogoMotos();
        for(int i = 0; i < motos.length; i++) {
            concesionario.agregarVehiculo(motos[i]);
        }
        
        return concesionario;
    }
}