(ns trampoline_example)

;; supongamos que tenemos dos funciones my-odd1? y my-even1?
(declare my-odd1? my-even1?)

;; estas funciones son recursivas y se usan una a la otra de la
;; siguiente forma:
(defn my-even1? [n]
  (if (zero? n)
    true
    (my-odd1? (dec n))))

(defn my-odd1? [n]
  (if (zero? n)
    false
    (my-even1? (dec n))))

;; El problema con estas funciones es que JVM no soporta tail call 
;; optimization por lo que cada llamada consumira stack y con un numero
;; grande de llamadas esto puede causar Stack Overflow. Para comprobar lo 
;; anterior podemos invocar (my-odd1? 100000)

(try (my-odd1? 100000) 
     (catch java.lang.StackOverflowError e (prn "Stack Overflow while executing (my-odd1? 100000)")))

;; TRAMPOLIN AL RESCATE

;; Trampolin es una tecnica utilizada para solucionar el problema de la acumulacion
;; de stack. El mismo termino "trampolin" nos ayudara a recordar como es que esta 
;; tecnica funciona. La idea es que existe una funcion que actuara como plataforma 
;; de lanzamiento (o trampolin) y desde la cual se efectuaran todas las llamadas. 
;; en lugar de realizar llamadas recursivas (o llamadas a otras funciones, como es 
;; el caso en nuestro ejemplo) se retornara la funcion a ejecutar y solo el trampolin
;; es quien efectuara la llamada. El stack seria algo asi:

;; tramp -> f1 -
;;   ^          |
;;   |____f2____|
;;
;; Tramp ejecuta la funcion f1 y esta en lugar de efectuar la llamada a la funcion f2
;; (aqui f2 puede ser la misma funcion que es la recursividad mas comun) solo retorna 
;; la funcion a ejecutar. Es la funcion trampolin quien ejecutara esta funcion
;;
;; tramp -> f2 -
;;   ^          |
;;   |____f3____|
;;
;; Asi mientras el resultado sea una funcion. Supongamos que f3 retorna el valor 45
;; entonces esta seria el ultimo llamado que efectue trampolin
;;
;; tramp -> f3 -> 45

;; ARREGLANDO NUESTRO EJEMPLO ANTERIOR

;; utilizare los mismos nombres anteriores, pero esta vez con sufijo "2"
(declare my-odd2? my-even2?)

;; escribiremos exactamente las mismas funciones, pero en lugar de efectuar las llamadas
;; retornaremos la siguiente funcion a ejecutar
(defn my-even2? [n]
  (if (zero? n)
    true
    #(my-odd2? (dec n))))

(defn my-odd2? [n]
  (if (zero? n)
    false
    #(my-even2? (dec n))))

;; como ven todo fue tan simple como adicionar el caracter # delante de la llamada
;; a la funcion. Esto es equivalente a crear una funcion anonima en clojure. 

;; Toda la logica de trampolin ya esta implementada en clojure, asi que solo nos 
;; resta utilizarla como sigue: 
(prn "return value for (trampoline my-even2? 1000000)): " (trampoline my-even2? 1000000))