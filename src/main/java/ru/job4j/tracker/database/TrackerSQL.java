package ru.job4j.tracker.database;

import ru.job4j.tracker.ITracker;
import ru.job4j.tracker.Item;

import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/** Tracker.
 * Designed for storage and work with items.
 *@author IvanPJF (teaching-light@yandex.ru)
 *@since 10.05.2019
 *@version 0.1
 */
public class TrackerSQL implements ITracker, AutoCloseable {

    private Connection connection;

    public TrackerSQL(Connection connection) {
        this.connection = connection;
        this.tableValid();
    }

    public TrackerSQL() {
        this.init();
    }

    public boolean isConnect() {
        return this.connection != null;
    }

    /**
     * Initializing the database.
     * @return true - if you can connect, else - false.
     */
    public boolean init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            this.connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        this.tableValid();
        return this.connection != null;
    }

    /**
     * Check the availability of the main table to work in the database.
     */
    private void tableValid() {
        try (ResultSet resultSet =
                     this.connection.getMetaData().getTables(null, null, "tracker", null)) {
            if (!resultSet.next()) {
                this.tableCreate();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Create a table.
     * @throws SQLException
     */
    private void tableCreate() throws SQLException {
        try (Statement statement = this.connection.createStatement()) {
            statement.execute("CREATE TABLE tracker(id serial PRIMARY KEY, name varchar(50), description varchar(100), create_date timestamp)");
        }
    }

    /**
     * Add a item to the database.
     * @param item Item without id.
     * @return Item with id.
     */
    @Override
    public Item add(Item item) {
        try (PreparedStatement pst = this.connection.prepareStatement("INSERT INTO tracker(name, description, create_date) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, item.getName());
            pst.setString(2, item.getDescription());
            pst.setTimestamp(3, new Timestamp(item.getCreate()));
            pst.executeUpdate();
            try (ResultSet generateKey = pst.getGeneratedKeys()) {
                if (generateKey.next()) {
                    item.setId(generateKey.getString("id"));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return item;
    }

    /**
     * To change the data of the item.
     * @param id Item id for change.
     * @param item Item with changes.
     * @return
     */
    @Override
    public boolean replace(String id, Item item) {
        boolean result = false;
        try (PreparedStatement pst = this.connection.prepareStatement("UPDATE tracker SET name = ?, description = ?, create_date = ? WHERE id = ?")) {
            pst.setString(1, item.getName());
            pst.setString(2, item.getDescription());
            pst.setTimestamp(3, new Timestamp(item.getCreate()));
            pst.setInt(4, Integer.parseInt(id));
            if (pst.executeUpdate() == 1) {
                result = true;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    /**
     * Delete item.
     * @param id Item id for delete.
     * @return
     */
    @Override
    public boolean delete(String id) {
        boolean result = false;
        try (PreparedStatement pst = this.connection.prepareStatement("DELETE FROM tracker WHERE id = ?")) {
            pst.setInt(1, Integer.parseInt(id));
            if (pst.executeUpdate() == 1) {
                result = true;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    /**
     * Get a list of all items.
     * @return
     */
    @Override
    public List<Item> findAll() {
        List<Item> result = new LinkedList<>();
        try (Statement st = this.connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM tracker")) {
            while (rs.next()) {
                Item newItem = new Item(rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("create_date").getTime());
                newItem.setId(rs.getString("id"));
                result.add(newItem);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    /**
     * Get a list of items filtered by name.
     * @param key Name for filtered.
     * @return
     */
    @Override
    public List<Item> findByName(String key) {
        List<Item> result = new LinkedList<>();
        try (PreparedStatement pst = this.connection.prepareStatement("SELECT * FROM tracker AS t WHERE t.name = ?")) {
            pst.setString(1, key);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Item newItem = new Item(rs.getString("name"),
                            rs.getString("description"),
                            rs.getTimestamp("create_date").getTime());
                    newItem.setId(rs.getString("id"));
                    result.add(newItem);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    /**
     * Get item for id.
     * @param id Item id for search.
     * @return
     */
    @Override
    public Item findById(String id) {
        Item result = null;
        try (PreparedStatement pst = this.connection.prepareStatement("SELECT * FROM tracker AS t WHERE t.id = ?")) {
            pst.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    result = new Item(rs.getString("name"),
                            rs.getString("description"),
                            rs.getTimestamp("create_date").getTime());
                    result.setId(rs.getString("id"));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    /**
     * Close the database connection.
     */
    @Override
    public void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
