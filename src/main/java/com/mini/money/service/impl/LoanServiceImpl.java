package com.mini.money.service.impl;

import com.mini.money.dto.loan.*;
import com.mini.money.dto.member.LoginRequest;
import com.mini.money.entity.Customer;
import com.mini.money.entity.Loan;
import com.mini.money.exceptrion.loan.NoSuchLoanException;
import com.mini.money.exceptrion.member.NoSuchMemberException;
import com.mini.money.parameter.*;
import com.mini.money.repository.CustomerRepository;
import com.mini.money.repository.LoanRepository;
import com.mini.money.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository repository;
    private final CustomerRepository customerRepository;

    /**
     * 메인페이지 내의 대출 상품 조회
     * @return 임의의 대출 상품 3개의 List를 LoanResponse로 변환 후 반환
     */
    @Override
    @Transactional(readOnly = true)
    public List<LoanResponse> selectLoanList() {
        int count = 3;
        List<Loan> loanList = repository.findAll();
        List<LoanResponse> loanResponses = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int num = (int) (Math.random() * loanList.size()) + 1;
            loanResponses.add(LoanResponse.from(loanList.get(num)));
        }
        return loanResponses;
    }

    /**
     * 전체 대출 상품 조회
     * @param pageable 페이징 처리(10개)
     * @return 전체 대출 상품(Loan) 리스트를 LoanResponse의 형태로 반환
     * 성능 개선을 위해 @Transactional(readOnly), @Cacheable을 활용
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "allItems", key = "#pageable")
    public Page<LoanResponse> selectAll(Pageable pageable) {
        return repository.findAll(pageable).map(loan -> new LoanResponse(loan));
    }

    /**
     * 대출 상품 조건 조회 - 기관
     * @param office 공공기관, 여신금융회사, 은행, 일반기관, 기타
     * @param pageable 페이징 처리(10개)
     * @return 대출 상품(Loan) 리스트를 LoanResponse의 형태로 반환
     * 성능 개선을 위해 @Transactional(readOnly), @Cacheable을 활용
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "itemsByOffice", key = "#office.name()+#pageable")
    public Page<LoanResponse> selectByOffice(Office office, Pageable pageable) {
        Office[] os = Office.values();
        List<String> officeList = Arrays.stream(os).map(Objects::toString).collect(Collectors.toList());
        officeList.remove("기타");

        Page<Loan> selectAllByOffice;

        if (office.name().equals("기타")) {
            selectAllByOffice = repository.findAllByDivisionOfficeNotIn(officeList, pageable);
        } else {
            selectAllByOffice = repository.findAllByDivisionOfficeContaining(office.name(), pageable);
        }

        return getLoanResponse(selectAllByOffice);
    }

    /**
     * 대출 상품 조건 조회 - 지역
     * @param area 강원, 경기, 경남, 경북, 광주, 대구, 대전, 부산, 서울, 세종, 인천, 전국, 전남, 전북, 제주, 충남, 충북, 기타
     * @param pageable 페이징 처리(10개)
     * @return 대출 상품(Loan) 리스트를 LoanResponse의 형태로 반환
     * 성능 개선을 위해 @Transactional(readOnly), @Cacheable을 활용
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "itemsByArea", key = "#area.name()+#pageable")
    public Page<LoanResponse> selectByArea(Area area, Pageable pageable) {
        Area[] ar = Area.values();
        List<String> areaList = Arrays.stream(ar).map(Objects::toString).collect(Collectors.toList());
        areaList.remove("기타");

        Page<Loan> selectAllByArea;

        if (area.name().equals("기타")) {
            selectAllByArea = repository.findAllByAreaNotIn(areaList, pageable);
        } else {
            selectAllByArea = repository.findAllByAreaContaining(area.name(), pageable);
        }

        return getLoanResponse(selectAllByArea);
    }

    /**
     * 대출 상품 조건 조회 - 상환 방법
     * @param repayment 균등분할상환, 만기일시상환, 원금균등분할상환, 원리금균등분할상환, 기타
     * @param pageable 페이징 처리(10개)
     * @return 대출 상품(Loan) 리스트를 LoanResponse의 형태로 반환
     * 성능 개선을 위해 @Transactional(readOnly), @Cacheable을 활용
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "itemsByRepayment", key = "#repayment.name()+#pageable")
    public Page<LoanResponse> selectByRepayment(Repayment repayment, Pageable pageable) {
        Repayment[] rm = Repayment.values();
        List<String> repayList = Arrays.stream(rm).map(Objects::toString).collect(Collectors.toList());
        repayList.remove("기타");

        Page<Loan> selectAllByRepayment;

        if (repayment.name().equals("기타")) {
            selectAllByRepayment = repository.findAllByRepayMethodNotIn(repayList, pageable);
        } else {
            selectAllByRepayment = repository.findAllByRepayMethodContaining(repayment.name(), pageable);
        }

        return getLoanResponse(selectAllByRepayment);
    }

    /**
     * 대출 상품 조건 조회 - 용도
     * @param usge 생계, 운영, 주거, 창업, 학자금, 기타
     * @param pageable 페이징 처리 (10개)
     * @return 대출 상품(Loan) 리스트를 LoanResponse의 형태로 반환
     * 성능 개선을 위해 @Transactional(readOnly), @Cacheable을 활용
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "itemsByUsge", key = "#usge.name()+#pageable")
    public Page<LoanResponse> selectByUsge(Usge usge, Pageable pageable) {
        Usge[] us = Usge.values();
        List<String> usgeList = Arrays.stream(us).map(Objects::toString).collect(Collectors.toList());
        usgeList.remove("기타");

        Page<Loan> selectAllByUsge;

        if (usge.name().equals("기타")) {
            selectAllByUsge = repository.findAllByUsgeNotIn(usgeList, pageable);
        } else {
            selectAllByUsge = repository.findAllByUsgeContaining(usge.name(), pageable);
        }

        return getLoanResponse(selectAllByUsge);
    }

    /**
     * 대출 상품 조건 조회 - 타겟층
     * @param target 근로자, 사업자, 소상공인, 사회적경제기업, 기타
     * @param pageable 페이징 처리 (10개)
     * @return 대출 상품(Loan) 리스트를 LoanResponse의 형태로 반환
     * 성능 개선을 위해 @Transactional(readOnly), @Cacheable을 활용
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "itemsByTarget", key = "#target.name()+#pageable")
    public Page<LoanResponse> selectByTarget(Target target, Pageable pageable) {
        Target[] tr = Target.values();
        List<String> targetList = Arrays.stream(tr).map(Objects::toString).collect(Collectors.toList());
        targetList.remove("기타");

        Page<Loan> selectAllByTarget;

        if (target.name().equals("기타")) {
            selectAllByTarget = repository.findAllByLoanTargetNotIn(targetList, pageable);
        } else {
            selectAllByTarget = repository.findAllByLoanTargetContaining(target.name(), pageable);
        }

        return getLoanResponse(selectAllByTarget);
    }

    /**
     * 대출 상품 조건 조회 - 금리 분류
     * @param baseRate 고정금리, 변동금리, 기타
     * @param pageable 페이징 처리 (10개)
     * @return 대출 상품(Loan) 리스트를 LoanResponse의 형태로 반환
     * 성능 개선을 위해 @Transactional(readOnly), @Cacheable을 활용
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "itemsByBaseRate", key = "#baseRate.name()+#pageable")
    public Page<LoanResponse> selectByBaseRate(BaseRate baseRate, Pageable pageable) {
        BaseRate[] br = BaseRate.values();
        List<String> baseRateList = Arrays.stream(br).map(Objects::toString).collect(Collectors.toList());
        baseRateList.remove("기타");

        Page<Loan> selectAllByBaseRate;

        if (baseRate.name().equals("기타")) {
            selectAllByBaseRate = repository.findAllByBaseRateNotIn(baseRateList, pageable);
        } else {
            selectAllByBaseRate = repository.findAllByBaseRateContaining(baseRate.name(), pageable);
        }

        return getLoanResponse(selectAllByBaseRate);
    }

    /**
     * 대출 상품 조건 조회 - 운영 기한
     * @param maturity 상시, 제한
     * @param pageable 페이징 처리 (10개)
     * @return 대출 상품(Loan) 리스트를 LoanResponse의 형태로 반환
     * 성능 개선을 위해 @Transactional(readOnly), @Cacheable을 활용
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "itemsByMaturity", key = "#maturity+#pageable")
    public Page<LoanResponse> selectByMaturity(String maturity, Pageable pageable) {
        Page<Loan> selectAllByMaturity;
        String filter = "상시";

        if (maturity.equals("상시")) {
            selectAllByMaturity = repository.findAllByMaturityContaining(filter, pageable);
        } else {
            selectAllByMaturity = repository.findAllByMaturityNotContaining(filter, pageable);
        }

        return getLoanResponse(selectAllByMaturity);
    }

    /**
     * 대출 상품 조건 조회 - 신용 제한
     * @param credit 유(Y), 무(N)
     * @param pageable 페이징 처리 (10개)
     * @return 대출 상품(Loan) 리스트를 LoanResponse의 형태로 반환
     * 성능 개선을 위해 @Transactional(readOnly), @Cacheable을 활용
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "itemsByCredit", key = "#credit+#pageable")
    public Page<LoanResponse> selectByCredit(String credit, Pageable pageable) {
        Page<Loan> selectAllByCredit;
        String filter = "-";

        if (credit.equals("N")) {
            selectAllByCredit = repository.findAllByCreditScoreContaining(filter, pageable);
        } else {
            selectAllByCredit = repository.findAllByCreditScoreNotContaining(filter, pageable);
        }

        return getLoanResponse(selectAllByCredit);
    }

    /**
     * 대출 상품 조건 조회 - 키워드
     * @param keyword 대출 상품명을 조건으로 조회
     * @param pageable 페이징 처리 (10개)
     * @return 대출 상품(Loan) 리스트를 LoanResponse의 형태로 반환
     * 성능 개선을 위해 @Transactional(readOnly), @Cacheable을 활용
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "itemsByKeyword", key = "#keyword+#pageable")
    public Page<LoanResponse> selectByKeyword(String keyword, Pageable pageable) {
        Page<Loan> selectAllByKeyword = repository.findAllByLoanNameContaining(keyword, pageable);

        return getLoanResponse(selectAllByKeyword);
    }

    /**
     * 대출 상품 상세 조회
     * @param snq 해당 대출 상품의 상품 번호
     * @return 대출 상품(Loan)의 상세 정보를 3개의 DTO(LoanProdResponse, LoanTargetResponse, LoanEtcResponse)로 변환 후 이를 포함한 LoanDetailResponse의 형태로 반환
     * 성능 개선을 위해 @Transactional(readOnly), @Cacheable을 활용
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "itemDetail", key = "#snq")
    public LoanDetailResponse selectLoanDetail(Long snq) {
        Loan loan = repository.findBySnq(snq)
                .orElseThrow(NoSuchLoanException::new);

        return LoanDetailResponse.from(LoanProdResponse.from(loan), LoanTargetResponse.from(loan), LoanEtcResponse.from(loan));
    }

    /**
     * 메인페이지 내 회원 추천 대출 상품 조회
     * @param loginRequest 로그인 한 회원 정보 조회
     * @return 로그인 한 회원 정보를 바탕으로 회원 상세정보에 접근하여 지역 기준으로 대출 상품 추천 (10개)
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LoanResponse> memberCommendLoanList(LoginRequest loginRequest) {
        Pageable page = PageRequest.of(0, 10);

        Customer customer = customerRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(NoSuchMemberException::new);
        Page<Loan> recommendByArea = repository.findAllByAreaContaining(areaCheck(customer),page);

        return getLoanResponse(recommendByArea);
    }

    public String areaCheck(final Customer customer) {
        String area = Area.전국.name();
        if (customer.getAddress() != null) {
            area = customer.getAddress();
        }
        return area;
    }

    private Page<LoanResponse> getLoanResponse(Page<Loan> selectData) {
        return selectData.map(loan -> new LoanResponse(loan));
    }
}
