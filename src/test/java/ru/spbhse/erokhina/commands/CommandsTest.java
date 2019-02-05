package ru.spbhse.erokhina.commands;

import org.junit.Test;
import ru.spbhse.erokhina.Environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the logic of the commands.
 */
public class CommandsTest {
    @Test
    public void testEcho() throws IOException {
        CommandExecutor executor = new EchoCommandExecutor();
        Environment environment = new Environment();

        List<String> args = Arrays.asList("first arg", "second arg");

        executor.execute(args, environment);

        assertEquals(Collections.singletonList("first arg second arg"), environment.getPrevCommandOutputLines());
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testEchoCommandWithPrevOutput() throws IOException {
        CommandExecutor executor = new EchoCommandExecutor();
        Environment environment = new Environment();

        List<String> output = Arrays.asList("first arg", "second arg");
        environment.setPrevCommandOutputLines(output);

        executor.execute(new ArrayList<>(), environment);

        assertEquals(output, environment.getPrevCommandOutputLines());
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testCatCommand() throws IOException {
        CommandExecutor executor = new CatCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Collections.singletonList("src/test/resources/test_file.txt"), environment);

        assertEquals(Arrays.asList("Twinkle twinkle little star", "How I wonder what you are"),
                environment.getPrevCommandOutputLines());
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testCatCommandWithPrevOutput() throws IOException {
        CommandExecutor executor = new CatCommandExecutor();
        Environment environment = new Environment();

        List<String> output = Arrays.asList("first line", "second line");
        environment.setPrevCommandOutputLines(output);

        executor.execute(new ArrayList<>(), environment);

        assertEquals(output, environment.getPrevCommandOutputLines());
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testExitCommand() throws IOException {
        CommandExecutor executor = new ExitCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Collections.emptyList(), environment);

        assertTrue(environment.getExitFlag());
    }

    @Test
    public void testWcCommandWithPrevOutput() throws IOException {
        CommandExecutor executor = new WcCommandExecutor();
        Environment environment = new Environment();

        List<String> output = Arrays.asList("word word", "word", "  word  ", "word");
        environment.setPrevCommandOutputLines(output);

        executor.execute(new ArrayList<>(), environment);

        assertEquals(Collections.singletonList("4 5 25"), environment.getPrevCommandOutputLines());
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testAssignCommand() throws IOException {
        CommandExecutor executor = new AssignCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Arrays.asList("a", "50"), environment);

        assertEquals("50", environment.getOrDefault("a", ""));
        assertEquals(new ArrayList<>(), environment.getPrevCommandOutputLines());
        assertFalse(environment.getExitFlag());
    }
}