interface Animal {
    val height: Double
    val weight: Double
    val age: Int

    fun info()
    fun sleep()
    fun eat()
}
class AnimalObject(override val height: Double, override val weight: Double, override val age: Int) : Animal {
    override fun info() {
        println("Height: " + this.height + " meters")
        println("Weight: " + this.weight + " kilograms")
        println("Age: " + this.age + " years")
    }
    override fun sleep() = println("Sleeping ...")
    override fun eat() = println("Eating ...")
}
class DomesticatedDecorator(private val animal: AnimalObject, private val name: String) : Animal by animal {
    fun name() = this.name
    override fun info() {
        if(this.name != "") println("Name: " + this.name())
        this.animal.info()
    }
    override fun sleep() = println(this.name + " is sleeping ...")
    override fun eat() = println(this.name + " is eating ...")
}
class WildDecorator(private val animal: AnimalObject) : Animal by animal {
    override fun info() = this.animal.info()
    override fun sleep() = this.animal.sleep()
    override fun eat() = this.animal.eat()
}
class Domestic(animal: AnimalObject, name: String) : Animal by animal {
    private val animal = DomesticatedDecorator(animal, name)
    override fun info() = this.animal.info()
    override fun sleep() = this.animal.sleep()
    override fun eat() = this.animal.eat()
    fun play() = println("Domestic cat " + this.animal.name() + " is playing ...")
}
class Tiger(animal: AnimalObject, name: String) : Animal by animal {
    private val animal = DomesticatedDecorator(animal, name)
    override fun info() = this.animal.info()
    override fun sleep() = this.animal.sleep()
    override fun eat() = this.animal.eat()
    fun tricks() = println("Tiger " + this.animal.name() + " is showing tricks ...")
}
class Lion(animal: AnimalObject, name: String) : Animal by animal {
    private val animal = DomesticatedDecorator(animal, name)
    override fun info() = this.animal.info()
    override fun sleep() = this.animal.sleep()
    override fun eat() = this.animal.eat()
    fun cage() = println("Lion " + this.animal.name() + " is sitting in cage ...")
}
class Puma(animal: AnimalObject) : Animal by animal {
    private val animal = WildDecorator(animal)
    override fun info() = this.animal.info()
    override fun sleep() = this.animal.sleep()
    override fun eat() = this.animal.eat()
    fun hunt() = println("Wild puma is hunting ...")
}
class Jaguar(animal: AnimalObject) : Animal by animal {
    private val animal = WildDecorator(animal)
    override fun info() = this.animal.info()
    override fun sleep() = this.animal.sleep()
    override fun eat() = this.animal.eat()
    fun hunt() = println("Wild jaguar is hunting ...")
}
class Leopard(animal: AnimalObject) : Animal by animal {
    private val animal = WildDecorator(animal)
    override fun info() = this.animal.info()
    override fun sleep() = this.animal.sleep()
    override fun eat() = this.animal.eat()
    fun hunt() = println("Wild leopard is hunting ...")
}

fun main(args: Array<String>) {
    val animal = AnimalObject(123.0, 20.2,23)
    val domestic = Domestic(animal, "Sharik")
    domestic.info()
    domestic.play()
    println()
    val lion = Lion(animal, "Simba")
    lion.info()
    lion.cage()
    println()
    val leopard = Leopard(animal)
    leopard.info()
    leopard.hunt()
}