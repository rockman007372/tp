package seedu.address.logic.commands.deckcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DECKS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all decks in the master deck to the user.
 */
public class ShowDecksCommand extends Command {

    public static final String COMMAND_WORD = "showDecks";

    public static final String MESSAGE_SUCCESS = "Listed all decks";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
        return new CommandResult(MESSAGE_SUCCESS,
                false, false, false, false, false, false, false, false, false, true);
    }
}
