package me.geso.iarea;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;

public class IAreaFinderImplTest {
	@Test
	public void testCalculateIAreaMesh() throws Exception {
		// 作ろうiモードコンテンツのPDFに載っている座標でちゃんと計算できてることを確認
		final IAreaFinder iAreaFinder = new IAreaFinderImpl();
		final Method calculateIAreaMesh = iAreaFinder.getClass().getDeclaredMethod("calculateIAreaMesh", double.class, double.class);
		calculateIAreaMesh.setAccessible(true);
		final String[] meshCodes = (String[])calculateIAreaMesh.invoke(iAreaFinder,
				35.0 + 40.0 / 60.0 + 42.0 / 3600,
				139.0 + 46.0 / 60 + 09.527 / 3600);
		assertThat(meshCodes[0], is("533946")); // 2次
		assertThat(meshCodes[1], is("5339460")); // 3次
		assertThat(meshCodes[2], is("53394600")); // 4次
		assertThat(meshCodes[3], is("533946003")); // 5次
		assertThat(meshCodes[4], is("5339460030")); // 6次
		assertThat(meshCodes[5], is("53394600300")); // 7次
	}
}
