package com.example.demo.src.user;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.config.BaseResponseStatus.USERS_STATUS_ERROR;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/app/users")
/**
 * Controller란?
 * 사용자의 Request를 전달받아 요청의 처리를 담당하는 Service, Prodiver 를 호출
 */
public class UserController {
    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************

    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!
    }

    // ******************************************************************************

    /**
     * 회원가입 API
     * [POST] /users
     */
    @ResponseBody
    @PostMapping("")    // POST 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        //  @RequestBody란, 클라이언트가 전송하는 HTTP Request Body(우리는 JSON으로 통신하니, 이 경우 body는 JSON)를 자바 객체로 매핑시켜주는 어노테이션
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        // TODO : 전화번호, 이메일 중복에대한 validation도 해야한다.
        // email에 값이 존재하는지, 빈 값으로 요청하지는 않았는지 검사합니다. 빈값으로 요청했다면 에러 메시지를 보냅니다.
        if (postUserReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현: 입력받은 이메일이 email@domain.xxx와 같은 형식인지 검사합니다. 형식이 올바르지 않다면 에러 메시지를 보냅니다.
        if (!isRegexEmail(postUserReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        // email 중복되었을떄? => 의미적 validation으로 service에서 해주어야한다.
        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            System.out.println(exception.getCause());
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 가입 유무 이메일로 확인
     * [Post] /users/email
     */
    @ResponseBody
    @PostMapping("/email")
    public BaseResponse<Integer> checkEmail(@RequestBody PostCheckEmailReq postCheckEmailReq){
        try {
            int i = userProvider.checkEmail(postCheckEmailReq.getEmail());
            return new BaseResponse<>(i);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    /**
     * 로그인 API
     * [POST] /users/log-in
     */
    @ResponseBody
    @PostMapping("/log-in")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) {
        try {
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            // status validation
            if (postLoginReq.getEmail() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            if (!isRegexEmail(postLoginReq.getEmail())) {
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
            }
            if (userProvider.checkStatus(postLoginRes.getUserId()).equals("ACTIVE")){
                return new BaseResponse<>(postLoginRes);
            } else if (userProvider.checkStatus(postLoginRes.getUserId()).equals("DELETE")) {
                return new BaseResponse<>(USERS_STATUS_DELETE);
            } else if (userProvider.checkStatus(postLoginRes.getUserId()).equals("INACTIVE")) {
                return new BaseResponse<>(USERS_STATUS_INACTIVE);
            } else {
                return new BaseResponse<>(USERS_STATUS_ERROR);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 기본 정보 조회 API
     * [GET] /users/:userIdx
     */
    // Path-variable
    @ResponseBody
    @GetMapping("{userId}") // (GET) 127.0.0.1:9000/app/users/:userId
    public BaseResponse<GetUserRes> getUser(@PathVariable("userId") long userId) {
        try {
            //jwt에서 id 추출.
            long userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 같은지 확인
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetUserRes getUserRes = userProvider.getUser(userId);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 유저 탈퇴 API
     * [PATCH] /users/:userId/status
     */
    @ResponseBody
    @PatchMapping("{userId}/status")
    public BaseResponse<String> deleteUser(@PathVariable("userId") long userId){
        try{
            //jwt에서 idx 추출.
            long userIdByJwt = jwtService.getUserId();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            userService.deleteUser(userId);
            String result = "유저 탈퇴 성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }



    /**
     * 유저정보변경 API
     * [PATCH] /users/:userId
     */
    @ResponseBody
    @PatchMapping("{userId}")
    public BaseResponse<String> modifyUser(@PathVariable("userId") int userId, @RequestBody PatchUserReq user) {
        try {

            //*********** 해당 부분은 7주차 - JWT 수업 후 주석해체 해주세요!  ****************
            //jwt에서 idx 추출.
            long userIdByJwt = jwtService.getUserId();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //**************************************************************************

            PatchUserReq patchUserReq = new PatchUserReq(userId, user.getName(), user.getEmail(), user.getPhone());
            userService.modifyUser(patchUserReq);

            String result = "회원정보가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            System.out.println(exception.getStatus());
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     *  [추가 6]
     *  유저 이미지 변경 API
     *  [PATCH] /users/:userId/image
     */
    @ResponseBody
    @PatchMapping("/{userId}/image")
    public BaseResponse<String> modifyUserImage(@PathVariable("userId") int userId, @RequestBody PatchUserImgReq user){
        try {
            //*********** 해당 부분은 7주차 - JWT 수업 후 주석해체 해주세요!  ****************
            //jwt에서 idx 추출.
            long userIdByJwt = jwtService.getUserId();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            //**************************************************************************
            PatchUserImgReq patchUserImgReq = new PatchUserImgReq(userId, user.getImageUrl());
            userService.modifyUserImg(patchUserImgReq);

            String result = "회원 이미지가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     *  [추가 7]
     *  유저 이미지 삭제 API
     *  [PATCH] /users/:userId/image
     */
    @ResponseBody
    @PatchMapping("/{userId}/image/status")
    public BaseResponse<String> deleteUserImage(@PathVariable("userId") int userId){
        try {
            //*********** 해당 부분은 7주차 - JWT 수업 후 주석해체 해주세요!  ****************
            //jwt에서 idx 추출.
            long userIdByJwt = jwtService.getUserId();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            //**************************************************************************
            PatchUserImgReq patchUserImgReq = new PatchUserImgReq(userId, null);
            userService.modifyUserImg(patchUserImgReq);

            String result = "회원 이미지가 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 카카오
     */
    @ResponseBody
    @GetMapping("/kakao")
    public BaseResponse<PostUserRes>  kakaoCallback(@RequestParam String code) throws BaseException {
        System.out.println(code);
        String access_Token = userService.getKaKaoAccessToken(code);
        ArrayList<String> res = userService.getKakaoUser(access_Token);
        String email = res.get(0);
        String name = res.get(1);

        if (email == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        try {
            PostUserRes postUserRes = userService.createKakaoUser(email, name);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
