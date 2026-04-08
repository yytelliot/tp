package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
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

    protected Set<Tag> getTags() {
        return new HashSet<>(tags);
    }
}
