(ns aoc.day5.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "day5/actual_input.txt")))

(defn transpose [m]
  (apply mapv vector m))

(defn get-entry-cols
  [num-stacks]
  (map #(+ 1 (* % 4)) (range num-stacks)))

(defn get-entries
  [num-stacks line]
  (let [cols (get-entry-cols num-stacks)]
    (map #(nth line % \space) cols)))

(defn generate-stacks
  [setup]
  (let [num-stacks (Integer/parseInt (str (last (str/trim (last setup)))))
        entries (transpose (map (partial get-entries num-stacks) (rest (reverse setup))))
        filtered-entries (map (fn [e] (into [] (filter #(not= \space %) e))) entries)]
    (into [] filtered-entries)))

(defn parse-instruction
  [instruction-string]
  (let [words (str/split instruction-string #" ")]
    {:from  (dec (Integer/parseInt (nth words 3)))
     :to    (dec (Integer/parseInt (nth words 5)))
     :count (Integer/parseInt (nth words 1))}))

(reduce-kv (fn [result _index v] (cons v result)) [] {1 1 2 2})

(defn execute-instruction-part-1
  [stacks instruction]
  (let [parsed-instruction (parse-instruction instruction)]
    (reduce
      (fn [acc _i]
        (let [indexed-stacks (into (sorted-map) (map-indexed (fn [a b] [a b]) acc))
              from-stack (into [] (get indexed-stacks (:from parsed-instruction)))
              to-stack (into [] (get indexed-stacks (:to parsed-instruction)))
              stack-value (peek from-stack)
              new-from-stack (pop from-stack)
              new-to-stack (into [] (conj to-stack stack-value))]
          (into [] (vals (into (sorted-map)
                           (assoc
                             (assoc indexed-stacks (:from parsed-instruction) new-from-stack)
                             (:to parsed-instruction)
                             new-to-stack)))))) stacks (range (:count parsed-instruction)))))

(defn execute-instructions
  [stacks instructions executor]
  (reduce executor
    stacks instructions))

(defn solution-part-1
  []
  (let [lines (str/split-lines input)
        [setup _gap instructions] (partition-by (partial = "") lines)
        stacks (generate-stacks setup)
        result (execute-instructions stacks instructions execute-instruction-part-1)
        tops (apply str (map peek result))
        ]
    (println tops)))

(defn execute-instruction-part-2
  [stacks instruction]
  (let [parsed-instruction (parse-instruction instruction)
        indexed-stacks (into (sorted-map) (map-indexed (fn [a b] [a b]) stacks))
        from-stack (into [] (get indexed-stacks (:from parsed-instruction)))
        to-stack (into [] (get indexed-stacks (:to parsed-instruction)))
        left-on-stack (- (count from-stack) (:count parsed-instruction))
        pop-n (into [] (drop left-on-stack from-stack))
        new-from-stack (into [] (take left-on-stack from-stack))
        new-to-stack (into [] (apply conj to-stack pop-n))]
    (into [] (vals (into (sorted-map)
                     (assoc
                       (assoc indexed-stacks (:from parsed-instruction) new-from-stack)
                       (:to parsed-instruction)
                       new-to-stack))))))

(defn solution-part-2
  []
  (let [lines (str/split-lines input)
        [setup _gap instructions] (partition-by (partial = "") lines)
        stacks (generate-stacks setup)
        result (execute-instructions stacks instructions execute-instruction-part-2)
        tops (apply str (map peek result))
        ]
    (println tops)))
