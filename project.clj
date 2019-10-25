(defproject sort-coding-test "0.1.0-SNAPSHOT"
  :description "Coding test for simple sort cli and REST endpoints"
  :url ""
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.cli "0.4.2"]
                 [metosin/compojure-api "1.1.12"]
                 [prismatic/schema "1.1.10"]
                 [ring/ring-core "1.7.1"]
                 [http-kit "2.3.0"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring/ring-mock "0.4.0"]
                 [cheshire "5.8.1"]
                 [metosin/ring-http-response "0.9.1"]]
  :main ^:skip-aot sort-coding-test.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :managed-dependencies [[org.clojure/core.rrb-vector "0.0.13"]
                         [org.flatland/ordered "1.5.7"]])
