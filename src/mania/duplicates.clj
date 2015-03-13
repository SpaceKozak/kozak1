(ns mania.duplicates
  (require [clj-webdriver.taxi :as t]
           [cemerick.url :as u]
           [mania.config :refer [NOTARIUS-LIST-URL]])
  (:gen-class))


(defn- get-last-page-number [list-page-url]
  (t/to list-page-url)
  (let [last-btn (t/element "#index_footer span.last a")
        href (t/attribute last-btn :href)
        query (:query (u/url href))]
    (read-string (query "page"))))


(defn- get-column-values [column-number]
  (let [css (str "#index_table_notarius_items tbody tr td:nth-child(" column-number ")")]
    (map t/text (t/elements css))))


(defn- url->notariuses [url]
  (t/to url)
  (let [names     (get-column-values 1)
        phones    (get-column-values 5)
        addresses (get-column-values 6)
        urls      (map #(t/attribute % :href) (t/elements "a.edit_link"))
        zipped    (map vector names phones addresses urls)]
    (map #(zipmap [:name :phone :address :url] %) zipped)))


(defn- set-page-number [href page#]
  (let [url (u/url href)]
    (str (assoc url :query {"page" page#}))))


(defn get-all-notariuses []
  (let [last-page-number (get-last-page-number NOTARIUS-LIST-URL)
        page-numbers (range last-page-number 0 -1)
        ;page-numbers (range last-page-number 24 -1)
        urls (map #(set-page-number NOTARIUS-LIST-URL %) page-numbers)]
    (loop [dudes []
           urls  urls]
      (println (first urls))
      (println (count dudes))
      (if (seq urls)
        (recur (concat dudes (url->notariuses (first urls))) (rest urls))
        dudes))
    ;(concat (map url->notariuses urls))
    ))


(defn find-duplicates
  ([notariuses]
    (find-duplicates notariuses :name))
  ([notariuses key]
    (let [g (group-by key notariuses)]
      (filter #(< 1 (count %)) (vals g)))))