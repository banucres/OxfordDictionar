# OxfordDictionary


prerequisite  
  Run this project in intellij Community IDE
  https://www.jetbrains.com/idea/download/#section=windows

Attached the video file for the API project.(need VLC player to run this video)
OxfordDictionary.webm  
  
  Dependency:
  Note : All the dependencies are in grouped in Pom file(maven)
   1. Cucumber    -BDD Test Framework
   2. RESTAssured -Restful API test Framework
   3. Junit -Unit test framework for java
   4. Yaml -Data Serialization
   5. lombok - Remove javas Verbosity
   6. Guava -Google guava common libraries
   7. Slf4j - Simple logging for java.
   
Test Execution:
  -ea
  -Dcucumber.options="--tags'@Positive'"
  

Questions: 
Instructions:
Please answer following questions and reply to the email that you have received with answers when you are done

You are an Automation Tester on an agile project. You have following stories in backlog. Please write automated tests for following user stories.
You may use any language, tool/framework to automate the user stories
Your solution should reflect your level of expertise
Please upload/share your solution to online source control repositories (e.g. GitHub, CodePlex)

Test User Story 1: 
Priority: Major
As a Product Owner of Oxford Dictionary
I want to get the meanings of English Words 
So that the user Retrieve dictionary information for a given word
Acceptance Criteria

User gets a response of 200 and is able to find the meanings of Valid words from the Retrieve dictionary information for a given word api
User responds with a 404 for invalid words that do not exist

API Details: 

https://developer.oxforddictionaries.com/documentation#!/Dictionary32entries/get_entries_source_lang_word_id


  
