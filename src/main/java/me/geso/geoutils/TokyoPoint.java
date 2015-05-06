package me.geso.geoutils;

/**
 * Tokyo Datum
 */
public class TokyoPoint {
	private final double lat;
	private final double lng;

	private TokyoPoint(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public static TokyoPoint fromDegree(double lat, double lng) {
		return new TokyoPoint(lat, lng);
	}

	public double getLat() {
		return this.lat;
	}

	public double getLng() {
		return this.lng;
	}

	public WGS84Point toWGS84() {
		double lat_w = lat -lat*0.00010695 +lng*0.000017464 +0.0046017;
		double lng_w = lng -lat*0.000046038 -lng*0.000083043+0.010040;
		return WGS84Point.fromDegree(lat_w, lng_w);
	}
}

