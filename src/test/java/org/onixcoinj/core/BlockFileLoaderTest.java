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

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.Context;
import org.bitcoinj.utils.BlockFileLoader;
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
public class BlockFileLoaderTest {

    private static final OnixcoinMainNetParams params = OnixcoinMainNetParams.get();

    @Before
    public void setUp() throws Exception {
        Context context = new Context(params);
    }

    //@Test
    public void testLocalMainBlockFile() {
        //FIXME Ubicación de los bloques de mi nodo local
        String blockDir = "/home/jestevez/.onixcoin/blocks/";
        
        List<File> list = new LinkedList<>();
        for (int i = 0; true; i++) {
            File file = new File(blockDir + String.format(Locale.US, "blk%05d.dat", i));
            if (!file.exists()) {
                break;
            }
            list.add(file);
        }
        BlockFileLoader bfl = new BlockFileLoader(params, list);

        int i = 0;
        while (bfl.hasNext()) {
            Block blk = bfl.next();
            
            // test block 30000 hash
            if(i == 30000) {
                Assert.assertEquals("0000000000974475481a0c083a65d12806a58f94200e32860999450bf2049c2f", blk.getHashAsString());
            }
            // test block 60000 hash
            if(i == 60000) {
                Assert.assertEquals("0000000000123af5ae90c441ca59b3cc12fb5f49cd8cc734f7228ad1f6ef5c61", blk.getHashAsString());
            }
            
            // test block 90000 hash
            if(i == 90000) {
                Assert.assertEquals("000000000000179a0439dcd880f808685e8035206982dcacd09fc2f0e9235190", blk.getHashAsString());
            }
            
            // test block 120000 hash
            if(i == 120000) {
                Assert.assertEquals("000000000000020ab41d21692dfa81ca9b7dab22956212be9be02df36f3c8b49", blk.getHashAsString());
                break; // last block test
            }
            i++;
        }


    }
}
