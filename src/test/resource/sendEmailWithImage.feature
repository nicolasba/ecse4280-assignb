Feature: Send an email with an image attachment
  As an Outlook user with a valid email account
  I would like to send an email with an image attachment
  So that the recipient I send it to can see it.

#  Scenario Outline: Email sent with an image stored locally (Normal flow)
#
#    Given I am logged into my email account
#    And I enter my friend's "<email>"
#    And I attach an "<image>" from my computer
#    When I send the email
#    Then I should be able to verify that the email was successfully sent
#
#    Examples:
#      |                email                |     image        |
#      | jose.barreyrolopez@mail.mcgill.ca   |   confused.jpg   |
#      | nicobarreyro@hotmail.com            |   confused.jpg   |
#      | nicobarreyro@hotmail.com            |   train.jpg      |
#      | erion.hysa@mail.mcgill.ca           |   flags.jpg      |
#      | nicobarreyro@hotmail.com            |   office.jpg     |
#
#  Scenario Outline: Email sent with an image that is on the cloud (OneDrive) (Alternate flow)
#
#    Given I am logged into my email account
#    And I enter my friend's "<email>"
#    And I attach an "<image>" from the cloud
#    When I send the email
#    Then I should be able to verify that the email was successfully sent
#
#    Examples:
#      |                email                |     image        |
#      | jose.barreyrolopez@mail.mcgill.ca   |   confused.jpg   |
#      | nicobarreyro@hotmail.com            |   confused.jpg   |
#      | nicobarreyro@hotmail.com            |   train.jpg      |
#      | erion.hysa@mail.mcgill.ca           |   flags.jpg      |
#      | nicobarreyro@hotmail.com            |   office.jpg     |

#
  Scenario Outline: Email sent without a recipient email (Error flow)

    Given I am logged into my email account
    And I attach an "<image>" from my computer
    When I send the email
    Then I should be warned that the email could not be sent

    Examples:
      |     image        |
      |   confused.jpg   |
      |   flags.jpg      |
      |   office.jpg     |


