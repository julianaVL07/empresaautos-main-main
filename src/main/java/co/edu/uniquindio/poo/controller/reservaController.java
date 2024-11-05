package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.Camioneta;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Empresa;
import co.edu.uniquindio.poo.model.ICosto;
import co.edu.uniquindio.poo.model.Moto;
import co.edu.uniquindio.poo.model.Reserva;
import co.edu.uniquindio.poo.model.TipoCaja;
import co.edu.uniquindio.poo.model.Vehiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList; //Listas dinámicas en la interfaz
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;



public class reservaController {

    //controlador que maneja las reservas de vehiculos en la aplicacion, maneja tambien el CRUD

    private Empresa empresa; //Contiene los datos de clientes,vehiculos y reservas
    private ObservableList<Reserva> reservas; //lista observable de reservas que se actualizará en tiempo real en la interfaz cuando haya cambios

    public ObservableList<Reserva> getReservas() {
        return reservas;
    }

    public reservaController() {
        this.empresa = new Empresa("Mi Empresa");
        this.reservas = FXCollections.observableArrayList();
    }

    @FXML
    private ComboBox<Cliente> txtListaClientes;
    @FXML
    private ComboBox<Vehiculo> txtListaVehiculo;


    // Cargar clientes y vehículos (asumiendo que se cargan de forma estática)
    public void cargarDatos(ObservableList<Cliente> clientes, ObservableList<Vehiculo> vehiculos) {
        // Aquí puedes cargar los datos desde una base de datos, un archivo o dejarlos quemados.
        // Ejemplo para cargar a la vista
        for (Cliente cliente : clientes) {
            empresa.agregarCliente(cliente);
        }
        for (Vehiculo vehiculo : vehiculos) {
            empresa.agregarVehiculo(vehiculo);
        }
    }

    public boolean crearReserva(int dias, Cliente cliente, Vehiculo vehiculo) {
        if (cliente != null && vehiculo != null) {
            double costo = calcularCosto(vehiculo) * dias; // Calcula el costo total basado en los días
            Reserva nuevaReserva = new Reserva(dias, "Reserva " + reservas.size(), cliente, vehiculo);
            nuevaReserva.setCosto(costo); // Asigna el costo calculado a la reserva
            if (empresa.agregarReserva(nuevaReserva)) {
                reservas.add(nuevaReserva);
                return true; // Reserva creada exitosamente
            }
        }
        return false; // Falló la creación de la reserva
    }
    
    public boolean actualizarReserva(Reserva reserva, int dias, Cliente cliente, Vehiculo vehiculo) {
        if (reserva == null) {
            return false; // No se puede actualizar si la reserva es nula
        }
    
        // Actualiza los campos de la reserva
        reserva.setDiasReserva(dias);
        reserva.setCliente(cliente);
        reserva.setVehiculo(vehiculo);
        
        // Actualiza el costo de la reserva
        double nuevoCosto = calcularCosto(vehiculo) * dias; // Calcula el nuevo costo
        reserva.setCosto(nuevoCosto); // Asigna el nuevo costo a la reserva
    
        // Aquí podrías agregar lógica adicional para persistir los cambios en la base de datos
        return true; // Retorna verdadero si la actualización fue exitosa
    }

    public boolean eliminarReserva(Reserva reserva) {
        if (reserva != null) {
            if (empresa.eliminarReserva(reserva.getNombre())) {
                reservas.remove(reserva);
                return true; // Reserva eliminada exitosamente
            }
        }
        return false; // Falló la eliminación de la reserva
    }


    public double calcularCosto(Vehiculo vehiculo) {
        if (vehiculo instanceof ICosto) {
            return ((ICosto) vehiculo).calcularCosto();
        }
        return 0; // o lanzar una excepción, si lo prefieres
    }
 
}

