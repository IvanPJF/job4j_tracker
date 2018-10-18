package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ValidateInputTest {
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOut() {
        System.setOut(this.stdout);
    }

    @Test
    public void whenInputNotNumberThenInvalid() {
        ValidateInput input = new ValidateInput(
                new StubInput(new String[] {"qwerty", "1"})
        );
        List<Integer> value = new ArrayList<>();
        value.add(1);
        input.ask("Enter", value);
        String expect =
                new StringBuilder()
                        .append("Please enter validate data again.")
                        .append(System.lineSeparator()).toString();
        assertThat(new String(this.out.toByteArray()), is(expect));

    }

    @Test
    public void whenInputNotInRangeThenInvalid() {
        ValidateInput input = new ValidateInput(
                new StubInput(new String[] {"55", "1"})
        );
        List<Integer> value = new ArrayList<>();
        value.add(1);
        input.ask("Enter", value);
        String expect =
                new StringBuilder()
                        .append("Please select key from menu.")
                        .append(System.lineSeparator()).toString();
        assertThat(new String(this.out.toByteArray()), is(expect));
    }
}
