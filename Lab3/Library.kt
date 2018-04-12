enum class TicketType {
  STUDENT { override fun price(): Double = 60.0 },
  SCHOOL { override fun price(): Double = 50.0 },
  SCIENCE { override fun price(): Double = 70.0 },
  ACADEMY { override fun price(): Double = 75.0 },
  JOURNALIST { override fun price(): Double = 80.0 },
  SPECIAL { override fun price(): Double = 100.0 };
  abstract fun price(): Double
}
const val declaredPhotoPrice: Double = 7.5
const val declaredHologramPrice: Double = 15.0

interface Ticket {
  val type: TicketType
  val photo: Boolean
  val hologram: Boolean
}
class TicketObject(override val type: TicketType, override val photo: Boolean = false, override val hologram: Boolean = false): Ticket {
  private val typePrice: Double = this.type.price()
  private val photoPrice: Double = if (this.photo) declaredPhotoPrice else 0.0
  private val hologramPrice: Double = if (this.hologram) declaredHologramPrice else 0.0
  fun summaryPrice(): Double = this.typePrice + this.photoPrice + this.hologramPrice
  fun typePrice(): Double = this.typePrice
  fun typePriceWithPhoto(): Double = this.typePrice + declaredPhotoPrice
  fun typePriceWithHologram(): Double = this.typePrice + declaredHologramPrice
  fun typePriceWithPhotoAndHologram(): Double = this.typePrice + declaredPhotoPrice + declaredHologramPrice
}
class CommonTicket(override val type: TicketType, override val photo: Boolean = false, override val hologram: Boolean = false): Ticket
class PhotoTicket(override val type: TicketType, override val photo: Boolean = true, override val hologram: Boolean = false): Ticket
class HologramTicket(override val type: TicketType, override val photo: Boolean = false, override val hologram: Boolean = true): Ticket
class PhotoAndHologramTicket(override val type: TicketType, override val photo: Boolean = true, override val hologram: Boolean = true): Ticket

abstract class TicketFactory {
  abstract fun make(): Ticket
  companion object {
    inline fun <reified T: Ticket> createFactory(budget: Double, expectedTicketType: TicketType): TicketFactory = when (T::class) {
      CommonTicket::class -> {
        val ticket = TicketObject(expectedTicketType)
        when {
          budget >= ticket.typePrice() -> CommonTicketFactory(expectedTicketType)
          else -> throw IllegalArgumentException("Your budget does not allow to by this ticket: " + expectedTicketType.toString())
        }
      }
      PhotoTicket::class -> {
        val ticket = TicketObject(expectedTicketType, true)
        when {
          budget >= ticket.typePriceWithPhoto() -> PhotoTicketFactory(expectedTicketType)
          budget >= ticket.typePrice() -> CommonTicketFactory(expectedTicketType)
          else -> throw IllegalArgumentException("Your budget does not allow to by this ticket: " + expectedTicketType.toString())
        }
      }
      HologramTicket::class -> {
        val ticket = TicketObject(expectedTicketType, false, true)
        when {
          budget >= ticket.typePriceWithHologram() -> HologramTicketFactory(expectedTicketType)
          budget >= ticket.typePriceWithPhoto() -> PhotoTicketFactory(expectedTicketType)
          budget >= ticket.typePrice() -> CommonTicketFactory(expectedTicketType)
          else -> throw IllegalArgumentException("Your budget does not allow to by this ticket: " + expectedTicketType.toString())
        }
      }
      PhotoAndHologramTicket::class -> {
        val ticket = TicketObject(expectedTicketType, true, true)
        when {
          budget >= ticket.typePriceWithPhotoAndHologram() -> PhotoAndHologramTicketFactory(expectedTicketType)
          budget >= ticket.typePriceWithHologram() -> HologramTicketFactory(expectedTicketType)
          budget >= ticket.typePriceWithPhoto() -> PhotoTicketFactory(expectedTicketType)
          budget >= ticket.typePrice() -> CommonTicketFactory(expectedTicketType)
          else -> throw IllegalArgumentException("Your budget does not allow to by this ticket: " + expectedTicketType.toString())
        }
      }
      else -> throw IllegalArgumentException("Unknown ticket type specified")
    }
  }
}
class CommonTicketFactory(private val type: TicketType): TicketFactory() {
  init { println("Now you have ${this.type} reader ticket") }
  override fun make(): Ticket = CommonTicket(this.type)
}
class PhotoTicketFactory(private val type: TicketType): TicketFactory() {
  init { println("Now you have ${this.type} reader ticket with photo") }
  override fun make(): Ticket = PhotoTicket(this.type)
}
class HologramTicketFactory(private val type: TicketType): TicketFactory() {
  init { println("Now you have ${this.type} reader ticket with hologram") }
  override fun make(): Ticket = HologramTicket(this.type)
}
class PhotoAndHologramTicketFactory(private val type: TicketType): TicketFactory() {
  init { println("Now you have ${this.type} reader ticket with photo and hologram") }
  override fun make(): Ticket = PhotoAndHologramTicket(this.type)
}

fun main(args: Array<String>) {
  try {
    val ticketFactory = TicketFactory.createFactory<HologramTicket>(80.0, TicketType.SCIENCE)
    val result = ticketFactory.make()
    val ticket = TicketObject(result.type, result.photo, result.hologram)
    println("Summary ticket price: ${ticket.summaryPrice()}")
  } catch(e: Exception) {
    println("Exception: ${e.message}")
  }
}
