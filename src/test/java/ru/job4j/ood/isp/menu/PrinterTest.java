package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

class PrinterTest {

    public static final ActionDelegate STUB_ACTION = System.out::println;
    private final String nextLine = System.lineSeparator();
    private final PrintStream stdOUt = System.out;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void resetDef() {
        System.setOut(stdOUt);
    }

    @Test
    public void whenUsePrinterThenGetSame() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add(Menu.ROOT, "Пройти курс на jobj4", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        MenuPrinter printer = new Printer();
        printer.print(menu);
        String expected = "1.Сходить в магазин" + nextLine
                + "----1.1.Купить продукты" + nextLine
                + "--------1.1.1.Купить хлеб" + nextLine
                + "--------1.1.2.Купить молоко" + nextLine
                + "2.Покормить собаку" + nextLine
                + "3.Пройти курс на jobj4";
        assertThat(expected).isEqualTo(outputStream.toString().trim());
    }
}