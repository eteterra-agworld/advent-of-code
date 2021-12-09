module Day2 where

import Control.Monad.State
import Data.Maybe (mapMaybe)

type Position = Int
type Depth = Int
type Aim = Int
type SubmarineState = (Position, Depth, Aim)

type Direction = String
type Value = Int
type Action = (Direction, Value)

actionFrom :: [String] -> Maybe Action
actionFrom xs = if length xs == 2 then Just (head xs, read $ last xs) else Nothing

initialState :: SubmarineState
initialState = (0, 0, 0)

-- part 1

move1 :: [Action] -> State SubmarineState Int
move1 [] = do
    (position, depth, aim) <- get
    return $ position * depth

move1 ((direction, value):actions) = do
    (position, depth, aim) <- get
    case direction of
         "forward" -> put (position + value, depth, aim)
         "down"    -> put (position, depth + value, aim)
         "up"      -> put (position, depth - value, aim)
         _         -> put (position, depth, aim)
    move1 actions

part1 input = do
    let actions = mapMaybe (actionFrom . words) input
    evalState (move1 actions) initialState

-- part 2

move2 :: [Action] -> State SubmarineState Int
move2 [] = do
    (position, depth, aim) <- get
    return $ position * depth

move2 ((direction, value):actions) = do
    (position, depth, aim) <- get
    case direction of
         "forward" -> put (position + value, depth + aim * value, aim)
         "down"    -> put (position, depth, aim + value)
         "up"      -> put (position, depth, aim - value)
         _         -> put (position, depth, aim)
    move2 actions

part2 input = do 
    let actions = mapMaybe (actionFrom . words) input
    evalState (move2 actions) initialState
