package com.example.demo.src.company;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.company.model.GetCompanyDetailRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/app/companies")
public class CompanyController {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final CompanyService companyService;
    @Autowired
    private final CompanyDao companyDao;

    public CompanyController(CompanyService companyService, CompanyDao companyDao) {
        this.companyService = companyService;
        this.companyDao = companyDao;
    }

    /**
     * 회사 상세 조회
     * [Get] {companyId}
     */
    @ResponseBody
    @GetMapping("{companyId}")
    public BaseResponse<GetCompanyDetailRes> getCompanyDetail(@PathVariable long companyId){
        try {
            GetCompanyDetailRes getCompanyDetailRes = companyService.getCompanyDetail(companyId);
            return new BaseResponse<>(getCompanyDetailRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
