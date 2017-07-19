(defproject kauppalehti-omxh "0.1.1"
  :dependencies [[org.clojure/clojure "1.8.0"],
                 [enlive "1.1.6"]]
  :main kauppalehti-omxh.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
