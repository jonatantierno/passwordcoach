package es.jonatantierno.passwordcoach.domain.model.dictionary;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

public class RxDictionaryTest {

    TestSubscriber<String> testSubscriber;

    @Before
    public void setup() {
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void smoke() {
        new RxDictionary(Observable.just("hello dolly")).asObservable().subscribe(testSubscriber);

        testSubscriber.assertValues("hello", "dolly");
    }

    @Test
    public void usernames() {
        new RxDictionary(Observable.just("@username")).asObservable().subscribe(testSubscriber);

        testSubscriber.assertValues("username");
    }

    @Test
    public void hashtags() {
        new RxDictionary(Observable.just("#hashtag")).asObservable().subscribe(testSubscriber);

        testSubscriber.assertValues("hashtag");
    }

    @Test
    public void punctuation() {
        new RxDictionary(Observable.just("hello, dolly; nice wool, really."))
                .asObservable().subscribe(testSubscriber);

        testSubscriber.assertValues("hello", "dolly", "nice", "wool", "really");
    }

    @Test
    public void specialPrefixes() {
        new RxDictionary(Observable.just("¿dolly ¡nice (really \"maybe 'probably"))
                .asObservable().subscribe(testSubscriber);

        testSubscriber.assertValues("dolly", "nice", "really", "maybe", "probably");
    }

    @Test
    public void specialSuffixes() {
        new RxDictionary(Observable.just("hello: dolly? nice! really) maybe\" probably'"))
                .asObservable().subscribe(testSubscriber);

        testSubscriber.assertValues("hello", "dolly", "nice", "really", "maybe", "probably");
    }

    @Test
    public void longWords() {
        new RxDictionary(Observable.just("oh my god")).asObservable().subscribe(testSubscriber);

        testSubscriber.assertNoValues();
    }

    @Test
    public void url() {
        new RxDictionary(Observable.just("http://bitly.com, co/tsts")).asObservable().subscribe(testSubscriber);

        testSubscriber.assertNoValues();
    }

    @Test
    public void uppercase() {
        new RxDictionary(Observable.just("LowerCase")).asObservable().subscribe(testSubscriber);

        testSubscriber.assertValues("lowercase");
    }

    @Test
    public void duplicates() {
        new RxDictionary(Observable.just("hola", "hola")).asObservable().subscribe(testSubscriber);

        testSubscriber.assertValues("hola");
    }

}