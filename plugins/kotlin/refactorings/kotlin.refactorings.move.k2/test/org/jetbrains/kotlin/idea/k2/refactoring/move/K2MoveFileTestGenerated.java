// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

package org.jetbrains.kotlin.idea.k2.refactoring.move;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.idea.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.idea.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.TestMetadata;
import org.jetbrains.kotlin.idea.base.test.TestRoot;
import org.junit.runner.RunWith;

/**
 * This class is generated by {@link org.jetbrains.kotlin.testGenerator.generator.TestGenerator}.
 * DO NOT MODIFY MANUALLY.
 */
@SuppressWarnings("all")
@TestRoot("refactorings/kotlin.refactorings.move.k2")
@TestDataPath("$CONTENT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
@TestMetadata("../../idea/tests/testData/refactoring/moveFile")
public class K2MoveFileTestGenerated extends AbstractK2MoveFileTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, this, testDataFilePath);
    }

    @TestMetadata("java/moveFilePackageVisibilityInaccessible/moveFilePackageVisibilityInaccessible.test")
    public void testJava_moveFilePackageVisibilityInaccessible_MoveFilePackageVisibilityInaccessible() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/java/moveFilePackageVisibilityInaccessible/moveFilePackageVisibilityInaccessible.test");
    }

    @TestMetadata("java/moveFileToAnotherPackage/moveFileToAnotherPackage.test")
    public void testJava_moveFileToAnotherPackage_MoveFileToAnotherPackage() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/java/moveFileToAnotherPackage/moveFileToAnotherPackage.test");
    }

    @TestMetadata("kotlin/addExtensionImport/addExtensionImport.test")
    public void testKotlin_addExtensionImport_AddExtensionImport() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/addExtensionImport/addExtensionImport.test");
    }

    @TestMetadata("kotlin/callableReferences/callableReferences.test")
    public void testKotlin_callableReferences_CallableReferences() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/callableReferences/callableReferences.test");
    }

    @TestMetadata("kotlin/internalReferences/internalReferences.test")
    public void testKotlin_internalReferences_InternalReferences() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/internalReferences/internalReferences.test");
    }

    @TestMetadata("kotlin/keepImportAliasRefs/keepImportAliasRefs.test")
    public void testKotlin_keepImportAliasRefs_KeepImportAliasRefs() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/keepImportAliasRefs/keepImportAliasRefs.test");
    }

    @TestMetadata("kotlin/longName/longName.test")
    public void testKotlin_longName_LongName() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/longName/longName.test");
    }

    @TestMetadata("kotlin/moveClassWithExtensionFunction/moveClassWithExtensionFunction.test")
    public void testKotlin_moveClassWithExtensionFunction_MoveClassWithExtensionFunction() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/moveClassWithExtensionFunction/moveClassWithExtensionFunction.test");
    }

    @TestMetadata("kotlin/moveFileAndDirWithJavaFileReferringToPackageFragementWithUnmatchedDir/moveFileAndDirWithJavaFileReferringToPackageFragementWithUnmatchedDir.test")
    public void testKotlin_moveFileAndDirWithJavaFileReferringToPackageFragementWithUnmatchedDir_MoveFileAndDirWithJavaFileReferringToPackageFragementWithUnmatchedDir() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/moveFileAndDirWithJavaFileReferringToPackageFragementWithUnmatchedDir/moveFileAndDirWithJavaFileReferringToPackageFragementWithUnmatchedDir.test");
    }

    @TestMetadata("kotlin/moveFilePackageVisibilityInaccessible/moveFilePackageVisibilityInaccessible.test")
    public void testKotlin_moveFilePackageVisibilityInaccessible_MoveFilePackageVisibilityInaccessible() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/moveFilePackageVisibilityInaccessible/moveFilePackageVisibilityInaccessible.test");
    }

    @TestMetadata("kotlin/moveFileToFile/moveFileToFile.test")
    public void testKotlin_moveFileToFile_MoveFileToFile() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/moveFileToFile/moveFileToFile.test");
    }

    @TestMetadata("kotlin/moveFileWithDotsAsFileReferences/moveFileWithDotsAsFileReferences.test")
    public void testKotlin_moveFileWithDotsAsFileReferences_MoveFileWithDotsAsFileReferences() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/moveFileWithDotsAsFileReferences/moveFileWithDotsAsFileReferences.test");
    }

    @TestMetadata("kotlin/moveFileWithPackageRename/moveFileWithPackageRename.test")
    public void testKotlin_moveFileWithPackageRename_MoveFileWithPackageRename() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/moveFileWithPackageRename/moveFileWithPackageRename.test");
    }

    @TestMetadata("kotlin/moveFileWithoutDeclarations/moveFileWithoutDeclarations.test")
    public void testKotlin_moveFileWithoutDeclarations_MoveFileWithoutDeclarations() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/moveFileWithoutDeclarations/moveFileWithoutDeclarations.test");
    }

    @TestMetadata("kotlin/moveFileWithoutPackageRename/moveFileWithoutPackageRename.test")
    public void testKotlin_moveFileWithoutPackageRename_MoveFileWithoutPackageRename() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/moveFileWithoutPackageRename/moveFileWithoutPackageRename.test");
    }

    @TestMetadata("kotlin/moveLastFileInPackageWithPackageRename/moveLastFileInPackageWithPackageRename.test")
    public void testKotlin_moveLastFileInPackageWithPackageRename_MoveLastFileInPackageWithPackageRename() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/moveLastFileInPackageWithPackageRename/moveLastFileInPackageWithPackageRename.test");
    }

    @TestMetadata("kotlin/moveMultipleFilesWithImplicitPrefix/moveMultipleFilesWithImplicitPrefix.test")
    public void testKotlin_moveMultipleFilesWithImplicitPrefix_MoveMultipleFilesWithImplicitPrefix() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/moveMultipleFilesWithImplicitPrefix/moveMultipleFilesWithImplicitPrefix.test");
    }

    @TestMetadata("kotlin/moveMultipleFiles/moveMultipleFiles.test")
    public void testKotlin_moveMultipleFiles_MoveMultipleFiles() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/moveMultipleFiles/moveMultipleFiles.test");
    }

    @TestMetadata("kotlin/moveObject/moveObject.test")
    public void testKotlin_moveObject_MoveObject() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/moveObject/moveObject.test");
    }

    @TestMetadata("kotlin/moveUnaryOperator/moveUnaryOperator.test")
    public void testKotlin_moveUnaryOperator_MoveUnaryOperator() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/moveUnaryOperator/moveUnaryOperator.test");
    }

    @TestMetadata("kotlin/packageWithQuotation/packageWithQuotation.test")
    public void testKotlin_packageWithQuotation_PackageWithQuotation() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/packageWithQuotation/packageWithQuotation.test");
    }

    @TestMetadata("kotlin/selfReferenceInImport/selfReferenceInImport.test")
    public void testKotlin_selfReferenceInImport_SelfReferenceInImport() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/selfReferenceInImport/selfReferenceInImport.test");
    }

    @TestMetadata("kotlin/typeRefWithArguments/typeRefWithArguments.test")
    public void testKotlin_typeRefWithArguments_TypeRefWithArguments() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/typeRefWithArguments/typeRefWithArguments.test");
    }

    @TestMetadata("kotlin/withoutUsages/withoutUsages.test")
    public void testKotlin_withoutUsages_WithoutUsages() throws Exception {
        runTest("../../idea/tests/testData/refactoring/moveFile/kotlin/withoutUsages/withoutUsages.test");
    }
}
