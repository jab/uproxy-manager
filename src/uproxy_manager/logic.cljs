(ns uproxy-manager.logic
  (:require
    [javelin.core :as j :refer [cell] :refer-macros [cell= defc defc=]]))


(def default-lang :en)
(def lang-names
  {:en "English"
   :es "Español"})


(defc current-lang default-lang)
(defc msgs-by-lang {})


(defn load-lang! [lang & {:keys [into-dict] :or [into-dict msgs-by-lang]}]
  (if-not (contains? lang-names lang)
   (js/alert (str "Language " lang " unavailable (TODO: handle this)"))
   (case lang
    :es
      (reset!
       msgs-by-lang
       {:es {"uProxy Manager" "Controlador uProxy"
             "uProxy Server" "Servidor uProxy"
             "Delete server" "Eliminar servidor"
             "Tip: To share access with more people, we recommend you create individual Access URLs. That way, you can easily revoke someone’s access if necessary." "Sugerencia: para compartir el acceso con más personas, te recomendamos que crees enlaces de acceso individuales. De esta manera, puede revocar fácilmente el acceso de alguien si es necesario."}})      
    (js/alert (str "Language " lang " unavailable (TODO: handle this)")))))



(defn set-lang! [lang]
  (if-not (or (contains? msgs-by-lang lang) (= lang default-lang))
    (load-lang! lang))
  (reset! current-lang lang))



(defc= current-lang-name (get lang-names current-lang "Language"))
(defc= current-msgs (get msgs-by-lang current-lang {}))



(defn _ [default-text]
  "gettext shim."  ;; TODO: use https://github.com/facundoolano/clojure-gettext
  (cell= (get current-msgs default-text (if (= current-lang default-lang) default-text (str current-lang ": " default-text)))))


(defn delete-server [server-id]
  (js/alert "TODO: delete server"))
