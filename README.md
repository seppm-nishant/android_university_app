# android_university_app

How to run the Project :
1st step is that you have to Clone the Project
After Git Cloning you have to set SDK Android Environment 

The Project Consists of : 
activities : 
MainActivity - It is proxy to SearchActivity(We can use it for multiple usecases in future)
SearchActivity - This is the main activity where it fetch data from api and update into database.
WebActivity - This loads web view.

services:
ForegroundService - This service is responsible for foreground activity(it fetches data from api and save into database when app is not active).

data:
MyDbHandler - It is responsible for all the database functions.

We use RecycleView for showing app data and data is refreshed in every 10 seconds.


