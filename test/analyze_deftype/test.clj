(ns analyze-deftype.test
  (:use clojure.test)
  (:require [analyze-deftype.util :as u]
            [analyze-deftype.cast]
            [analyze-deftype.extend]))

(deftest cast-test
  (is (do 
        (compile 'analyze-deftype.cast)
        (require 'analyze-deftype.cast :reload)
        true))
  (is (do
        (compile 'analyze-deftype.cast)
        (u/analyze-ns 'analyze-deftype.cast))))

(deftest extend-test
  (is (u/analyze-ns 'analyze-deftype.extend)))
