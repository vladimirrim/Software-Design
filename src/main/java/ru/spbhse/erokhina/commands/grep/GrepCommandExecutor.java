package ru.spbhse.erokhina.commands.grep;

import ru.spbhse.erokhina.Environment;
import ru.spbhse.erokhina.commands.CommandExecutor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CommandExecutor for grep command. Grep command searches the given file for lines containing a match to
 * the given strings or words.
 */
public class GrepCommandExecutor implements CommandExecutor {
    @Override
    public void execute(List<String> args, Environment environment) throws IOException {
        ArgumentsParser argsParser = new ArgumentsParser();

        Arguments arguments;
        try {
            arguments = argsParser.parse(args);
        } catch (ArgumentsParsingException e) {
            System.out.println("Error while parsing arguments: " + e.getMessage());
            return;
        }

        String pattern = arguments.getPattern();
        if (arguments.isWordRegexpFlag()) {
            pattern = "\\b" + pattern + "\\b";
        }

        int mask = 0;
        if (arguments.isIgnoreCaseFlag()) {
            mask = Pattern.CASE_INSENSITIVE;
        }

        List<String> inputLines;
        List<String> result = new ArrayList<>();

        if (arguments.getFileName() != null) {
            inputLines = Files.readAllLines(Paths.get(environment.getPathToFile(arguments.getFileName())));
        } else {
            inputLines = environment.getPrevCommandOutputLines();
        }

        Pattern compiledPattern = Pattern.compile(pattern, mask);
        int curPos = 0;

        while (curPos < inputLines.size()) {
            String curLine = inputLines.get(curPos);
            Matcher matcher = compiledPattern.matcher(curLine);

            if (matcher.find()) {
                result.add(curLine);

                for (int i = 0; i < arguments.getLinesAfterContextNum() &&
                        curPos < inputLines.size() - 1; i++) {
                    curPos++;

                    result.add(inputLines.get(curPos));
                }
            }
            curPos++;
        }

        environment.setPrevCommandOutputLines(result);
    }

}