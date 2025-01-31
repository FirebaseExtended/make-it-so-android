

https://user-images.githubusercontent.com/14080129/165527590-2f14b123-0d1c-482c-a485-0ebb5cb3139d.mp4

## Make it So

This is a sample Android app that demonstrates how to use Firebase Authentication, Crashlytics, Cloud Firestore, Performance Monitoring and Remote Config with Jetpack Compose UI. Make it So is a simple to-do list application that allows the user to add and edit to-do items, add flags, priorities, due dates, and mark the tasks as completed.

To explain how each product above was used in the code, we've written a series of articles that delve into creating this app from scratch. Navigate to [part 1](https://firebase.blog/posts/2022/04/building-an-app-android-jetpack-compose-firebase) of this series to understand how this app is structured. There you will find the links to all articles in the series.

## Setting up

In order for this app to work, you will need to create a [Firebase project](https://firebase.google.com/):

1. Clone this repository
1. Create a Firebase project in the [Firebase Console](https://console.firebase.google.com/)
1. Follow [these steps](https://firebase.google.com/docs/android/setup#register-app) to register Make it So app in your Firebase project
1. Follow [these steps](https://firebase.google.com/docs/android/setup#add-config-file) to add the Firebase Android configuration file to Make it So
1. [Create a Cloud Firestore database](https://firebase.google.com/docs/firestore/quickstart#create) in your Firebase project
1. [Enable Anonymous Authentication](https://firebase.google.com/docs/auth/android/anonymous-auth#before-you-begin) in your Firebase project
1. [Enable Email/Password Authentication](https://firebase.google.com/docs/auth/android/password-auth#before_you_begin) in your Firebase project
1. Run the app using Android Studio Flamingo+ on a device/emulator with API level 21 or above
1. Create your first to-do item in the app
1. In the Firebase console, navigate to the [Firestore Indexes tab](https://console.firebase.google.com/project/_/firestore/indexes)
1. Create a new composite index for the collection `tasks` with 2 fields: `userId` and `createdAt` (both Ascending)
1. Choose the "Collection" option in Query scopes (you won't be using `collectionGroup` in this app)

This index is necessary when fetching documents because this app uses `where` and `orderBy` operators in different fields: `where` uses the `userId` field and `orderBy` uses the `createdAt` field (see `StorageServiceImpl`). Learn more on the Firebase documentation about [Index types](https://firebase.google.com/docs/firestore/query-data/index-overview#composite_indexes).
