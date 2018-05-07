interface Factory {
  val maxProductionCapacity: Int
  val nextFactory: Factory?
  fun checkOrder(orderSize: Int): String?
}
class SmallFactory: Factory {
  override val maxProductionCapacity = 100
  override val nextFactory: Factory? = MediumFactory()
  override fun checkOrder(orderSize: Int): String? = if(orderSize <= maxProductionCapacity) "Small factory can process your order" else null
}
class MediumFactory: Factory {
  override val maxProductionCapacity = 500
  override val nextFactory: Factory? = BigFactory()
  override fun checkOrder(orderSize: Int): String? = if(orderSize <= maxProductionCapacity) "Medium factory can process your order" else null
}
class BigFactory: Factory {
  override val maxProductionCapacity = 1000
  override val nextFactory: Factory? = VeryBigFactory()
  override fun checkOrder(orderSize: Int): String? = if(orderSize <= maxProductionCapacity) "Big factory can process your order" else null
}
class VeryBigFactory: Factory {
  override val maxProductionCapacity = 10000
  override val nextFactory: Factory? = null
  override fun checkOrder(orderSize: Int): String? = if(orderSize <= maxProductionCapacity) "Very big factory can process your order" else "No one factory can process your order"
}
class Order(private val orderSize: Int, private var desirableFactoryToProcess: Factory? = SmallFactory()) {
  private var desirableFactoryStatus: String? = this.desirableFactoryToProcess!!.checkOrder(this.orderSize)
  private var agreement: Boolean = true
  fun make(): Boolean {
    return if(this.desirableFactoryStatus != null) {
      println(this.desirableFactoryStatus)
      if(!this.agreement) false
      else if(this.orderSize <= VeryBigFactory().maxProductionCapacity) {
        println("Are you agree with it?")
        true
      } else false
    } else {
      this.agreement = false
      this.desirableFactoryToProcess = this.desirableFactoryToProcess!!.nextFactory
      if(this.desirableFactoryToProcess != null) {
        this.desirableFactoryStatus = this.desirableFactoryToProcess!!.checkOrder(this.orderSize)
        make()
      } else false
    }
  }
  fun makeWithAgreement(): Boolean {
    this.agreement = true
    return if(this.desirableFactoryStatus != null) make()
    else false
  }
}

fun main(args: Array<String>) {
  try {
    val order = Order(6000, VeryBigFactory())
    val orderStatus = order.make()
    println("Processing: $orderStatus")
    if(!orderStatus) {
      val orderWithAgree = order.makeWithAgreement()
      println("Done: $orderWithAgree")
    }
  } catch (e: Exception) {
    println("EXCEPTION: ${e.message}")
  }
}