package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.cardcommands.DeleteCardCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCardCommand object
 */
public class DeleteCardCommandParser implements Parser<DeleteCardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCardCommand
     * and returns a DeleteCardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCardCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCardCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCardCommand.MESSAGE_USAGE), pe);
        }
    }

}
