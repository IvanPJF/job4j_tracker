package ru.job4j.tracker.database;

import org.junit.Test;
import ru.job4j.tracker.Item;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TrackerSQLTest {

    public Connection init() throws Exception {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        }
    }

    @Test
    public void checkConnection() throws Exception {
        try (TrackerSQL sql = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            assertThat(sql.isConnect(), is(true));
        }
    }

    @Test
    public void checkDefaultConnection() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL()) {
            assertThat(tracker.isConnect(), is(true));
        }
    }

    @Test
    public void whenAddItemToTracker() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item item = tracker.add(new Item("Ivan", "IvanDesc", System.currentTimeMillis()));
            Item result = tracker.findById(item.getId());
            assertThat(result, is(item));
        }
    }

    @Test
    public void whenReplaceItemToTracker() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item itemAdd = tracker.add(new Item("Ivan", "IvanDesc", System.currentTimeMillis()));
            Item itemReplace = new Item("Sam", "SamDesc", System.currentTimeMillis());
            tracker.replace(itemAdd.getId(), itemReplace);
            Item result = tracker.findById(itemAdd.getId());
            assertThat(result.getId(), is(itemAdd.getId()));
            assertThat(result.getName(), is(itemReplace.getName()));
            assertThat(result.getDescription(), is(itemReplace.getDescription()));
            assertThat(result.getCreate(), is(itemReplace.getCreate()));
        }
    }

    @Test
    public void whenDeleteItemFromTracker() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item itemAdd = tracker.add(new Item("Ivan", "IvanDesc", System.currentTimeMillis()));
            tracker.delete(itemAdd.getId());
            Item result = tracker.findById(itemAdd.getId());
            assertThat(result, is((Item) null));
        }
    }

    @Test
    public void whenFindAllToTracker() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item itemOne = tracker.add(new Item("Ivan", "IvanDesc", System.currentTimeMillis()));
            Item itemTwo = tracker.add(new Item("Sam", "SamDesc", System.currentTimeMillis()));
            List<Item> expected = new LinkedList<>(Arrays.asList(itemOne, itemTwo));
            List<Item> result = new LinkedList<>();
            tracker.findAll(result::add);
            assertThat(result, is(expected));
        }
    }

    @Test
    public void whenFindByNameToTracker() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item itemOne = tracker.add(new Item("Ivan", "IvanDesc", System.currentTimeMillis()));
            Item itemTwo = tracker.add(new Item("Sam", "SamDesc", System.currentTimeMillis()));
            Item itemThree = tracker.add(new Item("Sam", "SamTwoDesc", System.currentTimeMillis()));
            List<Item> expected = new LinkedList<>(Arrays.asList(itemTwo, itemThree));
            List<Item> result = tracker.findByName("Sam");
            assertThat(result, is(expected));
        }
    }

    @Test
    public void whenFindByIdToTracker() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item itemOne = tracker.add(new Item("Ivan", "IvanDesc", System.currentTimeMillis()));
            Item itemTwo = tracker.add(new Item("Sam", "SamDesc", System.currentTimeMillis()));
            Item result = tracker.findById(itemTwo.getId());
            assertThat(result, is(itemTwo));
        }
    }
}