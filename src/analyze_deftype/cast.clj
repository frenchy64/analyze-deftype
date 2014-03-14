(ns analyze-deftype.cast
  (:require [analyze-deftype.util :as u]))

(deftype Foo [normal])
(assert (cast Foo (->Foo 1)))
