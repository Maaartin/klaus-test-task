# Solution to the Software Engineer Test Task
* This project is written in Java. 
* It's using the [gRPC Spring Boot Starter dependency](https://github.com/yidongnan/grpc-spring-boot-starter) to run it as a gRPC service. 
* Java source files are generated from .proto files by the [Maven Protocol Buffers Plugin](https://www.xolstice.org/protobuf-maven-plugin/index.html) when running `mvn clean install`.
* To access the database data directly from the provided file it uses the [SQLite JDBC Driver library](https://github.com/xerial/sqlite-jdbc).

### Bonus
To containerize the project we could use Docker. 
Since the Maven build also provides the JAR file, we could run it as a Docker image composed together with the `database.db` volume.

### Ideas for improvement
* Refactor AggregatedCategoryScoreServiceImpl.java to make it more readable.
* Write Javadoc to describe some classes and methods.
* Use gRPC stream response to return larger datasets, e.g. for "Scores by ticket".

---
# Software Engineer Test Task

As a test task for [Klaus](https://www.klausapp.com) software engineering position we ask our candidates to build a small [gRPC](https://grpc.io) service using language of their choice. Preferred language for new services in Klaus is [Go](https://golang.org).

The service should be using provided sample data from SQLite database (`database.db`).

Please fork this repository and share the link to your solution with us.

### Tasks

1. Come up with ticket score algorithm that accounts for rating category weights (available in `rating_categories` table). Ratings are given in a scale of 0 to 5. Score should be representable in percentages from 0 to 100. 

2. Build a service that can be queried using [gRPC](https://grpc.io/docs/tutorials/basic/go/) calls and can answer following questions:

    * **Aggregated category scores over a period of time**
    
        E.g. what have the daily ticket scores been for a past week or what were the scores between 1st and 31st of January.

        For periods longer than one month weekly aggregates should be returned instead of daily values.

        From the response the following UI representation should be possible:

        | Category | Ratings | Date 1 | Date 2 | ... | Score |
        |----|----|----|----|----|----|
        | Tone | 1 | 30% | N/A | N/A | X% |
        | Grammar | 2 | N/A | 90% | 100% | X% |
        | Random | 6 | 12% | 10% | 10% | X% |

    * **Scores by ticket**

        Aggregate scores for categories within defined period by ticket.

        E.g. what aggregate category scores tickets have within defined rating time range have.

        | Ticket ID | Category 1 | Category 2 |
        |----|----|----|
        | 1   |  100%  |  30%  |
        | 2   |  30%  |  80%  |

    * **Overall quality score**

        What is the overall aggregate score for a period.

        E.g. the overall score over past week has been 96%.

    * **Period over Period score change**

        What has been the change from selected period over previous period.

        E.g. current week vs. previous week or December vs. January change in percentages.


### Bonus

* How would you build and deploy the solution?

    At Klaus we make heavy use of containers and [Kubernetes](https://kubernetes.io).
