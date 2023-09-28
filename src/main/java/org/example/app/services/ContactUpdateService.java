package org.example.app.services;

import org.example.app.database.DBCheck;
import org.example.app.entities.Contact;
import org.example.app.exceptions.DBException;
import org.example.app.exceptions.UpdateException;
import org.example.app.repositories.ContactUpdateRepository;
import org.example.app.utils.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactUpdateService {

    ContactUpdateRepository repository;
    private static final Logger LOGGER =
            Logger.getLogger(ContactUpdateService.class.getName());

    public ContactUpdateService(ContactUpdateRepository repository) {
        this.repository = repository;
    }

    public String updateContact(String[] data) {
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

        if (errors.size() > 0) {
            try {
                throw new UpdateException("Check inputs for update data.", errors);
            } catch (UpdateException ue) {
                LOGGER.log(Level.WARNING, Constants.LOG_DATA_INPTS_WRONG_MSG);
                return ue.getErrors(errors);
            }
        }

        return repository.updateContact(mapData(data));
    }

    private Map<String, String> validateData(String[] data) {
        String strId = data[0].trim();
        int id = 0;
        // Map для сбора ошибок
        Map<String, String> errors = new HashMap<>();

        try {
            // Код, который может вызвать исключение
            id = Integer.parseInt(strId);
        } catch(NumberFormatException nfe) {
            errors.put("id", nfe.getMessage());
        }

        if (IdValidator.isIdValid(strId))
            errors.put("id", Constants.WRONG_ID_MSG);

        if (id <= 0)
            errors.put("id", Constants.WRONG_ID_MSG);

        if (IdChecker.isIdExists(id))
            errors.put("id", Constants.ID_NO_EXISTS_MSG);

        if (PhoneValidator.isPhoneValid(data[1]))
            errors.put("phone", Constants.WRONG_PHONE_MSG);

        return errors;
    }

    // Преобразовываем массив данных в объект.
    private Contact mapData(String[] data) {
        // Создаем объект.
        Contact contact = new Contact();
        // Устанавливаем значения свойств объекта.
        contact.setId(Integer.parseInt(data[0].trim()));
        contact.setPhone(data[1].trim());
        // Возвращаем объект.
        return contact;
    }
}