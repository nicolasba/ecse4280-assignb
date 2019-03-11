Feature: Send an email with an image attachment
  As a user with a valid email account, I would like to be able to send an email containing an image attachment to my friend.

#  Scenario Outline: Email sent with an image stored locally (Normal flow)
#
#    Given I am logged into my email account
#    And I enter my friend's "<email>"
#    And I attach an "<image>" from my computer
#    When I send the email
#    Then I should receive a successful confirmation
#
#    Examples:
#      |                email                |     image        |
#      | jose.barreyrolopez@mail.mcgill.ca   |   confused.jpg   |
##      | nicobarreyro@hotmail.com            |   confused.jpg   |

  Scenario Outline:  Email sent with an image that is on the Google Drive (Alternate flow)

    Given I am logged into my email account
    And I enter my friend's "<email>"
    And I attach an "<image>" from the cloud
    When I send the email
    Then I should receive a successful confirmation

    Examples:
      |                email                |      image       |
#      | jose.barreyrolopez@mail.mcgill.ca   |     confused.jpg       |
      | nicobarreyro@hotmail.com            |     shaking_toad.gif       |

#
#  Scenario Outline: Error Email sent with an image attachment (Error flow)
#
#    Given I am logged into my email account
#    And I enter my friend's "<email>"
#    And I attach an "<image>"
#    When I send the email
#    Then I should receive a successful confirmation
#
#    Examples:
#      |                email                |     image      |
#      | jose.barreyrolopez@mail.mcgill.ca   |   asdada   |
##      | nicobarreyro@hotmail.com            |   asdada    |


