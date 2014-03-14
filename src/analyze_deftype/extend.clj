(ns analyze-deftype.extend)

(identity
  (defprotocol IFoo
    (bar [b])))

(deftype Bar [])

(extend-protocol IFoo
  Bar
  (bar [b] 'a))

(defn f [a] (bar a))

(f (->Bar))
