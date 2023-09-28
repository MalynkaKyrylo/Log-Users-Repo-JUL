package org.example.app.services;

import org.example.app.database.DBCheck;
import org.example.app.entities.Contact;
import org.example.app.exceptions.DBException;
import org.example.app.repositories.ContactReadRepository;
import org.example.app.utils.Constants;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactReadService {

    ContactReadRepository repository;
    private static final Logger LOGGER =
            Logger.getLogger(ContactReadService.class.getName());

    public ContactReadService(ContactReadRepository repository) {
        this.repository = repository;
    }

    public String readContacts() {
        // Проверяем на наличие файла БД.
        // ДА - работаем с данными. НЕТ - уведомление об отсутствии БД.
        if (DBCheck.isDBExists()) {
            try {
                throw new DBException(Constants.DB_ABSENT_MSG);
            } catch (DBException e) {
                LOGGER.log(Level.SEVERE, Constants.LOG_DB_ABSENT_MSG);
                return e.getMessage();
            }
        }

        // Получаем данные в коллекцию.
        List<Contact> contacts = repository.readContacts();

        // Если коллекция не null, формируем вывод.
        // Иначе уведомление об отсутствии данных.
        if (contacts != null) {
            // Если коллекция не пуста, формируем вывод.
            // Иначе уведомление об отсутствии данных.
            if (!contacts.isEmpty()) {
                AtomicInteger count = new AtomicInteger(0);
                StringBuilder stringBuilder = new StringBuilder();
                contacts.forEach((contact) ->
                        stringBuilder.append(count.incrementAndGet())
                                .append(") id - ")
                                .append(contact.getId())
                                .append(", ")
                                .append(contact.getName())
                                .append(", ")
                                .append(contact.getPhone())
                                .append("\n")
                );
                return stringBuilder.toString();
            } else {
                LOGGER.log(Level.WARNING, Constants.LOG_DATA_ABSENT_MSG);
                return Constants.DATA_ABSENT_MSG;
            }
        } else {
            LOGGER.log(Level.WARNING, Constants.LOG_DATA_ABSENT_MSG);
            return Constants.DATA_ABSENT_MSG;
        }
    }
}
