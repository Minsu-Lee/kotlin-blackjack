package blackjack.domain.card

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class CardTest : DescribeSpec({
    describe("카드 생성") {
        context("카드 전체 요청") {
            val result = Card.allShuffled()

            it("카드 전체는 52장") {
                result.cards.size shouldBe 52
            }

            it("스페이드 카드는 모든 랭크에 대한 13장") {
                val spadeCards = result.cards.filter { it.suit == Suit.CLUB }
                spadeCards.size shouldBe 13
                spadeCards.map { it.rank } shouldContainAll Rank.entries
            }

            it("다이아몬드 카드 13장") {
                val diamondCards = result.cards.filter { it.suit == Suit.DIAMOND }
                diamondCards.size shouldBe 13
                diamondCards.map { it.rank } shouldContainAll Rank.entries
            }

            it("하트 카드 13장") {
                val heartCard = result.cards.filter { it.suit == Suit.HEART }
                heartCard.size shouldBe 13
                heartCard.map { it.rank } shouldContainAll Rank.entries
            }

            it("스페이드 카드 13장") {
                val spadeCard = result.cards.filter { it.suit == Suit.SPADE }
                spadeCard.size shouldBe 13
                spadeCard.map { it.rank } shouldContainAll Rank.entries
            }
        }
    }
})