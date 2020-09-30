package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**Класс реализующий Меню.
 *@author IvanPJF (teaching-light@yandex.ru)
 *@since 02.10.2018
 *@version 0.1
 */
public class MenuTracker {
    private Input input;
    private ITracker tracker;
    private List<UserAction> actions = new ArrayList<>();

    public MenuTracker(Input input, ITracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Запись пунктов меню(внутренних классов) в массив.
     * @param ui Экземпляр класса StartUI.
     *           Необходим для контроля показа меню.
     */
    public void fillActions(StartUI ui) {
        this.actions.add(new AddItem(0, "Add new Item"));
        this.actions.add(new ShowAllItem(1, "Show all items"));
        this.actions.add(new EditItem(2, "Edit item"));
        this.actions.add(new DeleteItem(3, "Delete item"));
        this.actions.add(new FindByIdItem(4, "Find item by Id"));
        this.actions.add(new FindByNameItem(5, "Find items by name"));
        this.actions.add(new ExitProgram(6, "Exit Program", ui));
    }

    /**
     * Доступ к пунктам меню в массиве actions.
     * @param key Индекс элемента массива(пункта меню).
     */
    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }

    /**
     * Длина массива, содержащего пункты меню.
     */
    public int getActionsLength() {
        return this.actions.size();
    }

    /**
     * Вывод информации на консоль(пункты меню).
     */
    public void show(Consumer<List<UserAction>> consumer) {
        consumer.accept(this.actions);
    }

    /**
     * Набор возможных вариантов выбора меню
     * @return Список вариантов.
     */
    public List<Integer> getRanges() {
        List<Integer> ranges = new ArrayList<>(this.getActionsLength());
        for (int i = 0; i < this.getActionsLength(); i++) {
            ranges.add(i);
        }
        return ranges;
    }

    /**
     * Внутренний класс реализующий добавление заявки.
     */
    private class AddItem extends BaseAction {
        public AddItem(int key, String name) {
            super(key, name);
        }

        /**
         * Создание новой заявки.
         */
        @Override
        public void execute(Input input, ITracker tracker) {
            System.out.println("========== Add new item ==========");
            String name = input.ask("Enter name: ");
            String description = input.ask("Enter description: ");
            long create = System.currentTimeMillis();
            Item item = new Item(name, description, create);
            tracker.add(item);
            System.out.println("===== New item with id: " + item.getId() + " =====");
        }
    }

    /**
     * Внутренний класс реализующий показ всех заявок.
     */
    private class ShowAllItem extends BaseAction {
        public ShowAllItem(int key, String name) {
            super(key, name);
        }

        /**
         * Показ всех заявок.
         */
        @Override
        public void execute(Input input, ITracker tracker) {
            System.out.println("========== All items =========");
            tracker.findAll(System.out::println);
            System.out.println("==============================");
        }
    }

    /**
     * Внутренний класс реализующий редактирование заявки.
     */
    private class EditItem extends BaseAction {
        public EditItem(int key, String name) {
            super(key, name);
        }

        /**
         * Редактирование заявки по id.
         */
        @Override
        public void execute(Input input, ITracker tracker) {
            System.out.println("========== Edit item =========");
            String id = input.ask("Enter id: ");
            String name = input.ask("Enter name: ");
            String description = input.ask("Enter description: ");
            long create = System.currentTimeMillis();
            Item item = new Item(name, description, create);
            boolean result = tracker.replace(id, item);
            if (result) {
                System.out.println("=========== Edited ===========");
            } else {
                System.out.println("==== No item with this ID ====");
            }
        }
    }

    /**
     * Внутренний класс реализующий удаление заявки.
     */
    private class DeleteItem extends BaseAction {
        public DeleteItem(int key, String name) {
            super(key, name);
        }

        /**
         * Удаление заявки по id.
         */
        @Override
        public void execute(Input input, ITracker tracker) {
            System.out.println("======== Delete item =========");
            String id = input.ask("Enter id: ");
            boolean result = tracker.delete(id);
            if (result) {
                System.out.println("========== Deleted ===========");
            } else {
                System.out.println("==== No item with this ID ====");
            }
        }
    }

    /**
     * Внутренний класс реализующий поиск заявки по Id.
     */
    private class FindByIdItem extends BaseAction {
        public FindByIdItem(int key, String name) {
            super(key, name);
        }

        /**
         * Поиск заявки по id.
         */
        @Override
        public void execute(Input input, ITracker tracker) {
            System.out.println("====== Find item by Id =======");
            String id = input.ask("Enter id: ");
            Item item = tracker.findById(id);
            if (item != null) {
                System.out.println(item + System.lineSeparator() + "==============================");
            } else {
                System.out.println("==== No item with this ID ====");
            }
        }
    }

    /**
     * Внутренний класс реализующий поиск заявки по имени.
     */
    private class FindByNameItem extends BaseAction {
        public FindByNameItem(int key, String name) {
            super(key, name);
        }

        /**
         * Поиск заявок по имени.
         */
        @Override
        public void execute(Input input, ITracker tracker) {
            System.out.println("====  Find items with name ====");
            String name = input.ask("Enter name: ");
            for (Item item : tracker.findByName(name)) {
                System.out.println(item);
            }
            System.out.println("==============================");
        }
    }

    /**
     * Внутренний класс реализующий завершение программы.
     */
    private class ExitProgram extends BaseAction {
        private StartUI ui;
        public ExitProgram(int key, String name, StartUI ui) {
            super(key, name);
            this.ui = ui;
        }

        /**
         * Выход из программы.
         */
        @Override
        public void execute(Input input, ITracker tracker) {
            this.ui.stop();
        }
    }
}
