/*
 * Created by lzy on 2020/4/27 10:57 PM.
 */
package com.lzy.demo.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 本地ip工具类
 *
 * @author lzy
 * @version v1.0
 */
public class LocalAddressUtils {
    private static Logger logger = LoggerFactory.getLogger(LocalAddressUtils.class);


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
            logger.error("getLocalAddress error", e);
            return null;
        }
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface ni = networkInterfaces.nextElement();
            try {
                if (!ni.isUp() || ni.isVirtual() || ni.isLoopback()) {
                    continue;
                }
            } catch (SocketException e) {
                logger.error("getLocalAddress error", e);
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
