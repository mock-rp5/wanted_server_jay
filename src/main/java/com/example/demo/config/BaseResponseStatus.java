package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),
    USERS_STATUS_DELETE(false, 2011, "탈퇴한 유저입니다."),
    USERS_STATUS_INACTIVE(false, 2012, "휴면 유저입니다."),
    USERS_STATUS_ERROR(false, 2013, "유저의 상태가 올바르지 않습니다."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),



    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USER(false,4014,"유저 수정 실패"),
    DELETE_FAIL_USER(false, 4015, "유저 탈퇴 실패"),
    MODIFY_FAIL_USERIMG(false, 4016, "유저이미지 수정 실패"),
    MODIFY_FAIL_RESUME(false, 4017, "이력서 임시저장 실패"),
    COMPLETE_FAIL_RESUME(false, 4018, "이력서 작성 완료 실패"),
    DELETE_FAIL_RESUME(false, 4019, "이력서 삭제 실패"),
    MODIFY_FAIL_CAREER(false, 4020, "이력서 경력 수정 실패"),
    DELETE_FAIL_CAREER(false, 4021, "이력서 경력 삭제 실패"),
    MODIFY_FAIL_RESULT(false, 4022, "주요 성과 수정 실패"),
    DELETE_FAIL_RESULT(false, 4023, "주요 성과 삭제 실패"),
    MODIFY_FAIL_EDUCATION(false, 4024, "학력 수정 실패"),
    DELETE_FAIL_EDUCATION(false, 4025, "학력 삭제 실패"),
    MODIFY_FAIL_ETC(false, 4026, "수상 및 기타 수정 실패"),
    DELETE_FAIL_ETC(false, 4027, "수상 및 기타 삭제 실패"),
    MODIFY_FAIL_LANGUAGE(false, 4028, "외국어 수정 실패"),
    DELETE_FAIL_LANGUAGE(false, 4029, "외국어 삭제 실패"),
    MODIFY_FAIL_LINK(false, 4030, "링크 수정 실패"),
    DELETE_FAIL_LINK(false, 4031, "링크 삭제 실패"),
    DELETE_FAIL_RESUME_SKILL(false, 4032, "이력서 스킬 삭제 실패"),
    CREATE_FAIL_BOOKMARK(false, 4033, "북마크 실패"),
    DELETE_FAIL_BOOKMARK(false, 4034, "북마크 취소 실패"),
    CREATE_FAIL_LIKE(false, 4035, "좋아요 실패"),
    DELETE_FAIL_LIKE(false, 4036, "좋아요 취소 실패"),
    SAVE_FAIL_AUTHNUM(false, 4037, "인증번호 저장 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
