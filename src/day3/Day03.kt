package day3

import readInput

fun main() {
    val input = readInput("day3/Day03_test")
    val bitMatrix = getBitMatrix(input)
    part1(bitMatrix)
    part2(bitMatrix)
}

fun getBitMatrix(input: List<String>): List<CharArray> {
    return input.map { it.toCharArray() }
}

fun countZerosAndOnes(bitMatrix: List<CharArray>): List<List<BitCounter>> {
    val colSize = bitMatrix[0].size - 1
    val rowSize = bitMatrix.size - 1
    val zerosAndOnes = mutableListOf<List<BitCounter>>()

    var zero = 0
    var one = 0

    for (col in 0..colSize) {
        for (row in 0..rowSize) {
            when (bitMatrix[row][col]) {
                '1' -> one += 1
                '0' -> zero += 1
            }
        }
        zerosAndOnes.add(listOf(BitCounter('0',zero), BitCounter('1',one)))
        one = 0
        zero = 0
    }
    return zerosAndOnes
}

fun part1(bitMatrix: List<CharArray>) {
    val zerosAndOnes = countZerosAndOnes(bitMatrix)
    val colSize = bitMatrix[0].size-1
    val gammaRate = CharArray(colSize+1)
    val epsilonRate = CharArray(colSize+1)

    zerosAndOnes.forEachIndexed {
        index, zeroAndOne ->
        val zeroCounter = zeroAndOne[0].counter
        val oneCounter = zeroAndOne[1].counter

        if(oneCounter > zeroCounter) {
            gammaRate[index] = '1'
            epsilonRate[index] = '0'
        }else {
            gammaRate[index] = '0'
            epsilonRate[index] = '1'
        }
    }
    val gamma = String(gammaRate).toInt(2)
    val epsilon = String(epsilonRate).toInt(2)
    println(gamma * epsilon)
}

fun part2(bitMatrix: List<CharArray>) {
    val oxygen = String(getOxygenGenerator(0, bitMatrix)[0]).toInt(2)
    val CO2 = String(getCO2(0,bitMatrix)[0]).toInt(2)
    println(oxygen * CO2)
}

fun getOxygenGenerator(bitPosition: Int, bitMatrix: List<CharArray>): List<CharArray> {
        var bitPosition = bitPosition
        if(bitMatrix.size == 1) {
            return bitMatrix
        }

        val zerosAndOnes = countZerosAndOnes(bitMatrix)
        val max = getMaxOxygen(zerosAndOnes[bitPosition])
        val newBitMatrix = bitMatrix.filter { it[bitPosition] == max.bit }
        bitPosition++
        return getOxygenGenerator(bitPosition, newBitMatrix)
}

fun getCO2(bitPosition: Int, bitMatrix: List<CharArray>): List<CharArray> {
    var bitPosition = bitPosition
    if(bitMatrix.size == 1) {
        return bitMatrix
    }

    val zerosAndOnes = countZerosAndOnes(bitMatrix)
    val max = getMaxCO2(zerosAndOnes[bitPosition])
    val newBitMatrix = bitMatrix.filter { it[bitPosition] == max.bit }
    bitPosition++
    return getCO2(bitPosition, newBitMatrix)
}

fun getMaxOxygen(bitCounterList: List<BitCounter>): BitCounter {
    val zeroCounter = bitCounterList[0]
    val oneCounter = bitCounterList[1]
    return if(oneCounter.counter >= zeroCounter.counter) {
        oneCounter
    }else {
        zeroCounter
    }
}

fun getMaxCO2(bitCounterList: List<BitCounter>): BitCounter {
    val zeroCounter = bitCounterList[0]
    val oneCounter = bitCounterList[1]
    return if(zeroCounter.counter <= oneCounter.counter) {
        zeroCounter
    }else {
        oneCounter
    }
}

data class BitCounter(val bit: Char, val counter: Int)