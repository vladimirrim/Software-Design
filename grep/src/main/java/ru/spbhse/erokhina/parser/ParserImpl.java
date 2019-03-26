package ru.spbhse.erokhina.parser;

import ru.spbhse.erokhina.Environment;

import java.util.*;

/**
 * Class that implements Parser interface. Divides given string by pipes and returns the list of ParsedCommand.
 */
public class ParserImpl implements Parser {
    private final static char PIPELINE_SYMBOL = '|';
    private final static char SINGLE_QUOTE = '\'';
    private final static char DOUBLE_QUOTE = '\"';
    private final static char DOLLAR_SYMBOL = '$';
    private final static char SPACE_SYMBOL = ' ';
    private final static char ASSIGNMENT_SYMBOL = '=';

    @Override
    public List<ParsedCommand> parse(String str, Environment environment) {
        List<String> strAfterPrimaryParsing = primaryParsing(str);

        List<String> strAfterSecondaryParsing = secondaryParsing(strAfterPrimaryParsing, environment);

        return toParsedCommands(strAfterSecondaryParsing);
    }

    private List<String> primaryParsing(String str) {
        List<String> resList = new ArrayList<>();

        int curPos = 0;
        int n = str.length();
        StringBuilder growingStrBuilder = new StringBuilder();

        while (curPos < n) {
            char curSymbol = str.charAt(curPos);

            switch (curSymbol) {
                case PIPELINE_SYMBOL:
                    if (!growingStrBuilder.toString().isEmpty()) {
                        resList.add(growingStrBuilder.toString());
                        growingStrBuilder = new StringBuilder();
                    }

                    resList.add(String.valueOf(curSymbol));
                    curPos++;
                    break;
                case SINGLE_QUOTE:
                case DOUBLE_QUOTE:
                    if (!growingStrBuilder.toString().isEmpty()) {
                        resList.add(growingStrBuilder.toString());
                        growingStrBuilder = new StringBuilder();
                    }

                    growingStrBuilder.append(String.valueOf(curSymbol));

                    int i = curPos + 1;
                    while (i < n && str.charAt(i) != curSymbol) {
                        growingStrBuilder.append(str.charAt(i));
                        i++;
                    }

                    if (i == n) {
                        throw new ParserException("Found unmatched symbol [" + curSymbol + "] in position: " + curPos);
                    }

                    growingStrBuilder.append(String.valueOf(curSymbol));

                    if (!growingStrBuilder.toString().isEmpty()) {
                        resList.add(growingStrBuilder.toString());
                        growingStrBuilder = new StringBuilder();
                    }

                    curPos = i + 1;
                    break;
                case SPACE_SYMBOL:
                    if (!growingStrBuilder.toString().isEmpty()) {
                        resList.add(growingStrBuilder.toString());
                        growingStrBuilder = new StringBuilder();
                    }

                    resList.add(String.valueOf(curSymbol));

                    while (curPos < n && str.charAt(curPos) == SPACE_SYMBOL) {
                        curPos++;
                    }

                    break;
                default:
                    growingStrBuilder.append(curSymbol);
                    curPos++;
            }
        }

        if (!growingStrBuilder.toString().isEmpty()) {
            resList.add(growingStrBuilder.toString());
        }

        return resList;
    }

    private List<String> secondaryParsing(List<String> list, Environment environment) {
        List<String> resList = new ArrayList<>();
        int curPos = 0;
        int n = list.size();

        while (curPos < n) {
            String curStr = list.get(curPos);

            if (curStr.isEmpty() || curStr.equals(String.valueOf(SPACE_SYMBOL))) {
                curPos++;
                continue;
            }

            if (curStr.equals(String.valueOf(PIPELINE_SYMBOL))) {
                resList.add(curStr);
                curPos++;
                continue;
            }

            if (curStr.charAt(0) == SINGLE_QUOTE) {
                resList.add(curStr.substring(1, curStr.length() - 1));
                curPos++;
                continue;
            }

            if (curStr.charAt(0) == DOUBLE_QUOTE) {
                curStr = curStr.substring(1, curStr.length() - 1);
            }

            resList.add(makeSubstitutions(curStr, environment));

            curPos++;
        }

        return resList;
    }

    private String makeSubstitutions(String str, Environment environment) {
        StringBuilder resStr = new StringBuilder();
        int curPos = 0;
        int n = str.length();

        while (curPos < n) {
            char curSymbol = str.charAt(curPos);

            if (curSymbol == DOLLAR_SYMBOL) {
                StringBuilder var = new StringBuilder();

                int i = curPos + 1;
                while (i < n && str.charAt(i) != SPACE_SYMBOL) {
                    var.append(str.charAt(i));
                    i++;
                }

                String value = environment.getOrDefault(var.toString(), "");

                resStr.append(value);

                curPos = i;
            } else {
                resStr.append(curSymbol);
                curPos++;
            }
        }

        return resStr.toString();
    }

    private List<ParsedCommand> toParsedCommands(List<String> list) {
        List<ParsedCommand> resList = new ArrayList<>();

        int curPos = 0;
        int n = list.size();
        int pos_last_pipe = -1;

        while (curPos <= n) {
            if (curPos == n || list.get(curPos).equals(String.valueOf(PIPELINE_SYMBOL))) {
                List<String> sublist = list.subList(pos_last_pipe + 1, curPos);

                if (sublist.size() == 0) {
                    if (curPos == n && pos_last_pipe != -1) {
                        throw new ParserException("Command after `|' is not found");
                    } else if (curPos != n) {
                        throw new ParserException("Syntax error near unexpected token `|'");
                    }

                    continue;
                }

                if (sublist.size() == 1) {
                    int ind = sublist.get(0).indexOf(ASSIGNMENT_SYMBOL);
                    if (ind == -1) {
                        resList.add(new ParsedCommand(sublist.get(0), new ArrayList<>()));
                    } else {
                        resList.add(new ParsedCommand("assign",
                                Arrays.asList(sublist.get(0).substring(0, ind), sublist.get(0).substring(ind + 1))));
                    }
                } else {
                    resList.add(new ParsedCommand(sublist.get(0), sublist.subList(1, sublist.size())));
                }

                pos_last_pipe = curPos;
            }

            curPos++;
        }

        return resList;
    }
}