package tct;

import com.amazon.ata.test.helper.AtaTestHelper;
import com.amazon.ata.test.helper.PlantUmlClassDiagramHelper;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramContainsClass;
import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramIncludesContainsRelationship;
import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramIncludesExtendsRelationship;
import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramTypeContainsMember;
import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramTypeDoesNotContainMember;
import static org.junit.jupiter.api.Assertions.fail;

@Tag("MT03_DESIGN")
public class MT3DesignIntrospectionTests {
    private static final String CLASS_DIAGRAM_PATH = "mastery_task_03_CD.puml";
    String content;

    @BeforeEach
    public void setup() {
        content = AtaTestHelper.getFileContentFromResources(CLASS_DIAGRAM_PATH)
                // Removing colors specified in diagram
                .replaceAll("#[a-zA-Z]*", "")
                // Removing stereotypes specified in diagram
                .replaceAll("<<[a-zA-Z]*>>", "");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Box", "PolyBag"})
    void mt3_design_getClassDiagram_containsNewPackagingClasses(String packagingClass) {
        // THEN - diagram includes expected packaging class
        assertClassDiagramContainsClass(content, packagingClass);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Box", "PolyBag"})
    void mt3_design_getClassDiagram_newPackagingClassesExtendPackaging(String packagingSubclass) {
        // THEN - diagram includes extends relationship from packaging subclass --> packaging
        assertClassDiagramIncludesExtendsRelationship(content, packagingSubclass, "Packaging");
    }

    @Test
    void mt3_design_getClassDiagram_packagingLosesBoxAttributes() {
        // GIVEN -- diagram path, attributes expected to be absent from Packaging
        List<String> removedMembers = ImmutableList.of("height", "width", "length");

        // THEN - diagram does not include box-specific attributes
        for (String member : removedMembers) {
            assertClassDiagramTypeDoesNotContainMember(content, "Packaging", member);
        }
    }

    @Test
    void mt3_design_getClassDiagram_packagingKeepsPackagingAttributes() {
        // THEN - find expected Material
        assertClassDiagramIncludesContainsRelationship(content, "Packaging", "Material");
    }

    @Test
    void mt3_design_getClassDiagram_packagingOrSubclassesHaveGetMass() {
        // THEN - Packaging or Box and PolyBag contains getMass()
        assertMemberInPackagingOrAllSubTypes(content, "getMass");
    }

    @Test
    void mt3_design_getClassDiagram_packagingOrSubclassesHaveCanFitItem() {
        // THEN - Packaging or Box and PolyBag contains canFitItem()
        assertMemberInPackagingOrAllSubTypes(content, "canFitItem");
    }

    @Test
    void mt3_design_getClassDiagram_boxContainsDimensions() {
        // GIVEN -- diagram path
        // Box member variables
        List<String> members = ImmutableList.of("length", "width", "height");

        // THEN - find the expected members
        for (String member : members) {
            assertClassDiagramTypeContainsMember(content, "Box", member);
        }
        // at least one of them is a BigDecimal
        assertClassDiagramTypeContainsMember(content, "Box", "BigDecimal");
    }

    @Test
    void mt3_design_getClassDiagram_materialHasAllValues() {
        // GIVEN -- diagram path
        List<String> expectedValues = ImmutableList.of("LAMINATED_PLASTIC", "CORRUGATE");

        // THEN - material has expected enum values
        for (String member : expectedValues) {
            assertClassDiagramTypeContainsMember(content, "Material", member);
        }
    }

    @Test
    void mt3_design_getClassDiagram_polyBagContainsVolume() {
        // THEN - find the expected member, find BigDecimal
        assertClassDiagramTypeContainsMember(content, "PolyBag", "volume");
        assertClassDiagramTypeContainsMember(content, "PolyBag", "BigDecimal");
    }

    private void assertMemberInPackagingOrAllSubTypes(String memberContent, String includedMember) {
        String contentWithoutAbstract = memberContent.replaceAll("\\{abstract\\}", "");
        boolean packagingContainsMember = PlantUmlClassDiagramHelper.classDiagramTypeContainsMember(
                contentWithoutAbstract, "Packaging", includedMember);
        boolean boxContainsMember = PlantUmlClassDiagramHelper.classDiagramTypeContainsMember(memberContent,
            "Box", includedMember);
        boolean polyBagContainsMember = PlantUmlClassDiagramHelper.classDiagramTypeContainsMember(memberContent,
            "Polybag", includedMember);


        if (packagingContainsMember || (boxContainsMember && polyBagContainsMember)) {
            return;
        }

        fail(String.format("In class diagram, expected the member %s to be in either Packaging or all of its subtypes.",
            includedMember));
    }
}
