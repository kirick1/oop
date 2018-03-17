open class Component {
    private var information = ""
    open fun add(c: Component) {}
    open fun remove(c: Component) {}
    open fun display(depth: Int, position: String) {
        var i = 0
        while(i < depth) {
            print(" ")
            i++
        }
        print(position)
        println()
    }
    open fun order(information: String) {
        this.information = information
    }
}
class Leaf(private val position: String) : Component() {
    private var information = ""
    override fun display(depth: Int, position: String) {
        if(this.information != "") super.display(depth + 2, this.position + " --> order --> " + this.information)
        else super.display(depth + 2, this.position + " --> nothing to implement")
    }
    override fun order(information: String) {
        super.order(information)
        this.information = information
    }
}
class Composite(private val position: String) : Component() {
    private var children: MutableList<Component> = mutableListOf()
    override fun add(c: Component) {
        super.add(c)
        this.children.add(c)
    }
    override fun remove(c: Component) {
        super.remove(c)
        this.children.remove(c)
    }
    override fun display(depth: Int, position: String) {
        super.display(depth, this.position)
        this.children.forEach {
            it.display(depth + 2, this.position)
        }
    }
    override fun order(information: String) {
        super.order(information)
        this.children.forEach {
            it.order(information)
        }
    }
}

fun main(args: Array<String>) {
    val headmaster = Composite("HEADMASTER")

    val deputy1 = Composite("Deputy1")
    headmaster.add(deputy1)
    val deputy2 = Composite("Deputy2")
    headmaster.add(deputy2)

    val chief1 = Composite("Chief1")
    deputy1.add(chief1)
    val chief2 = Composite("Chief2")
    deputy1.add(chief2)
    val chief3 = Composite("Chief3")
    deputy2.add(chief3)

    val subordinate1 = Leaf("Subordinate1")
    chief1.add(subordinate1)
    val subordinate2 = Leaf("Subordinate2")
    chief2.add(subordinate2)
    val subordinate3 = Leaf("Subordinate3")
    chief2.add(subordinate3)
    val subordinate4 = Leaf("Subordinate4")
    chief3.add(subordinate4)

    println("Before order:\n")
    headmaster.display(0, "HEADMASTER")
    headmaster.order("work!!!")
    println("------------------------------------")
    println("After order:\n")
    headmaster.display(0, "HEADMASTER")
}