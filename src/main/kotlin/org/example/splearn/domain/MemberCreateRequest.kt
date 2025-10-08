package org.example.splearn.domain

data class MemberCreateRequest(
    val email: String,
    val nickname: String,
    val password: String,
) {
    companion object {
        fun of(
            email: String,
            nickname: String,
            password: String,
        ): MemberCreateRequest =
            MemberCreateRequest(
                email,
                nickname,
                password,
            )
    }
}