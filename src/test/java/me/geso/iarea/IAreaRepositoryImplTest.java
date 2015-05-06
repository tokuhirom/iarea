package me.geso.iarea;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;

import junit.framework.TestCase;

public class IAreaRepositoryImplTest extends TestCase {
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
	public void testFindByAreaCode() {
		final IAreaRepository iAreaRepository = new IAreaRepositoryImpl();
		final Optional<IArea> byAreaCode = iAreaRepository.findByAreaCode("00100");
		assertThat(byAreaCode.isPresent(), is(true));
		assertThat(byAreaCode.get().getName(), is("函館/渡島"));
	}

	@Test
	public void testGetAll() {
		final IAreaRepository iAreaRepository = new IAreaRepositoryImpl();
		final List<IArea> areas = iAreaRepository.getAll();
		assertThat(areas.size(), is(505));
		assertThat(areas.get(0).getName(), is("函館/渡島"));
		assertThat(areas.get(504).getName(), is("宮古/石垣"));
	}

}
