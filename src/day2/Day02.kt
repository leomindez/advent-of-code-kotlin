package day2

import readInput

fun main() {
    val input = readInput("day2/Day02_test")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    val forward = getCommandTotal(input, "forward")
    val up = getCommandTotal(input, "up")
    val down = getCommandTotal(input, "down")
    return forward * (down - up)
}

fun part2(input: List<String>): Int {
    val forward = getCommandTotal(input, "forward")
    val depth = getDepth(input)
    return forward * depth
}

fun getDepth(input: List<String>): Int {
    var aim = 0
    var depth = 0
    input.forEach {
        when {
            it.contains("forward") -> depth  += aim * it.substringAfter(' ').toInt()
            it.contains("down") -> aim += it.substringAfter(' ').toInt()
            it.contains("up") -> aim -= it.substringAfter(' ').toInt()
        }
    }
    return depth
}

fun getCommandTotal(input: List<String>, command: String): Int {
    return input.filter { it.contains(command) }
            .fold(0) { acc, command -> command.substringAfter(' ').toInt() + acc }
}
