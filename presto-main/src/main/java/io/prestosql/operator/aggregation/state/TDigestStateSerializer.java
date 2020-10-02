/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.operator.aggregation.state;

import io.airlift.stats.TDigest;
import io.prestosql.spi.block.Block;
import io.prestosql.spi.block.BlockBuilder;
import io.prestosql.spi.function.AccumulatorStateSerializer;
import io.prestosql.spi.type.Type;

import static io.prestosql.type.TDigestType.TDIGEST;

public class TDigestStateSerializer
        implements AccumulatorStateSerializer<TDigestState>
{
    @Override
    public Type getSerializedType()
    {
        return TDIGEST;
    }

    @Override
    public void serialize(TDigestState state, BlockBuilder out)
    {
        if (state.getTDigest() == null) {
            out.appendNull();
        }
        else {
            TDIGEST.writeObject(out, state.getTDigest());
        }
    }

    @Override
    public void deserialize(Block block, int index, TDigestState state)
    {
        state.setTDigest((TDigest) TDIGEST.getObject(block, index));
    }
}