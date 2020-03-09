# cordova-hms-map-kit
A cordova plugin to use the following [Huawei Map Kit](https://developer.huawei.com/consumer/en/hms/huawei-MapKit)

[demo](https://github.com/ikamaru/ionic-hmsMapKitDemo)

### Getting Started
Create a new ionic project
```
ionic start ionic-hmsMapKitDemo blank --type=angular && cd ionic-hmsMapKitDemo
```
Add the android platform to your project
```
ionic cordova platform add android
```

### Preparation
* Generate a keystore file 
```
keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
```
* Change the keystore info here **build.json**
* Sign in to [HUAWEI Developer](https://developer.huawei.com/consumer/en/console) then create a new app in HUAWEI AppGallery > My Apps.
* Run the following keytool command and obtain the SHA-256 fingerprint from the result
```
keytool -list -v -keystore my-release-key.keystore
```
* In AppGallery Connect, click the app that you have created and go to Develop -> Overview -> App information , then Insert the SHA-256 
* Download the **agconnect-services.json** file and put it in the root directory of your ionic project

### Add the plugin
* Add the plugin to your project
```
ionic cordova plugin add https://github.com/ikamaru/cordova-hms-map-kit
```
* Check [the plugin's doc](https://github.com/ikamaru/cordova-hms-map-kit#readme) or this [demo](https://github.com/ikamaru/ionic-hmsMapKitDemo) and enjoy your coding

These preparation steps are base on these [Preparations for Integrating HUAWEI HMS Core](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#0) (I tried to choose what we will need in our ionic project and automate the other steps inside the plugin)

### Build the APK
* Don't forget to build the ionic project using the same keystore file you generated for the AppGallery connect

Build the APK release according to the build configuration file **build.json**
```
ionic cordova build android --buildConfig=build.json --release
```
Plug your android phone into the computer's USB port and install the apk
```
adb install "platforms\android\app\build\outputs\apk\release\app-release.apk"
```
### Documentation
* cordova.plugins.HMSMapKit.loadMapWithMarkers(jsonObject,successCallback,failCallback)

This will display a map with markers as described in the JSON file 「markersJson」
```
var markersJson=
{
	markers:[
	{
		lat:"33.622981",	//required
		lng:"-7.477735",	//required
		title:"kokoro",		//optional
		snippet:"bokuNoIe"	//optional
	},
	//...
	]
};
cordova.plugins.HMSMapKit.loadMapWithMarkers(
			markersJson,
			(msg) => {
			    console.log(msg);
			    loading.dismiss();
			},(err) => {
			    console.log(err);
			    loading.dismiss();
			});
```
* cordova.plugins.HMSPushKit.isHMSAvailable(successCallback,failCallback)
```
cordova.plugins.HMSMapKit.isHMSAvailable(
			(msg) => {
			    //HMS exists
			},(err) => {
			    //HMS doesn't exists
			});
```
* cordova.plugins.HMSPushKit.isGMSAvailable(successCallback,failCallback)
```
cordova.plugins.HMSMapKit.isGMSAvailable(
			(msg) => {
			    //GMS exists
			},(err) => {
			    //GMS doesn't exists
			});
```
