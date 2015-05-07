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

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final IArea iArea = (IArea)o;

		if (region != null ? !region.equals(iArea.region) : iArea.region != null)
			return false;
		if (prefecture != null ? !prefecture.equals(iArea.prefecture) : iArea.prefecture != null)
			return false;
		if (name != null ? !name.equals(iArea.name) : iArea.name != null)
			return false;
		return !(areaCode != null ? !areaCode.equals(iArea.areaCode) : iArea.areaCode != null);

	}

	@Override
	public int hashCode() {
		int result = region != null ? region.hashCode() : 0;
		result = 31 * result + (prefecture != null ? prefecture.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (areaCode != null ? areaCode.hashCode() : 0);
		return result;
	}

}
