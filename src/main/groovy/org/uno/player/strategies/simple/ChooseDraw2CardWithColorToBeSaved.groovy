package org.uno.player.strategies.simple

import groovy.util.logging.Log4j2
import org.uno.GameState
import org.uno.cards.CardType
import org.uno.cards.ICard
import org.uno.cards.matcher.CardsMatchUtils
import org.uno.player.strategies.IChooseCardStrategy

@Log4j2
class ChooseDraw2CardWithColorToBeSaved implements IChooseCardStrategy {

    private GameState state

    ChooseDraw2CardWithColorToBeSaved(GameState state) {
        this.state = state
    }

    @Override
    ICard chooseCard(List<ICard> cards) {
        log.debug("Choose by ChooseDraw2CardWithColorToBeSaved")
        ICard card = cards
                .findAll { CardsMatchUtils.matchByColor(it, state.getActiveColor()) }
                .find { it.getCardType() == CardType.DRAW_2 }
        log.debug("Selected card: $card")
        card
    }
}
