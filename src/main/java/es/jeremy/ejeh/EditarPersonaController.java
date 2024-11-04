package es.jeremy.ejeh;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador para la vista de edición de una persona.
 * <p>
 * Esta clase permite la edición de los datos de una persona, mostrando la información
 * en un formulario y gestionando la actualización de los datos en la base de datos.
 * </p>
 */
public class EditarPersonaController {
    @FXML
    private TextField nombreField; // Campo para el nombre de la persona
    @FXML
    private TextField apellidosField; // Campo para los apellidos de la persona
    @FXML
    private TextField edadField; // Campo para la edad de la persona
    @FXML
    private Button guardarButton; // Botón para guardar los cambios
    @FXML
    private Button cancelarButton; // Botón para cancelar la edición

    private HelloController parentController; // Referencia al controlador principal
    private Persona personaEdicion; // Persona cuyos datos se están editando

    /**
     * Inicializa el controlador y asigna las acciones de los botones.
     */
    @FXML
    public void initialize() {
        guardarButton.setOnAction(e -> guardarPersona());
        cancelarButton.setOnAction(e -> cancelar());
    }

    /**
     * Carga los datos de la persona seleccionada en los campos de texto.
     *
     * @param persona la {@code Persona} cuyos datos se van a cargar.
     */
    public void cargarDatos(Persona persona) {
        this.personaEdicion = persona; // Guardar la referencia de la persona
        nombreField.setText(persona.getNombre());
        apellidosField.setText(persona.getApellidos());
        edadField.setText(String.valueOf(persona.getEdad()));
    }

    /**
     * Guarda los cambios realizados en la persona y actualiza la base de datos.
     * <p>
     * Realiza validaciones en los campos de entrada y actualiza la base de datos
     * con los datos modificados. Notifica al controlador padre para que actualice
     * la vista y cierra la ventana al finalizar.
     * </p>
     */
    private void guardarPersona() {
        String nombre = nombreField.getText();
        String apellidos = apellidosField.getText();
        String edadStr = edadField.getText();

        // Validación de datos
        if (nombre.isEmpty() || apellidos.isEmpty() || edadStr.isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }

        try {
            int edad = Integer.parseInt(edadStr);
            // Crear un nuevo objeto Persona con los datos modificados
            Persona personaNueva = new Persona(nombre, apellidos, edad);

            // Modificar la persona en la base de datos
            boolean modificadoEnBD = DaoPersona.modificarPersona(personaEdicion, personaNueva);
            if (modificadoEnBD) {
                // Actualizar la persona en la referencia actual
                personaEdicion.setNombre(nombre);
                personaEdicion.setApellidos(apellidos);
                personaEdicion.setEdad(edad);

                // Notificar al controlador padre que los datos han cambiado
                parentController.actualizarTabla(); // Asegúrate de implementar este método

                // Cerrar la ventana
                cerrarVentana();
            } else {
                mostrarAlerta("No se pudo actualizar la persona en la base de datos.");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("La edad debe ser un número válido.");
        }
    }

    /**
     * Cancela la edición y cierra la ventana actual.
     */
    private void cancelar() {
        cerrarVentana();
    }

    /**
     * Cierra la ventana actual de la vista de edición.
     */
    private void cerrarVentana() {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Muestra una alerta con un mensaje de advertencia.
     *
     * @param mensaje el mensaje que se mostrará en la alerta.
     */
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Establece el controlador padre para permitir la comunicación con otras vistas.
     *
     * @param parentController el controlador padre de tipo {@code HelloController}.
     */
    public void setParentController(HelloController parentController) {
        this.parentController = parentController;
    }
}
