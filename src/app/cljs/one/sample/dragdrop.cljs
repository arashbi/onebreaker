(ns one.sample.dragdrop
  (:require [goog.fx.DragDrop :as dd]
            [goog.events :as events]
            [clojure.browser.dom :as dom]
            [cljs.core :as core]
            [goog.dom :as dom]))
(def draggables [:green :yellow :blue :red])
(defn make-draggable [id target & events]
  (let [drag  (goog.fx.DragDrop. (core/name id) "")
        drop  (goog.fx.DragDrop. (core/name target) "")]
    (.addTarget drag drop)
    (.init drag)
    (.init drop)
    (map #(events/listen drag (core/name (first %)) (second %))
         (first events))))
(defn make-draggable [id target]
  (let [drag (goog.fx.DragDrop. (core/name id))]
    (.addTarget drag target)
    (.setSourceClass drag "source")
    (.init drag)
    drag))
(defn initialize-view []
  (dom/log "creating draggables")
  (def drop-target (goog.fx.DragDrop. "drop-target"))
  (def green (make-draggable :green drop-target))
  (def yellow (make-draggable :yellow drop-target))
  (def red (make-draggable "red" drop-target))
  (def blue (make-draggable :blue drop-target))
  (events/listen drop-target "drop" #(dom/log "Bang"))
  (.init drop-target))
(defn prepare-pass [number]
  (let [targets (take 4 (drop (* 4 (- number 1)) (dom/getElementsByClass "place-div")))
       target-objects (map #(goog.fx.DragDrop. %1) targets)]
    (map #(make-draggable %1 %2) draggables target-objects)
    (map (register-listener % "drop" #(dom/log "sss")) target-objects)
    ))
(defn- register-listener
  [target event action]
  (events/listen target event action))
(comment 
  (make-draggable :blue :drop-target {"dragstart" #(dom/log "dragged1")})
  (events/listen (dom/get-element :blue) "click" #(dom/log "clicked"))
  (events/listen (dom/get-element :yellow) "dragstart" #(dom/log "dragge1111d"))
  (initialize-view)
  (defn f[drag target & events]
    (map #(print %1 "\n") (first events)))
  
  (map #(let [[name age] %1]  (print  "name" name  age "\n")) {:arash :40 :ooldooz 28})
  (f "bbbb" :aaa {"a" "b" "v" "d"})
  )