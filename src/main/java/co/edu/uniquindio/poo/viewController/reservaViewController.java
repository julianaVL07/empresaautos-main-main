package co.edu.uniquindio.poo.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.App;
import co.edu.uniquindio.poo.controller.reservaController;
import co.edu.uniquindio.poo.model.Auto;
import co.edu.uniquindio.poo.model.Camioneta;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Moto;
import co.edu.uniquindio.poo.model.Reserva;
import co.edu.uniquindio.poo.model.TipoCaja;
import co.edu.uniquindio.poo.model.Vehiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class reservaViewController {

    App app;

    @FXML
    private TableView<Reserva> tabReserva;

    @FXML
    private TableColumn<Reserva, Cliente> columnCliente;

    @FXML
    private TableColumn<Reserva, Integer> columnDiasReserva;

    @FXML
    private TableColumn<Reserva, Vehiculo> columnVehiculo;

    @FXML
    private TableColumn<Reserva, Double> columnCosto;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnCrearReserva;

    @FXML
    private ComboBox<Cliente> txtListaClientes;

    @FXML
    private TextField txtDiasReserva;

    @FXML
    private TextField txtCostoTotal;

    @FXML
    private ComboBox<Vehiculo> txtListaVehiculo;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnActualizar;

    private reservaController controller;

    // Inicializa el controlador
    public reservaViewController() {
        this.controller = new reservaController();
    }

    @FXML
    void initialize() {
    // Asegurarse de que los elementos FXML fueron inyectados correctamente
    assert columnCliente != null : "fx:id=\"columnCliente\" was not injected: check your FXML file 'reserva.fxml'.";
    assert btnLimpiar != null : "fx:id=\"btnLimpiar\" was not injected: check your FXML file 'reserva.fxml'.";
    assert tabReserva != null : "fx:id=\"tabReserva\" was not injected: check your FXML file 'reserva.fxml'.";
    assert columnDiasReserva != null : "fx:id=\"columnDiasReserva\" was not injected: check your FXML file 'reserva.fxml'.";
    assert btnCrearReserva != null : "fx:id=\"btnCrearReserva\" was not injected: check your FXML file 'reserva.fxml'.";
    assert txtListaClientes != null : "fx:id=\"txtListaClientes\" was not injected: check your FXML file 'reserva.fxml'.";
    assert txtDiasReserva != null : "fx:id=\"txtDiasReserva\" was not injected: check your FXML file 'reserva.fxml'.";
    assert txtCostoTotal != null : "fx:id=\"txtCostoTotal\" was not injected: check your FXML file 'reserva.fxml'.";
    assert txtListaVehiculo != null : "fx:id=\"txtListaVehiculo\" was not injected: check your FXML file 'reserva.fxml'.";
    assert btnEliminar != null : "fx:id=\"btnEliminar\" was not injected: check your FXML file 'reserva.fxml'.";
    assert columnVehiculo != null : "fx:id=\"columnVehiculo\" was not injected: check your FXML file 'reserva.fxml'.";
    assert columnCosto != null : "fx:id=\"columnCosto\" was not injected: check your FXML file 'reserva.fxml'.";
    assert btnActualizar != null : "fx:id=\"btnActualizar\" was not injected: check your FXML file 'reserva.fxml'.";

    columnCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
    columnDiasReserva.setCellValueFactory(new PropertyValueFactory<>("diasReserva")); 
    columnVehiculo.setCellValueFactory(new PropertyValueFactory<>("vehiculo"));
    columnCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

    // Cargar datos en los ComboBox
    cargarDatosEnComboBoxes(); // Llama a este método para cargar datos
    actualizarTablaReservas(); // Carga la tabla de reservas

     // Listener para el ComboBox de clientes
     txtListaClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            // Si tienes un campo de texto para mostrar el nombre
            // txtNombreCliente.setText(newSelection.getNombre());
        }
    });

    // Listener para el ComboBox de vehículos
    txtListaVehiculo.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
        try {
            txtCostoTotal.setText(String.valueOf(controller.calcularCosto(newSelection)));
        } catch (Exception e) {
            System.out.println("Error al calcular el costo: " + e.getMessage());
            txtCostoTotal.setText("Error"); // Muestra un mensaje de error en el campo
        }
    }
    });

    }

    private void cargarDatosEnComboBoxes() {
        // Cargar clientes
        ObservableList<Cliente> clientes = FXCollections.observableArrayList(
            new Cliente("Juan Perez", "123456", null),
            new Cliente("Ana Gomez", "66666", null),
            new Cliente("Laura Lopera", "2345", null),
            new Cliente("Sara Bolaños", "0987", null)
        );
        txtListaClientes.setItems(clientes);
    
        // Cargar vehículos
        ObservableList<Vehiculo> vehiculos = FXCollections.observableArrayList(
            new Moto("ABC123", "Yamaha", "MT-07", 2020, 0, null, TipoCaja.AUTOMATICO),
            new Camioneta("XYZ789", "Toyota", "Hilux", 2021, 1000, null),
            new Auto ("ABC123", "Chevrolet", "Spar", 2022,4, null),
            new Auto ("HGF675", "chevrolet", "captiva", 2023,6, null)
        );
        txtListaVehiculo.setItems(vehiculos);
    }

    @FXML
    void crearReservaAction(ActionEvent event) {
        try {
            int dias = Integer.parseInt(txtDiasReserva.getText());
            Vehiculo vehiculo = txtListaVehiculo.getValue();
            Cliente cliente = txtListaClientes.getValue();
    
            if (cliente != null && vehiculo != null) {
                if (controller.crearReserva(dias, cliente, vehiculo)) {
                    txtCostoTotal.setText(String.valueOf(controller.calcularCosto(vehiculo)));
                    limpiarCampos();
                    actualizarTablaReservas(); // Actualiza la tabla tras crear la reserva
                    tabReserva.refresh();
                } else {
                    System.out.println("No se pudo crear la reserva. Verifique los datos.");
                }
            } else {
                System.out.println("Debe seleccionar un cliente y un vehículo.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un número válido para los días de reserva.");
        }
    }

    @FXML
    void eliminarAction(ActionEvent event) {
        Reserva reservaSeleccionada = tabReserva.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            controller.eliminarReserva(reservaSeleccionada);
            actualizarTablaReservas();
        } else {
            // Mostrar un mensaje de error al usuario
        }
    }

    @FXML
    void actualizarAction(ActionEvent event) {
    Reserva reservaSeleccionada = tabReserva.getSelectionModel().getSelectedItem();
    if (reservaSeleccionada != null) {
        // Validar y obtener los nuevos valores
        String diasStr = txtDiasReserva.getText();
        if (diasStr.isEmpty() || !diasStr.matches("\\d+")) {
            System.out.println("Error: Ingrese un número válido para los días de reserva.");
            return; // Salir del método si hay un error
        }

        try {
            int dias = Integer.parseInt(diasStr);
            Vehiculo vehiculo = txtListaVehiculo.getValue();
            Cliente cliente = txtListaClientes.getValue();

            if (vehiculo == null || cliente == null) {
                System.out.println("Error: Debe seleccionar un cliente y un vehículo.");
                return; // Salir del método si hay un error
            }

            // Actualizar la reserva
            boolean exito = controller.actualizarReserva(reservaSeleccionada, dias, cliente, vehiculo);
            if (exito) {
                // Actualizar la tabla y limpiar los campos
                actualizarTablaReservas(); // Esto ya incluirá el nuevo costo
                limpiarCampos();
                System.out.println("Reserva actualizada exitosamente.");
            } else {
                System.out.println("No se pudo actualizar la reserva.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un número válido para los días de reserva.");
        }
    } else {
        System.out.println("Debe seleccionar una reserva para actualizar.");
    }
    }
    
    @FXML
    void limpiarAction(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtDiasReserva.clear();
        txtCostoTotal.clear();
        txtListaClientes.getSelectionModel().clearSelection();
        txtListaVehiculo.getSelectionModel().clearSelection();
        tabReserva.getSelectionModel().clearSelection();
    }

    private void actualizarTablaReservas() {
        tabReserva.setItems(controller.getReservas()); // Asigna la lista actual a la tabla
        tabReserva.refresh(); // Esto puede ayudar a actualizar la vista
    }

    public void setApp(App app){
        this.app=app;
    }
}
