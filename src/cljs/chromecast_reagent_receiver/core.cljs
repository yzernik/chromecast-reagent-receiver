(ns chromecast-reagent-receiver.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]))

;; -------------------------
;; Media player
(defn blank-video-component []
  [:video {:id :media}])

(defn hook-up-media! [video]
  (let [mediaManager (js/cast.receiver.MediaManager. video)
        receiver-manager (.getInstance js/cast.receiver.CastReceiverManager)]
    (js/console.log "hooking up media element...")
    (js/console.log video)
    (.start receiver-manager)))

(def video-component
  (with-meta blank-video-component
    {:component-did-mount
     #(hook-up-media! (reagent/dom-node %))}))


;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to chromecast-reagent-receiver"]
   [:div [:a {:href "/about"} "go to about page!!!"]]
   [:div [video-component]]])

(defn about-page []
  [:div [:h2 "About chromecast-reagent-receiver"]
   [:div [:a {:href "/"} "go to the home page"]]])

(defn current-page []
  [:div [(session/get :current-page)]])

;; -------------------------
;; Routes

(secretary/defroute "/" []
  (session/put! :current-page #'home-page))

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!)
  (accountant/dispatch-current!)
  (mount-root))
