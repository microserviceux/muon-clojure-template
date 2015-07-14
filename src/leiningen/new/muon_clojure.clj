(ns leiningen.new.muon-clojure
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "muon-clojure"))

(defn muon-clojure
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' muon-clojure project.")
    (->files data
             ["src/{{sanitized}}/foo.clj" (render "foo.clj" data)])))
