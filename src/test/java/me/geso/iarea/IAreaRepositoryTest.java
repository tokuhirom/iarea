package me.geso.iarea;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class IAreaRepositoryTest {
	@Test
	public void testGetRegions() {
		final IAreaRepository iAreaRepository = new IAreaRepositoryImpl();
		final List<String> regions = iAreaRepository.getRegions();
		assertEquals(9, regions.size());
		assertEquals("北海道,東北,関東甲信越,東海,北陸,関西,中国,四国,九州",
				regions.stream().collect(Collectors.joining(",")));
	}

	@Test
	public void testGetPrefectures() {
		final IAreaRepository iAreaRepository = new IAreaRepositoryImpl();
		final List<String> prefectures = iAreaRepository.getPrefectures();
		assertEquals("北海道,岩手,青森,秋田,福島,宮城,山形,栃木,群馬,新潟,"
				+ "千葉,茨城,東京,埼玉,神奈川,静岡,山梨,長野,愛知,"
				+ "三重,岐阜,福井,石川,富山,滋賀,京都,兵庫,大阪,奈良,"
				+ "和歌山,鳥取,島根,岡山,広島,山口,香川,徳島,愛媛,"
				+ "高知,福岡,佐賀,長崎,熊本,大分,宮崎,鹿児島,沖縄", prefectures.stream().collect(Collectors.joining(",")));
		assertEquals(47, prefectures.size());
	}

	@Test
	public void testGetAreaByWgs84LatLng() {
		final IAreaRepository iAreaRepository = new IAreaRepositoryImpl();
		{
			// 作ろうiモードコンテンツのPDFに載っている座標
			final IArea area = iAreaRepository.getAreaByWgs84LatLng(
					35.0 + 40.0 / 60.0 + 42.0 / 3600,
					139.0 + 46.0 / 60 + 09.527 / 3600
			).get();
			assertEquals("関東甲信越", area.getRegion());
			assertEquals("東京", area.getPrefecture());
			assertEquals("東京駅周辺", area.getName());
			assertEquals("05700", area.getCode());
		}
		{
			// ヒカリエ
			final IArea area = iAreaRepository.getAreaByWgs84LatLng(35.659935, 139.703332).get();
			assertEquals("関東甲信越", area.getRegion());
			assertEquals("東京", area.getPrefecture());
			assertEquals("渋谷明治通り/宮益坂", area.getName());
			assertEquals("06201", area.getCode());
		}
		{
			// 新宿末広亭
			final IArea area = iAreaRepository.getAreaByWgs84LatLng(35.691190, 139.705836).get();
			assertEquals("関東甲信越", area.getRegion());
			assertEquals("東京", area.getPrefecture());
			assertEquals("新宿東口", area.getName());
			assertEquals("06300", area.getCode());
		}
		{
			// 上野鈴本
			final IArea area = iAreaRepository.getAreaByWgs84LatLng(35.709201, 139.772915).get();
			assertEquals("関東甲信越", area.getRegion());
			assertEquals("東京", area.getPrefecture());
			assertEquals("本郷", area.getName());
			assertEquals("06400", area.getCode());
		}
		{
			// 池袋演芸場
			final IArea area = iAreaRepository.getAreaByWgs84LatLng(35.731847, 139.710059).get();
			assertEquals("関東甲信越", area.getRegion());
			assertEquals("東京", area.getPrefecture());
			assertEquals("池袋", area.getName());
			assertEquals("06500", area.getCode());
		}
		{
			// （株）よしもとクリエイティブ・エージェンシー福岡支社
			final IArea area = iAreaRepository.getAreaByWgs84LatLng(33.58867, 130.3976405).get();
			assertEquals("九州", area.getRegion());
			assertEquals("福岡", area.getPrefecture());
			assertEquals("中央区", area.getName());
			assertEquals("22400", area.getCode());
		}
		{
			// なんばグランド花月
			final IArea area = iAreaRepository.getAreaByWgs84LatLng(34.664966, 135.50368).get();
			assertEquals("関西", area.getRegion());
			assertEquals("大阪", area.getPrefecture());
			assertEquals("難波/日本橋", area.getName());
			assertEquals("17218", area.getCode());
		}
		{
			// 沖縄美ら海水族館
			final IArea area = iAreaRepository.getAreaByWgs84LatLng(26.694158, 127.877932).get();
			assertEquals("九州", area.getRegion());
			assertEquals("沖縄", area.getPrefecture());
			assertEquals("沖縄/名護", area.getName());
			assertEquals("25000", area.getCode());
		}
		{
			// 旭山動物園
			final IArea area = iAreaRepository.getAreaByWgs84LatLng(43.767233,142.482228).get();
			assertEquals("北海道", area.getRegion());
			assertEquals("北海道", area.getPrefecture());
			assertEquals("旭川/上川", area.getName());
			assertEquals("00300", area.getCode());
		}
	}
}
