# 🎇 View Navigator
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

View Navigator is a tool that allows you to inspect and validate all the views of a screen individually, highlighting the margins and paddings of each component, in addition, it is possible to navigate in the hierarchy of views and analyze all the elements that make up your screen.

<p align="center">
  <img src="https://user-images.githubusercontent.com/75705626/196259820-8bdb8c12-a9dd-4f02-bfee-b6607df3df80.gif" width="35%">
&nbsp; &nbsp; &nbsp; &nbsp;
  <img src="https://user-images.githubusercontent.com/75705626/196262149-14687804-9402-4425-b8bc-3aa223ab19ce.gif" width="35%">
</p>

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

We can use the Navigator view in two ways:

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
