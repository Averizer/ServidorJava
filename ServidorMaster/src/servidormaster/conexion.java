/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidormaster;

import java.awt.List;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author emili
 */
public class conexion {
    private final String url = "jdbc:mysql://localhost/biblioteca";
    PreparedStatement psPrepararSentencia;
    Connection conn = null;
    

    public void conectar(){
        try{   //Inicio del try
         
         Class.forName("com.mysql.jdbc.Driver");     //Con el metodo de la clase forName, le pasamos el driver de MySQL para que lo cargue    
         conn = DriverManager.getConnection(url,"root","");    //Apuntamos nuestro objeto con a el intento de conectarse con los parametros o las credenciales que tenemos en MYSQL
        //Aqui mandamos la url donde viene la direccion de la BD, nuestro nombre de usuario y la contraseña, que por defecto al instalar viene vacia
        if (conn!=null){                         //Si logramos conectarnos, con deja de apuntar a null y obtenemos conexion
            System.out.println("Conexión a base de datos funcionando");                //Sin funciona imprimimos en consola un mensaje
            }
        }//cerramos el try
         catch(SQLException e)        //Agarramos excepciones de tipo SQL
         {
         System.out.println(e);          //las mostramos en consola
         }
         catch(ClassNotFoundException e)       //agarramos excepciones de tipo clase en java
         {
          System.out.println(e);               //las mostramos en consola
         }
    }
    
    public Connection conectado(){  //Este metodo de tipo Connection nos devuelve el estado del objeto
      return conn;
    }

    public void desconectar(){     //Por seguridad, cuando terminemos sentencias, cerramos la conexion o si la necesitamos cerrar por otro caso
      conn = null;                  //Ahora de nuevo con sera null
      System.out.println("La conexion la BD se ha cerrado");

    } 
    
    public String disponibilidad(){
        String resultado="";
        try{
            
            // our SQL SELECT query. 
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT nombre, autor, disponible FROM libro where disponible = DISPONIBLE";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
              String firstName = rs.getString("nombre");
              String autor = rs.getString("autor");
              String disponible = rs.getString("disponible");

              // print the results
              resultado = resultado 
                      + firstName + ", "
                      + autor + ", "
                      + disponible +"\n";
              System.out.format("%s, %s, %s\n", firstName, autor, disponible);
            }
            st.close();
            
        }catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return resultado;
    }
    
    public ArrayList<String> dpp(){
        // Definimos una ArrayList
        ArrayList<String> lista = new ArrayList<>();
        try{
            // our SQL SELECT query. 
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT nombre FROM libro where disponible = DISPONIBLE";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
              String firstName = rs.getString("nombre");

              // print the results
              lista.add(firstName);
              System.out.format("%s \n", firstName);
            }
            st.close();
            
        }catch (Exception e){
            System.err.println("Error al pedir disponibles! ");
            System.err.println(e.getMessage());
        }
        return lista;
    }
    
    public void prestamo(String cambio){
        try{
            // create the java mysql update preparedstatement
            String query = "update libro set disponible =? where nombre =?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, "AGOTADO");
            preparedStmt.setString(2, cambio);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();
            System.out.println("LIBRO PRESTADO");
        }catch (Exception e){
            System.err.println("Error al prestar! ");
            System.err.println(e.getMessage());
        }
    }
    
    public void reset(){
        try{
            // create the java mysql update preparedstatement
            String query = "update libro set disponible =? where disponible =?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, "DISPONIBLE");
            preparedStmt.setString(2, "AGOTADO");

            // execute the java preparedstatement
            preparedStmt.executeUpdate();
            System.out.println("TODO DISPONIBLE");
        }catch (Exception e){
            System.err.println("Error al resetear! ");
            System.err.println(e.getMessage());
        }
    }
}


