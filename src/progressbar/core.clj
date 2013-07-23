(ns progressbar.core
  (:require [clojure.string :as str]))


(defn unbounded-progress-seq [print-every]
  (map (fn [index]
         (when (= 0 (mod index print-every))
           (str "\r["(str/join (take (int (/ index print-every)) (repeat "=")))")")))
       (iterate inc 0)))

(defn progressbar
  "Transparently wrap any `seq`able to print feedback to standard out as items in
the seq are processed.

:print-every if using the default `:progress-seq`, progressbar will print an `=` every time this many items are processed
:progress-seq a seq (that **must** be as long or longer than the seq being processed) of strings to print. nil elements will not be printed.
"
  [seq & {:keys [print-every progress-seq]
          :or {print-every 10}}]
  (let [progress-seq (or progress-seq (unbounded-progress-seq print-every))]
    (map (fn [item progress]
           (when progress
             (print progress)
             (flush))
           item)
         seq progress-seq)))
