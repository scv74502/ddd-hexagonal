package org.example.splearn.domain

@JvmInline
value class Email(
    val value: String,
)

@JvmInline
value class Nickname(
    val value: String,
)

@JvmInline
value class PasswordHash(
    val value: String,
)

class Member private constructor(
    val email: Email,
    var nickname: Nickname,
    var passwordHash: PasswordHash,
    var status: MemberStatus,
) {
    fun activate() {
        check(status == MemberStatus.PENDING) {
            "회원이 PENDING 상태가 아닙니다"
        }
        this.status = MemberStatus.ACTIVATE
    }

    fun deactivate() {
        check(status == MemberStatus.ACTIVATE) {
            "회원이 ACTIVE 상태가 아닙니다"
        }
        this.status = MemberStatus.DEACTIVATED
    }

    fun verifyPassword(
        password: String,
        passwordEncoder: PasswordEncoder,
    ): Boolean = passwordEncoder.matches(password, this.passwordHash.value)

    fun changeNickname(nickname: String) {
        this.nickname = Nickname(nickname)
    }

    fun changePassword(
        password: String,
        passwordEncoder: PasswordEncoder,
    ) {
        this.passwordHash = PasswordHash(passwordEncoder.encode(password))
    }

    fun isActive(): Boolean = this.status == MemberStatus.ACTIVATE

    companion object {
        fun create(
            email: String,
            nickname: String,
            password: String,
            passwordEncoder: PasswordEncoder,
        ): Member =
            Member(
                email = memberCreateRequest.email,
                nickname = memberCreateRequest.nickname,
                passwordHash =
                    PasswordHash(
                        passwordEncoder.encode(memberCreateRequest.password.value),
                    ),
                status = MemberStatus.PENDING,
            )
    }
}
