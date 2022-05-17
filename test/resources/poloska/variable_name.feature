Feature: Variable gets name assigned
  Background:
    Given there are no other variables
    And we are not in a function

  Scenario: We create a variable with a given name
    Given there are no other variables
    When we create a variable called test with value 6.0
    Then the container has 1 variable
    And there will be a variable called test with value 6.0
  
  Scenario: We create a loop variable
    Given there are no other variables
    When we create a loop variable
    Then the container has 1 variable
    And there will be a variable called l1 with value 0.0

  Scenario: We create a loop variable when there's already one
    Given we are 2 loops deep
    When we create a loop variable
    Then there will be a variable called l3

  Scenario: We create a loop variable in a function
    Given we are in a function called testFunc
    When we create a loop variable
    Then there will be a variable called testFunc_l1

  Scenario: We create multiple loop variables in a function
    Given we are in a function called testFunc
    And we are 2 loops deep
    When we create a loop variable
    Then there will be a variable called testFunc_l1
    And there will be a variable called testFunc_l2
    And there will be a variable called testFunc_l3

  Scenario: Reuse variable name
    Given there are no other variables
    When we are 3 loops deep
    And we remove a loop variable l3
    And we create a loop variable
    Then there will not be a variable called l4
    And there will be a variable called l3

