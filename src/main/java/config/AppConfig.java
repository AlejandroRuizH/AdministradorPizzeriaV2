/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Gestiona la configuración de la aplicación.
 *
 * Centraliza la configuración de la base de datos y evita
 * tener diferentes clases encargadas de cargar las propiedades.
 */
public final class AppConfig {

    private static final String CONFIG_DIRECTORY = "config";
    private static final String CONFIG_FILE = "config.properties";

    private static final Path CONFIG_PATH =
            Paths.get(CONFIG_DIRECTORY, CONFIG_FILE);

    private static final Properties PROPERTIES = new Properties();

    private static boolean initialized = false;

    /**
     * Constructor privado para evitar instanciación.
     */
    private AppConfig() {
    }

    /**
     * Inicializa la configuración de la aplicación.
     *
     * @throws IOException si no es posible cargar o crear
     *                     el archivo de configuración.
     */
    public static void initialize() throws IOException {

        if (initialized) {
            return;
        }

        createConfigDirectory();
        createDefaultConfigIfNeeded();
        loadConfiguration();

        initialized = true;
    }

    /**
     * Crea el directorio de configuración si no existe.
     */
    private static void createConfigDirectory() throws IOException {

        Path directory = Paths.get(CONFIG_DIRECTORY);

        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
    }

    /**
     * Crea un archivo de configuración inicial si no existe.
     */
    private static void createDefaultConfigIfNeeded()
            throws IOException {

        if (Files.exists(CONFIG_PATH)) {
            return;
        }

        PROPERTIES.setProperty("db.host", "localhost");
        PROPERTIES.setProperty("db.port", "3306");
        PROPERTIES.setProperty("db.name", "pizzeria_pos");
        PROPERTIES.setProperty("db.user", "root");
        PROPERTIES.setProperty("db.password", "");

        saveConfiguration();
    }

    /**
     * Carga la configuración desde el archivo.
     */
    private static void loadConfiguration()
            throws IOException {

        try (InputStream input =
                     Files.newInputStream(CONFIG_PATH)) {

            PROPERTIES.clear();
            PROPERTIES.load(input);
        }
    }

    /**
     * Guarda la configuración actual en disco.
     */
    private static void saveConfiguration()
            throws IOException {

        try (OutputStream output =
                     Files.newOutputStream(CONFIG_PATH)) {

            PROPERTIES.store(
                    output,
                    "Configuracion de la Base de Datos"
            );
        }
    }

    /**
     * Obtiene una propiedad de configuración.
     *
     * @param key clave de configuración
     * @return valor de la propiedad o null si no existe
     */
    public static String get(String key) {

        return PROPERTIES.getProperty(key);
    }

    /**
     * Obtiene una propiedad de configuración.
     *
     * @param key clave de configuración
     * @param defaultValue valor utilizado si la propiedad no existe
     * @return valor de la propiedad
     */
    public static String get(
            String key,
            String defaultValue) {

        return PROPERTIES.getProperty(key, defaultValue);
    }

    /**
     * Obtiene el host de la base de datos.
     *
     * @return host de MySQL
     */
    public static String getDatabaseHost() {

        return get("db.host");
    }

    /**
     * Obtiene el puerto de la base de datos.
     *
     * @return puerto de MySQL
     */
    public static int getDatabasePort() {

        return Integer.parseInt(get("db.port", "3306"));
    }

    /**
     * Obtiene el nombre de la base de datos.
     *
     * @return nombre de la base de datos
     */
    public static String getDatabaseName() {

        return get("db.name");
    }

    /**
     * Obtiene el usuario de la base de datos.
     *
     * @return usuario de MySQL
     */
    public static String getDatabaseUser() {

        return get("db.user");
    }

    /**
     * Obtiene la contraseña de la base de datos.
     *
     * @return contraseña de MySQL
     */
    public static String getDatabasePassword() {

        return get("db.password");
    }

    /**
     * Construye la URL JDBC de MySQL.
     *
     * @return URL JDBC
     */
    public static String getDatabaseUrl() {

        return String.format(
                "jdbc:mysql://%s:%d/%s"
                + "?useSSL=false&serverTimezone=UTC",
                getDatabaseHost(),
                getDatabasePort(),
                getDatabaseName()
        );
    }

    /**
     * Actualiza la configuración de la base de datos.
     *
     * @param host servidor MySQL
     * @param port puerto MySQL
     * @param database nombre de la base de datos
     * @param user usuario
     * @param password contraseña
     * @throws IOException si no es posible guardar la configuración
     */
    public static void updateDatabaseConfiguration(
            String host,
            int port,
            String database,
            String user,
            String password)
            throws IOException {

        PROPERTIES.setProperty("db.host", host);
        PROPERTIES.setProperty(
                "db.port",
                String.valueOf(port)
        );
        PROPERTIES.setProperty("db.name", database);
        PROPERTIES.setProperty("db.user", user);
        PROPERTIES.setProperty("db.password", password);

        saveConfiguration();
    }

    /**
     * Indica si la configuración ha sido inicializada.
     *
     * @return true si fue inicializada
     */
    public static boolean isInitialized() {

        return initialized;
    }
}
