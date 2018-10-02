package ru.job4j.tracker;

/**Класс реализующий Меню.
 *@author IvanPJF (teaching-light@yandex.ru)
 *@since 02.10.2018
 *@version 0.1
 */
public class MenuTracker {

    private Input input;
    private Tracker tracker;
    private UserAction[] actions = new UserAction[7];
    private static final String ADD = "0";
    private static final String ALL = "1";
    private static final String EDIT = "2";
    private static final String DEL = "3";
    private static final String FIND_BY_ID = "4";
    private static final String FIND_BY_NAME = "5";
    private static final String EXIT = "6";

    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Запись пунктов меню(внутренних классов) в массив.
     * @param ui Экземпляр класса StartUI.
     *           Необходим для контроля показа меню.
     */
    public void  fillActions(StartUI ui) {
        this.actions[0] = new AddItem();
        this.actions[1] = new ShowAllItem();
        this.actions[2] = new EditItem();
        this.actions[3] = new DeleteItem();
        this.actions[4] = new FindByIdItem();
        this.actions[5] = new FindByNameItem();
        this.actions[6] = new ExitProgram(ui);

    }

    /**
     * Доступ к пунктам меню в массиве actions.
     * @param key Индекс элемента массива(пункта меню).
     */
    public void select(int key) {
        this.actions[key].execute(this.input, this.tracker);
    }

    /**
     * Длина массива, содержащего пункты меню.
     */
    public int getActionsLength() {
        return this.actions.length;
    }

    /**
     * Вывод информации на консоль(пункты меню).
     */
    public void show() {
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    /**
     * Внутренний класс реализующий добавление заявки.
     */
    private class AddItem implements UserAction {

        /**
         * Ключ меню.
         */
        @Override
        public int key() {
            return Integer.valueOf(ADD);
        }

        /**
         * Создание новой заявки.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("========== Add new item ==========");
            String name = input.ask("Enter name: ");
            String description = input.ask("Enter description: ");
            long create = System.currentTimeMillis();
            Item item = new Item(name, description, create);
            tracker.add(item);
            System.out.println("===== New item with id: " + item.getId() + " =====");
        }

        /**
         * Название пункта меню.
         */
        @Override
        public String info() {
            return String.format("%s. %s",this.key(), "Add new Item");
        }
    }

    /**
     * Внутренний класс реализующий показ всех заявок.
     */
    private class ShowAllItem implements UserAction {

        /**
         * Ключ меню.
         */
        @Override
        public int key() {
            return Integer.valueOf(ALL);
        }

        /**
         * Показ всех заявок.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("========== All items =========");
            for (Item item : tracker.findAll()) {
                System.out.println(item);
            }
            System.out.println("==============================");
        }

        /**
         * Название пункта меню.
         */
        @Override
        public String info() {
            return String.format("%s. %s",this.key(), "Show all items");
        }
    }

    /**
     * Внутренний класс реализующий редактирование заявки.
     */
    private class EditItem implements UserAction {

        /**
         * Ключ меню.
         */
        @Override
        public int key() {
            return Integer.valueOf(EDIT);
        }

        /**
         * Редактирование заявки по id.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
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

        /**
         * Название пункта меню.
         */
        @Override
        public String info() {
            return String.format("%s. %s",this.key(), "Edit item");
        }
    }

    /**
     * Внутренний класс реализующий удаление заявки.
     */
    private class DeleteItem implements UserAction {

        /**
         * Ключ меню.
         */
        @Override
        public int key() {
            return Integer.valueOf(DEL);
        }

        /**
         * Удаление заявки по id.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("======== Delete item =========");
            String id = input.ask("Enter id: ");
            boolean result = tracker.delete(id);
            if (result) {
                System.out.println("========== Deleted ===========");
            } else {
                System.out.println("==== No item with this ID ====");
            }
        }

        /**
         * Название пункта меню.
         */
        @Override
        public String info() {
            return String.format("%s. %s",this.key(), "Delete item");
        }
    }

    /**
     * Внутренний класс реализующий поиск заявки по Id.
     */
    private class FindByIdItem implements UserAction {

        /**
         * Ключ меню.
         */
        @Override
        public int key() {
            return Integer.valueOf(FIND_BY_ID);
        }

        /**
         * Поиск заявки по id.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("====== Find item by Id =======");
            String id = input.ask("Enter id: ");
            Item item = tracker.findById(id);
            if (item != null) {
                System.out.println(item + "\n==============================");
            } else {
                System.out.println("==== No item with this ID ====");
            }
        }

        /**
         * Название пункта меню.
         */
        @Override
        public String info() {
            return String.format("%s. %s",this.key(), "Find item by Id");
        }
    }

    /**
     * Внутренний класс реализующий поиск заявки по имени.
     */
    private class FindByNameItem implements UserAction {

        /**
         * Ключ меню.
         */
        @Override
        public int key() {
            return Integer.valueOf(FIND_BY_NAME);
        }

        /**
         * Поиск заявок по имени.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("====  Find items with name ====");
            String name = input.ask("Enter name: ");
            for (Item item : tracker.findByName(name)) {
                System.out.println(item);
            }
            System.out.println("==============================");
        }

        /**
         * Название пункта меню.
         */
        @Override
        public String info() {
            return String.format("%s. %s",this.key(), "Find items by name");
        }
    }

    /**
     * Внутренний класс реализующий завершение программы.
     */
    private class ExitProgram implements UserAction {
        private final StartUI ui;

        private ExitProgram(StartUI ui) {
            this.ui = ui;
        }

        /**
         * Ключ меню.
         */
        @Override
        public int key() {
            return Integer.valueOf(EXIT);
        }

        /**
         * Выход из программы.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            this.ui.stop();
        }

        /**
         * Название пункта меню.
         */
        @Override
        public String info() {
            return String.format("%s. %s",this.key(), "Exit Program");
        }
    }
}
