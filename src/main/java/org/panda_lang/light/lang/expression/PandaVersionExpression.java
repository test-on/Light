package org.panda_lang.light.lang.expression;

import org.panda_lang.light.core.Expression;
import org.panda_lang.light.core.Ray;
import org.panda_lang.panda.Panda;
import org.panda_lang.panda.core.syntax.Essence;
import org.panda_lang.panda.lang.StringEssence;

public class PandaVersionExpression extends Expression {

    @Override
    public Essence getValue(Ray ray) {
        return new StringEssence(Panda.PANDA_VERSION);
    }

}