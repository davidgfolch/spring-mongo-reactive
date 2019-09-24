# Getting Started

### Prerequisites
- Enable annotation processors (in your IDE for lombok)

### Architecture
- Reactive Layers architecture (spring-framework reactor).
- Java non-blocking functional programming: reactor+streams.
- REST API's with Spring-WebFlux (as a POC for me, I didn't use it before).
    - Using [Functional Programming Model](https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html#_functional_programming_model)
- Persistence with reactive MongoDb (as a POC for me, I did use it before).

Notes:
- No security implementation at all.
- No persistence transaction implementation (https://spring.io/blog/2019/05/16/reactive-transactions-with-spring).
- You'll find some junit tests.
- Sonar gradle plugin (you can run sonarqube gradle task if you have sonar installed on localhost:9000)
  
### Business logic
I've changed the requirement "renting for x days".

In my implementation, when renting, you are always paying for 1 day.

When you return films you pay for surcharges if applies.

#### Details

Rent & Return films:

- In both cases:
    - Non-existent `userId` throws UserNotFoundException.
    - duplicated `filmId`'s are ignored, just processing one.
      
- Rent films
    - Non-existent `filmId`'s or not available stock, are ignored and not returned in result list.
    - With all available films found:
        - decreases stock
        - calculates rent for 1 day.
        - get saved in `Customer.films` (including date in each film).
- Return films
    - Non-existent films in `Customer.films` are ignored and not returned in result list.
    - From all matching films in `Customer.films`, gets the older one (a filmId could be repeated with different rent date)
        - calculate surcharges (after x days).
        - remove from customer & save.
        - increase film stock.

### Try it
- Run App as a spring-boot app:
    - command line: `gradlew :bootRun`
    - intellij: right button on `App.java` & Run
- Use `postman_collection.json` (importing the json file in Postman client):
    - `rentFilms`: to rent
    - `customerGet`: to see customer rented films & bonusPoints
    - `returnFilms`: to return (after x days as path param)
- Every time app starts, customer and films are created automatically with same ids.
    - in log console generated json is printed, you can use it in post bodies.
