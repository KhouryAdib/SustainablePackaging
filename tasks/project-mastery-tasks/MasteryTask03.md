## Mastery Task 3: Time is marching On

**Unlocks after:**

* Milestone 1+: Lesson 3, *Inheritance Day 2, Inheritance and Polymorphism*

&nbsp;

Currently, our code only supports a single type of packaging corresponding to corrugate boxes. Amazon’s packaging
options have expanded to include polybags, and may expand to even more types in the future. Your PM has warned your
team that the first FC with polybags is going to onboard soon, and we need to prepare.

&nbsp;

### Milestone 1: Design

You should plan for at least two new packaging types: `Box` and `PolyBag`. We'd like to update our service to support
these new and future packaging types without requiring changes to the `PackagingDao`, `FcPackagingOption`, or the 
`ShipmentService`. We can do this by extending from the `Packaging` class. We know that no other teams use our `types` Java package, so it’s safe to change any of its classes. 
Both `Box` and `PolyBag` will both need to implement the methods `canFitItem()` and `getMass()`, which are used in 
determining the best shipment option. Below are details about how to calculate each.

A `Box` is made of `CORRUGATE`; has fixed `length`, `width`, and `height` (all measured in centimeters); and can fit
any item that is smaller in each dimension. (The Amazon catalog standard ensures that every `Item` is measured with its
shortest dimension in `width` and its longest in `height`. Boxes are measured the same way, so we can tell an item
won’t fit if any item dimension is larger than or equal to the same box dimension. Try it out until you’re convinced!) 
A `Box` has mass that is about 1 gram per square centimeter. We’ll simplify the calculations by ignoring any
overlapping flaps and only considering the exposed area, as represented by this pseudo code:

```
    endsArea = length * width * 2;
    shortSidesArea = length * height * 2;
    longSidesArea = width * height * 2;
    mass = endsArea + shortSidesArea + longSidesArea;
```

**HINT:** Remember that with BigDecimals, we can't use +, *, -, /, etc operators. Instead, we must use the class methods
`add`, `multiply`, etc. Addtionally we can turn an integer into a BigDecimal using the static `valueOf` method.

A `PolyBag` is made of `LAMINATED_PLASTIC`; has a fixed `volume` (in cubic centimeters); and can fit any `Item` whose
volume (length \* width \* height) is smaller than the bag’s volume. (The actual formula is quite complicated, but 
volume is a reasonable approximation in most cases.) Note that a polybag therefore should not have a `length`, `width`,
or `height` property, so we'll need to remove the fields from the `Packaging` parent class and move them to the new
`Box` class.
 
A `PolyBag` has mass that is a bit more compilcated to derive. The Data Engineering team handed you this code, which 
they say gets "close enough":

```
    mass = Math.ceil(Math.sqrt(volume) x 0.6);
```

Note: `Math.sqrt()` doesn’t support accepting a `BigDecimal`. We’ll have to make an approximation using `double` values, 
and covert that back to `BigDecimal`. The Data Engineering team confirms that’ll be sufficient.

Create a new smaller, focused class diagram with the changes you plan to make for this task. The diagram should include 
the changes you plan to make to the `types` package. This should include any classes you change or add, and any
relationships between them. Create a new file in the `src/resources` directory called `mastery_task_03_CD.puml` and 
add the plant uml source code to the file. 

You can run the MT3DesignIntrospection test to ensure you've met the requirements for this design. Either directly or by
running  `./gradlew -q clean :test --tests 'tct.MT3DesignIntrospection'`

&nbsp;

### Milestone 2: Implementation

Implement your new design. You likely will hit a point where you're not sure what to do about `getMass()` or 
`canFitItem()` in your `Packaging` class. One way to handle a method that needs to exist, but doesn't have any logical 
implementation is to just return some default value. However, this allows your methods to be used without the caller
knowing they really shouldn't be calling these methods. Another way is to implement it by throwing an exception that 
says "This method is not supported!". We can do that by throwing a `UnsupprtedOperationException`. Now, if anyone
calls these methods, they'll get a strong signal that they shouldn't be!

**Optional Side Quest: Kandinsky Class**
An even better way to handle a method that has no real implementation is to use an abstract method. These aren't 
required for you to learn at ATA, but if you are willing to accept this side quest, you can read more about creating
abstract methods in your `Packaging` class in the [Oracle Java documentation](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html).

In addition to implementing your design you'll need to make a few more changes. You'll need
to update the `PackagingDatastore`. Its `createFcPackagingOption()` method creates `Packaging` objects that get used to
create `FcPackagingOption`s. Our service will continue to use boxes as the only packaging option, so let's update
the code to create `Box` objects to pass to the `FcPackagingOption`s. Remember though, above we mentioned that we did
not want to change the `FcPackagingOption` class, so it will still need to accept an object that is-A `Packaging`.

Next, we'll need to update our `ShipmentOption` selection logic. We currently choose the option with the lowest monetary 
cost. However, we currently only know how to calculate the monetary cost of `CORRUGATE` packaging. We now have 
`LAMINATED_PLASTIC` as well. Let's open up `MonetaryCostStrategy` and make a couple updates so we can accurately 
calculate the cost of shipping polybags. The only method here, `getCost()` multiplies the cost per gram of the packaging 
material by the mass in grams of the packaging. It then adds in the cost of labor to get the total cost to ship. Next,
let's take a look at the variables at the top of the file. We have a class constant, `LABOR_COST`, that stores the labor 
cost we mentioned above. You'll be learning about constants in our Statics lesson, but for now you should understand 
that it is a value that won't change per object. The cost of labor will always be 43 cents every time we use this class.
There is also a `Map` called `materialCostPerGram`, which is used by the `getMass()` method to get the cost per gram of
the packaging's material. We will also learn about `Map` in this unit! You might be doing this task before we get there 
though, so we will walk you through how we need to update it. A map has what we call a key and a value. This map has 
material type as its key, and a value that represents the cost per gram of that material. We will need to add a new 
key, value pair, `LAMINATED_PLASTIC : 00.25`. Polybags are much more expensive per gram, but weigh much less! To add a 
new key, value pair, or an entry, to the map, you will use the `put()` method. Add the following line to your 
constructor:
```
    materialCostPerGram.put(Material.LAMINATED_PLASTIC, BigDecimal.valueOf(.25));
```

Before we are finished with this task we will need to make sure our code is properly tested. This means writing unit 
tests for new classes, and updating the existing unit tests. In any existing test classes that depended on `Packaging` 
you can take a similar approach to the changes you made to `FcPackagingOption`, using boxes as the packaging object. 
Please also include a new test in `MonetaryCostStrategyTest` validating the `getCost()` method for a polybag.
 
You may notice that the `PolyBag` class isn’t referenced anywhere in our code or data yet. That’s not a bad thing, 
since the existing fulfillment centers (FCs) can use our new code and get confident that it works before we introduce 
any other changes.

**HINTS:** There are some tests in `ShipmentOptionTest` that check for equality between `Packages` (which you will be
instantiating as `Box`s). For these tests to pass you will need to Generate `equals` and `hashcode` inside the `Box`
class. Boxes with different dimensions are not equal.

Once your design is implemented, the MT3 tests should pass.

**Exit Checklist**
-  `./gradlew -q clean :test --tests 'tct.MT3*'` passes
-  `./gradlew -q clean :test --tests 'com.amazon.ata.*'` passes
- You've pushed to github
