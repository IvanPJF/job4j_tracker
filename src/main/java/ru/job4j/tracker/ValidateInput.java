package ru.job4j.tracker;

import java.util.List;

/**Класс наследующий класс ConsoleInput.
 * Для задания вопросов и получения ответов от пользователя.
 *@author IvanPJF (teaching-light@yandex.ru)
 *@since 03.10.2018
 *@version 0.1
 */
public class ValidateInput implements Input {
    private final Input input;

    public ValidateInput(final Input input) {
        this.input = input;
    }

    /**
     * Задаём вопрос, получаем ответ.
     * @param question Вопрос пользователю.
     * @return Эмуляция ответа от пользователя.
     */
    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }

    /**
     * Работа с пользователем через консоль.
     * Задаём вопрос, получаем ответ.
     * Производим обработку исключений. Выкидываем ошибки при неправильных выборах пункта меню.
     * @param question Вопрос пользователю.
     * @param range Набор возможных вариантов выбора меню.
     * @return Ответ пользователя.
     */
    @Override
    public int ask(String question, List<Integer> range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = this.input.ask(question, range);
                invalid = false;
            } catch (MenuOutException moe) {
                System.out.println("Please select key from menu.");
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter validate data again.");
            }
        } while (invalid);
        return value;
    }
}
