(ns sort-coding-test.test-data)

;; NOTE: unordered means they're not sorted; not that they can be moved around without
;; breaking tests.
(def unordered-people-vectors
  "A group of people specifically not in any desired order. These are in a format that
  makes them easy to write to the input file format for the test file generator."
  [
   ["Herman" "Mark" "M" "blue" "9/14/1975"]
   ["Burns" "Montgomery" "M" "red" "12/12/1881"]
   ["Simpson" "Homer" "M" "yellow" "5/12/1956"]
   ["Simpson" "Marge" "F" "blue" "3/19/1951"]
   ["Simpson" "Bart" "M" "orange" "2/3/1983"]
   ["Simpson" "Lisa" "F" "green" "5/9/1982"]
   ["Cartman" "Eric" "M" "brown" "11/13/1987"]
   ]
  )

;; NOTE: unordered means they're not sorted; not that they can be moved around without
;; breaking tests.
(def unordered-people-maps
  "Unordered people strings converted to maps with proper data types"
  [{:last-name "Herman",
  :first-name "Mark",
  :gender :m,
  :favorite-color "blue",
  :date-of-birth #inst "1975-09-14T05:00:00.000-00:00"}
 {:last-name "Burns",
  :first-name "Montgomery",
  :gender :m,
  :favorite-color "red",
  :date-of-birth #inst "1881-12-12T06:00:00.000-00:00"}
 {:last-name "Simpson",
  :first-name "Homer",
  :gender :m,
  :favorite-color "yellow",
  :date-of-birth #inst "1956-05-12T05:00:00.000-00:00"}
 {:last-name "Simpson",
  :first-name "Marge",
  :gender :f,
  :favorite-color "blue",
  :date-of-birth #inst "1951-03-19T06:00:00.000-00:00"}
 {:last-name "Simpson",
  :first-name "Bart",
  :gender :m,
  :favorite-color "orange",
  :date-of-birth #inst "1983-02-03T06:00:00.000-00:00"}
 {:last-name "Simpson",
  :first-name "Lisa",
  :gender :f,
  :favorite-color "green",
  :date-of-birth #inst "1982-05-09T05:00:00.000-00:00"}
 {:last-name "Cartman",
  :first-name "Eric",
  :gender :m,
  :favorite-color "brown",
  :date-of-birth #inst "1987-11-13T06:00:00.000-00:00"}])

(def people-vectors-gender-asc-last-name-asc
  [["Burns" "Montgomery" "M" "red" "12/12/1881"]
   ["Cartman" "Eric" "M" "brown" "11/13/1987"]
   ["Herman" "Mark" "M" "blue" "9/14/1975"]
   ["Simpson" "Marge" "F" "blue" "3/19/1951"]
   ["Simpson" "Lisa" "F" "green" "5/9/1982"]
   ["Simpson" "Homer" "M" "yellow" "5/12/1956"]
   ["Simpson" "Bart" "M" "orange" "2/3/1983"]])

(def people-vectors-date-of-birth-asc
  [["Burns" "Montgomery" "M" "red" "12/12/1881"]
   ["Simpson" "Marge" "F" "blue" "3/19/1951"]
   ["Simpson" "Homer" "M" "yellow" "5/12/1956"]
   ["Herman" "Mark" "M" "blue" "9/14/1975"]
   ["Simpson" "Lisa" "F" "green" "5/9/1982"]
   ["Simpson" "Bart" "M" "orange" "2/3/1983"]
   ["Cartman" "Eric" "M" "brown" "11/13/1987"]])

(def people-vectors-last-name-desc
  [["Simpson" "Homer" "M" "yellow" "5/12/1956"]
   ["Simpson" "Marge" "F" "blue" "3/19/1951"]
   ["Simpson" "Bart" "M" "orange" "2/3/1983"]
   ["Simpson" "Lisa" "F" "green" "5/9/1982"]
   ["Herman" "Mark" "M" "blue" "9/14/1975"]
   ["Cartman" "Eric" "M" "brown" "11/13/1987"]
   ["Burns" "Montgomery" "M" "red" "12/12/1881"]])

(def people-vectors-gender-asc
  [["Simpson" "Marge" "F" "blue" "3/19/1951"]
   ["Simpson" "Lisa" "F" "green" "5/9/1982"]
   ["Herman" "Mark" "M" "blue" "9/14/1975"]
   ["Burns" "Montgomery" "M" "red" "12/12/1881"]
   ["Simpson" "Homer" "M" "yellow" "5/12/1956"]
   ["Simpson" "Bart" "M" "orange" "2/3/1983"]
   ["Cartman" "Eric" "M" "brown" "11/13/1987"]])

(def people-vectors-name-asc
  [["Burns" "Montgomery" "M" "red" "12/12/1881"]
   ["Cartman" "Eric" "M" "brown" "11/13/1987"]
   ["Herman" "Mark" "M" "blue" "9/14/1975"]
   ["Simpson" "Bart" "M" "orange" "2/3/1983"]
   ["Simpson" "Homer" "M" "yellow" "5/12/1956"]
   ["Simpson" "Lisa" "F" "green" "5/9/1982"]
   ["Simpson" "Marge" "F" "blue" "3/19/1951"]])
