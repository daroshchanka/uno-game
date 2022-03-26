package org.uno

import org.uno.cards.*

class Deck {

    private List<ICard> cards
    private List<ICard> playedCards = []

    Deck() {
        this.cards = generateCardsList()
        shuffle()
    }

    int getSize() {
        cards.size()
    }

    void moveToPlayedCards(ICard card) {
        playedCards << card
    }

    List<ICard> take(int amount) {
        if (amount > getSize()) {
            int availableCount = getSize()
            List<ICard> toTake = cards.clone() as List<ICard>
            reusePlayedCards()
            toTake += take(amount - availableCount)
            return toTake
        }
        List<ICard> toTake = cards[0..(amount - 1)]
        cards -= toTake
        toTake
    }

    private void reusePlayedCards() {
        cards = playedCards.clone() as List<ICard>
        playedCards.clear()
        shuffle()
    }

    void shuffle() {
        (new Random().nextInt(5) + 5).times {
            cards.shuffle(new Random(new Random().nextLong()))
        }
    }

    protected static List<ICard> generateCardsList() {
        List<ICard> cards = []
        CardColor.values().each { CardColor color ->
            cards << new NumericCard(color, 0)
            2.times {
                cards << new ColoredCard(CardType.DRAW_2, color)
                cards << new ColoredCard(CardType.SKIP, color)
                cards << new ColoredCard(CardType.REVERSE, color)
                9.times { int number ->
                    cards << new NumericCard(color, number + 1)
                }
            }
        }
        4.times {
            cards << new BaseCard(CardType.WILD)
            cards << new BaseCard(CardType.WILD_DRAW_4)
        }
        cards
    }

}
