package `2023`

import utils.println
import utils.readInput

fun main() {
    fun part1(input: List<String>, partTwoRules: Boolean = false): Int =
        input.map { it.toHand(partTwoRules) }
            .sortedWith(compareBy({ it.calculateType(partTwoRules) }, { it }))
            .mapIndexed { index, hand ->
                (index + 1) * hand.bid
            }
            .sum()

    fun part2(input: List<String>): Int {
        return part1(input, partTwoRules = true)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test", year = 2023)
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07", year = 2023)
    part1(input).println()
    part2(input).println()
}

private data class Hand(
    val first: Card,
    val second: Card,
    val third: Card,
    val fourth: Card,
    val fifth: Card,
    val bid: Int,
) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int = first.compareTo(other.first).takeIf { it != 0 }
        ?: second.compareTo(other.second).takeIf { it != 0 }
        ?: third.compareTo(other.third).takeIf { it != 0 }
        ?: fourth.compareTo(other.fourth).takeIf { it != 0 }
        ?: fifth.compareTo(other.fifth)
}

private enum class HandType {
    HighCard, OnePair, TwoPair, ThreeOfAKind, FullHouse, FourOfAKind, FiveOfAKind,
}

private enum class Card {
    Joker, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, J, Q, K, A
}

private fun Hand.calculateType(partTwoRules: Boolean = false): HandType = listOf(first, second, third, fourth, fifth)
    .groupingBy { it }
    .eachCount()
    .toMutableMap()
    .let { grouping ->
        if (partTwoRules) {
            // Replace jokers with card with more occurrences
            val jokers = grouping.getOrDefault(Card.Joker, 0)
            grouping.filterKeys { it != Card.Joker }
                .maxByOrNull { it.value }
                ?.let { max ->
                    grouping[max.key] = grouping[max.key]!! + jokers
                    grouping.remove(Card.Joker)
                }
        }
        val counts = grouping.values
        when {
            5 in counts -> HandType.FiveOfAKind
            4 in counts -> HandType.FourOfAKind
            3 in counts -> if (2 in counts) HandType.FullHouse else HandType.ThreeOfAKind
            counts.count { it == 2 } == 2 -> HandType.TwoPair
            2 in counts -> HandType.OnePair
            else -> HandType.HighCard
        }
    }

private fun String.toHand(partTwoRules: Boolean = false): Hand = Hand(
    first = this[0].toCard(partTwoRules),
    second = this[1].toCard(partTwoRules),
    third = this[2].toCard(partTwoRules),
    fourth = this[3].toCard(partTwoRules),
    fifth = this[4].toCard(partTwoRules),
    bid = this.substringAfter(' ').trim().toInt()
)

private fun Char.toCard(partTwoRules: Boolean = false): Card = when (this) {
    '2' -> Card.Two
    '3' -> Card.Three
    '4' -> Card.Four
    '5' -> Card.Five
    '6' -> Card.Six
    '7' -> Card.Seven
    '8' -> Card.Eight
    '9' -> Card.Nine
    'T' -> Card.Ten
    'J' -> if (partTwoRules) Card.Joker else Card.J
    'Q' -> Card.Q
    'K' -> Card.K
    'A' -> Card.A
    else -> throw IllegalStateException("Unknown card")
}
