package tct;

import com.amazon.ata.test.helper.AtaTestHelper;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramContains;
import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramContainsClass;
import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramContainsInterface;
import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramIncludesImplementsRelationship;
import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramTypeContainsMethod;
import static com.amazon.ata.test.helper.PlantUmlClassDiagramHelper.classDiagramIncludesContainsRelationship;
import static com.amazon.ata.test.helper.PlantUmlClassDiagramHelper.classDiagramIncludesRelationship;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("MT05_DESIGN")
public class MT5DesignIntrospectionTests {
    private static final String CLASS_DIAGRAM_PATH = "mastery_task_05_CD.puml";

    @ParameterizedTest
    @ValueSource(strings = {"CostStrategy", "MonetaryCostStrategy", "CarbonCostStrategy", "WeightedCostStrategy"})
    void mt5_design_getClassDiagram_containsNewStrategyTypes(String strategyType) {
        // GIVEN - diagram path, expected type name
        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(CLASS_DIAGRAM_PATH);

        // THEN - diagram includes expected strategy class
        assertClassDiagramContains(content, strategyType);
    }

    @Test
    void mt5_design_getClassDiagram_containsCostStrategyAsInterface() {
        // GIVEN - diagram path, expected interface name
        String interfaceName = "CostStrategy";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(CLASS_DIAGRAM_PATH);

        // THEN - CostStrategy is an interface
        assertClassDiagramContainsInterface(content, interfaceName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"MonetaryCostStrategy", "CarbonCostStrategy", "WeightedCostStrategy"})
    void mt5_design_getClassDiagram_containsCostStrategyImplementationClasses(String className) {
        // GIVEN - diagram path, expected class name
        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(CLASS_DIAGRAM_PATH);

        // THEN - class declaration is found
        assertClassDiagramContainsClass(content, className);
    }

    @Test
    void mt5_design_getClassDiagram_costStrategyHasGetCostMethod() {
        // GIVEN - diagram path
        // expected type
        String interfaceName = "CostStrategy";
        // expected method
        String methodName = "getCost";
        // expected arg type
        List<String> argTypes = ImmutableList.of("ShipmentOption");
        // expected return type
        String returnType = "ShipmentCost";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(CLASS_DIAGRAM_PATH);

        // THEN - CostStrategy has expected method
        assertClassDiagramTypeContainsMethod(content, interfaceName, methodName, returnType, argTypes);
    }

    @ParameterizedTest
    @ValueSource(strings = {"MonetaryCostStrategy", "CarbonCostStrategy", "WeightedCostStrategy"})
    void mt5_design_getClassDiagram_costStrategyHasConcreteImplementations(String costStrategySubclass) {
        // GIVEN - diagram path, name of class implementing CostStrategy
        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(CLASS_DIAGRAM_PATH);

        // THEN - found class implementing CostStrategy
        assertClassDiagramIncludesImplementsRelationship(content, costStrategySubclass, "CostStrategy");
    }

    @Test
    void mt5_design_getClassDiagram_weightedCostContainsCostImplementations() {
        // GIVEN - diagram path, name of class WeightedCostStrategy uses
        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(CLASS_DIAGRAM_PATH);

        boolean hasWeightedContainsRelationship =
                classDiagramIncludesContainsRelationship(content, "WeightedCostStrategy", "CostStrategy") ||
                        (classDiagramIncludesRelationship(content, "MonetaryCostStrategy", "WeightedCostStrategy") &&
                                classDiagramIncludesRelationship(content, "CarbonCostStrategy", "WeightedCostStrategy")
                        );

        // THEN - found class relationship
        assertTrue(hasWeightedContainsRelationship,
                "Expected WeightedCostStrategy to have a contains relationship with other strategy classes.");
    }
}
