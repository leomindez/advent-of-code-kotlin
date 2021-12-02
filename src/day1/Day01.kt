package day1

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        return input.map { it.toInt() }
                .zipWithNext { prev, next -> next - prev }
                .filter { it > 0 }.size
    }

    fun part2(input: List<String>): Int {
        return input.map { it.toInt() }
                .windowed(3)
                .map { it.sum() }
                .zipWithNext{prev,next -> next-prev}
                .filter { it > 0 }
                .size
    }

    val testInput = readInput("Day01_test")
    println(part1(testInput))
    println(part2(testInput))
}
