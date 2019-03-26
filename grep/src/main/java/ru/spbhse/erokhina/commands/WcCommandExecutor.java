package ru.spbhse.erokhina.commands;

import ru.spbhse.erokhina.Environment;

import java.io.IOException;
import java.util.Collections;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * CommandExecutor for wc command. Wc command reads either standard input or a list of files
 * and generates the following statistics: newline count, word count, and byte count.
 */
public class WcCommandExecutor implements CommandExecutor {
    @Override
    public void execute(List<String> args, Environment environment) throws IOException {
        int words = 0, bytes = 0;

        final List<String> input;

        if (args.isEmpty()) {
            input = environment.getPrevCommandOutputLines();
        } else {
            input = Files.readAllLines(Paths.get(args.get(0)));
        }

        for (String line : input) {
            if (!line.isEmpty()) {
                String[] allWords = line.split("\\s+");
                int curCnt = 0;

                for (String str : allWords) {
                    if (!str.isEmpty()) {
                        curCnt++;
                    }
                }

                words += curCnt;
            }

            bytes += line.getBytes().length;
        }

        environment.setPrevCommandOutputLines(Collections.singletonList(input.size() + " " + words + " " + bytes));
    }

}
