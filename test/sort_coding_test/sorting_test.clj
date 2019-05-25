(ns sort-coding-test.sorting-test
  (:require [clojure.test :refer :all]
            [sort-coding-test.sorting :refer :all]
            [sort-coding-test.test-data :as test-data]
            [sort-coding-test.data-types.people :as people]))

(deftest sorting-test
  (is (= test-data/people-vectors-gender-asc-last-name-asc
         (map people/map->vector (sort-maps-by-keys-and-directions
                                  test-data/unordered-people-maps [[:gender :asc] [:last-name :asc]])))
      "gender-asc-last-name-asc")

  (is (= test-data/people-vectors-date-of-birth-asc
         (map people/map->vector (sort-maps-by-keys-and-directions
                                  test-data/unordered-people-maps [[:date-of-birth :asc]])))
      "date-of-birth-asc")

  (is (= test-data/people-vectors-last-name-desc
         (map people/map->vector (sort-maps-by-keys-and-directions
                                  test-data/unordered-people-maps [[:last-name :desc]])))
      "last-name-desc")

  (is (= test-data/people-vectors-gender-asc
         (map people/map->vector (sort-maps-by-keys-and-directions
                                  test-data/unordered-people-maps [[:gender :asc]])))
      "gender-asc")

  (is (= test-data/people-vectors-name-asc
         (map people/map->vector (sort-maps-by-keys-and-directions
                                  test-data/unordered-people-maps [[:first-name :asc] [:last-name :asc]])))
      "name-asc"))
