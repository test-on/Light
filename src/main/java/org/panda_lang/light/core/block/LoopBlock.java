package org.panda_lang.light.core.block;

import org.panda_lang.light.Light;
import org.panda_lang.panda.core.Particle;
import org.panda_lang.panda.core.parser.Atom;
import org.panda_lang.panda.core.parser.essential.FactorParser;
import org.panda_lang.panda.core.parser.essential.util.BlockInitializer;
import org.panda_lang.panda.core.parser.essential.util.BlockLayout;
import org.panda_lang.panda.core.parser.essential.util.Numeric;
import org.panda_lang.panda.core.syntax.Block;
import org.panda_lang.panda.core.syntax.Essence;
import org.panda_lang.panda.core.syntax.Factor;

public class LoopBlock extends Block {

    private final Factor factor;

    public LoopBlock(Factor factor) {
        this.factor = factor;
    }

    @Override
    public Essence run(Particle particle) {
        Numeric numeric = factor.getValue(particle);
        int times = numeric.getInt();

        for (int i = 0; i < times; i++) {
            Essence essence = super.run(particle);

            if (essence != null) {
                return essence;
            }
        }

        return null;
    }

    public static void initialize(Light light) {
        BlockLayout blockLayout = new BlockLayout(LoopBlock.class, "loop");
        blockLayout.initializer(new BlockInitializer() {
            @Override
            public Block initialize(Atom atom) {
                String times = atom.getBlockInfo().getSpecifiers().get(0);
                FactorParser factorParser = new FactorParser();
                Factor number = factorParser.parse(atom, times);
                return new LoopBlock(number);
            }
        });
        light.registerBlock(blockLayout);
    }

}
