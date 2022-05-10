## Mastery Task 6: I Fits, I Sits

**Unlocks after:**

* Milestone 1+: Lesson 7, *Handling Exceptions*

&nbsp;

Your team has launched polybags! You've been on the lookout for any failures due to the new polybags when calling your 
service. You haven't seen any yet. In fact you haven't seen any exceptions being thrown from your service. You know 
no software service is ever perfect, so you do a little digging!

You discover that the `ShipmentService` is "swallowing" exceptions thrown by the `PackagingDAO` and just returning null.
This seems a little fishy so you bring it up to your team in your daily stand-up meeting. They agree with you. If a 
client (a client is any code that makes calls to your service code - you can think of a client as a programmatic user) 
is passing in a `FullfillmentCenter` that your service doesn't know about you should let them know that it is 
invalid! The other exceptional case is if your service cannot find a shipment option that will fit the item. Not having 
packaging that fits the item is a condition that the client should handle appropriately, but instead of returning null 
we should return a properly-populated `ShipmentOption` with `null` packaging. Returning a `null` object is not very 
user friendly. Remember, calling methods on `null` objects can cause a `NullPointerException`. We want to help prevent 
that! You can use the `ShipmentOption.builder` to help with this.

The scrum master swaps out a stretch goal on the sprint board for "Use exceptions in ShipmentService" and assigns it to
you. Well, it’s not exactly *specific* directions, but it looks like that’s all you’re going to get.

Update the `ShipmentService` to process the `PackagingDAO`‘s exceptions, and write unit tests, of course. Here are a few 
things to keep in mind:
- You should not change or modify the behavior of the `PackagingDAO` class.
- Use a `try...catch` block to catch each possible exception thrown by `PackagingDAO`
- You should throw a `RuntimeException` in the case of receiving an `UnknownFulfillmentCenterException`
- You can check out the `PackagingDAOTest` for an example of a unit test that verifies that an exception gets thrown.

**Exit Checklist**
- You're `findShipmentOption()` method no longer returns `null` when a shipment option cannot be found to fit the item
- You're `findShipmentOption()` throws a `RuntimeException` when a client provides an unknown `FullfillmentCenter`
- Your CR handling exceptions thrown by `PackagingDAO` has been approved and the code pushed
- (No MasteryTask TCTs this time)
