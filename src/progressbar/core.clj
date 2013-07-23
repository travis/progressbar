(ns progressbar.core
  (:require [clojure.string :as str]))


(defn unbounded-progress-seq [print-every]
  (map (fn [index]
         (when (= 0 (mod index print-every))
           (str "\r["(str/join (take (int (/ index print-every)) (repeat "=")))")")))
       (iterate inc 0)))

(defn progressbar [seq & {:keys [print-every progress-seq]
                          :or {print-every 10}}]
  (let [progress-seq (or progress-seq (unbounded-progress-seq print-every))]
    (map (fn [item progress]
           (when progress
             (print progress)
             (flush))
           item)
         seq progress-seq)))
