(ns sq_count)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; you can use the function "count" to obtain the number
;;; of element in a sequence. Implement your own version of count
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



;; usando el "for" list comprehension para transformar 
;; la lista de elementos a otra lista con la misma cantidad de 
;; elementos pero donde todos tienen valor 1, luego sumar todos
;; los 1 a traves de reduce. 
(defn count-for [coll]
  (reduce + (for [x coll] 1)))


;; misma implementacion que la anterior pero en lugar de utilizar
;; "for" para convertir la lista a todos 1 usar "map" esta vez
(defn count-map [coll] 
  (reduce + (map (fn[x] 1) coll) ))


;; Clojure no tiene "tail call optimization" por lo que 
;; esta funcion consumira Stack y puede causar Stack Overflow
;; para colecciones bien grandes, por ejemplo:
;; (count-rec1 (range 100000000) )
(defn count-rec1 [coll]
  (cond (empty? coll) 0
        :else (+ 1 (count-rec1 (rest coll)))))


;; Esta es la misma implementacion de count-rec1 pero que usa
;; 'recur' que es una respuesta de Clojure a la imcapacidad de 
;; la JVM de realizar tail call optimization.
;;
;; HINT: recur solo puede usarse en "tail position" por esta razon
;; no se puede terminar con (+ 1 (recur ...)) como es el caso 
;; de la funcion anterior, pues en ese caso recur no esta en tail position 
(defn count-rec2 [coll]
  (defn -count-rec2 [-coll acc] 
    (if (empty? -coll)
      acc
      (recur (rest -coll) (inc acc) )))
  (-count-rec2 coll 0))



(defn count-seq1 [coll]  (reduce (fn [acc v] (+ 1 acc)) (cons 0 coll)) )


;; misma implementacion que count-seq1 pero con algunas mejoras
;; notese que underscoard se utiliza para nombrar un parametro que no 
;; se utiliza
(defn count-seq2 [coll] 
  (reduce (fn [acc _] (inc acc)) 0 coll ))