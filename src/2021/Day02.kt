package `2021`

fun main() {
    fun part1(input: List<String>, isManual: Boolean = false): Int {
        var aim = 0
        var horizontal = 0
        var depth = 0
        for (line in input) {
            val (command, value) = line.split(' ')
            when (command) {
                "up" -> aim -= value.toInt()
                "down" -> aim += value.toInt()
                else -> value.toInt().let {
                    horizontal += it
                    depth += aim * it
                }
            }
        }
        return horizontal * if (isManual) depth else aim
    }

    fun part2(input: List<String>): Int = part1(input, isManual = true)

    // test if implementation meets criteria from the description:
    val testInput = utils.readInput("Day02_test", year = 2021)
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = utils.readInput("Day02", year = 2021)
    println(part1(input))
    println(part2(input))
}
