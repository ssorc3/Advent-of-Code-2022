(ns aoc.day8.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "day8/actual_input.txt")))

(defn visible-from-left?
  [trees x y]
  (let [tree-height (get-in trees [y x])]
    (loop [x (dec x)]
      (cond
        (< x 0) true
        (<= tree-height (get-in trees [y x])) false
        :else (recur (dec x))))))


(defn visible-from-top?
  [trees x y]
  (let [tree-height (get-in trees [y x])]
    (loop [y (dec y)]
      (cond
        (< y 0) true
        (<= tree-height (get-in trees [y x])) false
        :else (recur (dec y))))))


(defn visible-from-right?
  [trees x y]
  (let [width (count (first trees))
        tree-height (get-in trees [y x])]
    (loop [x (inc x)]
      (cond
        (> x (dec width)) true
        (<= tree-height (get-in trees [y x])) false
        :else (recur (inc x))))))


(defn visible-from-bottom?
  [trees x y]
  (let [height (count trees)
        tree-height (get-in trees [y x])]
    (loop [y (inc y)]
      (cond
        (> y (dec height)) true
        (<= tree-height (get-in trees [y x])) false
        :else (recur (inc y))))))

(defn is-visible?
  [trees x y]
  (or (visible-from-left? trees x y)
    (visible-from-top? trees x y)
    (visible-from-right? trees x y)
    (visible-from-bottom? trees x y)))

(defn solution-part-1
  []
  (let [lines (str/split-lines input)
        first-line (first lines)
        parse-line (fn [line] (into [] (map #(Integer/parseInt (str %)) line)))
        parsed-lines (into [] (map parse-line lines))
        visible (for [x (range (count first-line))
                      y (range (count lines))]
                  (is-visible? parsed-lines x y))]
    (println (count (filter identity visible)))))



(defn visible-trees-left
  [trees x y]
  (let [tree-height (get-in trees [y x])]
    (loop [x (dec x)
           num-trees 0]
      (cond
        (< x 0) num-trees
        (> tree-height (get-in trees [y x])) (recur (dec x) (inc num-trees))
        :else (inc num-trees)))))


(defn visible-trees-up
  [trees x y]
  (let [tree-height (get-in trees [y x])]
    (loop [y (dec y)
           num-trees 0]
      (cond
        (< y 0) num-trees
        (> tree-height (get-in trees [y x])) (recur (dec y) (inc num-trees))
        :else (inc num-trees)))))


(defn visible-trees-right
  [trees x y]
  (let [width (count (first trees))
        tree-height (get-in trees [y x])]
    (loop [x (inc x)
           num-trees 0]
      (cond
        (> x (dec width)) num-trees
        (> tree-height (get-in trees [y x])) (recur (inc x) (inc num-trees))
        :else (inc num-trees)))))


(defn visible-trees-down
  [trees x y]
  (let [height (count trees)
        tree-height (get-in trees [y x])]
    (loop [y (inc y)
           num-trees 0]
      (cond
        (> y (dec height)) num-trees
        (> tree-height (get-in trees [y x])) (recur (inc y) (inc num-trees))
        :else (inc num-trees)))))

(defn scenic-score
  [trees x y]
  (*
    (visible-trees-left trees x y)
    (visible-trees-up trees x y)
    (visible-trees-right trees x y)
    (visible-trees-down trees x y)))


(defn solution-part-2
  []
  (let [lines (str/split-lines input)
        first-line (first lines)
        parse-line (fn [line] (into [] (map #(Integer/parseInt (str %)) line)))
        parsed-lines (into [] (map parse-line lines))
        scenic-scores (for [x (range (count first-line))
                      y (range (count lines))]
                  (scenic-score parsed-lines x y))
        max (reduce (fn [acc i] (if (> i acc) i acc)) 0 scenic-scores)]
    (println max)))