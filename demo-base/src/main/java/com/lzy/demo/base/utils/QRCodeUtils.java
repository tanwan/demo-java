package com.lzy.demo.base.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class QRCodeUtils {
    /**
     * logo所占的比例
     */
    private static final float DEFAULT_LOGO_SCALE = 0.2F;
    private static final int DEFAULT_LOGO_BORDER = 1;
    private static final int DEFAULT_LOGO_PADDING = 5;
    private static final int DEFAULT_QR_WIDTH = 300;
    private static final int DEFAULT_QR_HEIGHT = 300;


    /**
     * 生成二维码
     *
     * @param qrParams the qr params
     * @return the buffered image
     * @throws IOException     the io exception
     * @throws WriterException the writer exception
     */
    public static BufferedImage createQRCode(QRParams qrParams) throws IOException, WriterException {
        //生成无logo的二维码
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrParams.getData(),
                BarcodeFormat.QR_CODE, qrParams.getWidth(), qrParams.getHeight(), qrParams.getHints());
        BufferedImage qrCodeWithoutLogo = MatrixToImageWriter.toBufferedImage(bitMatrix);

        if (qrParams.hasLogo()) {
            int qrCodeWidth = qrCodeWithoutLogo.getWidth();
            int qrCodeHeight = qrCodeWithoutLogo.getHeight();

            BufferedImage logo = qrParams.getLogoImage();
            //logo需要进行缩放
            if (logo.getWidth() > qrParams.getLogoWidth() || logo.getHeight() > qrParams.getLogoHeight()) {
                logo = new BufferedImage(qrParams.getLogoWidth(), qrParams.getLogoHeight(),
                        BufferedImage.TYPE_INT_RGB);

                logo.getGraphics().drawImage(
                        qrParams.getLogoImage().getScaledInstance(qrParams.getLogoWidth(), qrParams.getLogoHeight(),
                                Image.SCALE_SMOOTH), 0, 0, null);
            }

            BufferedImage qrCode = new BufferedImage(qrCodeWidth, qrCodeHeight, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g2 = qrCode.createGraphics();
            //绘制二维码
            g2.drawImage(qrCodeWithoutLogo, 0, 0, null);
            // 开始绘制logo
            g2.drawImage(roundRect(logo, qrParams.getLogoRadius(), qrParams.getLogoBorder(), qrParams.getLogoPadding()),
                    qrParams.getLogoWidth() * 2, qrParams.getLogoHeight() * 2, qrParams.getLogoWidth(), qrParams.getLogoHeight(), null);
            return qrCode;
        } else {
            return qrCodeWithoutLogo;
        }
    }


    /**
     * 图片切圆角,带边框和旁白
     *
     * @param srcImage the src image
     * @param radius   圆角矩形半径
     * @param border   图像边框
     * @param padding  边框往外的一层
     * @return the radius
     */
    private static BufferedImage roundRect(BufferedImage srcImage, int radius, int border, int padding) {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int canvasWidth = width + padding * 2;
        int canvasHeight = height + padding * 2;
        int diameter = radius * 2;

        //绘制画布
        BufferedImage image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gs = image.createGraphics();
        gs.setComposite(AlphaComposite.Src);
        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gs.setColor(Color.WHITE);
        gs.fill(new RoundRectangle2D.Float(0, 0, canvasWidth, canvasHeight, diameter, diameter));
        gs.setComposite(AlphaComposite.SrcAtop);

        //绘制图像
        gs.drawImage(roundRect(srcImage, radius), padding, padding, null);
        //绘制边框
        if (border != 0) {
            gs.setColor(Color.GRAY);
            gs.setStroke(new BasicStroke(border));
            gs.drawRoundRect(padding, padding, canvasWidth - 2 * padding, canvasHeight - 2 * padding, diameter, diameter);
        }
        gs.dispose();
        return image;
    }

    /**
     * 图片切圆角
     *
     * @param srcImage the src image
     * @param radius   圆角半径
     * @return the clip
     */
    private static BufferedImage roundRect(BufferedImage srcImage, int radius) {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gs = image.createGraphics();
        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gs.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius * 2, radius * 2));
        gs.drawImage(srcImage, 0, 0, null);
        gs.dispose();
        return image;
    }

    /**
     * 二维码logo的参数
     */
    public static class QRParams {
        //二维码数据
        private String data;
        //二维码宽度
        private Integer width;
        //二维码高度
        private Integer height;
        //二维码编码参数
        private Map<EncodeHintType, Object> hints;
        //二维码logo
        private BufferedImage logoImage;
        //二维码logo宽度
        private Integer logoWidth;
        //二维码logo高度
        private Integer logoHeight;
        //二维码logo圆角半径
        private Integer logoRadius;
        //二维码logo图像边框
        private Integer logoBorder;
        //二维码logo旁白
        private Integer logoPadding;

        /**
         * 构造函数
         *
         * @param data 数据
         */
        public QRParams(String data) {
            setData(data);
            this.hints = new HashMap<>();
            this.hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.toString());
            this.hints.put(EncodeHintType.MARGIN, 1);
        }

        public String getData() {
            return data;
        }

        /**
         * 设置数据
         *
         * @param data the data
         * @return the data
         */
        public QRParams setData(String data) {
            Assert.hasLength(data, "data can't be empty");
            this.data = data;
            return this;
        }

        /**
         * 静态函数设置数据
         *
         * @param data the data
         * @return the qr params
         */
        public static QRParams data(String data) {
            return new QRParams(data);
        }


        public Integer getWidth() {
            return Optional.ofNullable(this.width).orElse(DEFAULT_QR_WIDTH);
        }


        public Integer getHeight() {
            return Optional.ofNullable(this.height).orElse(DEFAULT_QR_HEIGHT);
        }

        /**
         * 设置二维码大小
         *
         * @param width  the width
         * @param height the height
         * @return the height
         */
        public QRParams size(Integer width, Integer height) {
            this.width = width;
            this.height = height;
            return this;
        }


        public Map<EncodeHintType, Object> getHints() {
            return this.hints;
        }

        /**
         * 设置编码参数
         *
         * @param hints the hints
         * @return the hint
         */
        public QRParams hints(Map<EncodeHintType, Object> hints) {
            this.hints.putAll(hints);
            return this;
        }

        /**
         * 添加编码参数
         *
         * @param encodeHintType the encode hint type
         * @param object         the object
         * @return the qr params
         */
        public QRParams addHint(EncodeHintType encodeHintType, Object object) {
            this.hints.put(encodeHintType, object);
            return this;
        }


        public BufferedImage getLogoImage() {
            return logoImage;
        }

        /**
         * 设置logo图像
         *
         * @param logoImage the logo image
         * @return the logo image
         */
        public QRParams logoImage(BufferedImage logoImage) {
            this.logoImage = logoImage;
            return this;
        }

        /**
         * 设置logo图像
         *
         * @param file the file
         * @return the logo image
         */
        public QRParams logoImage(File file) {
            try {
                this.logoImage = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        /**
         * 设置logo图像
         *
         * @param url the url
         * @return the logo image
         */
        public QRParams logoImage(String url) {
            try {
                this.logoImage = ImageIO.read(new URL(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }


        /**
         * logo宽度 默认为二维码宽度的五分之一
         *
         * @return the logo width
         */
        public Integer getLogoWidth() {
            return Optional.ofNullable(this.height).orElse(Math.round(this.getWidth() * DEFAULT_LOGO_SCALE));
        }


        /**
         * logo高度 默认为二维码高度的五分之一
         *
         * @return the logo height
         */
        public Integer getLogoHeight() {
            return Optional.ofNullable(this.height).orElse(Math.round(this.getHeight() * DEFAULT_LOGO_SCALE));
        }


        /**
         * 设置logo尺寸
         *
         * @param logoWidth  the logo width
         * @param logoHeight the logo height
         */
        public void logoSize(Integer logoWidth, Integer logoHeight) {
            this.logoWidth = logoWidth;
            this.logoHeight = logoHeight;
        }


        /**
         * logo圆角半径默认为图像的六分之一
         *
         * @return integer logo radius
         */
        public Integer getLogoRadius() {
            return Optional.ofNullable(this.logoRadius).orElse(this.logoImage.getWidth() / 6);
        }

        /**
         * 设置logo圆角半径
         *
         * @param logoRadius the logo radius
         * @return the logo radius
         */
        public QRParams logoRadius(Integer logoRadius) {
            this.logoRadius = logoRadius;
            return this;
        }

        /**
         * logo边框默认值为1
         *
         * @return the logo border
         */
        public Integer getLogoBorder() {
            return Optional.ofNullable(this.logoBorder).orElse(DEFAULT_LOGO_BORDER);
        }

        /**
         * 设置logo边框
         *
         * @param logoBorder the logo border
         * @return the logo border
         */
        public QRParams logoBorder(Integer logoBorder) {
            this.logoBorder = logoBorder;
            return this;
        }

        /**
         * logo旁白默认值为5
         *
         * @return the logo padding
         */
        public Integer getLogoPadding() {
            return Optional.ofNullable(this.logoPadding).orElse(DEFAULT_LOGO_PADDING);
        }

        /**
         * 设置logo旁白
         *
         * @param logoPadding the logo padding
         * @return the logo padding
         */
        public QRParams logoPadding(Integer logoPadding) {
            this.logoPadding = logoPadding;
            return this;
        }

        /**
         * 是否有logo
         *
         * @return boolean
         */
        public Boolean hasLogo() {
            return this.logoImage != null;
        }
    }
}
