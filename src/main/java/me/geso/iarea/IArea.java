package me.geso.iarea;

/**
 * iArea class
 * <a href="https://www.nttdocomo.co.jp/service/developer/make/content/iarea/domestic/index.html">オープンiエリア</a>
 */
public class IArea {
	private String region;
	private String prefecture;
	private String name;
	private String areaCode;

	public IArea(String region, String prefecture, String name, String areaCode) {
		this.region = region;
		this.prefecture = prefecture;
		this.name = name;
		this.areaCode = areaCode;
	}

	/**
	 * Get region name
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Get prefecture name
	 */
	public String getPrefecture() {
		return prefecture;
	}

	/**
	 * Get area name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get area code
	 */
	public String getAreaCode() {
		return areaCode;
	}
}
