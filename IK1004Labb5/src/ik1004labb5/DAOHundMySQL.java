/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik1004labb5;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TErik
 */
public class DAOHundMySQL implements DAOHund {

    private Connection con;

    private Connection getConnection() {

        try {
            /* con = DriverManager.getConnection("jdbc:mysql://utb-mysql.du.se:3306/db30", //Logga in via utb-mysql.du.se
                    "db30",
                    "FJJAcyMU");*/
            
            con = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7140796", 
                    "sql7140796",
                    "u3ZrUD9d6F");

            return con;
        } catch (SQLException ex) {
            Logger.getLogger(DAOHundMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void add(DTOHund dtoHund) {
        try {
            con = getConnection();

            String insertHundSQL = "INSERT INTO h15aspto_hundar"
                    + "(id, namn, ras, bildURL) VALUES"
                    + //Ordningen på frågetecknen måste överensstämma med platshållaren på raden nedan
                    "(?,?,?,?)"; //Frågetecken 1 pekar på id, 2 på namn osv. PLATSHÅLLARE!

            //PreparedStatement hanterar våra platshållare ovan
            PreparedStatement prepStatement = con.prepareStatement(insertHundSQL);
            prepStatement.setInt(1, dtoHund.id);
            prepStatement.setString(2, dtoHund.namn);
            prepStatement.setString(3, dtoHund.ras);
            prepStatement.setString(4, dtoHund.bildURL);

            prepStatement.executeUpdate();
            prepStatement.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOHundMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            con = getConnection();

            String deleteCarSQL = "DELETE FROM h15aspto_hundar WHERE id=?";

            PreparedStatement prepStatement = con.prepareStatement(deleteCarSQL);
            prepStatement.setInt(1, id);
            prepStatement.executeUpdate();
            prepStatement.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOHundMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void update(DTOHund dtoHund) {

        try {
            con = getConnection();

            String updateHundarSQL = "UPDATE h15aspto_hundar SET namn =?, ras=?, bildURL=? WHERE id=?";
            PreparedStatement prepStatement = con.prepareStatement(updateHundarSQL);
            prepStatement.setString(1, dtoHund.namn);
            prepStatement.setString(2, dtoHund.ras);
            prepStatement.setString(3, dtoHund.bildURL);
            prepStatement.setInt(4, dtoHund.id);

            prepStatement.executeUpdate();
            prepStatement.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOHundMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<DTOHund> getHundar() {
        try {
            List<DTOHund> hundar = new ArrayList();
            con = getConnection();
            Statement stmt = (Statement) con.createStatement();
            String sql = "SELECT id, namn, ras, bildURL from h15aspto_hundar";
            ResultSet rs = stmt.executeQuery(sql);

//ResultSet har som en cursor (typ iterator??) och med hjälp av metoden next() flyttar vi cursorn
            while (rs.next()) {
                int id = rs.getInt("id"); //Case sensitive och pekar på kolumnnamnen
                String namn = rs.getString("namn");
                String ras = rs.getString("ras");
                String bildURL = rs.getString("bildURL");
                hundar.add(new DTOHund(id, namn, ras, bildURL));

            }

            rs.close();
            stmt.close();
            con.close();
            return hundar;
        } catch (SQLException ex) {
            Logger.getLogger(DAOHundMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}

    /*public List<DTOHund> getListFromDatabase() {

        try {
            List<DTOHund> hundar = new ArrayList();
            con = getConnection();
            Statement stmt = (Statement) con.createStatement();
            String sql = "SELECT id, namn, ras, bildURL from h15aspto_hundar";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                int id = rs.getInt("id"); //Case sensitive och pekar på kolumnnamnen
                String namn = rs.getString("namn");
                String ras = rs.getString("ras");
                String bildURL = rs.getString("bildURL");
                hundar.add(new DTOHund(id, namn, ras, bildURL));

            }

            rs.close();
            stmt.close();
            con.close();
            return hundar;
        } catch (SQLException ex) {
            Logger.getLogger(DAOHundMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}
/*
1. Öppna
2. Preppa SQL-fråga
3. Kör SQL-fråga
(4). Ta hand om svar (ResultSet)
5. Sträng resurser (Connection, PreparedStatement samt eventuellt ResultSet)
 */
