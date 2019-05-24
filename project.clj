(defproject sort-coding-test "0.1.0-SNAPSHOT"
  :description "Coding test for simple sort cli and REST endpoints"
  :url ""
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.cli "0.4.2"]
                 [prismatic/schema "1.1.10"]
                 [org.clojure/data.csv "0.1.4"]]
  :main ^:skip-aot sort-coding-test.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
