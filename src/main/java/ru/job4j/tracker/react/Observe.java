package ru.job4j.tracker.react;

@FunctionalInterface
public interface Observe<T> {

    void recieve(T model);
}
