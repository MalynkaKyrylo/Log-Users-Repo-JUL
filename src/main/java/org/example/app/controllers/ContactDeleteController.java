package org.example.app.controllers;

import org.example.app.services.ContactDeleteService;
import org.example.app.utils.AppStarter;
import org.example.app.utils.Constants;
import org.example.app.views.ContactDeleteView;

public class ContactDeleteController {

    ContactDeleteService service;
    ContactDeleteView view;

    public ContactDeleteController(ContactDeleteService service, ContactDeleteView view) {
        this.service = service;
        this.view = view;
    }

    public void deleteContact() {
        // Получаем данные.
        // Передаем данные на обработку и получаем результат.
        String str = service.deleteContact(view.getData());
        // Проверяем результат.
        // Если БД отсутствует, выводим сообщение об этом
        // и закрываем приложение.
        // Иначе выводим сообщение и перезапускаем приложение.
        if (str.equals(Constants.DB_ABSENT_MSG)) {
            // Выводим уведомление.
            view.getOutput(str);
            // Закрываем приложение.
            System.exit(0);
        } else {
            // Выводим уведомление.
            view.getOutput(str);
            // Перезапускаем приложение.
            AppStarter.startApp();
        }
    }
}
