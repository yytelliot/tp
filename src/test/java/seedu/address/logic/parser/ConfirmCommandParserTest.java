package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ConfirmCommand;

public class ConfirmCommandParserTest {

    private final ConfirmCommandParser parser = new ConfirmCommandParser();

    @Test
    public void parse_validArgs_returnsConfirmCommand() {
        assertParseSuccess(parser, "clear", new ConfirmCommand("clear"));
        assertParseSuccess(parser, "  clear  ", new ConfirmCommand("clear"));
    }

    @Test
    public void parse_emptyArgs_returnsConfirmCommandWithEmptyString() {
        assertParseSuccess(parser, "", new ConfirmCommand(""));
    }
}
