## Mastery Task 7: We Will Mock You

**Unlocks after:**

* Milestone 1: Lesson 8, *Mocking*

The good news: we just hired a senior engineer for the team. The bad news: she is outraged that we do not use mocks in
our `ShipmentService` tests! She explains that without mocks, our unit tests are brittle because they depend on the
contents of the *actual* `PackagingDatastore`. This means we are dependent on actual data, that could be changed at any
time. If it changes then our unit tests could fail, and we can't deploy new code! 

Granted, all our project code is locally available Java classes; but this problem becomes even worse if we are depending
on an external class for our unit tests to pass. Let's fix this problem by updating our `ShipmentServiceTest` class
to use mocks and avoid calling the actual `PackagingDao`. Since the `PackagingDao` depends on the `PackagingDataStore`
we will have removed our use of actual data in our `ShipmentService` tests by mocking the `PackagingDao`.

You can use `PrepareShipmentActivityTest` as a reference, since its tests already use mocks.

Feel free to mock dependencies in any other tests, too; mocks are really neat, and we’re not going to stop you from
getting more experience if you like. 

**Exit Checklist**
- `./gradlew -q clean :test --tests 'tct.MT7*'` passes
- All TCTs should now pass - `./gradlew -q clean :test --tests 'tct*'` passes
- `./gradlew -q clean :test --tests 'com.amazon.ata.*'` passes
- You're `ShipmentServiceTest` no longer depends on the actual implementation of `PackagingDAO`
