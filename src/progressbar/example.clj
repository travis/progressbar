(ns progressbar.example
  (:require [progressbar.core :refer [progressbar]]))

(defn example1 []
  (dorun (map #(do (Thread/sleep 100) %) (progressbar (range 100)))))

(defn example2 []
  (dorun (map #(do (Thread/sleep 100) %) (progressbar (range 101) :count 101 :width 80))))

(defn example3 []
  (dorun (map #(do (Thread/sleep 100) %) (progressbar (vec (range 100))))))
