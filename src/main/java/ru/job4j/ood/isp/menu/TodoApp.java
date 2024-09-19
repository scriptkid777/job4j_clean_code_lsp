package ru.job4j.ood.isp.menu;

import java.util.Scanner;

/**
 * 6. Создайте простенький класс TodoApp. Этот класс будет представлять собой консольное приложение, которое позволяет:
 * Добавить элемент в корень меню;
 * Добавить элемент к родительскому элементу;
 * Вызвать действие, привязанное к пункту меню (действие можно сделать константой,
 * например, ActionDelete DEFAULT_ACTION = () -> System.out.println("Some action") и указывать при добавлении элемента в меню);
 * Вывести меню в консоль.
 */
public class TodoApp {
    public static final Integer ADD_POINT = 1;
    public static final Integer ADD_SUB_POINT = 2;
    public static final Integer SHOW_ACTION = 3;
    public static final Integer SHOW_MENU = 4;
    public static final String MENU = """
                Введите 1 для добавления пункта в меню.
                Введите 2, чтобы добавить подпункт в меню.
                Введите 3, чтобы вызвать действие пункта.
                Введите 4, чтобы вывести меню на экран.
                Введите любое другое число для выхода.
            """;
    public static final String POINT_NAME = "Введите название пункта: ";
    public static final String ACTION_NAME = "Введите название пункта, действие которого хотите вызвать: ";
    public static final String SUB_POINT_NAME = "Введите название элемента, подпункт которого хотите внести: ";
    public static final String EXIT = "Конец работы";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new SimpleMenu();
        String action = "make to action";
        start(scanner, menu, action);
    }

    private static void start(Scanner scanner, Menu menu, String action) {
        ActionDelegate defaultAction = () -> System.out.println(action);
        boolean run = true;
        while (run) {
            System.out.println("Menu");
            System.out.println(MENU);
            int choice = Integer.parseInt(scanner.nextLine());
            System.out.println(choice);
            if (choice == ADD_POINT) {
                System.out.println(POINT_NAME);
               String pointName = scanner.nextLine();
               menu.add(Menu.ROOT, pointName, defaultAction);
            } else if (choice == ADD_SUB_POINT) {
                System.out.println(SUB_POINT_NAME);
                String pointName = scanner.nextLine();
                System.out.println(POINT_NAME);
                String subPointName = scanner.nextLine();
                menu.add(pointName, subPointName, defaultAction);
            } else if (choice == SHOW_ACTION) {
                System.out.println(ACTION_NAME);
                String pointName = scanner.nextLine();
                menu.select(pointName).ifPresentOrElse(item -> item.getActionDelegate().delegate(),
                        () -> System.out.println("Такого пункта в меню нет"));
            } else if (choice == SHOW_MENU) {
                MenuPrinter printer = new Printer();
                printer.print(menu);
            } else {
                System.out.println("Exit");
                run = false;
            }
        }
    }
}
