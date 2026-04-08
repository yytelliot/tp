package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Base class for tag subcommands.
 */
public abstract class TagCommand extends BatchCommand {

    public static final String COMMAND_WORD = "tag";

    private final Set<Tag> tags;

    protected TagCommand(List<Index> targetIndices, Set<Tag> tags) {
        super(targetIndices);
        requireNonNull(tags);
        this.tags = new HashSet<>(tags);
    }

    /**
     * Returns a defensive copy of the requested tags for this command.
     */
    protected Set<Tag> getTags() {
        return new HashSet<>(tags);
    }

    /**
     * Formats a set of tags as a sorted, comma-separated list of tag names.
     *
     * @param tags The set of tags to format.
     * @return A string representation of the tags.
     */
    protected static String formatTags(Set<Tag> tags) {
        return tags.stream()
                .map(tag -> tag.tagName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.joining(", "));
    }

    /**
     * Formats each person together with the exact tags that were changed for that person.
     *
     * @param persons The list of persons whose tags were changed.
     * @param tagsByPerson The list of sets of tags corresponding to each person.
     * @return A string representation of the persons and their changed tags.
     */
    protected static String formatPersonTagChanges(List<Person> persons, List<Set<Tag>> tagsByPerson) {
        return IntStream.range(0, persons.size())
                .mapToObj(index -> String.format("%s (%s)",
                        persons.get(index).getName(), formatTags(tagsByPerson.get(index))))
                .collect(Collectors.joining("; "));
    }
}
