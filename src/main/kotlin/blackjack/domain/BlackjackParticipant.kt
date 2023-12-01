package blackjack.domain

abstract class BlackjackParticipant(val name: String) {
    private var _cards = Cards(mutableListOf())

    val isBusted get() = getScore() > Score.BLACKJACK

    val cards: Cards
        get() = _cards

    fun getScore(): Int {
        return Score(cards.cards.map { it.denomination }).calculate()
    }

    fun getFirstDealCards(twoCards: List<Card>) {
        twoCards.forEach { cards.addCard(it) }
    }

    fun hit(card: Card) {
        cards.addCard(card)
    }

    abstract val canHit: Boolean
}
