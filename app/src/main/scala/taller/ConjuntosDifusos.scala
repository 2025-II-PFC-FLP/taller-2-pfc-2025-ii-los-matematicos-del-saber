package taller

class ConjuntosDifusos {

  type ConjDifuso = Int => Double

  def pertenece(elem: Int, s: ConjDifuso): Double = s(elem)

  def grande(d: Int, e: Int): ConjDifuso = {
    def f(x: Int): Double = {
      if (x <= 0) 0.0
      else Math.pow(x.toDouble / (x + d), e)
    }
    f
  }

  def inclusion(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {
    import scala.annotation.tailrec
    @tailrec
    def aux(i: Int): Boolean = {
      if (i > 1000) true
      else if (cd1(i) > cd2(i)) false
      else aux(i + 1)
    }
    aux(0)
  }

  def complemento(c: ConjDifuso): ConjDifuso = {
    def f(x: Int): Double = 1.0 - c(x)
    f
  }


  def igualdad(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {
    @annotation.tailrec
    def aux(i: Int): Boolean = {
      if (i > 1000) true
      else if (cd1(i) != cd2(i)) false
      else aux(i + 1)
    }
    aux(0)
  }
}