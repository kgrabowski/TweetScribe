Feature: Stream Tweets to File

  Scenario: Single Topic
    When the user starts the program
    And the user chooses the topic "music"
    And the user waits a bit
    Then the file "music.txt" contains some tweets

  Scenario: Multiple Topics
    When the user starts the program
    And the user chooses topics:
      | food       |
      | movies     |
      | basketball |
    And the user waits a bit
    Then the file "food.txt" contains some tweets
    And the file "movies.txt" contains some tweets
    And the file "basketball.txt" contains some tweets
