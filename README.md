# 🎇 View Navigator
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

View Navigator is a tool that allows you to inspect and validate all the views of a screen individually, highlighting the margins and paddings of each component, in addition, it is possible to navigate in the hierarchy of views and analyze all the elements that make up your screen.

<p align="center">
  <img src="https://user-images.githubusercontent.com/75705626/196259820-8bdb8c12-a9dd-4f02-bfee-b6607df3df80.gif" width="35%">
&nbsp; &nbsp; &nbsp; &nbsp;
  <img src="https://user-images.githubusercontent.com/75705626/196262149-14687804-9402-4425-b8bc-3aa223ab19ce.gif" width="35%">
</p>

## Features
View Navigator Window<br>
<img src="https://user-images.githubusercontent.com/75705626/196679085-a6209d07-bb1f-4df4-993a-ee949441c8fe.png" alt="sample" title="sample" width="35%" />

### Info button
This icon shows the information about margins and paddings<br>
<img src="https://user-images.githubusercontent.com/75705626/196683879-a5775de1-3dc6-4d83-9e2c-0a7bf6293c1e.png"  width="35%" />

### Metrics button
This icon shows the information about margins and paddings with their values<br>
<img src="https://user-images.githubusercontent.com/75705626/196684700-5d19ec46-99df-43f9-8c5a-663fbed4789b.png"  width="35%" />

### Expand/Collapse button
This icon expand/collapse the items<br>
<img src="https://user-images.githubusercontent.com/75705626/196685408-c944ddbd-e9aa-42f5-9a5f-e824988beac6.png"  width="35%" />

### Close button
This icon close the window<br>
<img src="https://user-images.githubusercontent.com/75705626/196685700-18d9424d-1a45-47db-ba29-f7b76b63f9c4.png"  width="35%" />

### Items section
This section is a list with all the child views<br>
<img src="https://user-images.githubusercontent.com/75705626/196687042-ff96dc49-1773-4997-a5b1-b04de7d566f3.png" width="35%" />
<br>
  - <img src="https://user-images.githubusercontent.com/75705626/196688376-014bac12-d7df-4d4c-af21-5f1a9795c8f5.png" width="20px" /> Prev button: This button indicates that we want to analyze the views of the parent, we will go back one level in the hierarchy;
  - <img src="https://user-images.githubusercontent.com/75705626/196689016-d46ef00e-6ac7-4818-867c-523e85f11366.png" width="20px" /> Next button: This button indicates that we want to analyze the views of this ```ViewGroup```, we are going to advance one level in the hierarchy;
  - <img src="https://user-images.githubusercontent.com/75705626/196689170-031770c4-dded-4bd5-abcf-6de84b08ce4f.png" width="20px" /> Invisible icon: This icon indicates that the view is currently invisible.

## Setup
[![](https://jitpack.io/v/MarceloAlban/ViewNavigator.svg)](https://jitpack.io/#MarceloAlban/ViewNavigator)

To use the View Navigator, add the Jitpack repository to your root ```build.gradle```:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
Add the dependency:
```groovy
implementation 'com.github.MarceloAlban:ViewNavigator:{last_version}'
```
## How it works?
View Navigator shows all components of a base root layout visually, all elements are clickable and when clicked they are highlighted with margins and paddings.

We can use the View Navigator in two ways:

### For the entire application

In this case, the View Navigator will appear for all activities and fragments automatically, to do this, you must register the View Navigator in the ```Application``` class through the ```ViewNavigatorRegister``` class:

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ViewNavigatorRegister().register(this)
    }
}
```
Registering in the ```Application```, the View Navigator will be always visible in the screen and will refresh the views when you navigate to another Activity or Fragment.

### For a single Activity or Fragment

In this case, the view navigator must be called manually, we can do this through the ```ViewNavigatorWindow``` class:

```kotlin
ViewNavigatorWindow(rootView).show()
```

#### Example
I want to inspect a specific layout in my xml

**XML**
```xml
...
<androidx.appcompat.widget.LinearLayoutCompat
    android:id="@+id/rootLayout">

    <com.google.android.material.textview.MaterialTextView/>
  
    <com.google.android.material.textview.MaterialTextView/>
  
</androidx.appcompat.widget.LinearLayoutCompat>
...
```

**CODE**
```kotlin
// With ViewBinding
ViewNavigatorWindow(binding.rootLayout).show()
// Without ViewBinding
ViewNavigatorWindow(findViewById(R.id.rootLayout)).show()
```
