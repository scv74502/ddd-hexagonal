package org.example.splearn.domain

class Member private constructor(
    val email: String,
    var nickname: String,
    var passwordHash: String,
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
    ): Boolean = passwordEncoder.matches(password, this.passwordHash)

    fun changeNickname(nickname: String) {
        this.nickname = nickname
    }

    fun changePassword(
        password: String,
        passwordEncoder: PasswordEncoder,
    ) {
        this.passwordHash = passwordEncoder.encode(password)
    }

    companion object {
        fun create(
            email: String,
            nickname: String,
            password: String,
            passwordEncoder: PasswordEncoder,
        ): Member =
            Member(
                email,
                nickname,
                passwordEncoder.encode(password),
                MemberStatus.PENDING,
            )
    }
}
