package me.geso.iarea;

import java.util.List;
import java.util.Optional;

/**
 * IArea data repository.
 */
public interface IAreaRepository {
	/**
	 * Get list of regions
	 */
	List<String> getRegions();

	/**
	 * Get list of prefectures
	 */
	List<String> getPrefectures();

	/**
	 * Get area object by lat/lng.
	 *
	 * @param latWGS84Degree latitude(緯度) in WGS84, Degree.
	 * @param lngWGS84Degree longitude(経度) in WGS84, Degree.
	 * @return iArea object for specified lat/lng.
	 */
	Optional<IArea> getAreaByWgs84LatLng(double latWGS84Degree, double lngWGS84Degree);
}
