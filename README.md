# 🦊 AI Agent for the Fox Game

## Overview
The AI agent in your Fox game is responsible for making decisions on behalf of one of the players (typically the fox or the hounds) using game tree search logic. It evaluates possible moves and selects the best one based on a Minimax algorithm with evaluation heuristics.

## AI Agent (Minimax-Based Player)
To enable automated decision-making in the Fox game, I implemented an AI agent using the Minimax algorithm with a fixed search depth. The agent simulates possible future game states and evaluates them using a heuristic scoring function (FoxGameEval). It chooses the move that leads to the best possible outcome for its player.

Algorithm: Minimax (no alpha-beta pruning used)

Evaluation function: Custom logic to measure how close the fox is to winning or being trapped.

Game depth: [Insert your depth here if applicable, e.g., 3 levels deep]

Performance: Suitable for turn-based play with minimal lag.

Classes Involved:

FoxGameMiniMax: Core AI logic.

FoxGameEval: Heuristic evaluation function.

AiGameEngine: Runs the AI-controlled game.

FoxGameEngine: Manages game logic and rules.


