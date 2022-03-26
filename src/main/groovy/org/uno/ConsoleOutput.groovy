package org.uno

import groovy.util.logging.Log4j2
import org.uno.cards.CardType
import org.uno.cards.ICard
import org.uno.cards.NumericCard
import org.uno.player.IPlayer
import org.uno.player.actions.IPlayerAction

@Log4j2
class ConsoleOutput {


    static printIntro() {
        log.info('''
                                 ____ ___        ________   
                                |    |   \\ ____  \\_____  \\  
                                |    |   //    \\  /   |   \\ 
                                |    |  /|   |  \\/    |    \\
                                |______/ |___|  /\\_______  /
                                              \\/         \\/ 
                                                            
                                                            
''')

        log.info('''
-------------------------------------------------------------------------------------------------
|                                                                                               |
|                                     WELCOME TO UNO GAME                                       |    
|                                                                                               |
-------------------------------------------------------------------------------------------------
''')

        log.info('''
                                 -------------------------
                                 |    ENTER YOUR NAME    |    
                                 -------------------------
''')

    }

    static printEnterPlayersCount() {
        log.info('''
                           ---------------------------------------
                           |    ENTER PLAYERS COUNT.. [2..10]    |    
                           ---------------------------------------
''')
    }

    static printState(GameState state) {
        def color = (state?.activeColor ?: '').toString().toUpperCase()
        def typeOrValue = getCardTypeOrValue(state.activeCard, state.isActionApplied()).toUpperCase()
        log.info("""                                       |
                                       \\/
                                 --------------
                                 |            |    
                                 |${formatTextMiddle(color, 12)}|    
                                 |            |    
                                 |${formatTextMiddle(typeOrValue, 12)}|    
                                 |            |       
                                 --------------
                                       |
                                       \\/""")
    }

    static printAction(int stepNumber, IPlayer player, IPlayerAction action, GameDirection direction) {
        log.info(
                """             STEP: $stepNumber       PLAYER: ${player.getName().toUpperCase()}         CARDS:${player.viewCards().size()}        DIRECTION: ${direction == GameDirection.FORWARD ? '->' : '<-'}                                                      
                           ACTION: $action"""
        )
    }

    static String getCardTypeOrValue(ICard card, boolean isApplied) {
        def result = card.getCardType() == CardType.NUMERIC ? ((NumericCard) card).getNumber() : card.getCardType()
        result = isApplied ? "($result)" : result
        // TODO
        // workaround for 'WILD_DRAW_4' 13-th length,
        // should be improved in general case, but OK for finalized card types set
        result == "(${CardType.WILD_DRAW_4})" ? '(WLD_DRAW_4)' : result
    }

    static String formatTextMiddle(String text, int length) {
        if (length - text.length() < 2) {
            return " $text"
        }
        def prefix = (length - text.length()) / 2 as int
        if (prefix > 0) {
            prefix = (1..prefix).collect { ' ' }.join('')
        }
        def postfix = length - text.length() - prefix.length()
        if (postfix > 0) {
            postfix = (1..postfix).collect { ' ' }.join('')
        }
        "$prefix$text$postfix"
    }


}
