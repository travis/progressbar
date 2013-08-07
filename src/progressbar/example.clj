(ns progressbar.example
  (:require [progressbar.core :refer [progressbar]]))

(defn example1 []
  (map #(do (Thread/sleep 100) %) (progressbar (vec (range 100)))))

(defn example2 []
  (doall (map #(do (Thread/sleep 100) %) (progressbar (range 101) :count 101 :width 80)))
  nil)
