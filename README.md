## This program was created as the end project of Haaga Helia's Server Programming course

The app has a frontend created with thymeleaf but the backend provides an API-service as well so in theory any technology could be used to create a frontend.
The idea of the app is to allow an user to save their gym workouts and keep track what they have done in the past and some analytical information about themselves,
such as Wilks score which is calculated by person's weight, squat weight, bench press weight and deadlift weight to more equally compare to people who are in 
different weight classes.

Heroku link: https://powerliftingvault.herokuapp.com/login

# Technologies utilized
-Java Spring Backend
-PostgreSQL database
-Thymeleaf frontend

# Known bugs
-For some reason the server throws 404 after first login when the app has been in hibernation on Heroku. Login in again should fix it.
