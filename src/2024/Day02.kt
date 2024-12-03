package `2024`

import utils.println
import utils.readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int = input.count { Report(it).isSafe() }

    fun part2(input: List<String>): Int = input.count { Report(it, dampenerThreshold = 1).isSafe() }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test", year = 2024)
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02", year = 2024)
    part1(input).println()
    part2(input).println()
}

private class Report(input: String, private val dampenerThreshold: Int = 0) {
    val levels = input.split(" ").map { it.toInt() }

    fun isSafe(): Boolean {
        return isSafe(range = levels.indices, dampener = 0)
    }

    private fun isSafe(range: Iterable<Int>, dampener: Int): Boolean {
        val isIncreasing = levels[range.last()] > levels[range.first()]
        for ((i, j) in range.zipWithNext()) {
            val a = levels[i]
            val b = levels[j]
            if ((isIncreasing && a >= b) || (!isIncreasing && a <= b) || (abs(a - b) !in 1..3)) {
                if (dampener >= dampenerThreshold) {
                    return false
                }
                return isSafe(range = range.toMutableList().also { it.removeAt(i) }, dampener = 1) ||
                        isSafe(range = range.toMutableList().also { it.removeAt(j) }, dampener = 1)
            }
        }
        return true
    }
}