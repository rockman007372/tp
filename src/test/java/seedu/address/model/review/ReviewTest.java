package seedu.address.model.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.tag.Tag.TagName.EASY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.LOOP;
import static seedu.address.testutil.TypicalCards.getTypicalCards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.card.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.tag.Tag;

public class ReviewTest {
    private Review review;
    private final Deck deck = new Deck("testDeck");
    private List<Card> cardsInDeck = new ArrayList<>();
    private final int userSetNum = -1;
    private final int seed = 1;

    @BeforeEach
    public void setUp() {
        cardsInDeck = getTypicalCards();
        review = new Review(deck, cardsInDeck, userSetNum, new Random(seed));
    }

    @Test
    public void constructor_nullDeck_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Review(null, cardsInDeck, userSetNum, new Random(seed)));
    }

    @Test
    public void constructor_nullCardsInDeck_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Review(deck, null, userSetNum, new Random(seed)));
    }

    @Test
    public void constructor_nullRandom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Review(deck, cardsInDeck, userSetNum, null));
    }

    @Test
    public void isCurrCardFlipped_flippedCard_returnsTrue() {
        review.flipCurrCard();
        assertTrue(review.isCurrCardFlipped());
    }

    @Test
    public void isCurrCardFlipped_unflippedCard_returnsFalse() {
        assertFalse(review.isCurrCardFlipped());
    }

    @Test
    public void flipCurrCardTest() {
        Card modelCardFlipped = review.getCurrCardFlipped();
        Card modelCardUnflipped = modelCardFlipped.buildUnflippedCard();

        // Flips the current card to show answer
        review.flipCurrCard();
        Card currFlippedCard = review.getCurrCard();

        assertEquals(modelCardFlipped, currFlippedCard);

        // Unflips the current card again
        review.flipCurrCard();
        Card currUnflippedCard = review.getCurrCard();

        assertEquals(modelCardUnflipped, currUnflippedCard);
    }

    @Test
    public void goToNextCard_lastCard_returnsFalse() {
        for (int i = 0; i < cardsInDeck.size() - 1; i++) {
            assertTrue(review.goToNextCard());
        }
        assertFalse(review.goToNextCard());
    }

    @Test
    public void goToPrevCard_firstCard_returnsFalse() {
        assertFalse(review.goToPrevCard());
    }

    @Test
    void goToNextCard_goToPrevCard_sameReviewState() {
        // Construct review with the same original state
        Review modelReview = new Review(deck, cardsInDeck, userSetNum, new Random(seed));

        // Go forward, then backward
        review.goToNextCard();
        review.goToPrevCard();
        assertEquals(modelReview, review);
    }

    @Test
    void tagCurrentCardTest() {
        Tag easy = new Tag(EASY);
        Card currCard = review.getCurrCardFlipped();
        assertNotEquals(currCard.getTag(), easy); // ensures current card has a different tag

        review.tagCurrentCard(easy);

        Card expectedCard = currCard.buildCardWithtag(easy);
        Card testCard = review.getCurrCardFlipped();
        assertEquals(expectedCard, testCard);
    }

    @Test
    void getNoOfEasyTagsTest() {
        int easyTagCount = (int) cardsInDeck.stream().filter(card -> card.getTagName().equals("easy")).count();
        assertEquals(review.getNoOfEasyTags(), easyTagCount);
    }

    @Test
    void getNoOfMediumTagsTest() {
        int mediumTagCount = (int) cardsInDeck.stream().filter(card -> card.getTagName().equals("medium")).count();
        assertEquals(review.getNoOfMediumTags(), mediumTagCount);
    }

    @Test
    void getNoOfHardTagsTest() {
        int hardTagCount = (int) cardsInDeck.stream().filter(card -> card.getTagName().equals("hard")).count();
        assertEquals(review.getNoOfHardTags(), hardTagCount);
    }

    @Test
    void testEquals() {
        Review modelReview = new Review(deck, cardsInDeck, userSetNum, new Random(seed));

        // same object -> returns true
        assertEquals(review, review);

        // same values -> returns true
        assertEquals(modelReview, review);

        // different types -> returns false
        assertNotEquals(1, review);

        // null -> returns false
        assertNotEquals(null, review);

        // different deck -> return false
        modelReview = new Review(new Deck(""), cardsInDeck, userSetNum, new Random(seed));
        assertNotEquals(modelReview, review);

        // different cards -> return false
        modelReview = new Review(deck, List.of(LOOP), userSetNum, new Random(seed));
        assertNotEquals(modelReview, review);

        // different total number of cards -> return false
        modelReview = new Review(deck, cardsInDeck, 1, new Random(seed));
        assertNotEquals(modelReview, review);

        // different card orders -> return false
        modelReview = new Review(deck, cardsInDeck, userSetNum, new Random());
        assertNotEquals(modelReview, review);

        // different current card -> return false
        modelReview = new Review(deck, cardsInDeck, userSetNum, new Random(seed));
        review.goToNextCard();
        assertNotEquals(modelReview, review);
    }
}
