package taller

class ConjuntosDifusos {

  type ConjDifuso = Int => Double

  def pertenece(elem: Int, s: ConjDifuso): Double = s(elem)

  // Igualdad autónoma sin depender de inclusion
  def igualdad(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {
    @annotation.tailrec
    def aux(i: Int): Boolean = {
      if (i > 1000) true                // Caso base: todos los índices revisados
      else if (cd1(i) != cd2(i)) false  // Si algún valor difiere, no son iguales
      else aux(i + 1)                   // Revisar siguiente índice
    }
    aux(0)
  }
}
