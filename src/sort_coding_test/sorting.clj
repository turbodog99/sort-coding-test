(ns sort-coding-test.sorting)

(def compare-desc (comp - compare))

(def comparators {
                  :asc compare
                  :desc compare-desc
                  })

(defn sort-by-key-and-direction
  [input-maps [key direction]] (sort-by #(get % key) (get comparators direction) input-maps))

;; Example usage:
;;   (sort-maps-by-keys-and-directions input-maps [[:gender :asc] [:last-name :asc]]))
;;   (sort-maps-by-keys-and-directions input-maps [[:date-of-birth :asc]]))
(defn sort-maps-by-keys-and-directions
  "Given a collection of maps and a collection of vector pairs in the form
  [:first-level-key-name :asc|:desc], sorts the maps using the given
  first-level-key-names and directions. This only sorts on first-level keys.
  The sort happens in the order specified."
  [input-maps key-direction-pairs]
  (reduce sort-by-key-and-direction input-maps key-direction-pairs))
