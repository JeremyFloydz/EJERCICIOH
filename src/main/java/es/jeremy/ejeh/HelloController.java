package es.jeremy.ejeh;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador principal de la aplicación que gestiona la vista y las acciones de la interfaz de usuario.
 * <p>
 * Este controlador permite la visualización, modificación, eliminación y adición de personas a través
 * de una interfaz gráfica basada en JavaFX.
 * </p>
 */
public class HelloController {

    @FXML
    private TableView<Persona> tableView;  // Tabla de personas
    @FXML
    private TableColumn<Persona, String> nombreColumn;  // Columna Nombre
    @FXML
    private TableColumn<Persona, String> apellidosColumn;  // Columna Apellidos
    @FXML
    private TableColumn<Persona, Integer> edadColumn;  // Columna Edad
    @FXML
    private Button agregarButton;  // Botón para agregar personas
    @FXML
    private Button modificarButton;  // Botón para modificar personas
    @FXML
    private Button eliminarButton;  // Botón para eliminar personas
    @FXML
    private TextField filtroNombreField;  // Campo para filtrar por nombre

    private ObservableList<Persona> personas;

    /**
     * Método de inicialización que configura la tabla, columnas y las acciones de los botones.
     */
    @FXML
    public void initialize() {
        personas = FXCollections.observableArrayList();
        tableView.setItems(personas);

        // Configuración de las columnas de la tabla
        nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        apellidosColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellidos()));
        edadColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEdad()).asObject());

        // Asignar la acción a los botones
        agregarButton.setOnAction(e -> agregarPersona());
        modificarButton.setOnAction(e -> modificarPersona());
        eliminarButton.setOnAction(e -> eliminarPersona());

        // Configuración del filtro de la tabla
        filtroNombreField.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarTabla(newValue);
        });
    }

    /**
     * Abre una nueva ventana para agregar una persona.
     */
    private void agregarPersona() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ventana.fxml"));
            Parent root = loader.load();

            NuevaPersonaController controller = loader.getController();
            controller.setParentController(this); // Establecer el controlador padre

            Stage stage = new Stage();
            stage.setTitle("Nueva Persona");
            stage.setScene(new Scene(root));
            stage.setResizable(false); // No se puede cambiar el tamaño
            stage.initModality(Modality.APPLICATION_MODAL); // Modalidad
            stage.showAndWait(); // Esperar a que se cierre la ventana

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de agregar persona.");
        }
    }

    /**
     * Abre una ventana para modificar la persona seleccionada en la tabla.
     */
    private void modificarPersona() {
        Persona personaSeleccionada = tableView.getSelectionModel().getSelectedItem();
        if (personaSeleccionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("editarventana.fxml"));
                Parent root = loader.load();

                EditarPersonaController controller = loader.getController();
                controller.setParentController(this); // Establecer el controlador padre
                controller.cargarDatos(personaSeleccionada); // Cargar datos de la persona seleccionada

                Stage stage = new Stage();
                stage.setTitle("Modificar Persona");
                stage.setScene(new Scene(root));
                stage.setResizable(false); // No se puede cambiar el tamaño
                stage.initModality(Modality.APPLICATION_MODAL); // Modalidad
                stage.showAndWait(); // Esperar a que se cierre la ventana

                // Después de que se cierra la ventana de edición, actualiza la tabla
                actualizarTabla();

            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo abrir la ventana de editar persona.");
            }
        } else {
            mostrarAlerta("Advertencia", "Por favor, selecciona una persona para modificar.");
        }
    }

    /**
     * Elimina la persona seleccionada de la tabla y la base de datos.
     */
    private void eliminarPersona() {
        Persona personaSeleccionada = tableView.getSelectionModel().getSelectedItem();
        if (personaSeleccionada != null) {
            // Llama al método de eliminación en DaoPersona
            boolean eliminadoEnBD = DaoPersona.eliminarPersona(personaSeleccionada);
            if (eliminadoEnBD) {
                personas.remove(personaSeleccionada);
                mostrarAlerta("Éxito", "Persona eliminada con éxito.");
            } else {
                mostrarAlerta("Error", "No se pudo eliminar la persona de la base de datos.");
            }
        } else {
            mostrarAlerta("Advertencia", "Por favor, selecciona una persona para eliminar.");
        }
    }

    /**
     * Agrega una nueva persona a la lista y la guarda en la base de datos.
     *
     * @param nuevaPersona la persona que se va a agregar.
     */
    public void agregarPersona(Persona nuevaPersona) {
        if (!personas.contains(nuevaPersona)) {
            boolean guardadoEnBD = DaoPersona.nuevaPersona(nuevaPersona);

            if (guardadoEnBD) {
                personas.add(nuevaPersona);  // Agrega a la lista y tabla si se guardó en la BD
                mostrarAlerta("Éxito", "Persona agregada y guardada en la base de datos.");
            } else {
                mostrarAlerta("Error", "No se pudo guardar en la base de datos.");
            }
        } else {
            mostrarAlerta("Error", "Esta persona ya existe en la lista.");
        }
    }

    /**
     * Muestra una alerta de información al usuario.
     *
     * @param titulo  el título de la alerta.
     * @param mensaje el contenido del mensaje de la alerta.
     */
    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Actualiza la tabla para reflejar los cambios realizados.
     */
    public void actualizarTabla() {
        tableView.refresh();
    }

    /**
     * Filtra las personas mostradas en la tabla según el nombre ingresado.
     *
     * @param nombre el texto de filtro que se va a usar.
     */
    private void filtrarTabla(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            tableView.setItems(personas);
        } else {
            ObservableList<Persona> filtradas = FXCollections.observableArrayList();
            for (Persona persona : personas) {
                if (persona.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                    filtradas.add(persona);
                }
            }
            tableView.setItems(filtradas);
        }
    }
}
