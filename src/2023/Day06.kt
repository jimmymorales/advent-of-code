package `2023`

import utils.*

fun main() {
    fun part1(input: List<String>): Int {
        val times = input[0].parseListOfLongs()
        val distances = input[1].parseListOfLongs()
        return times.zip(distances)
            .map { (time, distance) -> countTimesRecordIsBeat(time, distance) }
            .product()
    }

    fun part2(input: List<String>): Int {
        val time = input[0].parseListOfInts().joinToString(separator = "").toLong()
        val distance = input[1].parseListOfInts().joinToString(separator = "").toLong()
        return countTimesRecordIsBeat(time, distance)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test", year = 2023)
    check(part1(testInput) == 288)

    val input = readInput("Day06", year = 2023)
    part1(input).println()
    part2(input).println()
}

private fun countTimesRecordIsBeat(time: Long, distance: Long) = (1..<time)
    .map { it * (time - it) }
    .count { it > distance }
