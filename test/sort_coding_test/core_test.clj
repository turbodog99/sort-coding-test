(ns sort-coding-test.core-test
  (:require [clojure.test :refer :all]
            [sort-coding-test.core :refer :all]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.java.io :as io]))

(def file-options
  {:comma-delimited-file "-c"
   :pipe-delimited-file "-p"
   :space-delimited-file "-s"})

(deftest cli-test
  (with-redefs [file-exists-validator [(fn [_] true) ""]]
    (testing "Properly parses arguments"
      (doseq [[option-key option-string] file-options]
        (is (= '("test1.csv")
               (get-in (parse-opts
                        [option-string "test1.csv"] (cli-options))
                       [:options option-key]))
            (str "single argument " option-string))
        (is (= '("test1.csv" "test2.csv")
               (get-in (parse-opts
                        [option-string "test1.csv" option-string "test2.csv"] (cli-options))
                       [:options option-key]))
            (str "accumulates file arguments for " option-string)))
      (is (get-in (parse-opts ["--start-server"] (cli-options)) [:options :start-server]))
      (is (not (get-in (parse-opts [] (cli-options)) [:options :start-server])))))

  (testing "Detects file existence errors"
    (is (empty? (:errors (parse-opts ["-c" (io/resource "empty_file.csv")] (cli-options)))))
    (is (= 1 (count (:errors (parse-opts ["-c" "does_not_exist.csv"] (cli-options))))))))
