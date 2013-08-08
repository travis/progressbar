(ns progressbar.core
  (:require [clojure.string :as str]))


(defn unbounded-progress-seq [print-every]
  (map (fn [index]
         (when (= 0 (mod index print-every))
           (str "\r["(str/join (take (int (/ index print-every)) (repeat "=")))")")))
       (iterate inc 0)))

(defn bounded-progress-seq [cnt & {:keys [width]
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

(defn progressbar
  "Transparently wrap any `seq`able to print feedback to standard out as items in
the seq are processed.

:print-every if using the default `:progress-seq`, progressbar will print an `=` every time this many items are processed
:progress-seq a seq (that **must** be as long or longer than the seq being processed) of strings to print. nil elements will not be printed.
"
  [seq & {:keys [print-every progress-seq count width]
          :or {print-every 10
               width 64}}]
  (let [progress-seq (or progress-seq (if count
                                        (bounded-progress-seq count :width width)
                                        (unbounded-progress-seq print-every)))]
    (map (fn [item progress]
           (when progress
             (print progress)
             (flush))
           item)
         seq progress-seq)))

(comment



  (condp = 3
      1 "insane"
      2 "less insane"
      3 "CRAZY")

  (let [total-number-of-equals 10
        count 100
        index 99

        ]
    )


  )
