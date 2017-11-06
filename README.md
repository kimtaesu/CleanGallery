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

## Step 1.
요구사항 : Local Storage에 있는 Images  _List up_

과정

 * Permission **READ_EXTERNAL_STORAGE** 권한 부여 로직 : [PermissionsDispatcher](https://github.com/permissions-dispatcher/PermissionsDispatcher) library로 해결
 * Local Storage Image 목록 _List up_ : [Simple-Gallery](https://github.com/SimpleMobileTools/Simple-Gallery) 로직 중 **Android Media**에서 Images를 가져올 수 있는
 로직을 사용.

[MediaFixture](https://github.com/kimtaesu/CleanGallery/blob/master/app/src/test/java/com/hucet/clean/gallery/fixture/MediaFixture.kt) Testable 환경 구축으로 앞으로의 요구사항 "Sort, Filter, Video etc.." 다양한 Test case 에 적용할 경우 쉽게 Test할 수 있습니다.
또한 *Local Storage*를 Data Source로 추상화하여 추가적인 Data Source(Cache, Network etc..) 에 대응이 가능합니다.

[TestableMedia]
![](/document/media_testable.jpg)

## Step 2.
요구사항 : Presenter <-> Repository <-> DataSource 관계 연결

과정
* MVP Pattern
* Dagger2 (v2.11) 적용
* DiffUtil

[DependencyGraph]
![](/document/di_graph.jpg)

## Step 3.
요구사항 : Repository With Rx

과정
* Rx Style [Start, Error, Complete] 정의

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

## Step 4.
요구사항 : Ui Thumbnail

과정
 * Glide v4 library를 사용하였습니다.


`@GlideModule` 이 있어야 `GlideRequests, GlideApp etc..`가 generate 됩니다.
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

   <img src="/document/gallery_thumbnail.png" alt="Smiley face" height="510" width="300">

## Step 5.
요구사항 : Image(detail) viewing

과정
* Gallery Click Event
1. Activity <-> Fragment 관련된 [Acticle](https://medium.com/@kimtaesoo188/android-weekly-277-from-fragments-to-activity-the-lambda-way-cac15973721a)

GalleryActivity.kt
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
ListGalleryFragment.kt
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
2. Fragment <-> Adapter

ListGalleryFragment.kt
```kotlin
gallery_list.apply {
            this@ListGalleryFragment.adapter.setOnClickListener(this, onClick)
```
GalleryAdapter.kt
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
* Detail View
<img src="/document/gallery_detail.png" alt="Smiley face" height="510" width="300">