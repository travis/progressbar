(ns progressbar.core-test
  (:require [clojure.test :refer :all]
            [progressbar.core :refer :all]))

(deftest a-test
  (testing ""
    (is (= "\r[>\r[=>\r[==>\r[===>\r[====>"
           (with-out-str (doall (map identity (progressbar (range 10) :print-every 2))))))))
