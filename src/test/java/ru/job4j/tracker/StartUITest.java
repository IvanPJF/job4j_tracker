package ru.job4j.tracker;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StartUITest {
	
	@Test
	public void whenAddItemThenTrackerHasNewItemWithSameName() {
		Tracker tracker = new Tracker();
		Input input = new StubInput(new String[]{"0", "Friday", "One comment", "6"});
		new StartUI(input, tracker).play();
		assertThat(tracker.findAll()[0].getName(), is("Friday"));
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
		assertThat(tracker.findAll()[0].getName(), is("Sunday"));
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
}