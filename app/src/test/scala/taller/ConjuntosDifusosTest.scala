package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalactic.Tolerance.convertNumericToPlusOrMinusWrapper
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

  // ---- Tests para grande ----
  test("grande debe retornar 0.0 para valores menores o iguales a 0") {
    val g = grande(1, 2)
    assert(g(0) === 0.0)
    assert(g(-1) === 0.0)
  }

  test("grande debe calcular correctamente (x/(x+d))^e para x > 0") {
    val g = grande(1, 2)
    assert(g(1) === 0.25)
    assert(Math.abs(g(2) - 0.4444444444444444) < 1e-10)
    assert(Math.abs(g(100) - 0.9801980198019802) < 1e-10)
  }

  test("grande con diferentes d y e debe ajustar el grado de pertenencia") {
    val g = grande(2, 3)
    assert(g(1) === Math.pow(1.0 / 3.0, 3))
    assert(g(10) === Math.pow(10.0 / 12.0, 3))
    assert(g(1000) === 1.0 +- 1e-5)
  }

  test("grande debe aproximarse a 1.0 para valores muy grandes") {
    val g = grande(5, 1)
    assert(g(1000000) > 0.99999)
  }

  test("grande debe retornar valores entre 0 y 1 para x > 0") {
    val g = grande(3, 4)
    assert(g(1) > 0.0 && g(1) < 1.0)
    assert(g(50) > 0.0 && g(50) < 1.0)
  }

  // ---- Tests para complemento ----
  test("complemento debe retornar 1 - grado de pertenencia para cualquier x") {
    val comp = complemento(cd1)
    assert(comp(3) === 1.0 - 0.2)
    assert(comp(7) === 1.0 - 0.8)
    assert(comp(15) === 1.0 - 1.0)
  }

  test("complemento de complemento debe ser el conjunto original") {
    val comp = complemento(cd4)
    val compComp = complemento(comp)
    (0 to 10).foreach { x =>
      assert(compComp(x) === cd4(x))
    }
  }

  test("complemento debe manejar valores en los bordes 0 y 1") {
    val cd: ConjDifuso = x => if (x > 10) 1.0 else 0.0
    val comp = complemento(cd)
    assert(comp(5) === 1.0)
    assert(comp(15) === 0.0)
  }

  test("complemento debe funcionar con conjuntos constantes") {
    val cdConst: ConjDifuso = _ => 0.5
    val comp = complemento(cdConst)
    assert(comp(42) === 0.5)
  }

  test("complemento debe preservar el rango [0,1]") {
    val comp = complemento(cd3)
    (0 to 20).foreach { x =>
      val res = comp(x)
      assert(res >= 0.0 && res <= 1.0)
    }
  }

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