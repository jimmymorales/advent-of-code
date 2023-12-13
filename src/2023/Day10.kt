package `2023`

import utils.Coord
import utils.println
import utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        val startingCoord = input.indexOfFirst { 'S' in it }
            .let { y -> Coord(x = input[y].indexOf('S'), y = y) }
        val visitedNodes = mutableSetOf(startingCoord)
        var (firstConnectedNode, secondConnectedNode) = input.neighborsPipes(startingCoord)
            .let { it.first() to it.last() }
        var count = 1
        while (firstConnectedNode != secondConnectedNode) {
            visitedNodes.add(firstConnectedNode)
            visitedNodes.add(secondConnectedNode)
            firstConnectedNode = input.neighborsPipes(firstConnectedNode).first { it !in visitedNodes }
            secondConnectedNode = input.neighborsPipes(secondConnectedNode).first { it !in visitedNodes }
            count++
        }
        return count
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    check(part1(readInput("Day10_test_1a", year = 2023)) == 4)
    check(part1(readInput("Day10_test_1b", year = 2023)) == 8)

    check(part2(readInput("Day10_test_2a", year = 2023)) == 4)
    check(part2(readInput("Day10_test_2b", year = 2023)) == 10)

    val input = readInput("Day10", year = 2023)
    part1(input).println()
    part2(input).println()
}

private fun List<String>.neighborsPipes(coord: Coord): List<Coord> = when (this[coord.y][coord.x]) {
    'S' -> buildList {
        // Check top pipe
        this@neighborsPipes.getOrNull(coord.y - 1)?.getOrNull(coord.x)
            ?.takeIf { it in setOf('|', 'F', '7') }
            ?.let { add(Coord(coord.x, coord.y - 1)) }

        // Check right pipe
        this@neighborsPipes.getOrNull(coord.y)?.getOrNull(coord.x + 1)
            ?.takeIf { it in setOf('-', 'J', '7') }
            ?.let { add(Coord(coord.x + 1, coord.y)) }

        // Check bottom pipe
        this@neighborsPipes.getOrNull(coord.y + 1)?.getOrNull(coord.x)
            ?.takeIf { it in setOf('|', 'J', 'L') }
            ?.let { add(Coord(coord.x, coord.y + 1)) }

        // Check left pipe
        this@neighborsPipes.getOrNull(coord.y)?.getOrNull(coord.x - 1)
            ?.takeIf { it in setOf('-', 'F', 'L') }
            ?.let { add(Coord(coord.x - 1, coord.y)) }
    }

    '|' -> listOf(Coord(x = coord.x, y = coord.y - 1), Coord(x = coord.x, y = coord.y + 1))
    '-' -> listOf(Coord(x = coord.x - 1, y = coord.y), Coord(x = coord.x + 1, y = coord.y))
    'L' -> listOf(Coord(x = coord.x + 1, y = coord.y), Coord(x = coord.x, y = coord.y - 1))
    'J' -> listOf(Coord(x = coord.x - 1, y = coord.y), Coord(x = coord.x, y = coord.y - 1))
    '7' -> listOf(Coord(x = coord.x - 1, y = coord.y), Coord(x = coord.x, y = coord.y + 1))
    'F' -> listOf(Coord(x = coord.x + 1, y = coord.y), Coord(x = coord.x, y = coord.y + 1))
    else -> throw IllegalStateException("Invalid pipe")
}