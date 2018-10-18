package ru.job4j.tracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TrackerTest {

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("Test1Name", "Test1Desc", 1989L);
        tracker.add(item);
        assertThat(tracker.findAll().get(0), is(item));
    }

    @Test
    public void whenReplaceDescriptionThenNewDescription() {
        Tracker tracker = new Tracker();
        Item oldItem = new Item("Test1Name", "Test1Desc", 1989L);
        Item nextItem = new Item("Test2Name", "Test2Desc", 1999L);
        tracker.add(oldItem);
        tracker.replace(oldItem.getId(), nextItem);
        assertThat(tracker.findById(oldItem.getId()).getDescription(), is("Test2Desc"));
    }

    @Test
    public void whenDelete2ItemThenRemainder1Item() {
        Item firstItem = new Item("Test1Name", "Test1Desc", 1989L);
        Item secondItem = new Item("Test2Name", "Test2Desc", 1999L);
        Item thirdItem = new Item("Test3Name", "Test3Desc", 2009L);
        List<Item> expect = new ArrayList<>();
        expect.add(secondItem);
        Tracker tracker = new Tracker();
        tracker.add(firstItem);
        tracker.add(secondItem);
        tracker.add(thirdItem);
        tracker.delete(firstItem.getId());
        tracker.delete(thirdItem.getId());
        assertThat(tracker.findAll(), is(expect));
    }

    @Test
    public void whenFindAllThenShowAllItem() {
        Item firstItem = new Item("Test1Name", "Test1Desc", 1989L);
        Item secondItem = new Item("Test2Name", "Test2Desc", 1999L);
        Item thirdItem = new Item("Test3Name", "Test3Desc", 2009L);
        List<Item> expect = new ArrayList<>();
        expect.add(firstItem);
        expect.add(secondItem);
        expect.add(thirdItem);
        Tracker tracker = new Tracker();
        tracker.add(firstItem);
        tracker.add(secondItem);
        tracker.add(thirdItem);
        assertThat(tracker.findAll(), is(expect));
    }

    @Test
    public void whenFindByNameThenShow2Item() {
        Item firstItem = new Item("Test1Name", "Test1Desc", 1989L);
        Item secondItem = new Item("Test2Name", "Test2Desc", 1999L);
        Item thirdItem = new Item("Test1Name", "Test3Desc", 2009L);
        List<Item> expect = new ArrayList<>();
        expect.add(firstItem);
        expect.add(thirdItem);
        Tracker tracker = new Tracker();
        tracker.add(firstItem);
        tracker.add(secondItem);
        tracker.add(thirdItem);
        assertThat(tracker.findByName(firstItem.getName()), is(expect));
    }

    @Test
    public void whenFindByIdThenShow1Item() {
        Tracker tracker = new Tracker();
        Item firstItem = new Item("Test1Name", "Test1Desc", 1989L);
        Item secondItem = new Item("Test2Name", "Test2Desc", 1999L);
        Item thirdItem = new Item("Test1Name", "Test3Desc", 2009L);
        tracker.add(firstItem);
        tracker.add(secondItem);
        tracker.add(thirdItem);
        assertThat(tracker.findById(thirdItem.getId()), is(thirdItem));
    }
}
