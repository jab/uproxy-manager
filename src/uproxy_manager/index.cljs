(ns ^{:hoplon/page "index.html"} uproxy-manager.index
  (:require
   [uproxy-manager.logic :as l :refer [_]]
   [javelin.core :as j :refer [cell] :refer-macros [cell= defc defc=]]
   [hoplon.core  :as h :refer [defelem when-tpl if-tpl case-tpl for-tpl loop-tpl text]]
   [cljsjs.material]
   [cljsjs.dialog-polyfill]))  ; Edge, Firefox, and Safari don't support <dialog>: http://caniuse.com/#feat=dialog
    ; If the dialog-polyfill CSS is actually needed, just include it in main.css (https://cdnjs.cloudflare.com/ajax/libs/dialog-polyfill/0.4.7/dialog-polyfill.min.css")
   


(defn reload []
  ; Reinitialize mdl components on hot reload
  (js/componentHandler.upgradeAllRegistered))


;; custom elements

(defelem icon [attributes children]
  (h/i :class "material-icons" children))


(defelem lang-chooser [attributes children]
  (let [button-id "lang-button"
        button-classes "mdl-color-text--primary-contrast mdl-button mdl-js-button mdl-js-ripple-effect"
        ul-classes "mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect"]
    (h/div
      (h/button :id button-id :class button-classes
        (text "~{l/current-lang-name}")
        (icon "arrow_drop_down"))
      (h/ul :data-mdl-for button-id :class ul-classes
        (loop-tpl :bindings [[lang {:keys [lang-names]}] l/lang-names]
          (h/li :class "mdl-menu__item"
            (h/a :class "change-lang" :click #(l/set-lang! @lang) (text (get l/lang-names lang lang)))))))))


(defelem page-header [attributes children]
  (h/header :class "mdl-layout__header"
    (h/div :class "mdl-layout__header-row"
      (h/span :class "mdl-layout-title"
        (h/img :src "img/uproxy-alpha-logo.svg" :alt "uProxy alpha logo"))
      (h/div :class "mdl-layout-spacer")
      (lang-chooser))))


(defelem server-menu [attributes children]
  (let [button-id "server-menu-button"
        button-classes "mdl-button--icon mdl-button mdl-js-button mdl-js-ripple-effect"
        ul-classes "mdl-menu mdl-menu--bottom-right mdl-js-menu"]
    (h/div
      (h/button :id button-id :class button-classes
        (icon "more_vert"))
      (h/ul :data-mdl-for button-id :class ul-classes
        (h/li :class "mdl-menu__item"
          (h/a :click #(l/delete-server "pass-server-id-here")
            (icon "delete")
            (_ "Delete server")))))))


(defelem user-table-header [attributes children]
  (h/thead
   (h/tr
    (h/th :class "server-header" :colspan "4"
      (h/h4
        (h/img :class "avatar" :src "img/uproxy-avatar.svg" :alt "uProxy server avatar")
        (_ "uProxy Server"))
      (h/p
        (_ "Tip: To share access with more people, we recommend you create individual Access URLs. That way, you can easily revoke someone’s access if necessary."))
      (server-menu)))))


(defelem user-table [attributes children]
  (h/table :class "mdl-data-table mdl-js-data-table mdl-shadow--2dp"
    (user-table-header)))


(h/html
  (h/head
    (h/html-meta :charset "UTF-8")
    (h/html-meta :name "viewport" :content "width=device-width, initial-scale=1.0")
    (h/title (_ "uProxy Manager"))
    (h/link :rel "icon" :href "img/favicon.png")
    ; Not available via npm or other CDNs (e.g. https://github.com/cdnjs/cdnjs/issues/7845):
    (h/link :rel "stylesheet" :href "https://fonts.googleapis.com/icon?family=Material+Icons")
    (h/link :rel "stylesheet" :href "material.min.css")
    (h/link :rel "stylesheet" :href "main.css"))
  (h/body
    (h/div :class "mdl-layout mdl-js-layout mdl-layout--fixed-header"
      (page-header)
      (h/main :class "mdl-layout__content"
        (user-table))
      #_(h/p (text "current-lang: ~{l/current-lang}"))
      #_(h/p (text "current-msgs: ~{l/current-msgs}"))
      #_(h/p (text "msgs-by-lang: ~{l/msgs-by-lang}")))))

;(script :src "https://unpkg.com/clipboard-js@0.3.3/clipboard.min.js")
