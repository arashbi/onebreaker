(ns one.sample.dragdrop
  (:require [goog.fx.DragDrop :as dd]
            [goog.events :as events]
            [clojure.browser.dom :as dom]
            [cljs.core :as core]))
  
(defn make-draggable [id target & events]
                (let [drag  (goog.fx.DragDrop. (core/name id) "")
                      drop  (goog.fx.DragDrop. (core/name target) "")]
                 (.addTarget drag drop)
                 (.init drag)
                 (.init drop)
                  (map #(events/listen drag (core/name (first %)) (second %))
                       (first events))
                  ))
(make-draggable :blue :drop-target {"dragstart" #(dom/log "dragged1")})
(events/listen (dom/get-element :blue) "click" #(dom/log "clicked"))
(events/listen (dom/get-element :yellow) "dragstart" #(dom/log "dragge1111d"))

(defn f[drag target & events]
  (map #(print %1 "\n") (first events)))

(map #(let [[name age] %1]  (print  "name" name  age "\n")) {:arash :40 :ooldooz 28})
(f "bbbb" :aaa {"a" "b" "v" "d"})