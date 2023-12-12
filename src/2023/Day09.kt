package `2023`

import utils.parseListOfInts
import utils.println
import utils.readInput

fun main() {
    fun part1(input: List<String>): Int = input.sumOf { line ->
        var history = line.parseListOfInts()
        var sum = history.last()
        while (!history.all { it == 0 }) {
            history = history.zipWithNext { a, b -> b - a }
            sum += history.last()
        }
        sum
    }

    fun part2(input: List<String>): Int = input.sumOf { line ->
        var history = line.parseListOfInts()
        val elements = mutableListOf(history.first())
        while (!history.all { it == 0 }) {
            history = history.zipWithNext { a, b -> b - a }
            elements.add(0, history.first())
        }
        elements.reduce { acc, l -> l - acc }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test", year = 2023)
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09", year = 2023)
    part1(input).println()
    part2(input).println()
}
