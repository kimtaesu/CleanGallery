# CleanGallery
Trello : https://trello.com/b/txtiUe3Y/cleangallery

우리의 프로젝트는 [CleanArchitecture](https://github.com/bufferapp/clean-architecture-components-boilerplate) 를 기반으로 학습을 목적으로 진행하고 있습니다.
요구사항을 도출하고 최적의 해결 방안을 모색하는 것을 학습할 수 있습니다.

## Demos
**[Directory]**

<img src="https://github.com/kimtaesu/CleanGallery/blob/master/document/directory.gif" alt="Smiley face" height="400" width="230">

**[Date]**

<img src="https://github.com/kimtaesu/CleanGallery/blob/master/document/date.gif" alt="Smiley face" height="400" width="230">

**[Sort]**

<img src="https://github.com/kimtaesu/CleanGallery/blob/master/document/sort.gif" alt="Smiley face" height="400" width="230">

**[Filter]**

<img src="https://github.com/kimtaesu/CleanGallery/blob/master/document/filter.gif" alt="Smiley face" height="400" width="230">

## Languages, libraries and tools used

* [Kotlin](https://kotlinlang.org/)
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
* Android Support Libraries
* [RxJava2](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0)
* [Dagger 2 (2.11)](https://github.com/google/dagger)
* [Glide (4)](https://github.com/bumptech/glide)
* [Gson](https://github.com/google/gson)
* [Timber](https://github.com/JakeWharton/timber)
* [Mockito](http://site.mockito.org/)
* [Spek](https://github.com/spekframework/spek)

## Denpencies Graph
Dagger2를 사용하여 DI를 구현하였으며, 더 나아가 **Graph**를 작성함으로써 명확하게 이해를 도울 수 있습니다.

각 객체들에 대한 자세한 설명은 앞으로 진행할 것 입니다.
> 아래 그림에서 Provide - Inject 관계 연결은 생략하였습니다.
![](https://raw.githubusercontent.com/kimtaesu/CleanGallery/master/document/di.jpg)

## Architecture
![](https://github.com/kimtaesu/CleanGallery/blob/master/document/architenture.jpg)

## Design Patterns
### Filter (Chain of responsibility)

![](https://github.com/kimtaesu/CleanGallery/blob/master/document/design_pattern_chainOfResponsibility.jpg)

### Classification (Strategy)

![](https://github.com/kimtaesu/CleanGallery/blob/master/document/design_pattern_strategy.jpg)

### SetUp View Mode (State)

![](https://github.com/kimtaesu/CleanGallery/blob/master/document/design_pattern_state.jpg)

### Adapter Delegation (State)

![](https://github.com/kimtaesu/CleanGallery/blob/master/document/design_pattern_state2.jpg)

### DialogRadioItem Mapper (Strategy)

![](https://github.com/kimtaesu/CleanGallery/blob/master/document/design_pattern_strategy2.jpg)

### ReadOnlyConfigs (Builder)

![](https://github.com/kimtaesu/CleanGallery/blob/master/document/design_pattern_builder.jpg)
