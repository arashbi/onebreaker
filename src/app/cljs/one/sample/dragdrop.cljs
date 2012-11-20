(ns one.sample.dragdrop
  (:require [goog.fx.DragDrop :as dd]
            [goog.events :as events]
            [clojure.browser.dom :as dom]
            [cljs.core :as core]))
  
(defn make-draggable [id {:keys target id &listeners} ]
                (let [drag  (goog.fx.DragDrop. (core/name id) "")
                      drop  (goog.fx.DragDrop. (core/name target) "")]
                  (.addTarget drag drop)
                  (.init drag)
                  (.init drop)
                 ;(events/listen drag "dragstart" #(dom/log "salam"))
                  ))
(make-draggable "blue" {:target "drop-target"})
(events/listen (dom/get-element :yellow) "click" #(dom/log "clicked"))