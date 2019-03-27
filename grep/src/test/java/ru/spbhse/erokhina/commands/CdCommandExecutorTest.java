package ru.spbhse.erokhina.commands;

import org.junit.Test;
import ru.spbhse.erokhina.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class CdCommandExecutorTest {

    @Test(expected = CommandExecutionException.class)
    public void testCdTwoArguments() throws IOException, CommandExecutionException {
        CommandExecutor executor = new CdCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Arrays.asList("kok", "kek"), environment);
    }

    @Test
    public void testCdZeroArguments() throws IOException, CommandExecutionException {
        CommandExecutor executor = new CdCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Collections.singletonList("src"), environment);
        executor.execute(Collections.emptyList(), environment);

        assertTrue(Files.isSameFile(Paths.get(System.getProperty("user.home")),
                Paths.get(environment.getCurrentDirectory())));
        assertFalse(environment.getExitFlag());
    }

    @Test
    public void testCdOneArgument() throws IOException, CommandExecutionException {
        CommandExecutor executor = new CdCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Collections.singletonList("src"), environment);

        assertTrue(Files.isSameFile(Paths.get("src"),
                Paths.get(environment.getCurrentDirectory())));
        assertFalse(environment.getExitFlag());
    }

    @Test(expected = CommandExecutionException.class)
    public void testCdNoSuchDirectory() throws IOException, CommandExecutionException {
        CommandExecutor executor = new CdCommandExecutor();
        Environment environment = new Environment();

        executor.execute(Collections.singletonList("src/test/resources/test_file.txt"), environment);
    }
}