(ns progressbar.core-test
  (:require [clojure.test :refer :all]
            [progressbar.core :refer :all]))

(deftest test-default
  (testing "default behavior"
    (is (= "\r[)\r[=)\r[==)\r[===)\r[====)"
           (with-out-str (doall (map identity (progressbar (range 10) :print-every 2)))))))

  (testing "count"
    (is (= "\r[    ]\r[=   ]\r[==  ]\r[=== ]\r[====]"
           (with-out-str (doall (map identity (progressbar (range 10) :count 10 :width 4))))))
    (is (= "\r[   ]\r[=  ]\r[== ]\r[===]"
           (with-out-str (doall (map identity (progressbar (range 10) :count 10 :width 3)))))))

  (testing "automatic progress-seq selection"
    (is (= "\r[    ]\r[=   ]\r[==  ]\r[=== ]\r[====]"
           (with-out-str (doall (map identity (progressbar (vec (range 10)) :width 4))))))
    (is (= "\r[    ]\r[=   ]\r[==  ]\r[=== ]\r[====]"
           (with-out-str (doall (map identity (progressbar (set (range 10)) :width 4))))))
    (is (= "\r[    ]\r[=   ]\r[==  ]\r[=== ]\r[====]"
           (with-out-str (doall (map identity (progressbar (apply list (range 10)) :width 4))))))))

(deftest test-custom-seq
  (testing "custom seqs"
    (is (= "two
four
six
eight
ten
"
           (with-out-str (doall (map identity (progressbar (range 10)
                                                           :progress-seq [nil "two\n" nil "four\n" nil "six\n" nil "eight\n" nil "ten\n"]))))))

    (is (= "0123456789"
           (with-out-str (doall (map identity (progressbar (range 10 20)
                                                           :progress-seq (iterate inc 0)))))))))

