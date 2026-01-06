(ns counter.app
  (:require
    [reagent.core :as r]
    [reagent.dom.client :as dom]))

(defonce counters (r/atom []))

(defn vec-delete
  [v i]
  (into (subvec v 0 i) (subvec v (inc i))))

(defn Counter
  [i counter]
  [:div
   counter
   " "
   [:button
    {:onClick (fn [] (swap! counters update i inc))}
    "+1"]
   " "
   [:button
    {:onClick (fn [] (swap! counters update i dec))}
    "-1"]
   [:button
    {:onClick (fn [] (swap! counters vec-delete i))}
    "X"]])

(defn Application []
  [:div
   [:h1 "Counter"]
   (doall
     (for [[i counter] (map vector (range) @counters)]
       ^{:key (str i)} [Counter i counter]))
   [:button {:onClick (fn [] (swap! counters conj 0))} "Add counter"]])

(defonce root
  (dom/create-root (.getElementById js/document "app")))

(defn render []
  (.render root (r/as-element [Application])))

(defn ^:dev/after-load reload []
  (render))

(defn init []
  (render))
