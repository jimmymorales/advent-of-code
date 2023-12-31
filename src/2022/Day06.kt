package `2022`

fun main() {

    fun part1(input: List<String>, windowSize: Int = 4): Int = input.first()
        .asSequence()
        .windowed(size = windowSize, transform = List<Char>::toSet)
        .indexOfFirst { it.count() == windowSize }
        .let { it + windowSize }

    fun part2(input: List<String>): Int = part1(input, windowSize = 14)

    // test if implementation meets criteria from the description, like:
    val testInput = utils.readInput("Day06_test", year = 2022)
    check(part1(testInput) == 7)

    val input = utils.readInput("Day06", year = 2022)
    println(part1(input))

    // part 2
    check(part2(testInput) == 19)
    println(part2(input))
}
