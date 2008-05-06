package com.googlecode.wmqutils.ssl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Simple custom SSL Socket factory for use instead of system properties. Based
 * on code by Peter Broadhurst
 * (http://hursleyonwmq.wordpress.com/2007/03/08/custom-sslsocketfactory-with-wmq-base-java/)
 */
public class PojoSslSocketFactoryFactory {

    public static final String PROTOCOL_SSL = "SSL";
    public static final String PROTOCOL_TLS = "TLS";
    public static final String PROTOCOL_SSL_TLS = "SSL_TLS";
    
    private String keyStoreType = KeyStore.getDefaultType();
    private File keyStore;
    private String keyStorePassword;
    private String keyPassword;
    private String keyManagementAlgorithm = KeyManagerFactory.getDefaultAlgorithm();

    private String trustStoreType = KeyStore.getDefaultType();
    private File trustStore;
    private String trustStorePassword;
    private String trustManagementAlgorithm = TrustManagerFactory.getDefaultAlgorithm();

    private String protocol = PROTOCOL_SSL;

    /**
     * Create the {@link SSLSocketFactory}
     * @return The {@link SSLSocketFactory}
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public SSLSocketFactory createSocketFactory() throws GeneralSecurityException, IOException {
        // Create a keystore object for the keystore
        KeyStore ks = KeyStore.getInstance(keyStoreType);
        
        if(keyStore == null) {
            throw new NullPointerException("keyStore must be set");
        } else if(!keyStore.exists()) {
            throw new FileNotFoundException("Key store file does not exist");
        }
        
        if(keyStorePassword == null) {
            throw new NullPointerException("keyStorePassword must be set");
        }
        
        // Open our file and read the keystore
        FileInputStream keyStoreInput = new FileInputStream(keyStore);
        try {
            ks.load(keyStoreInput, keyStorePassword.toCharArray());
        } finally {
            keyStoreInput.close();
        }

        KeyStore ts;
        if (trustStore != null) {
            // Create a keystore object for the truststore
            ts = KeyStore.getInstance(trustStoreType);

            if(!trustStore.exists()) {
                throw new FileNotFoundException("Trust store file does not exist");
            }
            
            // Open our file and read the truststore (no password)
            FileInputStream trustStoreInput = new FileInputStream(trustStore);
            try {
                char[] tsPassword = null;
                if(trustStorePassword != null) {
                    tsPassword = trustStorePassword.toCharArray();
                }
                ts.load(trustStoreInput, tsPassword);
            } finally {
                trustStoreInput.close();
            }
        } else {
            // fall back to using the key store
            ts = ks;
        }

        // Create a default trust and key manager
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(trustManagementAlgorithm);
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(keyManagementAlgorithm);

        // Initialise the managers
        trustManagerFactory.init(ts);
        
        String kp;
        if(keyPassword == null) {
            // fall back to key store password
            kp = keyStorePassword;
        } else {
            kp = keyPassword;
        }
        
        keyManagerFactory.init(ks, kp.toCharArray());

        // Get an SSL context. For more information on providers see:
        // http://www.ibm.com/developerworks/library/j-ibmsecurity.html
        // Note: Not all providers support all CipherSuites.
        SSLContext sslContext = SSLContext.getInstance(protocol);

        // Initialise our SSL context from the key/trust managers
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

        // Get an SSLSocketFactory to pass to WMQ
        return sslContext.getSocketFactory();
    }

    /**
     * Retrive the key store type. Defaults to
     * {@link KeyStore#getDefaultType()} 
     * @return The key store type.
     */
    public String getKeyStoreType() {
        return keyStoreType;
    }

    /**
     * Set the key store type. If not set,
     * defaults to
     * {@link KeyStore#getDefaultType()} 
     * @param keyStoreType The key store type, e.g "JKS"
     */
    public void setKeyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
    }

    /**
     * Retrieve the key store. Required.
     * @return The key store.
     */    
    public File getKeyStore() {
        return keyStore;
    }

    /**
     * Set the key store. Required.
     * @param keyStore The key store
     */
    public void setKeyStore(File keyStore) {
        this.keyStore = keyStore;
    }

    /**
     * Retrieve the key store password. Required.
     * @return The key store password.
     */
    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    /**
     * Set the key store password. Required.
     * @param keyStorePassword The key store password.
     */
    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    /**
     * Retrieve the key password. Defaults to
     * the key store password 
     * @return The key password.
     */
    public String getKeyPassword() {
        return keyPassword;
    }

    /**
     * Set the key password. If not set
     * it falls back to the key store password.
     * @param keyPassword The key store password
     */
    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    /**
     * Retrieve the key management factory algorithm. 
     * Defaults to
     * {@link KeyManagerFactory#getDefaultAlgorithm()} 
     * @return The key management factory algorithm.
     */
    public String getKeyManagementAlgorithm() {
        return keyManagementAlgorithm;
    }

     /**
     * Set the key management algorithm. If not set
     * it falls back to {@link KeyManagerFactory#getDefaultAlgorithm()} 
     * @param keyManagementAlgorithm The key management algorithm
     */
    public void setKeyManagementAlgorithm(String keyManagementAlgorithm) {
        this.keyManagementAlgorithm = keyManagementAlgorithm;
    }

    /**
     * Retrieve the trust store type.
     * Defaults to {@link KeyStore#getDefaultType()}
     * @return The trust store type
     */
    public String getTrustStoreType() {
        return trustStoreType;
    }

    /**
     * Set the trust store type. If not set
     * it falls back to {@link KeyStore#getDefaultType()} 
     * @param trustStoreType The trust store type
     */
    public void setTrustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
    }

    /**
     * Retrieve the trust store. 
     * If not set, falls back to the key store
     * @return The trust store
     */
    public File getTrustStore() {
        return trustStore;
    }

    /**
     * Set the trust store. If not set
     * it falls back to the key store 
     * @param trustStore The trust store
     */
    public void setTrustStore(File trustStore) {
        this.trustStore = trustStore;
    }

    /**
     * Retrieve the trust store password 
     * @return The trust store password
     */
    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    /**
     * Set the trust store password. Normally not required.
     * @param trustStorePassword The trust store password
     */
    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    /**
     * Retrieve the trust management factory algorithm. 
     * Defaults to
     * {@link KeyManagerFactory#getDefaultAlgorithm()} 
     * @return The key management factory algorithm.
     */
    public String getTrustManagementAlgorithm() {
        return trustManagementAlgorithm;
    }

    /**
     * Set the trust management algorithm. If not set
     * it falls back to {@link TrustManagerFactory#getDefaultAlgorithm()} 
     * @param trustManagementAlgorithm The trust management algorithm
     */
    public void setTrustManagementAlgorithm(String trustManagementAlgorithm) {
        this.trustManagementAlgorithm = trustManagementAlgorithm;
    }

    /**
     * Retrieve the SSL/TLS protocol. 
     * Defaults to SSL
     * @see PojoSslSocketFactoryFactory#PROTOCOL_SSL
     * @see PojoSslSocketFactoryFactory#PROTOCOL_TLS
     * @see PojoSslSocketFactoryFactory#PROTOCOL_SSL_TLS
     * @return The protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Sets the SSL/TLS protocol. 
     * Defaults to SSL.
     * @see PojoSslSocketFactoryFactory#PROTOCOL_SSL
     * @see PojoSslSocketFactoryFactory#PROTOCOL_TLS
     * @see PojoSslSocketFactoryFactory#PROTOCOL_SSL_TLS
     * @param protocol The protocol to use
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

}
