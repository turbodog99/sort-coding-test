(ns sort-coding-test.data-types.people-test
  (:require [clojure.test :refer :all]
            [sort-coding-test.data-types.people :refer :all]
            [sort-coding-test.test-data :as test-data]))

(deftest vector->map-test
  (is (= test-data/unordered-people-maps
         (map vector->map test-data/unordered-people-vectors))))

(deftest map->vector-test
  (is (= test-data/unordered-people-vectors
         (map map->vector test-data/unordered-people-maps))))

(def test-last-name "last_name")
(def test-first-name "first_name")
(def test-gender "M")
(def test-favorite-color "favorite_color")
(def test-date-of-birth "9/14/1975")

(def test-vector
  [test-last-name test-first-name test-gender test-favorite-color test-date-of-birth])

(def test-map
  {
   :last-name test-last-name
   :first-name test-first-name
   :gender (keyword (clojure.string/lower-case test-gender))
   :favorite-color test-favorite-color
   :date-of-birth (java.util.Date. test-date-of-birth)
   })

(def test-empty-gender-vector
  (assoc test-vector 2 ""))

(def test-empty-gender-map
  (assoc test-map :gender nil))

(def test-empty-date-of-birth-vector
  (assoc test-vector 4 ""))

(def test-empty-date-of-birth-map
  (assoc test-map :date-of-birth nil))

(def test-empty-string-field-vector
  (assoc test-vector 0 ""))

(def test-empty-string-field-map
  (assoc test-map :last-name ""))

;; Note: This is based on the conversion functions having knowledge of the expected
;; schema of the input data. I'm assuming mostly clean data. I figured handling empty
;; fields would be helpful without adding too much complexity.

;; For requested simplicity, I don't cover a number of possible cases here that could
;; make things break. For example, what happens if I put an actual integer in the :last-name
;; field of the map? If I want it completely bulletproof, I'd need to start doing some type testing,
;; throwing errors, or coercion. I've clearly specified in the comments these fields
;; should be strings. They aren't nil, integers, or anything else.
(deftest empty-fields-tests
  (testing "empty gender field"
    (is (= test-empty-gender-map
           (vector->map test-empty-gender-vector))
        "vector->map conversion")
    (is (= test-empty-gender-vector
           (map->vector test-empty-gender-map))
        "map->vector conversion"))
  (testing "empty date of birth field"
    (is (= test-empty-date-of-birth-map
           (vector->map test-empty-date-of-birth-vector))
        "vector->map conversion")
    (is (= test-empty-date-of-birth-vector
           (map->vector test-empty-date-of-birth-map))
        "map->vector conversion"))
  (testing "empty string field"
    (is (= test-empty-string-field-map
           (vector->map test-empty-string-field-vector))
        "vector->map conversion")
    (is (= test-empty-string-field-vector
           (map->vector test-empty-string-field-map))
        "map->vector conversion")))
