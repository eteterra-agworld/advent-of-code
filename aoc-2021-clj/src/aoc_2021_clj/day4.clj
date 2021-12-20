(ns aoc-2021-clj.day4
  (:require [clojure.string :as str]))

(defn filter-out [mask from]
  (filter (complement (into #{} mask)) from))

; board
(defn size-of [board] (-> board count Math/sqrt int))
(defn rows-of [board] (-> board size-of (partition board)))
(defn columns-of 
  "shift through the board from left to right and take every nth item."
  [board]
  (loop [shift 0 columns []]
    (if (< shift (size-of board))
      (recur (inc shift) (conj columns (take-nth (size-of board) (drop shift board))))
      columns)))

; win conditions
(defn line-drawn? [lines draws]
  (->> lines (map (partial filter-out draws)) (some empty?) true?))

(defn horizontal-win? [board draws] (line-drawn? (rows-of board) draws))
(defn vertical-win? [board draws] (line-drawn? (columns-of board) draws))

(defn bingo? [board draws] 
  (or (horizontal-win? board draws) (vertical-win? board draws)))
(def bingo?-memo (memoize bingo?))

(defn play 
  "keep playing until there are one or more winners."
  [boards numbers]
  (loop [draws []]
    (let [winners (filter #(bingo?-memo % draws) boards)]
      (cond
        (empty? winners) (recur (take (-> draws count inc) numbers))
        :else [winners draws]))))

(defn score [board draws]
  (* (apply + (filter-out draws board)) (last draws)))

; parse input
(def read-file (comp str/split-lines slurp))

(defn lines [input]
  (->> (partition-by empty? input) (remove #(empty? (first %)))))

(defn parse-board [board]
  (map #(Integer/parseInt %) (str/split (str/join, " " (map str/trim board)) #"\s+")))

(defn parse [lines]
  {:numbers (map #(Integer/parseInt %) (str/split (first (first lines)) #",")) 
   :boards (map parse-board (rest lines))})

; part 1
(defn first-board-to-win [boards numbers]
  (let [[winners draws] (play boards numbers)]
    (score (first winners) draws)))

; part 2
(defn last-board-to-win [all-boards numbers]
  (loop [boards all-boards]
    (let [[winners draws] (play boards numbers)]
      (cond
        (= 1 (count boards)) (score (first winners) draws)
        :else (recur (filter-out winners boards))))))

(defn answers []
  (let [{numbers :numbers boards :boards} (-> "files/day4.txt" read-file lines parse)]
    (println "Answer 1:" (time (first-board-to-win boards numbers)))
    (println "Answer 2:" (time (last-board-to-win boards numbers)))))