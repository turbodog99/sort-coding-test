(ns sort-coding-test.data-types.people-test
  (:require [clojure.test :refer :all]
            [sort-coding-test.data-types.people :refer :all]
            [sort-coding-test.test-data :as test-data]))

(deftest vector->map-test
  (is (= test-data/unordered-people-map
         (map vector->map test-data/unordered-people-vectors))))

(deftest map->vector-test
  (is (= test-data/unordered-people-vectors
         (map map->vector test-data/unordered-people-map))))

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

(deftest empty-fields-tests
  (testing "empty gender field"
    (is (= test-empty-gender-map
           (vector->map test-empty-gender-vector)))
    (is (= test-empty-gender-vector
           (map->vector test-empty-gender-map))))
  (testing "empty date of birth field"
    (is (= test-empty-date-of-birth-map
           (vector->map test-empty-date-of-birth-vector)))))
