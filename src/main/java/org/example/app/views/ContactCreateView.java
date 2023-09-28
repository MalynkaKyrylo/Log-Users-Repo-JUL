package org.example.app.views;

import java.util.Scanner;

public class ContactCreateView {

    // Получение данных.
    public String[] getData() {

        Scanner scanner = new Scanner(System.in);

        String title = "Enter name: ";
        System.out.print(title);
        String name = scanner.nextLine();
        title = "Enter phone in format xxx xxx-xxxx: ";
        System.out.print(title);
        String phone = scanner.nextLine();

        return new String[] {name, phone};
    }

    // Вывод результата.
    public void getOutput(String output) {
        System.out.println(output);
    }
}
