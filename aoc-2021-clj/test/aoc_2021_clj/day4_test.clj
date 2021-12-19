(ns aoc-2021-clj.day4-test
  (:require [clojure.test :refer :all]
            [aoc-2021-clj.day4 :refer :all]))

(def create-board
  "fixed size 5x5"
  [22 13 17 11 0 8 2 23 4 24 21 9 14 16 7 6 10 3 18 5 1 12 20 15 19])

(deftest submarine-bingo-game
  (testing "it has a winner"
    (is (true? (bingo? {:board [0] :draw [0]})))
    (is (true? (bingo? {:board [1 2 3 4] :draw [3 4]})) "horizontal win")
    (is (true? (bingo? {:board [1 2 3 4] :draw [4 3]})) "horizontal win out of order")
    (is (true? (bingo? {:board [1 2 3 4] :draw [1 3]})) "2x2 vertical win")
    (is (true? (bingo? {:board (range 1 10) :draw [1 4 7]})) " 3x3 vertical win"))

  (testing "it has a loser"
    (is (false? (bingo? {:board [0] :draw [1]}))))

  (testing "each board has a score"
    (is (= 0 (score {:board [0] :draw [0]})))
    (is (= 5 (score {:board [1 2 3] :draw [1]})))
    (is (= (* 239 24) (score {:board create-board :draw [8 2 23 4 24]})))))