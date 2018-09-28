package ru.job4j.tracker;

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
}
