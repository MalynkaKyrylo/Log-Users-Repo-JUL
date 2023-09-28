package org.example.app.services;

import org.example.app.database.DBCheck;
import org.example.app.entities.Contact;
import org.example.app.exceptions.CreateException;
import org.example.app.exceptions.DBException;
import org.example.app.repositories.ContactCreateRepository;
import org.example.app.utils.Constants;
import org.example.app.utils.PhoneValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactCreateService {

    ContactCreateRepository repository;
    private static final Logger LOGGER =
            Logger.getLogger(ContactCreateService.class.getName());

    public ContactCreateService(ContactCreateRepository repository) {
        this.repository = repository;
    }

    public String createContact(String[] data) {
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

        Map<String, String> errors = validateData(data);

        if (!errors.isEmpty()) {
            try {
                throw new CreateException("Check inputs", errors);
            } catch (CreateException ce) {
                LOGGER.log(Level.WARNING, Constants.LOG_DATA_INPTS_WRONG_MSG);
                return ce.getErrors(errors);
            }
        }

        return repository.createContact(mapData(data));
    }

    private Map<String, String> validateData(String[] data) {
        // Map для сбора ошибок
        Map<String, String> errors = new HashMap<>();

        if (data[0].trim().isEmpty())
            errors.put("name", Constants.INPUT_REQ_MSG);

        if (PhoneValidator.isPhoneValid(data[1].trim()))
            errors.put("phone", Constants.WRONG_PHONE_MSG);

        return errors;
    }

    // Преобразовываем массив данных в объект.
    private Contact mapData(String[] data) {
        // Создаем объект.
        Contact contact = new Contact();
        // Устанавливаем значения свойств объекта.
        contact.setName(data[0].trim());
        contact.setPhone(data[1].trim());
        // Возвращаем объект.
        return contact;
    }
}
