fun main() {

    val input = readInput("Day05")

    fun part1(input: List<String>): String {
        val structure = getStructure(input)
        val actions = getActions(input)
        return structure.performActions(actions).getTopElements()
    }

    fun part2(input: List<String>): String {
        val structure = getStructure(input)
        val actions = getActions(input)
        return structure.performActions(actions, true).getTopElements()
    }

    println(part1(input))
    println(part2(input))
}

typealias Structure = MutableList<MutableList<Char>>

private fun Structure.performActions(actions: List<Action>, reversed: Boolean = false): Structure {
    actions.forEach{action ->
        val itemsToMove = this[action.origin-1].takeLast(action.quantity).toMutableList()
        this[action.origin-1] = this[action.origin-1].dropLast(action.quantity).toMutableList()
        if(reversed){
            itemsToMove.reverse()
        }
        this[action.destination-1].addAll(itemsToMove)
    }
    return this
}

private fun Structure.getTopElements(): String = this.map { it.last() }.joinToString("")

private fun getStructure(input: List<String>): Structure {
    val structureText = input.takeWhile { it.isNotBlank() }
    val numberOfLists: Int = structureText.last().filter { it.isDigit() }.max().digitToInt()
    val structure = MutableList(numberOfLists) { mutableListOf<Char>() }

    structureText.dropLast(1).forEachIndexed { rowIndex, string ->
        string.mapIndexedNotNull { columnIndex, char ->
            if (char.isLetter()) {
                val structureIndex = (columnIndex / 4)
                structure[structureIndex].add(0, char)
            }
        }
    }
    return structure
}

private fun getActions(input: List<String>): List<Action> {
    val actionText = input.dropWhile { it.isNotBlank() }.drop(1)
    return actionText.map { text ->
        val numbers = text.split(" ").mapNotNull { it.toIntOrNull() }
        Action(
            quantity = numbers[0],
            origin = numbers[1],
            destination = numbers[2],
        )
    }
}

data class Action(
    val quantity: Int,
    val origin: Int,
    val destination: Int,
){
    override fun toString(): String = "Move $quantity from $origin to $destination"
}