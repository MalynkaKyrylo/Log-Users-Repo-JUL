package org.example.app.controllers;

import org.example.app.services.ContactReadService;
import org.example.app.utils.AppStarter;
import org.example.app.utils.Constants;
import org.example.app.views.ContactReadView;

public class ContactReadController {

    ContactReadService service;
    ContactReadView view;

    public ContactReadController(ContactReadService service, ContactReadView view) {
        this.service = service;
        this.view = view;
    }

    public void readContacts() {
        // Получаем результат.
        String str = service.readContacts();
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
            // Выводим уведомление или данные.
            view.getOutput("\n______ CONTACTS ___________\n" + str);
            // Перезапускаем приложение.
            AppStarter.startApp();
        }
    }
}
