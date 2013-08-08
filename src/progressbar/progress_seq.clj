(ns progressbar.progress-seq
  (:require [clojure.string :as str]))

(defn unbounded [{:keys [print-every]
                  :or {print-every 10}}]
  (map (fn [index]
         (when (= 0 (mod index print-every))
           (str "\r["(str/join (take (int (/ index print-every)) (repeat "=")))")")))
       (iterate inc 0)))

(defn bounded [cnt {:keys [width]
                    :or {width 64}}]
  {:pre [(number? width) (pos? width)] }
  "Produces a seq like:

 [         ]
 [=        ]
 [==       ]
 [===      ]
 [====     ]
 [=====    ]
 ...
 [=========]
"

  (let [items-per-equals (int (/ cnt width))]
   (map (fn [index]
          (let [n (inc index) ; index starting from 1
                last-n-remainder (mod cnt items-per-equals)
                segment-processed? #(= (mod n items-per-equals) last-n-remainder)]
            (when (segment-processed?)
              (let [number-of-equals (int (* width (/ n cnt)))]
                (apply str (concat ["\r["]
                                   (take number-of-equals (repeat "="))
                                   (take (- width number-of-equals) (repeat " "))
                                   ["]"]))))))
        (iterate inc 0))))

(defprotocol ProgressSeq
  (progress-seq [object args]))

(extend-protocol ProgressSeq
  clojure.lang.Counted
  (progress-seq [object args]
    (bounded (count object) args))
  java.lang.Object
  (progress-seq [object args]
    (unbounded args))
)

(defn create [object & {:as args}]
  (progress-seq object args))
