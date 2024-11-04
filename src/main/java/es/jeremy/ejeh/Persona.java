package es.jeremy.ejeh;

import java.util.Objects;

/**
 * Clase que representa una persona con atributos de nombre, apellidos y edad.
 * Incluye métodos para la manipulación de estos atributos, validación de la edad y
 * métodos sobrescritos para comparación e impresión.
 */
public class Persona {
    private String nombre;
    private String apellidos;
    private int edad;

    /**
     * Constructor que inicializa los atributos de una persona.
     *
     * @param nombre    el nombre de la persona
     * @param apellidos los apellidos de la persona
     * @param edad      la edad de la persona, debe ser no negativa
     * @throws IllegalArgumentException si la edad es negativa
     */
    public Persona(String nombre, String apellidos, int edad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        setEdad(edad); // Usar el setter para validar la edad
    }

    // Getters

    /**
     * Devuelve el nombre de la persona.
     *
     * @return el nombre de la persona
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve los apellidos de la persona.
     *
     * @return los apellidos de la persona
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Devuelve la edad de la persona.
     *
     * @return la edad de la persona
     */
    public int getEdad() {
        return edad;
    }

    // Setters

    /**
     * Asigna un nuevo nombre a la persona.
     *
     * @param nombre el nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * Asigna una nueva edad a la persona, validando que no sea negativa.
     *
     * @param edad la nueva edad
     * @throws IllegalArgumentException si la edad es negativa
     */
    public void setEdad(int edad) {
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa."); // Validación
        }
        this.edad = edad;
    }

    /**
     * Compara este objeto con otro para verificar si son iguales.
     *
     * @param obj el objeto a comparar
     * @return true si los objetos tienen el mismo nombre, apellidos y edad; false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Persona)) return false;
        Persona persona = (Persona) obj;
        return edad == persona.edad &&
                Objects.equals(nombre, persona.nombre) &&
                Objects.equals(apellidos, persona.apellidos);
    }

    /**
     * Devuelve un valor hash para la persona.
     *
     * @return el código hash basado en el nombre, apellidos y edad
     */
    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellidos, edad);
    }

    /**
     * Devuelve una representación en forma de cadena de la persona.
     *
     * @return una cadena en el formato "nombre apellidos (edad años)"
     */
    @Override
    public String toString() {
        return nombre + " " + apellidos + " (" + edad + " años)";
    }
}
