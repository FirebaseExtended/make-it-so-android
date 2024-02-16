

https://user-images.githubusercontent.com/14080129/165527590-2f14b123-0d1c-482c-a485-0ebb5cb3139d.mp4

## Make it So

This is a sample Android app that demonstrates how to use Firebase Authentication, Crashlytics, Cloud Firestore, Performance Monitoring and Remote Config with Jetpack Compose UI. Make it So is a simple to-do list application that allows the user to add and edit to-do items, add flags, priorities, due dates, and mark the tasks as completed.

To explain how each product above was used in the code, we've written a series of articles that delve into creating this app from scratch. Navigate to [part 1](https://firebase.blog/posts/2022/04/building-an-app-android-jetpack-compose-firebase) of this series to understand how this app is structured. There you will find the links to all articles in the series.

## Setting up

In order for this app to work, you will need to create a [Firebase project](https://firebase.google.com/):

* Clone this repository
* Create a Firebase project in the [Firebase Console](https://console.firebase.google.com/)
* Follow [these steps](https://firebase.google.com/docs/android/setup#register-app) to register Make it So app in your Firebase project
* Follow [these steps](https://firebase.google.com/docs/android/setup#add-config-file) to add the Firebase Android configuration file to Make it So
* [Create a Cloud Firestore database](https://firebase.google.com/docs/firestore/quickstart#create) in your Firebase project
* [Enable Anonymous Authentication](https://firebase.google.com/docs/auth/android/anonymous-auth#before-you-begin) in your Firebase project
* [Enable Email/Password Authentication](https://firebase.google.com/docs/auth/android/password-auth#before_you_begin) in your Firebase project
* Run the app using Android Studio Flamingo+ on a device/emulator with API level 21 or above
* Create your first to-do item, then open the Firestore dashboard on the Firebase console
* Navigate to the Indexes tab and create a new index with the following configuration:
  
<img width="691" alt="index-config" src="https://github.com/FirebaseExtended/make-it-so-android/assets/14080129/256ff875-f0d8-41d5-b1ab-8fe2e2a18a4a">

This is necessary when fetching documents because this app uses `where` and `orderBy` operators in different fields: `where` uses the `userId` field and `orderBy` uses the `createdAt` field (see `StorageServiceImpl`).

## Contact

Please navigate to the [discussions page](https://github.com/FirebaseExtended/make-it-so-android/discussions/) if you have any questions or want to suggest changes to the Make it So app.
