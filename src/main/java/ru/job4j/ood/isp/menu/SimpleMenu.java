package ru.job4j.ood.isp.menu;

import java.util.*;

public class SimpleMenu implements Menu {

    private final List<MenuItem> rootElements = new ArrayList<>();

    @Override
    public boolean add(String parentName, String childName, ActionDelegate actionDelegate) {
   /*  добавьте реализацию*/
        boolean rsl = false;
        if (parentName == null) {
            rootElements.add(new SimpleMenuItem(childName, actionDelegate));
            rsl = true;
        } else {
            Optional<ItemInfo> optItem = findItem(parentName);
            if (optItem.isPresent()) {
              ItemInfo item = optItem.get();
              item.menuItem.getChildren().add(new SimpleMenuItem(childName, actionDelegate));
              rsl = true;
            }
        }
        return  rsl;
    }

    @Override
    public Optional<MenuItemInfo> select(String itemName) {
        Optional<MenuItemInfo> rsl = Optional.empty();
        Optional<ItemInfo> item = findItem(itemName);
        if (item.isPresent()) {
            ItemInfo tempInfo = item.get();
            rsl = Optional.of(new MenuItemInfo(tempInfo.menuItem, tempInfo.number));
        }
        return rsl;
    }

    @Override
    public Iterator<MenuItemInfo> iterator() {

        return new Iterator<MenuItemInfo>() {
            private final List<MenuItemInfo> items = getMenuItemInfo();
            private  int point = 0;
            @Override
            public boolean hasNext() {
                return point < items.size();
            }

            @Override
            public MenuItemInfo next() {
               if (!hasNext()) {
                   throw new NoSuchElementException();
               } else {
                   return items.get(point++);
               }

            }
        };
    }

    private List<MenuItemInfo> getMenuItemInfo() {
        List<MenuItemInfo> items = new ArrayList<>();
        DFSIterator dfsIterator = new DFSIterator();
        while (dfsIterator.hasNext()) {
            ItemInfo tempItem = dfsIterator.next();
            items.add(new MenuItemInfo(tempItem.menuItem, tempItem.number));
        }
        return items;
    }

    private Optional<ItemInfo> findItem(String name) {
        Optional<ItemInfo> rsl = Optional.empty();
        DFSIterator iterator = new DFSIterator();
         while (iterator.hasNext()) {
            ItemInfo item = iterator.next();
            if (name.equals(item.menuItem.getName())) {
                rsl = Optional.of(item);
                break;
            }
         }
        return rsl;
    }

    private static class SimpleMenuItem implements MenuItem {

        private String name;
        private List<MenuItem> children = new ArrayList<>();
        private ActionDelegate actionDelegate;

        public SimpleMenuItem(String name, ActionDelegate actionDelegate) {
            this.name = name;
            this.actionDelegate = actionDelegate;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<MenuItem> getChildren() {
            return children;
        }

        @Override
        public ActionDelegate getActionDelegate() {
            return actionDelegate;
        }
    }

    private class DFSIterator implements Iterator<ItemInfo> {

     private   Deque<MenuItem> stack = new LinkedList<>();

     private   Deque<String> numbers = new LinkedList<>();

        DFSIterator() {
            int number = 1;
            for (MenuItem item : rootElements) {
                stack.addLast(item);
                numbers.addLast(String.valueOf(number++).concat("."));
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }


        @Override
        public ItemInfo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            MenuItem current = stack.removeFirst();
            String lastNumber = numbers.removeFirst();
            List<MenuItem> children = current.getChildren();
            int currentNumber = children.size();
            for (var i = children.listIterator(children.size()); i.hasPrevious();) {
                stack.addFirst(i.previous());
                numbers.addFirst(lastNumber.concat(String.valueOf(currentNumber--)).concat("."));
            }
            return new ItemInfo(current, lastNumber);
        }
    }

    private class ItemInfo {

      private final   MenuItem menuItem;
     private final    String number;

        public ItemInfo(MenuItem menuItem, String number) {
            this.menuItem = menuItem;
            this.number = number;
        }
    }
}