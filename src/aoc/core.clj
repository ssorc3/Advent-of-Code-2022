(ns aoc.core
  (:require [aoc.day1.core :as day1]
            [aoc.day2.core :as day2]
            [aoc.day3.core :as day3]
            [aoc.day4.core :as day4]
            [aoc.day5.core :as day5]
            ))

(defn -main
  [& _args]
  #_((println "------------- Day 1 -------------")
     (println "part 1")
     (day1/solution-part-1)
     (println "part 2")
     (day1/solution-part-2))

  #_((println "------------- Day 2 -------------")
     (println "part 1")
     (day2/solution-part-1)
     (println "part 2")
     (day2/solution-part-2))


  #_((println "------------- Day 3 -------------")
   (println "part 1")
   (day3/solution-part-1)
   (println "part 2")
   (day3/solution-part-2))

  #_((println "------------- Day 4 -------------")
     (println "part 1")
     (day4/solution-part-1)
     (println "part 2")
     (day4/solution-part-2))

  (println "------------- Day 5 -------------")
  (println "part 1")
  (day5/solution-part-1)
  (println "part 2")
  (day5/solution-part-2)
  )

