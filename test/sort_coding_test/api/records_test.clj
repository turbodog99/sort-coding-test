(ns sort-coding-test.api.records-test
  (:require [clojure.test :refer :all]
            [sort-coding-test.api.records :refer :all]
            [ring.mock.request :as mock]
            [schema.core :as s]
            [cheshire.core :as cheshire]
            [sort-coding-test.test-data :as test-data]
            [sort-coding-test.data-types.people :as people-data]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(def test-person-map
  {:last-name "test_last_name"
   :first-name "test_first_name"
   :gender :m
   :favorite-color "test_favorite_color"
   :date-of-birth (java.util.Date. "9/14/1975")})

(def test-output-person-map
  {:last-name "test_last_name"
   :first-name "test_first_name"
   :gender "M"
   :favorite-color "test_favorite_color"
   :date-of-birth "9/14/1975"})

(defn string->gender
  [gender]
  (cond (= "M" gender)
        :m
        (= "F" gender)
        :f
        :else
        nil))

(deftest string->gender-test
  (is (= :m (string->gender "M")))
  (is (= :f (string->gender "F")))
  (is (= nil (string->gender "sdfsdfsddsfsd"))))

(defn formatted-date->Date
  [formatted-date]
  (try
    (java.util.Date. formatted-date)
    (catch Exception e "")))

(s/defn display-person-map->person-map :- people-data/Person
  "Returns a person map that conforms to the requested JSON"
  [output-person-map :- OutputPerson]
  {:last-name (:last-name output-person-map)
   :first-name (:first-name output-person-map)
   :gender (string->gender (:gender output-person-map))
   :favorite-color (:favorite-color output-person-map)
   :date-of-birth (formatted-date->Date (:date-of-birth output-person-map))})

(deftest display-person-map->person-map-test
  (s/with-fn-validation
    (is (= test-person-map
           (display-person-map->person-map test-output-person-map)))))

(deftest person-map->display-person-map-test
  (s/with-fn-validation
    (is (= test-output-person-map
           (person-map->display-person-map test-person-map)))))

  (deftest server-test
    ;; (testing "Test GET request to /hello?name={a-name} returns expected response"
    ;; (let [response (app (-> (mock/request :get  "/api/plus?x=1&y=2")))
    ;;       body     (parse-body (:body response))]
    ;;   (is (= (:status response) 200))
    ;;   (is (= (:result body) 3)))))

    (with-redefs [get-people (constantly test-data/unordered-people-maps)]
      (testing "Test GET request to /records/gender returns gender ascending records"
        (let [response (records-api-handler (mock/request :get "/records/gender"))
              body (parse-body (:body response))]
          (is (= 200 (:status response)))
          (is (= (map people-data/vector->map test-data/people-vectors-gender-asc)
                 (map display-person-map->person-map body)))))

      (testing "Test GET request to /records/birthdate returns birthdate ascending records"
        (let [response (records-api-handler (mock/request :get "/records/birthdate"))
              body (parse-body (:body response))]
          (is (= 200 (:status response)))
          (is (= (map people-data/vector->map test-data/people-vectors-date-of-birth-asc)
                 (map display-person-map->person-map body)))))

      (testing "Test GET request to /records/name returns name ascending records"
        (let [response (records-api-handler (mock/request :get "/records/name"))
              body (parse-body (:body response))]
          (is (= 200 (:status response)))
          (is (= (map people-data/vector->map test-data/people-vectors-name-asc)
                 (map display-person-map->person-map body)))))))


;; (deftest your-handler-test
;;   (is (= (your-handler (mock/request :get "/doc/10"))
;;          {:status  200
;;           :headers {"content-type" "text/plain"}
;;           :body    "Your expected result"})))

;; (deftest your-json-handler-test
;;   (is (= (your-handler (-> (mock/request :post "/api/endpoint")
;;                            (mock/json-body {:foo "bar"})))
;;          {:status  201
;;           :headers {"content-type" "application/json"}
;;           :body    {:key "your expected result"}})))
