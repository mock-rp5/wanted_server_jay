package com.example.demo.src.posting;

import com.example.demo.config.BaseException;
import com.example.demo.src.posting.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class PostingService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostingDao postingDao;

    @Autowired
    public PostingService(PostingDao postingDao) {
        this.postingDao = postingDao;
    }

    //이력서 지원
    public PostApplyRes applyPosting(PostApplyReq postApplyReq) throws BaseException {
        try {
            long applyId = postingDao.applyPosting(postApplyReq);
            return new PostApplyRes(applyId);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //채용 공고 상세 조회
    public GetPostingRes getPostingDetail(long postingId, long userId) throws BaseException {
        try {
            GetPosting getPosting = postingDao.getPosting(postingId);
            List<String> pictures = postingDao.getPictures(postingId);
            List<String> tags = postingDao.getTags(postingId);
            int like;
            int bookMark;

            if (userId == 0){
                like = 0;
                bookMark = 0;
            } else {
                like = postingDao.checkLike(postingId, userId);
                bookMark = postingDao.checkBookMark(postingId, userId);
            }

            return new GetPostingRes(
                    getPosting.getPostingId(),
                    getPosting.getCompanyId(),
                    getPosting.getCompanyName(),
                    getPosting.getCompanyPlace(),
                    pictures,
                    tags,
                    getPosting.getTitle(),
                    getPosting.getDetail(),
                    getPosting.getRecommendMoney(),
                    getPosting.getApplyMoney(),
                    getPosting.getPlaceFull(),
                    like, bookMark);
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //채용 공고 북마크
    public void bookMarkPosting(long postingId, long userId) throws BaseException {
        try {
            int result;
            int r = postingDao.checkBookMarkHistory(postingId, userId);
            if (r == 0)
                result = postingDao.bookMarkPosting(postingId, userId);
            else
                result = postingDao.reBookMarkPosting(postingId, userId);
            if (result == 0){
                throw new BaseException(CREATE_FAIL_LIKE);
            }
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //북마크 해제
    public void cancelBookMark(long postingId, long userId) throws BaseException {
        try {
            int result = postingDao.cancelBookMark(postingId, userId);
            if (result == 0){
                throw new BaseException(DELETE_FAIL_BOOKMARK);
            }
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //북마크한 채용 공고 조회
    public List<GetBookMarkPostingRes> getBookMarkPostings(int userId) throws BaseException{
        try {
            List<GetBookMarkPostingRes> getBookMarkPostingRes = postingDao.getBookMarkPostings(userId);
            return getBookMarkPostingRes;
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //좋아요

    //채용 공고 좋아요
    public void likePosting(long postingId, long userId) throws BaseException {
        try {
            int result;
            int r = postingDao.checkLikeHistory(postingId, userId);
            if (r == 0)
                result = postingDao.likePosting(postingId, userId);
            else
                result = postingDao.reLikePosting(postingId, userId);
            if (result == 0){
                throw new BaseException(CREATE_FAIL_LIKE);
            }
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //좋아요 해제
    public void cancelLike(long postingId, long userId) throws BaseException {
        try {
            int result = postingDao.cancelLike(postingId, userId);
            if (result == 0){
                throw new BaseException(DELETE_FAIL_LIKE);
            }
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //좋아요한 채용 공고 조회
    public List<GetlikePostingRes> getLikePostings(int userId) throws BaseException{
        try {
            List<GetlikePostingRes> getlikePostingRes = postingDao.getLikePostings(userId);
            return getlikePostingRes;
        } catch (Exception e){
            System.out.println(e.getCause());
            throw new BaseException(DATABASE_ERROR);
        }
    }



//    // 채용 공고 리스트 조회
//    public List<GetPostingListRes> getPostingList(GetPostingListReq getPostingListReq) throws BaseException {
//        try {
//            List<GetPostingListRes> getPostingListRes = postingDao.getPostingList(getPostingListReq);
//            return getPostingListRes;
//        } catch (Exception e) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }


}
