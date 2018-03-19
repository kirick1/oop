enum class Type {
    KING { override fun toString() = "king" },
    QUEEN { override fun toString() = "queen" },
    ROOK { override fun toString() = "rook" },
    BISHOP { override fun toString() = "bishop" },
    KNIGHT { override fun toString() = "knight" },
    PAWN { override fun toString() = "pawn" },
    NONE { override fun toString() = "none" };
    abstract override fun toString(): String
}
enum class Colour {
    BLACK { override fun toString() = "black" },
    WHITE { override fun toString() = "white" },
    NONE { override fun toString() = "none" };
    abstract override fun toString(): String
}
class Figure(private var type:Type, private var colour:Colour) {
    fun getType():Type = this.type
    fun setType(expectedType:Type) { this.type = expectedType }
    fun getColour():Colour = this.colour
    fun setColour(expectedColour:Colour) { this.colour = expectedColour }
}
class Chess private constructor() {
    private val desk:Array<Array<Figure>> = Array(8, {Array(8,{Figure(Type.NONE, Colour.NONE)})})
    private fun showDesk() {
        var rowsCounter = 1
        print(" ")
        for(i in 1..8) print("\u001B[4;34m$i \u001B[0m ")
        println()
        for(row in this.desk) {
            for (col in row) when (col.getType()) {
                Type.KING -> if(col.getColour() == Colour.WHITE) print("\u001B[1;90m(K)\u001B[0m") else print("\u001B[1;97m[K]\u001B[0m")
                Type.QUEEN -> if(col.getColour() == Colour.WHITE) print("\u001B[1;90m(Q)\u001B[0m") else print("\u001B[1;97m[Q]\u001B[0m")
                Type.ROOK -> if(col.getColour() == Colour.WHITE) print("\u001B[0;37m(r)\u001B[0m") else print("\u001B[0;30m[r]\u001B[0m")
                Type.BISHOP -> if(col.getColour() == Colour.WHITE) print("\u001B[0;37m(b)\u001B[0m") else print("\u001B[0;30m[b]\u001B[0m")
                Type.KNIGHT -> if(col.getColour() == Colour.WHITE) print("\u001B[0;37m(k)\u001B[0m") else print("\u001B[0;30m[k]\u001B[0m")
                Type.PAWN -> if(col.getColour() == Colour.WHITE) print("\u001B[0;37m(p)\u001B[0m") else print("\u001B[0;30m[p]\u001B[0m")
                else -> print("   ")
            }
            print("\u001B[0;34m|$rowsCounter\u001B[0m")
            println()
            rowsCounter++
        }
        println()
    }
    init {
        println("Chess game started ...")
        this.desk[0] = arrayOf(Figure(Type.ROOK, Colour.BLACK), Figure(Type.KNIGHT, Colour.BLACK), Figure(Type.BISHOP, Colour.BLACK), Figure(Type.QUEEN, Colour.BLACK), Figure(Type.KING, Colour.BLACK), Figure(Type.BISHOP, Colour.BLACK), Figure(Type.KNIGHT, Colour.BLACK), Figure(Type.ROOK , Colour.BLACK))
        this.desk[1] = arrayOf(Figure(Type.PAWN, Colour.BLACK), Figure(Type.PAWN, Colour.BLACK), Figure(Type.PAWN, Colour.BLACK), Figure(Type.PAWN, Colour.BLACK), Figure(Type.PAWN, Colour.BLACK), Figure(Type.PAWN, Colour.BLACK), Figure(Type.PAWN, Colour.BLACK), Figure(Type.PAWN, Colour.BLACK))
        this.desk[2] = arrayOf(Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE))
        this.desk[3] = arrayOf(Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE))
        this.desk[4] = arrayOf(Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE))
        this.desk[5] = arrayOf(Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE), Figure(Type.NONE, Colour.NONE))
        this.desk[6] = arrayOf(Figure(Type.PAWN, Colour.WHITE), Figure(Type.PAWN, Colour.WHITE), Figure(Type.PAWN, Colour.WHITE), Figure(Type.PAWN, Colour.WHITE), Figure(Type.PAWN, Colour.WHITE), Figure(Type.PAWN, Colour.WHITE), Figure(Type.PAWN, Colour.WHITE), Figure(Type.PAWN, Colour.WHITE))
        this.desk[7] = arrayOf(Figure(Type.ROOK, Colour.WHITE), Figure(Type.KNIGHT, Colour.WHITE), Figure(Type.BISHOP, Colour.WHITE), Figure(Type.QUEEN, Colour.WHITE), Figure(Type.KING, Colour.WHITE), Figure(Type.BISHOP, Colour.WHITE), Figure(Type.KNIGHT, Colour.WHITE), Figure(Type.ROOK , Colour.WHITE))
        this.showDesk()
    }
    private object Holder {
        val INSTANCE = Chess()
    }
    companion object {
        val instance: Chess by lazy { Holder.INSTANCE }
    }
    private fun checkPosition(positionX:Int, positionY:Int):Boolean {
        return if(positionX in 1..8 && positionY in 1..8) (this.desk[positionY - 1][positionX - 1].getType() != Type.NONE && this.desk[positionY - 1][positionX - 1].getColour() != Colour.NONE)
        else false
    }
    private fun checkMovingOpportunity(currentPositionX:Int, currentPositionY:Int, expectedPositionX:Int, expectedPositionY:Int):Boolean {
        return if(this.checkPosition(currentPositionX,currentPositionY) && !this.checkPosition(expectedPositionX,expectedPositionY)) {
            val stepX = Math.abs(expectedPositionX - currentPositionX)
            val stepY = Math.abs(expectedPositionX - currentPositionX)
            when (this.desk[currentPositionY - 1][currentPositionX - 1].getType()) {
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
            this.desk[expectedPositionY - 1][expectedPositionX - 1].setType(this.desk[currentPositionY - 1][currentPositionX - 1].getType())
            this.desk[expectedPositionY - 1][expectedPositionX - 1].setColour(this.desk[currentPositionY - 1][currentPositionX - 1].getColour())
            this.desk[currentPositionY - 1][currentPositionX - 1].setType(Type.NONE)
            this.desk[currentPositionY - 1][currentPositionX - 1].setColour(Colour.NONE)
            this.showDesk()
        }
    }
}
fun main(args: Array<String>) {
    val white = Chess.instance
    val black = Chess.instance
    white.moveFigure(2,2,2,3)
    black.moveFigure(3,7,3,6)
}