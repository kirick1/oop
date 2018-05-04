enum class Textile { COTTON, FLAX, WOOL, SILK, SYNTHETIC } //бавовна, льон, шерсть, шовк, синтетика

class Clothes(private val textile: Textile, private var isClean: Boolean = false, private var isStraight: Boolean = false) {
  fun textile(): Textile = this.textile
  fun wash() {
    if(!this.isClean) this.isClean = true
    if(this.isStraight) this.isStraight = false
  }
  fun iron() {
    if(!this.isStraight) this.isStraight = true
  }
  override fun toString(): String = "Textile type: ${this.textile}; clean: ${this.isClean}; straight: ${this.isStraight}"
}
class Washer(private val washerStrategy: (Clothes) -> Clothes) {
  fun wash(clothes: Clothes) {
    this.washerStrategy.invoke(clothes)
  }
}
val coldWaterWasher: (Clothes) -> Clothes = {
  it.wash()
  it
}
val warmWaterWater: (Clothes) -> Clothes = {
  when(it.textile()) {
    Textile.SILK, Textile.SYNTHETIC -> {
      it.wash()
      it
    } else -> throw IllegalArgumentException("Warm water is not recommended for: " + it.textile())
  }
}
val hotWaterWasher: (Clothes) -> Clothes = {
  when(it.textile()) {
    Textile.COTTON, Textile.FLAX -> {
      it.wash()
      it
    } else -> throw IllegalArgumentException("Hot water is not recommended for: " + it.textile())
  }
}
val chemicalWasher: (Clothes) -> Clothes = {
  when(it.textile()) {
    Textile.COTTON, Textile.FLAX, Textile.WOOL, Textile.SILK -> {
      it.wash()
      it
    } else -> throw IllegalArgumentException("Chemical washing is not recommended for: " + it.textile())
  }
}
class Ironer(private val ironerStrategy: (Clothes) -> Clothes) {
  fun iron(clothes: Clothes) {
    this.ironerStrategy.invoke(clothes)
  }
}
val temperature60to80Ironer: (Clothes) -> Clothes = {
  when(it.textile()) {
    Textile.SILK, Textile.SYNTHETIC -> {
      it.iron()
      it
    } else -> throw IllegalArgumentException("Ironing with temperature 60-80 is not recommended for: " + it.textile())
  }
}
val temperature100to120Ironer: (Clothes) -> Clothes = {
  when(it.textile()) {
    Textile.WOOL -> {
      it.iron()
      it
    } else -> throw IllegalArgumentException("Ironing with temperature 100-120 is not recommended for: " + it.textile())
  }
}
val temperature140to170Ironer: (Clothes) -> Clothes = {
  when(it.textile()) {
    Textile.COTTON, Textile.FLAX -> {
      it.iron()
      it
    } else -> throw IllegalArgumentException("Ironing with temperature 140-170 is not recommended for: " + it.textile())
  }
}

fun main(args: Array<String>) {
  try {
    val cottonDirtyStraightClothes = Clothes(Textile.COTTON, false, true)
    println(cottonDirtyStraightClothes)
    Washer(coldWaterWasher).wash(cottonDirtyStraightClothes)
    println(cottonDirtyStraightClothes)
    Ironer(temperature140to170Ironer).iron(cottonDirtyStraightClothes)
    println(cottonDirtyStraightClothes)
  } catch (e: Exception) {
    println("EXCEPTION: ${e.message}")
  }
}