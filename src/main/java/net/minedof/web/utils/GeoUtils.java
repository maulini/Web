package net.minedof.web.utils;

public final class GeoUtils {

    private GeoUtils() {}

    /**
     * Calcule la différence entre deux points.
     * @param lat1
     *          Latitude du point 1.
     * @param lat2
     *          Latitude du point 2.
     * @param lon1
     *          Longitude du point 1.
     * @param lon2
     *          Longitude du point 2.
     * @return La distance entre les deux points.
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2) {

        final double R = 6371; // km
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double lat1B = Math.toRadians(lat1);
        double lat2B = Math.toRadians(lat2);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1B) * Math.cos(lat2B);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;

        return d;
    }

}
