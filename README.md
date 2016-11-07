# android-architecture
The repository demonstrates three different architectural patterns that we can use to build the Android app.

The same sample app is built three times using the following approaches:
* __Standard/Massive Activity__: traditional approach where we put what we can under activities/fragments
* __MVP__: [Model View Presenter](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter)
* __MVVM__: [Model View ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)

## The App
The demo app displays simple list of Github public repositories for a given username. When user taps on one of them, the app will open new view with simple details.

### So far the app used
* AppCompat, CardView and RecyclerView
* RxJava2 & RxAndroid2
* Retrofit 2
* Dagger 2
* Butterknife

## Standard/Massive Activity
A project where some API work is handled in some separate class but generally a view (in our case activities) knows about our model/API layer. These two fellows knows about each other.

## MVP - Model View Presenter
Here, our Activities/Fragments become a part of the 'view' layer and they delegate most of the work to presenters. Each activity or fragment has it's own presenter that handles user interaction and manage the data based on these actions.
Note, that writing unit tests for presenters become very easy by mocking the view layer! Hurra, no more 'Error java.lang.RuntimeException: Stub!'

## MVVM - Model View ViewModel
This approach supports two-way data binding between 'view' and 'View Model'. This enables automatic propagation of changes, within the state of view model to the View. Also here, we should have one ViewModel to one activity/fragment.
Also here, writting unit tests becomes easier because the ViewModels are decoupled from the view. The view knows about ViewModel but ViewModel doesn't know anything about the view. It knows only our model layer.

## Requirements

* SDK version 24 (uses Jack and Jill tool chain and Java 8 features)
* GitHub token : just fill out the github_token under config.xml file with your GitHub Personal access token

## Nice to read
[android-mvp-mvvm-redux-history](http://zserge.com/blog/android-mvp-mvvm-redux-history.html)
[android-databinding-goodbye-presenter-hello-viewmodel](http://tech.vg.no/2015/07/17/android-databinding-goodbye-presenter-hello-viewmodel/)

## License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```