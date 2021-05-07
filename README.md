# Forum Demo Application

This is a simple backend for a forum. It's built with Java 16 and Spring Boot.
It provides an HTTP/JSON API for inserting, editing and listing messages.   

This app demonstrates a few conventions I consider best practice:
- Separate data classes for input and output.
- Using builders in data classes unless Records are used.  
- Wrapping jdbcTemplate with a custom util to make Dao-layer clean.
- Logging with an aspect.
- Error handling and logging with GlobalExceptionHandler so that exception is tied to a request with a generated "requestId".
- Test data initialization with builders that introduce a clean "dsl" -kind of fixture description.

# Tools
- Java 11 (or newer)
- Spring Boot
- Maven
- H2 database
- JdbcTemplate

# Requirements - a simple backend for a forum
- Can add a messageboard
- Can add messages to a messageboard
- Can edit messages
- Messages are moderated (all messages containing ugly words are moderated out)
- Can remove a comment
- Can get messageboards (list of messageboard titles and a message count)
- Can get messages from a messageboard

# Non-functional requirements
- HTTP/json interface
- Layered architecture
- The backend logs all operations that change the state/database

# No implementation for
- Authentication
- User Interface