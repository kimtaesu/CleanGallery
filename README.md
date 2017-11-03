# CleanGallery
Trello : https://trello.com/b/txtiUe3Y/cleangallery

우리의 프로젝트는 [CleanArchitecture](https://github.com/bufferapp/clean-architecture-components-boilerplate) 를 기반으로 개발하고 있습니다.


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

