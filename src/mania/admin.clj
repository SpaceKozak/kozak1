(ns mania.admin
  (require [clj-webdriver.taxi :as t]
           [mania.config :refer [LOGIN-URL NOTARIUS-NEW-URL]])
  (:gen-class))


(defn login [email password]
  (t/to LOGIN-URL)
  (t/quick-fill-submit {"#admin_user_email"    email}
                       {"#admin_user_password" password}
                       {"[name='commit']"      t/submit}))


(defn add-notarius [{r :region c :city n :name a :address p :phone} data]
  (t/to NOTARIUS-NEW-URL)
  (t/select-option "#notarius_item_ua_region_id" {:text r})
  (t/select-option "#notarius_item_ua_city_id"   {:text c})
  (t/quick-fill-submit {"#notarius_item_name_ua"    n}
                       {"#notarius_item_address_ua" a}
                       {"#notarius_item_phone"      p}
                       ;{"#notarius_item_active" t/click}
                       {"[name='commit']"        t/click})
  (println "Created: " data))