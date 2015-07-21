(ns leiningen.new.muon-clojure
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files
                                             sanitize sanitize-ns project-name
                                             ]]
            [leiningen.core.main :as main]))

(def render (renderer "muon-clojure"))

(defn wrap-indent [wrap n list]
  (fn []
    (->> list
         (map #(str "\n" (apply str (repeat n " ")) (wrap %)))
         (clojure.string/join ""))))

(defn dep-list [n list]
  (wrap-indent #(str "[" % "]") n list))

(defn indent [n list]
  (wrap-indent identity n list))

(defn capitalize [^String method-name]
  (let [all-but-first (clojure.string/replace
                        method-name #"-(\w)" 
                        #(clojure.string/upper-case (second %1)))
        final-st (apply str (clojure.string/upper-case (first all-but-first))
                        (rest all-but-first))]
    final-st))

(defn muon-clojure
  "FIXME: write documentation"
  [name]
  (let [data {:full-name name
              :name (project-name name)
              :project-goog-module (sanitize (sanitize-ns name))
              :project-ns (sanitize-ns name)
              :sanitized (name-to-path name)
              :capitalized (capitalize (project-name name))}]
    (main/info "Generating fresh 'lein new' muon-clojure project.")
    (->files data
             [".gitignore"  (render "gitignore" )]
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)])))
