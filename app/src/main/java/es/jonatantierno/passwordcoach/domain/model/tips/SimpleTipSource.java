package es.jonatantierno.passwordcoach.domain.model.tips;

import es.jonatantierno.passwordcoach.domain.model.rules.Result;

public class SimpleTipSource implements TipSource {
    @Override
    public Tip tip(Result result) {
        if (result == null)
            return new EmptyTip();
        if (result.passwordIsStrong())
            return new Tip(TipType.ADVICE, "Writing passwords down", "If you need to write down your passwords, it is OK, as long as you keep it stored in your wallet, along with the rest of your personal items. Just do not write it in a post it on your monitor.");
        else
            return new Tip(TipType.TECHNIQUE, "Sentence", "Pick a sentence, and form a password with the first letter and the number of letters of each word. Symbols have length 0.");
    }
}
