// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.jetbrains.plugins.gradle.importing.syncAction

import com.intellij.gradle.toolingExtension.modelAction.GradleModelFetchPhase
import com.intellij.openapi.Disposable
import com.intellij.openapi.externalSystem.util.Order
import com.intellij.openapi.progress.ProcessCanceledException
import com.intellij.platform.workspace.storage.MutableEntityStorage
import com.intellij.testFramework.registerOrReplaceServiceInstance
import org.jetbrains.plugins.gradle.importing.GradleImportingTestCase
import org.jetbrains.plugins.gradle.model.ProjectImportModelProvider
import org.jetbrains.plugins.gradle.service.project.AbstractProjectResolverExtension
import org.jetbrains.plugins.gradle.service.project.GradleProjectResolverExtension
import org.jetbrains.plugins.gradle.service.project.ProjectResolverContext
import org.jetbrains.plugins.gradle.service.syncAction.GradleSyncContributor
import org.jetbrains.plugins.gradle.testFramework.util.createBuildFile
import org.jetbrains.plugins.gradle.testFramework.util.createSettingsFile
import org.junit.jupiter.api.Assertions
import org.opentest4j.AssertionFailedError
import org.opentest4j.MultipleFailuresError
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.cancellation.CancellationException

abstract class GradleProjectResolverTestCase : GradleImportingTestCase() {

  fun whenPhaseCompleted(parentDisposable: Disposable, action: suspend (ProjectResolverContext, MutableEntityStorage, GradleModelFetchPhase) -> Unit) {
    GradleSyncContributor.EP_NAME.point.registerExtension(
      @Order(Int.MAX_VALUE)
      object : GradleSyncContributor {
        override suspend fun onModelFetchPhaseCompleted(
          context: ProjectResolverContext,
          storage: MutableEntityStorage,
          phase: GradleModelFetchPhase
        ) {
          action(context, storage, phase)
        }
      }, parentDisposable)
  }

  fun whenModelFetchCompleted(parentDisposable: Disposable, action: suspend (ProjectResolverContext, MutableEntityStorage) -> Unit) {
    GradleSyncContributor.EP_NAME.point.registerExtension(
      @Order(Int.MAX_VALUE)
      object : GradleSyncContributor {
        override suspend fun onModelFetchCompleted(
          context: ProjectResolverContext,
          storage: MutableEntityStorage
        ) {
          action(context, storage)
        }
      }, parentDisposable)
  }

  fun whenProjectLoaded(parentDisposable: Disposable, action: suspend (ProjectResolverContext, MutableEntityStorage) -> Unit) {
    GradleSyncContributor.EP_NAME.point.registerExtension(
      @Order(Int.MAX_VALUE)
      object : GradleSyncContributor {
        override suspend fun onProjectLoadedActionCompleted(
          context: ProjectResolverContext,
          storage: MutableEntityStorage
        ) {
          action(context, storage)
        }
      }, parentDisposable)
  }

  /**
   * Gradle project resolver recreates all extensions for sync.
   * Therefore, this function doesn't allow providing an instance of extension.
   * @see org.jetbrains.plugins.gradle.service.project.GradleProjectResolverUtil.createProjectResolvers
   */
  fun addProjectResolverExtension(
    projectResolverExtensionClass: Class<out AbstractTestProjectResolverExtension>,
    parentDisposable: Disposable,
    configure: AbstractTestProjectResolverService.() -> Unit
  ) {
    val projectResolverExtension = registerProjectResolverExtension(projectResolverExtensionClass, parentDisposable)
    val projectResolverService = registerProjectResolverService(projectResolverExtension.serviceClass, parentDisposable)
    projectResolverService.configure()
  }

  private fun <T : AbstractTestProjectResolverService> registerProjectResolverService(
    projectResolverServiceClass: Class<T>,
    parentDisposable: Disposable
  ): T {
    val projectResolverService = projectResolverServiceClass.getDeclaredConstructor().newInstance()
    myProject.registerOrReplaceServiceInstance(projectResolverServiceClass, projectResolverService, parentDisposable)
    return projectResolverService
  }

  private fun <T : AbstractTestProjectResolverExtension> registerProjectResolverExtension(
    projectResolverExtensionClass: Class<T>,
    parentDisposable: Disposable
  ): T {
    val projectResolverExtension = projectResolverExtensionClass.getDeclaredConstructor().newInstance()
    GradleProjectResolverExtension.EP_NAME.point.registerExtension(projectResolverExtension, parentDisposable)
    return projectResolverExtension
  }

