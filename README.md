# React Native Push Notifications

[![npm version](https://badge.fury.io/js/react-native-push-notification.svg?update=9)](http://badge.fury.io/js/react-native-push-notification)
[![npm downloads](https://img.shields.io/npm/dm/react-native-push-notification.svg?update=9)](http://badge.fury.io/js/react-native-push-notification)

React Native Local and Remote Notifications for iOS and Android


## Supported React Native Versions

| Component Version | RN Versions          | README                                                                                                                 |
| ----------------- | -------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| **1.0.7**         | **<= 0.27**          | [Open](https://github.com/zo0r/react-native-push-notification/blob/f42723817f1687e0da23e6753eb8a9f0385b6ac5/README.md) |
| **1.0.8**         | **0.28**             | [Open](https://github.com/zo0r/react-native-push-notification/blob/2eafd1961273ca6a82ad4dd6514fbf1d1a829089/README.md) |
| **2.0.1**         | **0.29**             | [Open](https://github.com/zo0r/react-native-push-notification/blob/c7ab7cd84ea19e42047379aefaf568bb16a81936/README.md) |
| **2.0.2**         | **0.30, 0.31, 0.32** | [Open](https://github.com/zo0r/react-native-push-notification/blob/a0f7d44e904ba0b92933518e5bf6b444f1c90abb/README.md) |
| **>= 2.1.0**      | **>= 0.33**          | [Open](https://github.com/zo0r/react-native-push-notification/blob/a359e5c00954aa324136eaa9808333d6ca246171/README.md) |


## Changelog

Changelog is available from version 3.1.3 here: [Changelog](https://github.com/zo0r/react-native-push-notification/blob/master/CHANGELOG.md)


## Issues

Having a problem? Read the [troubleshooting](./trouble-shooting.md) guide before raising an issue.


## Pull Requests

[Please read...](./submitting-a-pull-request.md)


## Example app

Example folder contains an example app to demonstrate how to use this package. The notification Handling is done in `NotifService.js`. For Remote notifications, configure your SenderId in `app.json`. You can also edit it directly in the app.
To send Push notifications, you can use the online tool [PushWatch](https://www.pushwatch.com/gcm/).

Please test your PRs with this example app before submitting them. It'll help maintaining this repo.


## Installation

`npm install --save react-native-push-notification` or `yarn add react-native-push-notification`

Manualy linking for projects with RN version < 60 `react-native link react-native-push-notification`

**NOTE: For Android, you will still have to manually update the AndroidManifest.xml (as below) in order to use Scheduled Notifications.**

### iOS manual Installation

The component uses PushNotificationIOS for the iOS part.

[Please see: PushNotificationIOS](https://github.com/react-native-community/react-native-push-notification-ios)

### Android manual Installation

**NOTE: `play-service-gcm` and `firebase-messaging`, prior to version 15 requires to have the same version number in order to work correctly at build time and at run time. To use a specific version:**

In your `android/build.gradle`

```gradle
ext {
    googlePlayServicesVersion = "<Your play services version>" // default: "+"
    firebaseVersion = "<Your Firebase version>" // default: "+"

    // Other settings
    compileSdkVersion = <Your compile SDK version> // default: 23
    buildToolsVersion = "<Your build tools version>" // default: "23.0.1"
    targetSdkVersion = <Your target SDK version> // default: 23
    supportLibVersion = "<Your support lib version>" // default: 23.1.1
}
```

**NOTE: localNotification() works without changes in the application part, while localNotificationSchedule() only works with these changes:**

In your `AndroidManifest.xml`

```xml
    .....
    <!-- < Only if you're using GCM or localNotificationSchedule() > -->
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <permission
        android:name="${applicationId}.permission.C2D_MESSAGE"
        android:protectionLevel="signature" />
    <uses-permission android:name="${applicationId}.permission.C2D_MESSAGE" />
    <!-- </ Only if you're using GCM or localNotificationSchedule() > -->

    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>

    <application ....>
        <meta-data  android:name="com.dieam.reactnativepushnotification.notification_channel_name"
                android:value="YOUR NOTIFICATION CHANNEL NAME"/>
        <meta-data  android:name="com.dieam.reactnativepushnotification.notification_channel_description"
                    android:value="YOUR NOTIFICATION CHANNEL DESCRIPTION"/>
        <!-- Change the resource name to your App's accent color - or any other color you want -->
        <meta-data  android:name="com.dieam.reactnativepushnotification.notification_color"
                    android:resource="@android:color/white"/>

        <!-- < Only if you're using GCM or localNotificationSchedule() > -->
        <receiver
            android:name="com.google.android.gms.gcm.GcmReceiver"
            android:exported="true"
            android:permission="com.google.android.c2dm.permission.SEND" >
            <intent-filter>
                <action android:name="com.google.android.c2dm.intent.RECEIVE" />
                <category android:name="${applicationId}" />
            </intent-filter>
        </receiver>
        <!-- </ Only if you're using GCM or localNotificationSchedule() > -->

        <receiver android:name="com.dieam.reactnativepushnotification.modules.RNPushNotificationPublisher" />
        <receiver android:name="com.dieam.reactnativepushnotification.modules.RNPushNotificationBootEventReceiver">
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED" />
            </intent-filter>
        </receiver>

        <!-- < Only if you have notification actions > -->
        <receiver android:name="com.dieam.reactnativepushnotification.modules.RNPushNotificationActionReceiver" />
        <service android:name="com.dieam.reactnativepushnotification.modules.RNPushNotificationActionService" />
        <!-- </ Only if you have notification actions >  -->

        <service android:name="com.dieam.reactnativepushnotification.modules.RNPushNotificationRegistrationService"/>

        <!-- < Only if you're using GCM or localNotificationSchedule() > -->
        <service
            android:name="com.dieam.reactnativepushnotification.modules.RNPushNotificationListenerServiceGcm"
            android:exported="false" >
            <intent-filter>
                <action android:name="com.google.android.c2dm.intent.RECEIVE" />
            </intent-filter>
        </service>
        <!-- </ Only if you're using GCM or localNotificationSchedule() > -->

        <!-- < Else > -->
        <service
            android:name="com.dieam.reactnativepushnotification.modules.RNPushNotificationListenerService"
            android:exported="false" >
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>
        <!-- </Else> -->
     .....
```

In `android/app/src/res/values/colors.xml` (Create the file if it doesn't exist).

```xml
<resources>
    <color name="white">#FFF</color>
</resources>
```

## Methods

- [React Native Push Notifications](#react-native-push-notifications)
  - [Supported React Native Versions](#supported-react-native-versions)
  - [Changelog](#changelog)
  - [Issues](#issues)
  - [Pull Requests](#pull-requests)
  - [Example app](#example-app)
  - [Installation](#installation)
    - [iOS manual Installation](#ios-manual-installation)
    - [Android manual Installation](#android-manual-installation)
  - [Methods](#methods)
    - [PushNotification.configure](#pushnotificationconfigure)
    - [PushNotification.localNotification](#pushnotificationlocalnotification)
      - [Usage](#usage)
      - [Notification properties](#notification-properties)
        - [Android only](#android-only)
        - [iOS only](#ios-only)
        - [Android & iOS](#android--ios)
      - [Custom sounds](#custom-sounds)
      - [Notification priority](#notification-priority)
      - [Notification visibility](#notification-visibility)
      - [Notification importance](#notification-importance)
      - [Repeating Notifications](#repeating-notifications)
    - [PushNotification.localNotificationSchedule](#pushnotificationlocalnotificationschedule)
      - [Usage](#usage-1)
    - [PushNotification.cancelLocalNotifications](#pushnotificationcancellocalnotifications)
      - [Android](#android)
      - [iOS](#ios)
    - [PushNotification.cancelAllLocalNotifications](#pushnotificationcancelalllocalnotifications)
    - [PushNotification.setApplicationIconBadgeNumber](#pushnotificationsetapplicationiconbadgenumber)
    - [PushNotification.checkPermissions](#pushnotificationcheckpermissions)
  - [Android Only Methods](#android-only-methods)
    - [iOS Only Methods](#ios-only-methods)
  - [Sending Notification Data From Server](#sending-notification-data-from-server)
  - [Notification Actions](#notification-actions)
    - [0) Enable action receiver and service in AndroidManifest.xml](#0-enable-action-receiver-and-service-in-androidmanifestxml)
    - [1) Specify notification actions for a notification](#1-specify-notification-actions-for-a-notification)
    - [2) Specify handlers for the notification actions](#2-specify-handlers-for-the-notification-actions)
      - [When application is in the foreground](#when-application-is-in-the-foreground)
    - [When application is in the background or it is closed](#when-application-is-in-the-background-or-it-is-closed)


### PushNotification.configure

```js
import PushNotification from 'react-native-push-notification';

PushNotification.configure({
  // (optional) Called when Token is generated
  onRegister: function(token) {
    console.log("TOKEN:", token);
  },

  // (required) Called when a remote or local notification is opened or received
  onNotification: function(notification) {
    console.log("NOTIFICATION:", notification);

    // process the notification

    // required on iOS only (see fetchCompletionHandler docs: https://github.com/react-native-community/react-native-push-notification-ios)
    notification.finish(PushNotificationIOS.FetchResult.NoData);
  },

  // ONLY Android: GCM or FCM Sender ID (product_number) (optional) - not required for local notifications, but is need to receive remote push notifications)
  senderID: "YOUR GCM (OR FCM) SENDER ID",

  // ONLY iOS: (optional) default: all - Permissions to register.
  permissions: {
    alert: true,
    badge: true,
    sound: true
  },

  // Should the initial notification be popped automatically
  // default: true
  popInitialNotification: true,

  /**
   * (optional) default: true
   * - Specified if permissions (ios) and token (android and ios) will requested or not,
   * - if not, you must call PushNotificationsHandler.requestPermissions() later
   */
  requestPermissions: true
});
```

When any notification is opened or received the callback `onNotification` is called passing an object with the notification data.

Notification object example:

```js
{
    foreground: false, // BOOLEAN: If the notification was received in foreground or not
    userInteraction: false, // BOOLEAN: If the notification was opened by the user from the notification area or not
    message: 'My Notification Message', // STRING: The notification message
    data: {}, // OBJECT: The push data
}
```


### PushNotification.localNotification

#### Usage

```js
PushNotification.localNotification({
  message: 'My Notification Message',
  playSound: false,
  vibrate: false,
});
```

#### Notification properties

##### Android only

| key | description | type | default |
| --- | ----------- | ---- | ------- |
| id | (optional) Valid unique 32 bit integer specified as string. | string | Autogenerated ID |
| ticker | (optional) | string | |
| autoCancel | (optional) Should notification be dismissed on open or action press | boolean | `true` |
| largeIcon | (optional) Local resource or url image to use in notification body | string | `"ic_launcher"` |
| smallIcon | (optional) Notification icon | string | `"ic_notification"` with fallback to `"ic_launcher"` |
| bigText | (optional) A text shown when the notification is expanded | string | defaults to "message" key |
| subText | (optional) A subtext | string | |
| color | (optional) Background notification color | string | system default |
| vibrate | (optional) | boolean | `true` |
| vibration | (optional) Vibration duration (milliseconds). Ignored if `vibrate: true` | number | `1000` |
| tag | (optional) Notification tag | string | |
| group | (optional) Notification group | string | |
| ongoing | (optional) Set whether this is an "ongoing" notification | boolean | `false` |
| [priority](#notification-priority) | (optional) Set priority | string | `high` |
| [visibility](#notification-visibility) | (optional) Set visibility | string | `private` |
| [importance](#notification-importance) | (optional) Set channel importance | string | `high` |
| actions | (optional) Set notification actions. Must be in stringified JSON array format eg. `["Yes","No"]`. See [actions docs](#notification-actions) | string | |

##### iOS only

| key | description | type | default |
| --- | ----------- | ---- | ------- |
| alertAction | (optional) | | view |
| category | (optional) | | `null` |
| userInfo | (optional) Object containing additional notification data | object | `null` |

##### Android & iOS

| key | description | type | default |
| --- | ----------- | ---- | ------- |
| title | (optional) Notification title | string | |
| message | (required) Notification message | string | |
| playSound | (optional) | boolean | `true` |
| [soundName](#custom-sounds) | (optional) Sound to play when the notification is shown. Value of `"default"` plays the default sound. It can be set to a custom sound such as `"android.resource://com.xyz/raw/my_sound"`. It will look for the 'my_sound' audio file in 'res/raw' directory and play it. | string | `"default"` |
| number | (optional) Valid 32 bit integer specified as string. | string | |
| [repeatType](#repeating-notifications) | (optional) Repeating interval. One of: `month`, `week`, `day`, `hour`, `minute`, `time`. | string | |
| repeatTime | (optional) Only if `repeatType: 'time'`. The number of milliseconds between each interval. Must be > 0. | number | |
| fireDate | (optional) Only with `repeatType`. When to fire notification | number | `0` |

#### Custom sounds

In android, add your custom sound file to `[project_root]/android/app/src/main/res/raw`

In iOS, add your custom sound file to the project `Resources` in xCode.

In the location notification json specify the full file name:
```js
soundName: 'my_sound.mp3'
```

#### Notification priority

(optional) Specify `priority` to set priority of notification. Default value: `"high"`

Available options:

"max" = NotficationCompat.PRIORITY_MAX  
"high" = NotficationCompat.PRIORITY_HIGH  
"low" = NotficationCompat.PRIORITY_LOW  
"min" = NotficationCompat.PRIORITY_MIN  
"default" = NotficationCompat.PRIORITY_DEFAULT

More information: https://developer.android.com/reference/android/app/Notification.html#PRIORITY_DEFAULT

#### Notification visibility

(optional) Specify `visibility` to set visibility of notification. Default value: `"private"`

Available options:

"private" = NotficationCompat.VISIBILITY_PRIVATE  
"public" = NotficationCompat.VISIBILITY_PUBLIC  
"secret" = NotficationCompat.VISIBILITY_SECRET

More information: https://developer.android.com/reference/android/app/Notification.html#VISIBILITY_PRIVATE

#### Notification importance

(optional) Specify `importance` to set importance of notification. Default value: `"high"`

Available options:

"default" = NotificationManager.IMPORTANCE_DEFAULT  
"max" = NotificationManager.IMPORTANCE_MAX  
"high" = NotificationManager.IMPORTANCE_HIGH  
"low" = NotificationManager.IMPORTANCE_LOW  
"min" = NotificationManager.IMPORTANCE_MIN  
"none" = NotificationManager.IMPORTANCE_NONE  
"unspecified" = NotificationManager.IMPORTANCE_UNSPECIFIED

More information: https://developer.android.com/reference/android/app/NotificationManager#IMPORTANCE_DEFAULT

#### Repeating Notifications

(optional) Specify `repeatType` and optionally `repeatTime` while scheduling the local notification. Check the local notification example above.

Property `repeatType` could be one of `month`, `week`, `day`, `hour`, `minute`, `time`. If specified as time, it should be accompanied by one more parameter `repeatTime` which should the number of milliseconds between each interval.

### PushNotification.localNotificationSchedule

[Configuration properies](#configuration-properties) are the same as for [localNotification](#notification-properties).

#### Usage
```js
PushNotification.localNotificationSchedule({
  //... You can use all the options from localNotifications
  message: 'My Notification Message', // (required)
  date: new Date(Date.now() + 60 * 1000) // in 60 secs
});
```

### PushNotification.cancelLocalNotifications

#### Android

The `id` parameter for `PushNotification.localNotification` is required for this operation. The id supplied will then be used for the cancel operation.

```js
// Android
PushNotification.localNotification({
    ...
    id: '123'
    ...
});
PushNotification.cancelLocalNotifications({ id: '123' });
```

#### iOS

The `userInfo` parameter for `PushNotification.localNotification` is required for this operation and must contain an `id` parameter. The id supplied will then be used for the cancel operation.

```js
// iOS
PushNotification.localNotification({
    ...
    userInfo: { id: '123' }
    ...
});
PushNotification.cancelLocalNotifications({ id: '123' });
```

### PushNotification.cancelAllLocalNotifications

Cancels all scheduled notifications AND clears the notifications alerts that are in the notification centre.

_NOTE: there is currently no api for removing specific notification alerts from the notification centre._


### PushNotification.setApplicationIconBadgeNumber

`PushNotification.setApplicationIconBadgeNumber(number: number)`

Works natively in iOS.

Uses the [ShortcutBadger](https://github.com/leolin310148/ShortcutBadger) on Android, and as such will not work on all Android devices.

### PushNotification.checkPermissions

`PushNotification.checkPermissions(callback: Function)` Check permissions

`callback` will be invoked with a `permissions` object:

- `alert`: boolean
- `badge`: boolean
- `sound`: boolean

## Android Only Methods

`PushNotification.subscribeToTopic(topic: string)` Subscribe to a topic (works only with Firebase)

### iOS Only Methods

`PushNotification.getApplicationIconBadgeNumber(callback: Function)` Get badge number


## Sending Notification Data From Server

Same parameters as `PushNotification.localNotification()`

## Notification Actions

(For iOS) You can use this [package](https://github.com/holmesal/react-native-ios-notification-actions) to add notification actions.

(Android only)

Two things are required to setup notification actions:

### 0) Enable action receiver and service in AndroidManifest.xml

```xml
<application>
  ...

  <receiver android:name="com.dieam.reactnativepushnotification.modules.RNPushNotificationActionReceiver" />
  <service android:name="com.dieam.reactnativepushnotification.modules.RNPushNotificationActionService" />
</application>
```

### 1) Specify notification actions for a notification

This is done by specifying an `actions` parameters while configuring the local notification. This is an array of strings where each string is a notification action that will be presented with the notification.

For e.g. `actions: '["Accept", "Reject"]' // Must be in string format`

The array itself is specified in string format to circumvent some problems because of the way JSON arrays are handled by react-native android bridge.

### 2) Specify handlers for the notification actions

#### When application is in the foreground

```js
const listener = DeviceEventEmitter.addListener('notificationActionReceived', action => {
  const notification = JSON.parse(action.dataJSON);
  // Handle action
  // notification.action will tell you which action user pressed
});
```

### When application is in the background or it is closed

You need to register a `headlessTask` that will handle your notification action.

```js
// index.js - the same file where you register your App component

// Register a notification action handler
// It is used to handle notification actions when the app is closed or in background
AppRegistry.registerHeadlessTask(
  'RNPushNotificationActionHandler', // Do not change
  () => require('./services/PushNotificationService').default, // Your handler
);
```

```js
// PushNotificationService.js or any file you want to use for handling notification action

export default data => {
  // Handle action
  // notification.action will tell you which action user pressed
};
```
