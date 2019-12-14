package com.zhy.coroutines

import kotlinx.coroutines.runBlocking
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.junit.Arquillian
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.asset.EmptyAsset
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before

@RunWith(Arquillian::class)
class TryCoroutineTest {

    lateinit var aCase : TryCoroutine
    @Before
    fun setUp() {
        aCase = TryCoroutine()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun launchJob() {
        runBlocking {
            aCase.launchJob()
        }
    }

    companion object {
        @Deployment
        fun createDeployment(): JavaArchive {
            return ShrinkWrap.create(JavaArchive::class.java)
                    .addClass(TryCoroutine::class.java)
                    .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
        }
    }
}
