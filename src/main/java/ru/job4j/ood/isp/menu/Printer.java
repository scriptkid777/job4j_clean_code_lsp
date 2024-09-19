package ru.job4j.ood.isp.menu;

public class Printer implements MenuPrinter {
    @Override
    public void print(Menu menu) {
        StringBuilder ident = new StringBuilder("----");
        int prefix = 4;
        for (Menu.MenuItemInfo item : menu) {
            int currSizePref = item.getNumber().length();
            if (currSizePref == prefix) {
                System.out.printf("%s%s%s%n", ident, item.getNumber(), item.getName());
            } else if (currSizePref > prefix) {
                prefix = currSizePref;
                ident.append("----");
                System.out.printf("%s%s%s%n", ident, item.getNumber(), item.getName());
            } else {
                System.out.printf("%s%s%n", item.getNumber(), item.getName());
                ident.replace(0, ident.length(), "----");
                prefix = 4;
            }
        }
    }
}
