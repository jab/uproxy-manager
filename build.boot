; based on https://github.com/onetom/hoplon-layouts/blob/master/build.boot

(set-env!
  :dependencies
  '[
    [org.clojure/clojure           "1.8.0"         :scope "provided"]
    [org.clojure/clojurescript     "1.9.293"       :scope "provided"]

    [boot/core                     "2.6.0"         :scope "provided"]
   ;[onetom/boot-lein-generate     "0.1.3"         :scope "test"]
    [adzerk/boot-cljs              "1.7.228-2"     :scope "compile"]
    [adzerk/boot-reload            "0.5.1"         :scope "compile"]
    [tailrecursion/boot-static     "0.1.0"]

   ;[binaryage/devtools            "0.8.2"         :scope "test"]
   ;[binaryage/dirac               "0.7.2"         :scope "test"]
   ;[powerlaces/boot-cljs-devtools "0.1.2"         :scope "test"]

    [hoplon                        "6.0.0-alpha17" :scope "compile"]

    [cljsjs/material               "1.3.0-0"]
    [cljsjs/dialog-polyfill        "0.4.3-0"]]
   
  :target-path "target"
  :source-paths #{"src"}
  :asset-paths  #{"assets"})

;(require '[boot.lein])
;(boot.lein/generate)

(require
  '[adzerk.boot-reload :refer [reload]]
  '[adzerk.boot-cljs :refer [cljs]]
  '[hoplon.boot-hoplon :refer [hoplon prerender]]
  '[tailrecursion.boot-static :refer [serve]])
 ;'[powerlaces.boot-cljs-devtools :refer [cljs-devtools]]




(deftask from-jars
  "Import files from jars (e.g. CLJSJS) and move them to the desired location in the fileset."
  [i imports IMPORT #{[sym str str]} "Tuples describing imports: [jar-symbol path-in-jar target-path]"]
  (let [add-jar-args (into {} (for [[j p]   imports] [j (re-pattern (str "^" p "$"))]))
        move-args    (into {} (for [[_ p t] imports] [(re-pattern (str "^" p "$")) t]))]
    (sift :add-jar add-jar-args :move move-args)))

(task-options!
  from-jars {:imports #{['cljsjs/material
                         "cljsjs/material/production/material.min.inc.css"
                         "material.min.css"]}})



#_(deftask sync-translations
   "TODO. Use https://github.com/DeLaGuardo/boot-gettext?"
   [])
  


(deftask dev
  "Build for local development."
  []
  (comp
    (watch)
    (speak)
   #_(checkout :dependencies '[[hoplon "6.0.0-alpha17"]])
    (hoplon)
    (reload :on-jsload 'uproxy-manager.index/reload)
   #_(cljs-devtools)
    (cljs :optimizations :none
          :compiler-options {:parallel-build true
                             :external-config {:devtools/config {:features-to-install [:formatters :hints :async]
                                                                 :dont-detect-custom-formatters true}}})
    (from-jars)
    (target)
    (serve :port 3000)))

(deftask prod
  "Build for production deployment."
  []
  (comp
    (hoplon)
    (cljs :optimizations :advanced)
    (prerender)
    (target)))

#_(deftask stage
   "TODO"
   [])
