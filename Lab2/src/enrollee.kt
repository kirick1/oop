class Exam(certificate: Int, math: Int, ukrainian: Int, physics: Int = 0, english: Int = 0) {
    private var certificate = 0
    private var first = 0
    private var second = 0
    private var third = 0
    init {
        if(certificate in 90..200) this.certificate = certificate
        else throw IllegalArgumentException("Certificate result must be in range between 90 and 200")
        if(math in 90..200) this.first = math
        else throw IllegalArgumentException("Math result must be in range between 90 and 200")
        if(ukrainian in 90..200) this.second = ukrainian
        else throw IllegalArgumentException("Ukrainian result must be in range between 90 and 200")
        if(physics == 0) {
            if(english in 90..200) this.third = english
            else throw IllegalArgumentException("English result must be in range between 90 and 200")
        } else if(english == 0) {
            if(physics in 90..200) this.third = physics
            else throw IllegalArgumentException("Physics result must be in range between 90 and 200")
        } else if(english in 90..200 && physics in 90..200) this.third = if(english >= physics) english else physics
        else throw IllegalArgumentException("English and Physics result must be in range between 90 and 200")
    }
    fun result(certificateCoefficient: Double = 0.05, firstCoefficient: Double = 0.5, secondCoefficient: Double = 0.2, thirdCoefficient: Double = 0.25): Double {
        if(certificateCoefficient + firstCoefficient + secondCoefficient + thirdCoefficient != 1.0) throw IllegalArgumentException("Sum of coefficients must be 1.0")
        else return this.certificate * certificateCoefficient + this.first * firstCoefficient + this.second * secondCoefficient + this.third * thirdCoefficient
    }
    fun info() {
        println("School certificate score: " + this.certificate)
        println("First exam score(mathematics): " + this.first)
        println("Second exam score(ukrainian): " + this.second)
        println("Third exam score(physics or english): " + this.third)
    }
}
class Enrollee(private val name: String, private val result: Double) {
    fun name() = this.name
    fun result() = this.result
}
// Interface
abstract class Entering {
    abstract fun success(enrollee: Enrollee)
    abstract fun failure(enrollee: Enrollee)
}
// Real Subject
class University: Entering() {
    override fun success(enrollee: Enrollee) = println("Congratulations, " + enrollee.name() + ", welcome to university!")
    override fun failure(enrollee: Enrollee) = println("Sorry, " + enrollee.name() + ", your result is lower than passing score. Try may one more time next year.")
}
// Proxy
class ExamControl(private val passingScore: Int) {
    private var university = University()
    fun enter(enrollee: Enrollee) {
        if(enrollee.result() >= this.passingScore) this.university.success(enrollee)
        else this.university.failure(enrollee)
    }
}

fun main(args: Array<String>) {
    val result = Exam(186, 199, 186, 178).result()
    val enrollee = Enrollee("Ivan", result)
    ExamControl(180).enter(enrollee)
}

