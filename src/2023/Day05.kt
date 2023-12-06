package `2023`

import utils.parseListOfLongs
import utils.println
import utils.readLinesSplitedbyEmptyLine

fun main() {
    fun part1(input: List<String>): Long {
        val seeds = input[0].parseListOfLongs()
        val almanacMapping = input.parseMapping()
        return seeds.resolverMapping(almanacMapping)
    }

    fun part2(input: List<String>): Long {
        val seedRanges = input[0].parseListOfLongs().windowed(size = 2, step = 2)
            .map { it[0]..<it[0] + it[1] }
        val almanacMapping = input.parseMapping()
        // This is taking like 10 mins to run on an Intel Mac machines, definitely needs some optimizations
        // (maybe a caching layer in the mapping?)
        return seedRanges.minOfOrNull { seedRange ->
            seedRange.resolverMapping(almanacMapping)
        }!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readLinesSplitedbyEmptyLine("Day05_test", year = 2023)
    check(part1(testInput) == 35L)

    val input = readLinesSplitedbyEmptyLine("Day05", year = 2023)
    part1(input).println()
    part2(input).println()
}

fun String.parseNumbers(): List<Long> = """\d+""".toRegex().findAll(this).map { it.value.toLong() }.toList()

private fun List<String>.parseMapping() = drop(1).map { mapping ->
    mapping.split("\n")
        .drop(1)
        .map {
            it.parseNumbers().let { (destStart, sourceStart, rangeLength) ->
                (sourceStart..<sourceStart + rangeLength) to destStart
            }
        }
}

private fun Iterable<Long>.resolverMapping(almanac: List<List<Pair<LongRange, Long>>>) = minOfOrNull { seed ->
    almanac.fold(seed) { item, mapping ->
        mapping.firstNotNullOfOrNull { map ->
            map.second.takeIf { item in map.first }
                ?.let { item - map.first.first + map.second }
        } ?: item
    }
}!!
