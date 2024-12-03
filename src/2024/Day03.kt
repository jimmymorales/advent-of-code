package `2024`

import utils.parseListOfInts
import utils.parseListOfLongs
import utils.println
import utils.readInput

fun main() {
    fun part1(input: List<String>): Int = input.sumOf { line ->
        """(mul\(\d{1,3},\d{1,3}\))""".toRegex()
            .findAll(line)
            .sumOf { it.value.parseListOfInts().let { (x, y) -> x * y } }
    }

    fun part2(input: List<String>): Long {
        var add = true
        return input.sumOf { line ->
            """(mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\))""".toRegex()
                .findAll(line)
                .sumOf {
                    when (it.value) {
                        "do()" -> {
                            add = true
                            0
                        }

                        "don't()" -> {
                            add = false
                            0
                        }

                        else -> if (add) it.value.parseListOfLongs().let { (x, y) -> x * y } else 0
                    }
                }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test", year = 2024)
    check(part1(testInput) == 161)
    check(part2(testInput) == 48L)

    val input = readInput("Day03", year = 2024)
    part1(input).println()
    part2(input).println()
}