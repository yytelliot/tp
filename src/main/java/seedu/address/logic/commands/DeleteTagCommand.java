package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Deletes tags from one or more existing persons in the address book.
 */
public class DeleteTagCommand extends TagCommand {

    public static final String SUBCOMMAND_WORD = "delete";
    public static final String COMMAND_PHRASE = TagCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_PHRASE
            + ": Deletes tag(s) from person(s) in the address book. "
            + "Parameters: "
            + "INDEX [INDEX]... (must be positive integers) "
            + PREFIX_TAG + "TAG (must be a non-empty string)\n"
            + "Example: " + COMMAND_PHRASE + " "
            + "1 2 "
            + PREFIX_TAG + "Primary1 "
            + PREFIX_TAG + "Mathematics";

    public static final String MESSAGE_SUCCESS = "Deleted tags (%1$s) from student: %2$s";
    public static final String MESSAGE_BATCH_SUCCESS = "Deleted tags from students: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND =
            "No students were updated because none have the specified tags.";

    private final List<Person> affectedPersons = new ArrayList<>();
    private final List<Set<Tag>> tagsDeletedByPerson = new ArrayList<>();

    /**
     * Creates a DeleteTagCommand to remove tags from persons at {@code targetIndices}.
     */
    public DeleteTagCommand(List<Index> targetIndices, Set<Tag> tagsToDelete) {
        super(targetIndices, tagsToDelete);
    }

    @Override
    protected void checkPreconditions(List<Person> targetPersons) throws CommandException {
        affectedPersons.clear();
        tagsDeletedByPerson.clear();
        Set<Tag> requestedTags = getTags();

        for (int i = 0; i < targetPersons.size(); i++) {
            Person person = targetPersons.get(i);
            Set<Tag> tagsToDelete = new HashSet<>(person.getTags());
            tagsToDelete.retainAll(requestedTags);

            if (!tagsToDelete.isEmpty()) {
                affectedPersons.add(person);
                tagsDeletedByPerson.add(tagsToDelete);
            }
        }

        if (affectedPersons.isEmpty()) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }
    }

    @Override
    protected void executeBatch(List<Person> targetPersons, Model model) {
        for (int i = 0; i < affectedPersons.size(); i++) {
            model.deleteTagsFromPerson(affectedPersons.get(i), tagsDeletedByPerson.get(i));
        }
    }

    @Override
    protected String formatSuccessMessage(List<Person> processedPersons) {
        if (affectedPersons.size() == 1) {
            return String.format(MESSAGE_SUCCESS,
                    formatTags(tagsDeletedByPerson.get(0)), affectedPersons.get(0).getName());
        }
        return String.format(MESSAGE_BATCH_SUCCESS, formatPersonTagChanges(affectedPersons, tagsDeletedByPerson));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }
        DeleteTagCommand otherDeleteTagCommand = (DeleteTagCommand) other;
        return getTargetIndices().equals(otherDeleteTagCommand.getTargetIndices())
            && getTags().equals(otherDeleteTagCommand.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTargetIndices(), getTags());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIndices", getTargetIndices())
            .add("tagsToDelete", getTags())
            .toString();
    }
}
