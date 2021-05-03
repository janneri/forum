# Forum Demo Application

This is a simple backend for a forum. It's built with Java 16 and Spring Boot.
It provides an HTTP/JSON API for inserting, editing and listing messages.   

This app demonstrates a few conventions I consider best practice:
- Separate data classes for input and output.
- Using builders in data classes unless Records are used.  
- Wrapping jdbcTemplate to make Dao-layer clean.
- Logging with an aspect.
- Error handling and logging with GlobalExceptionHandler so that exception is tied to a request with a generated "requestId".
- Test data initialization with builders that introduce a clean "dsl"-kindof fixture description.
