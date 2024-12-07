package `2024`

import utils.parseNumbers
import utils.println
import utils.readInput
import java.math.BigInteger

fun main() {
    fun part1(input: List<String>, includeConcatenationOperand: Boolean = false): String {
        var result = BigInteger.ZERO
        input.forEach { line ->
            val (total, operands) = line.split(": ")
                .let { it[0].toBigInteger() to it[1].parseNumbers { n -> n } }
            if (operands.hasValidEquationFor(0, BigInteger.ZERO, total, includeConcatenationOperand)) {
                result = result.add(total)
            }
        }
        return result.toString()
    }

    fun part2(input: List<String>): String {
        return part1(input, includeConcatenationOperand = true)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test", year = 2024)
    check(part1(testInput) == "3749")
    check(part2(testInput) == "11387")

    val input = readInput("Day07", year = 2024)
    part1(input).println()
    part2(input).println()
}

private fun List<String>.hasValidEquationFor(
    pos: Int,
    acc: BigInteger,
    total: BigInteger,
    includeConcatenationOperand: Boolean,
): Boolean {
    if (acc > total) {
        return false
    }
    if (pos == size) {
        return acc == total
    }
    val n = get(pos).toBigInteger()
    return hasValidEquationFor(pos + 1, acc.add(n), total, includeConcatenationOperand) ||
            hasValidEquationFor(pos + 1, if (pos == 0) n else acc.multiply(n), total, includeConcatenationOperand) ||
            (includeConcatenationOperand &&
                    hasValidEquationFor(pos + 1, (acc.toString() + get(pos)).toBigInteger(), total, true))
}
