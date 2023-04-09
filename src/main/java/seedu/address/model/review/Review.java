package seedu.address.model.review;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.util.Pair;
import seedu.address.model.card.Card;
import seedu.address.model.card.IsSameCardPredicate;
import seedu.address.model.card.UniqueCardList;
import seedu.address.model.deck.Deck;
import seedu.address.model.tag.Tag;

/**
 * Represents a Review session that is currently underway.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Review {

    private final Deck deck;

    private UniqueCardList uniqueReviewCardList;
    private final FilteredList<Card> filteredReviewCardList;

    private final int totalNumCards;
    private Card currCard;
    private final List<Integer> orderOfCards;
    private int currCardIndex = 0; // 0-Indexed
    private final ObservableList<Pair<String, String>> reviewStatsList;

    /**
     * Constructs an instance of review.
     *
     * @param deck Deck to be reviewed
     * @param cardsInDeck Cards inside the deck to be reviewed
     * @param userSetNum The card limit in this review, set by users
     */
    public Review(Deck deck, List<Card> cardsInDeck, int userSetNum) {
        requireAllNonNull(deck, cardsInDeck, userSetNum);

        this.deck = deck;
        totalNumCards = userSetNum < 0
                ? cardsInDeck.size()
                : Integer.min(userSetNum, cardsInDeck.size());

        // Initialize the unique card list
        initReviewCardList(cardsInDeck);
        filteredReviewCardList = new FilteredList<>(uniqueReviewCardList.asUnmodifiableObservableList());

        // Randomise order of cards based on the total number of cards allowed in review
        orderOfCards = new Random().ints(0, cardsInDeck.size())
                .distinct().limit(totalNumCards).boxed().collect(Collectors.toList());

        // initialise first card
        currCard = uniqueReviewCardList.getCard(orderOfCards.get(currCardIndex));
        filteredReviewCardList.setPredicate(new IsSameCardPredicate(currCard));

        // initialize review stats
        reviewStatsList = FXCollections.observableList(new ArrayList<>());
        updateReviewStatsList();
    }

    /**
     * Creates a review instance with fixed predetermined order of cards. Used for testing only.
     */
    protected Review(Deck deck, List<Card> cardsInDeck, int userSetNum, boolean isTestConstructor) {
        requireAllNonNull(deck, cardsInDeck, userSetNum);

        this.deck = deck;
        totalNumCards = userSetNum < 0
                ? cardsInDeck.size()
                : Integer.min(userSetNum, cardsInDeck.size());

        // Initialize the unique card list
        initReviewCardList(cardsInDeck);
        filteredReviewCardList = new FilteredList<>(uniqueReviewCardList.asUnmodifiableObservableList());

        // Normal ordering
        orderOfCards = IntStream.range(0, totalNumCards).boxed().collect(Collectors.toList());

        // initialise first card
        currCard = uniqueReviewCardList.getCard(orderOfCards.get(currCardIndex));
        filteredReviewCardList.setPredicate(new IsSameCardPredicate(currCard));

        // initialize review stats
        reviewStatsList = FXCollections.observableList(new ArrayList<>());
        updateReviewStatsList();
    }

    /**
     * Initialize all cards as unflipped in an UniqueCardList instance.
     *
     * @param cardList List of Cards to initialize UniqueCardList with.
     */
    private void initReviewCardList(List<Card> cardList) {
        uniqueReviewCardList = new UniqueCardList();
        for (Card c : cardList) {
            uniqueReviewCardList.add(c.buildUnflippedCard());
        }
    }

    /**
     * Returns the list of card in this review.
     */
    public ObservableList<Card> getFilteredReviewCardList() {
        return filteredReviewCardList;
    }

    public Deck getDeck() {
        return deck;
    }

    public String getDeckName() {
        return deck.getDeckName();
    }

    private void unflipCard(Card card) {
        this.uniqueReviewCardList.setCard(card, card.buildUnflippedCard());
    }

    private void flipCard(Card card) {
        this.uniqueReviewCardList.setCard(card, card.buildFlippedCard());
    }

    /**
     * Updates the current card whenever the current card is modified in the Review card list.
     */
    private void updateCurrCard() {
        int indexInReview = orderOfCards.get(currCardIndex);
        currCard = uniqueReviewCardList.getCard(indexInReview);
    }

    /**
     * Flips the current card in review.
     */
    public void flipCurrCard() {
        if (currCard.isFlipped()) {
            unflipCard(currCard);
        } else {
            flipCard(currCard);
        }

        updateCurrCard();
    }

    /**
     * Checks the flip state of the current card in Review.
     *
     * @return true if the current card is flipped, otherwise false
     */
    public boolean isCurrCardFlipped() {
        return currCard.isFlipped();
    }

    /**
     * Move to the next card to be under review.
     * @return boolean indicating if card is the last card
     */
    public boolean goToNextCard() {
        if (currCardIndex == totalNumCards - 1) {
            return false;
        }

        unflipCard(currCard); // always unflip current card before moving to next

        currCardIndex++;
        currCard = uniqueReviewCardList.getCard(orderOfCards.get(currCardIndex));
        filteredReviewCardList.setPredicate(new IsSameCardPredicate(currCard));

        updateReviewStatsList();
        return true;
    }

    /**
     * Move back to previous card to be under review.
     * @return boolean indicating if card is the first card.
     */
    public boolean goToPrevCard() {
        if (currCardIndex == 0) {
            return false;
        }

        unflipCard(currCard); // always unflip current card before moving to previous one

        currCardIndex--;
        currCard = uniqueReviewCardList.getCard(orderOfCards.get(currCardIndex));
        filteredReviewCardList.setPredicate(new IsSameCardPredicate(currCard));

        updateReviewStatsList();
        return true;
    }

    /**
     * Returns the current card in the review.
     * Card is always flipped to be consistent with the cards in MasterDeck.
     *
     * @return the current card flipped.
     */
    public Card getCurrCardFlipped() {
        return currCard.buildFlippedCard();
    }

    /**
     * Returns the current card in the review.
     * Card has the same flip state in the review.
     * This method is used for test purposes only.
     *
     * @return the current card with the current flip state.
     */
    public Card getCurrCard() {
        return currCard;
    }

    /**
     * Tags the current card in review as easy/medium/hard
     *
     * @param tag the tag to add to current card.
     */
    public void tagCurrentCard(Tag tag) {
        uniqueReviewCardList.setCard(currCard, currCard.buildCardWithtag(tag));
        updateCurrCard();
        updateReviewStatsList();
    }

    public ObservableList<Pair<String, String>> getReviewStatsList() {
        return reviewStatsList;
    }

    public int getNoOfEasyTags() {
        return (int) orderOfCards.stream()
                .map(uniqueReviewCardList::getCard)
                .filter(card -> card.getTagName().equals("easy")).count();

    }

    public int getNoOfMediumTags() {
        return (int) orderOfCards.stream()
                .map(uniqueReviewCardList::getCard)
                .filter(card -> card.getTagName().equals("medium")).count();
    }

    public int getNoOfHardTags() {
        return (int) orderOfCards.stream()
                .map(uniqueReviewCardList::getCard)
                .filter(card -> card.getTagName().equals("hard")).count();
    }

    public ObservableList<Pair<String, String> > getReviewDeckNameList() {
        return deck.getDeckNameList();
    }

    /**
     * Updates the list of review statistics (deck name, number of cards seen and tag count)
     * The review statistics are updated when next, prev and tagging commands are called.
     */
    public void updateReviewStatsList() {
        Pair<String, String> title = new Pair<>("Deck Name", deck.getDeckName());
        Pair<String, String> cardsSeen = new Pair<>("Current Card Number:",
                String.format("%d/%d", currCardIndex + 1, totalNumCards));
        Pair<String, String> tagCount = new Pair<>("Current Tags:",
                 String.format("%d Easy, %d Medium, %d Hard",
                         getNoOfEasyTags(), getNoOfMediumTags(), getNoOfHardTags()));
        Pair<String, String> navGuide = new Pair<>("", "");
        this.reviewStatsList.clear();
        this.reviewStatsList.addAll(title, cardsSeen, tagCount, navGuide); // warning being called here
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Review)) {
            return false; // also handles null
        }

        Review otherReview = (Review) other;
        return deck.equals(otherReview.deck)
                && uniqueReviewCardList.equals(otherReview.uniqueReviewCardList)
                && filteredReviewCardList.equals(otherReview.filteredReviewCardList)
                && totalNumCards == otherReview.totalNumCards
                && currCard.equals(otherReview.currCard)
                && orderOfCards.equals(otherReview.orderOfCards)
                && currCardIndex == otherReview.currCardIndex
                && reviewStatsList.equals(otherReview.reviewStatsList);
    }

}
