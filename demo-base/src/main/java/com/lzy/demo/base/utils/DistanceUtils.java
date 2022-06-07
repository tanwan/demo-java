package com.lzy.demo.base.utils;

public class DistanceUtils {

    /**
     * 地球半径
     */
    private static final double EARTH_RADIUS = 6378137.0;

    /**
     * PI为180度
     */
    private static final double RAD_PI = 180.0;


    /**
     * 计算两点之间的距离
     *
     * @param firstX  the first x
     * @param firstY  the first y
     * @param secondX the second x
     * @param secondY the second y
     * @return the double
     */
    public static double pointDistance(final double firstX, final double firstY,
                                       final double secondX, final double secondY) {
        double dX = firstX - secondX;
        double dY = firstY - secondY;
        return Math.sqrt(dX * dX + dY * dY);
    }

    /**
     * 计算两个经纬度之间的距离
     *
     * @param lng1 第一个点经度
     * @param lat1 第一个点纬度
     * @param lng2 第两个点经度
     * @param lat2 第两个点纬度
     * @return the double
     */
    public static double earthDistance(final double lng1, final double lat1,  final double lng2, final double lat2) {
        double radLat1 = lat1 * Math.PI / RAD_PI;
        double radLat2 = lat2 * Math.PI / RAD_PI;
        double radLng1 = lng1 * Math.PI / RAD_PI;
        double radLng2 = lng2 * Math.PI / RAD_PI;
        double a = radLat1 - radLat2;
        double b = radLng1 - radLng2;
        double s = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return s;
    }
}