  fun initMultiModuleProject() {
    if (isGradleAtLeast("8.0")) {
      // For old Gradle versions, Idea sync project with build src in two sequent Gradle calls.
      // So don't use buildSrc for generic projects with multiple modules.
      createBuildFile("buildSrc") {
        withPlugin("groovy")
        addImplementationDependency(code("gradleApi()"))
        addImplementationDependency(code("localGroovy()"))
      }
    }
    createSettingsFile {
      setProjectName("project")
      include("module")
      includeBuild("../includedProject")
    }
    createBuildFile {
      withJavaPlugin()
    }
    createBuildFile("module") {
      withJavaPlugin()
    }
    createSettingsFile("../includedProject") {
      setProjectName("includedProject")
      include("module")
    }
    createBuildFile("../includedProject") {
      withJavaPlugin()
    }
    createBuildFile("../includedProject/module") {
      withJavaPlugin()
    }
  }

  fun assertMultiModuleProjectStructure() {
    val buildSrcModules = listOf(
      "project.buildSrc", "project.buildSrc.main", "project.buildSrc.test"
    )
    val projectModules = listOf(
      "project", "project.main", "project.test",
      "project.module", "project.module.main", "project.module.test"
    )
    val includesProjectModules = listOf(
      "includedProject", "includedProject.main", "includedProject.test",
      "includedProject.module", "includedProject.module.main", "includedProject.module.test"
    )
    assertModules(buildList {
      if (isGradleAtLeast("8.0")) {
        addAll(buildSrcModules)
      }
      addAll(projectModules)
      addAll(includesProjectModules)
    })
  }

  class TestProjectResolverExtension : AbstractTestProjectResolverExtension() {
    override val serviceClass = TestProjectResolverService::class.java
  }

  class TestProjectResolverService : AbstractTestProjectResolverService()

  abstract class AbstractTestProjectResolverExtension : AbstractProjectResolverExtension() {

    abstract val serviceClass: Class<out AbstractTestProjectResolverService>

    private fun getService(): AbstractTestProjectResolverService {
      val project = resolverCtx.externalSystemTaskId.findProject()!!
      return project.getService(serviceClass)
    }

    override fun getModelProviders(): List<ProjectImportModelProvider> {
      return getService().getModelProviders()
    }
  }

  abstract class AbstractTestProjectResolverService {

    private val modelProviders = CopyOnWriteArrayList<ProjectImportModelProvider>()

    fun getModelProviders(): List<ProjectImportModelProvider> {
      return modelProviders
    }

    fun addModelProviders(vararg modelProviders: ProjectImportModelProvider) {
      addModelProviders(modelProviders.toList())
    }

    fun addModelProviders(modelProviders: Collection<ProjectImportModelProvider>) {
      this.modelProviders.addAll(modelProviders)
    }
  }

  protected class ListenerAssertion {

    private val counter = AtomicInteger(0)
    private val failures = CopyOnWriteArrayList<Throwable>()

    fun reset() {
      counter.set(0)
      failures.clear()
    }

    inline fun trace(action: ListenerAssertion.() -> Unit) {
      touch()
      try {
        return action()
      }
      catch (exception: ExpectedException) {
        throw exception.original
      }
      catch (failure: Throwable) {
        addFailure(failure)
      }
    }

    fun touch() {
      counter.incrementAndGet()
    }

    fun addFailure(failure: Throwable) {
      failures.add(failure)
    }

    fun assertListenerFailures() {
      when {
        failures.size == 1 -> {
          throw AssertionError("", failures.single())
        }
        failures.size > 1 -> {
          throw MultipleFailuresError("", failures)
        }
      }
    }

    fun assertListenerState(expectedCount: Int, messageSupplier: () -> String) {
      Assertions.assertEquals(expectedCount, counter.get(), messageSupplier)
    }

    inline fun assertCancellation(action: () -> Unit, messageSupplier: () -> String) {
      try {
        action()
      }
      catch (e: CancellationException) {
        throw ExpectedException(e)
      }
      catch (e: ProcessCanceledException) {
        throw ExpectedException(e)
      }
      throw AssertionFailedError(messageSupplier())
    }

    class ExpectedException(val original: Exception) : Exception("Expected exception", original)
  }
}