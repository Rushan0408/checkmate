We CAN use @SubsribeMapping for matchmaking instead of @Matchmaking since this response will only ever be sent once

Why did i had to use custom HandShakeInterceptor?



----------------------------------------------------------------------------

TODOs (Backend) :


Low Priority:
1. Change the principal from player username to player id - this prevents unncessary database calls
2. add a chat feature
3. Basic rate limiting
4. Leaderboard with ranking logic


----------------------------------------------------------------------------

High Priority:
~~Promotion Move Handling~~
~~show possible moves~~
1. Implement Draw and Resign options
2. Add player game history details
3. add players win loss draw match played and other details 
4. player auto rejoin game after disconnect/refresh
5. game state persistence
6. reconnect match logic
7. Spectator mode Avoid database fetch on each move(change principal)




----------------------------------------------------------------------------

Working on it right now:
1. Promotion Move Handling 