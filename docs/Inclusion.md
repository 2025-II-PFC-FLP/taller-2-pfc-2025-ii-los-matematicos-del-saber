## Definicion de Inclusion de Conjuntos Difusos

La inclusion entre dos conjuntos difusos $cd1$ y $cd2$ se define formalmente como:

$
cd1 \subseteq cd2 \iff \forall x \in U,\ cd1(x) \leq cd2(x)
$

donde $U = [0,1000]$ es el universo discreto de valores.

**Interpretación:**  
Esto significa que un conjunto difuso esta incluido en otro si, y solo si, para cada elemento del dominio, el grado de pertenencia en el primero es menor o igual que en el segundo.

---

### Implementación en Scala

La función `inclusion` determina si el conjunto difuso $cd1$ está incluido en $cd2$ en todo el dominio $[0, 1000]$:

```scala
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
```

### Argumentación de Corrección

Queremos demostrar que:

$
\forall S_1, S_2 \subseteq U : inclusion(S_1, S_2) == f(S_1, S_2)
$

donde $f$ representa la definicion teorica de inclusion difusa.

---

### Caso Base de Inclusion

Para el valor inicial del dominio $i = 0$:

Si $cd1(0) > cd2(0)$, la funcion retorna `false`, indicando que la inclusion no se cumple.  
Si $cd1(0) \leq cd2(0)$, la funcion continua evaluando el siguiente indice.  
Si $i > 1000$, se retorna `true`, indicando que todos los elementos del dominio cumplen la inclusion.

Por tanto, el caso base garantiza:

$
inclusion(cd1, cd2) \Rightarrow \forall x \in [0,0],\ cd1(x) \leq cd2(x)
$

y coincide con la definicion matematica de inclusion difusa para el primer elemento del dominio.

## Caso Inductivo de Inclusión

Supongamos que para un valor $k$, la funcion `inclusion` es correcta hasta ese indice, es decir:

$
\forall x \le k,\ cd1(x) \leq cd2(x)
$

Ahora probemos que también se cumple para $k + 1$:

La función evalúa:

```scala
else if (cd1(i) > cd2(i)) 
  false 
else 
  aux(i + 1)
```

Si $cd1(k+1) > cd2(k+1)$, entonces retorna `false`, cumpliendo la definición teórica de no inclusión.  
Si $cd1(k+1) \leq cd2(k+1)$, continúa evaluando el siguiente elemento con `aux(k + 2)`.

Por induccion estructural sobre el dominio discreto $[0,1000]$:

$
inclusion(cd1, cd2) = true \iff \forall x \in U,\ cd1(x) \leq cd2(x)
$

---

## Conclusion para la Función Inclusión

La funcion `inclusion(cd1, cd2)` verifica exhaustivamente todos los elementos del dominio $[0,1000]$. Por lo tanto:

$
inclusion(cd1, cd2) = true \iff \forall x \in U,\ cd1(x) \leq cd2(x)
$

En otras palabras:

$
\forall cd1, cd2 \subseteq U : inclusion(cd1, cd2) \iff \forall x \in U,\ cd1(x) \leq cd2(x)
$

Esto garantiza que la funcion `inclusion` refleja exactamente la definicion teorica de inclusion difusa.

---

## Propiedades Verificadas de Inclusión

- **Reflexividad:**
  $
  inclusion(cd, cd) = true
  $

- **Antisimetría (con igualdad):**
  $
  inclusion(cd1, cd2) \land inclusion(cd2, cd1) \Rightarrow cd1 = cd2
  $

- **Transitividad:**
  $
  inclusion(cd1, cd2) \land inclusion(cd2, cd3) \Rightarrow inclusion(cd1, cd3)
  $

---

### Ejemplo de Ejecucion

```scala
val vacio: ConjDifuso = _ => 0.0
val lleno: ConjDifuso = _ => 1.0

inclusion(vacio, lleno)

```

### Diagrama de Pilas

```mermaid
sequenceDiagram
    participant Main
    participant Inclusion

    Main->>Inclusion: inclusion(cd1, cd2)
    Inclusion->>Inclusion: aux(0)
    Inclusion->>Inclusion: aux(1)
    Inclusion->>Inclusion: aux(2)
    Inclusion->>Inclusion: ...
    Inclusion->>Inclusion: aux(1000)
    Inclusion-->>Main: true
   ```

## Conclusion

- La funcion `inclusion` verifica completamente si un conjunto difuso está incluido en otro mediante la comparacion de todos sus elementos.

- Es determinista y confiable, porque evalua recursivamente cada indice del dominio.

- Cumple las propiedades de una relación de orden parcial: reflexividad, antisimetria y transitividad.

Por lo tanto, se concluye que la funcion `inclusion` es correcta segun la definicion teorica de inclusion en conjuntos difusos.