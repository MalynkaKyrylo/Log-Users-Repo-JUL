package org.example.app.repositories;

import org.example.app.database.DBConn;
import org.example.app.entities.Contact;
import org.example.app.utils.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactReadRepository {

    private static final Logger LOGGER =
            Logger.getLogger(ContactReadRepository.class.getName());

    public List<Contact> readContacts() {

        try (Statement stmt = DBConn.connect().createStatement()) {

            List<Contact> list = new ArrayList<>();

            String sql = "SELECT id, name, phone FROM " + Constants.TABLE_CONTACTS;
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setPhone(rs.getString("phone"));
                list.add(contact);
            }
            // Возвращаем коллекцию данных
            return list;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            // Если ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }
}
