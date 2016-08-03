(ns kauppalehti-omxh.core
  (:require [net.cgrand.enlive-html :as html]
    [org.httpkit.client :as http]))

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

(defn get-dom
  []
  (html/html-snippet
    (:body @(http/get "http://app.kauppalehti.fi/market/stockexchange"
                      {:insecure? true}))))

(defn extract-value
  [stock, selector]
  (:content (first (html/select stock [selector]))))

(defn extract-stock
    [date, stock]
    { :name (extract-value stock :span.stock-item-name)
      :date date
      :value (parse-int (str (extract-value stock :span.stock-item-value))) })

(defn extract-date
    [dom]
    (:content (last (html/select dom [:span.graph-time, :span]))))

(defn extract-stocks
    [dom]
    (let [date (extract-date dom)]
    (map (fn [x] (extract-stock date x)) (html/select dom [:div.stock-item]))))

(defn get-stocks
  []
  (extract-stocks (get-dom)))

(defn -main
  [& args]
  (let [stocks (get-stocks)]
    (println stocks)))
