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

package org.onixcoinj.params;

import static com.google.common.base.Preconditions.checkState;
import java.io.ByteArrayOutputStream;
import org.bitcoinj.core.AltcoinBlock;
import org.bitcoinj.core.Block;
import static org.bitcoinj.core.Coin.COIN;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.StoredBlock;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VerificationException;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptOpCodes;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.spongycastle.util.encoders.Hex;

/**
 * @date 13-ene-2018
 *
 * @version 1.0.0
 * @author Jose Luis Estevez jose.estevez.prieto@gmail.com
 */
public class OnixcoinMainNetParams extends AbstractOnixcoinParams {

    public static final int MAINNET_MAJORITY_WINDOW = MainNetParams.MAINNET_MAJORITY_WINDOW;
    public static final int MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED = MainNetParams.MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED;
    public static final int MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE = MainNetParams.MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;

    public OnixcoinMainNetParams() {
        super();
        id = ID_ONIX_MAINNET;

        // https://github.com/jestevez/onixcoin/blob/28aec388d7014fcc2bf1de60f2113b85d1840ddf/src/main.cpp#L3105
        packetMagic = 0xf3c3b9de;

        maxTarget = Utils.decodeCompactBits(0x1e0fffffL);
        port = 41016;
        addressHeader = 75;
        p2shHeader = 5;
        acceptableAddressCodes = new int[]{addressHeader, p2shHeader};
        dumpedPrivateKeyHeader = 128;

        this.genesisBlock = createGenesis(this);
        spendableCoinbaseDepth = 100;
        subsidyDecreaseBlockCount = 840000;

        String genesisHash = genesisBlock.getHashAsString();
        
        checkState(genesisHash.equals("000007140b7a6ca0b64965824f5731f6e86daadf19eb299033530b1e61236e43"));
        alertSigningKey = Hex.decode("04678afdb0fe5548271967f1a67130b7105cd6a828e03909a67962e0ea1f61deb649f6bc3f4cef38c4f35504e51ec112de5c384df7ba0b8d578a4c702b6bf11d5f");

        majorityEnforceBlockUpgrade = MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;
        majorityRejectBlockOutdated = MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED;
        majorityWindow = MAINNET_MAJORITY_WINDOW;

        dnsSeeds = new String[]{
            "seed.onixcoin.info",
            "localhost" // running local node 
        };
        
        bip32HeaderPub = 0x049d7cb2;
        bip32HeaderPriv = 0x049d7878;

        checkpoints.put(0,       Sha256Hash.wrap("000007140b7a6ca0b64965824f5731f6e86daadf19eb299033530b1e61236e43"));
        checkpoints.put(30000,   Sha256Hash.wrap("0000000000974475481a0c083a65d12806a58f94200e32860999450bf2049c2f"));
        checkpoints.put(60000,   Sha256Hash.wrap("0000000000123af5ae90c441ca59b3cc12fb5f49cd8cc734f7228ad1f6ef5c61"));
        checkpoints.put(90000,   Sha256Hash.wrap("000000000000179a0439dcd880f808685e8035206982dcacd09fc2f0e9235190"));
        checkpoints.put(120000,  Sha256Hash.wrap("000000000000020ab41d21692dfa81ca9b7dab22956212be9be02df36f3c8b49"));

    }

    //ONIXCOIN Mainnet Genesis block:
    //CBlock(hash=000007140b7a6ca0b64965824f5731f6e86daadf19eb299033530b1e61236e43, input=010000000000000000000000000000000000000000000000000000000000000000000000ea7b80ab9167a1e06e0654c686339ec1798e75a4b31f038d06d76cd52e82e1641636ed58f0ff0f1e83c50f00, PoW=000007140b7a6ca0b64965824f5731f6e86daadf19eb299033530b1e61236e43, ver=1, hashPrevBlock=0000000000000000000000000000000000000000000000000000000000000000, hashMerkleRoot=64e1822ed56cd7068d031fb3a4758e79c19e3386c654066ee0a16791ab807bea, nTime=1491940886, nBits=1e0ffff0, nNonce=1033603, vtx=1)
    //CTransaction(hash=64e1822ed56cd7068d031fb3a4758e79c19e3386c654066ee0a16791ab807bea, ver=1, vin.size=1, vout.size=1, nLockTime=0)
    //CTxIn(COutPoint(0000000000000000000000000000000000000000000000000000000000000000, 4294967295), coinbase 04ffff001d0104126f6e69782067656e6573697320626c6f636b)
    //CTxOut(nValue=1.00000000, scriptPubKey=04678afdb0fe5548271967f1a67130)
    //vMerkleTree: 64e1822ed56cd7068d031fb3a4758e79c19e3386c654066ee0a16791ab807bea 
    
    private static AltcoinBlock createGenesis(NetworkParameters params) {
        AltcoinBlock genesisBlock = new AltcoinBlock(params, Block.BLOCK_VERSION_GENESIS);
        Transaction t = new Transaction(params);
        try {
            // https://github.com/jestevez/onixcoin/blob/28aec388d7014fcc2bf1de60f2113b85d1840ddf/src/main.cpp#L2783
            byte[] bytes = Hex.decode("04ffff001d0104126f6e69782067656e6573697320626c6f636b");
            t.addInput(new TransactionInput(params, t, bytes));
            ByteArrayOutputStream scriptPubKeyBytes = new ByteArrayOutputStream();
            // https://github.com/jestevez/onixcoin/blob/28aec388d7014fcc2bf1de60f2113b85d1840ddf/src/main.cpp#L2789
            Script.writeBytes(scriptPubKeyBytes, Hex.decode("04678afdb0fe5548271967f1a67130b7105cd6a828e03909a67962e0ea1f61deb649f6bc3f4cef38c4f35504e51ec112de5c384df7ba0b8d578a4c702b6bf11d5f"));
            scriptPubKeyBytes.write(ScriptOpCodes.OP_CHECKSIG);
            t.addOutput(new TransactionOutput(params, t, COIN.multiply(1), scriptPubKeyBytes.toByteArray()));
        } catch (Exception e) {
            // Cannot happen.
            throw new RuntimeException(e);
        }
        genesisBlock.addTransaction(t);
        genesisBlock.setTime(1491940886L);
        genesisBlock.setDifficultyTarget(0x1e0ffff0L);
        genesisBlock.setNonce(1033603);
        return genesisBlock;
    }

    private static OnixcoinMainNetParams instance;

    public static synchronized OnixcoinMainNetParams get() {
        if (instance == null) {
            instance = new OnixcoinMainNetParams();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return ID_ONIX_MAINNET;
    }

    @Override
    public boolean isTestNet() {
        return false;
    }

    @Override
    public boolean allowMinDifficultyBlocks() {
        return false;
    }

    @Override
    public void checkDifficultyTransitions(StoredBlock sb, Block block, BlockStore bs) throws VerificationException, BlockStoreException {
        // TODO Implement this!!!
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public int getProtocolVersionNum(final NetworkParameters.ProtocolVersion version) {
        switch (version) {
            case PONG:
            case BLOOM_FILTER:
                return ONIXCOIN_PROTOCOL_VERSION_CURRENT;
            case CURRENT:
                return ONIXCOIN_PROTOCOL_VERSION_CURRENT;
            case MINIMUM:
            default:
                return ONIXCOIN_PROTOCOL_VERSION_CURRENT;
        }
    }
}
