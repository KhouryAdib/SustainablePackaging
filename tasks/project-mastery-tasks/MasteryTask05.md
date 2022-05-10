## Mastery Task 5: The Cost of Progress

**Unlocks after:** 

* Milestone 1+: Lesson 6, *Design with Composition*

&nbsp;

Like most teams at Amazon, the Sustainability team follows the Scrum process. This
means you plan work in two-week "sprints", review your progress every day in the "standup" meeting, and review your
accomplishments with the stakeholders during the "retrospective" meeting at the end of each sprint. 

During sprint planning, you propose that we should include the package’s environmental impact in the shipment
recommendation. Currently, which packaging we recommend for a shipment is based entirely on the monetary cost of the
packaging. Take a look at the `ShipmentService` class to see how a shipment option is selected based on lowest cost.
This class relies on the `MonetaryCostStrategy` to determine the cost of a packaging option in USD. You notice this
class implements an interface, `CostStrategy`. This gets you thinking that environmental impact can be thought of as a
carbon cost, a `CarbonCostStrategy`!

Little do you know, but you've stumbled upon the Strategy pattern, a classic software design pattern. The goal of the
Strategy pattern is to allow a piece of software to switch between different strategies to solve the same problem
easily. Here the problem we are trying to solve, is which packaging option to recommend. Our existing strategy selects
the packaging based on minimizing the monetary cost, but you'll be creating a new strategy that selects the packaging
based on minimizing the carbon cost. The Strategy pattern lets you store algorithms in separate classes and make them
interchangeable.

If we decide to track a new cost type in the future, we can simply develop a new implementation of `CostStrategy`.
If you'd like to learn more about the Strategy Design Pattern, we recommend
[this tutorials point article](https://www.tutorialspoint.com/design_pattern/strategy_pattern.htm). In the terminology
of this article, `CostStrategy` is our *Strategy* interface, and both `MonetaryCostStrategy` and `CarbonCostStrategy`
are our concrete strategy classes. The `ShipmentService` class is our *Context* class.

### Milestone 1:  At What Cost?

You confer with your Data Engineering team, who crunch the numbers and determine that **all** the current *and*
expected cost types can be calculated from two measurements: the amount of material (grams of mass) in the packaging
and the cost per gram of material. In mastery task 3, you added a method to each packaging type to get the amount
of material used, `getMass()`.

The `MonetaryCostStrategy` determines a cost in USD by taking the mass in grams and multiplying it by the cost of the
type of material per gram.

For the environmental cost, the Data Engineering team does some research and comes up with a **sustainability index**
for each material, expressed in "carbon units" (cu) per gram of mass. `CORRUGATE` is 0.017 cu per gram, and
`LAMINATED_PLASTIC` is 0.012 cu per gram. Just multiply the package’s mass by the sustainability index of its material
to get the carbon cost in cu. Add these factors to the `CarbonCostStrategy` class and implement the calculation.

To compare, our B2K `Box` would cost 17cu, while the equivalent P20 `PolyBag` would be only 0.324 cu (although its
per-gram carbon cost is similar, it weighs much less). With the `MonetaryCostStrategy`, the same B2K `Box` costs $5.43,
while the P20 `PolyBag` costs $7.18. While the `PolyBag` monetarily costs 32% more than the `Box`, we are reducing the
carbon credits we are using by 98%!

Implement the `CarbonCostStrategy` in the `strategy` package using these formulas and write unit tests to verify they
work.

When you are done, check that the `MT5CarbonIntrospectionTests` tests pass.

### Milestone 2: Designing a Blended Cost
**Reviewer: Project Buddy**

After discussing further with the team, you start to dig into what it means to return the *best* packaging option.
Your team decides to recommend packaging based on a combination of monetary cost and carbon cost. Your team decides that
the appropriate balance is 80% monetary cost and 20% carbon cost.

The blended cost of our **B2K** `Box` is `7.7440`, and our **P20** `PolyBag` is `5.80880`. (Don't worry about the units
here; it's an arbitrary unit based on our 80/20 split.) That means even with our blended strategy, we should still
select the `PolyBag` as our lowest cost packaging.

You *Disagree and Commit*, put aside your personal opinions about the appropriate ratios, and get cracking on the new
code. Create a class diagram to represent your design for a `WeightedCostStrategy`. Create a
new file in the `src/resources` directory called `mastery_task_05_CD.puml` with the PlantUML.

When you are done, check that the `MT5DesignIntrospectionTests` tests pass.

**Optional Side Quest: Future Costs**

Rather than hard-coding the 80/20 split, your `WeightedCostStrategy` would be more flexible if it accepted any number
of `CostStrategy` objects, each with its own weight. You could use a Builder pattern, like you saw in the *Delivering on
Our Promise!* project, with an `addStrategyWithWeight(CostStrategy, BigDecimal)` method to make it obvious which weights
went with each strategy; that would also let you guarantee the calling code had provided at least one strategy. This
will require a little more code, but it will also make it easy to change the cost calculation in the future.

### Milestone 3: Composing the Cost
**Reviewer: Project Buddy**

At this point in our story, several more FCs have onboarded with polybag options.
Add new `PolyBag` `FcPackagingOption`s to the `PackagingDatastore`, using the FC code and polybag volume pairs below.

```
"IAD2", "5000"
"YOW4", "2000"
"YOW4", "5000"
"YOW4", "10000"
"IND1", "2000"
"IND1", "5000"
"ABE2", "2000"
"ABE2", "6000"
"PDX1", "5000"
"PDX1", "10000"
"YOW4", "5000"
```

&nbsp;

Implement your design for the `WeightedCostStrategy` in the `strategy` package. Update the `getCostStrategy()` method
in the `App` class to return your newly created `WeightedCostStrategy` instead of a `MonetaryCostStrategy`.

When you are done, check that the `MT5WegithedIntrospectionTests` tests all pass.

**Exit Checklist**
- `./gradlew -q clean :test --tests 'tct.MT5*'` passes
- `./gradlew -q clean :test --tests 'com.amazon.ata.*'` passes
