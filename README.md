# BlueMarble

Blue marble uses the EPIC api from NASA to pull images taken by the EPIC satellite in either enhanced or natural mode.
I used the Android architecture sample [todo‑mvp‑clean](https://github.com/googlesamples/android-architecture/tree/todo-mvp-clean/),
as a way to learn and experiment with creating custom callbacks using interfaces, as well as dealing with background tasks
using executors. A lot of these responsibilities can be replaced with Architecture components such as LiveData, the
Databinding library and Viewmodels. I think it is important to understand how to create callbacks and deal with threading, and 
persistence without the use of components.

I made use of a method of extracting the view by creating a viewFactory, learnt by Vasiliy Zukanov in his Udemy [course](https://www.udemy.com/dependency-injection-in-android-with-dagger/)
on implementing dependency injection with Dagger2. I created listeners that pass events from the view to the controller.
This too can be replaced by creating A ViewmodelFactory, inflating viewmodels that make use of the databinding library and use observers
to update the ui.

This project is still in development.

### Architecture

Clean,
MVP,
Repository pattern,
Dagger2

## Built With

* Android

## Versioning

1

## Authors

* **Shannon Ferguson**  


## Acknowledgments

* Android Architecture BluePrints [todo‑mvp‑clean](https://github.com/googlesamples/android-architecture/tree/todo-mvp-clean/)
* Vasiliy Zukanov: [Udemy course](https://www.udemy.com/dependency-injection-in-android-with-dagger/)
