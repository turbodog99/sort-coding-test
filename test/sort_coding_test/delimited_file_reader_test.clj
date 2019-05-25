(ns sort-coding-test.delimited-file-reader-test
  (:require [clojure.test :refer :all]
            [sort-coding-test.delimited-file-reader :refer :all]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [sort-coding-test.test-data :as test-data]))

(def test-file-generator-delimiters
  "The test files have whitespace around some delimiters. I want to make sure I'm
  testing with the same file format this will be tested with. I'll use Clojure's CSV
  reader and trim the data when reading."
  {"pipe" " | "
   "comma" ", "
   "space" " "})

(defn test-filename
  [delimiter-name]
  (str "temp/test-" delimiter-name "-delimited-people.csv"))

;; I originally intended to use the CSV writer for this, but it only supports
;; character and not string separators.
(defn create-test-files
  "Adds people into test delimited files. The filename will be in the form
  'temp/test-delimiter_name-delimited-people.csv"
  []
  (doseq [delimiter test-file-generator-delimiters]
    (let [[delimiter-name delimiter-separator] delimiter]
      (with-open [w (io/writer (test-filename delimiter-name))]
        (doseq [test-person test-data/unordered-people-strings]
          (.write w (str (clojure.string/join delimiter-separator test-person) "\n")))))))

(defn gen-test-files-fixture
  [f]
  (create-test-files)
  (f))

(use-fixtures :once gen-test-files-fixture)

(deftest read-delimited-files
  (testing "Reading data from files with all required delimiters"
    ; It should be the same as the data that generated them
    (doseq [delimiter delimiters]
      (let [[delimiter-name delimiter-separator] delimiter]
        (is (= test-data/unordered-people-strings
               (read-all-lines (test-filename delimiter-name) delimiter-name))))))

  (testing "Exception handling"
    (let [valid-delimiter-name (key (first delimiters))
          filename-that-exists (test-filename valid-delimiter-name)]
      (is (thrown? java.io.FileNotFoundException
                   (read-all-lines "file-that-does-not-exist" valid-delimiter-name)))
      (is (thrown? AssertionError
                   (read-all-lines filename-that-exists "invalid-delimiter-name"))))))
