Feature: Variable can be updated
  Background:
    Given there are no other variables
    And we are not in a function

  Scenario: we update a variable
    Given we have a variable named test with value 6.0
    When we change the value of test to 3.0
    Then there will be a variable called test with value 3.0

