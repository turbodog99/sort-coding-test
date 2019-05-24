(ns sort-coding-test.delimited-file-reader-test
  (:require [clojure.test :refer :all]
            [sort-coding-test.delimited-file-reader :refer :all]
            [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def test-people
  "A group of people specifically not in any desired order."
  [
   ["Herman" "Mark" "M" "blue" "9/14/1975"]
   ["Burns" "Montgomery" "M" "red" "12/12/1881"]
   ["Simpson" "Homer" "M" "yellow" "5/12/1956"]
   ["Simpson" "Marge" "F" "blue" "3/19/1951"]
   ["Simpson" "Bart" "M" "orange" "2/3/1983"]
   ["Simpson" "Lisa" "F" "green" "5/9/1982"]
   ["Cartman" "Eric" "M" "brown" "11/13/1987"]
   ]
  )

;; I originally intended to use the CSV writer for this, but it only supports
;; character and not string separators.
(defn create-test-files
  "Adds people into test delimited files. The filename will be in the form
  'temp/test-delimiter_name-delimited-people.csv"
  []
  (doseq [delimiter delimiters]
    (let [[name separator] delimiter]
      (with-open [w (io/writer (str "temp/test-" name "-delimited-people.csv"))]
        (doseq [test-person test-people]
          (.write w (str (clojure.string/join separator test-person) "\n")))))))

(defn gen-test-files-fixture
  [f]
  (create-test-files)
  (f))

(use-fixtures :once gen-test-files-fixture)

(testing "Read delimited file"
  (is (= 0 0)))
