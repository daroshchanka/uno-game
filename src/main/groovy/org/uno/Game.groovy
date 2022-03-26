package org.uno

import groovy.util.logging.Log4j2
import org.uno.cards.ICard
import org.uno.player.BotPlayer
import org.uno.player.IPlayer
import org.uno.player.RealPlayer
import org.uno.player.actions.IPlayerAction
import org.uno.player.actions.PlayCardAction
import org.uno.player.actions.PlayerActionType
import org.uno.processactions.PlayerActionProcessorFactory

@Log4j2
class Game {

    private Deck deck
    private List<IPlayer> players
    private int stepNumber = 0
    private GameState gameState
    private IPlayer activePlayer
    private GameDirection direction = GameDirection.FORWARD

    static void main(args) {
        new Game().with {
            initGame()
            play()
        }
    }

    void initGame() {
        ConsoleOutput.printIntro()
        String realPlayerName = SystemInputUtils.readLine()
        if (realPlayerName.isEmpty()) {
            log.info("Player name cannot be empty, so, bots will play alone :)")
        }

        ConsoleOutput.printEnterPlayersCount()
        int playersCount = SystemInputUtils.enterIntegerByRule('Please, enter some number between 2 and 10') {
            assert it.toInteger() in 2..10
        }.toInteger()

        players = (1..playersCount - 1).collect { new BotPlayer("player$it") }
        players << (realPlayerName ? new RealPlayer(realPlayerName) : new BotPlayer("player$playersCount"))
        players.shuffle(new Random(Calendar.getInstance().getTimeInMillis()))

        deck = new Deck()
        7.times {
            players.each {
                it.takeCards(deck.take(1))
            }
        }

        ICard activeCard = deck.take(1).first()
        gameState = activeCard.buildGameState()
        printGameState()
    }

    void play() {
        do {
            activePlayer = nextPlayer()
            IPlayerAction action = activePlayer.doAction(gameState)
            processAction(action)
        } while (!activePlayer.viewCards().isEmpty())
        log.info("Game finished, player ${activePlayer.getName()} won!")
    }

    IPlayer nextPlayer() {
        int currentIndex = players.indexOf(activePlayer)
        IPlayer next
        if (direction == GameDirection.FORWARD) {
            next = currentIndex == (players.size() - 1) ? players.first() : players[currentIndex + 1]
        } else {
            next = currentIndex == 0 ? players.last() : players[currentIndex - 1]
        }
        log.debug("Next player -> $next.name")
        next
    }

    void processAction(IPlayerAction action) {
        stepNumber++
        ConsoleOutput.printAction(stepNumber, activePlayer, action, direction)
        PlayerActionProcessorFactory.build(action.getType()).processAction(this, action)
    }

    void processPostAction() {
        ICard newCard = giveCardsToPlayer(1).first()
        log.info("[${activePlayer.getName()}] try to play post-action with card: ${newCard}")
        IPlayerAction postAction = activePlayer.doAction(gameState, [newCard])
        if (postAction.getType() != PlayerActionType.CARD_NOT_FOUND) {
            ConsoleOutput.printAction(stepNumber, activePlayer, postAction, direction)
            PlayerActionProcessorFactory.build(postAction.getType()).processAction(this, postAction)
        } else {
            printGameState()
        }
    }

    void reverseDirection() {
        direction = GameDirection.values().find { it != direction }
    }

    void moveActiveCardToPlayed() {
        deck.moveToPlayedCards(gameState.getActiveCard())
    }

    void updateGameState(PlayCardAction action) {
        gameState = new GameState(action.getCard(), action.getColor())
    }

    List<ICard> giveCardsToPlayer(int amount) {
        List<ICard> cards = deck.take(amount)
        activePlayer.takeCards(cards)
        cards
    }

    ICard getActiveCard() {
        gameState.getActiveCard()
    }

    void setActionApplied() {
        gameState.setActionApplied(true)
    }

    boolean isActionApplied() {
        gameState.isActionApplied()
    }

    IPlayer getActivePlayer() {
        activePlayer
    }

    boolean withRealPlayer() {
        RealPlayer in players*.class.unique()
    }

    void printGameState() {
        ConsoleOutput.printState(gameState)
    }

}
