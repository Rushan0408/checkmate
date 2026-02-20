We CAN use @SubsribeMapping for matchmaking instead of @Matchmaking since this response will only ever be sent once

Why did i had to use custom HandShakeInterceptor?

----------------------------------------------------------------------------

TODOs (Backend) :

Low Priority:

1. add a chat feature
2. Basic rate limiting
3. Leaderboard with ranking logic

----------------------------------------------------------------------------

High Priority:

1. Implement Draw and Resign options
2. Add player game history details
3. add players win loss draw match played and other details 
4. game state persistence
5. Spectator mode 
6. check auth controller (and checkJWT for frontend)

----------------------------------------------------------------------------

Working on it right now:

1. Promotion Move Handling 

----------------------------------------------------------------------------

Completed:

~~Promotion Move Handling~~
~~show possible moves~~
~~player auto rejoin game after disconnect/refresh~~
~~Change the principal from player username to player id - this prevents unncessary database calls~~
