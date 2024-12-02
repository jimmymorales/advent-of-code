package `2024`

import utils.println
import utils.readInput
import kotlin.math.abs

fun main() {
    fun List<String>.parseLists(): Pair<MutableList<Int>, MutableList<Int>> {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        map { it.split(" +".toRegex()) }
            .forEach { (l1, l2) ->
                left.add(l1.toInt())
                right.add(l2.toInt())
            }
        return left to right
    }

    fun part1(input: List<String>): Int {
        val (left, right) = input.parseLists()
        left.sort()
        right.sort()
        return left.zip(right).sumOf { (l, r) -> abs(l - r) }
    }

    fun part2(input: List<String>): Int {
        val (left, right) = input.parseLists()
        val rightCount = right.groupingBy { it }.eachCount()
        return left.sumOf { l -> l * (rightCount[l] ?: 0) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test", year = 2024)
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01", year = 2024)
    part1(input).println()
    part2(input).println()
}