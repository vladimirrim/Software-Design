package ru.spbhse.erokhina.commands;

import ru.spbhse.erokhina.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * CommandExecutor for cat command. Cat command reads files sequentially, writing them to standard output.
 */
public class CatCommandExecutor implements CommandExecutor {
    @Override
    public void execute(List<String> args, Environment environment) throws IOException {
        if (args.isEmpty()) {
            environment.setPrevCommandOutputLines(environment.getPrevCommandOutputLines());
        } else {
            environment.setPrevCommandOutputLines(Files.readAllLines(Paths.get(args.get(0))));
        }
    }
}
