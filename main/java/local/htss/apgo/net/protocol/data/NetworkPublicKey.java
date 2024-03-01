package local.htss.apgo.net.protocol.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import local.htss.apgo.net.clients.NetworkThread;
import local.htss.apgo.net.protocol.shared.NetworkData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
@Embeddable
public class NetworkPublicKey extends NetworkData {
    @Column(name = "key_exp")
    private BigInteger exponent;
    @Column(name = "key_mod")
    private BigInteger modulus;
    @Transient
    private PublicKey publicKey;

    public NetworkPublicKey(NetworkThread networkThread) {
        super(networkThread);
    }

    @Override
    public void encode(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(modulus.toByteArray().length);
        dataOutputStream.write(modulus.toByteArray());
        dataOutputStream.writeShort(exponent.toByteArray().length);
        dataOutputStream.write(exponent.toByteArray());
    }

    @Override
    public void decode(DataInputStream dataInputStream) throws IOException {
        short lenMod = (short) Math.min(dataInputStream.readShort(), 513);
        byte[] mod = new byte[lenMod];
        dataInputStream.readFully(mod);
        modulus = new BigInteger(mod);

        short lenExp = (short) Math.min(dataInputStream.readShort(), 3);
        byte[] exp = new byte[lenExp];
        dataInputStream.readFully(exp);
        exponent = new BigInteger(exp);
    }

    public BigInteger getExponent() {
        return exponent;
    }

    public void setExponent(BigInteger exponent) {
        this.exponent = exponent;
    }

    public BigInteger getModulus() {
        return modulus;
    }

    public void setModulus(BigInteger modulus) {
        this.modulus = modulus;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
        modulus = ((RSAPublicKey) publicKey).getModulus();
        exponent = ((RSAPublicKey) publicKey).getPublicExponent();
    }
}
