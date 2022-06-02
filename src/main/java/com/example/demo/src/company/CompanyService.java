package com.example.demo.src.company;

import com.example.demo.config.BaseException;
import com.example.demo.src.company.model.*;
import com.example.demo.src.posting.PostingDao;
import com.example.demo.src.posting.model.GetPostingListRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional
public class CompanyService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CompanyDao companyDao;
    private final PostingDao postingDao;

    @Autowired
    public CompanyService(CompanyDao companyDao, PostingDao postingDao) {
        this.companyDao = companyDao;
        this.postingDao = postingDao;
    }

    //상세 조회
    public GetCompanyDetailRes getCompanyDetail(long companyId) throws BaseException {
        try {
            GetCompany getCompany =companyDao.getCompany(companyId);
            List<GetCompanyPicture> getCompanyPicture = companyDao.getCompanyPictures(companyId);
            List<GetCompanyPostings> getCompanyPostings = companyDao.getCompanyPostings(companyId);
            List<GetCompanyTags> getCompanyTags = companyDao.getCompanyTags(companyId);

            return new GetCompanyDetailRes(
                    companyId,
                    getCompany.getCompanyName(), getCompany.getCompanyLogo(),
                    getCompanyPicture,
                    getCompany.getIntroduce(),
                    getCompany.getSalary(),
                    getCompany.getPlace(),
                    getCompanyPostings,
                    getCompanyTags
            );
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
