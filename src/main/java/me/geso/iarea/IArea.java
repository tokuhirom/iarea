package me.geso.iarea;

public class IArea {
	private String region;
	private String prefecture;
	private String name;
	private String code;

	public IArea(String region, String prefecture, String name, String code) {
		this.region = region;
		this.prefecture = prefecture;
		this.name = name;
		this.code = code;
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
