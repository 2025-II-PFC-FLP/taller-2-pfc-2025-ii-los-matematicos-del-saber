package taller

class ConjuntosDifusos {

  type ConjDifuso = Int => Double

  def pertenece(elem: Int, s: ConjDifuso): Double = s(elem)

  // Igualdad autonoma sin depender de inclusion
  def igualdad(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {
    @annotation.tailrec
    def aux(i: Int): Boolean = {
      if (i > 1000) true
      else if (cd1(i) != cd2(i)) false
      else aux(i + 1)
    }
    aux(0)
  }

  def inclusion(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {
    import scala.annotation.tailrec
    @tailrec
    //Implementacion de la funcion
    def aux(i: Int): Boolean = {
      if (i > 1000) true
      else if (cd1(i) > cd2(i)) false
      else aux(i + 1)
    }
    aux(0)
  }
}

