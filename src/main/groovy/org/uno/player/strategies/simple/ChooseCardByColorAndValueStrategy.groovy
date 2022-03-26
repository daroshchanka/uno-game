package org.uno.player.strategies.simple

import groovy.util.logging.Log4j2
import org.uno.GameState
import org.uno.cards.ICard
import org.uno.player.strategies.IChooseCardStrategy

import static org.uno.cards.matcher.CardsMatchUtils.matchByColor
import static org.uno.cards.matcher.CardsMatchUtils.matchByValue

@Log4j2
class ChooseCardByColorAndValueStrategy implements IChooseCardStrategy {

    private GameState state

    ChooseCardByColorAndValueStrategy(GameState state) {
        this.state = state
    }

    @Override
    ICard chooseCard(List<ICard> cards) {
        log.debug("Choose by ChooseCardByColorAndValueStrategy")
        List<ICard> byColor = cards.findAll { matchByColor(it, state.getActiveColor()) }
        List<ICard> byValue = cards.findAll { matchByValue(it, state.getActiveCard()) }
        ICard card
        if (!byColor.isEmpty()) {
            card = byColor.first()
        } else if (!byValue.isEmpty()) {
            card = byValue.first()
        } else {
            log.debug('No cards by color and value, try wild card..')
            card = findWildCard(cards)
        }
        log.debug("Selected card: $card")
        card
    }
}
