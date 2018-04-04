enum class Type { KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN, NONE }
enum class Colour { BLACK, WHITE, NONE }

data class Figure(var colour: Colour = Colour.NONE, var type: Type = Type.NONE)
class Chess private constructor() {
    private val figure = Figure()
    private val desk: Array<Array<Figure>> = Array(8, {Array(8, {this.figure.copy()})})
    private fun showDesk() {
        var rowsCounter = 1
        print(" ")
        for(i in 1..8) print("\u001B[4;34m$i \u001B[0m ")
        println()
        for(row in this.desk) {
            for (col in row) when (col.type) {
                Type.KING -> if(col.colour == Colour.WHITE) print("\u001B[1;90m(K)\u001B[0m") else print("\u001B[1;97m[K]\u001B[0m")
                Type.QUEEN -> if(col.colour == Colour.WHITE) print("\u001B[1;90m(Q)\u001B[0m") else print("\u001B[1;97m[Q]\u001B[0m")
                Type.ROOK -> if(col.colour == Colour.WHITE) print("\u001B[0;37m(r)\u001B[0m") else print("\u001B[0;30m[r]\u001B[0m")
                Type.BISHOP -> if(col.colour == Colour.WHITE) print("\u001B[0;37m(b)\u001B[0m") else print("\u001B[0;30m[b]\u001B[0m")
                Type.KNIGHT -> if(col.colour == Colour.WHITE) print("\u001B[0;37m(k)\u001B[0m") else print("\u001B[0;30m[k]\u001B[0m")
                Type.PAWN -> if(col.colour == Colour.WHITE) print("\u001B[0;37m(p)\u001B[0m") else print("\u001B[0;30m[p]\u001B[0m")
                else -> print("   ")
            }
            print("\u001B[0;34m|$rowsCounter\u001B[0m")
            println()
            rowsCounter++
        }
        println()
    }
    init {
        val black = this.figure.copy(colour = Colour.BLACK)
        val white = this.figure.copy(colour = Colour.WHITE)
        this.desk[0] = arrayOf(black.copy(type = Type.ROOK), black.copy(type = Type.KNIGHT), black.copy(type = Type.BISHOP), black.copy(type = Type.QUEEN), black.copy(type = Type.KING), black.copy(type = Type.BISHOP), black.copy(type = Type.KNIGHT), black.copy(type = Type.ROOK))
        this.desk[1] = arrayOf(black.copy(type = Type.PAWN), black.copy(type = Type.PAWN), black.copy(type = Type.PAWN), black.copy(type = Type.PAWN), black.copy(type = Type.PAWN), black.copy(type = Type.PAWN), black.copy(type = Type.PAWN), black.copy(type = Type.PAWN))
        this.desk[6] = arrayOf(white.copy(type = Type.PAWN), white.copy(type = Type.PAWN), white.copy(type = Type.PAWN), white.copy(type = Type.PAWN), white.copy(type = Type.PAWN), white.copy(type = Type.PAWN), white.copy(type = Type.PAWN), white.copy(type = Type.PAWN))
        this.desk[7] = arrayOf(white.copy(type = Type.ROOK), white.copy(type = Type.KNIGHT), white.copy(type = Type.BISHOP), white.copy(type = Type.QUEEN), white.copy(type = Type.KING), white.copy(type = Type.BISHOP), white.copy(type = Type.KNIGHT), white.copy(type = Type.ROOK))
        this.showDesk()
    }
    private object Holder { val INSTANCE = Chess() }
    companion object { val instance: Chess by lazy { Holder.INSTANCE } }
    private fun checkPosition(positionX:Int, positionY:Int):Boolean = if(positionX in 1..8 && positionY in 1..8) (this.desk[positionY - 1][positionX - 1].type != Type.NONE && this.desk[positionY - 1][positionX - 1].colour != Colour.NONE) else false
    private fun checkMovingOpportunity(currentPositionX:Int, currentPositionY:Int, expectedPositionX:Int, expectedPositionY:Int):Boolean {
        return if(this.checkPosition(currentPositionX,currentPositionY) && !this.checkPosition(expectedPositionX,expectedPositionY)) {
            val stepX = Math.abs(expectedPositionX - currentPositionX)
            val stepY = Math.abs(expectedPositionX - currentPositionX)
            when (this.desk[currentPositionY - 1][currentPositionX - 1].type) {
                Type.KING -> (stepX <= 1 && stepY <= 1)
                Type.QUEEN -> (stepX <= 7 && stepY <= 7)
                Type.ROOK -> (stepX <= 7 && stepY <= 7)
                Type.BISHOP -> (stepX <= 7 && stepY <= 7)
                Type.KNIGHT -> (stepX <= 3 && stepY <= 3)
                Type.PAWN -> (stepX <= 1 && stepY <= 0)
                else -> false
            }
        } else false
    }
    fun moveFigure(currentPositionX:Int, currentPositionY:Int, expectedPositionX:Int, expectedPositionY:Int) {
        if(this.checkMovingOpportunity(currentPositionX,currentPositionY,expectedPositionX,expectedPositionY)) {
            this.desk[expectedPositionY - 1][expectedPositionX - 1].type = this.desk[currentPositionY - 1][currentPositionX - 1].type
            this.desk[expectedPositionY - 1][expectedPositionX - 1].colour = this.desk[currentPositionY - 1][currentPositionX - 1].colour
            this.desk[currentPositionY - 1][currentPositionX - 1].type = Type.NONE
            this.desk[currentPositionY - 1][currentPositionX - 1].colour = Colour.NONE
            this.showDesk()
        }
    }
}

fun main(args: Array<String>) {
    val desk = Chess.instance
    desk.moveFigure(2,2,2,3)
    desk.moveFigure(3,7,3,6)
}