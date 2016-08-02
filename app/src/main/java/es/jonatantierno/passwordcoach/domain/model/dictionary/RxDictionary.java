package es.jonatantierno.passwordcoach.domain.model.dictionary;

import rx.Observable;

public class RxDictionary {

    private final Observable<String> observable;

    public RxDictionary(Observable<String> observable) {

        this.observable = observable;
    }

    public Observable<String> asObservable() {
        return observable
                .flatMap(s -> split(s))
                .distinct()
                .map(s -> removePrefixes(s))
                .map(s -> removeSuffixes(s))
                .filter(s -> s.length() > 3)
                .filter(s -> !s.contains("://"))
                .map(s -> s.toLowerCase())
                .flatMap(this::userName);
    }

    private String removePrefixes(String s) {
        if (s.isEmpty()) return s;
        if (charIsOneOf(s.charAt(0), "(¿¡\"'")) return s.substring(1);
        else return s;
    }

    private String removeSuffixes(String s) {
        if (s.isEmpty()) return s;
        if (charIsOneOf(s.charAt(s.length()-1), ":)?!\"'")) return s.substring(0, s.length()-1);
        else return s;
    }

    private boolean charIsOneOf(char c, String characters) {
        return characters.indexOf(c) != -1;
    }

    private Observable<? extends String> split(String s) {
        return Observable.from(s.split("[ ,.;!()\\?¿]"));
    }

    private Observable<? extends String> userName(String s) {
        if (s.length() > 1 && s.charAt(0) == '@') {
            return Observable.just(s, s.substring(1));
        } else return Observable.just(s);
    }
}
