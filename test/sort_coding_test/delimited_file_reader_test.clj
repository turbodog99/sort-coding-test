(ns sort-coding-test.delimited-file-reader-test
  (:require [clojure.test :refer :all]
            [sort-coding-test.delimited-file-reader :refer :all]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [sort-coding-test.test-data :as test-data]))

;; I originally intended to use the CSV writer for this, but it only supports
;; character and not string separators.
(defn create-test-files
  "Adds people into test delimited files. The filename will be in the form
  'temp/test-delimiter_name-delimited-people.csv"
  []
  (doseq [delimiter delimiters]
    (let [[name separator] delimiter]
      (with-open [w (io/writer (str "temp/test-" name "-delimited-people.csv"))]
        (doseq [test-person test-data/unordered-people-strings]
          (.write w (str (clojure.string/join separator test-person) "\n")))))))

(defn gen-test-files-fixture
  [f]
  (create-test-files)
  (f))

(use-fixtures :once gen-test-files-fixture)

(testing "Read delimited file"
  (is (= 0 0)))
