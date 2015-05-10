# Panic
Wearhacks Toronto 2015 Hackathon submission

==========================================================
#Frontend Setup
If you are working on the frontend, make sure to run 

```
bower install
sudo npm insall
```
and then `grunt serve` to monitor changes made.

#Android App
##API
This package contains the class' and interfaces needed to send and receive information from the cloud.

##SMS Sending
This package contains the needed files inorder to deal with the tasks involved with sending textmessages.
It has been setup extremely simple.
All you need to do is declare `SmsService message = new SmsService();` 
and then `message.sendTextMessage("1234567890", "Hi.");` to send a text message where the first parameter is String number and the second parameter String message.

##Individual Files
###AudioRecording.java
Contains all the information needed by the program to record and save audio to the device.

###Home.java
Contains code to control the myo - such as the gesture to activate the panic - as well as the commands to communicate with our API.
