# Ti.Android.Admob ![](http://gitt.io/badge.svg)
Allows for the display of AdMob in Titanium Android applications.

Please note that if your androidManifest has screen support set to: **android:anyDensity="false"**, any banner ads will 
display too small on high density devices.
It is not clear at this point if this is a bug with AdMob or Titanium.
In any event, you will either need to NOT set your screen support -- or set android:anyDensity="true" and adjust your app layout accordingly

## Getting Started

View the [Using Titanium Modules](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_Titanium_Modules) document 
for instructions on getting started with using this module in your application.

## Requirements

For Ti.Android.Admob [5.1.5](https://github.com/deckameron/Ti.Android.Admob/blob/master/android/dist/ti.android.admob-android-5.1.5.zip) 
- [x] Titanium SDK 7.0.0+
- [x] [Ti.PlayServices](https://github.com/appcelerator-modules/ti.playservices) 16.1.3 module

For Ti.Android.Admob [6.0.3](https://github.com/deckameron/Ti.Android.Admob/blob/master/android/dist/ti.android.admob-android-6.0.3.zip)
- [x] Titanium SDK 9.0.0+

## Doubleclick for Publishers Developer Docs

<https://developers.google.com/mobile-ads-sdk/>

All AdViews, except Rewarded, have the parameters **_keyword_** and **_contentUrl_** and can be used with DFP mapping

## Download
You can get it [here](https://github.com/deckameron/Ti.Android.Admob/blob/master/android/dist/ti.android.admob-android-4.2.0.zip?raw=true)

## How to use it

First you need to require the module
```javascript
var admob = require("ti.android.admob");
```

It is recommended to get users locations before loading any ads in order the show a personalized Ads
```javascript
if (!Titanium.Geolocation.hasLocationPermissions(Titanium.Geolocation.AUTHORIZATION_ALWAYS)) {
	Titanium.API.warn('Location permissions not granted! Asking now...');
	Titanium.Geolocation.requestLocationPermissions(Titanium.Geolocation.AUTHORIZATION_ALWAYS, function(e){
		if (!e.success) {
			Titanium.API.error('Location permissions declined!');
		} else {
			Titanium.API.info('Location permissions ready');
			LoadAds();
		}
	});
} else {
	Titanium.API.warn('Location permissions already granted!');
	LoadAds();
}
```

# STANDARD ADVIEWS
### Supported AdView Sizes

|Types                |Description                          |
|----------------|-------------------------------|
|_ADAPTATIVE_BANNER_			|Drop-in replacement for the industry standard 320x50 banner size, as well as the smart banner format they supersede.
|_BANNER_			|Mobile Marketing Association (MMA) banner ad size (320x50 density-independent pixels).                      
|_LARGE_BANNER_    	|Large banner ad size (320x100 density-independent pixels).              
|_SMART_BANNER_    |A dynamically sized banner that is full-width and auto-height.
|_MEDIUM_RECTANGLE_         |Interactive Advertising Bureau (IAB) medium rectangle ad size (300x250 density-independent pixels).
|_FULLBANNER_         |Interactive Advertising Bureau (IAB) full banner ad size (468x60 density-independent pixels).
|_LEADERBOARD_         |Interactive Advertising Bureau (IAB) leaderboard ad size (728x90 density-independent pixels).
|_FLUID_         |A dynamically sized banner that matches its parent's width and expands/contracts its height to match the ad's content after loading completes.
|_WIDE_SKYSCRAPER_         |IAB wide skyscraper ad size (160x600 density-independent pixels).
|_SEARCH_         |A special variant of FLUID to be set on [SearchAdView](https://developers.google.com/android/reference/com/google/android/gms/ads/search/SearchAdView.html) when loading a [DynamicHeightSearchAdRequest](https://developers.google.com/android/reference/com/google/android/gms/ads/search/DynamicHeightSearchAdRequest.html)`.

```javascript
var adView = Admob.createView({
	bottom : 0,
	//keyword : "titanium",
	//contentUrl : "www.myur.com",
	extras : {
    	'npa' : '1' //Disable personalized ads
    }
	viewType : Admob.TYPE_ADS,
	adSizeType: Admob.ADAPTATIVE_BANNER,
	testDeviceId : "G9CCEHKYF95FFR8152FX50D059DC8336", //USE YOUR DEVICE ID HERE
	adUnitId: 'ca-app-pub-3940256099942544/6300978111', //USE YOUR AD_UNIT ID HERE
});	
window.add(adView);	

adView.addEventListener(Admob.AD_RECEIVED, function(e) {
	Titanium.API.info("Ad received");
});

adView.addEventListener(Admob.AD_NOT_RECEIVED, function(e) {
	Titanium.API.info("Ad failed");
});
```
# REWARDED VIDEOS
```javascript
var rewarded = Admob.createView({
	viewType : Admob.TYPE_ADS,
	adSizeType: Admob.REWARDED_VIDEO,
	testDeviceId : "G9CCEHKYF95FFR8152FX50D059DC8336", //USE YOUR DEVICE ID HERE
	adUnitId: 'ca-app-pub-3940256099942544/5224354917', //USE YOUR AD_UNIT ID HERE
});	
window.add(rewarded);

window.addEventListener("open", function(){
	//You can preload the ads at any moment you judge the best
	rewarded.loadRewardedAd();
});

rewarded.addEventListener(Admob.AD_RECEIVED, function(e) {
	Titanium.API.info("Rewarded Ad AD_RECEIVED");
	rewarded.showAdNow();
});

rewarded.addEventListener(Admob.AD_REWARDED, function(e) {
	Titanium.API.info("Rewarded Ad AD_REWARDED");
	Titanium.API.info("Yay! You can give the user his reward now!");
	Titanium.API.info(JSON.stringify(e));
});

rewarded.addEventListener(Admob.AD_OPENED, function(e) {
	Titanium.API.info("Rewarded Ad AD_OPENED");
});

rewarded.addEventListener(Admob.AD_VIDEO_STARTED, function(e) {
	Titanium.API.info("Rewarded Ad AD_VIDEO_STARTED");
});
```
# MULTIPLE AD SIZES
```javascript
var multipleAds = Admob.createView({
	top: 0,
	viewType : Admob.TYPE_ADS,
	adSizes: [
		{width: 320, height: 100},
		{width: 320, height: 50},
		{width: 320, height: 240}
	],
	testDeviceId : "G9CCEHKYF95FFR8152FX50D059DC8336", //USE YOUR DEVICE ID HERE
	adUnitId: 'ca-app-pub-3940256099942544/6300978111', //USE YOUR AD_UNIT ID HERE
});	
window.add(multipleAds);	

multipleAds.addEventListener(Admob.AD_RECEIVED, function(e) {
	Titanium.API.info("Ad received");
});

multipleAds.addEventListener(Admob.AD_NOT_RECEIVED, function(e) {
	Titanium.API.info("Ad failed");
});
```
# INTERSTITIAL AD
```javascript
var interstitialAd = Admob.createView({
	viewType : Admob.TYPE_ADS,
	adSizeType: Admob.INTERSTITIAL,
	testDeviceId : "G9CCEHKYF95FFR8152FX50D059DC8336", //USE YOUR DEVICE ID HERE
	adUnitId: 'ca-app-pub-3940256099942544/1033173712', //USE YOUR AD_UNIT ID HERE
});	
window.add(interstitialAd);

interstitialAd.addEventListener(Admob.AD_RECEIVED, function(e) {
	Titanium.API.warn("Interstital Ad Received");
});

interstitialAd.addEventListener(Admob.AD_NOT_RECEIVED, function(e) {
	Titanium.API.error("Interstital Ad failed");
});

interstitialAd.addEventListener(Admob.AD_READY_TO_BE_SHOWN, function(e) {
	Titanium.API.warn("Interstital Ad is READY!");
	interstitialAd.showAdNow();
});

interstitialAd.addEventListener(Admob.AD_NOT_READY_YET, function(e) {
	Titanium.API.warn("Interstital Ad is not ready yet!");
});

interstitialAd.addEventListener(Admob.AD_BEING_SHOWN, function(e) {
	Titanium.API.warn("Interstital Ad being shown right now!");
});

interstitialAd.addEventListener(Admob.AD_CLOSED, function(e) {
	Titanium.API.warn("Interstital ad close successfully. RIP!");
	interstitialAd.requestInterstitalAd();
});
```
# UNIFIED NATIVE ADS
```javascript
var masterView = Titanium.UI.createView({
	width : Titanium.UI.FILL,
	height : Titanium.UI.SIZE,
	layout : "vertical"
});

var topView = Titanium.UI.createView({
	top : 0,
	left : 0,
	right : 0,
	height : Titanium.UI.SIZE,
	layout : "horizontal"
});
masterView.add(topView);

var contentad_logo = Titanium.UI.createImageView({
	elevation : 12,
	height : 50
});
topView.add(contentad_logo);

var contentad_advertiser = Titanium.UI.createLabel({
	color : "#575757",
	left : 16,
	textAlign : Titanium.UI.TEXT_ALIGNMENT_LEFT,
	verticalAlign : Titanium.UI.TEXT_VERTICAL_ALIGNMENT_CENTER,
	height : 35,
	font : {
	fontSize : 18,
		fontWeight : "bold"
	}
});
topView.add(contentad_advertiser);

var mediaView = Admob.createView({
	viewType : Admob.TYPE_MEDIA,
	top : 0,
	left : 0,
	right : 0,
	height : 250
});
masterView.add(mediaView);

var contentad_headline = Titanium.UI.createLabel({
	top : 16,
	maxLines : 2,
	color : "#000000",
	left : 16,
	right : 16,
	textAlign : Titanium.UI.TEXT_ALIGNMENT_LEFT,
	font : {
	fontSize : 20,
	f	ontWeight : "bold"
	}
});
masterView.add(contentad_headline);

var contentad_body = Titanium.UI.createLabel({
	color : "#575757",
	left : 16,
	right : 16,
	textAlign : Titanium.UI.TEXT_ALIGNMENT_LEFT,
	font : {
	f	ontSize : 16
	}
});
masterView.add(contentad_body);

var contentad_call_to_action = Titanium.UI.createButton({
	top : 16,
	elevation : 8,
	right : 16,
	width : Titanium.UI.SIZE,
	height : 35,
	backgroundColor : "#ff5722",
	font : {
	fontSize : 14,
		fontWeight : "bold"
	}
});
masterView.add(contentad_call_to_action);

var ratingView = Admob.createView({
	viewType : Admob.TYPE_STARS,
	left : 0,
	right : 0
});
masterView.add(ratingView);

var contentad_store_view = Titanium.UI.createLabel({
	color : "#D50000",
	top : 8,
	font : {
		fontSize : 16
	}
});
masterView.add(contentad_store_view);

var contentad_price_view = Titanium.UI.createLabel({
	color : "#575757",
	height : Titanium.UI.SIZE,
	width : Titanium.UI.SIZE,
	font : {
		fontSize : 12
	}
});
masterView.add(contentad_advertiser);

var nativeAd = Admob.createView({
	//Standard Widgets
	masterView : masterView,
	headlineLabel : contentad_headline,
	bodyLabel : contentad_body,
	callToActionButton : contentad_call_to_action,
	logoOrIconImageView : contentad_logo,
	advertiserLabel : contentad_advertiser,
	mediaView : mediaView,

	//Store Widgets
	storeLabel : contentad_store_view,
	starsView : ratingView,
	priceLabel : contentad_price_view,

	top : 16,
	left : 16,
	right : 16,
	height : Titanium.UI.SIZE,
	backgroundColor : "#FFFFFF",

	viewType : Admob.TYPE_ADS,
	adSizeType: Admob.UNIFIED_NATIVE_ADS,
	adUnitId : "ca-app-pub-3940256099942544/2247696110",
	testDeviceId : "E6BB08637D19DA5B1C7BBA4758FC0F95",
});
window.add(nativeAd);

nativeAd.addEventListener(Admob.AD_RECEIVED, function(e) {
	Titanium.API.info("Native Ad received");
});

nativeAd.addEventListener(Admob.AD_NOT_RECEIVED, function(e) {
	Titanium.API.info("Native Ad failed");
});
```
# Events

|Events                |Description                          |
|----------------|-------------------------------|
|_AD_RECEIVED_				|   Ad have been successfully received
|_AD_NOT_RECEIVED_    				| 	A error occurred and the ads failed
|_AD_DESTROYED_   | 	Ad had been successfully destroyed and wiped out of memory 
|_AD_READY_TO_BE_SHOWN_ 				| 	**(INTERSTITIAL)** The ad is ready and can be shown
|_AD_NOT_READY_YET_ 				| 	**(INTERSTITIAL)** If you try to show the interstitial ad before it is ready, this event will fire
|_AD_BEING_SHOWN_ 				| 	**(INTERSTITIAL)** Ad is on screen
|_AD_CLOSED_	|  	**(REWARDED or INTERSTITIAL)** Ad had been successfully closed  
|_AD_OPENED_    			|	**(REWARDED)** Ad is now on screen and visible
|_AD_REWARDED_    	|	**(REWARDED)** When the video ended successfully and you can reward you user with his prize
|_AD_VIDEO_STARTED_ | 	**(REWARDED)** When the rewarded video starts playing

# Functions

### Number isGooglePlayServicesAvailable()

Returns a number value indicating the availability of Google Play Services which are for push notifications.

Possible values include `SUCCESS`, `SERVICE_MISSING`, `SERVICE_VERSION_UPDATE_REQUIRED`, `SERVICE_DISABLED`,
and `SERVICE_INVALID`.

### `requestConsentInfoUpdateForPublisherIdentifiers(args)`

Requests consent information update for the provided publisher identifiers. All publisher
identifiers used in the application should be specified in this call. Consent status is reset to
unknown when the ad provider list changes.

- `publisherIdentifiers` (Array<String>)
- `callback` (Function)

### `showConsentForm(args)`

Shows a consent modal form. Arguments:

- `shouldOfferPersonalizedAds` (Boolean)
Indicates whether the consent form should show a personalized ad option. Defaults to `true`.
- `shouldOfferNonPersonalizedAds` (Boolean)
Indicates whether the consent form should show a non-personalized ad option. Defaults to `true`.
- `shouldOfferAdFree` (Boolean)
Indicates whether the consent form should show an ad-free app option. Defaults to `false`.
- `callback` (Function)
Callback to be triggered once the form completes.

### `resetConsent()`

Resets consent information to default state and clears ad providers.

### `setTagForUnderAgeOfConsent(true|false)`

Sets whether the user is tagged for under age of consent.

### `isTaggedForUnderAgeOfConsent()` (Boolean)

Indicates whether the user is tagged for under age of consent.

## Properties

### `consentStatus` (`CONSENT_STATUS_UNKNOWN`, `CONSENT_STATUS_NON_PERSONALIZED` or `CONSENT_STATUS_PERSONALIZED`)

### `adProviders` (Array)

Array of ad providers.

### `debugGeography` (`DEBUG_GEOGRAPHY_DISABLED`, `DEBUG_GEOGRAPHY_EEA` or `DEBUG_GEOGRAPHY_NOT_EEA`)

Debug geography. Used for debug devices only.

### getAndroidAdId(callback)

Gets the user Android Advertising ID. Since this works in a background thread in native
Android a callback is called when the value is fetched. The callback parameter is a key/value
pair with key `androidAdId` and a String value with the id.

#### Example:

	Admob.getAndroidAdId(function (data) {
		Ti.API.info('AAID is ' + data.aaID);
	});

### isLimitAdTrackingEnabled(callback)

Checks whether the user has opted out from ad tracking in the device's settings. Since
this works in a background thread in native Android a callback is called when the value
is fetched. The callback parameter is a key/value pair with key `isLimitAdTrackingEnabled`
and a boolean value for the user's setting.

#### Example:

	Admob.isLimitAdTrackingEnabled(function (data) {
		Ti.API.info('Ad tracking is limited: ' + data.isLimitAdTrackingEnabled);
	});

### Support the Facebook Audience Network adapter

Starting in 4.3.0 you can use the included Facebook Audience Network adapter to turn on the mediation in your AdMob account.
Here you do not have to do anything 😙. You only need to configure mediation in your AdMob and Facebook accounts by 
following the [official guide](https://developers.google.com/admob/android/mediation/facebook).

## Constants

### Number `SUCCESS`
Returned by `isGooglePlayServicesAvailable()` if the connection to Google Play Services was successful.

### Number `SERVICE_MISSING`
Returned by `isGooglePlayServicesAvailable()` if Google Play Services is missing on this device.

### Number `SERVICE_VERSION_UPDATE_REQUIRED`
Returned by `isGooglePlayServicesAvailable()` if the installed version of Google Play Services is out of date.

### Number `SERVICE_DISABLED`
Returned by `isGooglePlayServicesAvailable()` if the installed version of Google Play Services has been disabled on this device.

### Number `SERVICE_INVALID`
Returned by `isGooglePlayServicesAvailable()` if the version of the Google Play Services installed on this device is not authentic.

### Number `CONSENT_STATUS_UNKNOWN`
Returned by `consentStatus` if the consent status is unknown.

### Number `CONSENT_STATUS_NON_PERSONALIZED`
Returned by `consentStatus` if the consent status is not personalized.

### Number `CONSENT_STATUS_PERSONALIZED`
Returned by `consentStatus` if the consent status is personalized.

### Number `DEBUG_GEOGRAPHY_DISABLED`
Returned by `debugGeography` if geography debugging is disabled.

### Number `DEBUG_GEOGRAPHY_EEA`
Returned by `debugGeography` if geography appears as in EEA for debug devices.

### Number `DEBUG_GEOGRAPHY_NOT_EEA`
Returned by `debugGeography` if geography appears as not in EEA for debug devices.


# Google Test Ads Ids
|Events                |Description                          |
|----------------|-------------------------------|
|[Adaptative Banner](https://developers.google.com/admob/android/banner/adaptive) | ca-app-pub-3940256099942544/9214589741
|[Banner](https://developers.google.com/admob/android/banner#add_adview) | ca-app-pub-3940256099942544/6300978111
|[Interstitial](https://developers.google.com/admob/android/interstitial#create_an_interstitial_ad_object) | ca-app-pub-3940256099942544/1033173712
|[Interstitial Video](https://developers.google.com/admob/android/interstitial#create_an_interstitial_ad_object) | ca-app-pub-3940256099942544/8691691433
|[Rewarded Video](https://developers.google.com/admob/android/rewarded-video#request_rewarded_video_ad) | ca-app-pub-3940256099942544/5224354917
|[Unified Native](https://developers.google.com/admob/android/native-advanced#build_an_adloader) | ca-app-pub-3940256099942544/2247696110
