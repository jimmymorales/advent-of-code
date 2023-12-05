package `2023`

import utils.println
import utils.readInput
import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int = input.sumOf { card ->
        val points = card.calculatePoints()
        if (points == 0) 0.0 else 2.0.pow(points - 1)
    }.toInt()

    fun part2(input: List<String>): Int {
        val cardCount = IntArray(input.size) { 1 }
        input.forEachIndexed { i, card ->
            val points = card.calculatePoints()
            for (c in i + 1..i + points) {
                cardCount[c] = cardCount[c] + cardCount[i]
            }
        }
        return cardCount.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test", year = 2023)
    check(part1(testInput) == 13)

    val input = readInput("Day04", year = 2023)
    part1(input).println()
    part2(input).println()
}

private fun String.calculatePoints(): Int = """Card\s+\d+:\s+([\d\s]+) \| ([\d\s]+)""".toRegex()
    .find(this)!!
    .destructured
    .let { (winners, mine) ->
        winners.splitBySpaces().intersect(mine.splitBySpaces().toSet()).count()
    }

private fun String.splitBySpaces() = split("""\s+""".toRegex())