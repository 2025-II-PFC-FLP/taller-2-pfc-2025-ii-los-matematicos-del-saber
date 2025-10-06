package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import org.scalactic.Tolerance.convertNumericToPlusOrMinusWrapper


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

  //Tests para grande
  test("grande debe retornar 0.0 para valores menores o iguales a 0") {
    val g = grande(1, 2)
    assert(g(0) === 0.0)
    assert(g(-1) === 0.0)
  }

  test("grande debe calcular correctamente (x/(x+d))^e para x > 0") {
    val g = grande(1, 2)
    assert(g(1) === 0.25 +- 1e-9)
    assert(g(2) === 0.4444444444444444 +- 1e-6)
    assert(g(100) === 0.9801980198019802 +- 1e-4)
  }

  test("grande con diferentes d y e debe ajustar el grado de pertenencia") {
    val g = grande(2, 3)
    assert(g(1) === math.pow(1.0 / 3.0, 3) +- 1e-6)
    assert(g(10) === math.pow(10.0 / 12.0, 3) +- 1e-6)
    assert(g(1000) > 0.99) // se aproxima suficientemente a 1
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

  //Tests para complemento
  test("complemento debe retornar 1 - grado de pertenencia para cualquier x") {
    val comp = complemento(cd1)
    assert(comp(3) === 0.8 +- 1e-9)
    assert(comp(7) === 0.2 +- 1e-9)
    assert(comp(15) === 0.0 +- 1e-9)
  }

  test("complemento de complemento debe ser el conjunto original") {
    val comp = complemento(cd4)
    val compComp = complemento(comp)
    (0 to 10).foreach { x =>
      assert(compComp(x) === cd4(x) +- 1e-8)
    }
  }

  test("complemento debe manejar valores en los bordes 0 y 1") {
    val cd: ConjDifuso = x => if (x > 10) 1.0 else 0.0
    val comp = complemento(cd)
    assert(comp(5) === 1.0 +- 1e-9)
    assert(comp(15) === 0.0 +- 1e-9)
  }

  test("complemento debe funcionar con conjuntos constantes") {
    val cdConst: ConjDifuso = _ => 0.5
    val comp = complemento(cdConst)
    assert(comp(42) === 0.5 +- 1e-9)
  }

  test("complemento debe preservar el rango [0,1]") {
    val comp = complemento(cd3)
    (0 to 20).foreach { x =>
      val res = comp(x)
      assert(res >= 0.0 && res <= 1.0)
    }
  }

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

  // Test de union
  test("union debe devolver el valor máximo de pertenencia entre dos conjuntos") {
    val u = union(cd1, cd3)
    (0 to 15).foreach { x =>
      val esperado = math.max(cd1(x), cd3(x))
      assert(u(x) === esperado +- 1e-9)
    }
  }

  test("union de dos conjuntos idénticos debe ser igual al conjunto original") {
    val u = union(cd4, cd5)
    (0 to 10).foreach { x =>
      assert(u(x) === cd4(x) +- 1e-9)
    }
  }

  test("union con conjunto vacío debe ser el conjunto original") {
    val u = union(cd1, vacio)
    (0 to 10).foreach { x =>
      assert(u(x) === cd1(x) +- 1e-9)
    }
  }

  test("union con conjunto lleno debe dar siempre 1.0") {
    val u = union(cd3, lleno)
    (0 to 10).foreach { x =>
      assert(u(x) === 1.0 +- 1e-9)
    }
  }

  test("union debe devolver valores dentro del rango [0,1]") {
    val u = union(cd1, cd3)
    (0 to 20).foreach { x =>
      val res = u(x)
      assert(res >= 0.0 && res <= 1.0)
    }
  }
  //Tests para interseccion
  test("interseccion debe devolver el valor mínimo de pertenencia entre dos conjuntos") {
    val i = interseccion(cd1, cd3)
    (0 to 15).foreach { x =>
      val esperado = math.min(cd1(x), cd3(x))
      assert(i(x) === esperado +- 1e-9)
    }
  }

  test("interseccion de dos conjuntos idénticos debe ser igual al conjunto original") {
    val i = interseccion(cd4, cd5)
    (0 to 10).foreach { x =>
      assert(i(x) === cd4(x) +- 1e-9)
    }
  }

  test("interseccion con conjunto lleno debe ser el conjunto original") {
    val i = interseccion(cd1, lleno)
    (0 to 10).foreach { x =>
      assert(i(x) === cd1(x) +- 1e-9)
    }
  }

  test("interseccion con conjunto vacío debe ser 0.0 en todo el dominio") {
    val i = interseccion(cd3, vacio)
    (0 to 10).foreach { x =>
      assert(i(x) === 0.0 +- 1e-9)
    }
  }

  test("interseccion debe devolver valores dentro del rango [0,1]") {
    val i = interseccion(cd1, cd3)
    (0 to 20).foreach { x =>
      val res = i(x)
      assert(res >= 0.0 && res <= 1.0)
    }
  }
}