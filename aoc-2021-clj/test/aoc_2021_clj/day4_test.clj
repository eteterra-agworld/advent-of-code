(ns aoc-2021-clj.day4-test
  (:require [clojure.test :refer :all]
            [aoc-2021-clj.day4 :refer :all]))

(deftest submarine-bingo-game
  (testing "it has a winner"
    (is (true? (bingo? {:board [0] :draw [0]})))))