package com.bili.sdk.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
//import org.apache.commons.codec.binary.Base64;

public class QRCodeUtil {
    public static String generateQRCodeBase64(String url, boolean needHead) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int size = 200;
        String imageFormat = "png";

        // Create QR Code from URL
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        java.util.Map<EncodeHintType, Object> hintMap = new java.util.HashMap<EncodeHintType, Object>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hintMap.put(EncodeHintType.MARGIN, 1); // default 4
        com.google.zxing.common.BitMatrix byteMatrix;

        try {
            byteMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, size, size, hintMap);
        } catch (com.google.zxing.WriterException e) {
            throw new RuntimeException("Error generating QR Code: " + e.getMessage());
        }

        // Create image from QR Code
        BufferedImage qrCodeImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        qrCodeImage.createGraphics();

        Graphics2D graphics = (Graphics2D) qrCodeImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, qrCodeImage.getWidth(), qrCodeImage.getHeight());
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        // Convert image to bytearray stream
        ImageIO.write(qrCodeImage, imageFormat, baos);
        byte[] byteData = baos.toByteArray();

        // Encode bytearray to Base64 String

        String base64QRCode =   Base64.getEncoder().encodeToString(byteData);

        return needHead?"data:image/png;base64,"+base64QRCode:base64QRCode;
    }
}
