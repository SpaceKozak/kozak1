(defproject mania "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-webdriver "0.6.1"]
                 [com.cemerick/url "0.1.1"]
                 [org.clojure/tools.cli "0.3.1"]]
  :main mania.core
  :aot [mania.core]
  :auto-clean true
  :uberjar-name "uber-mania-admin.jar")
