# Showing Endpoints

Base URL: `/showing`

## Create Showing
**POST** `/showing`

Creates a new showing. Returns the created showing with its generated ID.

- **Body:** Showing object (JSON)
- **Response:** `201 CREATED`

```json
{
  "date": "2026-03-09",
  "time": "14:30:00",
  "status": "ACTIVE"
}
```

---

## Get All Showings
**GET** `/showing`

Returns a list of all showings.

- **Response:** `200 OK`

---

## Get Showings By Movie
**GET** `/showing/movie/{movieId}`

Returns all showings for a specific movie.

- **Path variable:** `movieId` — ID of the movie
- **Response:** `200 OK`

---

## Get Showings By Theater
**GET** `/showing/theater/{theaterId}`

Returns all showings for a specific theater.

- **Path variable:** `theaterId` — ID of the theater
- **Response:** `200 OK`

---

## Get Showing By ID
**GET** `/showing/{showingId}`

Returns a single showing by its ID.

- **Path variable:** `showingId` — ID of the showing
- **Response:** `200 OK`

---

## Update Showing
**PUT** `/showing/{showingId}`

Updates an existing showing. The ID in the URL determines which showing is updated.

- **Path variable:** `showingId` — ID of the showing to update
- **Body:** Showing object with updated fields (JSON)
- **Response:** `200 OK`

```json
{
  "date": "2026-03-09",
  "time": "16:00:00",
  "status": "FINISHED"
}
```

---

## Delete Showing
**DELETE** `/showing/{showingId}`

Deletes a showing by its ID. Returns the deleted showing.

- **Path variable:** `showingId` — ID of the showing to delete
- **Response:** `200 OK`