(ns progressbar.core
  (:require [progressbar.progress-seq :as progress-seq]))

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
                                        (progress-seq/bounded count {:width width})
                                        (progress-seq/create seq :print-every print-every :width width)))]
    (map (fn [item progress]
           (when progress
             (print progress)
             (flush))
           item)
         seq progress-seq)))
