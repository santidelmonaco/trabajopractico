package org.ayed.gta;

import org.ayed.tda.lista.Cola;
import org.ayed.tda.vector.Vector;
import java.io.*;
public class Garaje {
    
    private static final int CREDITOS_INICIALES = 0;
    private static final int CAPACIDAD_INICIAL = 5;
    private static final int COSTO_MEJORA = 50;
    
    private Vector<Vehiculo> vehiculos;
    private int creditos;
    private int capacidadMaxVehiculos;
    private Cola<Vehiculo> zonaDeEspera; 
      /**
     * Constructor de Garaje.
     * <pre>
     * Precondición: ninguna.
     * Postcondición: se crea un garaje vacío con 0 créditos
     * y capacidad máxima inicial de 5 vehículos.
     * </pre>
     */
    public Garaje() {
        this.vehiculos = new Vector<>(); // empieza vacío
        this.creditos = CREDITOS_INICIALES;
        this.capacidadMaxVehiculos = CAPACIDAD_INICIAL;
        this.zonaDeEspera = new Cola<>();
    }
    
     /**
     * Agrega un vehículo al garaje.
     * <pre>
     * Precondición:
     *   - El parámetro { vehiculo} no puede ser nulo.
     *   - La cantidad de vehículos actuales debe ser menor a la capacidad máxima.
     * Postcondición:
     *   - El vehículo es agregado al vector de vehículos.
     *   - En caso contrario, se lanza {ExcepcionGaraje}.
     * </pre>
     */
    public void agregarVehiculo(Vehiculo vehiculo) {
            if (vehiculo == null) {
            throw new ExcepcionGaraje("El vehículo no puede ser nulo");
        }

        if (vehiculos.tamanio() < capacidadMaxVehiculos) {
            vehiculos.agregar(vehiculo);
            System.out.println("Vehículo \"" + vehiculo.getNombre() + "\" agregado al garaje.");
        } 
        else {
            zonaDeEspera.agregar(vehiculo);
            System.out.println("Garaje lleno. Vehículo \"" + vehiculo.getNombre() + "\" agregado a la zona de espera.");
        }
    }

    /**
     * Elimina un vehículo del garaje por su nombre.
     * <pre>
     * Precondición:
     *   - El parámetro {nombre} no puede ser nulo.
     *   - Debe existir un vehículo con ese nombre en el garaje.
     * Postcondición:
     *   - El vehículo es eliminado del vector de vehículos.
     *   - Si no existe, se lanza {@link ExcepcionGaraje}.
     * </pre>
     * 
     * @param nombre nombre del vehículo a eliminar
     */
    public void eliminarVehiculo(String nombre) {
        int posicion = buscarVehiculo(nombre);
        if(posicion < 0){
            throw new ExcepcionGaraje("No existe un vehículo con el nombre \"" + nombre + "\"");
        }else{
            vehiculos.eliminar(posicion);
            System.out.println("vehiculo eliminado correctamente.");

            moverVehiculosDesdeZonaDeEspera();
        }
    }

       /**
     * Agrega créditos al garaje.
     * <pre>
     * Precondición:
     *   - { creditos} debe ser un valor mayor o igual a 0.
     * Postcondición:
     *   - Los créditos del garaje aumentan en la cantidad indicada.
     *   - Si el valor es negativo, se lanza {@link ExcepcionGaraje}.
     * </pre>
     * 
     * @param creditos cantidad de créditos a agregar
     */
    public void agregarCreditos(int creditos) {
        if(creditos < 0) {
            throw new ExcepcionGaraje("Los créditos no pueden ser negativos");
        }
        this.creditos += creditos;
    }

