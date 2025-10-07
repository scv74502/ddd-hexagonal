package org.example.splearn.domain

data class MemberCreateRequest(
    val email: Email,
    val nickname: Nickname,
    val password: PasswordHash,
) {
    companion object {
        fun of(
            email: String,
            nickname: String,
            password: String,
        ): MemberCreateRequest =
            MemberCreateRequest(
                Email(email),
                Nickname(nickname),
                PasswordHash(password),
            )
    }
}
