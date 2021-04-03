<h1>Chess</h1>
<ol>
<li><h4>What will the application do?</h4>
<p>Chess is a game that has existed for centuries and recently has gained a lot of popularity. In my opinion, chess is the ultimate game that can elicit a range of emotions in its player: from desperation when you realize that you made a one-move blunder to great joy when you see a pattern that your opponent cannot escape and which will allow you to win the game. With this project, I will try to recreate chess with all its intricate rules, and after that, I will try to implement some features that will make chess more visually pleasing.</p></li>
<li><h4>Who will use it? </h4>
<p>This application is aimed to give chess enthusiasts and novices a place to explore different chess openings and enjoy the game with their friends.</p></li>
<li><h4>Why is this project of interest to you? </h4>
<p>The previous year, I rediscovered chess for myself and found it exciting to play. Also, I find chess programming interesting, and the idea that humans with everyday come closer and closer to solving this ancient game excites me. So this project would allow me to play with different sets of rules and practice abstraction in the short term, and in the long term, this project will provide a foundation for implementing chess programming techniques and creating my chess engine.</p></li>
</ol>

<h3>User stories</h3>
<ul>
<li>As a user, I want to be sure that every move that I play is legal.</li>
<li>As a user, I want to be able to add multiple games.</li>
<li>As a user, I want to see the names of all games that I added.</li>
<li>As a user, I want to close games and continue them exactly from where I left off when I reopen them.</li>
<li>As a user, I want to see at least some basic representation of a board to have an idea of where my pieces at.</li>
<li>As a user, I want to be able to save multiple games.</li>
<li>As a user, I want to be able to either save or not my current progress in a given game.</li>
</ul>

<h3>Phase 4: Task 2</h2>
<p> I have created a type hierarchy other than writeable. I have an abstract class which all other pieces extend.
Every piece provides a unique implementation for makeMove method in piece class. Also, I have implemented a bidirectional
relationship between piece and board, where every piece has a board and every board has at least 1 to 32 pieces.</p>

<h3>Phase 4: Task 3 </h3>
<p> If I had more time to work on this project I would try to refactor methods that check for pieces in the way.
All this methods have almost identical implementation, however, it was really to hard to abstract movement direction. 
If I had more time to work on this project this would be a first thing I would have change. Also there is unnecessary 
coupling between player and game, in future, I may try removing on of this classes or changing implementation of move 
method inside player.</p>