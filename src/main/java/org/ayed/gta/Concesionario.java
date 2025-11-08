package org.ayed.gta;

public class Concesionario {
    private Vehiculo[] inventario;
    private int cantidad;
    private static final int CAPACIDAD_INICIAL = 10;
    private static final int FACTOR_CRECIMIENTO = 2;
    
    public Concesionario() {
        this.inventario = new Vehiculo[CAPACIDAD_INICIAL];
        this.cantidad = 0;
    }
    
    /**
     * Expande la capacidad del arreglo automáticamente
     * Se duplica el tamaño cada vez que se llena
     */
    private void expandirCapacidad() {
        int nuevaCapacidad = inventario.length * FACTOR_CRECIMIENTO;
        Vehiculo[] nuevoInventario = new Vehiculo[nuevaCapacidad];
        
        for(int i = 0; i < cantidad; i++) {
            nuevoInventario[i] = inventario[i];
        }
        
        inventario = nuevoInventario;
    }
    
    /**
     * Agrega un vehículo al inventario del concesionario
     * No permite agregar vehículos exóticos
     * El inventario crece automáticamente sin límite
     */
    public void agregarVehiculo(Vehiculo vehiculo) {
        if(vehiculo == null) {
            throw new IllegalArgumentException("Vehículo no puede ser nulo");
        }
        // Expandir automáticamente si es necesario
        if(cantidad == inventario.length) {
            expandirCapacidad();
        }
        
        inventario[cantidad] = vehiculo;
        cantidad++;
    }
    
    /**
     * Agrega múltiples vehículos al inventario
     */
    public void agregarVehiculos(Vehiculo[] vehiculos) { 
        for(int i = 0; i < vehiculos.length; i++) {
            if(vehiculos[i] != null) {
                agregarVehiculo(vehiculos[i]);
            }
        }
    }
    
    /**
     * Busca vehículos por nombre (búsqueda parcial, case-insensitive)
     * @param nombreParcial Parte del nombre a buscar
     * @return Arreglo de vehículos que coinciden con la búsqueda
     */
    public Vehiculo[] buscarPorNombre(String nombreParcial) {
        if(nombreParcial == null || nombreParcial.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de búsqueda no puede ser nulo o vacío");
        }
        
        // Primer recorrido: contar coincidencias
        String busqueda = nombreParcial.toLowerCase().trim();
        int coincidencias = 0;
        
        for(int i = 0; i < cantidad; i++) {
            if(inventario[i].getNombre().toLowerCase().contains(busqueda)) {
                coincidencias++;
            }
        }
        
        // Segundo recorrido: llenar arreglo de resultados
        Vehiculo[] resultados = new Vehiculo[coincidencias];
        int indiceResultado = 0;
        
        for(int i = 0; i < cantidad; i++) {
            if(inventario[i].getNombre().toLowerCase().contains(busqueda)) {
                resultados[indiceResultado] = inventario[i];
                indiceResultado++;
            }
        }
        
        return resultados;
    }
    
    /**
     * Busca vehículos por marca (búsqueda parcial, case-insensitive)
     * @param marcaParcial Parte de la marca a buscar
     * @return Arreglo de vehículos que coinciden con la búsqueda
     */
    public Vehiculo[] buscarPorMarca(String marcaParcial) {
        if(marcaParcial == null || marcaParcial.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca de búsqueda no puede ser nula o vacía");
        }
        
        // Primer recorrido: contar coincidencias
        String busqueda = marcaParcial.toLowerCase().trim();
        int coincidencias = 0;
        
        for(int i = 0; i < cantidad; i++) {
            if(inventario[i].getMarca().toLowerCase().contains(busqueda)) {
                coincidencias++;
            }
        }
        
        // Segundo recorrido: llenar arreglo de resultados
        Vehiculo[] resultados = new Vehiculo[coincidencias];
        int indiceResultado = 0;
        
        for(int i = 0; i < cantidad; i++) {
            if(inventario[i].getMarca().toLowerCase().contains(busqueda)) {
                resultados[indiceResultado] = inventario[i];
                indiceResultado++;
            }
        }
        
        return resultados;
    }
    
    /**
     * Compra un vehículo por nombre exacto
     * @param nombreExacto Nombre exacto del vehículo a comprar
     * @return El vehículo comprado, o null si no se encuentra
     */
    public Vehiculo comprarVehiculo(String nombreExacto) {
        if(nombreExacto == null || nombreExacto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        
        for(int i = 0; i < cantidad; i++) {
            if(inventario[i].getNombre().equalsIgnoreCase(nombreExacto.trim())) {
                Vehiculo vehiculoComprado = inventario[i];
                
                // Desplazar elementos hacia la izquierda para eliminar el vehículo
                for(int j = i; j < cantidad - 1; j++) {
                    inventario[j] = inventario[j + 1];
                }
                
                inventario[cantidad - 1] = null; // Limpiar última referencia
                cantidad--;
                
                return vehiculoComprado;
            }
        }
        
        return null; // No se encontró el vehículo
    }

    public void pasarVehiculoAlGaraje(String nombreExacto, Garaje garaje) {
        Vehiculo vehiculo = comprarVehiculo(nombreExacto);
        if (vehiculo != null) {
            garaje.agregarVehiculo(vehiculo);
            System.out.println("Vehículo \"" + vehiculo.getNombre() + "\" comprado y enviado al garaje.");
        } else {
            System.out.println("No se encontró un vehículo con el nombre \"" + nombreExacto + "\" en el concesionario.");
        }
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CONCESIONARIO ===\n");             
        for(int i = 0; i < cantidad; i++) {
            Vehiculo v = inventario[i];
            sb.append(String.format("%-20s | Marca: %-15s | Precio: $%-8d | Tipo: %s\n", 
                v.getNombre(), 
                v.getMarca(), 
                v.getPrecio(),
                v.getClass().getSimpleName()));
        }
        return sb.toString();
    }
}