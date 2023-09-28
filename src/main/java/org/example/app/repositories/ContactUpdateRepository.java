package org.example.app.repositories;

import org.example.app.database.DBConn;
import org.example.app.entities.Contact;
import org.example.app.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactUpdateRepository {

    private static final Logger LOGGER =
            Logger.getLogger(ContactUpdateRepository.class.getName());

    public String updateContact(Contact contact) {

        String sql = "UPDATE " + Constants.TABLE_CONTACTS + " SET phone = ? WHERE id = ?";
        // PreparedStatement - подготовленное выражение, чтобы избежать SQL-инъекций
        try (PreparedStatement pstmt = DBConn.connect().prepareStatement(sql)) {
            pstmt.setString(1, contact.getPhone());
            pstmt.setInt(2, contact.getId());
            pstmt.executeUpdate();
            return Constants.DATA_UPDATE_MSG;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            return e.getMessage();
        }
    }
}
