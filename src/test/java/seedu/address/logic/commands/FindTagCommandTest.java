package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.TagsContainKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTagCommand}.
 */
public class FindTagCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagsContainKeywordsPredicate firstPredicate =
                new TagsContainKeywordsPredicate(Collections.singletonList("friends"));
        TagsContainKeywordsPredicate secondPredicate =
                new TagsContainKeywordsPredicate(Collections.singletonList("owesMoney"));

        FindTagCommand findFirstCommand = new FindTagCommand(firstPredicate);
        FindTagCommand findSecondCommand = new FindTagCommand(secondPredicate);

        assertTrue(findFirstCommand.equals(findFirstCommand));

        FindTagCommand findFirstCommandCopy = new FindTagCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        assertFalse(findFirstCommand.equals(1));

        assertFalse(findFirstCommand.equals(null));

        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noMatchingTags_noPersonFound() {
        String expectedMessage = FindTagCommand.MESSAGE_TAG_NOT_FOUND;
        TagsContainKeywordsPredicate predicate = preparePredicate("unknownTag");
        FindTagCommand command = new FindTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleMatchingTag_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        TagsContainKeywordsPredicate predicate = preparePredicate("friends");
        FindTagCommand command = new FindTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialTag_noPersonFound() {
        String expectedMessage = FindTagCommand.MESSAGE_TAG_NOT_FOUND;
        TagsContainKeywordsPredicate predicate = preparePredicate("fri");
        FindTagCommand command = new FindTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void hashCodeMethod() {
        TagsContainKeywordsPredicate predicate = new TagsContainKeywordsPredicate(Arrays.asList("friends"));
        FindTagCommand findTagCommand = new FindTagCommand(predicate);

        assertEquals(predicate.hashCode(), findTagCommand.hashCode());
    }

    @Test
    public void toStringMethod() {
        TagsContainKeywordsPredicate predicate = new TagsContainKeywordsPredicate(Arrays.asList("friends"));
        FindTagCommand findTagCommand = new FindTagCommand(predicate);
        String expected = FindTagCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";

        assertEquals(expected, findTagCommand.toString());
    }

    private TagsContainKeywordsPredicate preparePredicate(String... userInput) {
        return new TagsContainKeywordsPredicate(Arrays.asList(userInput));
    }
}
