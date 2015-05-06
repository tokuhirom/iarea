package me.geso.iarea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IAreaFinderImpl implements IAreaFinder {
	private final Map<String, String> meshCode2AreaCode;
	private final IAreaRepository iAreaRepository;

	public IAreaFinderImpl(IAreaRepository iAreaRepository) {
		this.meshCode2AreaCode = loadMeshCode2AreaCode();
		this.iAreaRepository = iAreaRepository;
	}

	public IAreaFinderImpl() {
		this(new IAreaRepositoryImpl());
	}

	private Map<String, String> loadMeshCode2AreaCode() {
		try (final InputStream inputStream = IAreaFinderImpl.class.getResourceAsStream("/me/geso/iarea/IAreaFinder/meshcode.properties")) {
			return loadMeshCode2AreaCode(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("Cannot load mesh code database for getting iArea");
		}
	}

	private Map<String, String> loadMeshCode2AreaCode(InputStream inputStream) throws IOException {
		Map<String, String> result = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			reader.lines()
					.map(String::trim)
					.map(line -> line.split("="))
					.filter(row -> row.length == 2)
					.forEach(row -> {
						final String areaCode = row[0];
						final String[] meshCodes = row[1].split(",");
						for (final String meshcode : meshCodes) {
							result.put(meshcode, areaCode);
						}
					});
		}
		return result;
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
				return iAreaRepository.findByAreaCode(areaCode);
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

