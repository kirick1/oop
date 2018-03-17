interface Household {
    val name: String

    fun info()
    fun work()
}
class Coffee(override val name: String) : Household {
    override fun info() = println("Coffee name: " + this.name)
    override fun work() = println("Coffee is making coffee ...")
}
class Juicer(override val name: String) : Household {
    override fun info() = println("Juicer name: " + this.name)
    override fun work() = println("Juicer is making juice ...")
}
class Grinder(override val name: String) : Household {
    override fun info() = println("Grinder name: " + this.name)
    override fun work() = println("Grinder is chopping meat ...")
}
class Combiner(override val name: String, private val coffeeName: String, private val juicerName: String, private val grinderName: String) : Household {
    private val coffeeMachine = Coffee(this.coffeeName)
    private val juicerMachine = Juicer(this.juicerName)
    private val grinderMachine = Grinder(this.grinderName)

    fun makeCoffee() = this.coffeeMachine.work()
    fun makeJuice() = this.juicerMachine.work()
    fun makeMeat() = this.grinderMachine.work()

    override fun info() {
        println("Household combine name: " + this.name)
        println("Components:")
        print("\t")
        this.coffeeMachine.info()
        print("\t")
        this.juicerMachine.info()
        print("\t")
        this.grinderMachine.info()
    }
    override fun work() = println("Household combiner " + this.name + " is working ...")
}

fun main(args: Array<String>) {
    val combiner = Combiner("House 3000", "SuperCoffee", "FreshJuiceMaker", "GrindAll 2.0")
    combiner.info()
    combiner.work()
    combiner.makeCoffee()
}