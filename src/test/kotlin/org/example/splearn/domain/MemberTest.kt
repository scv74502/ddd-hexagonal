package org.example.splearn.domain

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class MemberTest {
    @Test
    fun testMemberStatus() {
        val member = Member("woo@gs.com", "Woo", "secret")
        Assertions.assertThat(member.status).isEqualTo(MemberStatus.PENDING)
    }

    @Test
    fun activate() {
        val member = Member("woo@gs.com", "Woo", "secret")
        member.activate()
        Assertions.assertThat(member.status).isEqualTo(MemberStatus.ACTIVATE)
    }

    @Test
    fun activateFail() {
        val member = Member("woo@gs.com", "Woo", "secret")
        member.activate()

        assertThatThrownBy {
            member.activate()
        }.isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun deactivate() {
        val member = Member("woo@gs.com", "Woo", "secret")

        member.activate()
        member.deactivate()

        Assertions.assertThat(member.status).isEqualTo(MemberStatus.DEACTIVATED)
    }

    @Test
    fun deactivateFail() {
        val member = Member("woo@gs.com", "Woo", "secret")

        member.activate()
        member.deactivate()

        assertThatThrownBy {
            member.deactivate()
        }.isInstanceOf(IllegalStateException::class.java)
    }
}
