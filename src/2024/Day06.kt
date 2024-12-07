package `2024`

import utils.Coord
import utils.println
import utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        var direction = 3 // 0 = east, 1 = south, 2 = west, 3 = north
        var point = input.indexOfFirst { "^" in it }.let { Coord(input[it].indexOf('^'), it) }
        val visitedPoints = mutableSetOf<Coord>()
        do {
            visitedPoints.add(point)
            val nextPoint = when (direction) {
                0 -> point.copy(x = point.x + 1)
                1 -> point.copy(y = point.y + 1)
                2 -> point.copy(x = point.x - 1)
                3 -> point.copy(y = point.y - 1)
                else -> error("Invalid direction")
            }
            if (input.getOrNull(nextPoint.y)?.getOrNull(nextPoint.x) == '#') {
                direction = (direction + 1) % 4
            } else {
                point = nextPoint
            }
        } while (point.x >= 0 && point.y >= 0 && point.x < input[0].length && point.y < input.size)

        return visitedPoints.count()
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test", year = 2024)
    check(part1(testInput) == 41)
    //check(part2(testInput) == 48L)

    val input = readInput("Day06", year = 2024)
    part1(input).println()
    part2(input).println()
}
