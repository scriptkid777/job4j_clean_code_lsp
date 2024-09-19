package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class SimpleMenuTest {

    public static final ActionDelegate STUB_ACTION = System.out::println;

    @Test
    public void whenAddThenReturnSame() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        assertThat(new Menu.MenuItemInfo("Сходить в магазин",
                List.of("Купить продукты"), STUB_ACTION, "1."))
                .isEqualTo(menu.select("Сходить в магазин").get());
        assertThat(new Menu.MenuItemInfo(
                "Купить продукты",
                List.of("Купить хлеб", "Купить молоко"), STUB_ACTION, "1.1."))
                .isEqualTo(menu.select("Купить продукты").get());
        assertThat(new Menu.MenuItemInfo(
                "Покормить собаку", List.of(), STUB_ACTION, "2."))
                .isEqualTo(menu.select("Покормить собаку").get());
        menu.forEach(i -> System.out.println(i.getNumber() + i.getName()));
    }

    @Test
    public void whenSelectThenReturnSame() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add(Menu.ROOT, "Сделать задание на jobj4", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        menu.add("Сделать задание на jobj4", "Создать программу", STUB_ACTION);
        menu.add("Сделать задание на jobj4", "Написать тесты на программу", STUB_ACTION);
        menu.add("Написать тесты на программу", "Сдать задание ментору", STUB_ACTION);
        assertThat(new Menu.MenuItemInfo("Сделать задание на jobj4",
                List.of("Создать программу", "Написать тесты на программу"), STUB_ACTION, "3."))
                .isEqualTo(menu.select("Сделать задание на jobj4").get());
        assertThat(new Menu.MenuItemInfo("Создать программу",
                List.of(), STUB_ACTION, "3.1.")).
                isEqualTo(menu.select("Создать программу").get());
        assertThat(new Menu.MenuItemInfo("Написать тесты на программу",
                List.of("Сдать задание ментору"), STUB_ACTION, "3.2."))
                .isEqualTo(menu.select("Написать тесты на программу").get());
        assertThat(new Menu.MenuItemInfo("Сдать задание ментору",
                List.of(), STUB_ACTION, "3.2.1.")).
                isEqualTo(menu.select("Сдать задание ментору").get());
    }
}