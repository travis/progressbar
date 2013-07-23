(ns progressbar.core-test
  (:require [clojure.test :refer :all]
            [progressbar.core :refer :all]))

(deftest test-default
  (testing "default behavior"
    (is (= "\r[)\r[=)\r[==)\r[===)\r[====)"
           (with-out-str (doall (map identity (progressbar (range 10) :print-every 2))))))))

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

