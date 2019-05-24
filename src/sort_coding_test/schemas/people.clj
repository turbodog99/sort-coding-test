(ns sort-coding-test.schemas.people
  (:require [schema.core :as s])
  (:import java.util.Date))

;; File representation. Delimiters can be pipe, comma, or space.
;; LastName | FirstName | Gender | FavoriteColor | DateOfBirth

(def Person
  "A schema for our internal person representation"
  {:last-name s/Str
   :first-name s/Str
   :gender (s/enum :m :f)
   :favorite-color s/Str
   :date-of-birth Date})
