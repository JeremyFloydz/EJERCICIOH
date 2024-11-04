package es.jeremy.ejeh;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Clase principal de la aplicación que extiende {@code Application} de JavaFX.
 * <p>
 * Esta clase carga la interfaz gráfica de usuario desde un archivo FXML,
 * configura el escenario y maneja la inicialización de la conexión a la base de datos.
 * </p>
 */
public class HelloApplication extends Application {

    /**
     * Método de inicio de la aplicación JavaFX.
     * <p>
     * Configura el {@code Stage} principal, carga la escena desde el archivo FXML,
     * establece el título y el ícono de la ventana, y muestra la escena.
     * </p>
     *
     * @param stage el escenario principal de la aplicación.
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        // Cargar el logo como imagen
        Image icon = new Image(getClass().getResourceAsStream("/img/agenda.png"));
        stage.getIcons().add(icon); // Establecer el ícono de la ventana

        // Crear la escena con las dimensiones adecuadas
        Scene scene = new Scene(fxmlLoader.load(), 734, 474);

        // Establecer el título de la ventana
        stage.setTitle("Personas");

        // Mostrar la escena en la ventana
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método principal de la aplicación que inicia el programa.
     * <p>
     * Verifica la conexión a la base de datos al inicio y luego lanza la aplicación JavaFX.
     * </p>
     *
     * @param args los argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        try {
            // Crear la conexión para verificar que es posible conectarse a la base de datos.
            ConexionBBDD conexioPrueba = new ConexionBBDD();
            // Ejemplo comentado para la creación de un objeto de prueba y llamada al DAO
            // ModeloPersona personaPrueba = new ModeloPersona("69696969-Z");
            // DaoDni.nuevoDNI(personaPrueba);

        } catch (SQLException e) {
            // TODO: Manejar esta excepción adecuadamente
            throw new RuntimeException(e);
        }
        // Iniciar la aplicación JavaFX
        launch();
    }
}
