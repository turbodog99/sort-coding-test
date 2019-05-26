(ns sort-coding-test.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.java.io :as io])
  (:gen-class))

;; Each successive filename option will add to the accumulated filenames for that option
(defn accumulate-filenames
  [options-map option-id new-value]
  (assoc options-map option-id (concat (get options-map option-id) new-value)))

;; There will always be only one file since this is called right after parsing
(defn validate-first-input-file-exists
  [filename-list]
  (.exists (io/as-file (first filename-list))))

(def file-does-not-exist-message
  "Input file doesn't exist")

(def file-exists-validator
  [validate-first-input-file-exists file-does-not-exist-message])

;; This is a function so file-exists-validator can be overriden when testing
(defn cli-options
  []
  [["-c" "--comma-delimited-file FILENAME" "Comma Delimited Filename"
    :parse-fn #(list %)
    :assoc-fn accumulate-filenames
    :validate file-exists-validator]
   ["-p" "--pipe-delimited-file FILENAME" "Pipe Delimited Filename"
    :parse-fn #(list %)
    :assoc-fn accumulate-filenames
    :validate file-exists-validator]
   ["-s" "--space-delimited-file FILENAME" "Space Delimited Filename"
    :parse-fn #(list %)
    :assoc-fn accumulate-filenames
    :validate file-exists-validator]
   [nil "--start-server" "Start REST Server"]])

(defn usage [options-summary]
  (->> ["Prints the 3 requested views of the provided delimited files"
        ""
        "They are: "
        ""
        "- Sorted by gender then by last name ascending"
        "- Sorted by birthdate ascending"
        "- Sorted by last name descending"
        ""
        "In all delimited input files, the field order is:"
        "  [last-name] [first-name] [gender] [favorite-color] [date-of-birth]"
        ""
        "Delimited file inputs can be provided more than once for multiple files."
        "There is no record deduplication."
        ""
        "Usage: program-name [options]"
        ""
        "Options: "
        options-summary]
       (clojure.string/join \newline)))

;; TODO: now that parsing and validation work, make it do something
(defn -main
  "CLI entry point for sort program"
  [& args]
  ;; (println (usage (:summary (parse-opts args (cli-options)))))
  (println "Parse opts output: " (parse-opts args (cli-options))))
