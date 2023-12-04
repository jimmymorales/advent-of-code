package `2023`

import utils.println
import utils.readInput
import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int = input.filter { game ->
        """(\d+) (\w+)""".toRegex().findAll(game)
            .all {
                val (count, color) = it.destructured
                val max = when (color) {
                    "blue" -> 14
                    "green" -> 13
                    else -> 12
                }
                count.toInt() <= max
            }
    }.sumOf { game ->
        """^Game (\d+):""".toRegex().find(game)!!.groupValues[1].toInt()
    }

    fun part2(input: List<String>): Int = input.sumOf { record ->
        val minimumSetOfCubes = record.split(';')
            .fold(mutableMapOf<String, Int>().withDefault { 0 }) { minCubes, set ->
                """(\d+) (\w+)""".toRegex().findAll(set).forEach {
                    val (count, color) = it.destructured
                    minCubes[color] = max(minCubes.getValue(color), count.toInt())
                }
                minCubes
            }
        minimumSetOfCubes.getValue("blue") * minimumSetOfCubes.getValue("green") * minimumSetOfCubes.getValue("red")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test", year = 2023)
    check(part1(testInput) == 8)

    val input = readInput("Day02", year = 2023)
    part1(input).println()
    part2(input).println()
}
