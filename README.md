# FetchRewards

A simple app written using Kotlin and MVVM design pattern. The app retrieves items from a remote source and filters out items with null or blank names. Then it parses a number from item's name and stores the item with an aditional nameNumber field into a table. When items are retrieved from the database they are sorted by listId and nameNumber. The results are shown in a list using Paging library 2. The app shows loading state, empty database state and error messages for failed network requests or failed database queries.

## Libraries

- Dagger 2
- Retrofit
- Room
- Paging 2
- RxJava 2
- Mockito
- JUnit

## Testing

The project contains  [Local unit tests][2] and [Instrumented tests][3].

Just run `./gradlew test` or `./gradlew connectedAndroidTest`

## Screenshots

<img width="30%" src="screenshots/fetch_rewards.png" />

[2]: app/src/test/java/com/example/fetchrewards/
[3]: app/src/androidTest/java/com/example/fetchrewards/
