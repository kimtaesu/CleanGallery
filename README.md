# CleanGallery
Trello : https://trello.com/b/txtiUe3Y/cleangallery

우리의 프로젝트는 [CleanArchitecture][cleancode] 를 기반으로 개발하고 있습니다.


## Step 1.
요구사항 : Local Storage에 있는 Images  _List up_

과정

 * Permission **READ_EXTERNAL_STORAGE** 권한 부여 로직 : [PermissionsDispatcher](https://github.com/permissions-dispatcher/PermissionsDispatcher) library로 해결
 * Local Storage Image 목록 _List up_ : [Simple-Gallery](https://github.com/SimpleMobileTools/Simple-Gallery) 로직 중 **Android Media**에서 Images를 가져올 수 있는
 로직을 사용.

[MediaFixture](https://github.com/kimtaesu/CleanGallery/blob/master/app/src/test/java/com/hucet/clean/gallery/fixture/MediaFixture.kt) Testable 환경 구축으로 앞으로의 요구사항 "Sort, Filter, Video etc.." 다양한 Test case 에 적용할 경우 용이함.
또한 *Local Storage*를 Data Source로 추상화하여 추가적인 Data Source(Cache, Network etc..) 에 대응.

 [cleancode]: https://github.com/bufferapp/clean-architecture-components-boilerplate
