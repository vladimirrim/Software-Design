package ru.spbhse.erokhina.commands;

import org.junit.Test;
import ru.spbhse.erokhina.Environment;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class LsCommandExecutorTest {

    @Test(expected = CommandExecutionException.class)
    public void testLsTwoArguments() throws IOException, CommandExecutionException {
        CommandExecutor executor = new LsCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Arrays.asList("kok", "kek"), environment);
    }

    @Test
    public void testLsZeroArguments() throws IOException, CommandExecutionException {
        CommandExecutor executor = new LsCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Collections.singletonList("src/test/resources/"), environment);
        executor.execute(Collections.emptyList(), environment);

        assertEquals(Arrays.asList("dummy_dir", "f.txt", "test_file.txt"), environment.getPrevCommandOutputLines());
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testLsOneArgument() throws IOException, CommandExecutionException {
        CommandExecutor executor = new LsCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Collections.singletonList("src/test/resources/"), environment);

        assertEquals(Arrays.asList("dummy_dir", "f.txt", "test_file.txt"), environment.getPrevCommandOutputLines());
        assertFalse(environment.getExitFlag());
    }

    @Test(expected = CommandExecutionException.class)
    public void testLsNoSuchDirectory() throws IOException, CommandExecutionException {
        CommandExecutor executor = new LsCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Collections.singletonList("src/test/resources/test_file.txt"), environment);
    }
}