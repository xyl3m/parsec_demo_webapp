Feature: /users endpoints test cases

  @users @dev
  Scenario Outline: e2e test of /users
    When I query Users with ID "" and name "<username>"
    Then the response status code should be <200>
    And the response should be contain results total of pagination <0>

    When I create User with name "<username>"
    Then the response status code should be <201>
    And the response User should be the same as provided

    When I get User by ID "@userId"
    Then the response status code should be <200>
    And the response User should be the same as provided

    When I query Users with ID "@userId" and name ""
    Then the response status code should be <200>
    And the response should be contain results total of pagination <1>

    When I update User with name "<username>" by ID "@userId"
    Then the response status code should be <200>
    And the response User should be the same as provided

    When I delete User by ID "@userId"
    Then the response status code should be <204>

    When I get User by ID "@userId"
    Then the response status code should be <404> and detail error code should be <40401001>

    When I query Users with ID "@userId" and name ""
    Then the response status code should be <200>
    And the response should be contain results total of pagination <0>
    Examples:
      | username       |
      | testUser559FD7 |
      | testUser326012 |
      | testUser15EC63 |
