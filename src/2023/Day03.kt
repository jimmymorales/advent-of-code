package `2023`

import utils.Coord
import utils.neighbors
import utils.println
import utils.readInput

fun main() {
    fun part1(input: List<String>): Int = buildList {
        var num: String? = null
        for ((x, row) in input.withIndex()) {
            for ((y, item) in row.withIndex()) {
                if (item.isDigit()) {
                    num = (num ?: "") + item
                } else if (num != null) {
                    add(PartNumber(num.toInt(), x, y - num.length, y - 1))
                    num = null
                }
            }
            if (num != null) {
                add(PartNumber(num.toInt(), x, row.length - num.length, row.lastIndex))
                num = null
            }
        }
    }.filter { partNumber ->
        partNumber.neighbors(limitX = 0..input[0].lastIndex, limitY = 0..input.lastIndex)
            .any { input[it.y][it.x] != '.' }
    }.sumOf(PartNumber::num)

    fun part2(input: List<String>): Int = input.asSequence()
        .mapIndexed { y, row ->
            row.mapIndexedNotNull { x, c -> if (c == '*') Coord(x, y) else null }
        }
        .flatten()
        .map {
            it.partNumbersNeighbors(schematic = input, limitX = 0..input[0].lastIndex, limitY = 0..input.lastIndex)
        }
        .filter { it.size == 2 }
        .sumOf { it.first().num * it.last().num }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test", year = 2023)
    check(part1(testInput) == 4361)

    val input = readInput("Day03", year = 2023)
    part1(input).println()
    part2(input).println()
}

private data class PartNumber(val num: Int, val row: Int = 0, val start: Int = 0, val end: Int = 0)

private fun PartNumber.neighbors(
    limitX: IntRange = 0..Int.MAX_VALUE,
    limitY: IntRange = 0..Int.MAX_VALUE
): List<Coord> = buildList {
    add(Coord(start - 1, row))
    add(Coord(end + 1, row))
    for (x in start - 1..end + 1) {
        add(Coord(x, row - 1))
    }
    for (x in start - 1..end + 1) {
        add(Coord(x, row + 1))
    }

}.filter { it.x in limitX && it.y in limitY }

private fun Coord.partNumbersNeighbors(
    schematic: List<String>,
    limitX: IntRange = 0..Int.MAX_VALUE,
    limitY: IntRange = 0..Int.MAX_VALUE,
): List<PartNumber> = buildList {
    val neighbors = ArrayDeque(neighbors(includeDiagonal = true, limitX, limitY))
    while (neighbors.isNotEmpty()) {
        val coord = neighbors.removeFirst()
        if (schematic[coord.y][coord.x].isDigit()) {
            var num = schematic[coord.y][coord.x].toString()
            for (i in coord.x - 1 downTo 0) {
                if (schematic[coord.y][i].isDigit()) {
                    num = schematic[coord.y][i] + num
                    if (i in x - 1..x + 1) {
                        neighbors.remove(Coord(i, coord.y))
                    }
                } else {
                    break
                }
            }
            for (i in coord.x + 1..<schematic[0].length) {
                if (schematic[coord.y][i].isDigit()) {
                    num += schematic[coord.y][i]
                    if (i in x - 1..x + 1) {
                        neighbors.remove(Coord(i, coord.y))
                    }
                } else {
                    break
                }
            }
            add(PartNumber(num.toInt()))
        }
    }
}