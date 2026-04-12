package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.model.person.TagsContainKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FindTagCommandParserTest {
    private final FindTagCommandParser parser = new FindTagCommandParser();

    @Test
    public void parse_tagPrefixAtStart_success() {
        FindTagCommand expected = new FindTagCommand(
            new TagsContainKeywordsPredicate(Collections.singletonList("friends")));
        assertParseSuccess(parser, "t/friends", expected);
    }

    @Test
    public void parse_multipleTags_success() {
        FindTagCommand expected = new FindTagCommand(
            new TagsContainKeywordsPredicate(Arrays.asList("friends", "colleagues")));
        assertParseSuccess(parser, "t/friends t/colleagues", expected);
    }

    @Test
    public void parse_emptyInput_failure() {
        assertParseFailure(parser, " ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noTagPrefix_failure() {
        assertParseFailure(parser, "friends",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTagValue_failure() {
        assertParseFailure(parser, "t/", Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_preamblePresent_failure() {
        String input = "random t/friends";
        assertParseFailure(parser, input,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedTags_success() {
        FindTagCommand expected = new FindTagCommand(
            new TagsContainKeywordsPredicate(Arrays.asList("friends", "friends")));
        assertParseSuccess(parser, "t/friends t/friends", expected);
    }

    @Test
    public void parse_unknownPrefix_failure() {
        assertParseFailure(parser, "t/friends z/unexpected", "Unknown prefix: z/");
    }
}
