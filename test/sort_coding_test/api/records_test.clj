(ns sort-coding-test.api.records-test
  (:require [clojure.test :refer :all]
            [sort-coding-test.api.records :refer :all]
            [ring.mock.request :as mock]
            [schema.core :as s]))

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

(deftest person-map->display-person-map-test
  (s/with-fn-validation
    (is (= test-output-person-map
           (person-map->display-person-map test-person-map)))))

;; (deftest server-test
;;   (is (= (app (mock/request :get "/records/gender?x=2&y=3"))
;;          {:status 200
;;           :body "Expected body"})))

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
