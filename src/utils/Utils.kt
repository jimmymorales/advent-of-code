package utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String, year: Int = 2024) = Path("src/$year/$name.txt").readLines()

fun readLinesSplitedbyEmptyLine(
    name: String,
    year: Int = 2024,
) = File("src/$year", "$name.txt").readText().split("\n\n")

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun String.parseListOfInts(): List<Int> = parseNumbers { it.toInt() }

fun String.parseListOfLongs(): List<Long> = parseNumbers { it.toLong() }

private fun <T> String.parseNumbers(
    transform: (String) -> T
): List<T> = """-?\d+""".toRegex().findAll(this).map { transform(it.value) }.toList()
