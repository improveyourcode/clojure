(ns sq_count)

;;; you can use the function "count" to obtain the number
;;; of element in a sequence. Implement your own version of
;;; count

(defn my-count [coll]
  (reduce + (for [x coll] 1)))

(defn count-rec [coll]
  (cond (empty? coll) 0
        :else (+ 1 (count-rec (rest coll)))))

(defn seq-count [coll]  (reduce (fn [acc v] (+ 1 acc)) (cons 0 coll)) )
