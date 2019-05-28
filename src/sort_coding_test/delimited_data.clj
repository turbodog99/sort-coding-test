(ns sort-coding-test.delimited-data
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def delimiters
  "Maps delimiters from their names to their regular expressions."
  {"pipe" #"\s*\|\s*"
   "comma" #"\s*,\s*"
   "space" #"\s* \s*"})

(def valid-delimiter-names
  (set (keys delimiters)))

(defn parse-delimited-line
  "Parses a delimited line into a vector of field values"
  [line delimiter-name]
  (if-let [delimiter-re (get delimiters delimiter-name)]
    (string/split line delimiter-re)
    (throw (AssertionError. (str "Unsupported delimiter name passed. "
                                 "Valid delimiter names are: " valid-delimiter-names)))))

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
  (with-open [reader (io/reader filename)]
    (let [file-lines (line-seq reader)]
      (doall (for [file-line file-lines]
               (parse-delimited-line file-line delimiter-name))))))
