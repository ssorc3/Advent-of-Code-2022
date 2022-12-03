(ns aoc.day2.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "day2/actual_input.txt")))

(defn parse-pair
  [pair]
  (let [f (case (first pair)
            "A" :rock
            "B" :paper
            "C" :scissors)
        s (case (second pair)
            "X" :rock
            "Y" :paper
            "Z" :scissors)]
    [f s]))

(defn calculate-score
  [round]
  (let [object-score (case (second round)
                       :rock 1
                       :paper 2
                       :scissors 3)
        round-score (case round
                      ([:rock :paper] [:paper :scissors] [:scissors :rock])
                      6
                      ([:rock :rock] [:paper :paper] [:scissors :scissors])
                      3
                      ([:rock :scissors] [:paper :rock] [:scissors :paper])
                      0)]
    (+ object-score round-score)))

(defn parse-input
  [f input]
  (let [lines (str/split-lines input)
        pairs (map #(str/split % #" ") lines)]
    (map f pairs)))

(defn solution-part-1
  []
  (println (reduce + 0 (map calculate-score (parse-input parse-pair input)))))

(defmulti winning-object identity)
(defmethod winning-object :rock  [_] :paper)
(defmethod winning-object :paper  [_] :scissors)
(defmethod winning-object :scissors  [_] :rock)

(defmulti losing-object identity)
(defmethod losing-object :rock [_] :scissors)
(defmethod losing-object :paper [_] :rock)
(defmethod losing-object :scissors [_] :paper)

(defn parse-pair-2
  [pair]
  (let [f (case (first pair)
            "A" :rock
            "B" :paper
            "C" :scissors)
        s (case (second pair)
            "X" (losing-object f)
            "Y" f
            "Z" (winning-object f))]
    [f s]))

(defn solution-part-2
  []
  (println (reduce + 0 (map calculate-score (parse-input parse-pair-2 input)))))