    /**
     * Mejora la capacidad máxima del garaje consumiendo créditos.
     * <pre>
     * Precondición:
     *   - El garaje debe tener al menos 50 créditos disponibles.
     * Postcondición:
     *   - Se restan 50 créditos.
     *   - La capacidad máxima aumenta en 1.
     *   - Si no hay créditos suficientes, se lanza {ExcepcionGaraje}.
     * </pre>
     */
    public void mejorarGaraje() {
        if (creditos < COSTO_MEJORA) {
             throw new ExcepcionGaraje("No hay créditos suficientes para mejorar el garaje (50 necesarios).");
        }

        creditos -= 50;
        capacidadMaxVehiculos+= 1; 
        System.out.println("Garaje mejorado. Capacidad máxima: " + capacidadMaxVehiculos);

        moverVehiculosDesdeZonaDeEspera();
    }
     /**
     * Obtiene el valor total de todos los vehículos en el garaje.
     * <pre>
     * Precondición: ninguna.
     * Postcondición:
     *   - Retorna la suma de los precios de todos los vehículos almacenados.
     * </pre>
     * 
     * @return valor total de los vehículos
     */

    public int obtenerValorTotal() {
        int total = 0;
        for(int i = 0; i < vehiculos.tamanio(); i++) {
            total += vehiculos.dato(i).getPrecio();
        }
        return total;
    }

     /**
     * Calcula el costo de mantenimiento total de todos los vehículos.
     * <pre>
     * Precondición: ninguna.
     * Postcondición:
     *   - Retorna el costo total calculado como:
     *     (costo de ruedas + costo de gasolina) de cada vehículo.
     * </pre>
     * 
     * @return costo total de mantenimiento
     */
       public int obtenerCostoMantenimiento() {
        int total = 0;
        for (int i = 0; i < vehiculos.tamanio(); i++) {
            total += vehiculos.dato(i).calcularCostoMantenimiento();
    }
        return total;
    }

    public Vector<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public int getCantidadVehiculos() {
        return vehiculos.tamanio();
    }

       /**
     * Devuelve la cantidad de créditos disponibles.
     * <pre>
     * Precondición: ninguna.
     * Postcondición: retorna los créditos actuales del garaje.
     * </pre>
     * 
     * @return créditos actuales
     */
    public int getCreditos() {
        return creditos;
    }   
       /**
     * Devuelve la capacidad máxima de vehículos.
     * <pre>
     * Precondición: ninguna.
     * Postcondición: retorna la capacidad máxima actual del garaje.
     * </pre>
     * 
     * @return capacidad máxima
     */
       public int getCapacidadMaxVehiculos() {
        return capacidadMaxVehiculos;
    }
      
    public void mostrarVehiculos(){
        if (vehiculos.vacio()) {
            System.out.println("No hay vehículos en el garaje.");
        } else {
            System.out.println("Vehículos en el garaje:");
            for (int i = 0; i < vehiculos.tamanio(); i++) {
                System.out.println(vehiculos.dato(i));
            }
        }
    }
    
    public int buscarVehiculo(String nombre){
        if (nombre == null) {
            throw new ExcepcionGaraje("El nombre no puede ser nulo");
        }
        
        int posicion = -1;
        int i = 0;
        boolean encontrado = false;
        
        while(i < vehiculos.tamanio() && !encontrado){
            Vehiculo aux = vehiculos.dato(i);
            if(aux.getNombre().equals(nombre)){
                posicion = i;
                encontrado = true;
            } else{i++;}
    }
        return posicion;
    }

     /**
     * Mueve vehículos desde la zona de espera al garaje mientras haya espacio disponible.
     * <p>
     * Se respeta el orden FIFO de la cola: los vehículos que llegaron primero ingresan primero.
     * Se llama después de eliminar un vehículo o mejorar la capacidad del garaje.
     * </p>
     * <pre>
     * Precondición: ninguna.
     * Postcondición: se agregan al garaje los vehículos en espera hasta llenar la capacidad.
     * </pre>
     */
    private void moverVehiculosDesdeZonaDeEspera() {
        while (vehiculos.tamanio() < capacidadMaxVehiculos && !zonaDeEspera.vacio()) {
            Vehiculo v = zonaDeEspera.eliminar(); 
            vehiculos.agregar(v);           
            System.out.println("Vehículo \"" + v.getNombre() + "\" movido desde la zona de espera al garaje.");
        }

        if (zonaDeEspera.vacio()) {
            System.out.println("No quedan vehículos en la zona de espera.");
        } else if (vehiculos.tamanio() >= capacidadMaxVehiculos) {
            System.out.println("Garaje lleno. Algunos vehículos permanecen en la zona de espera.");
        }
    }

