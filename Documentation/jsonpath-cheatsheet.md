now i# JsonPath Cheatsheet

## Root & Fields
| Expression | Description |
|---|---|
| `$` | Root element |
| `$.status` | Field named "status" on root object |
| `$.date` | Field named "date" on root object |

## Arrays
| Expression | Description |
|---|---|
| `$.length()` | Length of root array |
| `$[0]` | First element of root array |
| `$[0].status` | "status" field of first element |
| `$[-1]` | Last element |

## Nested Objects
| Expression | Description |
|---|---|
| `$.movie.title` | Nested field |
| `$[0].movie.title` | Nested field on first array element |

## Filters
| Expression | Description |
|---|---|
| `$[?(@.status == 'ACTIVE')]` | All elements where status is ACTIVE |
| `$[?(@.showingId == 1)]` | Element where showingId is 1 |

## Common MockMvc Examples
```java
// Check status code
.andExpect(status().isOk())           // 200
.andExpect(status().isCreated())      // 201
.andExpect(status().isNotFound())     // 404

// Check array response
.andExpect(jsonPath("$.length()").value(10))
.andExpect(jsonPath("$[0].status").value("ACTIVE"))

// Check object response
.andExpect(jsonPath("$.status").value("ACTIVE"))
.andExpect(jsonPath("$.showingId").value(1))

// Check field exists
.andExpect(jsonPath("$.status").exists())

// Check array is not empty
.andExpect(jsonPath("$").isNotEmpty())
```