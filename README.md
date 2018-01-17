#Coding Challenge

##Summary

Welcome to your Dexcom Challenge.

We would like you to create a small mobile application, to retrieve weather data and present it to the user.

##App Requirements

* The app can be phone only (no need for tablet), and it should work on different device sizes
* Use the darksky weather API as described below, to get the weather data
* Use San Diego as the location
* Show at least 5 days, including the current day, in a table or list view. This view should be scrollable
* Each day should display the temperature and condition (eg. clear, sunny, raining, etc.) The condition can be stated as text, but you'll get bonus points for displaying an icon
* Tapping on a day should push a new view onto the view stack. The new view should contain at least one piece of data for the day that was not available in the list view (such as humidity, windspeed, etc.) Don't worry about spending time on the UI for this screen, it can just be a label
* The user should be able to navigate back to the list view
* The user should be able to refresh the list view. How you do this is up to you, for example it could be a button, or you could use pull down to refresh
* UI does not need to be polished, it can be a wire frame, but it should be thoughtfully layed out

##Other Requirements

* Create a personal fork of this repo on Bitbucket
* Make your repo private (Do NOT make it public)
* Please code the application in Java or Kotlin
* Create a new project for your work in Android Studio
* Make your commits to the develop branch and merge it to the master branch before submitting your solution
* Unit tests are required

##How You Will Be Graded

* App and Other requirements met
* App Compiles and Runs
    * Please provide instructions if it can't build directly after cloning, for example, if a script has to run
* Use of suitable design patterns, for example, separation of concerns
* Best practices regarding commits and git usage
* API calls do not block the user experience
* Unit tests do not have to be extensive, but make sure you include a couple. Don't worry, we're not measuring code coverage, just the way your tests are designed
* Only the happy path will be judged. Error handling is not required, but it would be nice if there was some indication of a failure - eg. in the case there is no internet connection, we won't think your app is broken
* Use of frameworks, libraries, and open-source code is allowed, but please reference their use in comments in the code. Please use package management for open source dependencies where suitable. 

##Getting Started
* Clone this repo and create a personal fork
* Register for a free API key for darksky.net
* The API to query is https://api.darksky.net/forecast/{Your_API_key_here}/{latitude},{longitude} | San Diego lat & lon: 32.715736, -117.161087
 

When complete, please submit a pull request to the original repo. Since we won't actually merge it, provide read access of your forked repo to:  

* **hsponberg**
* **ahmettekik**
* **amyllykoski**
