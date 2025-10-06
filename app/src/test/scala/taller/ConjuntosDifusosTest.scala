package taller

import org.scalatest.funsuite.AnyFunSuite

class ConjuntosDifusosTest extends AnyFunSuite {

  val conj = new ConjuntosDifusos
  import conj._

  // Conjuntos de prueba
  val cd1: ConjDifuso = x => if (x < 5) 0.2 else if (x < 10) 0.8 else 1.0
  val cd2: ConjDifuso = x => if (x < 5) 0.2 else if (x < 10) 0.8 else 1.0
  val cd3: ConjDifuso = x => if (x < 5) 0.1 else 0.9
  val cd4: ConjDifuso = x => if (x % 2 == 0) 0.5 else 1.0
  val cd5: ConjDifuso = x => if (x % 2 == 0) 0.5 else 1.0
  val cd6: ConjDifuso = x => if (x % 2 == 0) 0.4 else 1.0

  // ---- Tests solo para igualdad ----

  test("igualdad debe ser true para dos conjuntos idénticos") {
    assert(igualdad(cd1, cd2))
  }

  test("igualdad debe ser false para conjuntos con diferente grado de pertenencia") {
    assert(!igualdad(cd1, cd3))
  }

  test("igualdad debe ser true para funciones iguales en todo el dominio") {
    assert(igualdad(cd4, cd5))
  }

  test("igualdad debe ser false cuando hay una diferencia en la pertenencia de un valor") {
    assert(!igualdad(cd4, cd6))
  }
}
