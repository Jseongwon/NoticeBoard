'use strict';
/**
 * userCryptHelper helps encrypt/decrypt user information.
 */
export const UserCryptHelper = (function () {
    const crypto = CryptoJS;

    return {
        createSalt() {
            return crypto.lib.WordArray.random(16).toString(crypto.enc.Hex);
        },
        createHashedPassword(secret, salt, iterations = 1000, keyLength = 64) {
            let config = {
                keySize: keyLength / 4,
                iterations: iterations,
                hasher: crypto.algo.SHA256
            }
            let key = crypto.PBKDF2(secret, crypto.enc.Hex.parse(salt), config);
            return key.toString();
        },
        createSignature(message, secretKey) {
            return crypto.HmacSHA256(message, secretKey).toString(crypto.enc.Base64);
        }
    }
})();