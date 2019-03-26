package ru.spbhse.erokhina.parser;

import org.junit.Test;
import ru.spbhse.erokhina.Environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertTrue;

/**
 * Tests the correctness of the Parser.
 */
public class ParserTest {
    @Test
    public void testSimpleEcho() {
        ParserImpl parser = new ParserImpl();

        List<ParsedCommand> actualList = parser.parse("echo aaa", new Environment());
        List<ParsedCommand> expectedList = singletonList(new ParsedCommand("echo", singletonList("aaa")));

        assertTrue(checkEquality(expectedList, actualList));
    }

    @Test
    public void testEchoSeveralArguments() {
        ParserImpl parser = new ParserImpl();

        List<ParsedCommand> actualList = parser.parse("echo aaa bbb ccc", new Environment());
        List<ParsedCommand> expectedList = singletonList(new ParsedCommand("echo", Arrays.asList("aaa", "bbb", "ccc")));

        assertTrue(checkEquality(expectedList, actualList));
    }

    @Test
    public void testEchoWithSingleQuotes() {
        ParserImpl parser = new ParserImpl();

        List<ParsedCommand> actualList = parser.parse("echo \'aaa bbb ccc\'", new Environment());
        List<ParsedCommand> expectedList = singletonList(new ParsedCommand("echo", Collections.singletonList("aaa bbb ccc")));

        assertTrue(checkEquality(expectedList, actualList));
    }

    @Test
    public void testEchoWithEnvironment() {
        ParserImpl parser = new ParserImpl();
        Environment environment = new Environment();

        environment.put("aaa", "10");
        environment.put("bbb", "20");
        environment.put("ccc", "30");

        List<ParsedCommand> actualList = parser.parse("echo $aaa $bbb $ccc", environment);
        List<ParsedCommand> expectedList = singletonList(new ParsedCommand("echo", Arrays.asList("10", "20", "30")));

        assertTrue(checkEquality(expectedList, actualList));
    }

    @Test
    public void testWithPipe() {
        ParserImpl parser = new ParserImpl();

        List<ParsedCommand> actualList = parser.parse("command1 args1 | command2", new Environment());
        List<ParsedCommand> expectedList = Arrays.asList(new ParsedCommand("command1", singletonList("args1")),
                new ParsedCommand("command2", new ArrayList<>()));

        assertTrue(checkEquality(expectedList, actualList));
    }

    @Test
    public void testWithPipeWithoutSpace() {
        ParserImpl parser = new ParserImpl();

        List<ParsedCommand> actualList = parser.parse("command1 args1|command2", new Environment());
        List<ParsedCommand> expectedList = Arrays.asList(new ParsedCommand("command1", singletonList("args1")),
                new ParsedCommand("command2", new ArrayList<>()));

        assertTrue(checkEquality(expectedList, actualList));
    }

    @Test(expected = ParserException.class)
    public void testWithExceptionEmptyCommand() {
        ParserImpl parser = new ParserImpl();

        parser.parse("command1 args1 | | command2", new Environment());
    }

    @Test(expected = ParserException.class)
    public void testWithExceptionOnePipe() {
        ParserImpl parser = new ParserImpl();

        parser.parse("|", new Environment());
    }

    @Test
    public void testSimpleAssignment() {
        ParserImpl parser = new ParserImpl();

        List<ParsedCommand> actualList = parser.parse("val1=val2", new Environment());
        List<ParsedCommand> expectedList = singletonList(new ParsedCommand("assign", Arrays.asList("val1", "val2")));

        assertTrue(checkEquality(expectedList, actualList));
    }

    @Test
    public void testAssignmentWithAnotherCommand() {
        ParserImpl parser = new ParserImpl();

        List<ParsedCommand> actualList = parser.parse("command val1=val2", new Environment());
        List<ParsedCommand> expectedList = singletonList(new ParsedCommand("command", singletonList("val1=val2")));

        assertTrue(checkEquality(expectedList, actualList));
    }

    @Test
    public void testUnknownVars() {
        ParserImpl parser = new ParserImpl();

        List<ParsedCommand> actualList = parser.parse("echo $a $b", new Environment());
        List<ParsedCommand> expectedList = singletonList(new ParsedCommand("echo", Arrays.asList("", "")));

        assertTrue(checkEquality(expectedList, actualList));
    }

    @Test
    public void testEchoWithDoubleQuotes() {
        ParserImpl parser = new ParserImpl();
        Environment environment = new Environment();

        environment.put("aaa", "10");
        environment.put("bbb", "20");
        environment.put("ccc", "30");

        List<ParsedCommand> actualList = parser.parse("echo \"$aaa   $bbb\"    $ccc", environment);
        List<ParsedCommand> expectedList = singletonList(new ParsedCommand("echo", Arrays.asList("10   20", "30")));

        assertTrue(checkEquality(expectedList, actualList));
    }

    private boolean checkEquality(List<ParsedCommand> a, List<ParsedCommand> b) {
        if (a.size() != b.size()) {
            return false;
        }

        int n = a.size();

        for (int i = 0; i < n; i++) {
            if (!checkParsedCommandEquality(a.get(i), b.get(i))) {
                return false;
            }
        }

        return true;
    }

    private boolean checkParsedCommandEquality(ParsedCommand a, ParsedCommand b) {
        if (!a.getCommand().equals(b.getCommand())) {
            return false;
        }

        if (a.getArgs().size() != b.getArgs().size()) {
            return false;
        }

        int n = a.getArgs().size();

        for (int i = 0; i < n; i++) {
            if (!a.getArgs().get(i).equals(b.getArgs().get(i))) {
                return false;
            }
        }

        return true;
    }
}