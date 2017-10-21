(ns kauppalehti-omxh.core
  (:require [net.cgrand.enlive-html :as html]))

(defn- parse-number [s]
  (if (re-find #"^-?\d+\.?\d*$" s)
    (read-string s)))

(defn- parse-value [stock, selector]
  (html/text (first (html/select stock [selector]))))

(defn- parse-stock [stock]
    { :name (parse-value stock :span.stock-item-name)
      :value (parse-number (parse-value stock :span.stock-item-value))})

(defn- parse-stocks [dom]
    (map (fn [stock-dom] (parse-stock stock-dom))
         (html/select dom [:div.stock-item])))

(defn stocks [html]
  "http://app.kauppalehti.fi/market/stockexchange"
  (parse-stocks (html/html-snippet html)))

(defn -main [& args]
  (println (stocks (slurp "kauppalehti.htm"))))
