package blackjack.domain.player

import blackjack.domain.card.CardDeck
import blackjack.domain.game.strategy.CardsByPlayerViewStrategy
import blackjack.domain.game.strategy.TakeMorePlayerStrategy
import blackjack.domain.player.Player.Companion.INIT_PICK_CARD_NUMBER

class Players(playerNames: List<String>, private val cardDeck: CardDeck) {
    private val _players: List<Player>

    val players: List<Player>
        get() = _players

    init {
        _players = playerNames
            .map { Player(it, cardDeck.pickCards(INIT_PICK_CARD_NUMBER)) }
            .toMutableList()
    }

    fun play(
        takeMorePlayer: TakeMorePlayerStrategy,
        cardsByPlayerView: CardsByPlayerViewStrategy
    ) {
        playersToPlay().forEach {
            playByPlayer(it, cardDeck, takeMorePlayer, cardsByPlayerView)
        }
    }

    private fun playersToPlay(): List<Player> {
        return _players.filter { it.canMoreGame() }
    }

    private fun playByPlayer(
        player: Player,
        cardDeck: CardDeck,
        takeMorePlayer: TakeMorePlayerStrategy,
        cardsByPlayerView: CardsByPlayerViewStrategy
    ) {
        while (player.canMoreGame() && player.wantToTake(takeMorePlayer)) {
            player.addCard(cardDeck.pickCard())
            cardsByPlayerView.print(player, false)
        }
    }
}