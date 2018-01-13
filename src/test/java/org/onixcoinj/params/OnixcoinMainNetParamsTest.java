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

import org.bitcoinj.core.Block;
import org.bitcoinj.core.Context;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @date 13-ene-2018
 *
 * @version 1.0.0
 * @author Jose Luis Estevez jose.estevez.prieto@gmail.com
 */
public class OnixcoinMainNetParamsTest {

    private static final OnixcoinMainNetParams params = OnixcoinMainNetParams.get();

    @Before
    public void setUp() throws Exception {
        Context context = new Context(params);
    }

    @Test
    public void testMainGenesisBlock() {
        Block genesis = params.getGenesisBlock();
        Assert.assertEquals("000007140b7a6ca0b64965824f5731f6e86daadf19eb299033530b1e61236e43", genesis.getHashAsString());
    }

}
