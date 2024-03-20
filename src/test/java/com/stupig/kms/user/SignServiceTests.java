package com.stupig.kms.user;

import com.stupig.kms.common.constants.ResponseCode;
import com.stupig.kms.common.vo.ResponseVO;
import com.stupig.kms.user.service.SignService;
import com.stupig.kms.user.vo.sign.SignPVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SignServiceTests {

    private final SignService signService;

    @Autowired
    SignServiceTests(SignService signService) {
        this.signService = signService;
    }

    /**
     * signIn 검증
     */
    @Test
    void signInTest() {
        // ID 검증
        {
            SignPVO signPVO = new SignPVO();
            signPVO.setId("wrong");
            signPVO.setPassword("wrong");
            Assertions.assertEquals(
                    ResponseCode.NON_EXIST_ID, signService.signIn(signPVO, new ResponseVO<>()).getResCode()
            );
        }

        // PW 검증
        {
            SignPVO signPVO = new SignPVO();
            signPVO.setId("root");
            signPVO.setPassword("wrong");
            Assertions.assertEquals(
                    ResponseCode.NON_EXIST_ID, signService.signIn(signPVO, new ResponseVO<>()).getResCode()
            );
        }

        // 정상
        {
            SignPVO signPVO = new SignPVO();
            signPVO.setId("root");
            signPVO.setPassword("root");
            Assertions.assertEquals(
                    ResponseCode.SUCCESS, signService.signIn(signPVO, new ResponseVO<>()).getResCode()
            );
        }

        // PW 횟수 오류 검증
        {
            SignPVO signPVO = new SignPVO();
            signPVO.setId("root");
            signPVO.setPassword("wrong");

            for (int i = 0; i < 5; i++) {
                signService.signIn(signPVO, new ResponseVO<>());
            }
            Assertions.assertEquals(
                    ResponseCode.PASSWORD_INPUT_COUNT_OVER, signService.signIn(signPVO, new ResponseVO<>()).getResCode()
            );
        }
    }

    /**
     * signOut 검증
     */
    @Test
    void signOutTest() {
        Assertions.assertEquals(
                ResponseCode.SUCCESS, signService.signOut(new ResponseVO<>()).getResCode()
        );
    }

    /**
     * signUp 검증
     */
    @Test
    void signUpTest() {
        SignPVO signPVO = new SignPVO();
        signPVO.setId("test");
        signPVO.setPassword("test");
        signPVO.setName("test");
        Assertions.assertEquals(
                ResponseCode.DUPLICATE_ID, signService.signUp(signPVO, new ResponseVO<>()).getResCode()
        );

        signPVO.setId("test0");
        Assertions.assertEquals(
                ResponseCode.SUCCESS, signService.signUp(signPVO, new ResponseVO<>()).getResCode()
        );
    }

    /**
     * idCheck 검증
     */
    @Test
    void idCheckTest() {
        SignPVO signPVO = new SignPVO();
        signPVO.setId("test");
        Assertions.assertEquals(
                ResponseCode.DUPLICATE_ID, signService.idCheck(signPVO, new ResponseVO<>()).getResCode()
        );

        signPVO.setId("test99");
        Assertions.assertEquals(
                ResponseCode.SUCCESS, signService.idCheck(signPVO, new ResponseVO<>()).getResCode()
        );
    }

    /**
     * signConfirm 검증
     */
    @Test
    void signConfirmTest() {
        SignPVO signPVO = new SignPVO();
        signPVO.setUserSeq(100L);
        signPVO.setAuth("01");
        Assertions.assertEquals(
                ResponseCode.NON_EXIST_ID, signService.signConfirm(signPVO, new ResponseVO<>()).getResCode()
        );

        signPVO.setUserSeq(2L);
        Assertions.assertEquals(
                ResponseCode.APPROVED_USER, signService.signConfirm(signPVO, new ResponseVO<>()).getResCode()
        );

        signPVO.setUserSeq(3L);
        signPVO.setGroupSeq(1L);
        Assertions.assertEquals(
                ResponseCode.EXCEPTION, signService.signConfirm(signPVO, new ResponseVO<>()).getResCode()
        );

        signPVO.setGroupSeq(null);
        Assertions.assertEquals(
                ResponseCode.SUCCESS, signService.signConfirm(signPVO, new ResponseVO<>()).getResCode()
        );
    }

}
