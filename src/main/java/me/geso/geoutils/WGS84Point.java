package me.geso.geoutils;

/**
 * WGS84 Datum Point
 */
public class WGS84Point {
	private final double lat;
	private final double lng;

	private WGS84Point(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public static WGS84Point fromDegree(double lat, double lng) {
		return new WGS84Point(lat, lng);
	}

	public double getLat() {
		return this.lat;
	}

	public double getLng() {
		return this.lng;
	}

	public TokyoPoint toTokyo() {
		// http://d.hatena.ne.jp/end0tknr/20081026/1225012257
		double lat_t = lat +lat*0.00010696 -lng*0.000017467 -0.0046020;
		double lng_t = lng +lat*0.000046047 +lng*0.000083049 -0.010041;
		return TokyoPoint.fromDegree(lat_t, lng_t);
	}
}
