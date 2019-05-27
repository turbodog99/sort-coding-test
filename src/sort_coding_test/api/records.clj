(ns sort-coding-test.api.records
  (:require [clojure.test :refer :all]
            [schema.core :as s]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [sort-coding-test.data-types.people :as people-data]
            [sort-coding-test.shared-data :refer [people]]
            [schema.core :as s]))

(def OutputPerson
  {:last-name s/Str
   :first-name s/Str
   :gender s/Str
   :favorite-color s/Str
   :date-of-birth s/Str})

(defn get-people []
  @people)

(s/defn person-map->display-person-map :- OutputPerson
  "Returns a person map that conforms to the requested JSON"
  [person-map :- people-data/Person]
  {:last-name (:last-name person-map)
   :first-name (:first-name person-map)
   :gender (people-data/gender->string (:gender person-map))
   :favorite-color (:favorite-color person-map)
   :date-of-birth (people-data/date->formatted-string (:date-of-birth person-map))})

(def records-api-handler
 (api
  (context "/records" []
           (GET "/gender" []
                :return [OutputPerson]
                (ok
                 (map person-map->display-person-map
                      (people-data/sort-by-gender-asc (get-people)))))

           (GET "/birthdate" []
                :return [OutputPerson]
                (ok
                 (map person-map->display-person-map
                      (people-data/sort-by-date-of-birth-asc (get-people)))))

           (GET "/name" []
                :return [OutputPerson]
                (ok
                 (map person-map->display-person-map
                      (people-data/sort-by-name-asc (get-people)))))
           ;; (POST "/echo" []
           ;;       :return Pizza
           ;;       :body [pizza Pizza]
           ;;       :summary "echoes a Pizza"
           ;;       (ok pizza))
           )))
