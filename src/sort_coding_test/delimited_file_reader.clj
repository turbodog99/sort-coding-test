(ns sort-coding-test.delimited-file-reader
  (:require [clojure.java.io :as io]
            [clojure.data.csv :as csv]
            [clojure.string :as string]))

(def delimiters
  "Maps delimiters from their names to their characters."
  {"pipe" \|
   "comma" \,
   "space" \space})

(def valid-delimiter-names
  (keys delimiters))

;; NOTE: Since we're sorting in memory, all of the entries must be read at some point.
;; So, this returns all of the lines rather than a lazy seq so the file handle
;; can be let go.
(defn read-all-lines
  "Reads the lines of the file with the given filename using the
  provided delimiter. Output looks as expected by the Clojure CSV library
  but with the strings trimmed."
  [filename delimiter-name]
  (when-not (.exists (io/file filename))
    (throw (java.io.FileNotFoundException. (str "The file " filename " was not found."))))

  (if-let [delimiter (get delimiters delimiter-name)]
    (with-open [reader (io/reader filename)]
      (let [file-lines (csv/read-csv reader :separator delimiter)]
        (doall (for [file-line file-lines]
                 (map string/trim file-line)))))
    (throw (AssertionError. (str "Unsupported delimiter name passed. "
                                 "Valid delimiter names are: " valid-delimiter-names)))))
