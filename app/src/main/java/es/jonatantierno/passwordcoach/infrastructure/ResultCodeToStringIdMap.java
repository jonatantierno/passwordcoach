package es.jonatantierno.passwordcoach.infrastructure;

import java.util.HashMap;

import es.jonatantierno.passwordcoach.R;
import es.jonatantierno.passwordcoach.domain.model.rules.ResultCode;

public class ResultCodeToStringIdMap extends HashMap<ResultCode, Integer> {
    public ResultCodeToStringIdMap() {
        put(ResultCode.TOO_SHORT, R.string.password_is_too_short);
        put(ResultCode.IN_DICTIONARY, R.string.password_is_in_dictionary);
        put(ResultCode.WEAK, R.string.password_is_weak);
        put(ResultCode.STRONG, R.string.password_is_strong);
        put(ResultCode.WEAK_ACCORDING_TO_METER, R.string.weak_according_to_meter);
        put(ResultCode.CONTAINS_WORD_IN_DICTIONARY, R.string.contains_word_in_a_dictionary);
        put(ResultCode.IN_PERSONAL_ATTACK_DICTIONARY, R.string.in_personal_attack_dictionary);
    }
}
