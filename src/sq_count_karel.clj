(defn seq-count [coll]  (reduce (fn [acc v] (+ 1 acc)) (cons 0 coll)) )
