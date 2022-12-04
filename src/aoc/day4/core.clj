(ns aoc.day4.core
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.string :as str]))

(def input (slurp (io/resource "day4/actual_input.txt")))

(defn range-to-set
  [r]
  (let [[first, second] (str/split r #"-")]
    (set (range (Integer/parseInt first) (inc (Integer/parseInt second))))))

(defn either-subset?
  [[first second]]
  (or (set/subset? first second) (set/subset? second first)))

(defn find-overlaps
  [line]
  (->> (str/split line #",")
    (map range-to-set)
    either-subset?))

(defn solution-part-1
  []
  (let [pairs (->> input
                (str/split-lines)
                (map find-overlaps)
                (reduce (fn [acc i] (if i (inc acc) acc)) 0))]
    (prn pairs)))
(defn solution-part-2
  []
  )
