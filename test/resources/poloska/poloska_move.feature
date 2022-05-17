Feature: Poloska moves
  Scenario: Poloska moves to left
    Given a poloska at x:0 y:0 facing left
    When the poloska goes forward 100
    Then x position is -100
    And facing left

  Scenario: Poloska moves to right
    Given a poloska at x:0 y:0 facing right
    When the poloska goes forward 100
    Then x position is 100
    And facing right

  Scenario: Poloska makes a complex move
    Given a poloska at x:600 y:300 facing up
    When the poloska goes forward 100
    And turns right
    And goes forward 100
    Then x position is 700
    And y position is 200
    And facing right
