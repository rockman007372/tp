package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.MasterDeck;
import seedu.address.model.ReadOnlyMasterDeck;
import seedu.address.model.card.Card;
import seedu.address.model.deck.Deck;

/**
 * An Immutable Master Deck that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate card(s).";
    public static final String MESSAGE_DUPLICATE_DECK = "Deck list contains duplicate deck(s).";

    private final List<JsonAdaptedCard> persons = new ArrayList<>();
    private final List<JsonAdaptedDeck> decks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedCard> persons,
                                       @JsonProperty("decks") List<JsonAdaptedDeck> decks) {
        this.persons.addAll(persons);
        this.decks.addAll(decks);
    }

    /**
     * Converts a given {@code ReadOnlyDeck} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyMasterDeck source) {
        persons.addAll(source.getCardList().stream().map(JsonAdaptedCard::new).collect(Collectors.toList()));
        decks.addAll(source.getDeckList().stream().map(JsonAdaptedDeck::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Deck} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public MasterDeck toModelType() throws IllegalValueException {
        MasterDeck addressBook = new MasterDeck();

        for (JsonAdaptedCard jsonAdaptedCard : persons) {
            Card card = jsonAdaptedCard.toModelType();
            if (addressBook.hasCard(card)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addCard(card);
        }

        for (JsonAdaptedDeck jsonAdaptedDeck : decks) {
            Deck deck = jsonAdaptedDeck.toModelType();
            if (addressBook.hasDeck(deck)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DECK);
            }
            addressBook.addDeck(deck);
        }

        return addressBook;
    }

}
