(ns sort-coding-test.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.java.io :as io]
            [sort-coding-test.delimited-data :as delimited-data])
  (:gen-class))

(def delimiters
  "Options for handling multiple delimiters. :option-name, :short-opt, :long-opt, and :description
  are used by CLI tools for parsing the CLI and displaying usage."
  [{:name "comma"
    :option-name :comma-delimited-file
    :short-opt "-c"
    :long-opt "--comma-delimited-file FILENAME"
    :description "comma delimited filename"}
   {:name "pipe"
    :option-name :pipe-delimited-file
    :short-opt "-p"
    :long-opt "--pipe-delimited-file FILENAME"
    :description "pipe delimited filename"}
   {:name "space"
    :option-name :space-delimited-file
    :short-opt "-s"
    :long-opt "--space-delimited-file FILENAME"
    :description "space delimited filename"}])

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

(defn delimiter-options
  []
  (reduce (fn [acc delimiter]
            (conj acc [(:short-opt delimiter)
                       (:long-opt delimiter)
                       (:description delimiter)
                       :parse-fn #(list %)
                       :assoc-fn accumulate-filenames
                       :validate file-exists-validator])) [] delimiters))

;; This is a function so file-exists-validator can be overriden when testing
(defn cli-options
  []
  (conj (delimiter-options) [nil "--start-server" "Start REST Server"]))

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

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn load-files-for-delimiter
  "Loads files for the given delimiter entry from delimiters above and returns a collection of people"
  [delimiter cli-options]
  (reduce #(concat %1 (delimited-data/read-all-lines %2 (:name delimiter)))
          []
          (get cli-options (:option-name delimiter))))

(defn load-delimited-files
  "Loads all delimited files specified on the command-line and returns a collection of people"
  [cli-options]
  (reduce #(concat %1 (load-files-for-delimiter %2 cli-options)) [] delimiters))

(def people (ref []))

(defn -main
  "CLI entry point for sort program"
  [& args]
  (println "Delimiter options" delimiter-options)
  (let [{:keys [options arguments errors summary]} (parse-opts args (cli-options))]
    (cond
      (empty? options)
      (exit 1 (usage summary))
      (some #(seq (get options %)) [:comma-delimited-file :pipe-delimited-file :space-delimited-file])
      (dosync
       (ref-set people (load-delimited-files options)))
      :else
      (exit 1 (usage summary)))

    (if (empty? (get options :start-server))
      (println "Display people")
      (println "Start server")))


  (println "People: " @people))
