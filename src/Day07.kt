fun main() {

//    val input = readInput("Day07")
    val input = readInput("Day07")

    fun part1(input: List<String>): Int {
        val directoryWalker = DirectoryWalker(input)
        val rootDirectory = directoryWalker.walkDirectory()
        val allDirectories= rootDirectory.getAllSubDirectories()
        return allDirectories.filter { it.size() < 100000 }.sumOf { it.size() }
    }

    fun part2(input: List<String>): Int {
        val directoryWalker = DirectoryWalker(input)
        val rootDirectory = directoryWalker.walkDirectory()
        val allDirectories= rootDirectory.getAllSubDirectories()
        val sizeNeeded = 30000000 - (70000000 - rootDirectory.size())
        return allDirectories.filter{it.size() > sizeNeeded}.minBy { it.size() }.size()
    }

    println(part1(input))
    println(part2(input))

}

class DirectoryWalker(private val instructions: List<String>) {

    private val rootDirectory = Directory("Root")

    private var currentDirectory: Directory = rootDirectory

    fun walkDirectory(): Directory {
        instructions.forEach { command ->
            when {
                command.startsWith("$ cd") -> handleDirMove(command)
                command.startsWith("$ ls") -> {}
                command.startsWith("dir") -> addInnerDirectory(command.drop(4).trim())
                else -> {
                    addFile(command)
                }
            }
        }
        return rootDirectory
    }

    private fun handleDirMove(command: String) {
        val location = command.drop(5)
        when (location) {
            "/" -> gotToOuterMostDirectory()
            ".." -> currentDirectory =
                currentDirectory.parent ?: error("Directory ${currentDirectory.name} has no parent")
            else -> {
                currentDirectory = currentDirectory.childDirectories.single { it.name == location }
            }
        }
    }

    private fun gotToOuterMostDirectory() {
        currentDirectory = generateSequence(currentDirectory){
            it.parent
        }.last()
    }

    private fun addInnerDirectory(name: String) {
        currentDirectory.childDirectories.add(Directory(name, parent = currentDirectory))
    }

    private fun addFile(definition: String) {
        val size = definition.takeWhile { it.isDigit() }.toInt()
        val name = definition.dropWhile { it.isDigit() }.trim()
        currentDirectory.files.add(ContentFile(name = name, size = size))
    }
}

interface AFile {
    val name: String
    fun size(): Int
}

data class Directory(
    override val name: String,
    val parent: Directory? = null,
    val childDirectories: MutableList<Directory> = mutableListOf(),
    val files: MutableList<ContentFile> = mutableListOf(),
) : AFile{
    override fun toString(): String {
        return "Directory($name)"
    }

    override fun size(): Int {
        return files.sumOf{it.size} + childDirectories.fold(0) { acc, directory -> acc + directory.size() }
    }
}

fun Directory.getAllSubDirectories(): List<Directory>{
    return if (this.childDirectories.isEmpty()) {
        listOf(this)
    } else {
        val directories = this.childDirectories.flatMap { it.getAllSubDirectories() }.toMutableList()
        directories.add(this)
        directories
    }
}

data class ContentFile(
    override val name: String,
    val size: Int,
) : AFile {
    override fun size(): Int = size
}