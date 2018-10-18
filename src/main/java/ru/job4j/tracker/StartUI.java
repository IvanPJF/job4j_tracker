package ru.job4j.tracker;

import java.util.List;

/**Класс реализующий работу с tracker.
 * В консоли предлагаем пользователю меню для работы с tracker.
 * В зависимости от выбора пользователя проводим изменения, дополнения, просмотры в tracker.
 *@author IvanPJF (teaching-light@yandex.ru)
 *@since 28.09.2018
 *@version 0.1
 */
public class StartUI {
    private final Input input;
    private final Tracker tracker;
    private boolean run = true;

    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Работа с меню.
     * Меню будет демонстрироваться до тех пор, пока не будет выбран пункт Exit.
     */
    public void play() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        menu.fillActions(this);
        List<Integer> ranges = menu.getRanges();
        do {
            menu.show();
            menu.select(input.ask("Select: ", ranges));
        } while (run);
    }

    /**
     * Завершение показа меню.
     */
    public void stop() {
        this.run = false;
    }

    /**
     * Запуск программы.
     */
    public static void main(String[] args) {
        new StartUI(new ValidateInput(new ConsoleInput()), new Tracker()).play();
    }
}