    /**
     * Carga los tanques de todos los vehículos del garaje al máximo.
     * <p>
     * Para cada vehículo, calcula cuánto le falta y usa su método cargarGasolina() 
     * para llenar el tanque hasta su capacidad máxima.
     * </p>
     * Precondición: ninguna.
     * Postcondición: todos los vehículos del garaje tendrán su tanque lleno.
     */
    public void cargarTanquesMaximo() {
        for (int i = 0; i < vehiculos.tamanio(); i++) {
            Vehiculo v = vehiculos.dato(i);
            int faltante = v.getCapacidadGasolina() - v.getGasolinaActual();
            if(faltante > 0) {
                v.cargarGasolina(faltante);
                System.out.println("Tanque de \"" + v.getNombre() + "\" cargado al máximo.");
            }
        }
    }

    public Vehiculo asignarVehiculo(String nombre) {
        int posicion = buscarVehiculo(nombre);
        if(posicion < 0){
            throw new ExcepcionGaraje("No existe un vehículo con el nombre \"" + nombre + "\"");
        }else{
            return vehiculos.dato(posicion);
        }
    }

    public boolean guardarEnCSV(String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {

            bw.write("CREDITOS;" + this.creditos);
            bw.newLine();

            bw.write("CAPACIDAD;" + this.capacidadMaxVehiculos);
            bw.newLine();
            bw.newLine();

            bw.write("Tipo;Nombre;Marca;Precio;CapacidadGasolina;GasolinaActual;Kilometraje;VelocidadMaxima;Ruedas");
            bw.newLine();

            for (int i = 0; i < vehiculos.tamanio(); i++) {
            Vehiculo v = vehiculos.dato(i);

            bw.write(
                v.getClass().getSimpleName() + ";" +
                v.getNombre() + ";" +
                v.getMarca() + ";" +
                v.getPrecio() + ";" +
                v.getCapacidadGasolina() + ";" +
                v.getGasolinaActual() + ";" +
                v.getKilometraje() + ";" +
                v.getVelocidadMaxima() + ";" +
                v.getRuedas()
            );
            bw.newLine();
        }

        return true;
        } catch (Exception e) {
            System.out.println("Error al guardar garaje: " + e.getMessage());
            return false;
        }
    }

    
    public boolean cargarDesdeCSV(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {

            String linea;

            linea = br.readLine();
            if (linea != null && linea.startsWith("CREDITOS")) {
                this.creditos = Integer.parseInt(linea.split(";")[1]);
            }

            // Capacidad
            linea = br.readLine();
            if (linea != null && linea.startsWith("CAPACIDAD")) {
                this.capacidadMaxVehiculos = Integer.parseInt(linea.split(";")[1]);
            }

            br.readLine(); 
            br.readLine(); 

            this.vehiculos = new Vector<>();

            while ((linea = br.readLine()) != null) {

                String[] d = linea.split(";");

                String tipo = d[0];
                String nombre = d[1];
                String marca = d[2].equals("null") ? null : d[2];
                int precio = Integer.parseInt(d[3]);
                int capacidad = Integer.parseInt(d[4]);
                int gasolina = Integer.parseInt(d[5]);
                int km = Integer.parseInt(d[6]);
                int velMax = Integer.parseInt(d[7]);
                int ruedas = Integer.parseInt(d[8]);

                Vehiculo v;

                switch (tipo) {
                    case "Auto":
                        v = new Auto(nombre, marca, precio, capacidad, velMax);
                        break;
                    case "Moto":
                        v = new Moto(nombre, marca, precio, capacidad, velMax);
                        break;
                    case "Exotico":
                        v = new Exotico(nombre, precio, capacidad, velMax, ruedas);
                        break;
                    default:
                        System.out.println("Tipo desconocido: " + tipo);
                        continue;
                }

                v.cargarGasolina(gasolina);
                v.kilometraje = km;

                agregarVehiculo(v);
            }

            return true;

        } catch (Exception e) {
            System.out.println("Error al cargar garaje: " + e.getMessage());
            return false;
        }
    }

}
