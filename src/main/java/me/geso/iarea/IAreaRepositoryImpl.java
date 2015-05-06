package me.geso.iarea;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

public class IAreaRepositoryImpl implements IAreaRepository {
	private final Map<String, String> meshCode2AreaCode;
	private final Map<String, IArea> areaCode2IArea;

	public IAreaRepositoryImpl() {
		meshCode2AreaCode = loadMeshCode2AreaCode();
		areaCode2IArea = loadAreaCode2IArea();
	}

	private Map<String, String> loadMeshCode2AreaCode() {
		try (final InputStream inputStream = IAreaRepositoryImpl.class.getResourceAsStream("/me/geso/iarea/IAreaFinder/meshcode.properties")) {
			Map<String, String> result = new HashMap<>();
			final List<String> lines = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
			for (final String line : lines) {
				final String[] split = line.trim().split("=");
				if (split.length == 2) {
					final String areaCode = split[0];
					final String[] meshCodes = split[1].split(",");
					for (final String meshcode : meshCodes) {
						result.put(meshcode, areaCode);
					}
				}
			}
			return result;
		} catch (IOException e) {
			throw new RuntimeException("Cannot load mesh code database for getting iArea");
		}
	}

	private Map<String, me.geso.iarea.IArea> loadAreaCode2IArea() {
		try (final InputStream inputStream = IAreaRepositoryImpl.class.getResourceAsStream("/me/geso/iarea/IAreaFinder/area.tsv")) {
			if (inputStream == null) {
				throw new NullPointerException("No area.tsv in resources");
			}

			Map<String, me.geso.iarea.IArea> result = new LinkedHashMap<>();
			final List<String> lines = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
			for (final String line : lines) {
				final String[] split = line.trim().split("\\t");
				// 北海道	北海道	002	23	江別/石狩
				if (split.length == 5) {
					final String region = split[0];
					final String prefecture = split[1];
					final String areaCode = split[2] + split[3];
					final String name = split[4];
					result.put(areaCode, new IArea(region, prefecture, name, areaCode));
				}
			}
			return result;
		} catch (IOException e) {
			throw new RuntimeException("Cannot load area database for getting iArea");
		}
	}

	@Override
	public List<String> getRegions() {
		return areaCode2IArea
				.values()
				.stream()
				.map(IArea::getRegion)
				.distinct()
				.collect(Collectors.toList());
	}

	@Override
	public List<String> getPrefectures() {
		return areaCode2IArea
				.values()
				.stream()
				.map(IArea::getPrefecture)
				.distinct()
				.collect(Collectors.toList());
	}

	/**
	 * Get area object by lat/lng.
	 *
	 * @param latWGS84Degree latitude(緯度) in WGS84, Degree.
	 * @param lngWGS84Degree longitude(経度) in WGS84, Degree.
	 * @return iArea object for specified lat/lng.
	 */
	@Override
	public Optional<IArea> getAreaByWgs84LatLng(double latWGS84Degree, double lngWGS84Degree) {
		String[] meshCodes = calculateIAreaMesh(latWGS84Degree, lngWGS84Degree);
		for (String mesh : meshCodes) {
			String areaCode = meshCode2AreaCode.get(mesh);
			if (areaCode != null) {
				return Optional.of(areaCode2IArea.get(areaCode));
			}
		}
		return Optional.empty();
	}

	private String[] calculateIAreaMesh(double latWGS84Degree, double lngWGS84Degree) {
		// ref. https://github.com/tokuhirom/p5-geo-coordinates-converter-iarea/blob/master/lib/Geo/Coordinates/Converter/Format/IArea.pm#L51

		// convert to millisec
		int x = (int)(latWGS84Degree * 60 * 60 * 1000);
		int y = (int)(lngWGS84Degree * 60 * 60 * 1000);

		String[] meshCodes = new String[6];

		// 2次メッシュ
		// 小数点以下切り捨て
		int ab = Math.floorDiv(x, 2400000);
		int cd = Math.floorDiv(y, 3600000) - 100;
		int x1 = (cd + 100) * 3600000;
		int y1 = ab * 2400000;
		int e = Math.floorDiv(x - y1, 300000);
		int f = Math.floorDiv(y - x1, 450000);
		String mesh2 = "" + ab + cd + e + f;
		meshCodes[0] = mesh2;

		// 3次メッシュ
		int x2 = x1 + f * 450000;
		int y2 = y1 + e * 300000;
		int l3 = Math.floorDiv(y - x2, 225000);
		int m3 = Math.floorDiv(x - y2, 150000);
		int g = l3 + m3 * 2;
		String mesh3 = mesh2 + g;
		meshCodes[1] = mesh3;

		// 4次メッシュ
		int x3 = x2 + l3 * 225000;
		int y3 = y2 + m3 * 150000;
		int l4 = Math.floorDiv(y - x3, 112500);
		int m4 = Math.floorDiv(x - y3, 75000);
		int h = l4 + m4 * 2;
		String mesh4 = mesh3 + h;
		meshCodes[2] = mesh4;

		// 5次メッシュ
		int x4 = x3 + l4 * 112500;
		int y4 = y3 + m4 * 75000;
		int l5 = Math.floorDiv(y - x4, 56250);
		int m5 = Math.floorDiv(x - y4, 37500);
		int i = l5 + m5 * 2;
		String mesh5 = mesh4 + i;
		meshCodes[3] = mesh5;

		// 6次メッシュ
		int x5 = x4 + l5 * 56250;
		int y5 = y4 + m5 * 37500;
		int l6 = Math.floorDiv(y - x5, 28125);
		int m6 = Math.floorDiv(x - y5, 18750);
		int j = l6 + m6 * 2;
		String mesh6 = mesh5 + j;
		meshCodes[4] = mesh6;

		// 7次メッシュ
		int x6 = x5 + l6 * 28125;
		int y6 = y5 + m6 * 18750;
		int l7 = (int)Math.floor((y - x6) / 14062.5);
		int m7 = Math.floorDiv(x - y6, 9375);
		int k = l7 + m7 * 2;
		String mesh7 = mesh6 + k;
		meshCodes[5] = mesh7;

		return meshCodes;
	}
}

