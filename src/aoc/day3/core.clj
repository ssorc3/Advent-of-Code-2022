(ns aoc.day3.core
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.string :as str]))

(def input (slurp (io/resource "day3/actual_input.txt")))

(defn get-shared
  [rucksack]
  (let [halfway (/ (count rucksack) 2)
        [first-half second-half] (split-at halfway rucksack)]
    (first (set/intersection (set first-half) (set second-half)))))

(defn calculate-score
  [char]
  (if (<= (int \a) (int char) (int \z))
    (inc (- (int char) (int \a)))
    (if (<= (int \A) (int char) (int \Z))
      (+ 27 (- (int char) (int \A))))))

(defn solution-part-1
  []
  (let [rucksacks (str/split-lines input)]
    (println (->> rucksacks
           (map get-shared)
           (map calculate-score)
           (reduce + 0)))))

(defn get-badge
  [group]
  (->> group
    (map set)
    (apply set/intersection)
    first))

(defn solution-part-2
  []
  (let [rucksacks (str/split-lines input)
        groups (partition 3 rucksacks)]
    (println (->> groups
               (map get-badge)
               (map calculate-score)
               (reduce + 0)))))
