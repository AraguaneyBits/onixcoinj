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
package org.onixcoinj.core;

import java.net.InetAddress;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.utils.BriefLogFormatter;
import org.junit.Before;
import org.junit.Test;
import org.onixcoinj.params.OnixcoinMainNetParams;

/**
 * @date 16-ene-2018
 *
 * @version 1.0.0
 * @author Jose Luis Estevez jose.estevez.prieto@gmail.com
 */
public class PeersTest {

    private static final OnixcoinMainNetParams params = OnixcoinMainNetParams.get();

    @Before
    public void setUp() throws Exception {
        Context context = new Context(params);
    }

//    @Test
    public void testPeer() {
        try {
            BriefLogFormatter.init();
            System.out.println("Connecting to node");

            BlockStore blockStore = new MemoryBlockStore(params);
            BlockChain chain = new BlockChain(params, blockStore);
            PeerGroup peerGroup = new PeerGroup(params, chain);
            peerGroup.start();
            PeerAddress addr = new PeerAddress(params, InetAddress.getLocalHost());
            peerGroup.addAddress(addr);
            peerGroup.waitForPeers(1).get();
            Peer peer = peerGroup.getConnectedPeers().get(0);
            System.out.println("-" + peer);
//        Sha256Hash blockHash = Sha256Hash.wrap(args[0]);
//        Future<Block> future = peer.getBlock(blockHash);
//        System.out.println("Waiting for node to send us the requested block: " + blockHash);
//        Block block = future.get();
//        System.out.println(block);
            peerGroup.stopAsync();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
