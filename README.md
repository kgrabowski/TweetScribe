# TweetScribe

A sample application for reading tweets from the Twitter Streaming API in real time and outputting them to the
text files.


## Building and Running the Application

1. Install the prerequisites:
    * Maven (tested with version 3.6.1)
    * JDK 1.8

1. Create a `src/main/resources/twitter4j.properties` file with the following contents:
    ```
    oauth.consumerKey= <<your API key>>
    oauth.consumerSecret= <<your API secret key>>
    oauth.accessToken= <<your access token (can be read-only)>>
    oauth.accessTokenSecret= <<your access token secret>>
    ```

    You can find those values on the _Keys and tokens_ tab in your Twitter app configuration.

2. Build the application with Maven:
    ```bash
    mvn package
    ```
   
   If you didn't configure the Twitter API credentials above, the build will fail when running the acceptance
   tests.

3. Run the application:
    ```bash
    java -jar target/tweetscribe.jar
    ```
   
   You will be prompted to enter some topics to subscribe to. Although the technical limit in the app is 5, I
   wasn't able to use more than two after a first day without being rate-limited.
   
   After waiting some time, you can kill the app with CTRL-C and look at the generated files. They will have
   the same names as the selected topic names, with `.txt` extension, e.g. for _movies_ there will be a
   `movies.txt` file.
 
 
## Notes

This application is of course an overkill for such a small task. It only showcases some design choices and
patters used in larger applications, notably:

 * Hexagonal Architecture (or Ports and Adapters)
 * Dependency Injection and Inversion of Control
 * some design patterns (Abstract Factory, Facade)
 * driving the development of an application with TDD (acceptance tests and unit tests)
