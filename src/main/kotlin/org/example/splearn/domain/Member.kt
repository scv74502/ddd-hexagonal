package org.example.splearn.domain

import org.springframework.util.Assert
import org.springframework.util.Assert.state

class Member(
    val email: String,
    val nickname: String,
    val passwordHash: String,
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

    constructor(email: String, nickname: String, passwordHash: String) : this(email, nickname, passwordHash, MemberStatus.PENDING)
}
