package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StartUITest {
	private final PrintStream stdout = System.out;
	private final ByteArrayOutputStream out = new ByteArrayOutputStream();
	private final String ln = System.lineSeparator();

	@Before
	public void loadOutput() {
		System.setOut(new PrintStream(out));
	}

	@After
	public void backOut() {
		System.setOut(stdout);
	}

	private String showMenu() {
		return new StringBuilder()
				.append("0. Add new Item").append(ln)
				.append("1. Show all items").append(ln)
				.append("2. Edit item").append(ln)
				.append("3. Delete item").append(ln)
				.append("4. Find item by Id").append(ln)
				.append("5. Find items by name").append(ln)
				.append("6. Exit Program").append(ln).toString();
	}

	@Test
	public void whenAddItemThenTrackerHasNewItemWithSameName() {
		Tracker tracker = new Tracker();
		Input input = new StubInput(new String[]{"0", "Friday", "One comment", "6"});
		new StartUI(input, tracker).play();
		assertThat(tracker.findAll().get(0).getName(), is("Friday"));
	}

	@Test
	public void whenEditItemThenTrackerHasEditItemWithSameId() {
		Tracker tracker = new Tracker();
		Item item = tracker.add(new Item("Friday", "One comment", 123));
		Input input = new StubInput(new String[]{"2", item.getId(), "Sunday", "Two comment", "6"});
		new StartUI(input, tracker).play();
		assertThat(tracker.findById(item.getId()).getName(), is("Sunday"));
	}

	@Test
	public void whenDeleteItemThenTrackerNotHasItemWithSameId() {
		Tracker tracker = new Tracker();
		Item itemFirst = tracker.add(new Item("Friday", "One comment", 123));
		Item itemSecond = tracker.add(new Item("Sunday", "Two comment", 234));
		Input input = new StubInput(new String[]{"3", itemFirst.getId(), "6"});
		new StartUI(input, tracker).play();
		assertThat(tracker.findAll().get(0).getName(), is("Sunday"));
	}

	@Test
	public void whenFindByIdItemThenTrackerHasItemWithSameId() {
		Tracker tracker = new Tracker();
		Item itemFirst = tracker.add(new Item("Friday", "One comment", 123));
		Item itemSecond = tracker.add(new Item("Sunday", "Two comment", 234));
		Input input = new StubInput(new String[]{"4", itemSecond.getId(), "6"});
		new StartUI(input, tracker).play();
		assertThat(tracker.findById(itemSecond.getId()).getName(), is("Sunday"));
	}

	@Test
	public void whenShowAll() {
		Tracker tracker = new Tracker();
		Item itemFirst = tracker.add(new Item("Friday", "One comment", 123));
		Item itemSecond = tracker.add(new Item("Sunday", "Two comment", 234));
		Input input = new StubInput(new String[]{"1", "6"});
		new StartUI(input, tracker).play();
		String expect =
				new StringBuilder()
						.append(showMenu())
						.append("========== All items =========").append(ln)
						.append(itemFirst.toString()).append(ln)
						.append(itemSecond.toString()).append(ln)
						.append("==============================").append(ln)
						.append(showMenu()).toString();
		assertThat(new String(this.out.toByteArray()), is(expect));
	}

	@Test
	public void whenFindByName() {
		Tracker tracker = new Tracker();
		Item itemFirst = tracker.add(new Item("Friday", "One comment", 123));
		Item itemSecond = tracker.add(new Item("Sunday", "Two comment", 234));
		Input input = new StubInput(new String[]{"5", itemSecond.getName(), "6"});
		new StartUI(input, tracker).play();
		String expect =
				new StringBuilder()
						.append(showMenu())
						.append("====  Find items with name ====").append(ln)
						.append(itemSecond.toString()).append(ln)
						.append("==============================").append(ln)
						.append(showMenu()).toString();
		assertThat(new String(this.out.toByteArray()), is(expect));
	}
}