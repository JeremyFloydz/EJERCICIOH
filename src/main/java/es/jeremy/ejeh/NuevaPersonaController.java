package es.jeremy.ejeh;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador para la interfaz de creación de una nueva persona.
 * Esta clase permite al usuario ingresar datos de una nueva persona y guardarlos en la lista de personas gestionada por el controlador padre.
 */
public class NuevaPersonaController {
    /**
     * Campo de texto para el nombre de la persona.
     */
    @FXML
    private TextField nombreField;

    /**
     * Campo de texto para los apellidos de la persona.
     */
    @FXML
    private TextField apellidosField;

    /**
     * Campo de texto para la edad de la persona.
     */
    @FXML
    private TextField edadField;

    /**
     * Botón para guardar los datos ingresados.
     */
    @FXML
    private Button guardarButton;

    /**
     * Botón para cancelar y cerrar la ventana.
     */
    @FXML
    private Button cancelarButton;

    /**
     * Referencia al controlador padre que gestiona la lista de personas.
     */
    private HelloController parentController;

    /**
     * Inicializa el controlador y asigna las acciones a los botones.
     */
    @FXML
    public void initialize() {
        // Asignar acción al botón de guardar
        guardarButton.setOnAction(e -> guardarPersona());
        // Asignar acción al botón de cancelar
        cancelarButton.setOnAction(e -> cancelar());
    }

    /**
     * Establece el controlador padre para poder actualizar la lista de personas.
     *
     * @param parentController el controlador padre
     */
    public void setParentController(HelloController parentController) {
        this.parentController = parentController;
    }

    /**
     * Guarda los datos de la nueva persona si la validación es exitosa.
     * Muestra una alerta si hay campos vacíos o si la edad no es un número válido.
     */
    private void guardarPersona() {
        // Obtener datos de los campos
        String nombre = nombreField.getText();
        String apellidos = apellidosField.getText();
        int edad;

        // Validación de los datos
        if (nombre.isEmpty() || apellidos.isEmpty() || edadField.getText().isEmpty()) {
            mostrarAlerta("Error", "Por favor, completa todos los campos.");
            return;
        }

        try {
            // Convertir la edad a un entero
            edad = Integer.parseInt(edadField.getText());

            // Crear la nueva persona y agregarla al controlador padre
            Persona nuevaPersona = new Persona(nombre, apellidos, edad);
            parentController.agregarPersona(nuevaPersona); // Agregar la nueva persona a la lista del controlador padre

            // Cerrar la ventana después de guardar
            Stage stage = (Stage) guardarButton.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            // Manejar error de conversión a entero
            mostrarAlerta("Error", "La edad debe ser un número válido.");
        }
    }

    /**
     * Cierra la ventana sin realizar ninguna acción.
     */
    private void cancelar() {
        // Cerrar la ventana
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Muestra una alerta al usuario con un título y un mensaje específico.
     *
     * @param titulo  el título de la alerta
     * @param mensaje el mensaje que se mostrará en la alerta
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
