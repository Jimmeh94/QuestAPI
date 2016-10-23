# QuestAPI
A Sponge-Vanilla based API for quests

A quest is built from checkpoints. Each checkpoint has its own condition(s) to be satisfied before it's considered complete. You can add as many conditions as you want to each checkpoint.

How to Make a Quest:
  - Simply get the API instance from QuestAPI and get the QuestBuilder instance from there
  - Get the CheckpointBuilder from the QuestBuilder instance
  - Continue to make checkpoints by adding a description, target location, as many conditions as you want to, and then buildCheckpoint()
  - You can build as many checkpoints as you want
  - Once you have finished your checkpoints, set the name of your quest, description, level the player needs to access your quest (if you want a level restriction), use checkpoints() to load your checkpoints from the CheckpointBuilder, and then finally build().
  - If you want to build multiple quests, be sure to use questBuilder.reset() after each build
  
How to Make Your Own Conditions:
  - Simply extend Condition and pass that into the CheckPointBuilder
