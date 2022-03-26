package org.uno.player

import groovy.util.logging.Log4j2
import org.uno.GameState
import org.uno.SystemInputUtils
import org.uno.cards.CardColor
import org.uno.cards.ICard
import org.uno.player.actions.PlayCardAction
import org.uno.player.strategies.simple.ChooseCardSimpleStrategyFactory

@Log4j2
class RealPlayer extends BotPlayer {

    RealPlayer(String name) {
        super(name)
    }

    private boolean unoSayed = false

    @Override
    protected CardColor chooseColor() {
        log.info("Your cards:")
        viewCards().eachWithIndex { ICard entry, int i ->
            log.info("[${i + 1}]$entry")
        }
        log.info("Select color:")
        CardColor.values().toList().eachWithIndex { CardColor entry, int i ->
            log.info("[${i + 1}]$entry")
        }
        log.info("Enter choice number...")
        int maxIndex = CardColor.values().size()
        int number = SystemInputUtils.enterIntegerByRule("Please, enter some number between 1 and $maxIndex") {
            assert it.toInteger() in 1..maxIndex
        }.toInteger()
        CardColor color = CardColor.values().toList()[number - 1]
        log.info("Selected color: $color")
        color
    }

    @Override
    protected ICard chooseCard(GameState gameState, List<ICard> fromCards) {
        unoSayed = false
        log.info("Select card to play:")
        log.info("[0] No card to play")
        fromCards.eachWithIndex { ICard entry, int i ->
            log.info("[${i + 1}] $entry")
        }
        log.info("Enter choice number...")

        boolean isValid
        ICard card
        do {
            int number = getInputCardNumber(fromCards.size())
            if (number < 1) {
                return null
            }
            card = fromCards[number - 1]
            isValid = ChooseCardSimpleStrategyFactory.build(gameState).chooseCard([card]) as boolean
            if (!isValid) {
                log.info("Card $card is not valid choise, select another card!")
            }
        } while (!isValid)

        log.info("Selected card: $card")
        card
    }

    @Override
    protected void tryToSayUno(PlayCardAction action) {
        if (unoSayed) {
            action.setWithUno(true)
        }
    }

    private int getInputCardNumber(int maxNumber) {
        unoSayed = false
        String unoMarker = '+U'
        String input = SystemInputUtils.enterIntegerByRule("Please, enter some number between 0 and $maxNumber") {
            assert it.replace(unoMarker, '').toInteger() in 0..maxNumber
        }
        if (input.endsWith(unoMarker)) {
            input = input.replace(unoMarker, '')
            unoSayed = true
        }
        input.toInteger()
    }

}
