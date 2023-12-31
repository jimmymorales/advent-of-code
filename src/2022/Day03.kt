package `2022`

fun main() {

    fun Char.priority() = (if (this <= 'Z') this - 'A' + 26 else this - 'a') + 1

    fun part1(input: List<String>): Int = input.map { rubsack ->
        rubsack.chunked(rubsack.length / 2, CharSequence::toSet)
            .reduce(Set<Char>::intersect)
            .single()
    }.sumOf(Char::priority)

    fun part2(input: List<String>): Int = input.windowed(step = 3, size = 3)
        .map { groups ->
            groups.map(String::toSet)
                .reduce(Set<Char>::intersect)
                .single()
        }.sumOf(Char::priority)

    // test if implementation meets criteria from the description, like:
    val testInput = utils.readInput("Day03_test", year = 2022)
    check(part1(testInput) == 157)

    val input = utils.readInput("Day03", year = 2022)
    println(part1(input))

    // part 2
    check(part2(testInput) == 70)
    println(part2(input))
}
