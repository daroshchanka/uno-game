package org.uno.player.strategies.simple

import org.uno.GameState
import org.uno.cards.CardType
import org.uno.player.strategies.IChooseCardStrategy

class ChooseCardSimpleStrategyFactory {

    static IChooseCardStrategy build(GameState state) {
        if (state.isActionApplied()) {
            return new ChooseCardByColorAndValueStrategy(state)
        } else if (state.getActiveCard().getCardType() == CardType.DRAW_2) {
            return new ChooseCardByValueStrategy(state)
        } else if (state.getActiveCard().getCardType() in [CardType.SKIP, CardType.WILD_DRAW_4]) {
            return new ChooseDraw2CardWithColorToBeSaved(state)
        } else {
            return new ChooseCardByColorAndValueStrategy(state)
        }
    }
}
