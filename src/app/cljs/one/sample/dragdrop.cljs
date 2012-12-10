(ns one.sample.dragdrop
  (:require [goog.fx.DragDrop :as dd]
            [goog.events :as events]
            [clojure.browser.dom :as dom]
            [cljs.core :as core]
            [goog.dom :as dom]
            [domina :as domina]))
(defn make-draggable [elm]
  (let [drag (goog.fx.DragDrop. (core/name elm) "")]
    (.setSourceClass drag "source")
    drag))
(def target-objects (atom #{}))
(def draggables (map #(make-draggable %) [:green :yellow :blue :red :orange :purple]))
(def  pass (atom 1))

(defn- make-target [o]
  (goog.fx.DragDrop. o ""))

(defn- action [event]
  (.log js/console  (. event -target))
  (.log js/console  (. event -dropTargetItem))
  (.log js/console  (. event -dragSourceItem))
  (domina/set-styles! (. event -dropTargetItem.element)
                      {:background-color (. event -dragSourceItem.element.id)})
  (.log js/console (events/unlisten (. event -target) "drop" action))
  ;(swap! target-objects disj (. event -target))
  ;(if (empty? target-objects)
  ;  (doall
  ;   (score)
  ;   (prepare-pass (swap! pass + 1))))
  )

(defn prepare-pass [number]
  (let [targets (take 4 (drop (* 4 (- number 1))
                              (dom/getElementsByClass "place-div")))]
    (swap!  target-objects (union (map #(make-target %) targets)))
    (doseq [drag draggables target @target-objects]
      (.addTarget drag target))
    (doseq [[x y] (map list draggables @target-objects)]
      (register-listener x y "drop"))
    (doseq [t  (concat draggables @target-objects)]
      (.init t))))

(defn dragend
  []
  (.log js/console "dragend"))

(defn- register-listener
  [drag drop event]
  (print drag drop event)
  (events/listen drag "dragstart" #(.log js/console "dragged"))
  (events/listen drop "drop" action)
  (events/listen drag "dragend" dragend))

(comment
  (prepare-pass 1)
  (def number 1)
  (def targets (take 4 (drop (* 4 (- number 1)) (dom/getElementsByClass "place-div"))))
  (def target-objects (map #(make-target %) targets))
  (for [drag draggables target target-objects]
    (.addTarget drag target))
  (map #(.init %)  draggables)
  (map #(.init %) target-objects)
  (def drag (second draggables))
  (def t (make-target  (first (dom/getElementsByClass "place-div"))))
  (.addTarget drag t)
  (.init drag)
  (.init t)
  (.log js/console "sss")
  (events/unlisten (second draggables) "dragend" dragend)
  )