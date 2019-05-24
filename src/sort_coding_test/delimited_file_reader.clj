(ns sort-coding-test.delimited-file-reader
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(def delimiters
  "Maps delimiters from their names to their strings."
  {"pipe" " | "
   "comma" ", "
   "space" " "})

;; (with-open [reader (io/reader "in-file.csv")]
;;   (doall
;;     (csv/read-csv reader)))

;; (with-open [writer (io/writer "out-file.csv")]
;;   (csv/write-csv writer
;;                  [["abc" "def"]
;;                   ["ghi" "jkl"]]))
