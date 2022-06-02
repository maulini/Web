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

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters


        return Math.sqrt(distance);
    }

}
