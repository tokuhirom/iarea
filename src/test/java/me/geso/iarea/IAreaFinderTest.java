package me.geso.iarea;

import static org.junit.Assert.*;

import org.junit.Test;

public class IAreaFinderTest {
	@Test
	public void testGetAreaByWgs84LatLng() {
		final IAreaFinder iAreaFinder = new IAreaFinderImpl();
		{
			// 作ろうiモードコンテンツのPDFに載っている座標
			final IArea area = iAreaFinder.getAreaByWgs84LatLng(
					35.0 + 40.0 / 60.0 + 42.0 / 3600,
					139.0 + 46.0 / 60 + 09.527 / 3600
			).get();
			assertEquals("関東甲信越", area.getRegion());
			assertEquals("東京", area.getPrefecture());
			assertEquals("東京駅周辺", area.getName());
			assertEquals("05700", area.getAreaCode());
		}
		{
			// ヒカリエ
			final IArea area = iAreaFinder.getAreaByWgs84LatLng(35.659935, 139.703332).get();
			assertEquals("関東甲信越", area.getRegion());
			assertEquals("東京", area.getPrefecture());
			assertEquals("渋谷明治通り/宮益坂", area.getName());
			assertEquals("06201", area.getAreaCode());
		}
		{
			// 新宿末広亭
			final IArea area = iAreaFinder.getAreaByWgs84LatLng(35.691190, 139.705836).get();
			assertEquals("関東甲信越", area.getRegion());
			assertEquals("東京", area.getPrefecture());
			assertEquals("新宿東口", area.getName());
			assertEquals("06300", area.getAreaCode());
		}
		{
			// 上野鈴本
			final IArea area = iAreaFinder.getAreaByWgs84LatLng(35.709201, 139.772915).get();
			assertEquals("関東甲信越", area.getRegion());
			assertEquals("東京", area.getPrefecture());
			assertEquals("本郷", area.getName());
			assertEquals("06400", area.getAreaCode());
		}
		{
			// 池袋演芸場
			final IArea area = iAreaFinder.getAreaByWgs84LatLng(35.731847, 139.710059).get();
			assertEquals("関東甲信越", area.getRegion());
			assertEquals("東京", area.getPrefecture());
			assertEquals("池袋", area.getName());
			assertEquals("06500", area.getAreaCode());
		}
		{
			// （株）よしもとクリエイティブ・エージェンシー福岡支社
			final IArea area = iAreaFinder.getAreaByWgs84LatLng(33.58867, 130.3976405).get();
			assertEquals("九州", area.getRegion());
			assertEquals("福岡", area.getPrefecture());
			assertEquals("中央区", area.getName());
			assertEquals("22400", area.getAreaCode());
		}
		{
			// なんばグランド花月
			final IArea area = iAreaFinder.getAreaByWgs84LatLng(34.664966, 135.50368).get();
			assertEquals("関西", area.getRegion());
			assertEquals("大阪", area.getPrefecture());
			assertEquals("難波/日本橋", area.getName());
			assertEquals("17218", area.getAreaCode());
		}
		{
			// 沖縄美ら海水族館
			final IArea area = iAreaFinder.getAreaByWgs84LatLng(26.694158, 127.877932).get();
			assertEquals("九州", area.getRegion());
			assertEquals("沖縄", area.getPrefecture());
			assertEquals("沖縄/名護", area.getName());
			assertEquals("25000", area.getAreaCode());
		}
		{
			// 旭山動物園
			final IArea area = iAreaFinder.getAreaByWgs84LatLng(43.767233,142.482228).get();
			assertEquals("北海道", area.getRegion());
			assertEquals("北海道", area.getPrefecture());
			assertEquals("旭川/上川", area.getName());
			assertEquals("00300", area.getAreaCode());
		}
	}
}
