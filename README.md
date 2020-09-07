# Heroes Game
<img src="https://github.com/annihilator01/GameEngine/blob/master/src/main/java/gui/assets/icon.png" width="200">

## Description
Game inspired by “Heroes of Might and Magic” and “Disciples”, which is about armies, that are travelling around the world and engage in battles with other armies.

## Game Process
### Start Scene
<img src="https://i.imgur.com/pbjZSxK.png" width="700">

***

### Selection Scene
<img src="https://i.imgur.com/i0ehAm6.png" width="700">
Here you can choose player names, number of unit stacks (from 1 to 6), type of units in each unit stack, number of units in each unit stack (from 1 to 1000).
<br/>There are 11 base types of units in the game, but you can add your custom types creating folder 'mods/' in the same folder with .jar file of the game. This folder must contain .json file with information about custom type and image of this type (optional). Example of such .json files <a href="https://github.com/annihilator01/GameEngine/releases/tag/1.0">here</a>. 
<br/>Also, if you want to delete one unit stack you should right-click on it.

<br/>Each unit has some characteristics (list of them below).
- **Type**
- **HP**
- **Attack**
- **Defense**
- **Damage**
- **Initiative** - turn priority
- **Passive Skills**
- **Active Skill**
- **Icon**

<br/>Each type of units may or may not have several passive skills and one active skill (their descriptions below).
- **Passive skills**:
  - **Shooter** - target doesn't launch a counterattack, so doesn't actor
  - **Clearshot** - ignores target defense
  - **Undead** - this creature can be resurrected
  - **No Enemy Resistance** - target doesn't launch a counterattack
  - **Attack ALL** - attack all unit stacks of enemy's army
  - **Endless Resistance** - this creature always counterattack after being attacked
  
- **Active skills**:
  - **Punishing Hit** - increases target attack by 12 points
  - **Curse** - decreases target attack by 12 points (attack can't turn less than 0)
  - **Weakening** - decreases target defense by 12 points (attack can't turn less than 0)
  - **Acceleration** - increases target initiative by 40%
  - **Resurrection** - resurrects 100 target (only with passive skill Undead!) health points
for every unit that conjures (resurrection can't restore more hp than it was in the beginning of the battle)

***

### Battle Scene
<img src="https://i.imgur.com/MIdHSoK.png" width="900">
Here armies of two players are fighting each other. Almost every element of the scene has its own tooltip<br/><br/>

💚 **Green** highlighted unit stack ***(actor)*** is an unit stack, that can make a move right now.
<br/>❤️ **Red** highlighted unit stack ***(target)*** is an unit stack, that is selected for actions on it by ***actor***.
<br/>💙 **Blue** highlighted unit stack is a dead unit stack
<br/>💛 **Gold** highlighted unit stack ***(target)*** is a dead unit stack, that is selected by ***actor*** with *Resurrection* active skill

<br/>Players can choose different actions for current move unit stack (their descriptions below).
- **Actions:**
  - **Attack** - ***actor*** attacks ***target***, and ***target*** counterattacks (also depends on ***actor*** and ***target*** passive skills)
  - **Defense** - ***actor*** skips the turn (doesn't play in this round) and increases own defense by 30%
  - **Active Skill** - ***actor*** uses its active skill on ***target*** (one unit stack can use active skill only 3 times during the battle)
  - **Wait** - ***actor*** skips the turn (take turn further in this round) and move to the end of the initiative scale, where priority for anticipants is inversive
  - **Give Up** - ***actor's*** army gives up, enemy army wins

*(Note: Wait and Defense can't be used together more than 5 times in a row!)*

## Requirements
OS: Windows 7/8/10
<br/>Screen resolution: 1920x1080

## Links
### [Demo Video (02:14)](http://www.youtube.com/watch?feature=player_embedded&v=i6bLBVw-8AQ)
### [Download Game (15.9MB)](https://github.com/annihilator01/GameEngine/releases/tag/1.0)
