Feature: Retrieve dictionary information for a given word
  As a Product of Owner Dictionary
  I want to get the meanings of English word
  So that the user Retrieve dictionary information for a given word

  @positive
  Scenario Outline:   Retrieve dictionary information for a given word <Scenario Details>
    Given the base path for API
    And path parameters are the following
      | path | pathParameter | parameterValue   |
      | en   | id            | <parameterValue> |
    And send the Valid header
    When I send the GET request
    And I should receive <httpresponce> response

    Examples:
      | Scenario Details | parameterValue | httpresponce |
      | 1  hello         | hello          | 200-valid    |
      | 2  Efflorescence | Efflorescence  | 200-valid    |
      | 3  Petrichor     | Petrichor      | 200-valid    |
      | 4  Supine        | Supine         | 200-valid    |
      | 5  Idyllic       | Idyllic        | 200-valid    |
      | 6  Sequoia       | Sequoia        | 200-valid    |


  @Negative
  Scenario Outline:   Retrieve dictionary information for a given word <Scenario Details>
    Given the base path for API
    And path parameters are the following
      | path | pathParameter | parameterValue   |
      | en   | id            | <parameterValue> |
    And send the Valid header
    When I send the GET request
    And I should receive <httpresponce> response

    Examples:
      | Scenario Details | parameterValue | httpresponce  |
      | 7 1234           | 1234           | 404 - Invalid |
      | 8 )Q$£           | )Q$£           | 404 - Invalid |
      | 9 12sd£$         | 12sd£$         | 404 - Invalid |


  @Negative1
  Scenario Outline:   Retrieve dictionary information for a given word <Scenario Details>
    Given the base path for API
    And path parameters are the following
      | path | pathParameter | parameterValue |
      | en   | id            | hello          |
    And send the <header status> header
    When I send the GET request
    And I should receive <httpresponce> response

    Examples:
      | Scenario Details  | header status  | httpresponce  |
      | 10 NoApikey       | NoApikey       | 403 - Invalid |
      | 11 invalid.apikey | invalidapikey  | 403 - Invalid |
      | 12 invalid.appid  | invalidappid   | 403 - Invalid |





