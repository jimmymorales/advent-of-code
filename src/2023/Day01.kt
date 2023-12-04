package `2023`

import utils.println
import utils.readInput

fun main() {
    fun part1(input: List<String>): Int = input.sumOf { line ->
        line.first(Char::isDigit)
            .toString()
            .plus(line.last(Char::isDigit))
            .toInt()
    }

    fun part2(input: List<String>) = input.sumOf { line ->
        val first = line.indices.firstNotNullOf { line.parseDigit(it) }
        val last = line.indices.reversed().firstNotNullOf { line.parseDigit(it) }
        (first + last).toInt()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test", year = 2023)
    check(part1(testInput) == 142)

    val input = readInput("Day01", year = 2023)
    part1(input).println()
    part2(input).println()
}

private fun String.parseDigit(position: Int): String? {
    return when {
        this[position].isDigit() -> this[position].toString()
        this[position] == 'o' && getOrNull(position + 1) == 'n' && getOrNull(position + 2) == 'e' -> "1"
        this[position] == 't' && getOrNull(position + 1) == 'w' && getOrNull(position + 2) == 'o' -> "2"
        this[position] == 't' && getOrNull(position + 1) == 'h' && getOrNull(position + 2) == 'r' && getOrNull(position + 3) == 'e'
                && getOrNull(position + 4) == 'e' -> "3"

        this[position] == 'f' && getOrNull(position + 1) == 'o' && getOrNull(position + 2) == 'u' && getOrNull(position + 3) == 'r' -> "4"
        this[position] == 'f' && getOrNull(position + 1) == 'i' && getOrNull(position + 2) == 'v' && getOrNull(position + 3) == 'e' -> "5"
        this[position] == 's' && getOrNull(position + 1) == 'i' && getOrNull(position + 2) == 'x' -> "6"
        this[position] == 's' && getOrNull(position + 1) == 'e' && getOrNull(position + 2) == 'v' && getOrNull(position + 3) == 'e'
                && getOrNull(position + 4) == 'n' -> "7"

        this[position] == 'e' && getOrNull(position + 1) == 'i' && getOrNull(position + 2) == 'g' && getOrNull(position + 3) == 'h'
                && getOrNull(position + 4) == 't' -> "8"

        this[position] == 'n' && getOrNull(position + 1) == 'i' && getOrNull(position + 2) == 'n' && getOrNull(position + 3) == 'e' -> "9"
        else -> null
    }
}
