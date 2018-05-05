sealed class Category { abstract fun score(): Int }
class Unsatisfactorily: Category() { override fun score(): Int = 2 }
class Satisfactorily: Category() { override fun score(): Int = 3 }
class Nicely: Category() { override fun score(): Int = 4 }
class Perfectly: Category() { override fun score(): Int = 5 }

class Student(private var studiedMaterialFraction: Double = 0.0) {
  private var category: Category = Unsatisfactorily()
  private fun categoryByFraction(fraction: Double): Category = when (fraction) {
    in 0.95 .. 1.0 -> Perfectly()
    in 0.8 .. 0.95 -> Nicely()
    in 0.6 .. 0.8 -> Satisfactorily()
    else -> Unsatisfactorily()
  }
  init {
    if (this.studiedMaterialFraction !in 0.0 .. 1.0) throw IllegalArgumentException("Initial fraction of studied material must be in range of 0 and 1 but found ${this.studiedMaterialFraction}")
    this.category = this.categoryByFraction(this.studiedMaterialFraction)
  }
  fun fraction(): Double = this.studiedMaterialFraction
  fun study() {
    if (this.category != Perfectly()) {
      this.studiedMaterialFraction += 0.05
      this.category = this.categoryByFraction(this.studiedMaterialFraction)
    } else this.studiedMaterialFraction = 1.0
  }
  fun exam() {
    val score = this.category.score()
    when (score) {
      3, 4, 5 -> println("Congratulations, you passed the exam and your sore is $score")
      else -> println("You did not pass the exam and your score is $score")
    }
  }
}

fun main(args: Array<String>) {
  try {
    val student = Student(0.4)
    println("Initial studied material fraction: " + String.format("%.2f", student.fraction()))
    student.study()
    student.study()
    student.study()
    student.study()
    student.study()
    student.study()
    student.study()
    student.study()
    println("Current studied fraction: " + String.format("%.2f", student.fraction()))
    student.exam()
  } catch (e: Exception) {
    println("EXCEPTION: ${e.message}")
  }
}