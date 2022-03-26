package org.uno.player.strategies

import groovy.util.logging.Log4j2
import org.uno.cards.ICard

import static org.uno.cards.matcher.CardsMatchUtils.isWild
import static org.uno.cards.matcher.CardsMatchUtils.isWildDraw4

@Log4j2
trait IChooseCardStrategy {

    abstract ICard chooseCard(List<ICard> fromCards)

    ICard findWildCard(List<ICard> fromCards) {
        log.debug('Look for wildcard..')
        ICard card = fromCards.find { isWild(it) } ?:
                fromCards.find { isWildDraw4(it) } ?: null
        if (!card) {
            log.debug('Wildcards not found')
        }
        card
    }
}
