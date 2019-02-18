package ru.spbhse.erokhina.commands;

import org.junit.Test;
import ru.spbhse.erokhina.Environment;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class LsCommandExecutorTest {

    @Test
    public void testLsTwoArguments() throws IOException {
        CommandExecutor executor = new LsCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Arrays.asList("kok", "kek"), environment);

        assertEquals(Collections.singletonList("Ls: too much arguments"), environment.getPrevCommandOutputLines());
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testLsZeroArguments() throws IOException {
        CommandExecutor executor = new LsCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Collections.singletonList("src/test/resources/"), environment);
        executor.execute(Collections.emptyList(), environment);

        assertEquals(Arrays.asList("dummy_dir", "test_file.txt"), environment.getPrevCommandOutputLines());
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testLsOneArgument() throws IOException {
        CommandExecutor executor = new LsCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Collections.singletonList("src/test/resources/"), environment);

        assertEquals(Arrays.asList("dummy_dir", "test_file.txt"), environment.getPrevCommandOutputLines());
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testLsNoSuchDirectory() throws IOException {
        CommandExecutor executor = new LsCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Collections.singletonList("src/test/resources/test_file.txt"), environment);

        assertEquals(Collections.singletonList("Ls: no such directory: src/test/resources/test_file.txt"),
                environment.getPrevCommandOutputLines());
        assertFalse(environment.getExitFlag());
    }
}