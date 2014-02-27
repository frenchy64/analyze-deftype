(ns analyze-deftype.core
  (:require [analyze-deftype.util :as u]))

(deftype FooDT3 [])
(assert (cast FooDT3 (->FooDT3)))

;(u/analyze-ns *ns*)
