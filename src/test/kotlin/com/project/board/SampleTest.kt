package com.project.board

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SampleTest : FunSpec({
    this.test("sample test") {
        println("Hello kotest world !")
        1 shouldBe 1
    }

    this.test("TEST CHECK") {
        println("SDSDS")
        1 shouldBe 1
    }
})
