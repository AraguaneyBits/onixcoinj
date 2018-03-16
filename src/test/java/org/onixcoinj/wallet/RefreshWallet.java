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

package org.onixcoinj.wallet;

import java.io.File;
import java.net.InetAddress;
import java.util.Date;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;

import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;

import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.onixcoinj.params.OnixcoinMainNetParams;

/**
 * @date 22-feb-2018
 *
 * @version 1.0.0
 * @author Jose Luis Estevez jose.estevez.prieto@gmail.com
 */

public class RefreshWallet {
    private static final OnixcoinMainNetParams params = OnixcoinMainNetParams.get();

    public static String getPublicKey(ECKey privateKey) {
        return privateKey.getPublicKeyAsHex();
    }

    private static final String mnemonic = "brisk wood symptom party betray ozone dad super beyond sea memory power pig business extra";

    public static void main(String[] args) throws Exception {
        // mnemonic seed
        DeterministicSeed seed = new DeterministicSeed(mnemonic, null, "", new Date().getTime());

        // new wallet with mnemonic seed
        Wallet wallet = Wallet.fromSeed(params, seed);
        System.out.println(wallet.toString());

        // Set up the components and link them together.
        
        BlockStore blockStore = new MemoryBlockStore(params);
        BlockChain chain = new BlockChain(params, wallet, blockStore);

        final PeerGroup peerGroup = new PeerGroup(params, chain);
        InetAddress addr = InetAddress.getByName("node.onixcoin.info");
        peerGroup.addAddress(addr);
        peerGroup.startAsync();

        wallet.addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener() {
            @Override
            public synchronized void onCoinsReceived(Wallet w, Transaction tx, Coin prevBalance, Coin newBalance) {
                System.out.println("\nReceived tx " + tx.getHashAsString());
                System.out.println(tx.toString());
            }
        });

        // Now download and process the block chain.
        peerGroup.downloadBlockChain();
        peerGroup.stopAsync();
        wallet.saveToFile(new File("onixcoinj.wallet"));
        System.out.println("\nDone!\n");
        System.out.println(wallet.toString());
    }
}

