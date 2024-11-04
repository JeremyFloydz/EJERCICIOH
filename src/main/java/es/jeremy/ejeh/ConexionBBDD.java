package es.jeremy.ejeh;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase que gestiona la conexión a una base de datos MariaDB.
 * <p>
 * Esta clase implementa la interfaz {@code AutoCloseable} para permitir el uso
 * de la instrucción try-with-resources y garantizar el cierre de la conexión de
 * forma automática.
 * </p>
 *
 * <p>
 * La clase carga el driver de MariaDB, establece una conexión con la base de datos
 * y proporciona información de depuración sobre la base de datos conectada.
 * </p>
 */
public class ConexionBBDD implements AutoCloseable {
    private Connection conexion;

    /**
     * Constructor que inicializa la conexión a la base de datos MariaDB.
     *
     * @throws SQLException si no se puede cargar el driver o si falla la conexión
     *                      a la base de datos.
     */
    public ConexionBBDD() throws SQLException {
        try {
            // Cargar el driver de MariaDB
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("No se pudo cargar el driver de MariaDB", e);
        }

        // Configurar los parámetros de la conexión
        Properties connConfig = new Properties();
        connConfig.setProperty("user", "root");  // Usuario de la BD
        connConfig.setProperty("password", "myPass");  // Contraseña de la BD

        // Establecer la conexión a la base de datos MariaDB
        String url = "jdbc:mariadb://localhost:3310/personas?serverTimezone=Europe/Madrid";
        conexion = DriverManager.getConnection(url, connConfig);
        conexion.setAutoCommit(true);

        // Información de la base de datos para depuración
        DatabaseMetaData databaseMetaData = conexion.getMetaData();
        System.out.println("--- Datos de conexión ------------------------------------------");
        System.out.printf("Base de datos: %s%n", databaseMetaData.getDatabaseProductName());
        System.out.printf("Versión: %s%n", databaseMetaData.getDatabaseProductVersion());
        System.out.printf("Driver: %s%n", databaseMetaData.getDriverName());
        System.out.printf("Versión: %s%n", databaseMetaData.getDriverVersion());
        System.out.println("----------------------------------------------------------------");
    }

    /**
     * Devuelve la conexión establecida a la base de datos.
     *
     * @return un objeto {@code Connection} que representa la conexión a la base de datos.
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Cierra la conexión a la base de datos.
     * <p>
     * Este método se llama automáticamente al salir de un bloque try-with-resources.
     * Si la conexión ya está cerrada, no hace nada.
     * </p>
     */
    @Override
    public void close() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
