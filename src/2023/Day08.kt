package `2023`

import utils.println
import utils.readLinesSplitedbyEmptyLine

fun main() {
    fun part1(input: List<String>): Int {
        val instructions = input[0].toCharArray()
        val map = input[1].parseMap()

        var node = "AAA"
        var step = 0
        while (node != "ZZZ") {
            val inst = instructions[step % instructions.size]
            node = if (inst == 'L') map[node]!!.first else map[node]!!.second
            step++
        }
        return step
    }

    fun part2(input: List<String>): Long {
        val instructions = input[0].toCharArray()
        val map = input[1].parseMap()

        val nodes = map.keys.filter { it.endsWith('A') }.toMutableList()
        var step = 0L
        var nodesEndingInZ = 0
        while (nodesEndingInZ != nodes.size) {
            nodesEndingInZ = 0
            val inst = instructions[(step % instructions.size).toInt()]
            for ((i, node) in nodes.withIndex()) {
                nodes[i] = if (inst == 'L') map[node]!!.first else map[node]!!.second
                if (nodes[i].endsWith('Z')) {
                    nodesEndingInZ++
                }
            }
            step++
        }
        return step
    }

    check(part1(readLinesSplitedbyEmptyLine("Day08_test_1a", year = 2023)) == 2)
    check(part1(readLinesSplitedbyEmptyLine("Day08_test_1b", year = 2023)) == 6)
    check(part2(readLinesSplitedbyEmptyLine("Day08_test_2", year = 2023)) == 6L)

    val input = readLinesSplitedbyEmptyLine("Day08", year = 2023)
    part1(input).println()

    // Need to optimize part 2
    // part2(input).println()
}

private fun String.parseMap(): Map<String, Pair<String, String>> = split("\n")
    .associate {
        """\w+""".toRegex()
            .findAll(it)
            .map(MatchResult::value)
            .toList()
            .let { result ->
                val (key, left, right) = result
                key to (left to right)
            }
    }