/*
 * Copyright 2018 Jose Luis Estevez jose.estevez.prieto@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onixcoinj.crypto;

import com.google.common.collect.ImmutableList;
import java.util.Date;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.onixcoinj.params.OnixcoinMainNetParams;

/**
 * @date 13-ene-2018
 *
 * @version 1.0.0
 * @author Jose Luis Estevez jose.estevez.prieto@gmail.com
 */
public class BIP39Test {

    private static final OnixcoinMainNetParams params = OnixcoinMainNetParams.get();

    public static String getPublicKey(ECKey privateKey) {
        return privateKey.getPublicKeyAsHex();
    }

    private static final String mnemonic = "brisk wood symptom party betray ozone dad super beyond sea memory power pig business extra";

    @Before
    public void setUp() throws Exception {
        Context context = new Context(params);
    }

    // Bitcoinj BIP39 Derivation Path is m/0'/0
    // Testing online tools https://iancoleman.io/bip39/ 
    // BIP39 Mnemonic = brisk wood symptom party betray ozone dad super beyond sea memory power pig business extra
    // Coin = ONX -Onixcoin
    // Derivation Path = BIP32
    // Client = MultiBitHD
    @Test
    public void testDeriveBIP39Address() {

        try {
            // mnemonic seed
            DeterministicSeed seed = new DeterministicSeed(mnemonic, null, "", new Date().getTime());

            // new wallet with mnemonic seed
            Wallet wallet = Wallet.fromSeed(params, seed);
            System.out.println(wallet);
            DeterministicKeyChain keyChain = DeterministicKeyChain.builder().seed(seed).build();

            // Derive 100 children 
            keyChain.maybeLookAhead();

            // Get M/0H/0/5 key 
            DeterministicKey key5 = keyChain.getKeyByPath(ImmutableList.of(ChildNumber.ZERO_HARDENED, ChildNumber.ZERO, new ChildNumber(5)), false);

            // check address
            Assert.assertEquals("XDRiRZZw4ckZj3HQiEuFCVQU6c9VQM8QU8", key5.toAddress(params).toString());

            // check public key
            Assert.assertEquals("0316a3e0a0f9c1a9e31b062c9c0ade320a59906a60693441e60783b5520026399d", getPublicKey(key5));

            // check private key
            Assert.assertEquals("KzfmxGLpezTFq7iQRNmi9wYoQvNsYewzS4thdpB18DNGQnwLYUSZ", key5.getPrivateKeyAsWiF(params));

        } catch (Exception ex) {
            Assert.fail();
        }
    }

}
