# Android-SGPSI
SGPSI

A mobile app (Android) to get the SG PSI value and display it on screen.
This is the demo application.

Document and Application set up - 1 hr.

REQ01: Develop a mobile app (Android) to get the SG PSI value and display it on screen.
- Done.

REQ02: The PSI value can retrieved from the following API & Developer should register to the portal and get the
API key to query for the API.
- Done. - 30 mins.

REQ03: The API key & other sensitive information should be encrypted and stored securely in the app.
- Done. - 45 mins.
- Api key is stored in JNI in c class to secure it from threats.

REQ04: The mobile app should have a capability to allow user to refresh the latest PSI value from the web.
- Done - 10 mins.

REQ05: The mobile app should store the query PSI result locally, so that user will have the last stored PSI value even though there is no internet connection / web service is not available
- Done - 45 mins.
- For time being saved JSON objects in Shared preferences in Private mode, so that outside app, it wont be accessed. Permanent solution would be implementation of SQLite Db.

REQ06: The mobile app should display the PSI value according to region.
- Done - 30 mins.

REQ07: The mobile app should display the following PSI value.
- Done - 30 mins.

REQ08: Below are the basic mockup of the UI, feel free to use it as a reference or improve it.
- Basic UI completed - 1hr.