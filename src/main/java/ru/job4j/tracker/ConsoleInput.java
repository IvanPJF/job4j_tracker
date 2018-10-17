package ru.job4j.tracker;

import java.util.List;
import java.util.Scanner;

/**Класс реализующий интерфейс Input.
 * Для задания вопросов и получения ответов от пользователя.
 *@author IvanPJF (teaching-light@yandex.ru)
 *@since 28.09.2018
 *@version 0.1
 */
public class ConsoleInput implements Input {

    /**
     * Работа с пользователем через консоль.
     * Задаём вопрос, получаем ответ.
     * @param question Вопрос пользователю.
     * @return Ответ пользователя.
     */
    public String ask(String question) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(question);
        return scanner.nextLine();
    }

    /**
     * Работа с пользователем через консоль.
     * Задаём вопрос, получаем ответ.
     * Если пользователь ввёл невозможное значение пункта меню, выкидывается ошибка.
     * @param question Вопрос пользователю.
     * @param range Набор возможных вариантов выбора меню.
     * @return Ответ пользователя.
     */
    public int ask(String question, List<Integer> range) {
        int key = Integer.valueOf(this.ask(question));
        boolean exist = false;
        for (int index: range) {
            if (index == key) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            throw new MenuOutException("Out of menu range.");
        }
        return key;
    }
}
