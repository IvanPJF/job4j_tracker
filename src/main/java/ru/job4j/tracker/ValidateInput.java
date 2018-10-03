package ru.job4j.tracker;

/**Класс наследующий класс ConsoleInput.
 * Для задания вопросов и получения ответов от пользователя.
 *@author IvanPJF (teaching-light@yandex.ru)
 *@since 03.10.2018
 *@version 0.1
 */
public class ValidateInput extends ConsoleInput {

    /**
     * Работа с пользователем через консоль.
     * Задаём вопрос, получаем ответ.
     * Производим обработку исключений. Выкидываем ошибки при неправильных выборах пункта меню.
     * @param question Вопрос пользователю.
     * @param range Набор возможных вариантов выбора меню.
     * @return Ответ пользователя.
     */
    @Override
    public int ask(String question, int[] range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = super.ask(question, range);
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
