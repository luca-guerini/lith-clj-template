(ns build
  (:require [clojure.tools.build.api :as b]))

(def lib '{{top/ns}}/{{main/ns}})
(def version "0.1.0-SNAPSHOT")
(def class-dir "target/classes")
(def uber-file (format "target/%s-%s.jar" (name lib) version))

;; Includes Clojure + Paper API — used for AOT compilation only
(def compile-basis (delay (b/create-basis {:project "deps.edn" :aliases [:compile]})))

;; No Clojure, no Paper — produces a thin JAR containing only plugin code
(def uber-basis (delay (b/create-basis {:project "deps.edn"})))

(defn clean [_]
  (b/delete {:path "target"}))

(defn compile-plugin [_]
  (clean nil)
  (b/copy-dir {:src-dirs   ["src" "resources"]
               :target-dir class-dir})
  (b/compile-clj {:basis      @compile-basis
                  :src-dirs   ["src"]
                  :class-dir  class-dir
                  :ns-compile '[{{top/ns}}.{{main/ns}}.core]}))

(defn uber [_]
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis     @uber-basis}))

(defn build [_]
  (compile-plugin nil)
  (uber nil))
