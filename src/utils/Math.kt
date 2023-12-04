package utils

/* Math */
fun Long.gcm(value: Long): Long {
    var gcd = 1L
    for (i in 1..minOf(this, value)) {
        // Checks if i is factor of both integers
        if (this % i == 0L && value % i == 0L) {
            gcd = i
        }
    }
    return gcd
}
fun Long.lcm(value: Long): Long = (this * value) / (this.gcm(value))
fun List<Long>.lcm(): Long = reduce(Long::lcm)

fun List<Int>.product() = reduce { op1, op2 -> op1 * op2 }
fun List<Long>.product() = reduce { op1, op2 -> op1 * op2 }
