package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
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

  // Conjuntos auxiliares necesarios
  val vacio: ConjDifuso = _ => 0.0
  val lleno: ConjDifuso = _ => 1.0

  //Tests para igualdad

  test("igualdad debe ser true para dos conjuntos identicos") {
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

  //Tests para inclusion

  test("un conjunto vacio esta incluido en cualquier otro") {
    assert(inclusion(vacio, cd1))
    assert(inclusion(vacio, cd3))
    assert(inclusion(vacio, lleno))
  }

  test("un conjunto esta incluido en si mismo") {
    assert(inclusion(cd1, cd1))
    assert(inclusion(cd4, cd4))
  }

  test("cd1 esta incluido en cd2 porque son identicos") {
    assert(inclusion(cd1, cd2))
  }

  test("cd3 no esta incluido en cd1 porque en x>=5 tiene mayor pertenencia") {
    assert(!inclusion(cd3, cd1))
  }

  test("cd6 esta incluido en cd5 porque en cada punto es menor o igual") {
    assert(inclusion(cd6, cd5))
  }

  test("cd5 no esta incluido en cd6 porque en x pares tiene mayor pertenencia") {
    assert(!inclusion(cd5, cd6))
  }

  test("cualquier conjunto esta incluido en el conjunto lleno") {
    assert(inclusion(cd1, lleno))
    assert(inclusion(cd3, lleno))
    assert(inclusion(cd6, lleno))
  }
}
