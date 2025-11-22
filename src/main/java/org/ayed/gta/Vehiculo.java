package org.ayed.gta;

public abstract class Vehiculo {
    protected String nombre;
    protected String marca;
    protected int precio;
    protected int capacidadGasolina;
    protected int gasolinaActual;
    protected int kilometraje;
    protected int velocidadMaxima;
    
    
    public Vehiculo(String nombre,String marca, int precio, int capacidadGasolina, int velocidadMaxima) {
        if(nombre == null) {
            throw new IllegalArgumentException("Nombre no puede ser nulo");
        } 
        if(precio < 0 || capacidadGasolina < 0 || velocidadMaxima < 0) {
            throw new IllegalArgumentException("Precio, capacidad y velocidad no pueden ser negativos");
        }
        this.nombre = nombre;
        this.precio = precio;
        this.capacidadGasolina = capacidadGasolina;
        this.gasolinaActual = 0;
        this.kilometraje = 0;
        this.velocidadMaxima = velocidadMaxima;
        this.marca = marca;
    }
    
    
    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }
    
    public String getMarca() {
        return marca;
    }

    public int getCapacidadGasolina() {
        return capacidadGasolina;
    }
    
    public int getKilometraje(){
        return kilometraje;
    }
    
    public int getGasolinaActual(){
        return gasolinaActual;
    }

    public int getVelocidadMaxima(){
        return velocidadMaxima;
    }

    public int getCombustibleActual() {
        return gasolinaActual;
    }

    public int getCombustibleMax() {
        return capacidadGasolina;
    }


    public void setNombre(String nombre) {
        if(nombre == null) throw new IllegalArgumentException("Nombre no puede ser nulo");
        this.nombre = nombre;
    }

    public void setPrecio(int precio) {
        if(precio < 0) throw new IllegalArgumentException("Precio no puede ser negativo");
        this.precio = precio;
    }

    public void setCapacidadGasolina(int capacidadGasolina) {
        if(capacidadGasolina < 0) throw new IllegalArgumentException("Capacidad de gasolina no puede ser negativa");
        this.capacidadGasolina = capacidadGasolina;
    }
    
    public void cargarGasolina(int litros) {
        if(litros < 0) {
            throw new IllegalArgumentException("Litros no puede ser negativo");
        }
        int espacioDisponible = capacidadGasolina - gasolinaActual;
        if(litros > espacioDisponible) {
            throw new IllegalArgumentException("Supera la capacidad disponible. Capacidad disponible: " + capacidadGasolina);
        } else {
            gasolinaActual += litros;
        }
    }
    

    public abstract int calcularCostoMantenimiento();
    public abstract int getRuedas();
    
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", capacidadGasolina=" + capacidadGasolina +
                ", gasolinaActual=" + gasolinaActual +
                ", kilometraje=" + kilometraje +
                ", velocidadMaxima=" + velocidadMaxima +
                ",marca=" + marca +
                ", ruedas=" + getRuedas() +
                '}';
    }
    
    
    public String exportar() {
        return nombre + "," + marca + "," + precio + "," + getClass().getSimpleName() + "," + 
               getRuedas() + "," + capacidadGasolina + "," + velocidadMaxima + "," + kilometraje;
    }


    public void consumirGasolina(int cantidad){
        if(cantidad < 0){
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        if(getGasolinaActual() < cantidad){
            gasolinaActual = 0;
            throw new IllegalArgumentException("No hay suficiente gasolina");
        }
        gasolinaActual -= cantidad;
    }

    public void sumarKilometraje(int cantidad){
        if(cantidad < 0){
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        kilometraje += cantidad;
    }
}