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



httpOnly cookie setup - Or better — the proper way to handle this is to have the backend issue a short-lived, single-use WebSocket ticket:

Frontend calls /api/ws-ticket (HTTP request, cookie sent automatically ✅)
Backend validates the cookie, generates a short-lived token (e.g. 30 seconds), stores it in memory, returns it
Frontend passes that ticket in STOMP connectHeaders
Interceptor validates the ticket, sets the principal

java@GetMapping("/ws-ticket")
public ResponseEntity<Map<String, String>> getWsTicket() {
    // principal is set by JwtAuthFilter via cookie
    String ticket = UUID.randomUUID().toString();
    ticketStore.put(ticket, getCurrentUserId()); // store with short TTL
    return ResponseEntity.ok(Map.of("ticket", ticket));
}
ts// before activating STOMP
const res = await fetch("/api/ws-ticket", { credentials: "include" });
const { ticket } = await res.json();

const client = new Client({
  connectHeaders: { ticket },
  ...
});
This keeps httpOnly(true) fully intact and is actually the most secure pattern for WebSocket auth. Want me to implement the full thing?