package me.geso.geoutils.iarea;

import java.util.Optional;
import me.geso.geoutils.iarea.data.MeshCode2AreaCode;
import me.geso.geoutils.TokyoPoint;

public class IAreaDetector {
	public Optional<IArea> getArea(TokyoPoint tokyoPoint) {
		String[] meshs = calculateIAreaMesh(tokyoPoint);
		for (String mesh: meshs) {
			String areaCode = MeshCode2AreaCode.getAreaCode(mesh);
			if (areaCode != null) {
				return IArea.findByAreaCode(areaCode);
			}
		}
		return Optional.empty();
	}

	/**
	 * @args tokyoPoint Tokyo Datum point
	 */
	public String[] calculateIAreaMesh(TokyoPoint tokyoPoint) {
		// ref. https://github.com/tokuhirom/p5-geo-coordinates-converter-iarea/blob/master/lib/Geo/Coordinates/Converter/Format/IArea.pm#L51
		double lat = tokyoPoint.getLat() * 60 * 60 * 1000;
		double lng = tokyoPoint.getLng() * 60 * 60 * 1000;

		String []meshs = new String[7];

		int ab = (int)(lat / 2400000);
		int cd = ((int)(lng / 3600000)) - 100;
		int x1 = (cd +100) * 3600000;
		int y1 = ab * 2400000;
		int e = (int)((lat - y1) / 300000);
		int f = (int)((lng - x1) / 450000);
		String mesh1 = ""+ab+cd+e+f;
		meshs[0] = mesh1;

		int x2 = x1 + f * 450000;
		int y2 = y1 + e * 300000;
		int l3 = (int)((lng - x2) / 225000);
		int m3 = (int)((lat - y2) / 150000);
		int g = l3 + m3 * 2;
		String mesh2 = mesh1 + g;
		meshs[1] = mesh2;

		int x3 = x2 + l3 * 225000;
		int y3 = y2 + m3 * 150000;
		int l4 = (int)((lng - x3) / 112500);
		int m4 = (int)((lat - y3) / 75000);
		int h = l4 + m4 * 2;
		String mesh3 = mesh2 + h;
		meshs[2] = mesh3;

		int x4 = x3 + l4 * 112500;
		int y4 = y3 + m4 * 75000;
		int l5 = (int)((lng - x4) / 56250);
		int m5 = (int)((lat - y4) / 37500);
		int i = l5 + m5 * 2;
		String mesh4 = mesh3 + i;
		meshs[3] = mesh4;

		int x5 = x4 + l5 * 56250;
		int y5 = y4 + m5 * 37500;
		int l6 = (int)((lng - x5) / 28125);
		int m6 = (int)((lat - y5) / 18750);
		int j = l6 + m6 * 2;
		String mesh5 = mesh4 + j;
		meshs[4] = mesh5;

		int x6 = x5 + l6 * 28125;
		int y6 = y5 + m6 * 18750;
		int l7 = (int)((lng - x6) / 14062.5);
		int m7 = (int)((lat - y6) / 9375);
		int k = l7 + m7 * 2;
		String mesh6 = mesh5 + k;
		meshs[5] = mesh6;

		return meshs;
	}
}

