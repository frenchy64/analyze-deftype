(ns analyze-deftype.util
  (:require [clojure.tools.analyzer :as ta]
            [clojure.tools.analyzer.jvm :as taj]
            [clojure.tools.analyzer.passes.jvm.emit-form :as emit-form]
            [clojure.tools.reader :as tr]
            [clojure.tools.reader.reader-types :as readers]
            [clojure.string :as str]
            [clojure.java.io :as io]))

(defn ^:private analyze1 [form env]
  (let [a (taj/analyze form env)
        frm (emit-form/emit-form a)]
    (eval frm)
    a))

(defn analyze-ns [nsym]
  (let [nsym (ns-name nsym)
        res (munge nsym)
        p    (str (str/replace res #"\." "/") ".clj")
        eof  (reify)
        p (if (.startsWith p "/") (subs p 1) p)
        pres (io/resource p)
        _ (assert pres (str "Cannot find file for " nsym ": " p))
        file (-> pres io/reader slurp)
        reader (readers/indexing-push-back-reader file)
        asts (binding [*ns* (or (find-ns nsym)
                                *ns*)]
               (loop [asts []]
                 (let [form (tr/read reader false eof)]
                   (if (not= eof form)
                     (let [a (analyze1 form (taj/empty-env))]
                       (recur (conj asts a)))
                     asts))))]
    asts))

