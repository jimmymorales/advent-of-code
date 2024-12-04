package `2024`

import utils.Coord
import utils.println
import utils.readInput

fun main() {
    fun part1(input: List<String>): Int = input.withIndex().sumOf { (y, row) ->
        row.withIndex().sumOf { (x, _) ->
            input.countOccurrences(Coord(x, y))
        }
    }

    fun part2(input: List<String>): Int = input.withIndex().sumOf { (y, row) ->
        row.withIndex().sumOf { (x, _) ->
            input.countXOccurrences(Coord(x, y))
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test", year = 2024)
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("Day04", year = 2024)
    part1(input).println()
    part2(input).println()
}

private fun List<String>.countOccurrences(start: Coord): Int {
    if (this[start.y][start.x] != 'X') return 0

    var count = 0
    if (getOrNull(start.y - 1)?.getOrNull(start.x) == 'M' && getOrNull(start.y - 2)?.getOrNull(start.x) == 'A' && getOrNull(start.y - 3)?.getOrNull(start.x) == 'S') {
        count++
    }
    if (getOrNull(start.y + 1)?.getOrNull(start.x) == 'M' && getOrNull(start.y + 2)?.getOrNull(start.x) == 'A' && getOrNull(start.y + 3)?.getOrNull(start.x) == 'S') {
        count++
    }
    if (getOrNull(start.y)?.getOrNull(start.x - 1) == 'M' && getOrNull(start.y)?.getOrNull(start.x - 2) == 'A' && getOrNull(start.y)?.getOrNull(start.x - 3) == 'S') {
        count++
    }
    if (getOrNull(start.y)?.getOrNull(start.x + 1) == 'M' && getOrNull(start.y)?.getOrNull(start.x + 2) == 'A' && getOrNull(start.y)?.getOrNull(start.x + 3) == 'S') {
        count++
    }
    if (getOrNull(start.y - 1)?.getOrNull(start.x - 1) == 'M' && getOrNull(start.y - 2)?.getOrNull(start.x - 2) == 'A' && getOrNull(start.y - 3)?.getOrNull(start.x - 3) == 'S') {
        count++
    }
    if (getOrNull(start.y + 1)?.getOrNull(start.x + 1) == 'M' && getOrNull(start.y + 2)?.getOrNull(start.x + 2) == 'A' && getOrNull(start.y + 3)?.getOrNull(start.x + 3) == 'S') {
        count++
    }
    if (getOrNull(start.y - 1)?.getOrNull(start.x + 1) == 'M' && getOrNull(start.y - 2)?.getOrNull(start.x + 2) == 'A' && getOrNull(start.y - 3)?.getOrNull(start.x + 3) == 'S') {
        count++
    }
    if (getOrNull(start.y + 1)?.getOrNull(start.x - 1) == 'M' && getOrNull(start.y + 2)?.getOrNull(start.x - 2) == 'A' && getOrNull(start.y + 3)?.getOrNull(start.x - 3) == 'S') {
        count++
    }
    return count
}

private fun List<String>.countXOccurrences(start: Coord): Int {
    if (this[start.y][start.x] != 'A') return 0

    var count = 0
    if (getOrNull(start.y - 1)?.getOrNull(start.x - 1) == 'M' && getOrNull(start.y + 1)?.getOrNull(start.x + 1) == 'S') {
        if (getOrNull(start.y - 1)?.getOrNull(start.x + 1) == 'M' && getOrNull(start.y + 1)?.getOrNull(start.x - 1) == 'S' ||
            getOrNull(start.y - 1)?.getOrNull(start.x + 1) == 'S' && getOrNull(start.y + 1)?.getOrNull(start.x - 1) == 'M'
        ) {
            count++
        }
    }

    if (getOrNull(start.y - 1)?.getOrNull(start.x - 1) == 'S' && getOrNull(start.y + 1)?.getOrNull(start.x + 1) == 'M') {
        if (getOrNull(start.y - 1)?.getOrNull(start.x + 1) == 'M' && getOrNull(start.y + 1)?.getOrNull(start.x - 1) == 'S' ||
            getOrNull(start.y - 1)?.getOrNull(start.x + 1) == 'S' && getOrNull(start.y + 1)?.getOrNull(start.x - 1) == 'M'
        ) {
            count++
        }
    }

    return count
}