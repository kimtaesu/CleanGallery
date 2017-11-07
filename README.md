# CleanGallery
Trello : https://trello.com/b/txtiUe3Y/cleangallery

우리의 프로젝트는 [CleanArchitecture](https://github.com/bufferapp/clean-architecture-components-boilerplate) 를 기반으로 학습을 목적으로 진행하고 있습니다.
요구사항을 도출하고 최적의 해결 방안을 모색하는 것을 학습할 수 있습니다.


<img src="/document/gallery_thumbnail.png" alt="Smiley face" height="510" width="300">
<img src="/document/gallery_detail.png" alt="Smiley face" height="510" width="300">

## Languages, libraries and tools used

* [Kotlin](https://kotlinlang.org/)
<!-- * [Room](https://developer.android.com/topic/libraries/architecture/room.html) -->
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
* Android Support Libraries
* [RxJava2](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0)
* [Dagger 2 (2.11)](https://github.com/google/dagger)
* [Glide](https://github.com/bumptech/glide)
<!-- * [Retrofit](http://square.github.io/retrofit/) -->
<!-- * [OkHttp](http://square.github.io/okhttp/) -->
* [Gson](https://github.com/google/gson)
* [Timber](https://github.com/JakeWharton/timber)
* [Mockito](http://site.mockito.org/)
<!-- * [Espresso](https://developer.android.com/training/testing/espresso/index.html) -->
<!-- * [Robolectric](http://robolectric.org/) -->

## Step 1. Local Storage  Images  _List up_
Clearn Gallery를 계획하면서 많은 *Iteration*들이 있지만 Start Point는 Local Storage로 부터 Images들을 가져오는 것이었다.
> 우리의 기본 가치 중 하나는 "**가장 먼저 간단한 것 부터 해라**"이다. 진행하면서 실질적인 문제와 해결 방안이 보이기 때문이다. (도메인 학습)

이에 관련되어 많은 Open Source 를 리서치하고 정리한 결과  [Simple-Gallery](https://github.com/SimpleMobileTools/Simple-Gallery)
를 기초로 골격을 잡아가는 것으로 결정하게 되었다.

그러면서 필요한 두 가지 요구사항이 발생했다 :
 * Permission **READ_EXTERNAL_STORAGE**
 * Migration [Simple-Gallery MediaFetcher][simple-mediafetcher]
 * DataSource 추상화

#### Permission **READ_EXTERNAL_STORAGE**
READ_EXTERNAL_STORAGE의 권한을 획득하는 것은 [PermissionsDispatcher](https://github.com/permissions-dispatcher/PermissionsDispatcher) 로 쉽게 해결 할 수 있었다.

#### Migration [Simple-Gallery MediaFetcher][simple-mediafetcher]
MediaFetcher의 경우는 당장 필요하지 않는 코드들이 많으며 상당히 복잡했다. 우선은 가장 빠르게 현재의 요구사항을 만족할 수준으로만
개발하고 Testable 환경을 만들어 추후 Refactoring을 생각하게 되었다.

[MediaFixture](https://github.com/kimtaesu/CleanGallery/blob/master/app/src/test/java/com/hucet/clean/gallery/fixture/MediaFixture.kt)는 **Json -> deserialize -> Mock** 순서로 동작한다.
> 요구사항 "Sort, Filter, Video etc.." 다양한 Test case 에 적용할 경우 쉽게 Test가 가능하다.
![](https://raw.githubusercontent.com/kimtaesu/CleanGallery/master/document/media_testable.jpg)

#### DataSource 추상화
이 작업은 _Over Engineering_ 일 수도 있지만 우리의 생각을 다르다. DataSource는 Low level에 속하기 때문에
실제 문제가 1년 뒤에 나타날 수도 있기 때문이다. 이 시간이 지나면 1 년 동안 코드를 변경하는 것이 어려울 수 있다.

그리고 더 중요한 점은 다른 Type의 Data Source(Cache, Network etc..)도 Iteration에 포함되어 있기 때문이다.

[simple-mediafetcher]: https://github.com/SimpleMobileTools/Simple-Gallery/blob/master/app/src/main/kotlin/com/simplemobiletools/gallery/helpers/MediaFetcher.kt

## Step 2. Presenter <-> Data(Repository) <-> DataSource 관계 연결
 * Repository 구현
 * MVP Pattern
 * Dagger2 v2.11
#### Repository 구현
우리는 [Step 1](https://github.com/kimtaesu/CleanGallery#step-1-local-storage--images--list-up)에서 LocalStorage의 DataSource를 구현했다.

이제 다음으로 할 일은 Presenter와 DataSource의 관계를 연결해주는 것이다. 그 관계를 연결하기 위해서 [GalleryRepository](https://github.com/kimtaesu/CleanGallery/blob/master/app/src/main/java/com/hucet/clean/gallery/repository/GalleryRepository.kt)를 중간에 두기로 결정했다.

여기서의 Repository 개념은 외부 데이터 레이어에 대한 액세스 지점이며  Multiple DataSource (Local Storage, Cache, Network etc..)에서 데이터를 가져오는 것을 말한다. 우리는 이미 Cache, Network에 대한 Iteration을 계획했기 때문에 이 Repository를 구현했다.

#### MVP Pattern
[Android Architecture Components](https://github.com/googlesamples/android-architecture)에는 MVP, MVVM와 같은 여러가지 Pattern을 소개한다. Rx의 사용자인 우리는 MVVM을 사용할 경우 Rx를 사용할 수 없다는 사실을 알고 있다.
또, MVVM 패턴이 아직 Stable 단계가 아니기 때문에 MVP를 결정하게 되었다.

#### Dagger2 v2.11
[Dagger2](https://github.com/google/dagger)는 Android에서 Dependencies injection의 최강자라고 생각한다. v2.11은 AndroidInjector와 @ContributesAndroidInjector의 도입으로 많은 상용구를 제거할 수 있도록 지원하고 있다.

우리는 Dagger를 사용하기 위하여 항상 Denpendencies Graph를 그린다.
![](https://raw.githubusercontent.com/kimtaesu/CleanGallery/master/document/di_graph.jpg)


## Step 3. Presenter programming Rx Style
* Main / Background Scheduler 작업 정의

Rx를 사용하면 쉽게 Error Handlering이 가능하며 Schedulers를 정의할 수 있다.
Main Thread에서 16ms 동안 반응이 없는 경우 Frame을 Skip 한다고 한다. FPS (Frames Per Second)가 낮으면 Unresponsive UI라고 말할 수 있다.
UI를 Update하는 것외에 다른 작업은 별도의 Thread에서 하기를 권장한다.

우리는 Timber를 사용하여 ThreadName이 출력될 수 있도록 작업했다.
```kotlin
class OptionalTree(val threadName: Boolean = false) : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
        var msg = message
        if (threadName)
            msg = "Thread[${Thread.currentThread().name}] ${message}"
        super.log(priority, tag, msg, t)
    }
}
```
사용법은 간단하다. `Timber.plant`를 아래와 같이 호출하면 된다.
```kotlin
Timber.plant(OptionalTree(threadName = true))
```

이제 어떻게 Error Handlering과 Schedulers를 정의하는지 살펴보자.

[GalleryPresenter](https://github.com/kimtaesu/CleanGallery/blob/master/app/src/main/java/com/hucet/clean/gallery/gallery/list/presenter/GalleryPresenter.kt)
```kotlin
repository
                .getGalleries()
                .subscribeOn(Schedulers.io())
                .main()
                .doOnSubscribe {
                    view.showProgress()
                }
                .main()
                .doOnTerminate {
                    view.hideProgress()
                }
                .subscribe(
                        { next ->
                            adapter.updateData(next)
                        },
                        { error ->
                            view.showError()
                        },
                        {
                        })
```

> [UI Thread] : showProgress, hideProgress, showError

> [RxCachedThreadScheduler] : getGalleries

Thread Log
```java
Thread[main] doOnSubscribe
Thread[RxCachedThreadScheduler-1] GalleryPresenter getGalleries
Thread[main] Next[[Medium(name=tyler, path=/tyler, video=false, modified=1509514003251, taken=1509514003251, size=10)]]
Thread[main] doOnTerminate
Thread[main] Completed
```


## Step 4. Ui Thumbnail using [Glide](https://github.com/bumptech/glide)
우리는 많은 Loading image 라이브러리 중에 말도 필요없이 [Glide](https://github.com/bumptech/glide)를 사용하기로 결정하였다.
Glide가 어떻게 Image 처리를 최적화하는지 자세히 알고 싶으면 [여기](https://medium.com/@kimtaesoo188/how-the-android-image-loading-library-glide-and-fresco-works-94a156bed628)를 통해서 알 수 있다.

`@GlideModule` 이 있어야 `GlideRequests, GlideApp etc..`가 generate 된다.
참조 문서 : [Glide#Kotlin](http://bumptech.github.io/glide/doc/generatedapi.html#kotlin)
```kotlin
@GlideModule
public final class GalleryModule extends AppGlideModule {
    // Intentionally empty.
}
```


GalleryAdapter.kt
```kotlin
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        ...
        glideRequests
                .asDrawable()
                .centerCrop()
                .load(medium.path)
                .into(holder.thumbnail)
```

   <img src="https://github.com/kimtaesu/CleanGallery/blob/master/document/gallery_thumbnail.png" alt="Smiley face" height="510" width="300">

## Step 5. Detail View
우리는 [Step 4](https://github.com/kimtaesu/CleanGallery#step-4-ui-thumbnail-using-glide) 를 거쳐서 모든 Image를 UI상에 보여줄 수 있게 되었다.
다음은 List의 Item을 클릭했을 경우 Detail View로 이동하는 것이다.

이 작업을 하기 위해선 두 가지 작업이 필요하다.
 * Activity <-> Fragment 간 통신
 * Fragment <-> Adapter 간 통신

#### Activity <-> Fragment 간 통신
Activity와 Fragment 간 통신을 하기 위해선 Google에서 [권장하는 방법](https://developer.android.com/training/basics/fragments/communicating.html)이 있다.

그러나 위와 같은 방법은 두 가지 위험성이 있다.
 1. 위험한 Cast를 사용한다.
 2. Fragment와 Activity가 어떻게 연결되어 있는지 명확하게 알 수 없다.

관련 내용은 [여기](https://medium.com/@kimtaesoo188/android-weekly-277-from-fragments-to-activity-the-lambda-way-cac15973721a)에서 확인이 가능하다.

[MainActivity.kt](https://github.com/kimtaesu/CleanGallery/blob/master/app/src/main/java/com/hucet/clean/gallery/activity/MainActivity.kt)
```kotlin
galleryFragment = ListGalleryFragment.Companion.Builder()
            .setOnClickListener(object : GalleryListener {
                override fun onGalleryClicked(medium: Medium) {
                    Timber.d("onGalleryClicked ${medium}")
                }
            }).build()
```

```kotlin
        supportFragmentManager.beginTransaction()
                .add(android.R.id.content, galleryFragment)
                .commit()
```
[ListGalleryFragment.kt](https://github.com/kimtaesu/CleanGallery/blob/master/app/src/main/java/com/hucet/clean/gallery/gallery/list/ListGalleryFragment.kt)
```kotlin
class ListGalleryFragment : Fragment(), Gallery.View {
    companion object {
        private val BUNDLE_KEY_CLICK_LISTENER = "BUNDLE_KEY_CLICK_LISTENER"

        class Builder {
            private var onClick: GalleryListener? = null

            fun setOnClickListener(onClick: GalleryListener): Builder {
                this.onClick = onClick
                return this
            }

            fun build(): ListGalleryFragment {
                val fragment = ListGalleryFragment()
                fragment.setArguments(createArgs())
                return fragment
            }

            private fun createArgs(): Bundle {
                val bundle = Bundle()
                if (onClick != null) {
                    //store the listener in the arguments bundle
                    //it is a state less lambda, guaranteed to be serializable
                    bundle.putSerializable(BUNDLE_KEY_CLICK_LISTENER, onClick)
                }
                return bundle
            }

        }
    }
```
#### Fragment <-> Adapter 간 통신

[ListGalleryFragment.kt](https://github.com/kimtaesu/CleanGallery/blob/master/app/src/main/java/com/hucet/clean/gallery/gallery/list/ListGalleryFragment.kt)
```kotlin
gallery_list.apply {
            this@ListGalleryFragment.adapter.setOnClickListener(this, onClick)
```
[GalleryAdapter.kt](https://github.com/kimtaesu/CleanGallery/blob/master/app/src/main/java/com/hucet/clean/gallery/gallery/list/GalleryAdapter.kt)
```kotlin
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       ...
        itemView.setOnClickListener({
            val position = recyclerView?.getChildAdapterPosition(it)
            onClick?.onGalleryClicked(mediums.get(position!!))
        })
       ...
    }
```

<img src="https://github.com/kimtaesu/CleanGallery/blob/master/document/gallery_detail.png" alt="Smiley face" height="510" width="300">

## Step 6. Refactoring [MediaFetcher][mediafetcher]
Step 1 ~ Step 5로 기본적인 Gallery 골격이 갖춰지게 되었다.

그러나 [Simple-Gallery MediaFetcher][simple-mediafetcher]의 코드를 적용했지만 불필요한 작업들이 상당히 많다.
사용하지 않는 Parameter, 그 외 다양한 Preference 값에 따른 Action들.. 등 지금은 단지 **Images list** 만 필요하기 때문에
Refactroing이 필요하다고 생각됬다.

우리는 이미 [Step 1](https://github.com/kimtaesu/CleanGallery#step-1-local-storage--images--list-up)에서 Testable 환경을 만들었기 때문에
[MediaFetcher][mediafetcher]의 Refactring을 진행하면서 얼마나 쉽게 변경이 가능한지 알 수 있다.

[simple-mediafetcher]: https://github.com/SimpleMobileTools/Simple-Gallery/blob/master/app/src/main/kotlin/com/simplemobiletools/gallery/helpers/MediaFetcher.kt
[mediafetcher]: https://github.com/kimtaesu/CleanGallery/blob/master/app/src/main/java/com/hucet/clean/gallery/datasource/local/MediaFetcher.kt

> [설정 : 자막 On, 품질 1080P]으로 감상 부탁 드립니다.

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/cAsL72Mj-kc/0.jpg)](https://www.youtube.com/watch?v=cAsL72Mj-kc)