(ns aoc.day1.core
  (:require
    [clojure.java.io :as io]
    [clojure.string :as str]))

(def input-text (slurp (io/resource "day1/actual_input.txt")))

(defn parse-input-string
  [input]
  (let [lines (str/split-lines input)
        calorie-lists
        (->> lines
          (partition-by #(= "" %))
          (filter #(not= "" (first %)))
          (map #(into [] %))
          (into []))]
    (map #(reduce (fn [acc i] (+ acc (Integer/parseInt i))) 0 %) calorie-lists)))

(defn reverse-sort
  [list]
  (sort #(compare %2 %1) list))

(defn sorted-calories
  [input]
  (let [calorie-lists (parse-input-string input)]
    (reverse-sort calorie-lists)))

(defn solution-part-1
  []
  (let [most-calories (sorted-calories input-text)]
    (println (first most-calories))))

(defn solution-part-2
  []
  (let [most-calories (sorted-calories input-text)
        top-three (->> most-calories
                    (take 3)
                    (reduce + 0))]
    (println top-three)))


