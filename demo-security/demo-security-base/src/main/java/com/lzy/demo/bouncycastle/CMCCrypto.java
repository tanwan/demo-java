package com.lzy.demo.bouncycastle;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSAlgorithm;
import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.CMSEnvelopedDataGenerator;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.KeyTransRecipientInformation;
import org.bouncycastle.cms.RecipientInfoGenerator;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder;
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

import java.io.ByteArrayInputStream;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;

/**
 * CMS(Cryptographic Message Syntax),也就是PKSC#7
 *
 * @author LZY
 * @version v1.0
 */
public class CMCCrypto {

    public static byte[] encryptData(byte[] data, X509Certificate encryptionCertificate) throws Exception {
        // 用来指定加密密钥
        RecipientInfoGenerator jceKey = new JceKeyTransRecipientInfoGenerator(encryptionCertificate);

        // CMSEnvelopedDataGenerator用于生成加密的CMS数据
        CMSEnvelopedDataGenerator cmsEnvelopedDataGenerator = new CMSEnvelopedDataGenerator();
        cmsEnvelopedDataGenerator.addRecipientInfoGenerator(jceKey);

        // 待加密的数据
        CMSTypedData msg = new CMSProcessableByteArray(data);

        // 配置加密算法, 这里使用AES128_CBC,由BouncyCastle提供
        OutputEncryptor encryptor = new JceCMSContentEncryptorBuilder(CMSAlgorithm.AES128_CBC)
                .setProvider(BouncyCastleProvider.PROVIDER_NAME).build();

        // 生成加密的CMS数据
        CMSEnvelopedData cmsEnvelopedData = cmsEnvelopedDataGenerator.generate(msg, encryptor);
        // 加密后的数据
        return cmsEnvelopedData.getEncoded();
    }

    public static byte[] decryptData(byte[] encryptedData, PrivateKey decryptionKey) throws Exception {
        // 待解密的数据
        CMSEnvelopedData envelopedData = new CMSEnvelopedData(encryptedData);
        // 获取接收者信息的集合
        Collection<RecipientInformation> recip = envelopedData.getRecipientInfos().getRecipients();
        // 通常只有一个接收者
        KeyTransRecipientInformation recipientInfo = (KeyTransRecipientInformation) recip.iterator().next();

        // 使用密钥创建解密操作的接收者
        JceKeyTransRecipient recipient = new JceKeyTransEnvelopedRecipient(decryptionKey);
        // 使用接收者来解密
        return recipientInfo.getContent(recipient);
    }


    public static byte[] signData(byte[] data, X509Certificate signingCertificate, PrivateKey signingKey) throws Exception {
        // 待签名的数据
        CMSTypedData cmsData = new CMSProcessableByteArray(data);
        // 证书
        JcaCertStore certs = new JcaCertStore(Collections.singletonList(signingCertificate));
        // 算法
        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA").build(signingKey);
        // 用来生成签名
        CMSSignedDataGenerator cmsGenerator = new CMSSignedDataGenerator();

        // SignerInfoGenerator用于描述签名者的信息, 包括签名算法、证书等
        cmsGenerator.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder()
                .setProvider(BouncyCastleProvider.PROVIDER_NAME).build()).build(contentSigner, signingCertificate));
        cmsGenerator.addCertificates(certs);
        // 生成签名数据
        CMSSignedData cms = cmsGenerator.generate(cmsData, true);
        return cms.getEncoded();
    }

    public static boolean verifSignData(final byte[] signedData) throws Exception {
        ByteArrayInputStream bIn = new ByteArrayInputStream(signedData);
        ASN1InputStream aIn = new ASN1InputStream(bIn);
        // 构造出CMS的签名数据
        CMSSignedData s = new CMSSignedData(ContentInfo.getInstance(aIn.readObject()));
        aIn.close();
        bIn.close();

        // 从签名数据中获取所有证书
        Store<X509CertificateHolder> certs = s.getCertificates();
        // 从签名数据获取签名者信息
        SignerInformationStore signers = s.getSignerInfos();
        SignerInformation signer = signers.getSigners().iterator().next();

        // 根据签名者的唯一SID获取证书
        Collection<X509CertificateHolder> certCollection = certs.getMatches(signer.getSID());
        X509CertificateHolder certHolder = certCollection.iterator().next();

        // 使用签名者信息和相关证书来验证签名的有效性
        return signer.verify(new JcaSimpleSignerInfoVerifierBuilder().build(certHolder));
    }
}
