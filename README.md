# android-architecture
The repository demonstrates three different architectural patterns that we can use to build the Android app.

The same sample app is built three times using the following approaches:
* __Standard/Massive Activity__: traditional approach where we put what we can under activities/fragments
* __MVP__: [Model View Presenter!](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter)
* __MVVM__: [Model View ViewModel!](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)

## The App
The demo app displays simple list of Github public repositories for a given username. When user taps on one of them, the app will open new view with simple details.

### So far the app used
* AppCompat, CardView and RecyclerView
* RxJava2 & RxAndroid2
* Retrofit 2
* Dagger 2
* Butterknife

## Standard/Massive Activity
TODO

## MVP - Model View Presenter
TODO

## MVVM - Model View ViewModel
TODO

## Requirements

* SDK version 24 (uses Jack and Jill tool chain and Java 8 features)
* GitHub token : just fill out the github_token under config.xml file with your GitHub Personal access token

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