package org.uno

import groovy.util.logging.Log4j2
import org.uno.cards.CardColor
import org.uno.cards.ICard

@Log4j2
class GameState {

    final ICard activeCard
    private CardColor activeColor
    private boolean isActionApplied = false

    GameState(ICard card) {
        activeCard = card
        activeColor = null
    }

    GameState(ICard card, CardColor color) {
        activeCard = card
        activeColor = color
    }

    void setActiveColor(CardColor color) {
        log.info("Setting active color: $color")
        activeColor = color
    }

    ICard getActiveCard() { activeCard }

    CardColor getActiveColor() { activeColor }

    boolean isActionApplied() { isActionApplied }

    void setActionApplied(boolean isApplied) {
        isActionApplied = isApplied
    }

    boolean isChooseColorRequired() { activeColor == null }


    @Override
    String toString() {
        "$activeColor${isActionApplied ? '' : " $activeCard"}"
    }
}
