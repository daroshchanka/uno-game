package org.uno.player.actions

class BasePlayerAction implements IPlayerAction {

    private PlayerActionType type

    BasePlayerAction(PlayerActionType type) {
        this.type = type
    }

    @Override
    PlayerActionType getType() {
        type
    }

    @Override
    String toString() {
        type.toString()
    }
}
