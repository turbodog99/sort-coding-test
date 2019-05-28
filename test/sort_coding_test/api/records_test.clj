(ns sort-coding-test.api.records-test
  (:require [clojure.test :refer :all]
            [sort-coding-test.api.records :refer :all]
            [ring.mock.request :as mock]
            [schema.core :as s]
            [cheshire.core :as cheshire]
            [sort-coding-test.test-data :as test-data]
            [sort-coding-test.data-types.people :as people-data]
            [ring.util.http-predicates :as predicates]
            [clojure.string :as string]))

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

(def test-comma-delimited-person
  (string/join ", " (vals test-output-person-map)))

(def test-pipe-delimited-person
  (string/join " | " (vals test-output-person-map)))

(def test-space-delimited-person
  (string/join " " (vals test-output-person-map)))

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

    (testing "GET requests"
      (with-redefs [get-people (constantly (ref test-data/unordered-people-maps))]
        (testing "/records/gender returns gender ascending records"
          (let [response (records-api-handler (mock/request :get "/records/gender"))
                body (parse-body (:body response))]
            (is (predicates/ok? response))
            (is (= (map people-data/vector->map test-data/people-vectors-gender-asc)
                   (map display-person-map->person-map body)))))

        (testing "/records/birthdate returns birthdate ascending records"
          (let [response (records-api-handler (mock/request :get "/records/birthdate"))
                body (parse-body (:body response))]
            (is (predicates/ok? response))
            (is (= (map people-data/vector->map test-data/people-vectors-date-of-birth-asc)
                   (map display-person-map->person-map body)))))

        (testing "/records/name returns name ascending records"
          (let [response (records-api-handler (mock/request :get "/records/name"))
                body (parse-body (:body response))]
            (is (predicates/ok? response))
            (is (= (map people-data/vector->map test-data/people-vectors-name-asc)
                   (map display-person-map->person-map body))))))

      (with-redefs [get-people (constantly (ref []))]
        (testing "/records/gender with no records returns empty collection"
          (let [response (records-api-handler (mock/request :get "/records/gender"))
                body (parse-body (:body response))]
            (is (predicates/ok? response))
            (is (= [] body))))

        (testing "/records/birthdate with no records returns empty collection"
          (let [response (records-api-handler (mock/request :get "/records/birthdate"))
                body (parse-body (:body response))]
            (is (predicates/ok? response))
            (is (= [] body))))

        (testing "/records/name with no records returns empty collection"
          (let [response (records-api-handler (mock/request :get "/records/name"))
                body (parse-body (:body response))]
            (is (predicates/ok? response))
            (is (= [] body))))))

    (testing "POST requests"
      (let [people-mock (ref [])]
        (with-redefs [get-people (constantly people-mock)]
          (testing "/records with no delimiter query param returns bad-request"
            (let [response (records-api-handler (-> (mock/request :post "/records")
                                                    (mock/content-type "text/plain")
                                                    (mock/body "sdfsd")))]
              (is (predicates/bad-request? response))))
          (testing "/records with unsupported delimiter returns bad-request"
            (let [response (records-api-handler (-> (mock/request :post "/records?delimiter=unsupported")
                                                    (mock/content-type "text/plain")
                                                    (mock/body "sdfsd")))]
              (is (predicates/bad-request? response))))
          (testing "/records with no body returns bad-request"
            (let [response (records-api-handler (-> (mock/request :post "/records?delimiter=comma")
                                                    (mock/content-type "text/plain")))]
              (is (predicates/bad-request? response))))))

      ;; Since I'm reusing the same test person on each of these, I'm resetting the people ref
      ;; to be empty because otherwise it might give false passes based on previous runs.
      (let [people-mock (ref [])]
        (with-redefs [get-people (constantly people-mock)]
          (testing "/records with comma delimiter and proper body data creates record"
            (let [response (records-api-handler (-> (mock/request :post "/records?delimiter=comma")
                                                    (mock/content-type "text/plain")
                                                    (mock/body test-comma-delimited-person)))]
              (is (predicates/created? response))
              (is (= test-person-map (first @(get-people))))))))
      (let [people-mock (ref [])]
        (with-redefs [get-people (constantly people-mock)]
          (testing "/records with pipe delimiter and proper body data creates record"
            (let [response (records-api-handler (-> (mock/request :post "/records?delimiter=pipe")
                                                    (mock/content-type "text/plain")
                                                    (mock/body test-pipe-delimited-person)))]
              (is (predicates/created? response))
              (is (= test-person-map (first @(get-people))))))))
      (let [people-mock (ref [])]
        (with-redefs [get-people (constantly people-mock)]
          (testing "/records with space delimiter and proper body data creates record"
            (let [response (records-api-handler (-> (mock/request :post "/records?delimiter=space")
                                                    (mock/content-type "text/plain")
                                                    (mock/body test-space-delimited-person)))]
              (is (predicates/created? response))
              (is (= test-person-map (first @(get-people))))))))
      (testing "Can add multiple values"
        (let [people-mock (ref [])]
          (with-redefs [get-people (constantly people-mock)]
            (testing "/records with space delimiter and proper body data creates record"
              (records-api-handler (-> (mock/request :post "/records?delimiter=space")
                                                      (mock/content-type "text/plain")
                                                      (mock/body test-space-delimited-person)))
              (records-api-handler (-> (mock/request :post "/records?delimiter=space")
                                                      (mock/content-type "text/plain")
                                                      (mock/body test-space-delimited-person)))
              (is (= test-person-map (first @(get-people))))
              (is (= test-person-map (second @(get-people))))))))))
