# CleanGallery
Gallery라는 주제로 Clean Code를 지향하며 Architecture를 보다 나은 방향으로 학습하기 위한 용도의 프로젝트입니다.

> Source는 99% [Kotlin](https://kotlinlang.org/)으로 작성되었습니다.  Unit Test는 100% [Kotlin-Spek](https://github.com/spekframework/spek)으로 작성되었습니다.

## Demos
### Directory Category

<img src="https://github.com/kimtaesu/CleanGallery/blob/master/document/directory.gif" alt="Smiley face" height="400" width="230">

### Date Category

<img src="https://github.com/kimtaesu/CleanGallery/blob/master/document/date.gif" alt="Smiley face" height="400" width="230">

### Sort

<img src="https://github.com/kimtaesu/CleanGallery/blob/master/document/sort.gif" alt="Smiley face" height="400" width="230">

### Filter

<img src="https://github.com/kimtaesu/CleanGallery/blob/master/document/filter.gif" alt="Smiley face" height="400" width="230">

## Languages, libraries and tools used

* [Kotlin](https://kotlinlang.org/)
* Android Support Libraries
* [RxJava2](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0)
* [Dagger 2 (2.11)](https://github.com/google/dagger)
* [Glide (4)](https://github.com/bumptech/glide)
* [Gson](https://github.com/google/gson)
* [Timber](https://github.com/JakeWharton/timber)
* [Mockito](http://site.mockito.org/)
* [Spek](https://github.com/spekframework/spek)

## Architecture
![](https://github.com/kimtaesu/CleanGallery/blob/master/document/architenture.jpg)

### User Interface
[MainActivity](https://github.com/kimtaesu/CleanGallery/blob/master/app/src/main/java/com/hucet/clean/gallery/activity/MainActivity.kt)에서 표시 할 UI 구성 요소를 만드는 데 사용됩니다. [GalleryPresenter](https://github.com/kimtaesu/CleanGallery/blob/master/app/src/main/java/com/hucet/clean/gallery/presenter/GalleryPresenter.kt) 를 사용하여 Media 데이터를 가져올 수 있습니다.

### Presentation
이 Layer는 UI를 표현하는데 사용되지만, 동시에 UI 자체에 대해서는 알지 못합니다. 즉, 안드로이드 프레임 워크에 의존하지 않기 때문에 Test 환경을 쉽게 구성할 수 있습니다.  [GalleryRepository](https://github.com/kimtaesu/CleanGallery/blob/master/app/src/main/java/com/hucet/clean/gallery/repository/GalleryRepository.kt)를 사용하여 Media 데이터를 가져올 수 있습니다.

### Data
Data Layer는 여러 External data layer 대한 **Access Point**입니다. 현재는 Local data layer만 구현되어 있으며, 추후 요구사항에 따라 여러 Cache, Network data source로 부터 데이터를 가져오는 데 사용됩니다.

> Data layer의 추상화는 _Over Engineering_ 일 수 있습니다. Data layer는 **저 수준이기 때문에 Data Layer의 변경은 고 수준(UI, Presentation) 범위를 포함될 수 있습니다.** 또한 실제 문제가 뒤 늦게 나타날 수도 있으며, 시간이 길어질 수록 그동안 구현한 코드를 변경하는 것이 어려울 수 있습니다.

### Local
Local Layer는 [MediaFetcher](https://github.com/kimtaesu/CleanGallery/blob/master/app/src/main/java/com/hucet/clean/gallery/datasource/local/MediaFetcher.kt)를 사용하여 데이터를 가져올 수 있습니다. MediaFetcher는 [ContentResolver](https://developer.android.com/reference/android/content/ContentResolver.html)의 Query를 통해 Local의 저장되어 있는 Image, Video, Gif를 가져옵니다.

## Denpencies Graph
![](https://raw.githubusercontent.com/kimtaesu/CleanGallery/master/document/di.jpg)

Dagger2를 사용하여 DI를 구현하였으며, 더 나아가 **Graph**를 작성함으로써 명확하게 이해를 도울 수 있습니다.

각 객체들에 대한 자세한 설명은 앞으로 진행할 것 입니다.
> 아래 그림에서 Provide - Inject 관계 연결은 생략하였습니다.



## Design Patterns
### MediaFilter (Chain of responsibility)

<img src="https://github.com/kimtaesu/CleanGallery/blob/master/document/screen_filter.png" alt="Smiley face" height="230" width="230">

Media query를 조회할 경우 모든 File이 조회되도록 설정하였습니다.
[ImageVideoGifFilter](https://github.com/kimtaesu/CleanGallery/blob/master/app/src/main/java/com/hucet/clean/gallery/gallery/filter/ImageVideoGifFilter.kt)는 우리가 필요한 Media type [Image, Video, Gif]의 Filter를 담당합니다.

![](https://github.com/kimtaesu/CleanGallery/blob/master/document/design_pattern_chainOfResponsibility.jpg)

### Classification (Strategy)
<img src="https://github.com/kimtaesu/CleanGallery/blob/master/document/screen_category.png" alt="Smiley face" height="100" width="300">

[Date, Directory] 별로 Media를 탐색 할 수 있어야 하는 요구사항이 있었습니다. 우리는 이것을 **Category**로 명칭을 정했습니다.
Category는 Date, Direcotry 각각의 요소로  Groupping 되어야 합니다. Strategy 패턴은 이 행위를 표현하는데 적절합니다.

![](https://github.com/kimtaesu/CleanGallery/blob/master/document/design_pattern_strategy.jpg)

### SetUp View Mode (State)
<img src="https://github.com/kimtaesu/CleanGallery/blob/master/document/screen_viewmode.png" alt="Smiley face" height="100" width="300">

[Linear, Grid] 두 가지 Type의 User Interface의 요구사항이 있었습니다.  이것를 **ViewMode**라고 명칭을 정했습니다.

Client의 요청에 의해 Context의 미리 정의된 State[Linear, Grid]에 따라서 User Interface가 표현됩니다.

![](https://github.com/kimtaesu/CleanGallery/blob/master/document/design_pattern_state.jpg)

### Adapter Delegation (Adapter)
아래의 UML이 복잡하게 보이지만 간단합니다.
* Target : GalleryAdapter
* Adapter : LinearAdapter, GridAdapter
* Adaptee : DirectoryLinearDelegateAdapter, MediumLinearDelegateAdapter, DirectoryGridDelegateAdapter, MediumGridDelegateAdapter, DateGridDelegateAdapter

만약 이 요구사항을 하나의 Adapter에서 if ~ else문으로 해결해야 했다면, 많은 문제를 극복해야 했습니다.
* 동적으로 LayoutParam 수정
* Linear, Grid의 View 차이점
* etc...

Adapter 패턴으로 구조를 잡음으로써 **각 Class는 하나의 책임을 가지게 됩니다.** 즉, 다른 변경 사항의 영향을 받지 않습니다.

![](https://github.com/kimtaesu/CleanGallery/blob/master/document/design_pattern_adapter.jpg)

### ReadOnlyConfigs (Builder)

Client(MainActivity) 가 아닌 다른 곳에서 ApplicationConfig 를 수정하면 Application 전반적인 Sync 가 맞지 않아서 좋지 않은 상황이 발생합니다. ReadOnlyConfigs 는 Read만 할 수 있도록 만든 Class로 각각의 Field에 대해서 Builder 형태로 제공됩니다.

```kotlin
config.ReadOnlyConfigBuild {
                    viewMode()
                    filterType()
                    sortType()
                    categoryMode()
                }
```
![](https://github.com/kimtaesu/CleanGallery/blob/master/document/design_pattern_builder.jpg)


