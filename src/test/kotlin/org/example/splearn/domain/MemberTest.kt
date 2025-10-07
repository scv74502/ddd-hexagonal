package org.example.splearn.domain

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Locale.getDefault

class MemberTest {
    private lateinit var member: Member
    private lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun setUp() {
        passwordEncoder =
            object : PasswordEncoder {
                override fun encode(password: String): String = password.uppercase(getDefault())

                override fun matches(
                    password: String,
                    passwordHash: String,
                ): Boolean = encode(password) == passwordHash
            }

        member =
            Member.create(
                "woo@gs.com",
                "woo",
                "secret",
                passwordEncoder,
            )
    }

    @Test
    fun testMemberStatus() {
        Assertions.assertThat(member.status).isEqualTo(MemberStatus.PENDING)
    }

    @Test
    fun activate() {
        member.activate()
        Assertions.assertThat(member.status).isEqualTo(MemberStatus.ACTIVATE)
    }

    @Test
    fun activateFail() {
        member.activate()

        assertThatThrownBy {
            member.activate()
        }.isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun deactivate() {
        member.activate()
        member.deactivate()

        Assertions.assertThat(member.status).isEqualTo(MemberStatus.DEACTIVATED)
    }

    @Test
    fun deactivateFail() {
        member.activate()
        member.deactivate()

        assertThatThrownBy {
            member.deactivate()
        }.isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun verifyPassword() {
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue()
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse()
    }

    @Test
    fun changeNickname() {
        assertThat(member.nickname.value == "woo").isTrue
        member.changeNickname("Charlie")
        assertThat(member.nickname.value == "Charlie").isTrue
    }

    @Test
    fun changePassword() {
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue()
        member.changePassword("verySecret", passwordEncoder)
        assertThat(member.verifyPassword("verySecret", passwordEncoder)).isTrue()
    }

    @Test
    fun isActive() {
        assertThat(member.isActive()).isFalse()
        member.activate()
        assertThat(member.isActive()).isTrue()
    }
}
