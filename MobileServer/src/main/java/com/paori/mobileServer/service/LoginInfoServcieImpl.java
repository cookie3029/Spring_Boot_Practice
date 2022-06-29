package com.paori.mobileServer.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.paori.mobileServer.dto.LoginInfoDTO;
import com.paori.mobileServer.model.LoginInfo;
import com.paori.mobileServer.persistence.LoginInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginInfoServcieImpl implements LoginInfoService {
	private final LoginInfoRepository loginInfoRepository;

	@Override
	public Long registerLoginInfo(LoginInfoDTO dto) {
		System.out.println("controller : " + dto);

		// 현재 날짜 및 시간을 설정
		dto.setRegdate(LocalDateTime.now());

		// 위도와 경도 가져오기
		Double longitude = dto.getLongitude();
		Double latitude = dto.getLatitude();

		// 위도와 경도가 전송된 경우에만 작업을 수행
		if (longitude != null && latitude != null) {
			try {
				// 전송받을 URL 생성
				URL url = new URL("http://dapi.kakao.com/v2/local/geo/coord2address?" + "input_coord=WGS84&x="
						+ longitude + "&y=" + latitude);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();

				con.setConnectTimeout(30000);
				con.setUseCaches(false);

				// 헤더 설정 - 최근의 Open API는아이디와 비밀번호 대신에
				// API Key를 이용해서 인증을 받습니다.
				con.setRequestProperty("Authorization", "KakaoAK fed4cf7005daa6d12c41e4eb41414016");

				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

				StringBuilder sb = new StringBuilder();

				while (true) {
					String line = br.readLine();

					if (line == null) {
						break;
					}

					sb.append(line + "\n");
				}

				br.close();
				con.disconnect();

				System.out.println("카카오에서 받은 데이터 : " + sb.toString());

				if (sb.toString().length() > 0) {
					JSONObject object = new JSONObject(sb.toString());

					// 검색된 데이터 개수 가져오기
					JSONObject meta = object.getJSONObject("meta");
					Integer total_count = meta.getInt("total_count");

					if (total_count > 0) {
						JSONArray documents = object.getJSONArray("documents");

						// 첫번째 데이터 가져오기
						JSONObject first = documents.getJSONObject(0);
						
						JSONObject address = null;
						String address_name = null;

						try {
							address = first.getJSONObject("road_address");
						} catch (Exception e) {
							address = first.getJSONObject("address");
						} finally {
							address_name = address.getString("address_name");
						}

						System.out.println("주소 : " + address_name);

						// 주소 설정
						dto.setAddress(address_name);
					}
				}
			} catch (Exception e) {
				System.out.println("데이터 가져오기 에러 : " + e.getLocalizedMessage());
			}
		}

		// 데이터 변환 후 삽입
		LoginInfo loginInfo = dtoToEntity(dto);
		loginInfoRepository.save(loginInfo);

		return loginInfo.getLogininfoid();
	}
}
