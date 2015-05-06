package me.geso.iarea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class IAreaRepositoryImpl implements IAreaRepository {
	private final List<IArea> iAreas;

	public IAreaRepositoryImpl() {
		this.iAreas = loadIAreas();
	}

	private List<IArea> loadIAreas() {
		try (final InputStream inputStream = IAreaFinderImpl.class.getResourceAsStream("/me/geso/iarea/IAreaFinder/area.tsv")) {
			if (inputStream == null) {
				throw new NullPointerException("No area.tsv in resources");
			}

			try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
				// 北海道	北海道	002	23	江別/石狩
				return bufferedReader.lines().map(String::trim)
						.map(it -> it.split("\\t"))
						.filter(it -> it.length == 5)
						.map(it -> new IArea(it[0], it[1], it[4], it[2] + it[3]))
						.collect(Collectors.toList());
			}
		} catch (IOException e) {
			throw new RuntimeException("Cannot load area database for getting iArea");
		}
	}

	@Override
	public List<String> getRegions() {
		return iAreas
				.stream()
				.map(IArea::getRegion)
				.distinct()
				.collect(Collectors.toList());
	}

	@Override
	public List<String> getPrefectures() {
		return iAreas
				.stream()
				.map(IArea::getPrefecture)
				.distinct()
				.collect(Collectors.toList());
	}

	@Override
	public Optional<IArea> findByAreaCode(final String areaCode) {
		Objects.requireNonNull(areaCode);
		return iAreas.stream().filter(it -> it.getAreaCode().equals(areaCode)).findFirst();
	}

	@Override
	public List<IArea> getAll() {
		return iAreas;
	}

}
