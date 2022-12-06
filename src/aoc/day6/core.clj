(ns aoc.day6.core
  (:require [clojure.java.io :as io]))

(def input (slurp (io/resource "day6/actual_input.txt")))

(defn find-start
  [input length]
  (->> (range (- (count input) (dec length)))
    (map (fn [i] (subs input i (+ i length))))
    (map set)
    (into [])
    (reduce-kv (fn [_acc index val] (when (= length (count val)) (reduced index))) nil)
    (+ length)))

(defn solution-part-1
  []
  (println (find-start input 4)))

(defn solution-part-2
  []
  (println (find-start input 14)))