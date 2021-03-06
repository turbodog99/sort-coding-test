(ns sort-coding-test.data-types.people
  (:require [sort-coding-test.sorting :as sorting]
            [schema.core :as s]))
;;;; Defines functions for working with people data.

;;;; For our purposes, a person in vector form will look like:

;;;; ["last-name" "first-name" "gender" "favorite-color" "date-of-birth"]

;;;; where all values are strings. Dates are strings of the form M/D/YYYY.
;;;; Gender is a single lowercase string. Any missing value should be an empty string.

;;;; Any missing string value should be the empty string.

;;;; A person in map form will look as follows:

(def Person
  {:last-name s/Str
   :first-name s/Str
   :gender (s/enum :m :f)
   :favorite-color s/Str
   :date-of-birth java.util.Date})

(def default-date-format-string
  "M/d/YYYY")

(def default-date-formatter
  (java.text.SimpleDateFormat. default-date-format-string))

(defn date->formatted-string
  "Converts a java.util.Date to a string using the specified date format.
  Returns an empty string on a date it can't processs."
  [date]
  (try
    (.format default-date-formatter date)
    (catch Exception e "")))

(defn gender->string
  "Converts the :m and :f keywords to display strings. Returns empty string on
  unknown gender."
  [gender]
  (cond
    (= gender :f)
    "F"
    (= gender :m)
    "M"
    :else
    ""))

(defn vector->map
  [[last-name first-name gender favorite-color date-of-birth]]
  {
   :last-name last-name
   :first-name first-name
   :gender (when (seq gender) (keyword (clojure.string/lower-case gender)))
   :favorite-color favorite-color
   :date-of-birth (when (seq date-of-birth) (java.util.Date. date-of-birth))
   })

(defn map->vector
  [{:keys [last-name first-name gender favorite-color date-of-birth]}]
  [last-name
   first-name
   (if gender (clojure.string/upper-case (name gender)) "")
   favorite-color
   (if date-of-birth (date->formatted-string date-of-birth) "")])

;;; Common sorts
(defn sort-by-gender-asc-last-name-asc
  [people-maps]
  (sorting/sort-maps-by-keys-and-directions
   people-maps
   [[:gender :asc] [:last-name :asc]]))

(defn sort-by-date-of-birth-asc
  [people-maps]
  (sorting/sort-maps-by-keys-and-directions
   people-maps
   [[:date-of-birth :asc]]))

(defn sort-by-last-name-desc
  [people-maps]
  (sorting/sort-maps-by-keys-and-directions
   people-maps
   [[:last-name :desc]]))

(defn sort-by-gender-asc
  [people-maps]
  (sorting/sort-maps-by-keys-and-directions
   people-maps
   [[:gender :asc]]))

(defn sort-by-name-asc
  [people-maps]
  (sorting/sort-maps-by-keys-and-directions
   people-maps
   [[:first-name :asc] [:last-name :asc]]))
