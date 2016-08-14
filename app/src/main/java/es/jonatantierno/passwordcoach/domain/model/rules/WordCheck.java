package es.jonatantierno.passwordcoach.domain.model.rules;

public class WordCheck {
    public static final int MIN_DICTIONARY_WORD_LENGTH = 3;

    public ResultCode analyze(String word, String password) {
        if (word.length() <= MIN_DICTIONARY_WORD_LENGTH) return ResultCode.STRONG;

        CharSequence processed = processed(word);

        if (password.contains(word) || password.contains(processed)){
            if (password.equals(word)) return ResultCode.IN_DICTIONARY;
            if (password.equals(processed)) return ResultCode.IN_DICTIONARY;
            else return ResultCode.CONTAINS_WORD_IN_DICTIONARY;
        }
        return ResultCode.STRONG;
    }

    private CharSequence processed(String word) {
        return word.replace('ñ','n')
                .replace('á','a')
                .replace('é','e')
                .replace('í','i')
                .replace('ó','o')
                .replace('ú','u')
                .replace('ü','u')
                .replace('ç','c');
    }
}
