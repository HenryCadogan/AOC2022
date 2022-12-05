fun main() {
    val input = readInput("Day02")

    /*
        A | X -> Rock
        B | Y -> Paper
        C | Z -> Scissors
     */

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val myMove = it.takeLast(1)[0]
            val theirMove = it.take(1)[0]
            val shapePoints = shapePoint(myMove)
            val rpsPoints = calculateRPSWin(myMove, theirMove)
            //println("Evaluating $myMove VS $theirMove = ${shapePoints + rpsPoints} ($shapePoints + $rpsPoints)")
            shapePoints + rpsPoints
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val theirMove = it.take(1)[0]
            val outcome = it.takeLast(1)[0]
            val myMove = calculateMove(theirMove, outcome)
            val shapePoint = shapePoint(myMove)
            val rpsPoints = calculateRPSWin(myMove, theirMove)
            shapePoint + rpsPoints
        }
    }

    val testInput = """
        A Y
        B X
        C Z
        """.trimIndent()
    check(part2(testInput.lines()) == 12)
    println(part1(input))
    println(part2(input))
}

private fun shapePoint(move: Char): Int = when (move) {
    'X' -> 1
    'Y' -> 2
    'Z' -> 3
    else -> error("Unknown Move $move")
}

/*
X -> Lose
Y -> Draw
Z -> Win

A | X -> Rock
B | Y -> Paper
C | Z -> Scissors
 */

private fun calculateMove(theirMove: Char, outcomeNeeded: Char): Char{
    return when (theirMove) {
        'A' -> when (outcomeNeeded) {
            'X' -> 'Z'
            'Y' -> 'X'
            'Z' -> 'Y'
            else -> error("Unknown move $outcomeNeeded")
        }

        'B' -> when (outcomeNeeded) {
            'X' -> 'X'
            'Y' -> 'Y'
            'Z' -> 'Z'
            else -> error("Unknown move $outcomeNeeded")
        }

        'C' -> when (outcomeNeeded) {
            'X' -> 'Y'
            'Y' -> 'Z'
            'Z' -> 'X'
            else -> error("Unknown move $outcomeNeeded")
        }

        else -> error("Unknown move $outcomeNeeded")
    }
}

private fun calculateRPSWin(myMove: Char, theirMove: Char): Int {
    return when (theirMove) {
        'A' -> when (myMove) {
            'X' -> 3
            'Y' -> 6
            'Z' -> 0
            else -> error("Unknown move $myMove")
        }

        'B' -> when (myMove) {
            'X' -> 0
            'Y' -> 3
            'Z' -> 6
            else -> error("Unknown move $myMove")
        }

        'C' -> when (myMove) {
            'X' -> 6
            'Y' -> 0
            'Z' -> 3
            else -> error("Unknown move $myMove")
        }

        else -> error("Unknown move $myMove")
    }
}