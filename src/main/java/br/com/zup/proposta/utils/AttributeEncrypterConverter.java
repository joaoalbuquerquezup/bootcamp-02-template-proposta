package br.com.zup.proposta.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class AttributeEncrypterConverter implements AttributeConverter<String, String>  {

    // TODO: Insecure https://rules.sonarsource.com/java/type/Vulnerability/RSPEC-5542#:~:text=To%20perform%20secure%20cryptography%2C%20operation,no%20padding%20scheme%2C%20is%20recommended.
    private static final String ALGORITHM = "AES";

    @Value("${database.encrypt.secret}")
    private String secret;

    private Key key;
    private Cipher cipher;

    @PostConstruct
    public void init() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.key = new SecretKeySpec(this.secret.getBytes(), ALGORITHM);
        this.cipher = Cipher.getInstance(ALGORITHM);
    }

    @Override
    public String convertToDatabaseColumn(String dataToDb) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(dataToDb.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dataFromDb) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes = Base64.getDecoder().decode(dataFromDb);
            byte[] decodedByes = cipher.doFinal(bytes);
            return new String(decodedByes);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
