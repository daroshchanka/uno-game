package org.uno.player.strategies.simple

import groovy.util.logging.Log4j2
import org.uno.GameState
import org.uno.cards.ICard
import org.uno.cards.matcher.CardsMatchUtils
import org.uno.player.strategies.IChooseCardStrategy

@Log4j2
class ChooseCardByValueStrategy implements IChooseCardStrategy {

    private GameState state

    ChooseCardByValueStrategy(GameState state) {
        this.state = state
    }

    @Override
    ICard chooseCard(List<ICard> cards) {
        log.debug("Choose by ChooseCardByValueStrategy")
        List<ICard> byValue = cards.findAll { CardsMatchUtils.matchByValue(it, state.getActiveCard()) }
        ICard card = byValue.isEmpty() ? null : byValue.first()
        log.debug("Selected card: $card")
        card
    }
}
