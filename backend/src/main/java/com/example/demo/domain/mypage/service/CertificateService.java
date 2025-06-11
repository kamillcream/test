package com.example.demo.domain.mypage.service;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import com.example.demo.domain.mypage.dto.request.ResumeCertificateRequest;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CertificateService {

	// 자격증 목록을 외부 API에서 조회해서 CertificateDto 리스트로 반환
	public List<ResumeCertificateRequest> getCertificates(String keyword, int pageNo, int numOfRows) {
		String serviceKey = URLEncoder.encode("hRsP0IUH8bq2xvGwRO4GLNgdBeQpm1dOycol0n3/bUrz4vGBquK43tVCzjAC3OZ+GnJSV5dYkAEna9IbK2Y9kw==", StandardCharsets.UTF_8);
		String url = "http://openapi.q-net.or.kr/api/service/rest/InquiryInformationTradeNTQSVC/getList";
		
		UriComponents uri = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("serviceKey", serviceKey)
				.queryParam("jmNm", keyword)
				.queryParam("pageNo", pageNo)
				.queryParam("numOfRows", numOfRows)
				.build(true);

		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.getForEntity(uri.toUri(), String.class);

			System.out.println("자격증 API 응답: " + response.getBody());
			
			// XML 문자열 파싱
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			Document doc = factory.newDocumentBuilder()
	        .parse(new ByteArrayInputStream(response.getBody().getBytes(StandardCharsets.UTF_8)));

	     // item 태그로 자격증 목록 추출
	     NodeList itemList = doc.getElementsByTagName("item");
	     List<ResumeCertificateRequest> certificates = new ArrayList<>();

	     for (int i = 0; i < itemList.getLength(); i++) {
	         Element item = (Element) itemList.item(i);
	         ResumeCertificateRequest dto = new ResumeCertificateRequest();
	         dto.setCertificationNm(getTagValue("jmNm", item));         // 자격증 이름
	         dto.setCertificationIssuerNm(getTagValue("minClassNm", item)); // 발행 기관
	         certificates.add(dto);
	     }

			return certificates;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("자격증 API 파싱 중 오류 발생");
		}
	}

	// XML 태그 값 추출 유틸
	private String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag);
		if (nodeList.getLength() > 0) {
			return nodeList.item(0).getTextContent();
		}
		return "";
	}
}
