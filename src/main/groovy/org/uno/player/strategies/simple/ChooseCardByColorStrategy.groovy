package org.uno.player.strategies.simple

import groovy.util.logging.Log4j2
import org.uno.GameState
import org.uno.cards.ICard
import org.uno.cards.matcher.CardsMatchUtils
import org.uno.player.strategies.IChooseCardStrategy

@Log4j2
class ChooseCardByColorStrategy implements IChooseCardStrategy {

    private GameState state

    ChooseCardByColorStrategy(GameState state) {
        this.state = state
    }

    @Override
    ICard chooseCard(List<ICard> cards) {
        log.debug("Choose by ChooseCardByColorStrategy")
        List<ICard> byColor = cards.findAll { CardsMatchUtils.matchByColor(it, state.getActiveColor()) }
        log.debug("result byColor:${byColor}")
        ICard card
        if (!byColor.isEmpty()) {
            card = byColor.first()
        } else {
            log.debug('No cards by color and value, try wild card..')
            card = findWildCard(cards)
        }
        log.debug("Selected card: $card")
        card
    }
}
