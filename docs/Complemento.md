# Definición de Complemento de Conjuntos Difusos

---
El complemento de un conjunto difuso $S \subseteq U$ se define como:
$$f_{\neg S}(s) = 1 - f_S(s)$$
donde $U = [0, 1000]$ es el universo discreto, y $f_S(s)$ es el grado de pertenencia de $s$ a $S$.
Interpretación:

Si $f_S(s) = 0$, entonces $f_{\neg S}(s) = 1$, indicando plena no pertenencia.
Si $f_S(s) = 1$, entonces $f_{\neg S}(s) = 0$, indicando plena pertenencia al complemento.
Para valores intermedios (e.g., $f_S(s) = 0.5$), el complemento refleja el grado opuesto.

---
## Implementación en Scala
```Scala
def complemento(c: ConjDifuso): ConjDifuso = {
    def f(x: Int): Double = 1.0 - c(x)
    f
  }
```

La función complemento toma un conjunto difuso $c$ y retorna una nueva función que calcula $1 - f_c(x)$ para cada $x$.

---

##  Argumentación de Corrección
### Queremos demostrar que:
$$\forall s \in U, f_{\neg S}(s) = 1 - f_S(s)$$
### Caso Base
Para $x = 0$ con $c(x) = 0.2$:

$f_{\neg S}(0) = 1 - 0.2 = 0.8$, lo cual es correcto.

Para $x = 10$ con $c(x) = 1.0$:

$f_{\neg S}(10) = 1 - 1.0 = 0.0$, lo cual es correcto.

### Caso Inductivo
Supongamos que $f_{\neg S}(k) = 1 - f_S(k)$ es correcto. Para $k + 1$:

$f_{\neg S}(k+1) = 1 - f_S(k+1)$, que sigue la definición y es consistente para todo $x$.

Por inducción, la función implementa el complemento difuso correctamente.

---

## Diagrama de Llamadas o de Pilas
````mermaid
sequenceDiagram
    participant Main
    participant Complemento

    Main->>Complemento: complemento(cd1)
    Complemento->>Complemento: f(3)
    Complemento-->>Main: 0.8
    Main->>Complemento: complemento(cd1)
    Complemento->>Complemento: f(15)
    Complemento-->>Main: 0.0

````

---

## Conclusión para la Función complemento
La función complemento calcula correctamente el grado de pertenencia al complemento de un conjunto difuso, manteniendo valores en el rango $[0, 1]$ y reflejando la definición teórica.

Propiedades Verificadas

Invariancia del Complemento Doble: $\text{complemento}(\text{complemento}(c)) = c$.
Rango Preservado: $\forall x, 0 \leq \text{complemento}(c)(x) \leq 1$.

