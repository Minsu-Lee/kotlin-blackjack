package blackjack.view.output

import blackjack.model.PlayRoom
import blackjack.model.card.Card
import blackjack.model.card.CardShape
import blackjack.model.player.DealerHitDecisionMaker
import blackjack.model.player.Player
import blackjack.model.player.PlayerRecord
import blackjack.model.player.PlayerRecords

class ConsoleOutputView : OutputView {

    override fun printInitialMessage(playRoom: PlayRoom) {
        val dealerName = playRoom.dealer.name
        val playerNames = playRoom.guests.joinToString(",") { it.name }
        val initialCardCountForEachPlayer = playRoom.cardDistributor.initialCardCountForEachPlayer

        println("${dealerName}와 ${playerNames}에게 ${initialCardCountForEachPlayer}장씩 카드를 나누었습니다.")
        this.printCardsOfPlayRoom(playRoom, isGameOver = false)
    }

    override fun onPlayerHit(player: Player) {
        when (player) {
            is Player.Guest -> printCardsOfGuest(player, isGameOver = false)
            is Player.Dealer -> println("${player.name}는 ${DealerHitDecisionMaker.MAX_SCORE_FOR_DEALER_CAN_HIT}이하라 한장의 카드를 더 받았습니다.")
        }
    }

    override fun printCardsOfPlayer(player: Player, isGameOver: Boolean) {
        when (player) {
            is Player.Guest -> printCardsOfGuest(player, isGameOver = isGameOver)
            is Player.Dealer -> printCardsOfDealer(player, isGameOver = isGameOver)
        }
    }

    override fun printPlayerRecords(playerRecords: PlayerRecords) {
        println("## 최종 승패 ")
        playerRecords.forEach(this::printPlayerRecord)
    }

    private fun printPlayerRecord(playerRecord: PlayerRecord) {
        println("${playerRecord.player.name} :  ${playerRecord.toDisplayString()}")
    }

    private fun printCardsOfGuest(player: Player, isGameOver: Boolean) {
        printAllCardsOfPlayer(player, withScore = isGameOver)
    }

    private fun printCardsOfDealer(player: Player, isGameOver: Boolean) {
        if (isGameOver) {
            printAllCardsOfPlayer(player, withScore = true)
            return
        }

        print("${player.name}: ")
        val firstCard = player.cards.first()
        val cardsExceptFirst = player.cards.filter { it != firstCard }
        print(cardsExceptFirst.joinToString(", ") { it.displayName })
        println()
    }

    private fun printAllCardsOfPlayer(player: Player, withScore: Boolean) {
        print("${player.name} 카드: ")
        print(player.cards.joinToString(", ") { it.displayName })
        if (withScore) {
            print("  - 결과 : :${player.state.finalScore}")
        }
        println()
    }

    companion object {
        private val CardShape.displayName: String
            get() = when (this) {
                CardShape.SPADES -> "스페이드"
                CardShape.DIAMONDS -> "다이아몬드"
                CardShape.HEARTS -> "하트"
                CardShape.CLUBS -> "클로버"
            }

        val Card.displayName: String
            get() = "${denomination.displayName}${shape.displayName}"

        private fun PlayerRecord.toDisplayString(): String = when (this) {
            is PlayerRecord.GuestWin -> ("승")
            is PlayerRecord.GuestLose -> ("패")
            is PlayerRecord.GuestDraw -> ("무")
            is PlayerRecord.DealerRecord -> {
                val recordString = StringBuilder()
                if (this.win != 0) {
                    recordString.append("${this.win}승 ")
                }
                if (this.lose != 0) {
                    recordString.append("${this.lose}패 ")
                }
                if (this.draw != 0) {
                    recordString.append("${this.lose}무 ")
                }
                recordString.toString()
            }
        }
    }
}