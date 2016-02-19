package org.panda_lang.light.core.parser;

import org.panda_lang.light.Light;
import org.panda_lang.light.LightScript;
import org.panda_lang.light.core.Expression;
import org.panda_lang.light.core.Ray;
import org.panda_lang.light.core.parser.assistant.ExpressionRepresentation;
import org.panda_lang.light.core.parser.pattern.LightPattern;
import org.panda_lang.light.core.util.ExpressionRuntime;
import org.panda_lang.light.core.util.ExpressionUtils;
import org.panda_lang.panda.core.parser.Atom;
import org.panda_lang.panda.core.parser.Parser;
import org.panda_lang.panda.core.parser.essential.FactorParser;
import org.panda_lang.panda.core.syntax.Factor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExpressionParser implements Parser {

    public final Light light;

    public ExpressionParser(Light light) {
        this.light = light;
    }

    @Override
    public ExpressionRuntime parse(Atom atom) {
        String expressionSource = atom.getSourceCode().trim();

        for (final ExpressionRepresentation expressionRepresentation : light.getLightCore().getExpressionCenter().getExpressions()) {
            for (LightPattern pattern : expressionRepresentation.getPatterns()) {
                if (pattern.match(expressionSource)) {
                    final Collection<String> hollows = new ArrayList<>(pattern.getHollows());
                    final List<ExpressionRuntime> expressions = parse(atom, hollows);
                    final Factor[] factors = ExpressionUtils.toFactors(expressions);

                    final Expression expression = expressionRepresentation.getRepresentation();
                    final Ray ray = new Ray()
                            .lightScript((LightScript) atom.getPandaScript())
                            .pattern(pattern)
                            .expressionRuntimes(expressions)
                            .factors(factors);

                    return new ExpressionRuntime(expression, ray);
                }
            }
        }

        TypeParser typeParser = new TypeParser();
        Factor factor = typeParser.parse(atom, expressionSource);

        if (factor == null) {
            FactorParser factorParser = new FactorParser();
            factor = factorParser.parse(atom, expressionSource);
        }

        return new ExpressionRuntime(factor);
    }

    public ExpressionRuntime parse(Atom atom, String expression) {
        atom.setSourceCode(expression);
        return parse(atom);
    }

    public List<ExpressionRuntime> parse(Atom atom, Collection<String> expressions) {
        List<ExpressionRuntime> executables = new ArrayList<>(expressions.size());
        for (String expression : expressions) {
            ExpressionRuntime namedExecutable = parse(atom, expression);
            executables.add(namedExecutable);
        }
        return executables;
    }


}
