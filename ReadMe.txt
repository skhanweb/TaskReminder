
Project Name: Remind Me

Description: 

This is a reminder app in which first of all there is a sign in activity. A user can sign in with his/her login id if there is no account of that user then first, he will create it by going in sign up activity. 
Then there is a category fragment in which a user can view all categories in grid form and he can create a new category through navigation drawer.
Each category have its task and each task has its description.
 When adding a task in list, the following essentials must be necessary:
Task name
Its Description
Category in which task lie
Date and time to alert.

When date time of task matches system time then there is an alert through alarm manager. a service is there that checks each task date and time continuoutly after every 60 seconds. All these tasks and their categories store in real time database that is called Firebase.

There is another feature in this app that is opens description of each task and a user can also delete it.