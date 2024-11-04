package es.jeremy.ejeh;

/**
 * Modelo que refleja la tabla llamada Persona.
 * Esta clase se utiliza para representar los datos de una persona en el sistema.
 * <p>
 * Usado comúnmente como objeto de transferencia de datos entre la interfaz de usuario y la base de datos.
 * </p>
 *
 * @author [Tu Nombre]
 * @version $Id: $Id
 */
public class ModeloPersona {
    /**
     * El identificador único de la persona.
     */
    private int id;

    /**
     * El nombre de la persona.
     */
    private String nombre;

    /**
     * Los apellidos de la persona.
     */
    private String apellidos;

    /**
     * La edad de la persona.
     */
    private int edad;

    /**
     * Constructor que inicializa una persona con sus atributos.
     *
     * @param id el identificador de la persona
     * @param nombre el nombre de la persona
     * @param apellidos los apellidos de la persona
     * @param edad la edad de la persona
     */
    public ModeloPersona(int id, String nombre, String apellidos, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    /**
     * Constructor sin el identificador, útil para nuevos registros.
     *
     * @param nombre el nombre de la persona
     * @param apellidos los apellidos de la persona
     * @param edad la edad de la persona
     */
    public ModeloPersona(String nombre, String apellidos, int edad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    // Métodos getters y setters

    /**
     * Devuelve el id de la persona.
     *
     * @return el id
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna un nuevo id a la persona.
     *
     * @param id el nuevo id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre de la persona.
     *
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna un nuevo nombre a la persona.
     *
     * @param nombre el nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve los apellidos de la persona.
     *
     * @return los apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Asigna nuevos apellidos a la persona.
     *
     * @param apellidos los nuevos apellidos
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Devuelve la edad de la persona.
     *
     * @return la edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Asigna una nueva edad a la persona.
     *
     * @param edad la nueva edad
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Devuelve una representación en cadena de la persona.
     *
     * @return una cadena que representa la persona.
     */
    @Override
    public String toString() {
        return "ModeloPersona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", edad=" + edad +
                '}';
    }
}
