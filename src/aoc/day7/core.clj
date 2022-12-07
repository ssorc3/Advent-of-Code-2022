(ns aoc.day7.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "day7/actual_input.txt")))

(defn apply-command
  [state command]
  (if-let [[_ dir] (re-matches #"\$ cd ([a-zA-Z]+)" command)]
    (update state :path #(conj %1 dir))
    (if (re-matches #"\$ ls" command)
      state
      (if-let [[_ size] (re-matches #"([0-9]+) [a-zA-Z]+.?[a-zA-Z]*" command)]
        (->
          (reduce (fn [s n] (update-in s [:folders (str/join "/" (take n (:path state)))] #(+ (Integer/parseInt size) (or % 0)))) state (range 1 (inc (count (:path state)))))
          (update-in [:folders :root] #(+ (Integer/parseInt size) (or % 0))))
        (if (= "$ cd .." command)
          (update state :path pop)
          state)))))

(defn solution-part-1
  []
  (let [lines (str/split-lines input)
        result (reduce apply-command {:path [] :files [] :folders {}} (drop 1 lines))]
    (prn (reduce + 0 (filter (fn [val] (< val 100000)) (vals (:folders result)))))))

(defn solution-part-2
  []
  (let [lines (str/split-lines input)
        result (reduce apply-command {:path [] :files [] :folders {}} (drop 1 lines))
        total-used (get-in result [:folders :root])
        unused (- 70000000 total-used)
        smallest-deletion (- 30000000 unused)
        sorted-folder-sizes (sort (vals (:folders result)))
        first-match (reduce (fn [_ i] (if (<= smallest-deletion i) (reduced i) nil)) nil sorted-folder-sizes)
        ]
    (println first-match)))
