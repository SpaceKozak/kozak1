(ns mania.core
  (require [clj-webdriver.taxi :as t]
           [mania.duplicates :as d]
           [mania.admin :as a]
           [clojure.pprint :refer [pprint]]
           [clojure.tools.cli :as cli]
           [clojure.string :as s])
  (:gen-class))


(def cli-options
  [["-f" "--notariuses-edn-file c:/path/to/dudes.edn"
    "File with the to be imported notariuses in EDN format (usually is output of the mania-xl.jar)."
    :default "./notarius-candidates.edn"
    :parse-fn #(read-string (slurp %))]])


(defn usage [options-summary]
  (->> ["This is my program. There are many like it, but this one is mine."
        ""
        "Usage: program-name [options] action"
        ""
        "Options:"
        options-summary
        ""
        "Actions:"
        "  duplicates"
        "  add-notariuses"]
       (s/join \newline)))

(defn- exit [status msg]
  (println msg)
  (System/exit status))


(defn- start-browser []
  (System/setProperty "webdriver.chrome.driver" "/Users/mikhailakovantsev/Desktop/chromedriver")
  (t/set-driver! {:browser :chrome}))

(defn- duplicates! []
  (start-browser)
  (a/login )
  (let [notariuses (d/get-all-notariuses)
        duplicates (d/find-duplicates notariuses)]
    (if (empty? duplicates)
      (println "No duplicates found.")
      (pprint duplicates)))
  (t/close)
  (exit 0 ""))


(defn- create-notariuses! [options]
  (start-browser)
  (a/login )
  (let [candidates (read-string (slurp (:notariuses-edn-file options)))
        existing   (d/get-all-notariuses)
        all (concat candidates existing)
        duplicates (d/find-duplicates all)]
    (pprint duplicates))
  (t/close)
  (exit 0 ""))


(defn -main [& args]
  (pprint args)
  (let [{:keys [options arguments errors summary]} (cli/parse-opts args cli-options)]
    (pprint options)
    (pprint arguments)
    (case (first arguments)
      "duplicates" (duplicates!)
      "add-notariuses" (create-notariuses! options)
      (exit 1 (usage summary)))))

;(defn -main
;  [& args]
;  (when-let [xl-path (first args)
;             people  (->> (xl->rowmaps xl-path) (map set-region))]
;    (System/setProperty "webdriver.chrome.driver" "/Users/mikhailakovantsev/Desktop/chromedriver")
;    (t/set-driver! {:browser :htmlunit} "http://v1.site.lawyers.newdev.dev.studio7.com.ua/admin/login")
;    (login "pavlooprya@gmail.com" "SDdf92026!djkl")
;    ;(map add-notarius people)
;    (t/close)))

;(-main "/Users/mikhailakovantsev/Desktop/nota.xls")
;(-main "f/Users/mikhailakovantsev/Desktop/notarius-candidates.edn" "add-notariuses")
;(-main "duplicates")