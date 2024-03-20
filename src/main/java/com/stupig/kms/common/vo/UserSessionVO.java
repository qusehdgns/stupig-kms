package com.stupig.kms.common.vo;

import com.stupig.kms.user.vo.UserMgmtRVO;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class UserSessionVO implements Serializable {
    private static final long serialVersionUID = 317069934698722309L;

    /* 사용자일련번호 */
    private Long userSeq;
    /* 유저이름 */
    private String id;
    /* 이름 */
    private String name;
    /* 회사명 */
    private String compName;
    /* 사번 */
    private String compId;
    /* 전화번호 */
    private String phone;
    /* 이메일 */
    private String email;
    /* 부서일련번호 */
    private Long groupSeq;
    /* 권한코드(00001) */
    private String auth;

    public UserSessionVO(UserMgmtRVO userMgmtRVO) {
        this.userSeq = userMgmtRVO.getUserSeq();
        this.id = userMgmtRVO.getId();
        this.name = userMgmtRVO.getName();
        this.compName = userMgmtRVO.getCompName();
        this.compId = userMgmtRVO.getCompId();
        this.phone = userMgmtRVO.getPhone();
        this.email = userMgmtRVO.getEmail();
        this.groupSeq = userMgmtRVO.getGroupSeq();
        this.auth = userMgmtRVO.getAuth();
    }
}
