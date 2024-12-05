package `2024`

import utils.parseListOfInts
import utils.println
import utils.readLinesSplitedByEmptyLine

fun main() {
    fun parseRules(input: List<String>) = input.first()
        .lines()
        .map { it.split("|").let { (a, b) -> a.toInt() to b.toInt() } }
        .groupBy({ it.first }, { it.second })
        .withDefault { emptyList() }

    fun parseUpdates(input: List<String>) = input.last()
        .lineSequence()
        .map { it.parseListOfInts() }

    fun part1(updates: Sequence<List<Int>>, rules: Map<Int, List<Int>>): Int = updates
        .filter { it.isInOrder(rules) }
        .sumOf { it[it.size / 2] }

    fun part2(updates: Sequence<List<Int>>, rules: Map<Int, List<Int>>): Int = updates
        .filterNot { it.isInOrder(rules) }
        .map { it.fixOrder(rules) }
        .sumOf { it[it.size / 2] }

    // test if implementation meets criteria from the description, like:
    val testInput = readLinesSplitedByEmptyLine("Day05_test", year = 2024)
    val testRules = parseRules(testInput)
    val testUpdates = parseUpdates(testInput)
    check(part1(testUpdates, testRules) == 143)
    check(part2(testUpdates, testRules) == 123)

    val input = readLinesSplitedByEmptyLine("Day05", year = 2024)
    val rules = parseRules(input)
    val updates = parseUpdates(input)
    part1(updates, rules).println()
    part2(updates, rules).println()
}

private fun List<Int>.isInOrder(beforeRules: Map<Int, List<Int>>): Boolean {
    for ((i, n) in withIndex()) {
        for (j in 0 until i) {
            if (this[j] in (beforeRules.getValue(n))) return false
        }
    }
    return true
}

private fun List<Int>.fixOrder(beforeRules: Map<Int, List<Int>>): List<Int> {
    val result = toMutableList()
    var i = 0
    while (i < result.size) {
        for (j in 0 until i) {
            if (result[j] in (beforeRules.getValue(result[i]))) {
                val n = result.removeAt(i)
                result.add(j, n)
                i = 0
                break
            }
        }
        i++
    }
    return result
}