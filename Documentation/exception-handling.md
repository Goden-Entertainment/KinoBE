# Exception Handling in Spring Boot

## The Problem

When a business rule is violated (e.g. a showing is scheduled in the past), we need to tell the client something went wrong — and give them the right HTTP status code.

Throwing a generic `RuntimeException` from the service layer will result in a **500 Internal Server Error**, even when the mistake is the client's fault. The correct status for invalid client input is **400 Bad Request**.

---

## The Layers and Their Responsibilities

```
Client → Controller → Service → Repository
              ↑
        Where HTTP lives
```

- **Service** — knows *what* went wrong (business rule violation)
- **Controller** — knows *how* to respond over HTTP (status codes, response bodies)
- **`@ControllerAdvice`** — a global interceptor that translates exceptions into HTTP responses

The goal is **separation of concerns**: the service should not know about HTTP, and the controller should not contain business logic.

---

## The Flow with a Custom Exception

1. Client sends a POST with a past date
2. Controller calls `service.createShowing(showing)`
3. Service throws `InvalidShowingDateException`
4. The exception bubbles up through the call stack
5. Spring intercepts it **before** it becomes a 500 error
6. `@ControllerAdvice` catches it and returns `400 Bad Request`

---

## Implementation

### 1. Custom Exception

```java
public class InvalidShowingDateException extends RuntimeException {
    public InvalidShowingDateException(String message) {
        super(message);
    }
}
```

### 2. Throw it in the Service

```java
if (showing.getDate().isBefore(today)) {
    throw new InvalidShowingDateException("Showing date cannot be in the past");
}
```

### 3. Global Exception Handler

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidShowingDateException.class)
    public ResponseEntity<String> handleInvalidDate(InvalidShowingDateException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
```

---

## Why a Custom Exception over `IllegalArgumentException`?

`IllegalArgumentException` is a standard Java exception — too broad to catch precisely. A custom exception gives you:

- **Precision** — catch only this specific business rule violation
- **Extensibility** — add fields like `invalidDate` or `allowedRange` to include in the error response
- **Readability** — the class name documents the intent

---

## The `@ControllerAdvice` as a Translator

Think of it as a dictionary mapping Java exceptions to HTTP responses:

| Exception                    | HTTP Response        |
|------------------------------|----------------------|
| `InvalidShowingDateException`| 400 Bad Request      |
| `ShowingNotFoundException`   | 404 Not Found        |
| (unhandled)                  | 500 Internal Server Error |

This keeps error handling consistent across the whole API without cluttering every controller method with try/catch blocks.