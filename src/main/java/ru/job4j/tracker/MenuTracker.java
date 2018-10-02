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
        this.actions[0] = new AddItem(0, "Add new Item");
        this.actions[1] = new ShowAllItem(1, "Show all items");
        this.actions[2] = new EditItem(2, "Edit item");
        this.actions[3] = new DeleteItem(3, "Delete item");
        this.actions[4] = new FindByIdItem(4, "Find item by Id");
        this.actions[5] = new FindByNameItem(5, "Find items by name");
        this.actions[6] = new ExitProgram(6, "Exit Program", ui);

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
        private final int key;
        private final String name;

        public AddItem(int key, String name) {
            this.key = key;
            this.name = name;
        }

        /**
         * Ключ меню.
         */
        @Override
        public int key() {
            return this.key;
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
            return String.format("%s. %s", this.key, this.name);
        }
    }

    /**
     * Внутренний класс реализующий показ всех заявок.
     */
    private class ShowAllItem implements UserAction {
        private final int key;
        private final String name;

        public ShowAllItem(int key, String name) {
            this.key = key;
            this.name = name;
        }

        /**
         * Ключ меню.
         */
        @Override
        public int key() {
            return this.key;
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
            return String.format("%s. %s", this.key, this.name);
        }
    }

    /**
     * Внутренний класс реализующий редактирование заявки.
     */
    private class EditItem implements UserAction {
        private final int key;
        private final String name;

        public EditItem(int key, String name) {
            this.key = key;
            this.name = name;
        }

        /**
         * Ключ меню.
         */
        @Override
        public int key() {
            return this.key;
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
            return String.format("%s. %s", this.key, this.name);
        }
    }

    /**
     * Внутренний класс реализующий удаление заявки.
     */
    private class DeleteItem implements UserAction {
        private final int key;
        private final String name;

        public DeleteItem(int key, String name) {
            this.key = key;
            this.name = name;
        }

        /**
         * Ключ меню.
         */
        @Override
        public int key() {
            return this.key;
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
            return String.format("%s. %s", this.key, this.name);
        }
    }

    /**
     * Внутренний класс реализующий поиск заявки по Id.
     */
    private class FindByIdItem implements UserAction {
        private final int key;
        private final String name;

        public FindByIdItem(int key, String name) {
            this.key = key;
            this.name = name;
        }

        /**
         * Ключ меню.
         */
        @Override
        public int key() {
            return this.key;
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
            return String.format("%s. %s", this.key, this.name);
        }
    }

    /**
     * Внутренний класс реализующий поиск заявки по имени.
     */
    private class FindByNameItem implements UserAction {
        private final int key;
        private final String name;

        public FindByNameItem(int key, String name) {
            this.key = key;
            this.name = name;
        }

        /**
         * Ключ меню.
         */
        @Override
        public int key() {
            return this.key;
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
            return String.format("%s. %s", this.key, this.name);
        }
    }

    /**
     * Внутренний класс реализующий завершение программы.
     */
    private class ExitProgram implements UserAction {
        private final int key;
        private final String name;
        private final StartUI ui;

        public ExitProgram(int key, String name, StartUI ui) {
            this.key = key;
            this.name = name;
            this.ui = ui;
        }

        /**
         * Ключ меню.
         */
        @Override
        public int key() {
            return this.key;
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
            return String.format("%s. %s", this.key, this.name);
        }
    }
}
