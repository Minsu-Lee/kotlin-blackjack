package blackjack.domain

import blackjack.common.Policy

class Participant(
    val name: String,
    val cards: Cards = Cards(),
) {
    fun addCard(card: Card) {
        cards.add(card)
    }

    fun isBust(): Boolean {
        return cards.getScore() > Policy.BUST_SCORE
    }

    fun isBlackJack(): Boolean {
        return cards.getScore() == Policy.BUST_SCORE && cards.size == Policy.INITIAL_CARD_COUNT
    }

    fun isMaxScore(): Boolean {
        return cards.getScore() == Policy.BUST_SCORE
    }

    fun getScore(): Int {
        return cards.getScore()
    }
}