package org.uno.cards.matcher

import org.uno.cards.*

class CardsMatchUtils {

    static boolean matchByColor(ICard card, CardColor color) {
        if (!(card instanceof ColoredCard)) {
            return false
        }
        ((ColoredCard) card).getColor() == color
    }

    static boolean matchByValue(ICard card, ICard cardToMatch) {
        if (card.getCardType() == CardType.SKIP) {
            return false
        } else if (card.getCardType() != cardToMatch.getCardType()) {
            return false
        } else if (card.getCardType() == CardType.NUMERIC) {
            return ((NumericCard) card).getNumber() == ((NumericCard) cardToMatch).getNumber()
        }
        true
    }

    static boolean isWild(ICard card) {
        card.getCardType() == CardType.WILD
    }

    static boolean isWildDraw4(ICard card) {
        card.getCardType() == CardType.WILD_DRAW_4
    }
}
