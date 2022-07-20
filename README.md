

https://user-images.githubusercontent.com/14080129/165527590-2f14b123-0d1c-482c-a485-0ebb5cb3139d.mp4

## Make it So

This is a sample Android app that demonstrates how to use Firebase Authentication, Crashlytics, Cloud Firestore and Hilt with Jetpack Compose UI. Make it So is a simple to-do list application that allows the user to add and edit to-do items, add flags, priorities, due dates, and mark the tasks as completed.

To explain how each product above was used in the code, we've written a series of articles that delve into creating this app from scratch. [Part 1](https://firebase.blog/posts/2022/04/building-an-app-android-jetpack-compose-firebase) of this series has an overview of what you can do with this app and how it is structured. [Part 2](https://firebase.blog/posts/2022/05/adding-firebase-auth-to-jetpack-compose-app) shows how to implement login and sign up flows using Firebase Authentication. [Part 3](https://firebase.blog/posts/2022/06/adding-crashlytics-to-jetpack-compose-app) covers how to add Firebase Crashlytics to handle crashes and display messages to the user. [Part 4](https://firebase.blog/posts/2022/07/adding-cloud-firestore-to-jetpack-compose-app) is all about Cloud Firestore: it covers how to listen to changes on the stored items and update the UI based on these events.

## Setting up

In order for this app to work, you will need to create a [Firebase project](https://firebase.google.com/):

* Clone this repository
* Create a Firebase project in the [Firebase Console](https://console.firebase.google.com/)
* Follow [these steps](https://firebase.google.com/docs/android/setup#register-app) to register Make it So app in your Firebase project
* Follow [these steps](https://firebase.google.com/docs/android/setup#add-config-file) to add the Firebase Android configuration file to Make it So
* [Create a Cloud Firestore database](https://firebase.google.com/docs/firestore/quickstart#create) in your Firebase project
* [Enable Anonymous Authentication](https://firebase.google.com/docs/auth/android/anonymous-auth#before-you-begin) in your Firebase project
* [Enable Email/Password Authentication](https://firebase.google.com/docs/auth/android/password-auth#before_you_begin) in your Firebase project
* Run Make it So

## Contact

Twitter: [@coelho_dev](https://twitter.com/coelho_dev)
