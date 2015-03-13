(ns mania.config
  (:gen-class))

(def BASE-URL "http://v1.site.lawyers.newdev.dev.studio7.com.ua")
(def LOGIN-URL         (str BASE-URL "/admin/login"))
(def NOTARIUS-LIST-URL (str BASE-URL "/admin/notarius_items"))
(def NOTARIUS-NEW-URL  (str BASE-URL "/admin/notarius_items/new"))
