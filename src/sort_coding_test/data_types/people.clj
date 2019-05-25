(ns sort-coding-test.data-types.people)
;;;; Defines functions for working with people data.

;;;; I avoided using plumatic/schema for simplicity but would consider it in a more
;;;; complex use case. We were told the data is as expected, and the conversions that
;;;; need to be done are easy without sending someone off to learn how a
;;;; coercer works. I would use it if the input was JSON.


;;;; For our purposes, a person in vector form will look like:

;;;; [[last-name] [first-name] [gender] [favorite-color] [date-of-birth]]

;;;; where all values are strings. Dates are strings of the form M/D/YYYY.
;;;; Gender is a single lowercase string.


;;;; A person in map form will look as follows:

;;;; {
;;;;  last-name: String
;;;;  first-name: String
;;;;  gender: either :m or :f
;;;;  favorite-color: String
;;;;  date-of-birth: java.util.Date
;;;;  }

(def default-date-format-string
  "M/d/YYYY")

(def default-date-formatter
  (java.text.SimpleDateFormat. default-date-format-string))

(defn date->formatted-string
  "Converts a java.util.Date to a string using the specified date format"
  [date]
  (.format default-date-formatter date))

;; TODO: make gender and date nil if not specified
(defn vector->map
  [[last-name first-name gender favorite-color date-of-birth]]
  {
   :last-name last-name
   :first-name first-name
   :gender (when (seq gender) (keyword (clojure.string/lower-case gender)))
   :favorite-color favorite-color
   :date-of-birth (java.util.Date. date-of-birth)
   })

;; TODO: handle nil gender and date
(defn map->vector
  [{:keys [last-name first-name gender favorite-color date-of-birth]}]
  [last-name
   first-name
   (if gender (clojure.string/upper-case (name gender)) "")
   favorite-color
   (date->formatted-string date-of-birth)]
  )
