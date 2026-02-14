We CAN use @SubsribeMapping for matchmaking instead of @Matchmaking since this response will only ever be sent once

Why did i had to use custom HandShakeInterceptor?



----------------------------------------------------------------------------

TODOs :


Low Priority:
1. Change the principal from player username to player id - this prevents unncessary database calls
2. add a chat feature
3. Basic rate limiting
4. Leaderboard with ranking logic


----------------------------------------------------------------------------

High Priority:
1. Promotion Move Handling 
2. Implement Draw and Resign options
3. Add player game history details
4. add players win loss draw match played and other details 
5. player auto rejoin game after disconnect/refresh
6. game state persistence
7. reconnect match logic
8. Spectator mode



----------------------------------------------------------------------------

Working on it right now:
1. Promotion Move Handling 