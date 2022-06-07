package com.lzy.demo.io.utils;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class LocalAddressUtils {

    /**
     * 获取本机ip
     *
     * @param preferIPv6Addresses the prefer i pv 6 addresses
     * @return the local address
     */
    public static String getLocalAddress(boolean preferIPv6Addresses) {

        Enumeration<NetworkInterface> networkInterfaces;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface ni = networkInterfaces.nextElement();
            try {
                if (!ni.isUp() || ni.isVirtual() || ni.isLoopback()) {
                    continue;
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
            Enumeration<InetAddress> e = ni.getInetAddresses();
            while (e.hasMoreElements()) {
                InetAddress inetAddress = e.nextElement();
                if (preferIPv6Addresses) {
                    // ipv6
                    if (inetAddress instanceof Inet6Address) {
                        if (((Inet6Address) inetAddress).getScopedInterface().getName().contains("en")) {
                            return inetAddress.getHostAddress();
                        }
                    }
                } else if (inetAddress instanceof Inet4Address) {
                    // ipv4
                    return inetAddress.getHostAddress();
                }
            }
        }
        return null;
    }


}

