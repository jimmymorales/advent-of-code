package `2024`

import utils.Coord
import utils.println
import utils.readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>, maxResonance: Int = 1): Int {
        val antinodes = mutableSetOf<Coord>()
        input.parseAntennas()
            .forEach { (_, coords) ->
                for (i in coords.indices) {
                    for (j in i + 1 until coords.size) {
                        val a = coords[i]
                        val b = coords[j]
                        val dx = abs(a.x - b.x)
                        val dy = abs(a.y - b.y)
                        val antennaAntinodes = mutableListOf<Coord>()
                        var resonance = 0
                        if (a.x < b.x) {
                            var l = a.x - dx
                            var m = a.y - dy
                            while (resonance < maxResonance && l in input[0].indices && m in input.indices) {
                                antennaAntinodes.add(Coord(l, m))
                                l -= dx
                                m -= dy
                                resonance++
                            }
                            resonance = 0
                            l = b.x + dx
                            m = b.y + dy
                            while (resonance < maxResonance && l in input[0].indices && m in input.indices) {
                                antennaAntinodes.add(Coord(l, m))
                                l += dx
                                m += dy
                                resonance++
                            }
                        } else {
                            var l = a.x + dx
                            var m = a.y - dy
                            while (resonance < maxResonance && l in input[0].indices && m in input.indices) {
                                antennaAntinodes.add(Coord(l, m))
                                l += dx
                                m -= dy
                                resonance++
                            }
                            resonance = 0
                            l = b.x - dx
                            m = b.y + dy
                            while (resonance < maxResonance && l in input[0].indices && m in input.indices) {
                                antennaAntinodes.add(Coord(l, m))
                                l -= dx
                                m += dy
                                resonance++
                            }
                        }
                        antinodes.addAll(antennaAntinodes + if (maxResonance > 1) listOf(a, b) else emptyList())
                    }
                }
            }
        return antinodes.count()
    }

    fun part2(input: List<String>): Int {
        return part1(input, maxResonance = Int.MAX_VALUE)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test", year = 2024)
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput("Day08", year = 2024)
    part1(input).println()
    part2(input).println()
}

private fun List<String>.parseAntennas(): Map<Char, List<Coord>> {
    val antennas = mutableMapOf<Char, List<Coord>>().withDefault { emptyList() }
    forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            if (c != '.') {
                antennas[c] = antennas.getValue(c) + Coord(x, y)
            }
        }
    }
    return antennas
}
