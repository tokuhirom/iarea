package me.geso.geoutils.iarea;

import java.util.Optional;

public enum IArea {
	TOKYO("関東", "東京", "恵比寿", "0001")
	;

	public IArea(String region, String prefecture, String name, String code) {
		this.region = region;
		this.prefecture = prefecture;
		this.name = name;
		this.code = code;
	}

	private String region;
	private String prefecture;
	private String name;
	private String code;

	public Optional<IArea> findByAreaCode(String areaCode) {
		if (areaCode == null) {
			throw new NullPointerException("areaCode must not be null");
		}

		for (IArea iarea : IArea.values()) {
			if (areaCode.equals(iarea.getCode())) {
				return Optional.of(iarea);
			}
		}
		return Optional.empty();
	}

	public String getRegion() {
		return region;
	}

	public String getPrefecture() {
		return prefecture;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
}
