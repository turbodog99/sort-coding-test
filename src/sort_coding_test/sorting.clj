(ns sort-coding-test.sorting)

(def compare-desc (comp - compare))

(def comparators {
                  :asc compare
                  :desc compare-desc
                  })

(defn sort-by-key-and-direction
  [input-maps [key direction]] (sort-by #(get % key) (get comparators direction) input-maps))

(defn sort-maps-by-keys-and-directions
  "Given a collection of maps and a collection of vector pairs in the form
  [:first-level-key-name :asc|:desc], sorts the maps using the given
  first-level-key-names and directions. This only sorts on first-level keys.
  The sort happens in the order specified."
  [key-direction-pairs input-maps]
  (reduce sort-by-key-and-direction input-maps key-direction-pairs))

;; Some convenience partials
(defn sort-maps-by-gender-asc-last-name-asc
  [input-maps]
  (partial sort-maps-by-keys-and-directions [[:gender :asc] [:last-name :asc]]))
