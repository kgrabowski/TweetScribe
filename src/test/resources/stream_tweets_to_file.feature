Feature: Stream Tweets to File

  Scenario: Single Topic Stream
    When the user starts the program
    And the user chooses the topic "music"
    And the user waits a bit
    Then the file "music.txt" contains some tweets
