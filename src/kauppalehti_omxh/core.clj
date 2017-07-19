(ns kauppalehti-omxh.core
  (:require [net.cgrand.enlive-html :as html]))

(defn parse-number
  [s]
  (if (re-find #"^-?\d+\.?\d*$" s)
    (read-string s)))

;; (defn get-dom
;;   []
;;   (html/html-snippet

(defn parse-value
  [stock, selector]
  (html/text (first (html/select stock [selector]))))

(defn parse-stock
    [stock]
    { :name (parse-value stock :span.stock-item-name)
      :volume (parse-value stock :span.stock-item-volume)
      :change (parse-value stock :span.stock-item-change)
      :value (parse-number (parse-value stock :span.stock-item-value))})

(defn parse-stocks
    [dom]
    (map (fn [stock-dom] (parse-stock stock-dom))
         (html/select dom [:div.stock-item])))

(defn stocks
  [html]
  (parse-stocks (html/html-snippet html)))

(defn -main
  [& args]
;;  (let [ dom (:body @(http/get "http://app.kauppalehti.fi/market/stockexchange"
;;                               {:insecure? true}))]
  (let [dom (slurp "kauppalehti.htm")]
    (println (stocks dom))))
