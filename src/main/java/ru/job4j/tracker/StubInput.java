package ru.job4j.tracker;

/**Класс реализующий интерфейс Input.
 * Для задания вопросов и эмуляции получения ответов от пользователя.
 *@author IvanPJF (teaching-light@yandex.ru)
 *@since 28.09.2018
 *@version 0.1
 */
public class StubInput implements Input {
	private String[] answers;
	private int position;
	
	public StubInput(String[] answers) {
		this.answers = answers;
	}

	/**
	 * Задаём вопрос, получаем ответ.
	 * @param question Вопрос пользователю.
	 * @return Эмуляция ответа от пользователя.
	 */
	public String ask(String question) {
		return this.answers[position++];
	}
}