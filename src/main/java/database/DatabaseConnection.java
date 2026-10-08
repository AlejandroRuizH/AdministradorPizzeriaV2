/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;
import config.AppConfig;
import java.sql.*;

/**
 * Gestiona las conexiones con la base de datos.
 *
 * Esta clase no mantiene una conexión global.
 * Cada operación obtiene su propia conexión y debe cerrarla
 * mediante try-with-resources.
 *
 * @author Alejandro Ruiz
 */
public final class DatabaseConnection {

    private DatabaseConnection() {
        // Evita instanciación.
    }

    /**
     * Obtiene una conexión utilizando la configuración
     * actualmente almacenada en AppConfig.
     *
     * @return conexión activa con MySQL
     * @throws SQLException si no es posible establecer la conexión
     */
    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                AppConfig.getDatabaseUrl(),
                AppConfig.getDatabaseUser(),
                AppConfig.getDatabasePassword()
        );
    }

    /**
     * Prueba una conexión utilizando la configuración
     * actualmente guardada en AppConfig.
     *
     * @return true si la conexión fue exitosa
     */
    public static boolean probarConexion() {

        try (Connection connection = getConnection()) {

            return connection != null
                    && !connection.isClosed();

        } catch (SQLException e) {

            return false;
        }
    }

    /**
     * Prueba una conexión utilizando parámetros proporcionados
     * por el usuario.
     *
     * Este método es utilizado por el panel de configuración
     * antes de guardar una nueva configuración.
     *
     * @param host servidor MySQL
     * @param port puerto MySQL
     * @param database nombre de la base de datos
     * @param user usuario de MySQL
     * @param password contraseña de MySQL
     * @return true si la conexión fue exitosa
     */
    public static boolean probarConexion(
            String host,
            int port,
            String database,
            String user,
            String password) {

        String url = construirUrl(
                host,
                port,
                database
        );

        try (Connection connection =
                     DriverManager.getConnection(
                             url,
                             user,
                             password)) {

            return connection != null
                    && !connection.isClosed();

        } catch (SQLException e) {

            return false;
        }
    }

    /**
     * Prueba una conexión y devuelve la excepción en caso
     * de que exista un error.
     *
     * Es útil para mostrar al usuario el motivo del fallo.
     *
     * @param host servidor MySQL
     * @param port puerto MySQL
     * @param database nombre de la base de datos
     * @param user usuario
     * @param password contraseña
     * @throws SQLException si la conexión falla
     */
    public static void probarConexionConDetalle(
            String host,
            int port,
            String database,
            String user,
            String password)
            throws SQLException {

        String url = construirUrl(
                host,
                port,
                database
        );

        try (Connection connection =
                     DriverManager.getConnection(
                             url,
                             user,
                             password)) {

            if (connection == null || connection.isClosed()) {
                throw new SQLException(
                        "No fue posible establecer la conexión."
                );
            }
        }
    }

    /**
     * Construye la URL JDBC de MySQL.
     */
    private static String construirUrl(
            String host,
            int port,
            String database) {

        return String.format(
                "jdbc:mysql://%s:%d/%s"
                + "?useSSL=false&serverTimezone=UTC",
                host,
                port,
                database
        );
    }
}